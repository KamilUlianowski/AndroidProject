package com.example.kamel.learnprogrammingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void goToMainMenu(View view){
        new WebServiceHandler()
                .execute("http://androidwebservice.azurewebsites.net/api/courses");
    }

    class WebServiceHandler extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog = new ProgressDialog(LoginActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage(getString(R.string.wait));
            dialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {

            try {
                URL url = new URL(urls[0]);
                URLConnection connection = url.openConnection();

                InputStream in = new BufferedInputStream(
                        connection.getInputStream());

                return streamToString(in);

            } catch (Exception e) {
                Log.d(LoginActivity.class.getSimpleName(), e.toString());
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {

            dialog.dismiss();
            try {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("scoresJson", result);
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish();


            } catch (Exception e) {
                Log.d(LoginActivity.class.getSimpleName(), e.toString());
            }
        }
    }

    public static String streamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try {

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            reader.close();

        } catch (IOException e) {
            Log.d(LoginActivity.class.getSimpleName(), e.toString());
        }

        return stringBuilder.toString();
    }
}
