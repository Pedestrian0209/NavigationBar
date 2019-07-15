package com.zk.design;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zk.design.fragment.IFragment;

public class FragmentC extends Fragment implements IFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_c, container, false);
    }

    @Override
    public String getTitle() {
        return "C";
    }

    @Override
    public int getActiveIconRes() {
        return R.drawable.ic_notifications_blue_24dp;
    }

    @Override
    public int getInActiveIconRes() {
        return R.drawable.ic_notifications_black_24dp;
    }

    @Override
    public void onContinueClick() {

    }

    @Override
    public boolean onInterceptClick(Context context) {
        return false;
    }
}
