package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class TheGame extends AppCompatActivity {
   TextView button1,button2,button3,button4,button5,button6,button7,button8,button9,Player1score,Player2score,Player1Name,Player2Name;
   boolean PlayerTurn=true;
   int[] arr =new int[9];
   int[][] board = new int[3][3];
    ImageView settings;
   Map<Pair<Integer, Integer>,TextView>ConvertRowCol=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_game);
        Intent intent = getIntent();
        settings=findViewById(R.id.settings_icon);
        button1=findViewById(R.id.neon_button1);
        button2=findViewById(R.id.neon_button2);
        button3=findViewById(R.id.neon_button3);
        button4=findViewById(R.id.neon_button4);
        button5=findViewById(R.id.neon_button5);
        button6=findViewById(R.id.neon_button6);
        button7=findViewById(R.id.neon_button7);
        button8=findViewById(R.id.neon_button8);
        button9=findViewById(R.id.neon_button9);
        Player1score=findViewById(R.id.Player1score);
        Player2score=findViewById(R.id.Player2score);
        Player1Name=findViewById(R.id.neon_x);
        Player2Name=findViewById(R.id.neon_o);
        String mode=intent.getStringExtra("GameMode");
        Player1Name.setText(intent.getStringExtra("Player1"));
        Log.d("detecterror",mode);
        if(Objects.equals(mode, "Human")) {
            Player2Name.setText(intent.getStringExtra("Player2"));
        }
        else{
            Player2Name.setText("Ai");
        }
        Convert();
        settings.setOnClickListener(view -> {
            SettingsBox.showSettingsDialog(this);
            onclicksound.clicksound(this);
        });
        //why did i make it difficult fixed over 400 line of code
        button1.setOnClickListener( view -> {
                if(arr[0]==0) {
                    onclicksound.clicksound(view.getContext());
                    arr[0]=1;
                    if(Objects.equals(mode, "Ai"))
                    MarkCellai(button1,0,0);
                    else{
                        MarkCell(button1,0,0);
                    }
                }
        });
        button2.setOnClickListener(view -> {
            if(arr[1]==0) {
                onclicksound.clicksound(view.getContext());
                arr[1]=1;
                if(Objects.equals(mode, "Ai"))
                MarkCellai(button2,0,1);
                else{
                    MarkCell(button2,0,1);
                }

            }
        });
        button3.setOnClickListener( view -> {
            if(arr[2]==0) {
                onclicksound.clicksound(view.getContext());
                arr[2]=1;
                if(Objects.equals(mode, "Ai"))
               MarkCellai(button3,0,2);
                else{
                    MarkCell(button3,0,2);
                }
            }
        });
        button4.setOnClickListener( view -> {
            if(arr[3]==0) {
                onclicksound.clicksound(view.getContext());
                arr[3]=1;
                if(Objects.equals(mode, "Ai"))
                MarkCellai(button4,1,0);
                else{
                    MarkCell(button4,1,0);
                }
            }
        });
        button5.setOnClickListener( view -> {
            if(arr[4]==0) {
                onclicksound.clicksound(view.getContext());
                arr[4]=1;
                if(Objects.equals(mode, "Ai"))
             MarkCellai(button5,1,1);
                else{
                    MarkCell(button5,1,1);
                }
            }
        });
        button6.setOnClickListener( view -> {
            if(arr[5]==0) {
                onclicksound.clicksound(view.getContext());
                arr[5]=1;
                if(Objects.equals(mode, "Ai"))
                MarkCellai(button6,1,2);
                else{
                    MarkCell(button6,1,2);
                }
            }
        });
        button7.setOnClickListener( view -> {
            if(arr[6]==0) {
                onclicksound.clicksound(view.getContext());
                arr[6]=1;
                if(Objects.equals(mode, "Ai"))
                MarkCellai(button7,2,0);
                else{
                    MarkCell(button7,2,0);
                }
            }
        });
        button8.setOnClickListener( view -> {
            if(arr[7]==0) {
                onclicksound.clicksound(view.getContext());
                arr[7]=1;
                if(Objects.equals(mode, "Ai"))
             MarkCellai(button8,2,1);
                else{
                    MarkCell(button8,2,1);
                }
            }
        });
        button9.setOnClickListener( view -> {
            if (arr[8] == 0) {
                onclicksound.clicksound(view.getContext());
                arr[8] = 1;
                if(Objects.equals(mode, "Ai"))
               MarkCellai(button9,2,2);
                else{
                    MarkCell(button9,2,2);
                }
            }
        });
    }


    // assign every cell with Corresponding player and checking wining drawing condition
    public void MarkCell(TextView button,int row,int col){
        if (PlayerTurn) {
            SetPlayerView(button,row,col,1);
            if (checkwin(1)) {
                int score = Integer.parseInt(Player1score.getText().toString()) + 1;
                Player1score.setText(score + "");
            }
            else{
                if(IsGridEmpty())makedraw();
            }
        } else {
            SetPlayerView(button,row,col,-1);
            if (checkwin(-1)) {
                int score = Integer.parseInt(Player2score.getText().toString()) + 1;
                Player2score.setText(score + "");
            }
            else{
                if(IsGridEmpty())makedraw();
            }

        }
        ObjectAnimator fadeInAnimator = ObjectAnimator.ofFloat(button, "alpha", 0f, 1f);
        fadeInAnimator.setDuration(100); // 1 second animation
        fadeInAnimator.start();

    }


    // color the cell with the appropriate color
    public void SetPlayerView(TextView button,int row,int col,int player){
        if(player==1) {
            board[row][col] = 1;
            button.setText("X");
            button.setTextSize(80);
            button.setTextColor(Color.parseColor("#FF0000"));
            button.setShadowLayer(8, 0, 0, Color.RED);
        }
        else{
            board[row][col] = -1;
            button.setText("O");
            button.setTextSize(80);
            button.setTextColor(Color.parseColor("#FFFFFF"));
            button.setShadowLayer(8, 0, 0, Color.WHITE);
        }
        PlayerTurn=!PlayerTurn;
    }



    // Ai mode Overrided functions
    public void MarkCellai(TextView button,int row,int col){

        SetPlayerViewAi(button,row,col);
        if(checkwin(1)){
            int score = Integer.parseInt(Player1score.getText().toString()) + 1;
            Player1score.setText(score + "");
        }
        else if(IsGridEmpty()){
            makedraw();
        }
        else  {
            SetInv(0);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    computerMove();
                    if (checkwin(-1)) {
                        int score = Integer.parseInt(Player2score.getText().toString()) + 1;
                        Player2score.setText(score + "");
                    }
                    SetInv(1);
                }
            }, 1000);

        }

        ObjectAnimator fadeInAnimator = ObjectAnimator.ofFloat(button, "alpha", 0f, 1f);
        fadeInAnimator.setDuration(100); // 1 second animation
        fadeInAnimator.start();

    }

    public void computerMove() {
        Random rand = new Random();
        int row, col;
        do {
            row = rand.nextInt(3);
            col = rand.nextInt(3);
        } while (board[row][col]!=0); // Keep trying until a valid move is found
        board[row][col] = -1;
        arr[row*3+col]=1;
        TextView button=ConvertRowCol.get(new Pair<>(row+1,col+1));
        button.setText("O");
        button.setTextSize(80);
        button.setTextColor(Color.parseColor("#FFFFFF"));
        button.setShadowLayer(8, 0, 0, Color.WHITE);
        ObjectAnimator fadeInAnimator = ObjectAnimator.ofFloat(button, "alpha", 0f, 1f);
        fadeInAnimator.setDuration(100); // 1 second animation
        fadeInAnimator.start();

    }
    public void SetPlayerViewAi(TextView button,int row,int col){

            board[row][col] = 1;
            button.setText("X");
            button.setTextSize(80);
            button.setTextColor(Color.parseColor("#FF0000"));
            button.setShadowLayer(8, 0, 0, Color.RED);

    }

    // end Of Ai dedicated functions


    public void draw(TextView button){
        button.setText("Draw");
        button.setTextSize(30);
        button.setTextColor(Color.parseColor("#808080"));
        button.setShadowLayer(8, 0, 0, Color.parseColor("#808080"));
    }

    public void win(TextView button){
        button.setText("Winner");
        button.setTextSize(30);
    }

    public void reset(){
        for(int i=0;i<9;i++)arr[i]=0;
        for(int i=0;i<3;i++)for(int j=0;j<3;j++)board[i][j]=0;
        PlayerTurn=true;
        button1.setText("");
        button2.setText("");
        button3.setText("");
        button4.setText("");
        button5.setText("");
        button6.setText("");
        button7.setText("");
        button8.setText("");
        button9.setText("");
    }

    public void makedelay(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SetInv(1);
                reset();
            }
        }, 2000);
    }

    public boolean checkwin(int player){
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                SetInv(0);
                if(i==0){
                   win(button1);
                   win(button2);
                   win(button3);
                    makedelay();
                }
                else if(i==1){
                    win(button4);
                    win(button5);
                    win(button6);
                    makedelay();
                }
                else{
                    win(button7);
                    win(button8);
                    win(button9);
                    makedelay();
                }
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                SetInv(0);
                if(i==0){
                   win(button1);
                   win(button4);
                   win(button7);
                    makedelay();
                }
                else if(i==1){
                    win(button2);
                    win(button5);
                    win(button8);
                    makedelay();
                }
                else{
                    win(button3);
                    win(button6);
                    win(button9);
                    makedelay();
                }
                return true;
            }
        }
        // Check diagonals
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            SetInv(0);
            win(button1);
            win(button5);
            win(button9);
            makedelay();
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            SetInv(0);
            win(button3);
            win(button5);
            win(button7);
            makedelay();
            return true;
        }
        //check if the grid is full
        return false;  // No winning condition met
    }
    public boolean IsGridEmpty(){
        int cnt=0;
        for(int i=0;i<3;i++)for(int j=0;j<3;j++)if(board[i][j]==0)cnt++;
        if(cnt==0){
           return true;
        }
        return false;
    }
    public void makedraw(){

            draw(button1);
            draw(button2);
            draw(button3);
            draw(button4);
            draw(button5);
            draw(button6);
            draw(button7);
            draw(button8);
            draw(button9);
            makedelay();

    }
    public void Convert(){
        ConvertRowCol.put(new Pair<>(1,1),button1);
        ConvertRowCol.put(new Pair<>(1,2),button2);
        ConvertRowCol.put(new Pair<>(1,3),button3);
        ConvertRowCol.put(new Pair<>(2,1),button4);
        ConvertRowCol.put(new Pair<>(2,2),button5);
        ConvertRowCol.put(new Pair<>(2,3),button6);
        ConvertRowCol.put(new Pair<>(3,1),button7);
        ConvertRowCol.put(new Pair<>(3,2),button8);
        ConvertRowCol.put(new Pair<>(3,3),button9);
    }
    public void SetInv(int a){
        if(a==1) {
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);
            button4.setVisibility(View.VISIBLE);
            button5.setVisibility(View.VISIBLE);
            button6.setVisibility(View.VISIBLE);
            button7.setVisibility(View.VISIBLE);
            button8.setVisibility(View.VISIBLE);
            button9.setVisibility(View.VISIBLE);
        }
        else {
            if(button1.getText().toString().isEmpty())
             button1.setVisibility(View.INVISIBLE);
            if(button2.getText().toString().isEmpty())
             button2.setVisibility(View.INVISIBLE);
            if(button3.getText().toString().isEmpty())
             button3.setVisibility(View.INVISIBLE);
            if(button4.getText().toString().isEmpty())
             button4.setVisibility(View.INVISIBLE);
            if(button5.getText().toString().isEmpty())
             button5.setVisibility(View.INVISIBLE);
            if(button6.getText().toString().isEmpty())
             button6.setVisibility(View.INVISIBLE);
            if(button7.getText().toString().isEmpty())
             button7.setVisibility(View.INVISIBLE);
            if(button8.getText().toString().isEmpty())
             button8.setVisibility(View.INVISIBLE);
            if(button9.getText().toString().isEmpty())
             button9.setVisibility(View.INVISIBLE);
        }

    }

}