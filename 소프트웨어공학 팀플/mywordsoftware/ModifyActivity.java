package com.example.mywordsoftware;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ModifyActivity extends AppCompatActivity {

    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify);


        final TextView ENGLISHText = (TextView) findViewById(R.id.ENGLISHText);
        final TextView KOREANText = (TextView) findViewById(R.id.KOREANText);
        final EditText MEMOText = (EditText) findViewById(R.id.MEMOText);
        final Button modifycompleteButton = (Button) findViewById(R.id.modifycompleteButton);

        Intent intent = getIntent();
        ENGLISHText.setText(intent.getStringExtra("ENGLISHText"));
        KOREANText.setText(intent.getStringExtra("KOREANText"));
        MEMOText.setText(intent.getStringExtra("MEMOText"));

        modifycompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String MEMOTEXT = MEMOText.getText().toString();
                String ENGLISHTEXT = ENGLISHText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyActivity.this);
                                AlertDialog dialog = builder.setMessage("수정 되었습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                Intent intent = new Intent(ModifyActivity.this, ShowVocaActivity.class);
                                startActivity(intent);
                                dialog.show();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyActivity.this);
                                AlertDialog dialog = builder.setMessage("수정하지 못 했습니다.")
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
                ModifyRequest modifyRequest = new ModifyRequest(MEMOTEXT,ENGLISHTEXT,responseListener);
                RequestQueue queue = Volley.newRequestQueue(ModifyActivity.this);
                queue.add(modifyRequest);
            }
        });


    }

}
