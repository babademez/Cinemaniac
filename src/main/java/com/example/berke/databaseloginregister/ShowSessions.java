package com.example.berke.databaseloginregister;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class ShowSessions extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sessions);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView sessions = (TextView) findViewById(R.id.sessions);
        sessions.setText("");

        sessions.append( "Address: \n" + UserLocation.theaterName +"\n" + "Sessions:" + "\n");
        // Showing the sessions adding them to the session TextView.
        for (int i = 0; i < ParseActivity.parser.getMovieSessions().get(ParseActivity.clickedButtonId)
                .length; i++)
        {
            sessions.append(ParseActivity.parser.getMovieSessions().get(ParseActivity.clickedButtonId)[i]
                    + '\n');
        }

    }

    @Override
    // This method connects this class with show sessions class.
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
