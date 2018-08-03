package in.shivamkumar.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    int x=0;

    EditText editText;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText =findViewById(R.id.editText);
        sharedPreferences=getSharedPreferences("my_shared_pref",MODE_PRIVATE);

        String ss= sharedPreferences.getString("previousplayer",null);
        if(ss!=null)
            editText.setText(ss);
        int level= sharedPreferences.getInt("DEFAULTEDITOR",-1);
        if(level>0){
            if(level==1)
                x=1;
            else if(level==2)
                x=2;
            else if(level==3)
                x=3;
        }else{
            CheckBox checkBox = findViewById(R.id.hard);
            checkBox.setChecked(true);
            x=3;
        }

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

        String name= editText.getText().toString();
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("previousplayer",name);
        editor.putInt("DEFAULTEDITOR",x);
        editor.commit();
        Intent intent = new Intent(this,GamePageActivity.class);
        intent.putExtra("level_set",x);
        intent.putExtra("name",name);
        startActivity(intent);

    }

    public void buttonHighScore(View view) {
        Intent intent = new Intent(this,HighScore.class);
        startActivity(intent);
    }
}
