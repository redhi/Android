package com.example.mywordsoftware;

import android.content.Intent;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TestActivity2 extends AppCompatActivity {

    private String ENGLISH;
    private String KOREAN;
    private String ID;
    private AlertDialog dialog;
    private int length;
    private ListView ShowVocaView;
    private TestAdapter adapter;
    private List<TestVoca> ShowVoca;
    private int i= 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test2);
        ShowVocaView = (ListView) findViewById(R.id.ShowVocaView);
        ShowVoca = new ArrayList<TestVoca>();

        final EditText inputEnglishText = (EditText) findViewById(R.id.inputEnglishText);
        final TextView KOREANText = (TextView) findViewById(R.id.KOREANText);

        final Button completeButton = (Button) findViewById(R.id.completeButton);

        Intent intent = getIntent();
        ENGLISH = getIntent().getStringExtra("ENGLISH");
        KOREAN = getIntent().getStringExtra("KOREAN");
        length = intent.getIntExtra("length", 0);

        KOREANText.setText(intent.getStringExtra("KOREAN"));


        completeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                i++;
                if (i < length)
                {
                    final String ENGLISHTEXT = inputEnglishText.getText().toString();
                    final String KOREANTEXT = KOREANText.getText().toString();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try
                            {

                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if(success)
                                {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity2.this);
                                    AlertDialog dialog = builder.setMessage("정답입니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                    Intent intent = new Intent(TestActivity2.this, TestActivity2.class);
                                    intent.putExtra("ENGLISHText", ShowVoca.get(i).ENGLISH);
                                    intent.putExtra("KOREANText", ShowVoca.get(i).KOREAN);
                                    intent.putExtra("ENGLISH", ENGLISHTEXT);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity2.this);
                                    AlertDialog dialog = builder.setMessage("오답입니다.")
                                            .setNegativeButton("확인", null)
                                            .create();
                                    dialog.show();
                                    Intent intent = new Intent(TestActivity2.this, TestActivity2.class);
                                    intent.putExtra("ENGLISHText", ShowVoca.get(i).ENGLISH);
                                    intent.putExtra("KOREANText", ShowVoca.get(i).KOREAN);
                                    intent.putExtra("ENGLISH", ENGLISHTEXT);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    };
                    TestRequest testRequest = new TestRequest(ENGLISHTEXT, KOREANTEXT,MainActivity.ID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(TestActivity2.this);
                    queue.add(testRequest);
                }
                else
                {
                    Intent intent1 = new Intent(TestActivity2.this, MainActivity.class);
                    startActivity(intent1);
                }
            }
        });

    }
}
