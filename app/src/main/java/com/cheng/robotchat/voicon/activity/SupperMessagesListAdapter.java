package com.cheng.robotchat.voicon.activity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheng.robotchat.voicon.model.SupperLink;
import com.cheng.robotchat.voicon.R;

import java.util.ArrayList;

/**
 * Created by chientruong on 6/1/16.
 */
public class SupperMessagesListAdapter extends BaseAdapter {

    public static String typeLink;
    boolean isShowDialog = false;
    private Context context;
    private ArrayList<SupperLink> messagesItems;
    private IsShowMedia isShowMedia;

    public SupperMessagesListAdapter(Context context, ArrayList<SupperLink> messagesItems, IsShowMedia isShowMedia) {
        this.context = context;
        this.messagesItems = messagesItems;
        this.isShowMedia = isShowMedia;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
          ViewHolder viewHolder = null;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_message_left_supper,null);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.txtMsg);
            viewHolder.btnPlay = (ImageView) convertView.findViewById(R.id.btnPlay);
            viewHolder.btnDownload = (ImageView) convertView.findViewById(R.id.btnDownload);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final SupperLink message = messagesItems.get(position);
        //Log.d("Supper", "getView: "+message.getMessages());
        if(message.isDownload() && message.isPlay()){
            viewHolder.btnPlay.setVisibility(View.VISIBLE);
            viewHolder.btnDownload.setVisibility(View.VISIBLE);
        }else if(message.isDownload()){
            viewHolder.btnDownload.setVisibility(View.VISIBLE);
        }else if(message.isPlay()){
            viewHolder.btnPlay.setVisibility(View.VISIBLE);
        }
        Log.d("Supper", "getView: "+message.getMessages());
        viewHolder.tv.setText(message.getMessages());
        //final ViewHolder finalViewHolder = viewHolder;
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // finalViewHolder.btnPlay.setImageResource(R.drawable.ic_play_disable);
               // finalViewHolder.btnPlay.setEnabled(false);
                isShowMedia.isShow(message.getType(),message.getLink(),message.getValue());

            }
        });
      //  final ViewHolder finalViewHolder1 = viewHolder;
        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // finalViewHolder1.btnDownload.setImageResource(R.drawable.ic_download_dis);
               // finalViewHolder1.btnDownload.setEnabled(false);
                isShowMedia.isDowload(message.getFileName(),message.getType(),message.getLink(),message.getValue());
            }
        });
        return convertView;
    }
    public interface IsShowMedia {
        void isShow(String type, String url, String title);
        void isDowload(String fileName, String type, String url, String title);

    }
    public class ViewHolder{
        TextView tv;
        ImageView btnPlay , btnDownload;
    }

}

