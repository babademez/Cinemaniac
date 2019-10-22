package com.example.berke.databaseloginregister;

/**
 * Created by Berk Mehmet Gürlek on 28.07.2017.
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * This Class is a parser for the movie theater webpages
 * @author Berk Mehmet Gurlek & Omer Faruk Babademez
 * @version 1.1.0
 */
public class HTMLMovieTheaterParser {

    // Properties of a movie theater.
    private String theaterName;
    private List<String[]> moviesSessions = new ArrayList<String[]>();
    private List<String> moviesOnScreen = new ArrayList<String>();
    private String theaterAdress;
    private String url;
    /**
     * This constructor not only constructs the MovieTheatherHTMLParser but also translates the movie names from sinemalar.com to English
     * @param Url
     */
    public HTMLMovieTheaterParser (String Url)
    {
        url = Url;
        Document doc = null;

        try {
            doc = Jsoup.connect(url).get(); // Connects with the url which is a requirement of JSoup.
        } catch (IOException e) {
            e.printStackTrace();
        }


        try
        {
            // Parsing the name of movie theater.
            Element name = doc.select("h1.fl").first();
            theaterName = name.text();
            //Parsing the adress of movie theater.
            Element adress = doc.getElementsByClass("grid8").first().getElementsByClass("bestof-detail ").first().select("p").get(2);
            theaterAdress = adress.text();
            // Parsing the movies on screen in that movie theater.
            Elements movies = doc.getElementsByClass("grid8").not(".hauto");
            for (Element temp: movies)
            {
                // Parsing the name of movies.
                String nameMovie = temp.getElementsByClass("bestof-detail ").select("h3").select("small").first().text();
                moviesOnScreen.add(nameMovie);
                // Parsing the session of movies.
                Elements sessions = temp.getElementsByClass("grid6").first().getElementsByClass("select-seans");

                // Putting the sessions in the new arrayç
                String[] arrSession = new String[sessions.size()];
                for (int i = 0; i < sessions.size(); i++)
                    arrSession[i] = sessions.get(i).text().trim().substring(0, 5);
                moviesSessions.add(arrSession);
            }
        }
        catch (Exception ex)
        {
        }
    }

    public int getMoviesOnScreenCount(){
        return getMoviesOnScreen().size();
    }

    public String getMovieTheaterName()
    {
        return theaterName;
    }

    public List<String> getMoviesOnScreen()
    {
        return moviesOnScreen;
    }

    public String getAdress()
    {
        return theaterAdress;
    }

    public List<String[]> getMovieSessions()
    {
        return moviesSessions;
    }

    public String getURL()
    {
        return url;
    }

    public String toString()
    {
        String result = "";
        for(int i = 0; i < getMovieSessions().size(); i++)
        {
            result = result + getMoviesOnScreen().get(i) + "\n";
            for(int j = 0; j <getMovieSessions().get(i).length; j++)
            {
                result = result + getMovieSessions().get(i)[j] + "\n";
            }
        }
        return result;
    }
}
