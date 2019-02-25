package com.xinlan.yoki3d;

import android.app.Application;
import com.xinlan.yoki3d.utils.ShaderUtil;

public class YokiHelper {
    public static final String TAG = "YokiHelper";

    public static void init(Application context) {
        ShaderUtil.ctx = context;
    }

}//end class
