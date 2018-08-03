package in.shivamkumar.minesweeper;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;


public class MineButton extends AppCompatButton {

    public int bValue=-3;
    public int row,column;
    public boolean isMine=false;
    public boolean isClicked= false;
    public boolean mineFlag=false;
    public void setFlag(){
        if(mineFlag){
            mineFlag=false;
            GamePageActivity.countFlag--;
            setBackgroundColor(getResources().getColor(R.color.lightYellow,null));
        }else {
            mineFlag=true;
            GamePageActivity.countFlag++;
            setBackgroundDrawable(getResources().getDrawable(R.drawable.flag_icon,null));
        }
    }

    public MineButton(Context context) {
        super(context);
        setBackgroundColor(getResources().getColor(R.color.lightYellow,null));

    }
    public void setbValue(int value){
        if(value>0){
            setText(value + "");
            setBackgroundColor(getResources().getColor(R.color.green,null));

        }else if(value==0)
        {
            setBackgroundColor(getResources().getColor(R.color.normal,null));
        }
        else if(value==-1){
            setBackgroundDrawable(getResources().getDrawable(R.drawable.flag_icon,null));
        }
        else if(value==-2){
            setBackground(getResources().getDrawable(R.drawable.mine,null));
        }
        setEnabled(false);
    }
    public void setMine(){
        bValue=-2;
        isMine=true;
    }
    public void setCoordinate(int r,int c){
        row=r;
        column=c;
    }
    public boolean pressed(){
        return bValue==-3;
    }
    public void printCurrent(){

    }

    public void printButton() {

        if(bValue>0){
            if(mineFlag)
                setBackgroundDrawable(getResources().getDrawable(R.drawable.flag_cut,null));
            else {


                setBackgroundColor(getResources().getColor(R.color.green, null));
            }
            setText(bValue + "");

        }else if(bValue==0)
        {
            if(mineFlag)
                setBackgroundDrawable(getResources().getDrawable(R.drawable.flag_cut,null));
            else
            setBackgroundColor(getResources().getColor(R.color.normal,null));
        }
        else if(bValue==-2){
            if(!mineFlag)
            setBackgroundDrawable(getResources().getDrawable(R.drawable.mine2,null));


        }else
            setText("W");
    }
    public void printMine(){
        setBackgroundDrawable(getResources().getDrawable(R.drawable.mine2,null));
    }
}
