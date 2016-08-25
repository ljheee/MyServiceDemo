package com.example.administrator.myservicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Date;

public class TimeService extends Service {


    /**
     * 和业务逻辑相关的数据
     */
    String time="";

    String data[] = {"a","b","c","d"};

    public String[] getData() {
        return data;
    }

    ServiceThread thread;
    public static final String ACTION_TIME = "com.example.administrator.myservicedemo.Action_TIME";
    public static final String TAG = "ServiceThread";
    public static final String EXTRA_TIME = "MyService" ;

    /**
     * 获得  服务 中的数据
     * @return
     */
    public String getTime() {
        return time;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //创建子线程
        thread = new ServiceThread();
        thread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //结束子线程
        thread.isRunning = false;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    class  ServiceBinder extends Binder{

        public TimeService getTimeService(){
            return TimeService.this;
        }
    }




    /**
     * 内部类
     * 执行服务耗时操作
     */
    class ServiceThread extends  Thread{


        volatile boolean isRunning = true;

        @Override
        public void run() {
            super.run();

            while(isRunning){
                time = new Date().toLocaleString();
                Log.wtf(TAG, time);

                //主动  Push数据
                //发广播
                Intent intent = new Intent();
                intent.setAction(ACTION_TIME);
//                intent.setData();
                intent.putExtra(EXTRA_TIME, time);

//                sendBroadcast(intent);//发普通广播:消耗大，系统中所有广播接收器，都可以收到



                //本地广播，只在一个程序内部
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);//

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
