package com.example.berke.databaseloginregister;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import static com.example.berke.databaseloginregister.MenuActivity.whereFrom;

public class MovieActivity extends AppCompatActivity {

    protected static MovieHTMLParser mParser;
    private ImageView iw;
    private TextView tw1;
    private TextView tw2;
    private TextView tw3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        // Connecting the activity_movie layout with this class.
        iw = (ImageView) findViewById(R.id.moviePoster);
        tw1 = (TextView) findViewById(R.id.movieInfo);
        tw2 = (TextView) findViewById(R.id.movieRating);
        tw3 = (TextView) findViewById(R.id.moviePlot);

        // These two lines are here because if they prevent an error about html parsing.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (whereFrom == "onscreen")
            showMovieInfo(OnScreenActivity.url);
        else if (whereFrom == "upcoming")
            showMovieInfo(UpcomingActivity.url);
    }

    // This method shows information about movie with taking its url.
    public void showMovieInfo(String url){
        // Creating an object for parsing.
        mParser = new MovieHTMLParser(url);

        // Getting the movie poster link and showing it as an image on the scren.
        try {

            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(mParser.getMoviePosterLink()).getContent());
            iw.setImageBitmap(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // There will be three textviews. First is some information about movie, second is ratings and the last one is plot.

        // Getting the needed information from methods of MovieHTMLParser class.
        tw1.setText(mParser.getMovieInfo());
        tw2.setText("IMDb Rating: " + mParser.getMovieIMDBRate() + " - METASCORE: " + mParser.getMovieMetacriticScore());
        tw3.setText(mParser.getMoviePlot());

        tw1.setTextSize(19);
        tw2.setTextSize(17);
        tw3.setTextSize(15);
    }


    // This method sets the returning button on upper left corner available.
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
