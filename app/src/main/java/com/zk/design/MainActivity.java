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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomNavigationView navigationView = findViewById(R.id.navigation);
        List<IFragment> fragments = new ArrayList<>();
        fragments.add(new FragmentA());
        fragments.add(new FragmentB());
        fragments.add(new FragmentC());
        navigationView.initFragments(getSupportFragmentManager(), R.id.container, fragments);
        navigationView.showTip(1, "你好");
        navigationView.showTipDot(2, true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigationView.showTip(1, "100000");
                navigationView.showTipDot(2, false);
            }
        }, 3000);
    }

}
