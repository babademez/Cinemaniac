package com.example.berke.databaseloginregister;

        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ProgressBar;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;
        import android.widget.TextView;

        import static android.R.id.message;

/**
 * Created by berke on 30.07.2017.
 */

public class AddMovie extends Fragment {

    private Button addMovieButton;
    private EditText movieUrlInput;
    private EditText movieNameInput;
    private RadioGroup radioGroup;
    private RadioButton onScreenButton;
    private RadioButton upComingButton;
    private TextView status;

    private int value;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.add_movie, container, false);
        addMovieButton = (Button) rootView.findViewById(R.id.addMovieButton);
        movieUrlInput = ( EditText)rootView.findViewById(R.id.addMovieUrlInput);
        movieNameInput = ( EditText)rootView.findViewById(R.id.addMovieName);
        radioGroup = ( RadioGroup)rootView.findViewById(R.id.movieRadioGroup);
        onScreenButton = (RadioButton)rootView.findViewById(R.id.onScreenButton);
        upComingButton = (RadioButton)rootView.findViewById(R.id.upComingButton);
        status =(TextView)rootView.findViewById(R.id.statusOfAddMoviePage);

        addMovieButton.setOnClickListener( new addMovieButtonListener());
        return rootView;
    }

    private class addMovieButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            AddMovieControl ctrl = new AddMovieControl();
            ctrl.execute("");
        }
    }

    public class AddMovieControl extends AsyncTask<String, String, String>
    {

        String url = movieUrlInput.getText().toString();
        String mName = movieNameInput.getText().toString();
        String msg;
        @Override

        protected  void onPreExecute()
        {
            status.setText("Please wait");
        }

        protected String doInBackground(String... strings) {


            if(DataBase.addMovieUrl( url, mName, whichOne()))
                msg = "Success";
            else
                msg ="Failed :/";
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            status.setText(msg);
            ManageMovies.getMovieList();
        }
    }


    public int whichOne()
    {
        int result = 0;
        if( onScreenButton.isChecked())
        {
            result = 0;
        }
        else if( upComingButton.isChecked())
        {
            result = 1;
        }
        return result;
    }

}
