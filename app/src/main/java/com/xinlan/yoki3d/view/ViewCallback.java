package com.xinlan.yoki3d.view;

public interface ViewCallback {
    void init(MainView ctx);

    void beforeOnDraw(MainView ctx);

    void afterOnDraw(MainView ctx);

    void onPause(MainView ctx);

    void onResume(MainView ctx);
}
