package com.example.ryan.javalabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MessageDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        TextView msgText = findViewById(R.id.msgDetails);
        TextView msgID = findViewById(R.id.msgID);
        Button delBtn = findViewById(R.id.deleteMsgBtn);
        Intent in = this.getIntent();
        String test = in.getExtras().getString("Message");
        msgText.setText(test);
        msgID.setText(""+in.getExtras().getLong("MessageID"));
        delBtn.setOnClickListener(t->{
            setResult(86);
            Intent n = this.getIntent();
            n.getExtras().putLong("IDtoDelete",in.getExtras().getLong("MessageID"));
            finish();

        });
    }


}
