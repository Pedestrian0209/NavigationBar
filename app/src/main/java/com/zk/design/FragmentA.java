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

public class FragmentA extends Fragment implements IFragment, View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);
        view.findViewById(R.id.show).setOnClickListener(this);
        view.findViewById(R.id.hide).setOnClickListener(this);
        return view;
    }

    @Override
    public String getTitle() {
        return "A";
    }

    @Override
    public int getActiveIconRes() {
        return R.drawable.ic_home_blue_24dp;
    }

    @Override
    public int getInActiveIconRes() {
        return R.drawable.ic_home_black_24dp;
    }

    @Override
    public void onContinueClick() {
        Toast.makeText(getContext(), "onContinueClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onInterceptClick(Context context) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show:
                ((MainActivity)getActivity()).show();
                break;
            case R.id.hide:
                ((MainActivity)getActivity()).hide();
                break;
        }
    }
}
