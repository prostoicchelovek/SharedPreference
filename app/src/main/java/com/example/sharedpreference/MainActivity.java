package com.example.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private TextView textView;
    private boolean isTimerOn;
    private Button button;
    private CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);
        seekBar.setMax(600);
        seekBar.setProgress(30);
        isTimerOn=false;
        button = findViewById(R.id.button);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                long progressInMillis = progress*1000;
                updateTime(progressInMillis);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void start(View view) {
        if (isTimerOn){
            button.setText("Stop");
            seekBar.setEnabled(false);
            isTimerOn=true;
            countDownTimer = new CountDownTimer(seekBar.getProgress()*1000,1000) {
                @Override
                public void onTick(long l) {
                    updateTime(l);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.bell_sound);
                    mediaPlayer.start();
                    resetTimer();
                }
            };
            countDownTimer.start();
        } else{
resetTimer();
        }

    }
    private void updateTime(long l){
        int minuts = (int)l/1000/60;
        int seconds = (int) l/1000 - (minuts*60);
        String minutesString = "";
        String secondsString = "";
        if (minuts<10){minutesString = "0"+minuts;}
        else{ minutesString = String.valueOf(minuts);}
        if (seconds<10){secondsString = "0"+seconds;}
        else {secondsString = String.valueOf(seconds);}
        textView.setText(minutesString + ":"+secondsString);
    }
    private void resetTimer(){
        countDownTimer.cancel();
        textView.setText("00:30");
        button.setText("start");
        seekBar.setEnabled(true);
        seekBar.setProgress(60);
        isTimerOn=false;
    }
}