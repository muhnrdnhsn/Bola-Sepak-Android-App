package com.example.bolasepak.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bolasepak.R;
import com.example.bolasepak.activity.adapter.MatchRecyclerAdapter;
import com.example.bolasepak.adapter.MainRecycleAdapter;
import com.example.bolasepak.database.AppDatabaseHelper;
import com.example.bolasepak.model.MatchEvent;
import com.example.bolasepak.model.MatchEventAPI;
import com.example.bolasepak.model.Team;
import com.example.bolasepak.model.TeamAPI;
import com.example.bolasepak.service.NotificationJobService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    RecyclerView recyclerView;
    MainRecycleAdapter recyclerAdapter;
    SensorManager sensorManager;

    TextView countSteps;
    public List<MatchEvent> matchEvents;
    boolean isRunning = false;
    public AppDatabaseHelper dbhelp;
    public MatchEventAPI reqBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        matchEvents = new ArrayList<MatchEvent>();

        dbhelp = new AppDatabaseHelper(getApplicationContext());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Log.e("CREATEDB", "run: ");
            }
        }, 5000);

        scheduleJob();

        TeamAPI teamapi = new TeamAPI(getApplicationContext());

        String HostName = "http://134.209.97.218:5050";

        teamapi.getAllTeam(HostName + "/api/v1/json/1/lookup_all_teams.php?id=4328");
        teamapi.getAllTeam(HostName + "/api/v1/json/1/lookup_all_teams.php?id=4329");
        teamapi.getAllTeam(HostName + "/api/v1/json/1/lookup_all_teams.php?id=4330");
        teamapi.getAllTeam(HostName + "/api/v1/json/1/lookup_all_teams.php?id=4331");
        teamapi.getAllTeam(HostName + "/api/v1/json/1/lookup_all_teams.php?id=4332");
        teamapi.getAllTeam(HostName + "/api/v1/json/1/lookup_all_teams.php?id=4499");
        teamapi.getAllTeam(HostName + "/api/v1/json/1/lookup_all_teams.php?id=4500");
        teamapi.getAllTeam(HostName + "/api/v1/json/1/lookup_all_teams.php?id=4501");
        teamapi.getAllTeam(HostName + "/api/v1/json/1/lookup_all_teams.php?id=4502");
        teamapi.getAllTeam(HostName + "/api/v1/json/1/lookup_all_teams.php?id=4335");
        setContentView(R.layout.match_event_recyclerview);
        setContentView(R.layout.activity_main);

        countSteps = (TextView) findViewById(R.id.step_counter);

        reqBuilder = new MatchEventAPI(getApplicationContext());

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        recyclerView = findViewById(R.id.main_recview);

        boolean isOnline = isNetworkAvailable();

        if (isOnline){
            reqBuilder.getFutureEventsByLeagueID("4328", recyclerView, matchEvents);
        }else{

            matchEvents = dbhelp.getEventByLeagueID(4328);

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

            MainRecycleAdapter recyclerAdapter = new MainRecycleAdapter(matchEvents);
            recyclerView.setAdapter(recyclerAdapter);
        }
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null){
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        }else{
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
        sensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.searchbar);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Log.e("MATCHEV", Integer.toString(matchEvents.size()));
                List<MatchEvent> filterList = new ArrayList<MatchEvent>();

                filterList.addAll(matchEvents);

                MainRecycleAdapter recyclerAdapter = new MainRecycleAdapter(filterList);
                recyclerAdapter.getFilter().filter(newText);

                recyclerView = findViewById(R.id.main_recview);
                recyclerView.setAdapter(recyclerAdapter);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(isRunning){
            countSteps.setText(String.valueOf(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void scheduleJob(){
        ComponentName componentName = new ComponentName(this, NotificationJobService.class);
        JobInfo info = new JobInfo.Builder(1, componentName)
                .setPeriodic(100000)
                .setPersisted(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int resultcode = scheduler.schedule(info);
        if (resultcode == JobScheduler.RESULT_SUCCESS){
            Log.e("JOB SCHEDULED", "scheduleJob: ");
        }else{
            Log.e("JOB FAILED", "scheduleJob: ");
        }
    }

    public void cancelJob(){
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(1);
        Log.e("JOB DEAD", "");
        return;
    }
}
