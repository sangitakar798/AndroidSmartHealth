package com.example.smarthealth;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class APIHandler {
    private static final String BASE_URL = "https://smarthealthcare-production.up.railway.app";

    private Context context;
    private RequestQueue requestQueue;

    public APIHandler(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void predictDisease(String[] symptoms, final VolleyCallback callback) {
        String url = BASE_URL + "/predict";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String disease = jsonObject.getString("Disease");
                            callback.onSuccess(disease);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onError();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                for (int i = 0; i < symptoms.length; i++) {
                    params.put("Symptom_" + (i + 1), symptoms[i]);
                }
                return params;
            }
        };

        requestQueue.add(request);
    }

    public interface VolleyCallback {
        void onSuccess(String disease);

        void onError();
    }
}
