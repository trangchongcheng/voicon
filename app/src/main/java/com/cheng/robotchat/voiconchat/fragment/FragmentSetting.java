package com.cheng.robotchat.voiconchat.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cheng.robotchat.voiconchat.BaseFragment;
import com.cheng.robotchat.voiconchat.R;
import com.cheng.robotchat.voiconchat.model.User;
import com.cheng.robotchat.voiconchat.provider.UserDataHelper;
import com.cheng.robotchat.voiconchat.ultils.CoverBitmapToByte;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chientruong on 6/9/16.
 */
public class FragmentSetting extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private UserDataHelper userDataHelper;
    private ArrayList<User> arrUser;
    private static final int SELECT_PHOTO_FROM = 100;
    private static final int SELECT_PHOTO_TO = 200;
    private OnUpdatelListener mCallback;
    @BindView(R.id.setting_imgAvatarFrom)
    CircleImageView imgAvatarFrom;

    @OnClick(R.id.setting_imgAvatarFrom)
    public void changeAvatarFrom() {
//        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//        photoPickerIntent.setType("image/*");
//        startActivityForResult(photoPickerIntent, SELECT_PHOTO_FROM);
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                SELECT_PHOTO_FROM);
    }


    @BindView(R.id.setting_tvNameFrom)
    TextView tvNameFrom;


    @BindView(R.id.setting_edtNameFrom)
    EditText edtNameFrom;

    @BindView(R.id.setting_btnChange)
    Button btnChange;
    @BindView(R.id.setting_rbtnEnglish)
    RadioButton rbtnEnglish;
    @BindView(R.id.setting_rbtnTiengViet)
    RadioButton rbtnTiengViet;

    @OnClick(R.id.setting_btnChange)
    public void onChange() {
        String nameFrom = edtNameFrom.getText().toString().trim();
        String nameTo = null;
        if (nameFrom.length() > 3) {
            String language = "vn";
            byte[] imageFrom = CoverBitmapToByte.getBytes(CoverBitmapToByte.coverImageViewToBimap(imgAvatarFrom));
            if (rbtnTiengViet.isChecked()) {
                language = "vn";
                nameTo = "Voi Con";
            } else if (rbtnEnglish.isChecked()) {
                language = "en";
                nameTo = "Robot Chat";
            }
            userDataHelper.UpdateFrom(new User(1, nameFrom, language,imageFrom,nameTo));
            mCallback.onUpdatelListener(true);
            Toast.makeText(getActivity(), "Thành công! Tắt mở lại ứng dụng để cập nhật thay đổi!", Toast.LENGTH_LONG).show();
            mMainActivity.popFragment();
        } else {
            Toast.makeText(getActivity(), "Vui long nhap ten 4 ki tu tro len vao!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        userDataHelper = new UserDataHelper(getActivity());
        arrUser = userDataHelper.getAllArticle();
        tvNameFrom.setText(arrUser.get(0).getmNameFrom());
        setImageFrom(imgAvatarFrom);
        if (arrUser.get(0).getmLanguage().equals("vn")) {
            rbtnTiengViet.setChecked(true);
        } else {
            rbtnEnglish.setChecked(true);
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void setImageFrom(CircleImageView circleImage) {
      //  Bitmap bitmap = BitmapFactory.decodeByteArray(arrUser.get(0).getmImageFrom(), 0, arrUser.get(0).getmImageFrom().length);
      //  circleImage.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 120, 120, false));
        Glide.with(mMainActivity)
                .load(arrUser.get(0).getmImageFrom())
                .asBitmap()
                .placeholder(R.drawable.ic_avatar)
                .into(circleImage);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.setting).setVisible(false);
        menu.findItem(R.id.photo).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                // Not implemented here
                return false;
            default:
                break;
        }

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PHOTO_FROM:
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = data.getData();
                    // InputStream imageStream = null;
                    try {
                        //imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                        // Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                        imgAvatarFrom.setImageBitmap(decodeBitmap(selectedImage, getActivity()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
                break;
            default:
                break;
        }
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
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = mMainActivity;
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
        try {
            mCallback = (OnUpdatelListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    public interface OnUpdatelListener{
        void onUpdatelListener(boolean isUpdate);
    }

}
