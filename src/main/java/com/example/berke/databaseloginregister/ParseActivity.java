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

public class ParseActivity extends AppCompatActivity implements View.OnClickListener {

    // Properties
    protected static RelativeLayout innerRelLayout;
    protected static HTMLMovieTheaterParser parser;
    protected static int clickedButtonId;
    private String urlToUse;

    private String bilkentCenter = "http://www.sinemalar.com/sinemasalonu/280/ankara-bilkent-prestige";
    private String taurus = "http://www.sinemalar.com/sinemasalonu/1936/ankara-taurus-cinemarine";
    private String arcadium = "http://www.sinemalar.com/sinemasalonu/279/ankara-arcadium";
    private String buyulufener = "http://www.sinemalar.com/sinemasalonu/284/ankara-buyulu-fener-kizilay";
    private String gordion = "http://www.sinemalar.com/sinemasalonu/1422/ankara-cinemaximum-gordion";
    private String armada = "http://www.sinemalar.com/sinemasalonu/1911/ankara-cinemaximum-armada";
    private String cepa = "http://www.sinemalar.com/sinemasalonu/1091/ankara-cinemaximum-cepa";
    private String kentpark = "http://www.sinemalar.com/sinemasalonu/1573/ankara-kentpark-prestige";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // These two lines are here because if they prevent an error about html parsing.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Adding scroll activity.
        ScrollView scrollView = new ScrollView(this);

        // Initializing the layoutr.
        innerRelLayout = new RelativeLayout(this);
        innerRelLayout.setId(R.id.innerRelativeLayout);

        whichTheaterUrl();
        if( urlToUse != null) {
            parser = new HTMLMovieTheaterParser(urlToUse);
            addButton(parser);
        }
        else
            Toast.makeText(ParseActivity.this, "We couldn't find the theater, sorry :/", Toast.LENGTH_LONG).show();


        scrollView.addView(innerRelLayout);
        setContentView(scrollView);
    }

    //This method adds buttons of the movies that are on screen on the movie theater.
    public void addButton(HTMLMovieTheaterParser htmlTheater) {
        Button cinemaButton;
        RelativeLayout.LayoutParams buttonDetails;

        // Adding the movies with creating buttons.
        for (int i = 0; i < htmlTheater.getMoviesOnScreenCount(); i++) {
            cinemaButton = new Button(this);
            cinemaButton.setText(htmlTheater.getMoviesOnScreen().get(i));
            cinemaButton.setId(i);
            cinemaButton.setTransformationMethod(null);
            cinemaButton.setOnClickListener(this);
            buttonDetails = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            buttonDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
            if (i != 0)
                buttonDetails.addRule(RelativeLayout.BELOW, i - 1);

            innerRelLayout.addView(cinemaButton, buttonDetails);
        }
    }

    @Override
    // This method connects this class with show sessions class.
    public void onClick(View v) {
        // Clicked button id gets the id of movie and help to show the sessions of it.
        clickedButtonId = v.getId();
        Intent i = new Intent(ParseActivity.this, ShowSessions.class);
        startActivity(i);
    }

    public String whichTheaterUrl() {
        if (UserLocation.theaterName.contains("Bilkent")) {
            urlToUse = bilkentCenter;
        }

        else if( UserLocation.theaterName.contains("Cepa")) {
            urlToUse = cepa;
        }

        else if( UserLocation.theaterName.contains("Arcadium")) {
            urlToUse = arcadium;
        }
        else if( UserLocation.theaterName.contains("Kentpark")) {
            urlToUse = kentpark;
        }
        else if( UserLocation.theaterName.contains("fener")) {
            urlToUse = buyulufener;
        }
        else if( UserLocation.theaterName.contains("Gordion")) {
            urlToUse = gordion;
        }
        else if( UserLocation.theaterName.contains("Taurus")) {
            urlToUse = taurus;
        }
        else if( UserLocation.theaterName.contains("Armada")) {
            urlToUse = armada;
        }
        return urlToUse;
    }
}
