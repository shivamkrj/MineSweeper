package com.example.shivamkumar.minesweeper;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HighScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        SharedPreferences sharedPreferences=getSharedPreferences("my_shared_pref",MODE_PRIVATE);
        TextView textView1= findViewById(R.id.textView2);
        TextView textView2= findViewById(R.id.textView3);
        TextView textView3= findViewById(R.id.textView4);

        String s1=sharedPreferences.getString("HIGHSCORE1",null);
        String s2=sharedPreferences.getString("HIGHSCORE2",null);
        String s3=sharedPreferences.getString("HIGHSCORE3",null);

        textView1.setText(s1);
        textView2.setText(s2);
        textView3.setText(s3);
    }
}
