package com.example.berke.databaseloginregister;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class RegisterActivity extends AppCompatActivity {

    private EditText username, email, password;
    private Button registerButton, backToLoginButton;
    private TextView message;
    DataBase userData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = (Button) findViewById(R.id.registerButton);
        backToLoginButton = (Button) findViewById(R.id.linkToLoginPageButton);
        username = (EditText) findViewById(R.id.regUsernameInput);
        email = (EditText) findViewById(R.id.regEmailInput);
        password = (EditText)findViewById(R.id.regPasswordInput);
        message = (TextView)findViewById(R.id.statusOfRegisterPage) ;
        registerButton.setOnClickListener( new RegisterButtonListener());
        backToLoginButton.setOnClickListener(new GoBackToLoginButtonListener());
    }


    private class RegisterButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            RegisterControl ctrl = new RegisterControl();
            ctrl.execute("");
        }
    }

    public class RegisterControl extends AsyncTask<String, String, String>
    {

        String usrnm = username.getText().toString();
        String eml = email.getText().toString();
        String pass = password.getText().toString();
        String msg;
        boolean a;
        @Override

        protected  void onPreExecute()
        {
            message.setText("Please wait");
        }

        protected String doInBackground(String... strings) {
            userData = new DataBase( usrnm, eml, pass);
            if(userData.registerUser())
                msg = "Success";
            else
                msg ="Failed :/";
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            message.setText(msg);
        }
    }

    private class GoBackToLoginButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            startActivity( new Intent( RegisterActivity.this, LoginActivity.class));
        }
    }
}
