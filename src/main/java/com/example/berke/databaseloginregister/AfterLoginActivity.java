package com.example.berke.databaseloginregister;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AfterLoginActivity extends AppCompatActivity {

    private String username;
    private String password;
    private Button adminButton;
    private Button locationButton;
    private Button goMovies;
    private TextView message;
    private DataBase userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        adminButton =(Button)findViewById(R.id.onlyAdminButton);
        locationButton = (Button)findViewById(R.id.findLocationButton);
        goMovies = (Button)findViewById(R.id.goToMoviesButton);
        if( UserSetting.getIsAdmin() == 1)
        {
            adminButton.setVisibility(View.VISIBLE);
            adminButton.setOnClickListener( new adminButtonListener());
        }


        goMovies.setOnClickListener( new goToMoviesButtonListener());
        locationButton.setOnClickListener( new LocationButtonListener());
    }


    private class LocationButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            startActivity( new Intent( AfterLoginActivity.this, UserLocation.class));
        }
    }

    private class adminButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            startActivity( new Intent( AfterLoginActivity.this, AdminPage.class));
        }
    }
    private class goToMoviesButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            startActivity( new Intent( AfterLoginActivity.this, MenuActivity.class));
        }
    }
}
