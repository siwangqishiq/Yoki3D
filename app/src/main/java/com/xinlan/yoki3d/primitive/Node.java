package com.xinlan.yoki3d.primitive;

import android.opengl.Matrix;

import com.xinlan.yoki3d.render.CoreRender;
import com.xinlan.yoki3d.render.IRender;

import java.util.LinkedList;

/**
 * 基础 节点类
 * 游戏中的对象 均派生自此类 用此类来管理对象树
 */
public class Node implements IRender {
    public static final int NODE_STATUS_NORMAL = 1;
    public static final int NODE_STATUS_UNVISIBLE = 0;
    public static final int NODE_STATUS_GONE = -1;


    protected LinkedList<Node> mChildNodes;
    protected String mName;
    protected int mIndex;
    protected Node mParentNode;
    protected int mNextInsertIndex = 0;
    protected int mStatus = NODE_STATUS_NORMAL;

    public float[] mModelMatrix = new float[4 * 4];//存贮节点变换的矩阵 model Matrix

    public Node() {
        mChildNodes = new LinkedList();
        Matrix.setIdentityM(mModelMatrix, 0);
    }

    public void scale(float scale) {
        Matrix.scaleM(mModelMatrix, 0, scale, scale, scale);
        adjustChildMatrix(mModelMatrix);
    }

    public void translate(float x, float y, float z) {
        Matrix.translateM(mModelMatrix, 0, x, y, z);
        adjustChildMatrix(mModelMatrix);
    }

    public void rotate(float x, float y, float z, float angle) {
        Matrix.rotateM(mModelMatrix, 0, angle, x, y, z);
        adjustChildMatrix(mModelMatrix);
    }

    public void adjustChildMatrix(float[] parentMat) {
        if (mChildNodes != null && mChildNodes.size() > 0) {
            for (Node node : mChildNodes) {
                //Matrix.setIdentityM(node.mModelMatrix, 0);
                Matrix.multiplyMM(node.mModelMatrix, 0, parentMat, 0,
                        node.mModelMatrix, 0);
                //node.adjustChildMatrix(parentMat);
            }//end for each
        }
    }

    public float[] getModelMatrix() {
        return mModelMatrix;
    }

    public void setNodeName(String name) {
        this.mName = name;
    }

    public void addChild(final Node n) {
        if (n == null)
            return;

        n.mParentNode = this;
        mChildNodes.add(n);
    }

    public void removeChild(final Node n) {
        if (n == null)
            return;

        mChildNodes.remove(n);
    }

    public void visit() {
        onVisit();

        if (mChildNodes.size() == 0)
            return;

        for (final Node node : mChildNodes) {
            if (node != null && node.mStatus == NODE_STATUS_NORMAL) {
                CoreRender.getInstance().addRenderCmd(this);
                node.visit();
            }
        }//end for each
    }

    /**
     *
     */
    public void onVisit() {
        CoreRender.getInstance().addRenderCmd(this);
    }

    @Override
    public void render() {
        //default do nothing
    }
}//end class
