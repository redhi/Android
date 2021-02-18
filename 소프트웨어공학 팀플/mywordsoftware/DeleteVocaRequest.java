package com.example.mywordsoftware;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteVocaRequest extends StringRequest {
    final static private String URL = "http://jylisa0612.cafe24.com/DeleteVoca.php";
    private Map<String, String> parameters;

    public DeleteVocaRequest(String ID, String ENGLISH, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("ID", ID);
        parameters.put("ENGLISH", ENGLISH);
    }

    @Override
    public Map<String, String> getParams() { return parameters; }
}