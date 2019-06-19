package com.wangfeixixi.logxixi;

import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * 基于java环境的log打印
 */
public class LogJava {
    /**
     * 任何bean对象，或数据类型
     *
     * @param o debug
     */
    public static void d(Object o) {
        d(Utils.toString(o));
    }

    /**
     * 任何bean对象，或数据类型
     *
     * @param o debug
     */
    public static void e(Object o) {
        e(Utils.toString(o));
    }

    /**
     * 任何bean对象，打印并规范数据
     *
     * @param o debug
     */
    public static void bean(Object o) {
        String jsonString = JSON.toJSONString(o);
        dJsonString(jsonString, 2);
    }

    /**
     * 打印json数据
     *
     * @param json String
     */
    public static void json(String json) {
        dJsonString(json, 2);
    }

    /**
     * 打印xml数据
     *
     * @param xml xml数据
     */
    public static void xml(@Nullable String xml) {
        if (Utils.isEmpty(xml)) {
            d("Empty/Null xml content");
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            d(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException e) {
            e("Invalid xml");
        }
    }

    private static void d(String msg) {
        System.out.println(msg);
    }

    private static void e(String msg) {
        System.err.println(msg);
    }

    private static void dJsonString(String json, int indentSpaces) {
        if (Utils.isEmpty(json)) {
            d("Empty/Null json content");
            return;
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                //设置缩进
                String message = jsonObject.toString(indentSpaces);

                d(message);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(indentSpaces);
                d(message);
                return;
            }
            e("Invalid Json");
        } catch (JSONException e) {
            e("Invalid Json");
        }
    }
}
