package com.cheng.robotchat.voicon.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.cheng.robotchat.voicon.BaseActivity;
import com.cheng.robotchat.voicon.fragment.ChatFragment;
import com.cheng.robotchat.voicon.R;


/**
 * Created by Welcome on 4/18/2016.
 */
public class VideoActivity extends BaseActivity {
    private VideoView videoView;
    private int position = 0;
    private MediaController mediaController;
    private Intent intent;
    private String linkArticle, typeVideo, title;
    private TextView tvTitle;
    private ProgressBar activity_progressBar;
    private String currentPosition ="currentPosition";
    private WebView webview;
    private final String  styleCss="<style>img{max-width: 100%; width:auto; height: auto;}\" +\n"+
            "                       a{color:#374046; text-decoration:none}"+
            "                    h3{font-size: 25px;color:#374046;}"+
            "</style>";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_video);
    }

    @Override
    public void init() {

        videoView = (VideoView) findViewById(R.id.videoView);
        tvTitle = (TextView) findViewById(R.id.activity_tvTitle);
        activity_progressBar = (ProgressBar) findViewById(R.id.activity_progressBar);
        intent = getIntent();
        webview = (WebView) findViewById(R.id.activity_webview);
        webview.setWebViewClient(new WebViewClient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        Log.d(TAG, "init: "+intent.getStringExtra(ChatFragment.URL));
        onReturnContent(intent.getStringExtra(ChatFragment.TITLE),intent.getStringExtra(ChatFragment.URL));
    }

    @Override
    public void setValue(Bundle savedInstanceState) {

    }

    @Override
    public void setEvent() {

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(currentPosition, videoView.getCurrentPosition());
        videoView.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt(currentPosition);
        videoView.seekTo(position);
    }



    public void onReturnContent(String content, String videoUrl) {
        activity_progressBar.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
        webview.loadData(styleCss+content,"text/html; charset=UTF-8", null);
        if (mediaController == null) {
            mediaController = new MediaController(VideoActivity.this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
        }
        try {
            videoView.setVideoURI(Uri.parse(videoUrl.replace(" ","%20")));

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
                }
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });
        activity_progressBar.setVisibility(View.GONE);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }
    }
}