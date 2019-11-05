package com.example.androidlabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class Message extends AppCompatActivity {

    String message;
    boolean isSent;
    long id;

    Message(String mess, boolean sent, long id) {
        this.message = mess;
        this.isSent = sent;
        this.id = id;

    }

    public String getMessage() {
        return message;
    }

    public boolean getSend() {
        return isSent;
    }

    public long getID() {
        return id;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
