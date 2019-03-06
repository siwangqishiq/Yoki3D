package com.xinlan.yoki3d.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import com.xinlan.yoki3d.MatrixState;
import com.xinlan.yoki3d.R;
import com.xinlan.yoki3d.YokiHelper;
import com.xinlan.yoki3d.primitive.Sphere;
import com.xinlan.yoki3d.view.MainView;
import com.xinlan.yoki3d.view.ViewCallback;

public class SphereActivity extends Activity implements ViewCallback {
    private MainView mMainView;

    private SeekBar mSeekBar1;
    private SeekBar mSeekBar2;
    private SeekBar mSeekBar3;

    private Sphere planet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YokiHelper.init(getApplication());
        setContentView(R.layout.view_seekbar);

        mSeekBar1 = findViewById(R.id.ambient_light);
        mSeekBar2 = findViewById(R.id.diffuse_light);
        mSeekBar3 = findViewById(R.id.specular_light);

        mMainView = new MainView(this);

        ((FrameLayout) findViewById(R.id.content)).addView(mMainView);
        mMainView.setRefreshColor(255, 255, 255, 255);
        mMainView.setCustomAction(this);


        mSeekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (planet != null) {
                    float v = ((float) progress) / 100;
                    planet.setAmbientLightStrength(v);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (planet != null) {
                    float v = ((float) progress) / 100;
                    planet.setDiffuseLightStrength(v);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        mSeekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (planet != null) {
                    float v = ((float) progress) / 100;
                    planet.setSpecularLightStrength(v);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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

    @Override
    public void init(MainView ctx) {
        MatrixState.getInstance().setPointLightPos(200, 200, 0);

        planet = new Sphere(4);
        planet.setPosition(0, 0, -15);
        ctx.addChild(planet);
    }

    @Override
    public void beforeOnDraw(MainView ctx) {

    }

    @Override
    public void afterOnDraw(MainView ctx) {

    }

    @Override
    public void onPause(MainView ctx) {

    }

    @Override
    public void onResume(MainView ctx) {

    }
}//end class
