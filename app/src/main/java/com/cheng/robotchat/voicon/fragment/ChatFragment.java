package com.cheng.robotchat.voicon.fragment;


import android.Manifest;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.robotchat.voicon.activity.MainCreenActivity;
import com.cheng.robotchat.voicon.activity.MessagesListAdapter;
import com.cheng.robotchat.voicon.activity.VideoActivity;
import com.cheng.robotchat.voicon.interfaces.OnReturnObject;
import com.cheng.robotchat.voicon.model.Links;
import com.cheng.robotchat.voicon.model.Message;
import com.cheng.robotchat.voicon.model.User;
import com.cheng.robotchat.voicon.provider.UserDataHelper;
import com.cheng.robotchat.voicon.services.ApiService;
import com.cheng.robotchat.voicon.services.GetJSONObject;
import com.cheng.robotchat.voicon.ultils.Session;
import com.cheng.robotchat.voicon.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chientruong on 5/30/16.
 */
public class ChatFragment extends Fragment implements OnReturnObject, MessagesListAdapter.OnPassLink {
    private final String TAG = getClass().getSimpleName();
    public static final String URL = "url";
    public static final String TITLE = "title";
    private UserDataHelper userDataHelper;
    private ArrayList<User> arrUser;
    private User user;
    private String time;
    private Shimmer shimmer;
    private MediaPlayer player;
    private MessagesListAdapter adapter;
    private List<Message> listMessages;
    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();
    public static int oneTimeOnly = 0;
    private double positionPause = 0;
    int id = 1;

    @BindView(R.id.fragment_chat_imgSend)
    ImageView imgSend;
    @BindView(R.id.fragment_chat_imgClose)
    ImageView imgClose;
    @BindView(R.id.tvWaiting)
    ShimmerTextView tvWaiting;
    @OnClick(R.id.fragment_chat_imgClose)
    void onClose() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
        ll.setVisibility(View.GONE);
    }

    //    @BindView(R.id.fragment_chat_seekBar)
//    SeekBar seekBar;
    @BindView(R.id.tvDuration)
    TextView tvDuration;

    @BindView(R.id.tvNameSong)
    TextView tvNameSong;

    @BindView(R.id.llMedia)
    RelativeLayout ll;
    @BindView(R.id.fragment_chat_imgPlay)
    ImageView img;
    @BindView(R.id.llChat)
    LinearLayout llChat;
    boolean isPause = false;

    @OnClick(R.id.fragment_chat_imgPlay)
    void onPlay() {
        if (player.isPlaying()) {
            player.pause();
            img.setImageResource(R.drawable.ic_pause);
            positionPause = player.getCurrentPosition();
            isPause = true;
        } else if (isPause) {
            player.seekTo((int) positionPause);
            player.start();
            img.setImageResource(R.drawable.ic_play);
        }
    }

    @OnClick(R.id.fragment_chat_imgSend)
    void onClick() {
        rlStart.setVisibility(View.GONE);
        time = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()) + "<br>";
        String data = edtMessages.getText().toString().trim().replace(" ", "%20");
        String params = GetSesion(data, arrUser.get(0).getmLanguage());
        if (!data.equals("")) {
            listMessages.add(new Message(arrUser.get(0).getmImageFrom(), arrUser.get(0).getmNameFrom(), getTime(), edtMessages.getText().toString().trim(), true));
            adapter.notifyDataSetChanged();
            new GetJSONObject(getActivity(),
                    "http://vnbot.azurewebsites.net/api/bot?data=" + data + params, "", ApiService.ApiResquestType.TYPE_GET, ChatFragment.this).execute();

            edtMessages.setText("");
            tvWaiting.setVisibility(View.VISIBLE);
            //llChat.setEnabled(false);
            enableDisableView(llChat,false);
        } else {
            Toast.makeText(getActivity(), getString(R.string.nhap), Toast.LENGTH_SHORT).show();
        }
    }

    private final String Key = "LOLQuang-_-";

    public String GetSesion(String data, String param) {
        String time = String.valueOf(System.currentTimeMillis());
        data = Session.convertPassMd5(data + Key);
        String data2 = Session.convertPassMd5(data + param + "*" + time);
        String h = Session.convertPassMd5(data + "?" + data2);

        return "&param=" + param + "&time=" + time + "&sesion=" + h;
    }


    @BindView(R.id.fragment_chat_edtMessages)
    EditText edtMessages;
    @BindView(R.id.fragment_chat_lvMessages)
    ListView lvMessages;
    @BindView(R.id.rlStart)
    RelativeLayout rlStart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        userDataHelper = new UserDataHelper(getActivity());
        Log.d(TAG, "onCreateView: hihihi");
        arrUser = userDataHelper.getAllArticle();
        ButterKnife.bind(this, view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
        Log.d(TAG, "onCreateView: " + getActivity().getClass().getSimpleName());
        Animation mAnimation = new TranslateAnimation(0, 300,
                0, 0);
        mAnimation.setDuration(15000);
        mAnimation.setRepeatMode(Animation.RESTART);
        mAnimation.setRepeatCount(Animation.INFINITE);
        tvNameSong.setAnimation(mAnimation);
        toggleAnimation(view);
        listMessages = new ArrayList<Message>();
        adapter = new MessagesListAdapter(getActivity(), listMessages, this);
        lvMessages.setAdapter(adapter);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);

        if ( view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup)view;

            for ( int idx = 0 ; idx < group.getChildCount() ; idx++ ) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }

    public void toggleAnimation(View target) {
        if (shimmer != null && shimmer.isAnimating()) {
            shimmer.cancel();
        } else {
            shimmer = new Shimmer();
            shimmer.start(tvWaiting);
        }
    }


    public String getTime() {
        DateFormat df = new SimpleDateFormat("h:mm:a");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    public void playBeep() {

        try {
            Uri notification = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getActivity(),
                    notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReturnObjectFinish(JSONObject object) throws JSONException {
        enableDisableView(llChat,true);
        tvWaiting.setVisibility(View.GONE);
        if (object == null) {
            listMessages.add(new Message(arrUser.get(0).getmImageFrom(), arrUser.get(0).getmNameFrom(), getTime(), "Lỗi kết nối mạng vui lòng thử lại (@_@)", true));
            adapter.notifyDataSetChanged();
        } else {
            String value = null;
            try {
                value = object.getString("Value");

                String isDownload = object.getString("IsDownload");
                String type = object.getString("Type");
                ArrayList<Links> link = new ArrayList<>();

                if (!object.getString("Links").equals("null")) {
                    JSONArray jArray = object.getJSONArray("Links");
                    for (int i = 0; i < jArray.length(); i++) {
                        link.add(new Links(
                                jArray.getJSONObject(i).getString("FileName"),
                                jArray.getJSONObject(i).getString("Title"),
                                jArray.getJSONObject(i).getString("Url"),
                                jArray.getJSONObject(i).getBoolean("IsDownload"),
                                jArray.getJSONObject(i).getBoolean("IsPlay"))
                        );
                    }
                }
                listMessages.add(new Message(arrUser.get(0).getmImageTo(), arrUser.get(0).getmNameTo(), getTime(), value, isDownload, type, link, false));
                adapter.notifyDataSetChanged();
                playBeep();
              //  llChat.setEnabled(true);
            } catch (JSONException e) {
                e.printStackTrace();
                listMessages.add(new Message(arrUser.get(0).getmImageFrom(), arrUser.get(0).getmNameFrom(), getTime(), "Nội dung bạn nhập không hợp lệ", true));
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onPassPlayLink(final String type, final String url, final String title) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type.equals("Mp3")) {
                    Log.d(TAG, "onPassPlayLink: " + url);

                    if (player != null) {
                        player.stop();
                        player.release();
                        player = null;
                    }
                    // Log.d(TAG, "onPassPlayLink: " + player.isPlaying());
                    player = new MediaPlayer();
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        player.setDataSource(url);
                        player.prepare();
                        player.start();

                        ll.setVisibility(View.VISIBLE);
                        ll.startAnimation(AnimationUtils.loadAnimation(getActivity(),
                                R.anim.slid_down));
                        finalTime = player.getDuration();
                        startTime = player.getCurrentPosition();

                        //seekBar.setProgress(0);
                        // tvDuraion.setText(startTime+"%");
                        tvNameSong.setText(title);
                        myHandler.postDelayed(UpdateSongTime, 100);

                        //progressBar.dismiss();
                    } catch (Exception e) {
                        //  progressBar.dismiss()\;
                        e.printStackTrace();
                        ll.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), getString(R.string.mp3_error), Toast.LENGTH_SHORT).show();
                    }


                } else if (type.equals("Video")) {
                    Log.d(TAG, "onPassPlayLink: " + url);
                    Intent intent = new Intent(getActivity(), VideoActivity.class);
                    intent.putExtra(URL, url);
                    intent.putExtra(TITLE, title);
                    startActivity(intent);
                }
            }
        });

    }


    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            if (player != null) {
                startTime = player.getCurrentPosition();
                int time = (int) ((startTime / finalTime) * 100);
                tvDuration.setText(time + "%");
                myHandler.postDelayed(this, 100);
            }

        }
    };

    @Override
    public void onPassDownloadLink(String fileName, String type, String url, String title) {
        new Downloader().execute(url, title, fileName);
    }

    private ProgressDialog progressBar;

    public void showDiaglog(Context context) {
        progressBar = new ProgressDialog(context);
        progressBar.setCancelable(true);
        progressBar.setMessage("Vui long doi...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();
    }


    private class Downloader extends AsyncTask<String, Integer, Void> {
        private NotificationManager mNotifyManager;
        private Builder mBuilder;

        @Override
        protected void onPreExecute() {
            mNotifyManager = (NotificationManager) getActivity().getSystemService(getContext().NOTIFICATION_SERVICE);
            mBuilder = new Builder(getContext());

            super.onPreExecute();
        }

        private boolean isDownloaded = false;

        @Override
        protected Void doInBackground(String... params) {
            mBuilder.setContentTitle(getString(R.string.dang_tai))
                    .setContentText(params[1])
                    .setSmallIcon(R.drawable.ic_app);
            FileOutputStream fos;
            InputStream is;

            try {
                String pathl = "";
                URL url = new URL(params[0]);
            /* making a directory in sdcard */
                String sdCard = Environment.getExternalStorageDirectory() + "/Download/";

                File myDir = new File(sdCard);
                String fileName = params[2];
            /* if specified not exist create new */
                if (myDir.exists()) {
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.connect();
                    is = con.getInputStream();

                    //                   int responseCode = con.getResponseCode();
//                    if (responseCode == HttpURLConnection.HTTP_OK) {
//                        String disposition = con.getHeaderField("Content-Disposition");
//                        String contentType = con.getContentType();
//                        Log.d(TAG, "doInBackground: "+contentType);
//                       // int contentLength = con.getContentLength();
//                        if (contentType.equals("video/mp4")) {
//
//                           fileName=params[1]+".mp4";
//                        }else if(contentType.equals("audio/mpeg")){
//                            fileName=params[1]+".mp3";
//                        }
//                        else {
//                           // Toast.makeText(getActivity(), "Khong the tai file nay", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    Map<String, List<String>> headerFields = con.getHeaderFields();
//
//                    Set<String> headerFieldsSet = headerFields.keySet();
//                    Iterator<String> hearerFieldsIter = headerFieldsSet.iterator();
//
//                    while (hearerFieldsIter.hasNext()) {
//
//                        String headerFieldKey = hearerFieldsIter.next();
//                        List<String> headerFieldValue = headerFields.get(headerFieldKey);
//
//                        StringBuilder sb = new StringBuilder();
//                        for (String value : headerFieldValue) {
//                            sb.append(value);
//                            sb.append("");
//                        }
//
//                        Log.d(TAG, headerFieldKey + "=" + sb.toString());
//
//                    }toString

                    pathl = sdCard + fileName;

                    Log.d(TAG, "doInBackground1: " + fileName);

                    int lenghtOfFile = con.getContentLength();

                    long total = 0;
                    fos = new FileOutputStream(new File(pathl));
                    byte[] buffer = new byte[1024];
                    int count, latestPercentDone;
                    int percentDone = -1;
                    while ((count = is.read(buffer)) != -1) {
                        total += count;
                        latestPercentDone = ((int) (total * 100) / lenghtOfFile);
                        // publishProgress((int) (total * 100) / lenghtOfFile);
                        if (percentDone != latestPercentDone) {
                            percentDone = latestPercentDone;
                            publishProgress(percentDone);
                        }
                        fos.write(buffer, 0, count);
                    }
                    is.close();
                    fos.flush();
                    fos.close();
                    Log.d("test", "Image Saved in sdcard..");
                } else {
                    Toast.makeText(getActivity(), "Khong tim thay thu muc", Toast.LENGTH_SHORT).show();
                }

            } catch (FileNotFoundException ex) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        isDownloaded = true;
                        Toast.makeText(getActivity(), "Link nay chua ho tro", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException io) {
                is = null;
                fos = null;
                io.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            // Update progress
            Log.d(TAG, "onProgressUpdate: " + values[0]);
            mBuilder.setProgress(100, values[0], false);
            mNotifyManager.notify(id, mBuilder.build());
            //    super.onProgressUpdate(values);
        }


        @Override
        protected void onPostExecute(Void result) {
            if (!isDownloaded) {
                mBuilder.setContentText(getString(R.string.da_tai_xong));
                // Removes the progress bar
                mBuilder.setProgress(0, 0, false);
                mNotifyManager.notify(id, mBuilder.build());
            }
        }

    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

}
