package com.example.ryan.javalabs;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {
    ListView messageListView ;
    EditText chatMsg;
    Button sendBtn;
    ArrayList<String> messageList;
    SQLiteDatabase db;
    Cursor curse;
    private class ChatAdapter extends ArrayAdapter<String>{
        public ChatAdapter(Context con) {
            super(con, 0);
        }
        @Override

        public int getCount(){
          //  Log.println(Log.ERROR,"crash",""+messageList.size());
            return messageList.size();
        }
        @Override
        public String getItem(int pos){
            if(messageList.isEmpty()){
                return "EmptyList";
            }
            return messageList.get(pos);
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent){
            //Log.println(Log.ERROR,"Crash","getView Issue");
            LayoutInflater inf  = ChatWindow.this.getLayoutInflater();
            View res = null;

            if(pos%2==0) {
                res = inf.inflate(R.layout.chat_in, null);
            }
            else{
                res = inf.inflate(R.layout.chat_out,null);
            }

            TextView msg = (TextView)res.findViewById((R.id.message_text));
            msg.setText(getItem(pos));
            return res;
        }
        public long getItemID(int pos){
            curse.moveToPosition(pos);
            Log.i("Test","getItemID");
            return curse.getInt(curse.getColumnIndex(ChatDBHelper.KEY_ID));
        }
        long getID(int pos){
            return pos;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

        FrameLayout test = findViewById(R.id.testFrame);
        Log.i("NullCheck" ,"" +(test==null));
        setContentView(R.layout.activity_chat_window);

        messageListView = findViewById(R.id.chatView);
        chatMsg= findViewById(R.id.chatMsg);
        sendBtn = findViewById(R.id.sendButton);
        messageList= new ArrayList<>();

        ChatAdapter mA = new ChatAdapter(this);
        messageListView.setAdapter(mA);

      ChatDBHelper dbHelper = new ChatDBHelper(this);
         db = dbHelper.getWritableDatabase();
        curse = db.rawQuery("SELECT *" +" FROM " +ChatDBHelper.TABLE_NAME,null);
        //curse.moveToFirst();
        Log.i ("DATABASE" , ""+curse.getColumnCount());
        for(int i = 0; i < curse.getColumnCount();i++){
            Log.i("Columns",curse.getColumnName(i));
        }
        curse.moveToFirst();
       while(!curse.isAfterLast()) {
            Log.i("DATABASE", curse.getString(curse.getColumnIndex(dbHelper.KEY_MESSAGE)));

            messageList.add(curse.getString(curse.getColumnIndex(dbHelper.KEY_MESSAGE)));
            mA.notifyDataSetChanged();
            curse.moveToNext();
        }


      sendBtn.setOnClickListener(cl->{
            String input = chatMsg.getText().toString();
            ContentValues values = new ContentValues();
            values.put(dbHelper.KEY_MESSAGE,input);
            db.insert(dbHelper.TABLE_NAME,null,values);
            messageList.add(chatMsg.getText().toString());
            mA.notifyDataSetChanged();
            chatMsg.setText("");
        });
      messageListView.setOnItemClickListener((adapterView, view, pos, l) -> {
          Intent n = new Intent(this, MessageDetails.class);
          Bundle b = new Bundle();
          b.putLong("MessageId", mA.getItemID(pos));
          b.putString("Message",mA.getItem(pos));
          n.putExtras(b);
          startActivityForResult(n,86);
      });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==86){
            Log.i("TEST",""+data.getExtras().getLong("IDtoDelete"));

        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        curse.close();
        db.close();
    }

}
