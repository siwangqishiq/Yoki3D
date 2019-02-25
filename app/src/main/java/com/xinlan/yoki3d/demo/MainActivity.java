package com.xinlan.yoki3d.demo;

import android.app.Activity;
import android.os.Bundle;

import com.xinlan.yoki3d.YokiHelper;
import com.xinlan.yoki3d.view.MainView;

public class MainActivity extends Activity {
    private MainView mMainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YokiHelper.init(getApplication());
        mMainView = new MainView(this);
        mMainView.setRefreshColor(255 , 0 , 0, 255);
        setContentView(mMainView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMainView.onResume();
    }

}
