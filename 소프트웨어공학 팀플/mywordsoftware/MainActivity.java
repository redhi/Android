package com.example.mywordsoftware;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ID = getIntent().getStringExtra("ID");


        final Button vocaButton = (Button) findViewById(R.id.vocaButton);
        final Button wrongvocaButton = (Button) findViewById(R.id.wrongvocaButton);
        final Button testButton = (Button) findViewById(R.id.testButton);

        vocaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowVocaActivity.class);
                startActivity(intent);

            }
        });

        wrongvocaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowWrongVocaActivity.class);
                startActivity(intent);

            }
        });

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestPreviewActivity.class);
                startActivity(intent);
            }
        });



    }


}
