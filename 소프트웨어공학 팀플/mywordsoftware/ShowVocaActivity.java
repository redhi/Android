package com.example.mywordsoftware;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

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

public class
ShowVocaActivity extends AppCompatActivity {
    private AlertDialog dialog;

    private ListView ShowVocaView;
    private ShowVocaAdapter adapter;
    private List<Voca> ShowVoca;
    Context context = this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_voca);
        ShowVocaView = (ListView) findViewById(R.id.ShowVocaView);
        ShowVoca = new ArrayList<Voca>();
        adapter = new ShowVocaAdapter(context, ShowVoca);
        ShowVocaView.setAdapter(adapter);

        final EditText searchText = (EditText) findViewById(R.id.searchText);
        final Button searchButton = (Button) findViewById(R.id.searchButton);
        final Button addButton = (Button) findViewById(R.id.addButton);
        final Button modifyButton = (Button) findViewById(R.id.modifyButton);
        final Button deleteButton = (Button) findViewById(R.id.deleteButton);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ENGLISH = searchText.getText().toString();
                if(ENGLISH.length()>0) {
                    Intent intent = new Intent(ShowVocaActivity.this, SearchActivity.class);
                    intent.putExtra("ENGLISH", ENGLISH);
                    startActivity(intent);
                    finish();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowVocaActivity.this);
                    dialog = builder.setMessage("검색값을 입력하세요.")
                            .setNegativeButton("다시 시도", null)
                            .create();
                    dialog.show();
                }
               };


        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(ShowVocaActivity.this, AddVocaActivity.class);
                    startActivity(intent);
                    finish();

            };


        });

        new BackgroundTask().execute();


    }
    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;
        protected void onPreExecute() {
            try {
                target = "http://jylisa0612.cafe24.com/ShowVoca.php?ID=" + URLEncoder.encode(MainActivity.ID, "UTF-8");
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
                String ENGLISH, KOREAN, MEMO;
                while(count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    ENGLISH = object.getString("ENGLISH");
                    KOREAN = object.getString("KOREAN");
                    MEMO = object.getString("MEMO");
                    Voca voca = new Voca(ENGLISH,KOREAN, MEMO);
                    ShowVoca.add(voca);
                    count++;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
