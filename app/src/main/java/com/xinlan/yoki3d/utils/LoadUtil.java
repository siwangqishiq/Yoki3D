package com.xinlan.yoki3d.utils;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.xinlan.yoki3d.model.ObjData;
import com.xinlan.yoki3d.model.Vec3;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class LoadUtil {
    //求两个向量的叉积
    public static float[] getCrossProduct(float x1, float y1, float z1,
                                          float x2, float y2, float z2) {
        //求出两个矢量叉积矢量在XYZ轴的分量ABC
        float A = y1 * z2 - y2 * z1;
        float B = z1 * x2 - z2 * x1;
        float C = x1 * y2 - x2 * y1;

        return new float[]{A, B, C};
    }

    //向量规格化
    public static float[] vectorNormal(float[] vector) {
        //求向量的模
        float module = (float) Math.sqrt(vector[0] * vector[0] + vector[1] * vector[1] + vector[2] * vector[2]);
        return new float[]{vector[0] / module, vector[1] / module, vector[2] / module};
    }


    //从obj文件中加载携带顶点信息的物体，并自动计算每个顶点的平均法向量
    public static ObjData loadObjFromAsset(String fileName, Resources res) {
        ObjData objData = new ObjData();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(res.getAssets().open(fileName));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String readLineContent = null;

            List<Vec3> vBankList = new ArrayList<Vec3>();
            //扫面文件，根据行类型的不同执行不同的处理逻辑
            while ((readLineContent = bufferedReader.readLine()) != null) {
                if(TextUtils.isEmpty(readLineContent))
                    continue;

                //System.out.println(readLineContent);
                //用空格分割行中的各个组成部分
                String[] contents = readLineContent.split("[ ]+");
                String typeValue = contents[0];
                if (typeValue.trim().equals("v")) {//此行为顶点坐标
                    //若为顶点坐标行则提取出此顶点的XYZ坐标添加到原始顶点坐标列表中
                    Vec3 v = new Vec3(Float.parseFloat(contents[1]) ,
                            Float.parseFloat(contents[2]),
                            Float.parseFloat(contents[3]));
                    vBankList.add(v);
                } else if (typeValue.trim().equals("f")) {//此行为三角形面
                    //f 1/2/3 2/3/4 3/4/5

                    String face1 = contents[1];
                    String[] face1Strs = face1.split("/");
                    int point1Index = Integer.parseInt(face1Strs[0]) - 1;

                    String face2 = contents[2];
                    String[] face2Strs = face2.split("/");
                    int point2Index = Integer.parseInt(face2Strs[0]) - 1;

                    String face3 = contents[3];
                    String[] face3Strs = face3.split("/");
                    int point3Index = Integer.parseInt(face3Strs[0]) - 1;

                    objData.vertexList.add(vBankList.get(point1Index));
                    objData.vertexList.add(vBankList.get(point2Index));
                    objData.vertexList.add(vBankList.get(point3Index));
                }
            }//end while
        } catch (Exception e) {
            Log.d("load error", "load error");
            e.printStackTrace();
        }
        return objData;
    }
}//end class
