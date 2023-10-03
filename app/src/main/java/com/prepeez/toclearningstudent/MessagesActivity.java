package com.prepeez.toclearningstudent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MessagesActivity extends AppCompatActivity {
    TextView Msg_title, Msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(mHandler, new IntentFilter("com.google.firebase.udacity.virtualclassroom_FCM-MESSAGE"));
        setContentView(R.layout.activity_messages);
        Msg_title = findViewById(R.id.msg_title);
        Msg = findViewById(R.id.msg);
        if (getIntent().getExtras() != null){
            for (String key : getIntent().getExtras().keySet()){
                if (key != null && key.equals("title")){
                    Msg_title.setText(getIntent().getExtras().getString(key));
                }
                else if (key != null && key.equals("message")){
                    Msg_title.setText(getIntent().getExtras().getString(key));
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHandler);
    }

    private BroadcastReceiver mHandler = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String title = intent.getStringExtra("title");
            String message = intent.getStringExtra("message");
            Msg_title.setText(title);
            Msg.setText(message);
        }
    };
}
