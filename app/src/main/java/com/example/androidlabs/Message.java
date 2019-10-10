package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;



public class Message extends AppCompatActivity {

    String message;
    boolean type;

    Message(String mess, boolean type){
        this.message= mess;
        this.type=type;
    }

    public String getMessage() {
        return message;
    }

    public boolean getType() {
        return type;
    }



}
