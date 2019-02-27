package com.xinlan.yoki3d.model;

import java.util.ArrayList;
import java.util.List;

public class ObjData {
    public List<Vec3> vertexList = new ArrayList<Vec3>(64);

    public List<Vec3> normalList = new ArrayList<Vec3>(64);

    public List<Vec2> textureCoordList = new ArrayList<Vec2>(64);

    public String textureImage;

    public float[] convertVertexListToArray() {
        float[] vs = new float[3 * vertexList.size()];

        for (int i = 0, len = vertexList.size(); i < len; i++) {
            Vec3 v = vertexList.get(i);
            vs[3 * i + 0] = v.x;
            vs[3 * i + 1] = v.y;
            vs[3 * i + 2] = v.z;
        }//end for i

        return vs;
    }
}//end class
