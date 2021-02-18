package com.example.mywordsoftware;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

import static com.example.mywordsoftware.MainActivity.ID;

public class TestAdapter extends BaseAdapter {
    private Context context;
    private List<TestVoca> ShowVoca;
    //private TestActivity parent;

    public TestAdapter(Context context, List<TestVoca> ShowVoca) {
        this.context = context;
        this.ShowVoca = ShowVoca;
        //this.parent = parent;
    }

    @Override
    public int getCount() {
        return ShowVoca.size();
    }

    @Override
    public Object getItem(int i) {
        return ShowVoca.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context, R.layout.testword, null);
        final EditText ENGLISHText = (EditText) v.findViewById(R.id.ENGLISHText);
        final TextView KOREANText = (TextView) v.findViewById(R.id.KOREANText);

        //ENGLISHText.setText(ShowVoca.get(i).getENGLISH());
        KOREANText.setText(ShowVoca.get(i).getKOREAN());

        v.setTag(ShowVoca.get(i).getENGLISH());
        v.setTag(ShowVoca.get(i).getKOREAN());

        final Button submitButton = (Button) v.findViewById(R.id.submitButton);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ENGLISH = ENGLISHText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                AlertDialog dialog = builder.setMessage("정답입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                ENGLISHText.setFocusable(false);
                                ENGLISHText.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                                KOREANText.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                                submitButton.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                AlertDialog dialog = builder.setMessage("오답입니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                                ENGLISHText.setFocusable(false);
                                ENGLISHText.setBackgroundColor(context.getResources().getColor(R.color.colorWarning));
                                KOREANText.setBackgroundColor(context.getResources().getColor(R.color.colorWarning));
                                submitButton.setBackgroundColor(context.getResources().getColor(R.color.colorWarning));
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                TestRequest testRequest = new TestRequest(ENGLISH, ShowVoca.get(i).getKOREAN(), ID ,responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(testRequest);
            }
        });

        return v;
    }
}
