package com.zk.design;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zk.design.fragment.IFragment;
import com.zk.design.widget.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    ArrayList<IFragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.navigation);
        fragments = new ArrayList<>();
        if (savedInstanceState == null) {
            fragments.add(new FragmentA());
            fragments.add(new FragmentB());
            fragments.add(new FragmentC());
        } else {
            FragmentA fragmentA = (FragmentA) getSupportFragmentManager().findFragmentByTag(FragmentA.class.getSimpleName());
            if (fragmentA == null) {
                fragmentA = new FragmentA();
            }
            FragmentB fragmentB = (FragmentB) getSupportFragmentManager().findFragmentByTag(FragmentB.class.getSimpleName());
            if (fragmentB == null) {
                fragmentB = new FragmentB();
            }
            FragmentC fragmentC = (FragmentC) getSupportFragmentManager().findFragmentByTag(FragmentC.class.getSimpleName());
            if (fragmentC == null) {
                fragmentC = new FragmentC();
            }
            fragments.add(fragmentA);
            fragments.add(fragmentB);
            fragments.add(fragmentC);
        }
        navigationView.initFragments(getSupportFragmentManager(), R.id.container, fragments);
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigationView.showTip(1, "100000");
                navigationView.showTipDot(2, false);
            }
        }, 3000);*/
    }

    public void show() {
        navigationView.showTip(1, "你好");
        navigationView.showTipDot(2, true);
    }

    public void hide() {
        navigationView.showTip(1, "");
        navigationView.showTipDot(2, false);
    }
}
