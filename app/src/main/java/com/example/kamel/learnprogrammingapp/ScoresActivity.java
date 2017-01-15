package com.example.kamel.learnprogrammingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ScoresActivity extends AppCompatActivity {

    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String scoresJson = sharedPreferences.getString("scoresJson", "");
        ArrayList<Result>  results = new ArrayList<Result>();
        results.add(new Result(getString(R.string.result), getString(R.string.course), getString(R.string.username)));
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
                String result = oneObject.getString("result");
                String username = oneObject.getString("username");
                JSONObject innerJsonObject = new JSONObject(oneObject.getString("course"));
                String courseName = innerJsonObject.getString("title");
                results.add(new Result(result,courseName,username));
            } catch (JSONException e) {
                // Oops
            }
        }
        mListView = (ListView) findViewById(R.id.scores_list_view);
        ResultAdapter adapter = new ResultAdapter(this, results);
        mListView.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ScoresActivity.this, MainMenuActivity.class);
        startActivity(intent);
        finish();
    }
}
