package com.cheng.robotchat.voicon;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cheng.robotchat.voicon.activity.MainCreenActivity;


/**
 * Created by chientruong on 6/13/16.
 */
public class BaseFragment extends Fragment {

    protected MainCreenActivity mMainActivity;
    protected SharedPreferences mSharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivity = (MainCreenActivity)getActivity();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mMainActivity);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBase(view);
        executeBase();
    }

    protected void initBase(View view) {
    }

    protected void executeBase() {
    }

    public void switchChildFragment(Fragment parentFragment, Fragment childFragment, int viewContainer) {
        String FRAGMENT_TAG = childFragment.getClass().getSimpleName();
        parentFragment.getChildFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(viewContainer, childFragment, FRAGMENT_TAG)
                .commitAllowingStateLoss();
    }

    public void switchChildFragmentWithoutAddToBackStack(Fragment parentFragment, Fragment childFragment, int viewContainer) {
        String FRAGMENT_TAG = childFragment.getClass().getSimpleName();
        parentFragment.getChildFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(viewContainer, childFragment, FRAGMENT_TAG)
                .commitAllowingStateLoss();
    }
}
