package com.example.mywordsoftware;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class AddWrongVocaActivity extends AppCompatActivity {
    private String ENGLISH;
    private String KOREAN;
    private String MEMO;
    private String ID;
    private AlertDialog dialog;
    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final EditText ENGLISHText = (EditText) findViewById(R.id.ENGLISHText);
        final EditText KOREANText = (EditText) findViewById(R.id.KOREANText);
        final EditText MEMOText = (EditText) findViewById(R.id.MEMOText);
        final Button validateButton = (Button) findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ENGLISH = ENGLISHText.getText().toString();
                if(validate)
                {
                    return;
                }
                if(ENGLISH.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddWrongVocaActivity.this);
                    dialog = builder.setMessage("영단어는 빈 칸 일 수 없습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddWrongVocaActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 영단어입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                ENGLISHText.setEnabled(false);
                                validate = true;
                                ENGLISHText.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                                validateButton.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddWrongVocaActivity.this);
                                dialog = builder.setMessage("사용할 수 없는 영단어입니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                AddWrongValidateRequest addwrongvalidateRequest = new AddWrongValidateRequest(ENGLISH, MainActivity.ID,responseListener);
                RequestQueue queue = Volley.newRequestQueue(AddWrongVocaActivity.this);
                queue.add(addwrongvalidateRequest);
            }
        });

        final Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ENGLISH = ENGLISHText.getText().toString();
                String KOREAN = KOREANText.getText().toString();
                String MEMO = MEMOText.getText().toString();

                if(!validate)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddWrongVocaActivity.this);
                    dialog = builder.setMessage("먼저 중복 체크를 해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                if(ENGLISH.equals("") || KOREAN.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddWrongVocaActivity.this);
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddWrongVocaActivity.this);
                                dialog = builder.setMessage("단어 등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                Intent intent = new Intent(AddWrongVocaActivity.this, ShowWrongVocaActivity.class);
                                startActivity(intent);
                                dialog.show();
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddWrongVocaActivity.this);
                                dialog = builder.setMessage("단어 등록에 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                };
                AddWrongVocaRequest addwrongvocaRequest = new AddWrongVocaRequest(ENGLISH, KOREAN, MEMO, MainActivity.ID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AddWrongVocaActivity.this);
                queue.add(addwrongvocaRequest);
            }
        });
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        if(dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }
}

