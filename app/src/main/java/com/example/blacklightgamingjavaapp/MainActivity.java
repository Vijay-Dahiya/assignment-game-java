package com.example.blacklightgamingjavaapp;

import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.example.blacklightgamingjavaapp.databinding.ActivityMainBinding;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LifecycleOwner {

    private Timer timer;
    private boolean isClicked = true;
    private ActivityMainBinding binding;
    private int greyPosition = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listenerInIt();
        startTimer();
    }

    private void runCondition(int randomNumber) {
        greyPosition = randomNumber;
        switch (randomNumber) {
            case 1:
                binding.square1.setBackgroundTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(this, R.color.light_gray)));
                break;
            case 2:
                binding.square2.setBackgroundTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(this, R.color.light_gray)));
                break;
            case 3:
                binding.square3.setBackgroundTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(this, R.color.light_gray)));
                break;
            case 4:
                binding.square4.setBackgroundTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(this, R.color.light_gray)));
                break;
        }
    }

    private void listenerInIt() {
        binding.square1.setOnClickListener(this);
        binding.square2.setOnClickListener(this);
        binding.square3.setOnClickListener(this);
        binding.square4.setOnClickListener(this);
        binding.gameOver.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(binding.square1)) {
            if (greyPosition == 1) {
                increaseScore();
                isClicked = true;
                binding.square1.setBackgroundTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(this, R.color.red)));
            } else gameOver();
        } else if (view == binding.square2) {
            if (greyPosition == 2) {
                increaseScore();
                isClicked = true;
                binding.square2.setBackgroundTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(this, R.color.blue)));
            } else gameOver();
        } else if (view == binding.square3) {
            if (greyPosition == 3) {
                increaseScore();
                isClicked = true;
                binding.square3.setBackgroundTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(this, R.color.yellow)));
            } else gameOver();
        } else if (view == binding.square4) {
            if (greyPosition == 4) {
                increaseScore();
                isClicked = true;
                binding.square4.setBackgroundTintList(ColorStateList.valueOf(
                        ContextCompat.getColor(this, R.color.green)));
            } else gameOver();
        }
    }

    private void gameOver() {
        runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Game Over")
                    .setMessage("Your Score is: " + score)
                    .setPositiveButton("Restart", (dialog, which) -> {
                        dialog.dismiss();
                        restartTimer();
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
        stopTimer();
    }

    private void restartTimer() {
        isClicked = true;
        stopTimer();
        resetScreen();
        startTimer();
    }

    private void resetScreen() {
        score = 0;
        greyPosition = 0;
        isClicked = true;
        binding.square1.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red)));
        binding.square2.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue)));
        binding.square3.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow)));
        binding.square4.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.green)));
        binding.score.setText("Score : " + score);
    }

    private void startTimer() {
        Toast.makeText(this, "Game is started", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(this::activate, 3000);
    }

    private void activate() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (isClicked) {
                    isClicked = false;
                } else {
                    gameOver();
                }
                Random random = new Random();
                int randomNumber = random.nextInt(4) + 1;
                runCondition(randomNumber);
            }
        }, 0, 1000);
    }


    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void increaseScore() {
        score++;
        binding.score.setText("Score : " + score);
    }
}
