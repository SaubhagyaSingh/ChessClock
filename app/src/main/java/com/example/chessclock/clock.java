package com.example.chessclock;

import static java.lang.Long.valueOf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

public class clock extends AppCompatActivity {

    private long timeLeft_in_timer1;
    private long timeLeft_in_timer2;

    private CountDownTimer timer1;
    private CountDownTimer timer2;

    private String display1="";
    private String display2="";

    int counter1 = 0;
    int counter2 = 0;

    private ToggleButton p1;
    private ToggleButton p2;
    private Button pause;
    private Button play;
    private String move_turn;
    private String state="0";
    private int minutes;
    private int increment;
    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        p1 = findViewById(R.id.p1);
        p2 = findViewById(R.id.p2);

        pause=findViewById(R.id.pause);
        play=findViewById(R.id.play);

        mode=getIntent().getIntExtra("mode",1);
        if(mode==1)
        {
            minutes = getIntent().getIntExtra("time_set", 0);
            increment = getIntent().getIntExtra("increment_set", 0);

        }
        else if(mode==0){
            minutes = getIntent().getIntExtra("MINUTES_EXTRA", 0);
            increment=0;
        }
        else if(mode==2){
            minutes=5;
            increment=0;
        }
        Log.d("check mode",""+mode);
        p1.setText(Integer.toString(minutes));
        p2.setText(Integer.toString(minutes));

        timer1 = new CountDownTimer(minutes * 60000L, 1000) {
            public void onTick(long l) {
                long seconds = (l / 1000) % 60;
                long minutes = (l / (1000 * 60)) % 60;
                 display1 = minutes + ":" + String.format("%02d", seconds);
                p1.setText(display1);
                timeLeft_in_timer1 = l;

            }

            public void onFinish() {
                p1.setText("times up");
            }
        };

        timer2 = new CountDownTimer(minutes * 60000, 1000) {
            public void onTick(long l) {
                long seconds = (l / 1000) % 60;
                long minutes = (l / (1000 * 60)) % 60;
                 display2 = minutes + ":" + String.format("%02d", seconds);
                p2.setText(display2);
                timeLeft_in_timer2=l;

            }

            public void onFinish() {
                p2.setText("times up");

            }
        };

        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(p1.isChecked())
                {
                    p2.setChecked(false);
                    p2.setEnabled(true);
                    p1.setEnabled(false);
                    timeLeft_in_timer1=timeLeft_in_timer1+ (long) increment*1000;
                    long seconds = (timeLeft_in_timer1 / 1000) % 60;
                    long minutes = (timeLeft_in_timer1 / (1000 * 60)) % 60;
                    display1 = minutes + ":" + String.format("%02d", seconds);
                    updateText(display1,display2);

                }
                if(p1.isChecked()&&counter1==0)
                {

                    timer2.start();
                    counter1++;
                    Log.d("Clock","TIMER1 CANCELLED and timer 2 STARTED" + display1 + "     " + display2);
                    updateText(display1,display2);
                    p1.setText(Integer.toString(minutes));
                    timer1.cancel();
                }
               else if(p1.isChecked()&&counter1>0&&
                !p2.isChecked())
                {

                    timer1.cancel();

                    Log.d("clock","TIME LEFT IN TIMER"+timeLeft_in_timer2+"increment is"+increment);

                    Resume2(timeLeft_in_timer2);
                    Log.d("Clock","TIMER1 CANCELLED and timer 2 resumed"+display1+"     "+display2);
                }
            }
        });

        p2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(p2.isChecked())
                {

                    timeLeft_in_timer2=timeLeft_in_timer2+ (long) increment*1000;
                    long seconds = (timeLeft_in_timer2 / 1000) % 60;
                    long minutes = (timeLeft_in_timer2 / (1000 * 60)) % 60;
                    display2 = minutes + ":" + String.format("%02d", seconds);
                    Log.d("display",display1);
                    updateText(display1,display2);
                    p1.setChecked(false);
                    p1.setEnabled(true);
                    p2.setEnabled(false);
                }
                if(p2.isChecked()&&counter2==0)
                {
                    timer1.start();
                    counter2++;
                    timer2.cancel();
                    Log.d("Clock","TIMER2 CANCELLED and timer 1 started"+display1+"     "+display2);
                    updateText(display1,display2);
                }
               else if(p2.isChecked()&&counter2>0&&!p1.isChecked())
                {


                    Log.d("clock","TIME LEFT IN TIMER"+timeLeft_in_timer1+" increment is"+increment);
                    timer2.cancel();
                    updateText(display1,display2);
                    Resume1(timeLeft_in_timer1);
                    Log.d("Clock","TIMER2 CANCELLED and timer 1 resumed"+display1+"     "+display2);
                }
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (state == "0") {
                    if (p1.isChecked()) {
                        move_turn = "p2";
                        p2.setEnabled(false);
                        timer2.cancel();

                    } else if (p2.isChecked()) {
                        move_turn = "p1";
                        p1.setEnabled(false);
                        timer1.cancel();
                    }
                    state="1";
                }
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == "1") {
                    if (move_turn == "p1") {
                        Resume1(timeLeft_in_timer1);
                        p1.setEnabled(true);
                    } else if (move_turn == "p2") {
                        Resume2(timeLeft_in_timer2);
                        p2.setEnabled(true);
                    }
                    state="0";
                }
            }
        });
    }
    public void Resume1(long timeLeft)
    {
        timer1 = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                long seconds = (l / 1000) % 60;
                long minutes = (l / (1000 * 60)) % 60;
                timeLeft_in_timer1 = l;
                display1 = minutes + ":" + String.format("%02d", seconds);
                updateText(display1,display2);

            }

            public void onFinish() {
                p1.setText("times up");
            }
        };
        timer1.start();
    }
    public void Resume2(long timeLeft)
    {
        timer2 = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                long seconds = (l / 1000) % 60;
                long minutes = (l / (1000 * 60)) % 60;
                timeLeft_in_timer2=l;
                display2 = minutes + ":" + String.format("%02d", seconds);
                updateText(display1,display2);
            }

            public void onFinish() {
                p2.setText("times up");
            }
        };
        timer2.start();
    }
    public void updateText(String d1,String d2)
    {
        p1.setText(d1);
        p2.setText(d2);

    }
}