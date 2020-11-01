package com.example.jithin.womensafety;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Map extends AppCompatActivity {

    LinearLayout hospital ,police,bus;
    String latitude,longtitude;
    //private AirLocation airLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        hospital = (LinearLayout) findViewById(R.id.hospital);
        police = (LinearLayout) findViewById(R.id.police);
        bus = (LinearLayout) findViewById(R.id.bus);


        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri gmmIntentUri = Uri.parse("geo:"+latitude+","+longtitude+"0,0?q=hospitals");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW,gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity (mapIntent);

            }
        });
        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:"+latitude+","+longtitude+"0,0?q=police station");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW,gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity (mapIntent);
            }
        });
        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Uri gmmIntentUri = Uri.parse("geo:"+latitude+","+longtitude+"?q=bus stops");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW,gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
            }
        });
    }
}
