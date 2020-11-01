package com.example.jithin.womensafety;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {
    long delay = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        Timer timertask=new Timer();
        //Toast.makeText(Splash.this,"welcome", Toast.LENGTH_SHORT).show();
        TimerTask showsplash=new TimerTask() {
            @Override
            public void run() {
                finish();

                    Intent i = new Intent(Splash.this, Login.class);
                    startActivity(i);

            }
        };
        timertask.schedule(showsplash,delay);
    }
}
