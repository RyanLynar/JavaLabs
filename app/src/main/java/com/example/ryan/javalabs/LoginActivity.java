package com.example.ryan.javalabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity{

    protected static final String ACTIVITY_NAME = "LoginActivity";
    public static final String PREF_NAME ="preffile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "in OnCreate()");
        Context con = this.getApplicationContext();

        final SharedPreferences sPref = con.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        TextView t = findViewById(R.id.userNameField);
        t.setText(sPref.getString("DefaultEmail","email@domain.com"));

        Button logB = findViewById(R.id.loginBtn);
        logB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               // Log.i(v.toString(),"Click");
                TextView t = findViewById(R.id.userNameField);

                SharedPreferences.Editor editor = sPref.edit();
                editor.putString("DefaultEmail",t.getText().toString());
                editor.commit();
                Intent in = new Intent(LoginActivity.this,StartActivity.class);
                startActivity(in);
            }
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

}
