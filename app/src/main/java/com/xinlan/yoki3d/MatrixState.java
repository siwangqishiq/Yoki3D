package com.xinlan.yoki3d;

import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class MatrixState {
    private static final int MATRIX_STACK_SIZE = 32;

    private float[] mProjMatrix = new float[16];//4x4矩阵 投影用
    private float[] mVMatrix = new float[16];//摄像机位置朝向9参数矩阵
    private float[] currMatrix;//当前变换矩阵

    private float[] pointLightPos = new float[3];
    private FloatBuffer pointLightPosBuf = ByteBuffer.allocateDirect(Float.BYTES * 3)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer();

    //设置摄像机
    private FloatBuffer cameraPosBuf = ByteBuffer.allocateDirect(Float.BYTES * 3)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer();
    private float[] cameraPos = new float[3];

    float[][] mStack = new float[MATRIX_STACK_SIZE][16];//用于保存变换矩阵的栈
    int stackTop = -1;//栈顶索引

    //获取具体物体的总变换矩阵
    float[] mMVPMatrix = new float[16];//总变换矩阵


    public static ThreadLocal<MatrixState> threadLocals = new ThreadLocal<MatrixState>();

    public static MatrixState getInstance() {
        if (threadLocals.get() == null) {
            threadLocals.set(new MatrixState());
        }
        return threadLocals.get();
    }

    //产生无任何变换的初始矩阵
    public void setInitStack() {
        currMatrix = new float[16];
        Matrix.setRotateM(currMatrix, 0, 0, 1, 0, 0);
    }

    //将当前变换矩阵存入栈中
    public void pushMatrix() {
        stackTop++;//栈顶索引加1
        for (int i = 0; i < 16; i++) {
            mStack[stackTop][i] = currMatrix[i];//当前变换矩阵中的各元素入栈
        }
    }

    //从栈顶取出变换矩阵
    public void popMatrix() {
        for (int i = 0; i < 16; i++) {
            currMatrix[i] = mStack[stackTop][i];//栈顶矩阵元素进当前变换矩阵
        }
        stackTop--;//栈顶索引减1
    }

    //沿X、Y、Z轴方向进行平移变换的方法
    public void translate(float x, float y, float z) {
        Matrix.translateM(currMatrix, 0, x, y, z);
    }

    //沿X、Y、Z轴方向进行旋转变换的方法
    public void rotate(float angle, float x, float y, float z)//设置绕xyz轴移动
    {
        Matrix.rotateM(currMatrix, 0, angle, x, y, z);
    }

    //设置摄像机的方法
    public void setCamera
    (
            float cx,
            float cy,
            float cz,
            float tx,
            float ty,
            float tz,
            float upx,
            float upy,
            float upz
    ) {
        Matrix.setLookAtM
                (
                        mVMatrix,    //存储生成矩阵元素的float[]类型数组
                        0,            //填充起始偏移量
                        cx, cy, cz,    //摄像机位置的X、Y、Z坐标
                        tx, ty, tz,    //观察目标点X、Y、Z坐标
                        upx, upy, upz    //up向量在X、Y、Z轴上的分量
                );

        cameraPos[0] = cx;
        cameraPos[1] = cy;
        cameraPos[2] = cz;

        cameraPosBuf.position(0);
        cameraPosBuf.put(cameraPos);
        cameraPosBuf.position(0);
    }

    public FloatBuffer getCameraPosBuf() {
        cameraPosBuf.position(0);
        return cameraPosBuf;
    }

    //设置透视投影参数
    public void setProjectFrustum
    (
            float left,        //near面的left
            float right,    //near面的right
            float bottom,   //near面的bottom
            float top,      //near面的top
            float near,        //near面与视点的距离
            float far       //far面与视点的距离
    ) {
        Matrix.frustumM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }

    //设置正交投影参数
    public void setProjectOrtho
    (
            float left,        //near面的left
            float right,    //near面的right
            float bottom,   //near面的bottom
            float top,      //near面的top
            float near,        //near面与视点的距离
            float far       //far面与视点的距离
    ) {
        Matrix.orthoM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }


    public float[] getFinalMatrix()//计算产生总变换矩阵的方法
    {
        //摄像机矩阵乘以变换矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, currMatrix, 0);
        //投影矩阵乘以上一步的结果矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
        return mMVPMatrix;
    }

    //获取具体物体的变换矩阵
    public float[] getMMatrix() {
        return currMatrix;
    }

    public void setPerspective(float yFovInDegrees, float aspect, float n, float f) {
        final float angleInRadians = (float) (yFovInDegrees * Math.PI / 180.0);
        final float a = (float) (1.0 / Math.tan(angleInRadians / 2.0));

        mProjMatrix[0] = a / aspect;
        mProjMatrix[1] = 0f;
        mProjMatrix[2] = 0f;
        mProjMatrix[3] = 0f;

        mProjMatrix[4] = 0f;
        mProjMatrix[5] = a;
        mProjMatrix[6] = 0f;
        mProjMatrix[7] = 0f;

        mProjMatrix[8] = 0f;
        mProjMatrix[9] = 0f;
        mProjMatrix[10] = -((f + n) / (f - n));
        mProjMatrix[11] = -1f;

        mProjMatrix[12] = 0f;
        mProjMatrix[13] = 0f;
        mProjMatrix[14] = -((2f * f * n) / (f - n));
        mProjMatrix[15] = 0f;
    }

    public void setPointLightPos(float x, float y, float z) {
        pointLightPos[0] = x;
        pointLightPos[1] = y;
        pointLightPos[2] = z;

        pointLightPosBuf.position(0);
        pointLightPosBuf.put(pointLightPos);
        pointLightPosBuf.position(0);
    }

    public FloatBuffer getPointLightPosBuf() {
        pointLightPosBuf.position(0);
        return pointLightPosBuf;
    }

}//end class
