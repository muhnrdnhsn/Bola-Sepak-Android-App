package com.example.bolasepak.model;

import android.content.Context;
import android.provider.Contacts;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bolasepak.activity.adapter.GoalRecyclerAdapter;
import com.example.bolasepak.activity.adapter.MatchRecyclerAdapter;
import com.example.bolasepak.activity.adapter.ShotRecyclerAdapter;
import com.example.bolasepak.adapter.MainRecycleAdapter;
import com.example.bolasepak.database.AppDatabaseHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


public class MatchEventAPI {


    public GsonBuilder builder;
    public Gson gson;
    public List<MatchEvent> responseObjects;
    public RequestQueue queue;
    public AppDatabaseHelper dbhelp;
    public String HostName;

    public MatchEventAPI(Context context) {
        builder = new GsonBuilder();
        HostName = "http://134.209.97.218:5050";
        dbhelp = new AppDatabaseHelper(context);
        gson = builder.create();
        responseObjects = new ArrayList<>();
        queue = Volley.newRequestQueue(context);
    }

    public void getEventByEventID(String EventID, final RecyclerView mRecyclerView, final RecyclerView homeGoalRecyclerView, final RecyclerView awayGoalRecyclerView, final RecyclerView shotsRecyclerView){
        responseObjects.clear();

        String RawURL = HostName + "/api/v1/json/1/lookupevent.php?id=" + EventID;

        builder.setPrettyPrinting();

        Log.e("ENTER",RawURL);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, RawURL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                MatchEvent[] matchEvents = new MatchEvent[0];
                try {
                    matchEvents = gson.fromJson(response.getJSONArray("events").toString(), MatchEvent[].class);
                } catch (JSONException e) {
                    Log.e("ANTIPOG", "onResponse: " + e.toString() );
                }

                responseObjects = Arrays.asList(matchEvents);

                Log.e("OMEGAULTRAWACK", "getFutureEventsByTeamID: " + Integer.toString(responseObjects.size()));

                responseObjects.forEach(new Consumer<MatchEvent>() {
                    @Override
                    public void accept(MatchEvent n) {
                        n.Format();
                        Team home = dbhelp.getTeam(n.getIdHomeTeam());
                        Team away = dbhelp.getTeam(n.getIdAwayTeam());
                        n.setStrHomeBadge(home.getStrTeamBadge());
                        n.setStrAwayBadge(away.getStrTeamBadge());
                        dbhelp.insertEvent(n);
                    }
                });

                MatchRecyclerAdapter recyclerAdapterMatch = new MatchRecyclerAdapter(responseObjects);
                mRecyclerView.setAdapter(recyclerAdapterMatch);

                GoalRecyclerAdapter homeGoalAdapter = new GoalRecyclerAdapter(responseObjects.get(0).getListHomeGoalDetail());
                homeGoalRecyclerView.setAdapter(homeGoalAdapter);

                GoalRecyclerAdapter awayGoalAdapter = new GoalRecyclerAdapter(responseObjects.get(0).getListAwayGoalDetail());
                awayGoalRecyclerView.setAdapter(awayGoalAdapter);

                ShotRecyclerAdapter shotsAdapter = new ShotRecyclerAdapter(responseObjects);
                shotsRecyclerView.setAdapter(shotsAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Wack", "Error :" + error.toString());
            }
        });
        queue.add(jsonObjectRequest);

        Log.e("OMEGAWACK", "getFutureEventsByTeamID: " + responseObjects.toString());
//        return wack;
    }

    public void getFutureEventsByTeamID(String teamID, final RecyclerView mRecyclerView) {
        responseObjects.clear();

        String RawURL = HostName + "/api/v1/json/1/eventsnext.php?id=" + teamID;

        builder.setPrettyPrinting();

        Log.e("ENTER",RawURL);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, RawURL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("RESPONSOOOOOOOOOOOOOO", response.toString());

                MatchEvent[] matchEvents = new MatchEvent[0];
                try {
                    matchEvents = gson.fromJson(response.getJSONArray("events").toString(), MatchEvent[].class);
                } catch (JSONException e) {
                    Log.e("ANTIPOG", "onResponse: " + e.toString() );
                }

                responseObjects = Arrays.asList(matchEvents);

                Log.e("OMEGAULTRAWACK", "getFutureEventsByTeamID: " + Integer.toString(responseObjects.size()));

                responseObjects.forEach(new Consumer<MatchEvent>() {
                    @Override
                    public void accept(MatchEvent n) {
                        n.Format();
                        Team home = dbhelp.getTeam(n.getIdHomeTeam());
                        Team away = dbhelp.getTeam(n.getIdAwayTeam());
                        n.setStrHomeBadge(home.getStrTeamBadge());
                        n.setStrAwayBadge(away.getStrTeamBadge());
                        dbhelp.insertEvent(n);
                    }
                });

                
                MatchRecyclerAdapter recyclerAdapterMatch = new MatchRecyclerAdapter(responseObjects);
                mRecyclerView.setAdapter(recyclerAdapterMatch);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Wack", "Error :" + error.toString());
            }
        });
        queue.add(jsonObjectRequest);

        Log.e("OMEGAWACK", "getFutureEventsByTeamID: " + responseObjects.toString());
//        return wack;
    }

    public void getFutureEventsByTeamIDNotification(String teamID) {
        responseObjects.clear();

        String RawURL = HostName + "/api/v1/json/1/eventsnext.php?id=" + teamID;

        builder.setPrettyPrinting();

        Log.e("ENTER",RawURL);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, RawURL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                MatchEvent[] matchEvents = new MatchEvent[0];
                try {
                    matchEvents = gson.fromJson(response.getJSONArray("events").toString(), MatchEvent[].class);
                } catch (JSONException e) {
                    Log.e("ANTIPOG", "onResponse: " + e.toString() );
                }

                responseObjects = Arrays.asList(matchEvents);

                responseObjects.forEach(new Consumer<MatchEvent>() {
                    @Override
                    public void accept(MatchEvent n) {
                        n.Format();
                        Team home = dbhelp.getTeam(n.getIdHomeTeam());
                        Team away = dbhelp.getTeam(n.getIdAwayTeam());
                        n.setStrHomeBadge(home.getStrTeamBadge());
                        n.setStrAwayBadge(away.getStrTeamBadge());
                        dbhelp.insertEvent(n);
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Wack", "Error :" + error.toString());
            }
        });
        queue.add(jsonObjectRequest);
    }

    public void getPastEventsByTeamID(String teamID, final RecyclerView mRecyclerView) {
        responseObjects.clear();
        String RawURL = HostName + "/api/v1/json/1/eventslast.php?id=" + teamID;
        builder.setPrettyPrinting();
        Log.e("ENTER",RawURL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, RawURL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                MatchEvent[] matchEvents = new MatchEvent[0];
                try {
                    matchEvents = gson.fromJson(response.getJSONArray("results").toString(), MatchEvent[].class);
                } catch (JSONException e) {
                    Log.e("ANTIPOG", "onResponse: " + e.toString() );
                }

                responseObjects = Arrays.asList(matchEvents);

                Log.e("OMEGAULTRAWACK", "getPastEventsByTeamID: " + Integer.toString(responseObjects.size()));

                responseObjects.forEach(new Consumer<MatchEvent>() {
                    @Override
                    public void accept(MatchEvent n) {
                        n.Format();

                        Team home = dbhelp.getTeam(n.getIdHomeTeam());
                        Team away = dbhelp.getTeam(n.getIdAwayTeam());
                        if (home != null) {
                            n.setStrHomeBadge(home.getStrTeamBadge());
                        }
                        if (away != null) {
                            n.setStrAwayBadge(away.getStrTeamBadge());
                        }
                        
                        dbhelp.insertEvent(n);
                    }
                });


                MatchRecyclerAdapter recyclerAdapterMatch = new MatchRecyclerAdapter(responseObjects);
                mRecyclerView.setAdapter(recyclerAdapterMatch);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Wack", "Error :" + error.toString());
            }
        });
        queue.add(jsonObjectRequest);

        Log.e("OMEGAWACK", "getFutureEventsByTeamID: " + responseObjects.toString());
//        return wack;
    }

    public void getEventsByName(String query, final RecyclerView mRecyclerView){


        String RawURL = HostName + "/api/v1/json/1/searchevents.php?e=" + query;

        builder.setPrettyPrinting();

        Log.e("ENTER",RawURL);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, RawURL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                MatchEvent[] matchEvents = new MatchEvent[0];
                try {
                    matchEvents = gson.fromJson(response.getJSONArray("events").toString(), MatchEvent[].class);
                } catch (JSONException e) {
                    Log.e("ANTIPOG", "onResponse: " + e.toString() );
                }

                responseObjects = Arrays.asList(matchEvents);

                Log.e("OMEGAULTRAWACK", "getFutureEventsByTeamID: " + Integer.toString(responseObjects.size()));

                responseObjects.forEach(new Consumer<MatchEvent>() {
                    @Override
                    public void accept(MatchEvent n) {
                        n.Format();
                        Team home = dbhelp.getTeam(n.getIdHomeTeam());
                        Team away = dbhelp.getTeam(n.getIdAwayTeam());
                        n.setStrHomeBadge(home.getStrTeamBadge());
                        n.setStrAwayBadge(away.getStrTeamBadge());
                        dbhelp.insertEvent(n);
                    }
                });

                MainRecycleAdapter recyclerAdapter = new MainRecycleAdapter(responseObjects);
                mRecyclerView.setAdapter(recyclerAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Wack", "Error :" + error.toString());
            }
        });
        queue.add(jsonObjectRequest);

        Log.e("OMEGAWACK", "getFutureEventsByTeamID: " + responseObjects.toString());
//        return wack;

    }

    public void getFutureEventsByLeagueID(String leagueID, final RecyclerView mRecyclerView, final List<MatchEvent> callback){
        responseObjects.clear();
        String RawURL = HostName + "/api/v1/json/1/eventsnextleague.php?id=" + leagueID;
        builder.setPrettyPrinting();

        Log.e("ENTER",RawURL);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, RawURL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                MatchEvent[] matchEvents = new MatchEvent[0];
                try {
                    matchEvents = gson.fromJson(response.getJSONArray("events").toString(), MatchEvent[].class);
                } catch (JSONException e) {
                    Log.e("ANTIPOG", "onResponse: " + e.toString() );
                }

                responseObjects = Arrays.asList(matchEvents);

                Log.e("OMEGAULTRAWACK", "getFutureEventsByTeamID: " + Integer.toString(responseObjects.size()));

                responseObjects.forEach(new Consumer<MatchEvent>() {
                    @Override
                    public void accept(MatchEvent n) {
                        n.Format();
                        Team home = dbhelp.getTeam(n.getIdHomeTeam());
                        Team away = dbhelp.getTeam(n.getIdAwayTeam());
                        n.setStrHomeBadge(home.getStrTeamBadge());
                        n.setStrAwayBadge(away.getStrTeamBadge());
                        dbhelp.insertEvent(n);
                        callback.add(n);
                    }
                });

                MainRecycleAdapter recyclerAdapter = new MainRecycleAdapter(responseObjects);
                mRecyclerView.setAdapter(recyclerAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Wack", "Error :" + error.toString());
            }
        });
        queue.add(jsonObjectRequest);

        Log.e("OMEGAWACK", "getFutureEventsByTeamID: " + responseObjects.toString());
//        return wack;

    }
}
