package com.example.mygatodayan;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity<v> extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button[] buttons = new Button[36];
    private Button resetGame;

    private int playerOneScoreCount, playerTwoScoreCount = 0, rountCount;
    boolean activePlayer;

    // p1 => 0
    // p2 => 1
    // empate =>2

    String text;

    int[] gameState = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2};

    int[][] winningPositions = {
            //filas
            {0,1,2,3},{1,2,3,4},{2,3,4,5},
            {6,7,8,9},{7,8,9,10},{8,9,10,11},
            {12,13,14,15},{13,14,15,16},{14,15,16,17},
            {18,19,20,21},{19,20,21,22},{20,21,22,23},
            {24,25,26,27},{25,26,27,28},{26,27,28,29},
            {30,31,32,33},{31,32,33,34},{32,33,34,35},
            //columnas
            {0,6,12,18},{6,12,18,24},{12,18,24,30},
            {1,7,13,19},{7,13,19,25},{13,19,25,31},
            {2,8,14,20},{8,14,20,26},{14,20,26,32},
            {3,9,15,21},{9,15,21,27},{15,21,27,33},
            {4,10,16,22},{10,16,23,28},{16,22,28,34},
            {5,11,17,23},{11,17,24,29},{17,23,29,35},
            //linea 1
            {2,9,16,23},{1,8,15,22},{8,15,22,29},
            {0,7,14,21},{7,14,21,28},{14,21,28,35},
            {6,13,20,27},{13,20,27,34},{12,19,26,33},
            //linea 2
            {3,8,13,18},{4,9,14,19},{9,14,19,24},
            {5,10,15,20},{10,15,20,25},{15,20,25,30},
            {11,16,21,26},{16,21,26,31},{17,22,27,32}
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);

        resetGame = (Button) findViewById(R.id.resetGame);

        for (int i = 0; i < buttons.length; i++) {
            String buttonID = "btn_" + i;
            String defType;
            int resourceID = getResources().getIdentifier(buttonID, defType = "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        rountCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;

    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1, buttonID.length()));

        String colorString;
        if (activePlayer) {
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor(colorString = "#70FFEA"));
            gameState[gameStatePointer] = 0;
        } else {
            ((Button) v).setText("0");
            ((Button) v).setTextColor(Color.parseColor(colorString = "#FF1493"));
            gameState[gameStatePointer] = 1;
        }
        rountCount++;

        MainActivity<v> context;
        if (checkWinner()) {
            if (activePlayer) {
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(context = this, text = "El jugador 1 esta ganando!", Toast.LENGTH_SHORT).show();
            } else {
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(context = this, text = "El jugador 2 esta ganando!", Toast.LENGTH_SHORT).show();
            }
            playerAgain();

        } else if (rountCount == 36) {
            playerAgain();
            Toast.makeText(context = this, text = "Empate!", Toast.LENGTH_SHORT).show();

        } else {
            activePlayer = !activePlayer;
        }
        if (playerOneScoreCount > playerTwoScoreCount) {
            playerStatus.setText("Jugador 1 gano!");
        } else if (playerTwoScoreCount > playerOneScoreCount) {
            playerStatus.setText("Jugador 2 gano");
        } else {
            playerStatus.setText("");
        }
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });


    }


    public boolean checkWinner() {
        boolean winnerResult = false;

        for (int[] winningPosion : winningPositions) {
            if (gameState[winningPosion[0]] == gameState[winningPosion[1]] &&
                    gameState[winningPosion[1]] == gameState[winningPosion[2]] &&
                    gameState[winningPosion[2]] == gameState[winningPosion[3]] &&
                    gameState[winningPosion[0]] != 2) {
                winnerResult = true;
            }

        }
        return winnerResult;


    }

    public void updatePlayerScore() {
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    public void playerAgain() {
        rountCount = 0;
        activePlayer = true;

        for (int i = 0; i < buttons.length; i++) {
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}
