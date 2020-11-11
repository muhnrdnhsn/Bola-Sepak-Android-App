package com.example.bolasepak.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bolasepak.R;
import com.example.bolasepak.activity.adapter.MatchRecyclerAdapter;
import com.example.bolasepak.database.AppDatabaseHelper;
import com.example.bolasepak.model.MatchEvent;
import com.example.bolasepak.model.MatchEventAPI;
import com.example.bolasepak.model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FutureMatch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FutureMatch extends Fragment {

    private static final String TAG = "FutureMatchFragment";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View v;
    private List<MatchEvent> matchList;

    private Toolbar toolbar;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Boolean mParam3;
    public AppDatabaseHelper dbhelp;

    public FutureMatch() {
        // Required empty public constructor
    }

    public static FutureMatch newInstance(String param1, String param2, Boolean param3) {
        FutureMatch fragment = new FutureMatch();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putBoolean(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        dbhelp = new AppDatabaseHelper(getContext());


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getBoolean(ARG_PARAM3);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        matchList = new ArrayList<>();

        v = inflater.inflate(R.layout.fragment_future_match, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.TeamDetailFutureRecView);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        MatchEventAPI reqBuilder = new MatchEventAPI(getContext());

        if (mParam3){
            if (mParam2 == "Future")
            {
                reqBuilder.getFutureEventsByTeamID(mParam1, mRecyclerView);
                Log.e("ENTER","NEGUCHI");
            }else{
                reqBuilder.getPastEventsByTeamID(mParam1, mRecyclerView);
                Log.e("ENTER","NEGUCHI");
            }
            matchList = reqBuilder.responseObjects;
        }else{
            matchList = dbhelp.getAllEventByTeamID(mParam1);

            matchList.forEach(new Consumer<MatchEvent>() {
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
                }
            });


            MatchRecyclerAdapter recyclerAdapterMatch = new MatchRecyclerAdapter(matchList);
            mRecyclerView.setAdapter(recyclerAdapterMatch);
        }



        return v;
    }

    public void getFutureEventsByTeamIDLocal(String teamID, final RecyclerView mRecyclerView){
        dbhelp = new AppDatabaseHelper(getContext());

    }
}
