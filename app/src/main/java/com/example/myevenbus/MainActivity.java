package com.example.myevenbus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myevenbus.event.MessageEvent;
import com.example.myevenbus.event.StickyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends Activity {
    private Button eventSendAct;
    private Button sendStickyEvent;
    private TextView showresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findAllViews();
        listener();
        EventBus.getDefault().register(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    //接收信息
    public void recEventBus(MessageEvent event){
        showresult.setText(event.name);
    }
    private void listener() {
        eventSendAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SendEventActivity.class);
                startActivity(intent);
            }
        });
        sendStickyEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送
                EventBus.getDefault().postSticky(new StickyEvent("我是粘性事件"));
                Intent intent = new Intent(MainActivity.this,SendEventActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findAllViews() {
        eventSendAct = (Button) findViewById(R.id.send_even);
        sendStickyEvent = (Button) findViewById(R.id.send_stickyeven);
        showresult = (TextView) findViewById(R.id.show_result);
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
       //解注册
        EventBus.getDefault().unregister(this);
    }
}
