package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    private ArrayList<Message> objects = new ArrayList<>();
    MyListAdapter myAdapter;
    //SQLiteDatabase db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room_activity);

         //you need to declare the database
        //SQLiteDatabase db = new SQLiteDatabase();

        //EditText text = findViewById(R.id.edit);
        Button send = findViewById(R.id.sendButton);
        Button receive = findViewById(R.id.receiveButton);
        EditText text = findViewById(R.id.edit);

        myAdapter = new MyListAdapter();
        ListView theList = findViewById(R.id.list);
        theList.setAdapter(myAdapter);

        theList.setOnItemClickListener((lv, vw, pos, id) -> {

            Toast.makeText(ChatRoomActivity.this,
                    "You clicked on:" + pos, Toast.LENGTH_SHORT).show();
        });


        MyDatabaseOpenHelper dbOpener = new MyDatabaseOpenHelper(this);
        objects.addAll(dbOpener.getAllMessages());
        myAdapter.notifyDataSetChanged();

       // EditText type = findViewById(R.id.edit);
        send.setOnClickListener(click ->
        {
            String message = text.getText().toString();
            if (!message.isEmpty()) {
                Message msg = new Message(message, true, -1);
                msg.id = dbOpener.insert(msg);

                objects.add(msg);
                text.setText("");
                myAdapter.notifyDataSetChanged(); //update
            }
        });

        receive.setOnClickListener(click ->
        {
            String message = text.getText().toString();
            if (!message.isEmpty()) {
                Message msg = new Message(message, false, -1);
                msg.id = dbOpener.insert(msg);

                objects.add(msg);
                text.setText("");
                myAdapter.notifyDataSetChanged(); //update
            }
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
            return objects.get(i).id;
        }

        @Override
        public View getView(int p, View convertView, ViewGroup parent) {
           // View thisRow = convertView;

            View newV;
            Message thisRow = objects.get(p);

            if (thisRow.getSend()) {
                newV = getLayoutInflater().inflate(R.layout.send_avatar, null);
                TextView itemText = newV.findViewById(R.id.sendText);
                itemText.setText(thisRow.getMessage());
            } else {
                newV = getLayoutInflater().inflate(R.layout.receive_avatar, null);
                TextView itemText2 = newV.findViewById(R.id.receiveText);
                itemText2.setText(thisRow.getMessage());
            }

            return newV;
        }
    }


}
