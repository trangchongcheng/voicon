package com.cheng.robotchat.voicon;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cheng.robotchat.voicon.ultils.ConnectivityChangeReceiver;
import com.cheng.robotchat.voicon.R;

/**
 * Created by chientruong on 5/30/16.
 */
public abstract class BaseActivity extends AppCompatActivity implements ConnectivityChangeReceiver.OnConnectivityChangedListener {
    public final String TAG = getClass().getSimpleName();
    private ConnectivityChangeReceiver connectivityChangeReceiver;
    private IntentFilter filter;
    private int times;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        connectivityChangeReceiver = new ConnectivityChangeReceiver(this);
        filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        init();
        setValue(savedInstanceState);
        setEvent();
        Log.d(TAG, "onCreate: hihi");
    }
    public abstract void setContentView();
    public abstract void init();
    public abstract void setValue(Bundle saveInstance);
    public abstract void setEvent();

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
        
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        unregisterReceiver(connectivityChangeReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        times = 0;
        super.onResume();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        Log.d(TAG, "onResumeBaseMainActivity: ");
        if (times < 1) {
            registerReceiver(connectivityChangeReceiver, filter);
            ++times;
            Log.d(TAG, "times: " + times);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }
    @Override
    public void onConnectivityChanged(boolean isConnected) {
        if (!isConnected) {
            showDialog(this);
        }

    }
    public void showDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getResources().getString(R.string.khong_co_ket_noi));
        builder.setMessage(getResources().getString(R.string.kiem_tra_ket_noi));
        builder.setPositiveButton(getResources().getString(R.string.dong_y), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.cancel();
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.bat_mang),new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                dialog.dismiss();
            }
        });
        AlertDialog diag = builder.create();
        diag.show();
    }
}
