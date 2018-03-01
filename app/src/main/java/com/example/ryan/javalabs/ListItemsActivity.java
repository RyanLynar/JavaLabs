package com.example.ryan.javalabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends Activity {
    protected static final String ACTIVITY_NAME = "ListItemActivity";
    protected static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME,"in OnCreate()");
        ImageButton imBtn = findViewById(R.id.imageButton);
        imBtn.setOnClickListener(e ->{
          Intent pic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
          if(pic.resolveActivity(getPackageManager()) != null){
              startActivityForResult(pic, REQUEST_IMAGE_CAPTURE);

          }
        });
        Switch s = findViewById(R.id.switch1);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                CharSequence txt = "";
                int duration = 0;


                if(b) {
                    txt = "Switch is On"; //="Switch is off";
                    duration = Toast.LENGTH_SHORT; //=Toast.LENGTH_LONG;
                }else{
                    txt ="Switch is off";
                    duration=Toast.LENGTH_LONG;
                }

                Toast t = Toast.makeText(compoundButton.getContext(),txt,duration);
                t.show();

            }
        });
        CheckBox checkBox = findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this );
                builder.setMessage(R.string.dialog_msg).setTitle(R.string.dialog_title).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent resInt = new Intent();
                        resInt.putExtra("Response", "Here is my response");
                        setResult(Activity.RESULT_OK,resInt);
                        finish();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
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
    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data){
        if(reqCode ==REQUEST_IMAGE_CAPTURE && resCode == RESULT_OK){
            ImageButton b = findViewById(R.id.imageButton);
            b.setImageBitmap((Bitmap)  data.getExtras().get("data")); //GOOD CODE ANDROID
        }
    }
}
