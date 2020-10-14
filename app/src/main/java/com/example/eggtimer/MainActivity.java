package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    Button btnGo;
    TextView tvTimer;
    Boolean counterIsActive=false;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar=findViewById(R.id.seekBar);
        tvTimer=findViewById(R.id.tvTimer);
        btnGo=findViewById(R.id.btnGo);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (counterIsActive) {
                    resetTimer();
                } else {
                    counterIsActive = true;
                    seekBar.setEnabled(false);
                    btnGo.setText("STOP!");

                    countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {

                        @Override
                        public void onTick(long l) {

                            update((int) l / 1000);                       //conversion from milli to seconds.
                        }

                        @Override
                        public void onFinish() {

                            tvTimer.setText("0:00");

                            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound);
                            mediaPlayer.start();

                            resetTimer();
                        }
                    }.start();
                }
            }
        });

        seekBar.setMax(600);              //10 min
        seekBar.setProgress(30);          //starting point of seek bar.
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                update(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void update(int i)
    {
        int minutes=i/60;
        int seconds=i-(minutes*60);

        String secondsString=Integer.toString(seconds);
        if(seconds <=9)
        {
            secondsString="0"+secondsString;
        }
        tvTimer.setText(Integer.toString(minutes) + ":" + secondsString);
    }

    public void resetTimer()
    {
        tvTimer.setText("0:30");
        seekBar.setProgress(30);
        seekBar.setEnabled(true);
        countDownTimer.cancel();
        btnGo.setText("GO!");
        counterIsActive=false;
    }
}