package com.example.mywordsoftware;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestPreviewActivity extends AppCompatActivity {
    private ListView ShowVocaView;
    private TestAdapter adapter;
    private List<TestVoca> ShowVoca;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_test);
        ShowVocaView = (ListView) findViewById(R.id.ShowVocaView);
        ShowVoca = new ArrayList<TestVoca>();
        //adapter = new TestAdapter(getApplicationContext(), ShowVoca);
        //ShowVocaView.setAdapter(adapter);

        final Button startButton = (Button) findViewById(R.id.startButton);
        final Button backButton = (Button) findViewById(R.id.backButton);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestPreviewActivity.this, TestActivity.class);
                startActivity(intent);
                finish();
            }

        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestPreviewActivity.this, MainActivity.class);
                startActivity(intent);
            }

        });

        new BackgroundTask().execute();

    }
    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;
        protected void onPreExecute() {
            try {
                target = "http://jylisa0612.cafe24.com/TestPreview.php?ID=" + URLEncoder.encode(MainActivity.ID, "UTF-8");
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder  stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(temp +"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }
        public void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray =jsonObject.getJSONArray("response");
                int count = 0;
                String ENGLISH, KOREAN;
                int length;
                while(count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    ENGLISH = object.getString("ENGLISH");
                    KOREAN = object.getString("KOREAN");
                    length = jsonArray.length();
                    TestVoca testVoca = new TestVoca(ENGLISH,KOREAN);
                    ShowVoca.add(testVoca);
                    count++;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
