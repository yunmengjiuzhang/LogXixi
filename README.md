# LogXixi
用于Android环境，java环境的log打印，可打印任何类型数据,根据android项目环境debug环境自动打开，release环境自动关闭android环境log打印，规范bean对象，json，xml对应log，crash捕捉，disk存储 log crash，回放disk数据


###配置： 

1.项目根build.gradle添加

            allprojects {
                repositories {
                    google()
                    jcenter()
                    maven { url 'https://jitpack.io' } //添加仓库依赖
                }
            }
2.module的build.gradle添加

          implementation 'com.github.wangfeixixi:LogXixi:vertion'
		  
vertion最新版本如下
[![](https://jitpack.io/v/wangfeixixi/LogXixi.svg)](https://jitpack.io/#wangfeixixi/LogXixi)

3.在Application中添加

          LogXixi.init(this);
	  
	  

###开始使用啦！

1.运行在android设备上的log，Android环境

    	LogAndroid.d(123);
        LogAndroid.d(new int[]{1,2,3});
        TestBean testBean = new TestBean();
        testBean.a ="1";
        testBean.b = 2;
        LogAndroid.bean(testBean);
        LogAndroid.json();
        LogAndroid.xml();

2.运行在java环境的log：

        LogJava.d();
        LogJava.e();
        LogJava.bean();
        LogJava.json();
        LogJava.xml();
		
3.抓捕disk记录crash
 
4.disk数据回放，打开界面

        getActivity().startActivity(new Intent(getActivity(), XixiFileActivity.class));
	  或者利用XixiFileUtils工具类里的方法自定义disk数据显示

如果觉得好请给我点赞哈！
如果需要进一步交流，邮件哦：xuanyuanxixi@foxmail.com
