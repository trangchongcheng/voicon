package com.cheng.robotchat.voicon.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cheng.robotchat.voicon.model.Message;
import com.cheng.robotchat.voicon.model.SupperLink;
import com.cheng.robotchat.voicon.ultils.Utils;
import com.cheng.robotchat.voicon.R;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesListAdapter extends BaseAdapter implements SupperMessagesListAdapter.IsShowMedia {
    private OnPassLink onPassLink;
    public static String typeLink;
    boolean isShowDialog = false;
    private Context context;
    private List<Message> messagesItems;
    private SupperMessagesListAdapter.IsShowMedia isShowMedia;
    private static final String HASHTAG_PATTERN = "(#[\\p{L}0-9-_]+)";
    private static final String MENTION_PATTERN = "(@[\\p{L}0-9-_]+)";

    public MessagesListAdapter(Context context, List<Message> messagesItems, OnPassLink onPassLink) {
        this.context = context;
        this.messagesItems = messagesItems;
        this.onPassLink = onPassLink;
    }

    @Override
    public int getCount() {
        return messagesItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messagesItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String link = "";
        int isFirts = 1;
        SupperMessagesListAdapter sup = null;
        Message m = messagesItems.get(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (messagesItems.get(position).isSelf()) {
                convertView = mInflater.inflate(R.layout.list_item_message_right,
                        null);
                CircleImageView circleImageView = (CircleImageView) convertView.findViewById(R.id.profile_image);
                TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
                TextView tvFrom = (TextView) convertView.findViewById(R.id.lblMsgFrom);
                TextView tvMsg = (TextView) convertView.findViewById(R.id.txtMsg);

                if(m.getImageFrom()!=null){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(m.getImageFrom() , 0, m.getImageFrom() .length);
                    circleImageView.setImageBitmap(bitmap);
                }
                tvTime.setText(m.getTime());
                tvFrom.setText(m.getFromName());
                tvMsg.setText(m.getValue());

        } else {
            ArrayList<SupperLink> list = new ArrayList<>();
            convertView = mInflater.inflate(R.layout.listitem_layout,
                    null);
            CircleImageView circleImageView = (CircleImageView) convertView.findViewById(R.id.listitem_profile_image);
            TextView tvTime = (TextView) convertView.findViewById(R.id.listitem_tvTime);
            TextView tvFrom = (TextView) convertView.findViewById(R.id.listitem_tvFrom);
            ListView lv = (ListView) convertView.findViewById(R.id.listitem_lv);

            tvTime.setText(m.getTime());
            tvFrom.setText(m.getFromName());
            if(m.getImageTo()!=null){
                Bitmap bitmap = BitmapFactory.decodeByteArray(m.getImageTo() , 0, m.getImageTo() .length);
                circleImageView.setImageBitmap(bitmap);
            }
            if (m.getLinks().size() > 0) {
                //  lp.height = 1200;
                //  lv.setLayoutParams(lp);
                for (int i = 0; i < m.getLinks().size(); i++) {
                    if (!m.getLinks().get(i).getUrl().equals("null")) {

                        list.add(new SupperLink(m.getLinks().get(i).getFileName(),m.getType(),m.getValue(), m.getLinks().get(i).getTitle(), m.getLinks().get(i).getUrl(), m.getLinks().get(i).isDownload(), m.getLinks().get(i).isPLay()));
                    } else {
                        list.add(new SupperLink(m.getLinks().get(i).getTitle()));
                    }
                }
            } else {
                //  lp.height = 80;
                //  lv.setLayoutParams(lp);
                list.add(new SupperLink(m.getValue()));
            }


            Log.d("ngu nguoi", "getView: " + list.size());
            sup = new SupperMessagesListAdapter(context, list, this);
            if(isFirts==1){
                lv.setAdapter(sup);
                ++isFirts;
            }
            sup.notifyDataSetChanged();
            Utils.setListViewHeightBasedOnItems(lv);
        }
        return convertView;

    }

    @Override
    public void isShow(String type,String url, String title) {
        onPassLink.onPassPlayLink(type,url,title);
    }

    @Override
    public void isDowload(String fileName,String type,String url, String title) {
        onPassLink.onPassDownloadLink(fileName,type,url,title);

    }

    public interface OnPassLink{
        void onPassPlayLink(String type, String url, String title);
        void onPassDownloadLink(String fileName, String type, String url, String title);
    }
    public static Bitmap decodeBitmap(Uri selectedImage, Context context)
            throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(context.getContentResolver()
                .openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 100;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(context.getContentResolver()
                .openInputStream(selectedImage), null, o2);
    }
}
