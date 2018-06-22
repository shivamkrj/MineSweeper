package com.example.shivamkumar.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    int x=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
    public void easysetLevel(View view){
        CheckBox easycheckBox= (CheckBox)findViewById(R.id.easy);
        CheckBox mediumcheckBox= (CheckBox)findViewById(R.id.medium);
        CheckBox hardcheckBox= (CheckBox)findViewById(R.id.hard);
        if(easycheckBox.isChecked()){
            mediumcheckBox.setChecked(false);
            hardcheckBox.setChecked(false);
            x=1;
        }
    }
    public void mediumsetLevel(View view){
        CheckBox easycheckBox= (CheckBox)findViewById(R.id.easy);
        CheckBox mediumcheckBox= (CheckBox)findViewById(R.id.medium);
        CheckBox hardcheckBox= (CheckBox)findViewById(R.id.hard);
        if(mediumcheckBox.isChecked()){
            easycheckBox.setChecked(false);
            hardcheckBox.setChecked(false);
        x=2;}
    }
    public void hardsetLevel(View view){
        CheckBox easycheckBox= (CheckBox)findViewById(R.id.easy);
        CheckBox mediumcheckBox= (CheckBox)findViewById(R.id.medium);
        CheckBox hardcheckBox= (CheckBox)findViewById(R.id.hard);
        if(hardcheckBox.isChecked()){
            easycheckBox.setChecked(false);
            mediumcheckBox.setChecked(false);
            x=3;
        }
    }

    public void game(View view){
        Intent intent = new Intent(this,GamePageActivity.class);
        intent.putExtra("level_set",x);
        startActivity(intent);
    }
}
