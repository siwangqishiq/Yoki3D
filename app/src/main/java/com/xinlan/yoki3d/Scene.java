package com.xinlan.yoki3d;

import com.xinlan.yoki3d.primitve.Node;

/**
 * 基础场景类
 * 游戏节点 依附与此类中
 */
public class Scene {
    private int mIndex;
    protected Node mRoot = new Node();

    public Scene() {
    }

    public void addChild(Node node){
        mRoot.addChild(node);
    }

    public void update() {
        mRoot.visit();
    }
}//end class
