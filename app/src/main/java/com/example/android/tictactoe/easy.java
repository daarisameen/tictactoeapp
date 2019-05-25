package com.example.android.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class easy extends AppCompatActivity implements View.OnClickListener{
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
        setContentView(R.layout.activity_easy);
        textViewPlayer1 = (TextView) findViewById(R.id.playerchance);
        chance = (TextView) findViewById(R.id.chancesetter);
        textViewPlayer2 = (TextView) findViewById(R.id.winview);
        textViewdraw = (TextView) findViewById(R.id.drawview);

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
                Intent backtomain = new Intent(easy.this,mainpage.class);
                startActivity(backtomain);
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (!(chancecount == 10))
        {
            switch (v.getId())
            {
                case R.id.b00:
                    decidechance(buttons[0][0]);
                    break;

                case R.id.b01:
                    decidechance(buttons[0][1]);
                    break;

                case R.id.b02:
                    decidechance(buttons[0][2]);
                    break;

                case R.id.b10:
                    decidechance(buttons[1][0]);
                    break;

                case R.id.b11:
                    decidechance(buttons[1][1]);
                    break;

                case R.id.b12:
                    decidechance(buttons[1][2]);
                    break;

                case R.id.b20:
                    decidechance(buttons[2][0]);
                    break;

                case R.id.b21:
                    decidechance(buttons[2][1]);
                    break;

                case R.id.b22:
                    decidechance(buttons[2][2]);
                    break;
            }
        }
    }

    void decidechance(Button obj)
    {
        obj.setText("x");
        obj.setTextColor(getResources().getColor(R.color.xcolor));
        chance.setText("2 PLAYER");
        obj.setEnabled(false);
        chancecount++;
        player1Turn=!player1Turn;
        result();
    }
    void result() {
        if (checkForWin())
        {
            if (!player1Turn)
            {
                player1Wins();
            }
            else
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
            randombutton();
            chancecount++;
        }
    }
    void randombutton()
    {
        Random random = new Random();
        int i = random.nextInt(3);
        int j = random.nextInt(3);
        compchance(buttons[i][j]);
    }
    void compchance(Button obj)
    {
        if(obj.getText().toString().isEmpty())
        {
            obj.setText("o");
            obj.setTextColor(getResources().getColor(R.color.ocolor));
            chance.setText("1 PLAYER");
            obj.setEnabled(false);
            if (checkForWin())
            {
                player2Wins();
            }
        }
        else
        {
            randombutton();
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
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Computer wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        draw++;
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayer1.setText(player1Points + " wins");
        textViewPlayer2.setText(player2Points + " wins");
        textViewdraw.setText(draw + " draws");
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
