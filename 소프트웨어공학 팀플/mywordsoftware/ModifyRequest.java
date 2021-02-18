package com.example.mywordsoftware;

import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ModifyRequest extends StringRequest {

    final static private String URL = "http://jylisa0612.cafe24.com/ModifyVoca.php";
    private Map<String,String> parameters;

    public ModifyRequest(String MEMO, String ENGLISH, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("MEMO", String.valueOf(MEMO));
        parameters.put("ENGLISH", String.valueOf(ENGLISH));
    }
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}