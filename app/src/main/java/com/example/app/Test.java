package com.example.app;//package com.wangfeixixi.sample;

import com.wangfeixixi.logxixi.LogJava;

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




    LogJava.bean(aa);
    LogJava.bean(testBean);
    LogJava.bean(test2);

    LogJava.d(new int[]{1, 2, 3});
    LogJava.d(1);
    LogJava.e(13123);
    LogJava.xml("<xml>Test</xml>");




  }
}
