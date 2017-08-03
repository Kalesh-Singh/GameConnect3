package com.kaleshsingh.gameconnect3;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // 0 = yellow, 1 = red
    int activePlayer = 0;

    boolean gameIsActive = true;

    // 2 means not played
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};



    public void dropIn(View view) {

        ImageView counter = (ImageView) view;

        System.out.println(counter.getTag().toString());

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameIsActive) {

            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);

            for(int[] winningPosition : winningPositions){
                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2){

                    System.out.println("The winner is " + gameState[winningPosition[0]]);

                    String winner = "Red has won!";

                    if(gameState[winningPosition[0]] == 0){
                        winner = "Yellow has won!";
                    }
                    else{
                        // Check if all the spot have been played on to see if it's a draw.
                        boolean gameIsOver = true;
                        for(int counterState : gameState){
                            if(counterState == 2) gameIsOver = false;
                        }

                        if(gameIsOver){
                            String draw = "It is a draw!";
                            TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                            winnerMessage.setText(draw);

                            LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                            layout.setVisibility(View.VISIBLE);
                        }

                    }

                    // Someone has won

                    gameIsActive = false;

                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);

                    winnerMessage.setText(winner);

                    LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                }
                // Moved from here
            }
        }
    }

    public void playAgain(View view){

        gameIsActive = true;

        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        activePlayer = 0;

        for(int i = 0; i < gameState.length; ++i){
            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i = 0; i < gridLayout.getChildCount(); ++i){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
