package com.example.mywordsoftware;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddValidateRequest extends StringRequest {
    final static private String URL = "http://jylisa0612.cafe24.com/AddVocaDuplicated.php";
    private Map<String,String> parameters;

    public AddValidateRequest(String ENGLISH, String ID, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("ENGLISH",ENGLISH);
        parameters.put("ID", MainActivity.ID);
    }
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
