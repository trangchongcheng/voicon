package com.cheng.robotchat.voicon.fragment;

import android.os.Bundle;
import android.util.Log;

import com.cheng.robotchat.voicon.BaseContainerFragment;
import com.cheng.robotchat.voicon.R;

/**
 * Created by chientruong on 5/30/16.
 */
public class FragmentContainer extends BaseContainerFragment {
    private final String TAG = getClass().getSimpleName();

    @Override
    public void init() {
        Log.d(TAG, "init: ");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_container;
    }

    @Override
    protected int getContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState==null){
//            if(UserPreference.getInstance(getActivity()).getIsLogin()){
//                replaceFragment(new ChatFragment(),true);
//
//            }else {
//                replaceFragment(new StartFragment(),true);
//            }
            replaceFragment(new ChatFragment(),true);

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }
}
