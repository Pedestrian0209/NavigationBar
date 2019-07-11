package com.zk.design;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zk.design.fragment.IFragment;

public class FragmentB extends Fragment implements IFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_b, container, false);
    }

    @Override
    public String getTitle() {
        return "B";
    }

    @Override
    public int getActiveIconRes() {
        return R.drawable.ic_dashboard_blue_24dp;
    }

    @Override
    public int getInActiveIconRes() {
        return R.drawable.ic_dashboard_black_24dp;
    }

    @Override
    public void onContinueClick() {

    }

    @Override
    public boolean onInterceptClick() {
        return true;
    }

    @Override
    public void onInterceptClickEvent(Context context) {
        Toast.makeText(context, "onInterceptClickEvent", Toast.LENGTH_SHORT).show();
    }
}
