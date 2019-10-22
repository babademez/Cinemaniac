package com.example.berke.databaseloginregister;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by berke on 30.07.2017.
 */

public class ManageMovies extends Fragment {

    public static TextView movieList;
    private EditText movieNameToRemove;
    private Button removeButton;
    private TextView status;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.manage_movies, container, false);

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        movieList = (TextView) rootView.findViewById(R.id.movieList);
        movieNameToRemove = (EditText) rootView.findViewById(R.id.removeMovieNameInput);
        removeButton = (Button) rootView.findViewById(R.id.removeMovieButton);
        removeButton.setOnClickListener(new addMovieButtonListener());
        status = (TextView) rootView.findViewById(R.id.statusOfRemovePage);
        getMovieList();

        return rootView;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        getMovieList();
    }

    private class addMovieButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            removeMovieControl ctrl = new removeMovieControl();
            ctrl.execute("");
        }
    }

    public class removeMovieControl extends AsyncTask<String, String, String> {

        String mName = movieNameToRemove.getText().toString();
        String msg;

        @Override

        protected void onPreExecute() {
            status.setText("Please wait");
        }

        protected String doInBackground(String... strings) {


            if (DataBase.removeMovie(mName))
                msg = "Success";
            else
                msg = "Failed :/";
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            status.setText(msg);
            getMovieList();
        }
    }

    public static void getMovieList() {
        try {
            ResultSet rs = DataBase.getMovieNames();
            int rowCount = 0;
            rs.last();
            rowCount = rs.getRow();
            rs.beforeFirst();
            movieList.setText("");
            for (int i = 0; i <= rowCount; i++) {
                rs.next();
                movieList.append(rs.getString(1) + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

