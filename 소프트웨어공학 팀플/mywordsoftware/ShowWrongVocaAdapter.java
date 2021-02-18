package com.example.mywordsoftware;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

import static com.example.mywordsoftware.MainActivity.ID;

public class ShowWrongVocaAdapter extends BaseAdapter {
    private Context context;
    private List<WrongVoca> ShowWrongVoca;
    //private ShowVocaActivity parent;

    public ShowWrongVocaAdapter(Context context, List<WrongVoca> ShowWrongVoca) {
        this.context = context;
        this.ShowWrongVoca = ShowWrongVoca;
        //this.parent = parent;


    }

    @Override
    public int getCount() {
        return ShowWrongVoca.size();
    }

    @Override
    public Object getItem(int i) {
        return ShowWrongVoca.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context, R.layout.wrongvoca, null);
        TextView ENGLISHText = (TextView) v.findViewById(R.id.ENGLISHText);
        TextView KOREANText = (TextView) v.findViewById(R.id.KOREANText);
        TextView MEMOText = (TextView) v.findViewById(R.id.MEMOText);

        ENGLISHText.setText(ShowWrongVoca.get(i).getENGLISH());
        KOREANText.setText(ShowWrongVoca.get(i).getKOREAN());
        MEMOText.setText(ShowWrongVoca.get(i).getMEMO());

        v.setTag(ShowWrongVoca.get(i).getENGLISH());
        v.setTag(ShowWrongVoca.get(i).getKOREAN());
        Button deleteButton = (Button) v.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success)
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                AlertDialog dialog = builder.setMessage("단어가 삭제되었습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                ShowWrongVoca.remove(i);
                                notifyDataSetChanged();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                AlertDialog dialog = builder.setMessage("단어 삭제에 실패하였습니다.")
                                        .setNegativeButton("다시 시도", null)
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
                DeleteWrongVocaRequest deleteWrongVocaRequest = new DeleteWrongVocaRequest(ID,ShowWrongVoca.get(i).getENGLISH()+ "", responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(deleteWrongVocaRequest);
            }
        });

        Button repeatButton = (Button) v.findViewById(R.id.repeatButton);
        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, RepeatActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                );

                Intent intent2 = new Intent(context.getApplicationContext(), RepeatActivity.class);
                intent2.putExtra("ENGLISHText", ShowWrongVoca.get(i).ENGLISH);
                intent2.putExtra("KOREANText", ShowWrongVoca.get(i).KOREAN);
                context.startActivity(intent2);

            }
        });
        return v;
    }
}
