package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText edit1;


    SharedPreferences prefs;

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_file);

        //EditText edit2 = findViewById(R.id.edit2);

        edit1 = findViewById(R.id.edit1);

        prefs = getSharedPreferences("FileName", MODE_PRIVATE);
        String previous = prefs.getString("", "");
        edit1.setText(previous);


        Button login = findViewById(R.id.button);

        if(login !=null){
            login.setOnClickListener(view -> {
                Intent goTo = new Intent(MainActivity.this, ProfileActivity.class);
                goTo.putExtra("Email", edit1.getText().toString());
                startActivity(goTo);
            });
        }


    }

    @Override
    protected void onPause() {
        super.onPause();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("", edit1.getText().toString());
            editor.commit();
    }


}
