package com.example.mywordsoftware;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

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

public class TestActivity extends AppCompatActivity {
    private ListView TestVocaView;
    private TestAdapter adapter;
    private List<TestVoca> ShowVoca;
    Context context = this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        TestVocaView = (ListView) findViewById(R.id.TestVocaView);
        ShowVoca = new ArrayList<TestVoca>();
        adapter = new TestAdapter(context, ShowVoca);
        TestVocaView.setAdapter(adapter);

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
                while(count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    ENGLISH = object.getString("ENGLISH");
                    KOREAN = object.getString("KOREAN");
                    TestVoca testVoca = new TestVoca(ENGLISH,KOREAN);
                    ShowVoca.add(testVoca);
                    count++;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private long lastTimeBackPressed;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}