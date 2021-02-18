package com.example.mywordsoftware;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddWrongVocaRequest extends StringRequest {
    final static private String URL = "http://jylisa0612.cafe24.com/AddWrongVoca.php";
    private Map<String,String> parameters;

    public AddWrongVocaRequest(String ENGLISH, String KOREAN, String MEMO, String ID, Response.Listener<String> listener){
        super(Request.Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("ENGLISH", ENGLISH);
        parameters.put("KOREAN", KOREAN);
        parameters.put("MEMO", MEMO);
        parameters.put("ID", MainActivity.ID);
    }
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
