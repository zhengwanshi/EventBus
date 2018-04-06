package com.example.myevenbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myevenbus.event.MessageEvent;
import com.example.myevenbus.event.StickyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SendEventActivity extends AppCompatActivity {

    private Button sendEvent;
    private Button recStickyEvent;
    private TextView showResult;
    private boolean isFirst = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_event);
        findAllViews();
        listener();
    }

    private void listener() {
        sendEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("我是EvntBus"));

            }
        });
        recStickyEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册
                if (isFirst) {
                    isFirst = false;
                    EventBus.getDefault().register(SendEventActivity.this);
                }

            }
        });
    }


    private void findAllViews() {
        sendEvent = (Button) findViewById(R.id.send_event);
        recStickyEvent = (Button) findViewById(R.id.recstickyevent);
        showResult = (TextView) findViewById(R.id.show_result);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //解注册
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void recEvent(StickyEvent event){
        showResult.setText(event.name);
    }
}
