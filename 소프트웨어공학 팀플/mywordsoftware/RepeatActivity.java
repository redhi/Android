package com.example.mywordsoftware;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RepeatActivity extends AppCompatActivity {
    private String RepeatText1;
    private String RepeatText2;
    private String RepeatText3;
    private String RepeatText4;
    private String RepeatText5;
    private int i=0;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repeatvoca);

        final EditText ENGLISHText = (EditText) findViewById(R.id.ENGLISHText);
        final TextView KOREANText = (TextView) findViewById(R.id.KOREANText);
        final Button repeatButton = (Button) findViewById(R.id.repeatButton);
        final EditText repeatText1  = (EditText) findViewById(R.id.repeatText1);
        final EditText repeatText2  = (EditText) findViewById(R.id.repeatText2);
        final EditText repeatText3  = (EditText) findViewById(R.id.repeatText3);
        final EditText repeatText4  = (EditText) findViewById(R.id.repeatText4);
        final EditText repeatText5  = (EditText) findViewById(R.id.repeatText5);

        final Intent intent = getIntent();
        //ENGLISHText.setText(intent.getStringExtra("ENGLISHText"));
        KOREANText.setText(intent.getStringExtra("KOREANText"));

        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String RepeatText2 = repeatText2.getText().toString();
                String RepeatText1 = repeatText1.getText().toString();
                String RepeatText3 = repeatText3.getText().toString();
                String RepeatText4 = repeatText4.getText().toString();
                String RepeatText5 = repeatText5.getText().toString();
                if(repeatText1.equals("") || repeatText2.equals("") || repeatText3.equals("") || repeatText4.equals("") || repeatText5.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RepeatActivity.this);
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }
                else {
                    Intent intent2 = new Intent(RepeatActivity.this, MainActivity.class);
                    startActivity(intent2);
                }
            }

        });

    }
}