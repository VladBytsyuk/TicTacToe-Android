package edu.android.tictactoe_v11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

enum Player {
    X, O, Null;
    public Player swap() {
        if (this == Player.X)
            return Player.O;
        else if (this == Player.O)
            return Player.X;
        else
            return Player.Null;
    }
}

public class MainActivity extends AppCompatActivity {
    int FIELD_SIZE = 3;
    private Player currentPlayer;
    private Player[][] gameField;
    private boolean isGameState;

    private int playerXScore;
    private int playerOScore;
    private TextView playerXTextView;
    private TextView player0TextView;
    private TextView playerXScoreTextView;
    private TextView playerOScoreTextView;
    private TextView messageTextView;
    private TextView winnerTextView;

    private Button buttonPlayAgain;
    private ImageButton[][] imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameInit();
    }
    private void gameInit() {
        currentPlayer = Player.X;
        gameField = new Player[FIELD_SIZE][FIELD_SIZE];
        isGameState = true;
        playerXScore = 0;
        playerOScore = 0;
        textViewInit();
        imageButtonInit();
        buttonPlayAgain = (Button) findViewById(R.id.buttonPlayAgain);
        clearGameField();
    }
    private void textViewInit() {
        playerXTextView = (TextView) findViewById(R.id.playerXTextView);
        player0TextView = (TextView) findViewById(R.id.playerOTextView);
        playerXScoreTextView = (TextView) findViewById(R.id.playerXScoreTextView);
        playerOScoreTextView = (TextView) findViewById(R.id.playerOScoreTextView);
        messageTextView = (TextView) findViewById(R.id.messageTextView);
        winnerTextView = (TextView) findViewById(R.id.winnerTextView);
    }
    private void imageButtonInit() {
        imageButton = new ImageButton[3][3];
        imageButton[0][0] = (ImageButton) findViewById(R.id.imageButton00);
        imageButton[0][1] = (ImageButton) findViewById(R.id.imageButton01);
        imageButton[0][2] = (ImageButton) findViewById(R.id.imageButton02);
        imageButton[1][0] = (ImageButton) findViewById(R.id.imageButton10);
        imageButton[1][1] = (ImageButton) findViewById(R.id.imageButton11);
        imageButton[1][2] = (ImageButton) findViewById(R.id.imageButton12);
        imageButton[2][0] = (ImageButton) findViewById(R.id.imageButton20);
        imageButton[2][1] = (ImageButton) findViewById(R.id.imageButton21);
        imageButton[2][2] = (ImageButton) findViewById(R.id.imageButton22);
    }
    private void clearGameField() {
        for (int i = 0; i < FIELD_SIZE; ++i) {
            for (int j = 0; j < FIELD_SIZE; ++j) {
                gameField[i][j] = Player.Null;
                imageButton[i][j].setImageResource(R.drawable.space);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actionRestart) {
            restartGame();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isGameOver() {
        return isWinInRow() || isWinInColumn() || isWinInMainDiagonal() || isWinInSubDiagonal() || isGameFieldIsFull();
    }
    private boolean isDraw() {
        return !isWinInRow() && !isWinInColumn() && !isWinInMainDiagonal() && !isWinInSubDiagonal() && isGameFieldIsFull();
    }
    private boolean isWinInRow() {
        boolean isWinInRow0 = ( (gameField[0][0] == gameField[0][1]) && (gameField[0][1] == gameField[0][2]) && (gameField[0][0] != Player.Null) );
        boolean isWinInRow1 = ( (gameField[1][0] == gameField[1][1]) && (gameField[1][1] == gameField[1][2]) && (gameField[1][0] != Player.Null) );
        boolean isWinInRow2 = ( (gameField[2][0] == gameField[2][1]) && (gameField[2][1] == gameField[2][2]) && (gameField[2][0] != Player.Null) );
        return isWinInRow0 || isWinInRow1 || isWinInRow2;
    }
    private boolean isWinInColumn() {
        boolean isWinInColumn0 = ( (gameField[0][0] == gameField[1][0]) && (gameField[1][0] == gameField[2][0]) && (gameField[0][0] != Player.Null) );
        boolean isWinInColumn1 = ( (gameField[0][1] == gameField[1][1]) && (gameField[1][1] == gameField[2][1]) && (gameField[0][1] != Player.Null) );
        boolean isWinInColumn2 = ( (gameField[0][2] == gameField[1][2]) && (gameField[1][2] == gameField[2][2]) && (gameField[0][2] != Player.Null) );
        return isWinInColumn0 || isWinInColumn1 || isWinInColumn2;
    }
    private boolean isWinInMainDiagonal() {
        return ((gameField[0][0] == gameField[1][1]) && (gameField[1][1] == gameField[2][2]) && (gameField[0][0] != Player.Null));
    }
    private boolean isWinInSubDiagonal() {
        return ((gameField[0][2] == gameField[1][1]) && (gameField[1][1] == gameField[2][0]) && (gameField[0][2] != Player.Null));
    }
    private boolean isGameFieldIsFull() {
        for (int i = 0; i < FIELD_SIZE; ++i) {
            for (int j = 0; j < FIELD_SIZE; ++j) {
                if (gameField[i][j] == Player.Null)
                    return false;
            }
        }
        return true;
    }

    public void onClick00(View v) { onClickCell(0, 0);  }
    public void onClick01(View v) { onClickCell(0, 1);  }
    public void onClick02(View v) { onClickCell(0, 2);  }
    public void onClick10(View v) { onClickCell(1, 0);  }
    public void onClick11(View v) { onClickCell(1, 1);  }
    public void onClick12(View v) { onClickCell(1, 2);  }
    public void onClick20(View v) { onClickCell(2, 0);  }
    public void onClick21(View v) { onClickCell(2, 1);  }
    public void onClick22(View v) { onClickCell(2, 2);  }

    private void onClickCell(int x, int y) {
        if (isGameState && gameField[x][y] == Player.Null) {
            makeMove(x, y);
            currentPlayer = currentPlayer.swap();
            setMessageTextView();
            checkEndOfGame();
        }
    }
    private void makeMove(int i, int j) {
        gameField[i][j] = currentPlayer;
        if (currentPlayer == Player.X)
            imageButton[i][j].setImageResource(R.drawable.x);
        else if (currentPlayer == Player.O)
            imageButton[i][j].setImageResource(R.drawable.o);
    }
    private void setMessageTextView() {
        if (currentPlayer == Player.X)
            messageTextView.setText(R.string.playerXMove);
        else if (currentPlayer == Player.O)
            messageTextView.setText(R.string.playerOMove);
    }
    private void checkEndOfGame() {
        if (isGameOver()) {
            messageTextView.setText(R.string.empty);
            isGameState = false;
            if (!isGameFieldIsFull() || !isDraw()) {
                if (currentPlayer == Player.X)
                    playerWin(Player.O);
                else if (currentPlayer == Player.O)
                    playerWin(Player.X);
            } else if (isDraw())
                winnerTextView.setText(R.string.draw);
            buttonPlayAgain.setVisibility(View.VISIBLE);
        }
    }
    private void playerWin(Player player) {
        if (player == Player.X) {
            ++playerXScore;
            playerXScoreTextView.setText(Integer.toString(playerXScore));
            winnerTextView.setText(R.string.playerXWin);
        } else if (player == Player.O) {
            ++playerOScore;
            playerOScoreTextView.setText(Integer.toString(playerOScore));
            winnerTextView.setText(R.string.playerOWin);
        }
    }

    public void restartGame () {
        currentPlayer = Player.X;
        isGameState = true;
        playerXScore = 0;
        playerOScore = 0;
        playerXScoreTextView.setText(Integer.toString(playerXScore));
        playerOScoreTextView.setText(Integer.toString(playerOScore));
        messageTextView.setText(R.string.playerXMove);
        winnerTextView.setText(R.string.empty);
        buttonPlayAgain.setVisibility(View.INVISIBLE);
        clearGameField();
    }
    public void playAgain(View v) {
        currentPlayer = Player.X;
        isGameState = true;
        messageTextView.setText(R.string.playerXMove);
        winnerTextView.setText(R.string.empty);
        buttonPlayAgain.setVisibility(View.INVISIBLE);
        clearGameField();
    }
}
