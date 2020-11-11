package com.example.bolasepak.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bolasepak.R;
import com.example.bolasepak.adapter.ViewPagerAdapter;
import com.example.bolasepak.fragment.FutureMatch;
import com.example.bolasepak.model.Team;
import com.example.bolasepak.model.TeamAPI;
import com.google.android.material.tabs.TabLayout;
import com.example.bolasepak.database.AppDatabaseHelper;

public class TeamDetailActivity extends AppCompatActivity {

    private static final String TAG = "TeamDetailActivity";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public TabLayout tabLayout;
    public ViewPager viewPager;
    public ViewPagerAdapter pagerAdapter;
    public AppDatabaseHelper dbhelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_team_detail);

        Intent intent = getIntent();
        String message = intent.getStringExtra("team_id");

        dbhelp = new AppDatabaseHelper(getApplicationContext());
        viewPager = findViewById(R.id.TeamPagerID);


        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        ImageView Depe = findViewById(R.id.DEPE);
        TextView teamName = findViewById(R.id.teamName);
        TextView teamEst = findViewById(R.id.teamEst);
        Switch sabskrib = findViewById(R.id.sabskrib);

        Team curteam = dbhelp.getTeam(message);

        Glide.with(getApplicationContext()).load(curteam.getStrTeamBadge()).into(Depe);
        teamName.setText(curteam.getStrTeam());
        teamEst.setText(curteam.getStrLeague());
        sabskrib.setChecked(1 == curteam.isSubscribed());
        sabskrib.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (1 == curteam.isSubscribed()){
                    dbhelp.unsubscribe(curteam.getIdTeam());
                }
                else{
                    dbhelp.subscribe(curteam.getIdTeam());
                }
            }
        });

        boolean isonline = isNetworkAvailable();

        pagerAdapter.addFragment(FutureMatch.newInstance(message,"Future", isonline));
        pagerAdapter.addFragment(FutureMatch.newInstance(message,"Past", isonline));

        viewPager.setAdapter(pagerAdapter);
        tabLayout = findViewById(R.id.TabLayout);
        tabLayout.setupWithViewPager(viewPager);



        super.onCreate(savedInstanceState);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
