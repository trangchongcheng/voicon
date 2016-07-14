package com.cheng.robotchat.voicon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by HuuTai on 8/24/15.
 */
public abstract class BaseContainerFragment extends Fragment {

    private boolean isViewInited;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isViewInited) {
            init();
            isViewInited = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    public abstract void init();

    public abstract int getLayoutId();

    protected abstract int getContainerId();

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        if( addToBackStack ) {
            transaction.addToBackStack(null);
        }

        transaction.add(getContainerId(), fragment);
        transaction.commit();
        //getChildFragmentManager().executePendingTransactions();
    }

    public boolean popFragment() {
        boolean isPop = false;

        if ( getChildFragmentManager().getBackStackEntryCount() > 0 ) {
            isPop = true;
            getChildFragmentManager().popBackStack();
        }
        return isPop;
    }
}
