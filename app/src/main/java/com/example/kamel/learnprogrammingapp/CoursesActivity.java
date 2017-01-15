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
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class CoursesActivity extends AppCompatActivity {

    private int selectedCourse = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String scoresJson = sharedPreferences.getString("coursesJson", "");
        JSONArray jArray = null;
        try {
            jArray = new JSONArray(scoresJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i=0; i < jArray.length(); i++)
        {
            try {
                JSONObject oneObject = jArray.getJSONObject(i);
                String title = oneObject.getString("title");
                final String id = oneObject.getString("id");
                LinearLayout row2 = (LinearLayout) findViewById(R.id.coursesList);
                Button ivBowl = new Button(this);
                ivBowl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new WebServiceHandler()
                                .execute("http://androidwebservice.azurewebsites.net/api/course/" + id);
                    }
                });
                ivBowl.setText(title);
                LinearLayout.LayoutParams layoutParams = new  LinearLayout.LayoutParams(700,200);
                if(i== 0) {
                    layoutParams.setMargins(0, 200, 0, 0); // left, top, right, bottom
                }
                else{
                    layoutParams.setMargins(0, 0, 0, 0); // left, top, right, bottom
                }

                ivBowl.setLayoutParams(layoutParams);

                row2.addView(ivBowl);
            } catch (JSONException e) {
                // Oops
            }
        }
    }

    class WebServiceHandler extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog = new ProgressDialog(CoursesActivity.this);

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
                editor.putString("moviesJson", result);
                editor.commit();
                Intent intent = new Intent(CoursesActivity.this, MoviesActivity.class);
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
