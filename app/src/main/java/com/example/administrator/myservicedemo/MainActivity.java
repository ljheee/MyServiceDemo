package com.example.administrator.myservicedemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * 主活动
 * 注册广播接收器，让数据提供者push数据
 */
public class MainActivity extends AppCompatActivity {

    DataReceiver receiver ;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

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

        //注册广播接收器
        receiver = new DataReceiver();

        //意图过滤器：拦截各种意图
        IntentFilter filter = new IntentFilter();
        filter.addAction(TimeService.ACTION_TIME);//指定要接收的广播
        filter.addAction(Intent.ACTION_BATTERY_LOW);

        //注册全局广播
//        registerReceiver(receiver, filter);

        //本地广播
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(receiver,filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注销广播接收器
//        unregisterReceiver(receiver);

        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(receiver);
    }


    /**
     * 定义广播接收器
     * 一个广播接收器，可以接收多种广播
     */
    class DataReceiver extends BroadcastReceiver{

        /**
         * 收到广播
         * @param context
         * @param intent    发广播时，设定的意图
         */
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            switch (action){
                case TimeService.ACTION_TIME:  //本地广播--时间
                    //获得广播中的数据
                    String time = intent.getStringExtra(TimeService.EXTRA_TIME);
                    textView.setText(time);//会自动刷新界面

                    break;
                case Intent.ACTION_BATTERY_LOW:
                    break;



            }

        }
    }


    /**
     * 创建菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_next:
                startActivity(new Intent(this,NextActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
