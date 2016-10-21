package com.nhancv.mainmodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nhancv.appmanager.R;
import com.nhancv.appmanager.modules.appmanager.AppManagerActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by nhancao on 10/21/16.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnStartAppManager)
    void btnStartAppManagerOnClick() {
        startActivity(new Intent(MainActivity.this, AppManagerActivity.class));
    }

}
