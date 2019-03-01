package com.xinlan.yoki3d.primitive;

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

    public Node() {
        mChildNodes = new LinkedList();
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
