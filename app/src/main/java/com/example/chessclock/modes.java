package com.example.chessclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class modes extends AppCompatActivity {
    public int mode=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modes);


        Button bullet = findViewById(R.id.bullet);
        Button blitz = findViewById(R.id.blitz);
        Button rapid = findViewById(R.id.rapid);
        Button classic = findViewById(R.id.classic);
        EditText increment_set = findViewById(R.id.increment);
        EditText time_set = findViewById(R.id.time);

        Button custom = findViewById(R.id.custom);

        bullet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimer(1);
            }
        });

        blitz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimer(5);
            }
        });

        rapid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimer(30);
            }
        });

        classic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimer(60);
            }
        });

        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String stime= time_set.getText().toString();
               String sincrement=increment_set.getText().toString();
               if(stime.isEmpty())
               {
                   stime="5";
               }
               if(sincrement.isEmpty()){
                   sincrement="0";
               }
               int time=Integer.parseInt(stime);
               int increment=Integer.parseInt(sincrement);
               setTimer(time, increment);
            }
        });

    }

    public void setTimer(int minutes) {
        mode=0;
        Intent intent = new Intent(this, clock.class);
        intent.putExtra("MINUTES_EXTRA", minutes);
        intent.putExtra("mode", mode);
        startActivity(intent);
    }

    public  void setTimer(int time,int increment)
    {
        mode=1;
        Intent intent = new Intent(this, clock.class);
        intent.putExtra("time_set", time);
        intent.putExtra("increment_set", increment);
        intent.putExtra("mode", mode);

        startActivity(intent);

    }
}


