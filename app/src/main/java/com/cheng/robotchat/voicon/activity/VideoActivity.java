package com.cheng.robotchat.voicon.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.cheng.robotchat.voicon.BaseActivity;
import com.cheng.robotchat.voicon.fragment.ChatFragment;
import com.cheng.robotchat.voicon.R;
import com.cheng.robotchat.voicon.ultils.CustomVideoView;


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
    private WebView webview;
    private final String  styleCss="<style>img{max-width: 100%; width:auto; height: auto;}\" +\n"+
            "                       a{color:#374046; text-decoration:none}"+
            "                    h3{font-size: 25px;color:#374046;}"+
            "</style>";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        init();
        setValue(savedInstanceState);
        setEvent();
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
        activity_progressBar.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
        try {
            videoView.setVideoURI(Uri.parse(videoUrl));
            webview.loadData(styleCss+content,"text/html; charset=UTF-8", null);
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