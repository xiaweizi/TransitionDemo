package com.xiaweizi.transitiondemo.activity.weather;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.xiaweizi.transitiondemo.R;
import com.xiaweizi.transitiondemo.fragment.DetailFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.view_detail, new DetailFragment());
        fragmentTransaction.commit();
    }
}
