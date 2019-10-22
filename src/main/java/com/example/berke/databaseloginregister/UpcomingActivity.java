package com.example.berke.databaseloginregister;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.berke.databaseloginregister.MenuActivity.whereFrom;

/**
 * Created by BABADEMEZ on 31/07/2017.
 */

public class UpcomingActivity extends AppCompatActivity implements View.OnClickListener{
    protected static RelativeLayout uLayout;
    protected static String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ScrollView scView = new ScrollView(this);

        uLayout = new RelativeLayout(this);
        uLayout.setId(R.id.innerRelativeLayout);

        // Adding movies from database as upcoming movies.
        addMovieButton(DataBase.getMovieNames(1));

        scView.addView(uLayout);
        setContentView(scView);

    }

    public void addMovieButton(ResultSet rs) {
        int rowCount = 0;
        Button upcomingButton;
        RelativeLayout.LayoutParams detailsUp;

        try {
            rs.last();
            rowCount = rs.getRow(); // last row's index is rowCount
            rs.beforeFirst();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If count is bigger than 0, it will add buttons.
        if (rowCount > 0) {
            detailsUp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            detailsUp.addRule(RelativeLayout.CENTER_HORIZONTAL);

            // Setting the button properties.
            upcomingButton = new Button(this);
            upcomingButton.setId(0);
            upcomingButton.setTransformationMethod(null);
            upcomingButton.setOnClickListener(this);
            detailsUp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            detailsUp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            detailsUp.addRule(RelativeLayout.BELOW, 0);
            uLayout.addView(upcomingButton, detailsUp);

            // After adding the first one, program adds others in this loop.
            for (int i = 1; i < rowCount+1; i++) {
                upcomingButton = new Button(this);

                try {
                    rs.next();
                    upcomingButton.setText(rs.getString(1));

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // Adding button properties.
                upcomingButton.setId(i);
                upcomingButton.setTransformationMethod(null);
                upcomingButton.setOnClickListener(this);
                detailsUp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                detailsUp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                if (i >= 0)
                    detailsUp.addRule(RelativeLayout.BELOW, i - 1);
                uLayout.addView(upcomingButton, detailsUp);

            }
        }
    }

    @Override
    // Connecting this class with movie activity to show the details.
    public void onClick(View view) {
        Button button = (Button) this.findViewById(view.getId());
        url = DataBase.getMovieUrl(button.getText().toString(), 1);
        whereFrom = "upcoming";
        Intent i = new Intent(UpcomingActivity.this, MovieActivity.class);
        startActivity(i);

    }
}
