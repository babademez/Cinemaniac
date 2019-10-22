package com.example.berke.databaseloginregister;

/**
 * Created by berke on 27.07.2017.
 */

public class UserSetting {

    private static int id = 0;
    private static String username = "";
    private static String password = "";
    private static String email = "";
    private static int isAdmin = 0;

    public static void setUserInfo( int i, String u, String p, String e, int a)
    {
        id = i;
        username = u;
        password = p;
        email = e;
        isAdmin = a;
    }

    public static String getUsername()
    {
        return username;
    }

    public static String getPassword()
    {
        return password;
    }

    public static int getId() {
        return id;
    }

    public static String getEmail() {
        return email;
    }

    public static int getIsAdmin() {
        return isAdmin;
    }
}
