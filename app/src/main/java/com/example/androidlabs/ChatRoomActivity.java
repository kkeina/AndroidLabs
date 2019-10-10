package com.example.androidlabs;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    ArrayList<Message> objects = new ArrayList<>();
    BaseAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room_activity);


        Button send = findViewById(R.id.sendButton);
        Button receive = findViewById(R.id.receiveButton);

        ListView theList = findViewById(R.id.list);
        theList.setAdapter(myAdapter = new MyListAdapter());
        theList.setOnItemClickListener((lv, vw, pos, id) -> {

            Toast.makeText(ChatRoomActivity.this,
                    "You clicked on:" + pos, Toast.LENGTH_SHORT).show();

        });

        send.setOnClickListener(click ->
        {
            EditText text = findViewById(R.id.edit);
            objects.add(new Message(text.getText().toString(), true));
            myAdapter.notifyDataSetChanged(); //update
            text.setText("");
        });

        receive.setOnClickListener(click ->
        {
            EditText text = findViewById(R.id.edit);
            objects.add(new Message(text.getText().toString(), false));
            myAdapter.notifyDataSetChanged(); //update
            text.setText("");
        });


    }

    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public Message getItem(int i) {
            return objects.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int p, View convertView, ViewGroup parent) {
            View thisRow = convertView;
                if (objects.get(p).type) {

                    thisRow = getLayoutInflater().inflate(R.layout.send_avatar, null);

                    TextView itemText = thisRow.findViewById(R.id.sendText);
                    itemText.setText(objects.get(p).message);
                }
                else {
                    thisRow = getLayoutInflater().inflate(R.layout.receive_avatar, null);

                    TextView itemText2 = thisRow.findViewById(R.id.receiveText);
                    itemText2.setText(objects.get(p).message);
                }

            return thisRow;
        }
    }


}
