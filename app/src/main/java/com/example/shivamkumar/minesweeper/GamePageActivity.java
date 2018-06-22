package com.example.shivamkumar.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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

    int a[][];
    int b[][];
    int cc[][];
    boolean fff=false;
    int m[];
    int n[];
    int xx=0;
    boolean firstMove =true;
    MineButton board[][];
    Random random= new Random();
    int mineNum=16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);
        Intent intent=getIntent();
        xx= intent.getIntExtra("level_set",0);
        if(xx>0){
            if(xx==3){
                x=12;y=9;mineNum=18;setBoard();
            }else if(xx==2){
                x=10;y=8;mineNum=14;setBoard();
            }else if(xx==1){
                x=8;y=6;mineNum=8;setBoard();
            }
        }
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
        if(id==R.id.reset){
            setBoard();
        }else if(id==R.id.hard){
            x=12;y=9;mineNum=18;setBoard();
        }else if(id==R.id.medium){
            x=10;y=8;mineNum=14;setBoard();
        }else if(id==R.id.easy){
            x=8;y=6;mineNum=8;setBoard();
        }
        return super.onOptionsItemSelected(item);
    }


    public void setBoard() {
        count=0;
        fff=false;
        firstMove=true;
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
            return;
        }

        MineButton button= (MineButton) view;

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
            return;
        }
        if(button.bValue==-2)
        {

            button.printMine();
            fff=true;

            Toast.makeText(this,"GameOver",Toast.LENGTH_SHORT).show();
            revealAll();
            return;
        }
        int r=button.row;
        int c=button.column;
        showCurrent(r,c);
    }

    private void gameover() {
           Toast.makeText(this,"YOU Win",Toast.LENGTH_LONG).show();
       //   revealAll();
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
        if(count==requiredCount)
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
        return true;
    }
}
