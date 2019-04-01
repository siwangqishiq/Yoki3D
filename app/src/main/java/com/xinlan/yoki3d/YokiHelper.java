package com.xinlan.yoki3d;

import android.app.Application;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.xinlan.yoki3d.utils.ShaderUtil;
import com.xinlan.yoki3d.view.MainView;


public class YokiHelper {
    public static final String TAG = "YokiHelper";

    public static Application ctx;

    public static void init(Application context) {
        ctx = context;
        ShaderUtil.ctx = context;
    }


    public static void add3dObjView(Window window, int x, int y, int width, int height, MainView view) {
        if (window == null)
            return;

        View decorView = window.getDecorView();
        if (decorView != null && decorView instanceof FrameLayout) {
            FrameLayout frameLayout = (FrameLayout) decorView;
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
            params.setMargins(x, y, 0, 0);
            frameLayout.addView(view, params);
        }
    }

}//end class
