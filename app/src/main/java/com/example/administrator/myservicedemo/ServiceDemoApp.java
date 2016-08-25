package com.example.administrator.myservicedemo;

import android.app.Application;
import android.content.Intent;

/**
 * 应用程序的起点
 * 如果自己不创建此类，系统会提供一个默认的
 * 此类名，需要在清单文件中声明
 */
public class ServiceDemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //程序一启动，就打开服务
        startService(new Intent(this,TimeService.class));

        //在此处---初始化应用
    }



}
