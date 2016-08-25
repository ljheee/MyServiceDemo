package com.example.administrator.myservicedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

/**
 * 依赖于 服务
 * 活动从服务 pull 数据
 * 活动 拿到服务的引用，调用方法获取数据
 * 服务的引用不能直接new,而是通过服务类中onBind()方法，间接返回
 */
public class NextActivity extends AppCompatActivity {

    TimeService service;
    String data[];
    ListView listView;
    ArrayAdapter<String> adapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        listView = (ListView) findViewById(R.id.listView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //绑定服务
        //参数一：意图--指定具体要绑定那个服务
        //参数二：ServiceConnection是服务的监视器
        //参数三：标识符--若不存在则创建
        bindService(
                new Intent(this,TimeService.class),
                conn,
                BIND_AUTO_CREATE);  //onBind
    }

    @Override
    protected void onPause() {
        super.onPause();

        unbindService(conn);
    }

    ServiceConnection conn = new ServiceConnection() {
        /**
         * 绑定服务后立刻执行
         *
         * @param name
         * @param iBinder
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            TimeService.ServiceBinder binder = (TimeService.ServiceBinder) iBinder;
            service = binder.getTimeService();//拿到服务引用

            //可访问服务
            data = service.getData();
            adapter = new ArrayAdapter<String>(NextActivity.this,android.R.layout.simple_list_item_1,data);
            listView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }

        /**
         * 系统因意外中断了服务--才执行
         * @param name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
}

