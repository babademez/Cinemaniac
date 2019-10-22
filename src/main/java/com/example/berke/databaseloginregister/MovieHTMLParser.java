package com.example.berke.databaseloginregister;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
/**
 * This class basically a collector class for the informations of the searched movie
 * @author Berk Mehmet Gurlek & Omer Faruk Babademez
 * @version 1.1.0
 */
public class MovieHTMLParser {
	
	// Properties of movies.
	private int metascore;
	private double imdbScore;
	private String moviePlot;
	private String movieInfo;
	private String url;
	private String posterURL;
	
	// Constructor with parsing ability.
	public MovieHTMLParser(String url)
	{
		this.url = url;
		
	    Document doc = null;
	    
	    try {
			doc = Jsoup.connect(url).get(); // Connects with the url which is a requirement of JSoup.
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    // Parsing the metascore from the source code of IMDb source code.
		try
		{
			Element metacriticRating = doc.select("div.plot_summary_wrapper").select("div.titleReviewBarItem").first().select("span").first();
			metascore = Integer.parseInt(metacriticRating.text());
		}
		catch (Exception ex)
		{
			metascore = 0;
		}
		
		 // Parsing the IMDb rating from the source code of IMDb source code.
		try
		{
			Element imdbRating = doc.select("div.ratingValue").first().select("span[itemprop]").first();
			imdbScore = Double.parseDouble(imdbRating.text());
		}
		catch (Exception ex)
		{
			imdbScore = 0;
		}	
		
		 // Parsing the movies plot from the source code of IMDb source code.	
		try{
			Element mPlot = doc.select("div.summary_text").first();
			moviePlot = (mPlot.text());
		}
		catch(Exception ex)
		{
			moviePlot = "";
		}
		
		 // Parsing the movie name and some other information from the source code of IMDb source code.
		try {
			Element movieName = doc.select("div.title_wrapper").first();
			movieInfo = (movieName.text());
		}
		catch(Exception ex)
		{
			movieInfo = "";
		}
		
		 // Parsing the movie poster's url from the source code of IMDb source code.
		try {
			Element poster = doc.select("div.slate_wrapper").first().select("img").first();
			posterURL = (poster.absUrl("src"));
		}
		catch(Exception ex)
		{
			posterURL = "";
		}
		
	}
	
	public String getMovieInfo ()
	{
		return movieInfo;
	}
	
	public String getMoviePlot()
	{
		return moviePlot;
	}
	
	public String getMoviePosterLink()
	{
		return posterURL;
	}
	
	public double getMovieIMDBRate()
	{
		return imdbScore;
	}
	
	public int getMovieMetacriticScore()
	{
		return metascore;
	}
	
	public String toString()
	{
		return movieInfo + " " + imdbScore;
	}
}
