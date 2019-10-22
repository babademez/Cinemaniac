package com.example.berke.databaseloginregister;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import static com.example.berke.databaseloginregister.MenuActivity.whereFrom;

/**
 * Created by BABADEMEZ on 31/07/2017.
 */

public class OnScreenActivity extends AppCompatActivity implements View.OnClickListener {
    protected static RelativeLayout oLayout;
    protected static String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ScrollView sView = new ScrollView(this);

        oLayout = new RelativeLayout(this);
        oLayout.setId(R.id.innerRelativeLayout);

        // Adding movies from database as movies on screen.
        addMovieButton(DataBase.getMovieNames(0));

        sView.addView(oLayout);
        setContentView(sView);

    }

    public void addMovieButton(ResultSet rs) {

        int rowCount = 0;
        Button movieButton;
        RelativeLayout.LayoutParams details;

        try {
            rs.last();
            rowCount = rs.getRow(); // last row's index is rowCount
            rs.beforeFirst();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If count is bigger than 0, it will add buttons.
        if (rowCount > 0) {
            details = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            details.addRule(RelativeLayout.CENTER_HORIZONTAL);

            // Setting the button properties.
            movieButton = new Button(this);
            movieButton.setId(0);
            movieButton.setTransformationMethod(null);
            movieButton.setOnClickListener(this);
            details = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            details.addRule(RelativeLayout.CENTER_HORIZONTAL);
            details.addRule(RelativeLayout.BELOW, 0);
            oLayout.addView(movieButton, details);

            // After adding the first one, program adds others in this loop.
            for (int i = 1; i < rowCount+1; i++) {
                movieButton = new Button(this);

                try {
                    rs.next();
                    movieButton.setText(rs.getString(1));

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // Adding button properties.
                movieButton.setId(i);
                movieButton.setTransformationMethod(null);
                movieButton.setOnClickListener(this);
                details = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                details.addRule(RelativeLayout.CENTER_HORIZONTAL);
                if (i >= 0)
                    details.addRule(RelativeLayout.BELOW, i - 1);
                oLayout.addView(movieButton, details);

            }
        }
    }

    @Override
    // Connecting this class with movie activity to show the details.
    public void onClick(View view) {
        Button button = (Button) this.findViewById(view.getId());
        if( DataBase.getMovieUrl(button.getText().toString(), 0) != null) {
            url = DataBase.getMovieUrl(button.getText().toString(), 0);
            whereFrom = "onscreen";
            Intent i = new Intent(OnScreenActivity.this, MovieActivity.class);
            startActivity(i);
        }
        else
            Toast.makeText(OnScreenActivity.this, button.getText().toString(), Toast.LENGTH_LONG).show();


    }
}