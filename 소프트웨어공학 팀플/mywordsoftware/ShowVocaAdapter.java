package com.example.mywordsoftware;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

import static com.example.mywordsoftware.MainActivity.ID;

public class ShowVocaAdapter extends BaseAdapter {
    private Context context;
    private List<Voca> ShowVoca;
    //private ShowVocaActivity parent;

    public ShowVocaAdapter(Context context, List<Voca> ShowVoca) {
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

        View v = View.inflate(context, R.layout.voca, null);
        TextView ENGLISHText = (TextView) v.findViewById(R.id.ENGLISHText);
        TextView KOREANText = (TextView) v.findViewById(R.id.KOREANText);
        TextView MEMOText = (TextView) v.findViewById(R.id.MEMOText);

        ENGLISHText.setText(ShowVoca.get(i).getENGLISH());
        KOREANText.setText(ShowVoca.get(i).getKOREAN());
        MEMOText.setText(ShowVoca.get(i).getMEMO());

        v.setTag(ShowVoca.get(i).getENGLISH());
        v.setTag(ShowVoca.get(i).getKOREAN());
        v.setTag(ShowVoca.get(i).getMEMO());

        final Button modifyButton = (Button) v.findViewById(R.id.modifyButton);
        Button deleteButton = (Button) v.findViewById(R.id.deleteButton);

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ModifyActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                );

                Intent intent2 = new Intent(context.getApplicationContext(), ModifyActivity.class);
                intent2.putExtra("ENGLISHText", ShowVoca.get(i).ENGLISH);
                intent2.putExtra("KOREANText", ShowVoca.get(i).KOREAN);
                intent2.putExtra("MEMOText", ShowVoca.get(i).MEMO);
                context.startActivity(intent2);


            }
        });
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

                                ShowVoca.remove(i);
                                notifyDataSetChanged();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                AlertDialog dialog = builder.setMessage("단어 삭제에 실패하였습니다.")
                                        //.setNegativeButton("다시 시도", null)
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
                DeleteVocaRequest deleteVocaRequest = new DeleteVocaRequest(ID,ShowVoca.get(i).getENGLISH()+ "", responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(deleteVocaRequest);
            }
        });
        return v;
    }

}
