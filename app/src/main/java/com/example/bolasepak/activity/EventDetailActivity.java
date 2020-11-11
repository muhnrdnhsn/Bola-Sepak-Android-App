package com.example.bolasepak.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.example.bolasepak.activity.adapter.GoalRecyclerAdapter;
import com.example.bolasepak.R;
import com.example.bolasepak.activity.adapter.MatchRecyclerAdapter;
import com.example.bolasepak.activity.adapter.ShotRecyclerAdapter;
import com.example.bolasepak.database.AppDatabaseHelper;
import com.example.bolasepak.model.MatchEvent;
import com.example.bolasepak.model.MatchEventAPI;
import com.example.bolasepak.model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventDetailActivity extends AppCompatActivity {

    private static final String TAG = "TeamDetailActivity";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private RecyclerView homeGoalRecyclerView;
    private RecyclerView.Adapter homeGoalAdapter;
    private RecyclerView.LayoutManager homeGoalLayoutManager;

    private RecyclerView awayGoalRecyclerView;
    private RecyclerView.Adapter awayGoalAdapter;
    private RecyclerView.LayoutManager awayGoalLayoutManager;

    private RecyclerView shotsRecyclerView;
    private RecyclerView.Adapter shotsAdapter;
    private RecyclerView.LayoutManager shotsLayoutManager;

    public final static String LIST_STATE_KEY_MATCH = "recycler_list_state_match";
    public final static String LIST_STATE_KEY_SHOTS = "recycler_list_state_shots";
    public final static String LIST_STATE_KEY_HGOALS = "recycler_list_state_home_goal";
    public final static String LIST_STATE_KEY_AGOALS= "recycler_list_state_away_goal";
    public AppDatabaseHelper dbhelp;
    Parcelable listStateMatch;
    Parcelable listStateShots;
    Parcelable listStateHomeGoals;
    Parcelable listStateAwayGoals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail_recyclerview);

        Intent intent = getIntent();
        String message = intent.getStringExtra("event");

        Log.e(TAG, "SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSonCreate: " + message);

        dbhelp = new AppDatabaseHelper(getApplicationContext());

        mRecyclerView = (RecyclerView) findViewById(R.id.TeamDetailRecyclerView);
        mRecyclerView.setHasFixedSize(true);

        homeGoalRecyclerView = (RecyclerView) findViewById(R.id.home_goals_recyclerView);
        homeGoalRecyclerView.setHasFixedSize(true);

        awayGoalRecyclerView = (RecyclerView) findViewById(R.id.away_goals_recyclerView);
        awayGoalRecyclerView.setHasFixedSize(true);

        shotsRecyclerView = (RecyclerView) findViewById(R.id.shots_recyclerview);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        homeGoalLayoutManager = new LinearLayoutManager(this);
        homeGoalRecyclerView.setLayoutManager(homeGoalLayoutManager);

        awayGoalLayoutManager = new LinearLayoutManager(this);
        awayGoalRecyclerView.setLayoutManager(awayGoalLayoutManager);

        shotsLayoutManager = new LinearLayoutManager(this);
        shotsRecyclerView.setLayoutManager(shotsLayoutManager);

//        boolean isonline = isNetworkAvailable();
        boolean isonline = true;
        if (isonline){
            MatchEventAPI reqBuilder = new MatchEventAPI(getApplicationContext());
            reqBuilder.getEventByEventID(message, mRecyclerView, homeGoalRecyclerView, awayGoalRecyclerView, shotsRecyclerView);
        }else{
            List<MatchEvent> matchEvents = new ArrayList<MatchEvent>();

            matchEvents = dbhelp.getEventByEventID(Integer.parseInt(message));

            matchEvents.forEach(new Consumer<MatchEvent>() {
                @Override
                public void accept(MatchEvent n) {
                    n.Format();
                    Team home = dbhelp.getTeam(n.getIdHomeTeam());
                    Team away = dbhelp.getTeam(n.getIdAwayTeam());
                    n.setStrHomeBadge(home.getStrTeamBadge());
                    n.setStrAwayBadge(away.getStrTeamBadge());
                }
            });

            MatchRecyclerAdapter recyclerAdapterMatch = new MatchRecyclerAdapter(matchEvents);
            mRecyclerView.setAdapter(recyclerAdapterMatch);

            GoalRecyclerAdapter homeGoalAdapter = new GoalRecyclerAdapter(matchEvents.get(0).getListHomeGoalDetail());
            homeGoalRecyclerView.setAdapter(homeGoalAdapter);

            GoalRecyclerAdapter awayGoalAdapter = new GoalRecyclerAdapter(matchEvents.get(0).getListAwayGoalDetail());
            awayGoalRecyclerView.setAdapter(awayGoalAdapter);

            ShotRecyclerAdapter shotsAdapter = new ShotRecyclerAdapter(matchEvents);
            shotsRecyclerView.setAdapter(shotsAdapter);
        }
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        // Simpan state List
        listStateMatch = mLayoutManager.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY_MATCH, listStateMatch);

        listStateShots = shotsLayoutManager.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY_SHOTS, listStateShots);

        listStateHomeGoals = homeGoalLayoutManager.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY_HGOALS, listStateHomeGoals);

        listStateAwayGoals = awayGoalLayoutManager.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY_AGOALS, listStateAwayGoals);
    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        // Kembalikan state List dan posisi item
        if (state != null) {
            listStateMatch = state.getParcelable(LIST_STATE_KEY_MATCH);
            listStateShots = state.getParcelable(LIST_STATE_KEY_SHOTS);
            listStateHomeGoals = state.getParcelable(LIST_STATE_KEY_HGOALS);
            listStateAwayGoals = state.getParcelable(LIST_STATE_KEY_AGOALS);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (listStateMatch != null) {
            mLayoutManager.onRestoreInstanceState(listStateMatch);
        }
        if (listStateShots != null) {
            shotsLayoutManager.onRestoreInstanceState(listStateShots);
        }
        if (listStateHomeGoals != null) {
            homeGoalLayoutManager.onRestoreInstanceState(listStateHomeGoals);
        }
        if (listStateAwayGoals != null) {
            awayGoalLayoutManager.onRestoreInstanceState(listStateAwayGoals);
        }
    }


    public void onImageClick(View view) {
        Intent intent = new Intent(EventDetailActivity.this, TeamDetailActivity.class);
        intent.putExtra("team_name", "Mancehster");
        startActivity(intent);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
