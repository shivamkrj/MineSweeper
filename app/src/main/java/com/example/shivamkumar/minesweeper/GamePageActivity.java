package com.example.shivamkumar.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Random;
import java.util.zip.Inflater;

public class GamePageActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {

    ArrayList<LinearLayout> rows;
    public static int x=10;
    public static int y=8;
    public static int count=0;
    public static int requiredCount;
    TextView timer;
    int a[][];
    int b[][];
    int cc[][];
    public static int countFlag=0;
    boolean fff=false;
    String player_name="";
    int m[];
    int n[];
    int xx=0;
    boolean firstMove =true;
    MineButton board[][];
    Random random= new Random();
    int mineNum=16;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);
        Intent intent=getIntent();
        xx= intent.getIntExtra("level_set",0);
        player_name=intent.getStringExtra("name");
        if(xx>0){
            if(xx==3){
                x=12;y=9;mineNum=21;setBoard();
            }else if(xx==2){
                x=10;y=8;mineNum=15;setBoard();
            }else if(xx==1){
                x=8;y=6;mineNum=10;setBoard();
            }
        }

        sharedPreferences =getSharedPreferences("my_shared_pref",MODE_PRIVATE);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);

        setBoard();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        timerPause();
        startTime = 0L;
        timeInMilliseconds = 0L;
        timeSwapBuff = 0L;
        updatedTime = 0L;
        timer.setText("00:00:00");

        if(id==R.id.reset){
            setBoard();
        }else if(id==R.id.hard){
            x=12;y=9;mineNum=21;setBoard();
        }else if(id==R.id.medium){
            x=10;y=8;mineNum=15;setBoard();
        }else if(id==R.id.easy){
            x=8;y=6;mineNum=10;setBoard();
        }
        return super.onOptionsItemSelected(item);
    }


    public void setBoard() {
        count=0;
        countFlag=0;
        fff=false;
        firstMove=true;
        timer=findViewById(R.id.timerTextView);
        if(xx==0)
            xx=1;
        mineNum=mineNum+random.nextInt(xx);
        requiredCount=x*y-mineNum;
        cc=new int[x][y];
        b=new int[x][y];
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                b[i][j]=0;
                cc[i][j]=0;
            }
        }
        board= new MineButton[x][y];
        a = new int[x][y];
        for(int i=0;i<x;i++)
        {
            for(int j=0;j<y;j++)
                a[i][j]=0;
        }
        m=new int[x];
        n=new int [y];
        for(int i=0;i<x;i++)
            m[i]=0;
        for(int j=0;j<y;j++)
            n[j]=0;
        rows= new ArrayList<>();
        LinearLayout rootLayout = new LinearLayout(this);

        rootLayout=findViewById(R.id.rootLayout);
        rootLayout.removeAllViews();
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        for(int i=0;i<x;i++){
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams1 =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,1);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setLayoutParams(layoutParams1);
            rows.add(linearLayout);
            rootLayout.addView(linearLayout);
        }
        LinearLayout.LayoutParams layoutParams1= new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
        layoutParams1.setMargins(3,3,3,3);
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                MineButton b= new MineButton(this);
                b.setOnClickListener(GamePageActivity.this);
                b.setOnLongClickListener(GamePageActivity.this);
                b.setLayoutParams(layoutParams1);
                b.setCoordinate(i,j);
                rows.get(i).addView(b);
                board[i][j]=b;
            }
        }

    }

    @Override
    public void onClick(View view) {

        if(fff){
            Toast.makeText(this,"Game Over",Toast.LENGTH_LONG).show();
            timerPause();
            return;
        }

        MineButton button= (MineButton) view;
        button.isClicked=true;

        if(button.mineFlag)
        {
            Toast.makeText(this,"flagged",Toast.LENGTH_SHORT).show();
            return;
        }


        if(firstMove){

            int r=button.row;
            int c=button.column;
            setMines(r,c);
          //  Toast.makeText(this,"first move",Toast.LENGTH_SHORT).show();
            firstMove = false;
            showCurrent(r,c);
            timerStart();
            return;
        }
        if(button.bValue==-2)
        {

            button.printMine();
            fff=true;

            Toast.makeText(this,"GameOver",Toast.LENGTH_SHORT).show();
            timerPause();
            revealAll();
            return;
        }
        int r=button.row;
        int c=button.column;
        showCurrent(r,c);
    }

    private void timerStart() {

        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);

    }
    private void timerPause(){

        timeSwapBuff += timeInMilliseconds;
        customHandler.removeCallbacks(updateTimerThread);
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timer.setText("" + mins + ":"
                            + String.format("%02d", secs) + ":"
                            + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };


    private void gameover() {
           Toast.makeText(this,"YOU Win",Toast.LENGTH_LONG).show();
           timerPause();
           saveGameScore();
           revealAll();
    }

    private void saveGameScore() {
        String xxx= timer.getText().toString();
      //  String highScore=xxx;
        Toast.makeText(this,xxx,Toast.LENGTH_SHORT).show();

        String p1 = sharedPreferences.getString("HIGHSCORE1",null);
        String p2 = sharedPreferences.getString("HIGHSCORE2",null);
        String p3 = sharedPreferences.getString("HIGHSCORE3",null);

        String n1 = sharedPreferences.getString("PLAYER1",null);
        String n2 = sharedPreferences.getString("PLAYER2",null);
        String n3 = sharedPreferences.getString("PLAYER3",null);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(p1!=null&&p2!=null&&p3!=null){
            if(highScoreComparer(xxx,p1)){
                p3=p2;n3=n2;
                p2=p1;n2=n1;
                p1=xxx;n1=player_name;
            }else if(highScoreComparer(xxx,p2)){
                p3=p2;n3=n2;
                p2=xxx;n2=player_name;
            }else if(highScoreComparer(xxx,p3)){
                p3=xxx;n3=player_name;
            }

            editor.putString("HIGHSCORE1",p1);
            editor.putString("HIGHSCORE2",p2);
            editor.putString("HIGHSCORE3",p3);

            editor.putString("PLAYER1",n1);
            editor.putString("PLAYER2",n2);
            editor.putString("PLAYER3",n3);

        }else if(p1!=null&&p2!=null){
            if(highScoreComparer(xxx,p1)){
                p3=p2;p2=p1;p1=xxx;
                n3=n2;n2=n1;n1=player_name;
            }else if(highScoreComparer(xxx,p2)){
                p3=p2;p2=xxx;
                n3=n2;n2=player_name;
            }else{
                p3=xxx;n3=player_name;
            }
            editor.putString("HIGHSCORE1",p1);
            editor.putString("HIGHSCORE2",p2);
            editor.putString("HIGHSCORE3",p3);

            editor.putString("PLAYER1",n1);
            editor.putString("PLAYER2",n2);
            editor.putString("PLAYER3",n3);

        }else if(p1!=null){
            if(highScoreComparer(xxx,p1)){
                p2=p1;p1=xxx;
                n2=n1;n1=player_name;
            }else{
                n2=player_name;
                p2=xxx;
            }
            editor.putString("HIGHSCORE1",p1);
            editor.putString("HIGHSCORE2",p2);

            editor.putString("PLAYER1",n1);
            editor.putString("PLAYER2",n2);
        }else{
            p1=xxx;
            n1=player_name;
            editor.putString("HIGHSCORE1",p1);
            editor.putString("PLAYER1",n1);
        }
        editor.apply();

        Intent intent= new Intent(this,HighScore.class);
        startActivity(intent);
    }

    private boolean highScoreComparer(String s, String p) {


        String y[]=s.split(":");
        String z[]=p.split(":");
        int s1=0;
        for(int i=0;i<y.length;i++){
            s1*=1000;
            s1+=Integer.parseInt(y[i]);
        }
        int s2=0;
        for(int i=0;i<y.length;i++){
            s2*=1000;
            s2+=Integer.parseInt(z[i]);
        }
        if(s1>s2)
            return false;
        else
            return true;
    }

    public void showCurrent(int r, int c) {

        board[r][c].printButton();
        if(board[r][c].bValue==0)
        {
            if(b[r][c]==1)
                return;
            b[r][c]=1;
            showAdjacent(r,c);
        }
        if(cc[r][c]==0){
            count++;
            cc[r][c]=1;
        }
        if(count==requiredCount&&countFlag==mineNum)
        {
            fff=true;
            gameover();
        }
    }

    public void showAdjacent(int r, int c){
        if(r>0){
            showCurrent(r-1,c);
            if(c>0)
                showCurrent(r-1,c-1);
            if(c<y-1)
                showCurrent(r-1,c+1);
        }
        if(r<x-1){
            showCurrent(r+1,c);
            if(c>0)
                showCurrent(r+1,c-1);
            if(c<y-1)
                showCurrent(r+1,c+1);
        }
        if(c>0)
            showCurrent(r,c-1);
        if(c<y-1)
            showCurrent(r,c+1);


    }

    private void revealAll(){
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                board[i][j].printButton();
            }
        }
    }

    private void setMines(int r, int c) {
        int z=mineNum;
        while (z>0){
            int rowNO=random.nextInt(x);

            int colNO=random.nextInt(y);
            if(rowNO==r&&colNO==c)
                continue;
            if((rowNO==r-1&&colNO==c-1)||(rowNO==r-1&&colNO==c)||(rowNO==r-1&&colNO==c+1)||(rowNO==r&&colNO==c-1)||(rowNO==r&&colNO==c+1)||(rowNO==r+1&&colNO==c-1)||(rowNO==r+1&&colNO==c)||(rowNO==r+1&&colNO==c+1))
                continue;
            if(a[rowNO][colNO]==1)
                continue;
            a[rowNO][colNO]=1;
            board[rowNO][colNO].bValue=-2;
            z--;
        }
        //setting numbers
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                if(a[i][j]==1)
                    continue;
                int count=0;
                if(i>0){
                    if(j>0){
                        if(a[i-1][j-1]==1)
                            count++;
                    }
                    if(a[i-1][j]==1)
                        count++;
                    if(j<y-1){
                        if(a[i-1][j+1]==1)
                            count++;
                    }
                }
                if(j>0){
                    if(a[i][j-1]==1)
                        count++;
                    if(i<x-1){
                        if(a[i+1][j-1]==1)
                            count++;
                    }
                }
                if(j<y-1){
                    if(a[i][j+1]==1)
                        count++;
                    if(i<x-1){
                        if(a[i+1][j+1]==1)
                            count++;
                    }
                }
                if(i<x-1)
                {
                    if(a[i+1][j]==1)
                        count++;
                }
           //     Toast.makeText(this,count+"",Toast.LENGTH_SHORT).show();
                board[i][j].bValue=count;
            }
        }
    }
    @Override
    public boolean onLongClick(View view) {

        MineButton mineButton= (MineButton)view;
        if(mineButton.isClicked||fff)
            return true;
        mineButton.setFlag();
        if(count==requiredCount&&countFlag==mineNum)
        {
            fff=true;
            gameover();
        }

        return true;
    }
}
