package com.cheng.robotchat.voicon.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cheng.robotchat.voicon.AnalyticsApplication;
import com.cheng.robotchat.voicon.R;
import com.cheng.robotchat.voicon.fragment.ChatFragment;
import com.cheng.robotchat.voicon.ultils.CustomVideoView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


/**
 * Created by Welcome on 4/18/2016.
 */
public class VideoActivity extends AppCompatActivity {
    private final String TAG= getClass().getSimpleName();
    private CustomVideoView videoView;
    private int position = 0;
    private MediaController mediaController;
    private Intent intent;
    private String linkArticle, typeVideo, title;
    private TextView tvTitle;
    private ProgressBar activity_progressBar;
    private String currentPosition ="currentPosition";
    private Tracker mTracker;
    private CountDownTimer count;
    private final String  styleCss="<style>img{max-width: 100%; width:auto; height: auto;}\" +\n"+
            "                       a{color:#374046; text-decoration:none}"+
            "                    h3{font-size: 25px;color:#374046;}"+
            "</style>";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        setContentView();
        init();
        setValue(savedInstanceState);
        setEvent();
        count = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "onTick: "+millisUntilFinished);
            }

            public void onFinish() {
                Log.d(TAG, "onFinish: "+MainCreenActivity.isShow);
               MainCreenActivity.isShow=true;
            }
        }.start();
    }

    public void sendScreenImageName(String tag) {
        // [START screen_view_hit]
        mTracker.setScreenName(getString(R.string.app_name) + "-" + tag + "-" + android.os.Build.MODEL);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]
    }
    public void setContentView() {
        setContentView(R.layout.activity_video);
    }

    public void init() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        videoView = (CustomVideoView) findViewById(R.id.videoView);
        tvTitle = (TextView) findViewById(R.id.activity_tvTitle);
        activity_progressBar = (ProgressBar) findViewById(R.id.activity_progressBar);
        activity_progressBar.setVisibility(View.VISIBLE);
        intent = getIntent();
        onReturnContent(intent.getStringExtra(ChatFragment.TITLE),intent.getStringExtra(ChatFragment.URL));
    }

    public void setValue(Bundle savedInstanceState) {

    }

    public void setEvent() {

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(currentPosition, videoView.getCurrentPosition());
       // videoView.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt(currentPosition);
        Log.d(TAG, "onRestoreInstanceState: ");
        videoView.seekTo(position);
        videoView.start();
    }



    public void onReturnContent(String content, String videoUrl) {
        tvTitle.setText(getString(R.string.title_video)+": "+content);
        sendScreenImageName(TAG+"-"+content);
        try {
            videoView.setVideoURI(Uri.parse(videoUrl));
            if (mediaController == null) {
                mediaController = new MediaController(VideoActivity.this);
                videoView.setMediaController(mediaController);
            }

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        videoView.requestFocus();
        videoView.setVisibility(View.VISIBLE);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.seekTo(position);
                if (position == 0) {
                    videoView.start();
                    activity_progressBar.setVisibility(View.GONE);
                }
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });

    }
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//
//            getWindow().addFlags(
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//           getWindow().clearFlags(
//                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//
//            getWindow().addFlags(
//                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//            getWindow().clearFlags(
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }
}