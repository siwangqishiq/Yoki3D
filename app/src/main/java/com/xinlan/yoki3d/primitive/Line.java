package com.xinlan.yoki3d.primitive;

import com.xinlan.yoki3d.utils.OpenglEsUtils;

import java.nio.ByteBuffer;

public class Line extends RenderNode{
    protected float[] mPoints = new float[2 * 3];
    protected ByteBuffer mPointBuf;

    protected float[] mColor = new float[3];
    protected ByteBuffer mColorBuf;

    public Line(){
    }

    @Override
    public void render() {

    }
}//end class
