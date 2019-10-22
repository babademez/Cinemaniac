package com.example.berke.databaseloginregister;

import android.provider.Settings;
import android.util.Log;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Ekin Berk Ekinci on 26.07.2017.
 */

public class DataBase {

    private static final String DATABASE_URL = "jdbc:mysql://139.179.103.72/log_in";
    private static final String USER = "Admin";
    private static final String PASS = "1234";

    private String name;
    private String email;
    private String password;
    private static Connection con;
    private int adminNumber;


    public DataBase(String name, String email, String password) {
        adminNumber = 1;
        this.name = name;
        this.email = email;
        this.password = password;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DATABASE_URL, USER, PASS);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public DataBase(String name, String password) {
        adminNumber = 1;
        this.name = name;
        this.password = password;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DATABASE_URL, USER, PASS);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean registerUser() {
        if (doesUsernameExist())
            return false;
        else {
            boolean msg = false;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(DATABASE_URL, USER, PASS);
                if (con == null) {
                    msg = false;
                } else {
                    String query = "INSERT INTO cinemaniac_user_info (`id`, `username`, `passwrd`, `email`) VALUES ( NULL, '" + name + "' , '" + password + "' , '" + email + "')";
                    Statement stmt = con.createStatement();
                    stmt.execute(query);
                    msg = true;
                }
            } catch (Exception var2) {
                System.out.println(var2);
            }
            System.out.print(con);
            return msg;
        }
    }

    public boolean userLogin() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from `cinemaniac_user_info` WHERE username = '" + name + "'" + " and passwrd = '" + password + "'");


            if (rs.next()) {
                UserSetting.setUserInfo(rs.getInt("id"), rs.getString("username"), rs.getString("passwrd"), rs.getString("email"), rs.getInt("isAdmin"));
                return true;
            }
            return false;
        } catch (Exception var4) {
            System.out.println(var4 + " cause:" + var4.getCause());
            return false;
        }
    }

    public boolean doesUsernameExist() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT username from `cinemaniac_user_info` WHERE username = '" + name + "'");
            if (rs.next())
                return true;

            else
                return false;
        } catch (Exception var4) {
            System.out.println(var4 + " cause:" + var4.getCause());
            return false;

        }
    }

    public static String getMovieUrl(String movieName, int onscreenOrupcoming) {

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from movie_urls WHERE movie_name = '" + movieName + "'");
            if (rs.next()) {
                try {
                    Statement stmts = con.createStatement();
                    ResultSet rse = stmts.executeQuery("SELECT movie_url from `movie_urls` WHERE movie_name = '" + movieName + "' and onscreen_or_upcoming = " + onscreenOrupcoming);
                    if (rse.next())
                        return rse.getString("movie_url");

                    else
                        return null;
                } catch (Exception var4) {
                    System.out.println(var4 + " cause:" + var4.getCause());
                    return null;
                }
            }
            else{
                return "Movie does not exist";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet getMovieNames(int onScreenOrUpcoming)
    {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT movie_name  from `movie_urls` WHERE onscreen_or_upcoming=" + onScreenOrUpcoming );
            return rs;
        } catch (Exception var4) {
            Log.i("GETMOVIENAMES ", var4 + " cause:" + var4.getCause());
            System.out.println(var4 + " cause:" + var4.getCause());
            return null;
        }
    }

    public static ResultSet getMovieNames()
    {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT movie_name  from `movie_urls`");
            return rs;
        } catch (Exception var4) {
            Log.i("GETMOVIENAMES ", var4 + " cause:" + var4.getCause());
            System.out.println(var4 + " cause:" + var4.getCause());
            return null;
        }
    }


    public static boolean addMovieUrl(String url, String mName, int onScreenOrUpcoming) {
        if (doesMovieOnScreenExist(mName))
            return false;
        else {
            boolean msg = false;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(DATABASE_URL, USER, PASS);
                if (con == null) {
                    msg = false;
                } else {
                    String query = "INSERT INTO movie_urls ( `movie_url`, `movie_name`, onscreen_or_upcoming) VALUES (  '" + url + "' , '" + mName + "', '" + onScreenOrUpcoming + "')";
                    Statement stmt = con.createStatement();
                    stmt.execute(query);
                    msg = true;
                }
            } catch (Exception var2) {
                System.out.println(var2);
            }
            System.out.print(con);
            return msg;
        }
    }

    public static boolean removeMovie(String mName) {
        if (doesMovieOnScreenExist(mName))
            return false;
        else {
            boolean msg = false;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(DATABASE_URL, USER, PASS);
                if (con == null) {
                    msg = false;
                } else {
                    String query = "DELETE FROM movie_urls WHERE movie_name = \"" + mName + "\"";
                    Statement stmt = con.createStatement();
                    stmt.execute(query);
                    msg = true;
                }
            } catch (Exception var2) {
                System.out.println(var2);
            }
            System.out.print(con);
            return msg;
        }
    }

    public static boolean doesMovieOnScreenExist(String name) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT movie_name from `movie_urls` WHERE movie_url = '" + name + "'");
            if (rs.next())
                return true;

            else
                return false;
        } catch (Exception var4) {
            System.out.println(var4 + " cause:" + var4.getCause());
            return false;

        }
    }
}
