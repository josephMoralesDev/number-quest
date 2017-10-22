package com.joxamo.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button startButton, tryAgain, button1, button2, button3, button4;
    TextView timer, scoreBoard, problem;
    CountDownTimer countDownTimer;
    boolean timerStart = false;
    int number1, number2, solution, correct, problemsAsked;
    Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = (Button) findViewById(R.id.startButton);
        tryAgain = (Button) findViewById(R.id.tryAgainButton);
        timer = (TextView) findViewById(R.id.timeTextView);
        problem = (TextView) findViewById(R.id.problemTextView);
        scoreBoard = (TextView) findViewById(R.id.scoreTextView);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        rand = new Random();
    }

    public void start(View view) {
        startButton.setVisibility(View.INVISIBLE);
        tryAgain.setVisibility(View.INVISIBLE);
        timer.setVisibility(View.VISIBLE);
        problem.setVisibility(View.VISIBLE);
        scoreBoard.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);
        timerStart = true;
        correct = 0;
        problemsAsked = 0;
        StringBuilder score = new StringBuilder(Integer.toString(correct) + "/" + Integer.toString(problemsAsked));
        scoreBoard.setText(score);
        controlTimer();
        presentedProblem();

    }

    private void controlTimer() {

        countDownTimer = new CountDownTimer(30 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer((int) millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerStart = false;
                tryAgain.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Game over man. Game over", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    private void updateTimer(int secondsLeft) {
        int minutes = (int) secondsLeft / 60;
        int second = secondsLeft - minutes * 60;
        StringBuilder stringBuilder = new StringBuilder(Integer.toString(second));
        if(stringBuilder.length() == 1) {
            stringBuilder.insert(0, "0");
        }
        timer.setText(Integer.toString(minutes) + ":" + stringBuilder);
    }

    private void presentedProblem() {
        number1 = rand.nextInt(30) + 1;
        number2 = rand.nextInt(30) + 1;
        solution = number1 + number2;
        problem.setText(Integer.toString(number1) + " + " + Integer.toString(number2));
        setButtons();
    }

    private void setButtons() {
        int solutionBox = rand.nextInt(4) + 1;

        if(solutionBox == 1) {
            button1.setText(Integer.toString(solution));
        } else {
            button1.setText(Integer.toString(getValidRandom(1, 30, solution)));
        }

        if(solutionBox == 2) {
            button2.setText(Integer.toString(solution));
        } else {
            button2.setText(Integer.toString(getValidRandom(1, 30, solution)));
        }

        if(solutionBox == 3) {
            button3.setText(Integer.toString(solution));
        } else {
            button3.setText(Integer.toString(getValidRandom(1, 30, solution)));
        }

        if(solutionBox == 4) {
            button4.setText(Integer.toString(solution));
        } else {
            button4.setText(Integer.toString(getValidRandom(1, 30, solution)));
        }

    }

    private int getValidRandom(int min, int max, int exclude) {
        int ourNewRandom = rand.nextInt(max) + min;

        while(exclude == ourNewRandom) {
            ourNewRandom = rand.nextInt(max) + min;
            if(exclude != ourNewRandom)
                return ourNewRandom;
        }

        return ourNewRandom;
    }

    public void checkSelection(View view) {
        if (timerStart == true) {
            Button selection = (Button) view;
            int userSelect = Integer.parseInt(selection.getText().toString());
            problemsAsked++;
            if (userSelect == solution) {
                correct++;
                Toast.makeText(this, "Good choice", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Bad choice", Toast.LENGTH_SHORT).show();
            }
            StringBuilder score = new StringBuilder(Integer.toString(correct) + "/" + Integer.toString(problemsAsked));
            scoreBoard.setText(score);
            presentedProblem();
        }
    }
}
