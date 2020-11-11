package com.example.bolasepak.model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bolasepak.database.AppDatabaseHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamAPI {
    public GsonBuilder builder;
    public Gson gson;
    public List<Team> responseObjects;
    public RequestQueue queue;
    public Context contextLocal;
    public AppDatabaseHelper dbhelp;

    public TeamAPI(Context context) {
        dbhelp = new AppDatabaseHelper(context);
        builder = new GsonBuilder();
        contextLocal = context;
        gson = builder.create();
        responseObjects = new ArrayList<>();
        queue = Volley.newRequestQueue(context);
    }

    public void getAllTeam(String RawURL) {
        responseObjects.clear();

        builder.setPrettyPrinting();
        Log.e("ENTER",RawURL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, RawURL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Log.e("ANTIPOG1", "onResponse: " );
                Team[] teams = new Team[0];
                try {
                    Log.e("ANTIPOG2", "onResponse: ");
                    teams = gson.fromJson(response.getJSONArray("teams").toString(), Team[].class);
                } catch (JSONException e) {
                    Log.e("ANTIPOG3", "onResponse: ");
                }

                responseObjects = Arrays.asList(teams);

                Log.e("BIGOMEGA", "BIGOMEGA: " + Integer.toString(responseObjects.size()));
                Log.e("BIGOMEGA", "BIGOMEGA: " + responseObjects.toString());

                responseObjects.forEach((n) ->{
                    n.setSubscribed(0);
                    dbhelp.insertTeam(n);
                    Log.e("YESIUBDKJASBDKJW", n.toString());
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Wack", "Error :" + error.toString());
            }
        });
        queue.add(jsonObjectRequest);
        Log.e("OMEGAWACK", "getFutureEventsByTeamID: " + responseObjects.toString());
    }

}
