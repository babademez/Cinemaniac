package com.example.berke.databaseloginregister;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button loginButton, goToRegisterButton;
    private TextView message;
    DataBase userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.loginUsernameInput);
        password = ( EditText)findViewById(R.id.loginPasswordInput);
        loginButton = (Button)findViewById(R.id.loginButton);
        goToRegisterButton = (Button)findViewById(R.id.goToRegisterPageButton);
        message = (TextView)findViewById(R.id.statusOfLoginPage);

        loginButton.setOnClickListener( new loginButtonListener());
        goToRegisterButton.setOnClickListener(new goRegisterPageButtonListener ());
    }

    private class loginButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            LoginControl ctrl = new LoginControl();
            ctrl.execute("");

        }
    }

    public class LoginControl extends AsyncTask<String, String, String>
    {

        String usrnm = username.getText().toString();
        String pass = password.getText().toString();
        String msg;
        boolean a;
        @Override

        protected  void onPreExecute()
        {
            message.setText("Please wait");
        }
        protected String doInBackground(String... strings) {
            userData = new DataBase( usrnm, pass);
            if(userData.userLogin() ) {
                msg = "Success!";
                startActivity( new Intent( LoginActivity.this, AfterLoginActivity.class));
            }
            else
                msg = "Something is wrong :(";
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            message.setText(msg);
        }
    }


    private class goRegisterPageButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            startActivity( new Intent( LoginActivity.this, RegisterActivity.class));
        }
    }
}
