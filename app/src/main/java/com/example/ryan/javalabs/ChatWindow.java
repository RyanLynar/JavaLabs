package com.example.ryan.javalabs;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {
    ListView messageListView ;
    EditText chatMsg;
    Button sendBtn;
    ArrayList<String> messageList;

    private class ChatAdapter extends ArrayAdapter<String>{
        public ChatAdapter(Context con) {
            super(con, 0);
        }
        @Override
        public int getCount(){
            Log.println(Log.ERROR,"crash",""+messageList.size());
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
            Log.println(Log.ERROR,"Crash","getView Issue");
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
        long getID(int pos){
            return pos;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_chat_window);

      messageListView = findViewById(R.id.chatView);
      chatMsg= findViewById(R.id.chatMsg);
      sendBtn = findViewById(R.id.sendButton);
      messageList= new ArrayList<>();

      ChatAdapter mA = new ChatAdapter(this);
      messageListView.setAdapter(mA);

      sendBtn.setOnClickListener(t->{
          messageList.add(chatMsg.getText().toString());
          mA.notifyDataSetChanged();
          chatMsg.setText("");
        });
    }


}
