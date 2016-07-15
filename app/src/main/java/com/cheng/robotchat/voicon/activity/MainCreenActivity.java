package com.cheng.robotchat.voicon.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.cheng.robotchat.voicon.fragment.ChatFragment;
import com.cheng.robotchat.voicon.fragment.FragmentSetting;
import com.cheng.robotchat.voicon.fragment.StartFragment;
import com.cheng.robotchat.voicon.ultils.UserPreference;
import com.cheng.robotchat.voicon.R;
import com.facebook.FacebookSdk;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.robohorse.gpversionchecker.GPVersionChecker;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chientruong on 5/30/16.
 */
public class MainCreenActivity extends AppCompatActivity implements FragmentSetting.FinishFragment {
    public final String TAG = getClass().getSimpleName();
    private Bundle save = null;
    private ShareDialog shareDialog;
    private View rootView;
    Fragment fragmentContainer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ViewDialog alert;
    private static int RESULT_LOAD_IMAGE = 1;

    public void setContentView() {
        setContentView(R.layout.activity_maincreen);
        rootView = getWindow().getDecorView().findViewById(android.R.id.content);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        init();
        setValue(savedInstanceState);
        setEvent();
    }

    public void init() {
        ButterKnife.bind(this);
        //toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        new GPVersionChecker.Builder(this).create();
        FacebookSdk.sdkInitialize(getApplicationContext());
        shareDialog = new ShareDialog(this);
        alert = new ViewDialog();
    }

    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public void store(Bitmap bm) {
        final String dirPath = Environment.getExternalStorageDirectory().getPath() + "/Sceenshots";
        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(dirPath, "Chat" + getTime() + ".PNG");
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public String getTime() {
        DateFormat df = new SimpleDateFormat("h:mm:a");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    public void sharePhotoToFacebook(Bitmap imagePath) {
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(imagePath)
                .setCaption("Testing")
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareApi.share(content, null);

    }


    public void setValue(Bundle saveInstance) {
        save = saveInstance;
        if (!UserPreference.getInstance(this).getIsLogin() && saveInstance == null) {
            fragmentContainer = new StartFragment();
            switchFragmentWithoutAddToBackstack(fragmentContainer);
        } else {
            fragmentContainer = new ChatFragment();
            replaceFragment(fragmentContainer);

        }
    }

    public void setEvent() {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.setting) {
            replaceFragment(new FragmentSetting());
            return true;
        }
        if (id == R.id.photo) {
            Bitmap bm = getScreenShot(rootView);
            store(bm);
            Toast.makeText(MainCreenActivity.this, getString(R.string.chup_man_hinh), Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean popFragment() {
        boolean isPop = false;

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            isPop = true;
            getSupportFragmentManager().popBackStack();
        }
        return isPop;
    }

    private Boolean exit = false;

    @Override
    public void onBackPressed() {

        if (!popFragment()) {
            if (exit) {
                finish();
            } else {
                Toast.makeText(this, getString(R.string.exit),
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 2500);
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");

    }

    @Override
    public void onFinish() {
        getSupportFragmentManager().popBackStack();
        Toast.makeText(MainCreenActivity.this, "!", Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }


    public class ViewDialog {

        public void showDialog(final Activity activity, final Bitmap bm) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog);

            Button btnShare = (Button) dialog.findViewById(R.id.btnShare);
            assert btnShare != null;
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // sharePhotoToFacebook(bm);
//                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                    Log.d(TAG, "onClick: "+Environment.getExternalStorageDirectory() + "/VoiConApp");
//                    Uri uri = Uri.parse(Environment.getExternalStorageDirectory() + "/VoiConApp");
//                    intent.setDataAndType(uri, "*/*");
//                    startActivity(Intent.createChooser(intent, "Voi Con App"));
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);

                }
            });

            dialog.show();

        }
    }
    public void replaceFragment(Fragment fragment){
        String FRAGMENT_TAG = fragment.getClass().getSimpleName();
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.activity_maincreen_frame, fragment, FRAGMENT_TAG)
                .addToBackStack(FRAGMENT_TAG)
                //.commitAllowingStateLoss();
                .commit();
    }
    public void switchFragmentWithoutAddToBackstack(Fragment fragment) {
        String FRAGMENT_TAG = fragment.getClass().getSimpleName();
        this.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.activity_maincreen_frame, fragment, FRAGMENT_TAG)
                .commitAllowingStateLoss();
    }
}


