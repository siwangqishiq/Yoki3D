package com.xinlan.yoki3d.primitive;

import android.opengl.GLES20;
import android.opengl.GLES30;

import com.xinlan.yoki3d.MatrixState;
import com.xinlan.yoki3d.R;
import com.xinlan.yoki3d.utils.OpenglEsUtils;
import com.xinlan.yoki3d.utils.ShaderUtil;

import java.nio.FloatBuffer;

public class Point extends RenderNode{
    protected float mPointSize = 1;

    protected float mColors[] = new float[4];
    protected FloatBuffer mColorBuf;

    protected float mPosition[] = new float[3];
    protected FloatBuffer mPositionBuf;


    public Point(){
        mColorBuf = OpenglEsUtils.allocateBuf(mColors);
        mPositionBuf = OpenglEsUtils.allocateBuf(mPosition);

        mProgramId = ShaderUtil.buildShaderProgram(R.raw.point_vertex , R.raw.point_frg);
        mUniformMvpMatrixLoc = GLES30.glGetUniformLocation(mProgramId , "uMvpMatrix");
    }

    public void setColor(int r,int g, int b){
        mColors = OpenglEsUtils.convertColor(r , g , b , 255);

        mColorBuf.put(mColors[0]);
        mColorBuf.put(mColors[1]);
        mColorBuf.put(mColors[2]);
        mColorBuf.put(mColors[3]);

        mColorBuf.position(0);
    }

    public void setSize(float size){
        mPointSize = size;
    }

    public void setPosition(float x , float y , float z){
        mPosition[0] = x;
        mPosition[1] = y;
        mPosition[2] = z;

        mPositionBuf.put(mPosition[0]);
        mPositionBuf.put(mPosition[1]);
        mPositionBuf.put(mPosition[2]);
        mPositionBuf.position(0);
    }

    @Override
    public void render() {
        GLES30.glUseProgram(mProgramId);
        GLES30.glUniformMatrix4fv(mUniformMvpMatrixLoc ,
                1 , false , MatrixState.getFinalMatrix() , 0);
        GLES30.glVertexAttribPointer(0 ,4 ,  GLES20.GL_FLOAT , false , 0 , mColorBuf);
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glVertexAttribPointer(1 ,3 ,  GLES20.GL_FLOAT ,false ,  0 , mPositionBuf);
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glVertexAttrib1f(2,mPointSize);

        GLES30.glDrawArrays(GLES30.GL_POINTS , 0 , 1);
    }

}//end class
