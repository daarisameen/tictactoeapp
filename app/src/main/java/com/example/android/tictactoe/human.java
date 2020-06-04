package com.example.android.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class human extends AppCompatActivity implements View.OnClickListener{
TextView tv_declare;
Button button4;
    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int chancecount=0;
    private TextView chance;

    private int player1Points=0;
    private int player2Points=0;
    private int draw=0;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private TextView textViewdraw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_human);

 tv_declare = (TextView) findViewById(R.id.tv_declare);

        textViewPlayer1 = (TextView) findViewById(R.id.playerchance);
        chance = (TextView) findViewById(R.id.chancesetter);
        textViewPlayer2 = (TextView) findViewById(R.id.winview);
        textViewdraw = (TextView) findViewById(R.id.drawview);

        button4= (Button)findViewById(R.id.button4);
        //connect all the buttons with their ids(giving reference to all buttons)
        for (int i=0; i<3;i++)
        {
            for(int j=0; j<3;j++)
            {
                String buttonid = "b"+i+j;
                int resid = getResources().getIdentifier(buttonid, "id" ,getPackageName());
                buttons[i][j] = findViewById(resid);
                buttons[i][j].setOnClickListener(this);
            }
        }

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                leaderboard();
            }
        });

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                player1Points = 0;
                player2Points = 0;
                draw=0;
                updatePointsText();
                resetBoard();
            }
        });
        Button buttonback = findViewById(R.id.button);
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backtomain = new Intent(human.this,mainpage.class);
                startActivity(backtomain);
            }
        });
    }
    public void onClick(View v) {
        if (!(chancecount == 10))
        {
            switch (v.getId())
            {
                case R.id.b00:
                    decidechance(buttons[0][0]);
                    result();
                    break;

                case R.id.b01:
                    decidechance(buttons[0][1]);
                    result();
                    break;

                case R.id.b02:
                    decidechance(buttons[0][2]);
                    result();
                    break;

                case R.id.b10:
                    decidechance(buttons[1][0]);
                    result();
                    break;

                case R.id.b11:
                    decidechance(buttons[1][1]);
                    result();

                    break;

                case R.id.b12:
                    decidechance(buttons[1][2]);
                    result();

                    break;

                case R.id.b20:
                    decidechance(buttons[2][0]);
                    result();

                    break;

                case R.id.b21:
                    decidechance(buttons[2][1]);result();
                    break;

                case R.id.b22:
                    decidechance(buttons[2][2]);
                    result();

                    break;
            }
        }
    }

    void decidechance(Button obj) {
        if (player1Turn)
        {
tv_declare.setText("Let's see who wins");
            obj.setText("x");
            obj.setTextColor(getResources().getColor(R.color.xcolor));
            // obj.setTextSize(24);
            chance.setText("2 PLAYER");
            obj.setEnabled(false);
        } else
        {

            obj.setText("o");
            obj.setTextColor(getResources().getColor(R.color.ocolor));
            //obj.setTextSize(24);
            chance.setText("1 PLAYER");
            obj.setEnabled(false);

        }
        chancecount++;
    }

    void result() {
        if (checkForWin())
        {
            if (player1Turn)
            {
                player1Wins();
            } else
            {
                player2Wins();
            }
        }
        else if (chancecount == 9)
        {
            draw();
        }
        else
        {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++)
        {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals(""))
            {
                return true;
            }
        }

        for (int i = 0; i < 3; i++)
        {
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals(""))
            {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals(""))
        {
            return true;
        }

        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals(""))
        {
            return true;
        }

        return false;
    }

    private void player1Wins() {
        player1Points++;
    //    Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        EditText name = (EditText) findViewById(R.id.textView2);
        String nameString=name.getText().toString();
        Toast.makeText(getApplicationContext(),nameString + " WINS",Toast.LENGTH_SHORT).show();

        tv_declare.setText("Congratulations!!! " + nameString + " wins");
/*
        tv_declare.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_declare.setVisibility(View.INVISIBLE);
            }
        }, 3000);
*/
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_declare.setText("Play Once More");
            }
        }, 1500);






        final MediaPlayer catSoundMediaPlayer = MediaPlayer.create(this, R.raw.win);
        catSoundMediaPlayer.start();
        updatePointsText();
        resetBoard();
    }
void leaderboard(){
    EditText name = (EditText) findViewById(R.id.textView3);
    String nameString=name.getText().toString();

    EditText name1 = (EditText) findViewById(R.id.textView2);
    String nameString1=name1.getText().toString();
if(player2Points > player1Points) {
    Toast.makeText(getApplicationContext(), nameString + " " + player2Points + "\n" + nameString1 + player1Points, Toast.LENGTH_LONG).show();
    tv_declare.setText(nameString + " " + player2Points + " points\n" + nameString1 + " " + player1Points + " points");

    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            tv_declare.setText("Continue Playing");
        }
    }, 1500);
}
    if(player1Points > player2Points) {
        Toast.makeText(getApplicationContext(), nameString1 + " " + player1Points + "\n" + nameString + " " + player2Points, Toast.LENGTH_LONG).show();
        tv_declare.setText(nameString1 + " " + player1Points + " points\n" + nameString + " " + player2Points + " points");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_declare.setText("Continue PLaying");
            }
        }, 1500);
    }

    if(player1Points == player2Points)
    {
        Toast.makeText(getApplicationContext(),"both have same pts",Toast.LENGTH_LONG).show();
        tv_declare.setText("Both have SAME points");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_declare.setText("Continue PLaying");
            }
        }, 1500);
    }

    }

    private void player2Wins() {
        player2Points++;
       // Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();

        EditText name = (EditText) findViewById(R.id.textView3);
        String nameString=name.getText().toString();
        Toast.makeText(getApplicationContext(),nameString + " WINS",Toast.LENGTH_SHORT).show();

      //  tv_declare.setText(message);
        tv_declare.setText("Congratulations!!! " + nameString + " wins ");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_declare.setText("Play Once More");
            }
        }, 1500);

        final MediaPlayer catSoundMediaPlayer = MediaPlayer.create(this, R.raw.win);
        catSoundMediaPlayer.start();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        draw++;
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();

        tv_declare.setText("oops... DRAW MATCH");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_declare.setText("Play Once More");
            }
        }, 1500);

        final MediaPlayer catSoundMediaPlayer = MediaPlayer.create(this, R.raw.draw);
        catSoundMediaPlayer.start();
        updatePointsText();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayer1.setText(player1Points + " wins");
        textViewPlayer2.setText(player2Points + " wins");
        textViewdraw.setText(draw + " draws");

     /*   if(player1Points > player2Points)
        {


         EditText e1=(EditText)findViewById(R.id.textView2);
            EditText e2=(EditText)findViewById(R.id.textView3);
            String getFirstString = String.valueOf(e1.getText());
            String getSecondString = String.valueOf(e2.getText());
            e1.setText(getSecondString);
            e2.setText(getFirstString);

        }*/


    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
        chancecount = 0;
        //use this code if you take users name also as input so that there is random chandom chance of each player first
        //Random random= new Random();
        //boolean randomoutcome=random.nextBoolean();
        player1Turn = true;
        chance.setText("1 PLAYER");
    }

}
