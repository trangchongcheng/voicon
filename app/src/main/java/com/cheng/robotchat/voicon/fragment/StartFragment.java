package com.cheng.robotchat.voicon.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cheng.robotchat.voicon.BaseFragment;
import com.cheng.robotchat.voicon.model.User;
import com.cheng.robotchat.voicon.provider.UserDataHelper;
import com.cheng.robotchat.voicon.ultils.CoverBitmapToByte;
import com.cheng.robotchat.voicon.ultils.UserPreference;
import com.cheng.robotchat.voicon.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chientruong on 6/7/16.
 */
public class StartFragment extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private UserDataHelper userDataHelper;
    private UserPreference userPreference;
    private static final int SELECT_PHOTO = 100;
    @BindView(R.id.imgAvatar)
    CircleImageView imgName;

    @OnClick(R.id.imgAvatar)
    public void changeAvatar() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @BindView(R.id.edtName)
    EditText edtName;

    @BindView(R.id.rbtnTiengViet)
    RadioButton rbtnTiengViet;

    @BindView(R.id.rbtnEnglish)
    RadioButton rbtnEnglish;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @OnClick(R.id.btnLogin)
    public void onClickLogin() {
        String nameFrom = edtName.getText().toString().trim();
        if (nameFrom.length() > 3) {
            String nameTo = null;
            String language = "vn";
            byte[] imageFrom = CoverBitmapToByte.getBytes(CoverBitmapToByte.coverImageViewToBimap(imgName));
            byte[] imageTo = CoverBitmapToByte.getBytes(BitmapFactory.decodeResource(getResources(), R.drawable.ic_app));
            if (rbtnTiengViet.isChecked()) {
                language = "vn";
                nameTo = "Voi Con";
            } else if (rbtnEnglish.isChecked()) {
                language = "en";
                nameTo = "Elephant Chat";
            }
            userDataHelper.deleteAll();
            userDataHelper.addUser(new User(nameFrom, nameTo, language, imageFrom, imageTo));
            UserPreference.getInstance(getActivity()).setLoginTrue();
            mMainActivity.replaceFragment(new ChatFragment());
        } else {
            Toast.makeText(getActivity(), "Vui long nhap ten tu 4 ky tu tro len vao!", Toast.LENGTH_SHORT).show();
        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        userDataHelper = new UserDataHelper(getActivity());
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
            case SELECT_PHOTO:
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = data.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                        Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                        imgName.setImageBitmap(yourSelectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}
