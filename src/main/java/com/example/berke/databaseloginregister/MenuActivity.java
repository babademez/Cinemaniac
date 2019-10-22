package com.example.berke.databaseloginregister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {


    protected static String whereFrom;

       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Thera are two buttons which show upcoming and on screen movies.
        Button b1 =(Button) findViewById(R.id.mBut);
        Button b2 =(Button) findViewById(R.id.uBut);

        // The first button opens the OnScreenActivity.
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int1 = new Intent(MenuActivity.this, OnScreenActivity.class);
                startActivity(int1);

            }
        });

        // The second button opens the UpcomingActivity.
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int2 = new Intent(MenuActivity.this, UpcomingActivity.class);
                startActivity(int2);
            }
        });
    }

}
