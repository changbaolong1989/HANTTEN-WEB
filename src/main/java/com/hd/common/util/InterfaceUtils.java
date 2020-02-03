////////////////////////////////////////////////////////////////////
//        NMG(广播系统)
//
//        Copyright 2018  作者:SSY
////////////////////////////////////////////////////////////////////
package com.hd.common.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * 接口工具类
 * @author YJ
 */
public final class InterfaceUtils {


    /** 私有构造函数 */
    private InterfaceUtils(){
    }

    /**
     * 将一个json字符串转为List泛型对象
     *
     * @param jsonDataList json字符串
     *                     （如:{"data": [{"xx":"123"},{"xx":"456"}]}）
     * @param classType 数据要转换成的数据类型
     * @return <T> List<T> List对象数组
     */
    public static <T> List<T> jsonToObjectList(String jsonDataList, Class<T> classType){
        List<T> list = new ArrayList<T>();
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(jsonDataList).getAsJsonArray();
        Gson gson = new Gson();
        //加强for循环遍历JsonArray
        for (JsonElement item : jsonArray) {
            //使用GSON，直接转成Bean对象
            T tmpObject = gson.fromJson(item, classType);
            list.add(tmpObject);
        }
        return list;
    }

    /**
     * 将JSON转换为java单个对象
     *
     * @param json json字符串
     * @param classType 需要转换成的类型
     *
     * @return T 转换成的对象
     */
    public static <T> T jsonToObject(String json, Class<T> classType) {
        Gson gson = new Gson();
        T object = gson.fromJson(json,classType);
        return object;
    }



}