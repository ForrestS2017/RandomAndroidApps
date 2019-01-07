package com.example.fsmit.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView;
    boolean counterIsActive;
    Button controllerButton;
    CountDownTimer countDownTimer;

    public void UpdateTimer(int secondsLeft)
    {
        // Get minutes and seconds
        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;
        // Update timer text
        String secondsString = Integer.toString(seconds);
        String minutesString = Integer.toString(minutes);
        if(seconds < 10) secondsString = "0"+secondsString;
        if(minutes < 10) minutesString = "0"+minutesString;
        timerTextView.setText(minutesString + ":" + secondsString);
    }

    public void controlTimer(View view)
    {
        if(counterIsActive){
            ResetTimer();
            return;
        }

        counterIsActive = true;
        timerSeekBar.setEnabled(false);
        String stop = "STOP";
        controllerButton.setText(stop);
        countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                UpdateTimer((int) millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                //timerTextView.setText("00:00");
                //Log.i("finsihed", "timer done");
                MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                mplayer.start();
                ResetTimer();
            }
        }.start();
    }

    private void ResetTimer() {
        UpdateTimer((int)timerSeekBar.getProgress());
        countDownTimer.cancel();
        String go = "GO";
        controllerButton.setText(go);
        timerSeekBar.setEnabled(true);
        counterIsActive = false;
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        controllerButton = (Button)findViewById(R.id.controllerButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
               UpdateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }



}
