package com.example.shivamkumar.minesweeper;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
        TextView textView=findViewById(R.id.textView5);
        textView.setVisibility(View.GONE);

        String s1=sharedPreferences.getString("HIGHSCORE1",null);
        String s2=sharedPreferences.getString("HIGHSCORE2",null);
        String s3=sharedPreferences.getString("HIGHSCORE3",null);

        String n1=sharedPreferences.getString("PLAYER1",null);
        String n2=sharedPreferences.getString("PLAYER2",null);
        String n3=sharedPreferences.getString("PLAYER3",null);

        if(s1!=null){
            textView1.setVisibility(View.VISIBLE);
            textView1.setText(s1 + "  "+n1);

        }else{
            textView.setVisibility(View.VISIBLE);
        }
        if(s2!=null){
            textView2.setVisibility(View.VISIBLE);
            textView2.setText(s2+ "  "+n2);
        }
        if(s3!=null){
            textView3.setVisibility(View.VISIBLE);
            textView3.setText(s3+ "  "+n3);
        }

    }
}
