package com.wangfeixixi.log;

import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import static com.wangfeixixi.log.Utils.isEmpty;

/**
 * String规范类
 */
public final class ToStringUtil {
    /**
     * 任意数据类型转String
     *
     * @param object 任意数据类型
     * @return String
     */
    public static String toString(Object object) {
        if (object == null) {
            return "null";
        }
        //打印数组
        if (object instanceof boolean[])
            return Arrays.toString((boolean[]) object);
        if (object instanceof byte[])
            return Arrays.toString((byte[]) object);
        if (object instanceof char[])
            return Arrays.toString((char[]) object);
        if (object instanceof short[])
            return Arrays.toString((short[]) object);
        if (object instanceof int[])
            return Arrays.toString((int[]) object);
        if (object instanceof long[])
            return Arrays.toString((long[]) object);
        if (object instanceof float[])
            return Arrays.toString((float[]) object);
        if (object instanceof double[])
            return Arrays.toString((double[]) object);
        if (object instanceof Object[]) {
            String jsonString = JSON.toJSONString(object);
            return json2String(jsonString);
        }
        if (object instanceof List) {
            String jsonString = JSON.toJSONString(object);
            return json2String(jsonString);
        }
        if (object instanceof Map) {
            String jsonString = com.alibaba.fastjson.JSON.toJSONString(object);
            return json2String(jsonString);
        }

        //基本数据类型
        if (object instanceof Integer)
            return object.toString();
        if (object instanceof Boolean)
            return object.toString();
        if (object instanceof Long)
            return object.toString();
        if (object instanceof Character)
            return object.toString();
        if (object instanceof Byte)
            return object.toString();
        if (object instanceof Short)
            return object.toString();
        if (object instanceof Double)
            return object.toString();
        if (object instanceof String) {
            String s = bean2String(object);
            if (s != null) {
                return s;

            }
            s = xml(object.toString());
            if (s != null)
                return s;
            return object.toString();
        }

        //其他bean类型
        String s = bean2String(object);
        if (s != null)
            return s;
        else
            return object.toString();
    }

    /**
     * 任何bean对象 或 JsonString，转化为规范String
     *
     * @param o debug
     */
    private static String bean2String(Object o) {
        String s = json2String(o.toString());
        if (s == null)
            s = json2String(JSON.toJSONString(o));
        return s;
    }

    /**
     *  JsonString转化为规范String
     * @param json String
     * @return String 规范
     */
    private static String json2String(String json) {
        if (isEmpty(json)) {
            return null;
        }
        int indentSpaces = 2;
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                //设置缩进
                return jsonObject.toString(indentSpaces);
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                return jsonArray.toString(indentSpaces);
            }
            return null;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 打印xml数据
     *
     * @param xml xml数据
     */
    private static String xml(@Nullable String xml) {
        if (isEmpty(xml)) {
            return null;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
        } catch (TransformerException e) {
            return null;
        }
    }
}
