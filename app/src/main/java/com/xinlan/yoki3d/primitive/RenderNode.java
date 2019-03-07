package com.xinlan.yoki3d.primitive;

/**
 *
 */
public abstract class RenderNode extends Node {
    protected int mProgramId;
    protected int mUMvpMatrixLoc;
    protected int mUTextureLoc;
    protected int mUCameraPosLoc;
    protected int mUModelMatrixLoc;
    protected int mULightPosLoc;

    abstract void initShader();

    abstract void initVertex();

}//end class
