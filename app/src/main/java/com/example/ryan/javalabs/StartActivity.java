package com.example.ryan.javalabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {

    protected static final String ACTIVITY_NAME = "StartActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME,"in OnCreate()");

        Button b = findViewById(R.id.pushBtn);
        b.setOnClickListener(e ->{
            Intent in = new Intent(StartActivity.this, ListItemsActivity.class);
            startActivityForResult(in,50);
        });
        Button chatBtn = findViewById(R.id.sChatBtn);
        chatBtn.setOnClickListener(t->{
           Intent  in = new Intent(this,ChatWindow.class);
           startActivity(in);
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME,"In on Create()");
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME,"In On Start()");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME,"In OnPause()");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME,"in onStop()");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"in OnDestroy()");
    }
    @Override
    public void onActivityResult(int reqCode,int resCode, Intent data){
        //super.onActivityResult(reqCode,resCode,data);
        if(resCode == Activity.RESULT_OK) {
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
            Toast t = Toast.makeText(this,"ListActivity Response my information to share "+ data.getStringExtra("Response"),Toast.LENGTH_LONG);
            t.show();
        }else{
            Log.i(ACTIVITY_NAME, "Bad Return call");
        }
    }
}
