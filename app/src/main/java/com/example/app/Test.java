package com.example.app;//package com.wangfeixixi.sample;

import com.wangfeixixi.log.ToStringUtil;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        TestBean testBean = new TestBean();
        testBean.a = "aaaaaaa";
        testBean.b = 11111111;

        TestBean test2 = new TestBean();
        test2.a = "bbbbbbb";
        test2.b = 2222222;
        testBean.testBean = test2;

        ArrayList<TestBean> aa = new ArrayList<>();
        aa.add(test2);
        aa.add(test2);
        aa.add(test2);


//    LogJava.bean(aa);
//    LogJava.bean(testBean);
//    LogJava.bean(test2);
//
//    LogJava.d(new int[]{1, 2, 3});
//    LogJava.d(1);
//    LogJava.e(13123);
//    LogJava.xml("<xml>Test</xml>");


//        TestBean[] bb = new TestBean[]{test2, test2, test2};


//        System.out.println(XixiUtils.toString(bb));
//        System.out.println(XixiUtils.toString(aa));

//        LogJava.d(bb);
//        LogJava.d(aa);
//        LogJava.d(1);

//        LogJava.bean(123);

        d("<xml>Test</xml>");

        d("{test:1}");
    }

    public static void d(Object o) {
        System.out.println(ToStringUtil.toString(o));
    }
}
