package com.example.chessclock;

import static java.lang.Long.valueOf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
    private Button home;
    private String move_turn;
    private String state="0";
    private int minutes;
    private int increment;
    private int hours;
    int mode;

    int no_of_moves_by_p1=0;
    int no_of_moves_by_p2=0;

    private TextView m1;
    private TextView m2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);

        constraintLayout.setBackgroundColor(Color.parseColor("#F5F5F5"));
        p1 = findViewById(R.id.p1);
        p2 = findViewById(R.id.p2);

        m1=findViewById(R.id.m1);
        m2=findViewById(R.id.m2);

        pause=findViewById(R.id.pause);
        play=findViewById(R.id.play);
        home=findViewById(R.id.home);

        mode=getIntent().getIntExtra("mode",1);
        final MediaPlayer mp=MediaPlayer.create(this,R.raw.click_sound);

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

        timeLeft_in_timer1=minutes*60000;
        timeLeft_in_timer2=minutes*60000;
        Log.d("check mode",""+mode);
        p1.setText(Integer.toString(minutes));
        p2.setText(Integer.toString(minutes));

        timer1 = new CountDownTimer(minutes * 60000L, 1000) {
            public void onTick(long l) {
                long seconds = (l / 1000) % 60;
                long minutes_rn = (l / (1000 * 60)) % 60;
                long hours = (l / (1000 * 60 * 60)) % 60;

                display1 = String.format("%02d:%02d:%02d", hours, minutes_rn, seconds); // Display hours, minutes, and seconds
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
                long minutes_rn = (l / (1000 * 60)) % 60;
                long hours = (l / (1000 * 60 * 60)) % 60;
                display2 = String.format("%02d:%02d:%02d", hours, minutes_rn, seconds);
                    p2.setText(display2);
                    timeLeft_in_timer2 = l;
            }

            public void onFinish() {
                p2.setText("times up");

            }
        };

        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                if(p1.isChecked()) {

                        timeLeft_in_timer1 = timeLeft_in_timer1 + (long) increment * 1000;
                        long seconds = (timeLeft_in_timer1 / 1000) % 60;
                        long minutes_rn = (timeLeft_in_timer1 / (1000 * 60)) % 60;
                    long hours = (timeLeft_in_timer1 / (1000 * 60 * 60)) % 60;
                    display1 = String.format("%02d:%02d:%02d", hours, minutes_rn, seconds);
                    Log.d("display ", display1+"display2 "+display2+"seconds "+seconds);


                    p2.setChecked(false);
                    p2.setEnabled(true);
                    p1.setEnabled(false);
                    updateText(display1,display2);
                    no_of_moves_by_p1++;
                    m1.setText("MOVES: "+no_of_moves_by_p1);

                }
                if(p1.isChecked()&&counter1==0)
                {

                    timer2.start();
                    counter1++;
                    updateText(display1,display2);
                    Log.d("Clock","TIMER1 CANCELLED and timer 2 STARTED" + display1 + "     " + display2);
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
                mp.start();


                if(p2.isChecked()) {
                        timeLeft_in_timer2 = timeLeft_in_timer2 + (long) increment * 1000;
                        long seconds = (timeLeft_in_timer2 / 1000) % 60;
                        long minutes_rn = (timeLeft_in_timer2 / (1000 * 60)) % 60;
                        long hours = (timeLeft_in_timer2 / (1000 * 60 * 60)) % 60;
                        display2 = String.format("%02d:%02d:%02d", hours, minutes_rn, seconds);
                        Log.d("display ", display1+"display2 "+display2+"seconds "+seconds);

                    p1.setChecked(false);
                    p1.setEnabled(true);
                    p2.setEnabled(false);
                    updateText(display1,display2);
                    no_of_moves_by_p2++;
                    m2.setText("MOVES: "+no_of_moves_by_p2);
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
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), modes.class);
                startActivity(intent);
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
                long hours = (l / (1000 * 60 * 60)) % 60;
                display1 = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                timeLeft_in_timer1 = l;
                updateText(display1,display2);

            }

            public void onFinish() {
                p1.setText("times up");
                timer2.cancel();
                timer1.cancel();
                p2.setEnabled(false);
                p1.setEnabled(false);

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
                long hours = (timeLeft_in_timer2 / (1000 * 60 * 60)) % 60;
                display2 = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                timeLeft_in_timer2=l;
                updateText(display1,display2);
            }

            public void onFinish() {
                p2.setText("times up");
                p1.setEnabled(false);
                p2.setEnabled(false);
                timer1.cancel();
                timer2.cancel();

            }
        };
        timer2.start();
    }
    public void updateText(String d1,String d2)
    {
        p1.setText(d1);
        p2.setText(d2);
        m1.setText("MOVES: "+no_of_moves_by_p1);
        m2.setText("MOVES: "+no_of_moves_by_p2);


    }
}