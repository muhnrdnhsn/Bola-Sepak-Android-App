package com.example.bolasepak.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bolasepak.R;
import com.example.bolasepak.activity.EventDetailActivity;
import com.example.bolasepak.activity.TeamDetailActivity;
import com.example.bolasepak.model.MatchEvent;

import java.util.List;

public class MatchRecyclerAdapter extends RecyclerView.Adapter<MatchRecyclerAdapter.ViewHolder> {
    private static final Integer MATCH_UPCOMING = 1;
    private static final Integer MATCH_PAST = 2;
    private List<MatchEvent> matchList;
    public Context context;


    public MatchRecyclerAdapter(List<MatchEvent> matchList){
        this.matchList = matchList;
    }

    @NonNull
    @Override
    public MatchRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewHolder viewHolder;

        if (viewType == MATCH_PAST){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_event_recyclerview, parent, false);
            viewHolder =  new ViewHolder(view, MATCH_PAST);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_event_recyclerview, parent, false);
            viewHolder = new ViewHolder(view, MATCH_UPCOMING);
        }

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MatchRecyclerAdapter.ViewHolder holder, int position) {
        holder.bindMatch(this.matchList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.matchList.size();
    }

    @Override
    public int getItemViewType(int position){
        if (matchList.get(position).isPast()){
            return MATCH_PAST;
        }else {
            return MATCH_UPCOMING;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView EventDate, EventTeamHome, EventTeamAway, EventScoreHome, EventScoreAway, VersusLogo;
        public ImageView HomeBadge, AwayBadge;

        public ViewHolder(@NonNull final View itemView, int type) {
            super(itemView);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent i = new Intent(view.getContext(), )
//                }
//            });

            EventDate = itemView.findViewById(R.id.EventDate);
            EventTeamHome = itemView.findViewById(R.id.EventTeamHome);
            EventTeamAway = itemView.findViewById(R.id.EventTeamAway);
            EventScoreHome = itemView.findViewById(R.id.EventScoreHome);
            EventScoreAway = itemView.findViewById(R.id.EventScoreAway);
            HomeBadge = (ImageView) itemView.findViewById(R.id.HomeBadge);
            AwayBadge = (ImageView) itemView.findViewById(R.id.AwayBadge);
            VersusLogo = itemView.findViewById(R.id.versus);

        }


        public void bindMatch(final MatchEvent match){
            EventDate.setText(match.getDateEvent());
            EventTeamAway.setText(match.getStrAwayTeam());
            EventTeamHome.setText(match.getStrHomeTeam());
            EventScoreHome.setText(match.getStrScoreHome());
            EventScoreAway.setText(match.getStrScoreAway());
            HomeBadge.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(context,TeamDetailActivity.class);
                    intent.putExtra("team_id", match.getIdHomeTeam());
                    context.startActivity(intent);
                }
            });
            VersusLogo.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(context,EventDetailActivity.class);
                    intent.putExtra("event", Integer.toString(match.getIdEvent()));
                    context.startActivity(intent);
                }
            });
            AwayBadge.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(context,TeamDetailActivity.class);
                    intent.putExtra("team_id", match.getIdAwayTeam());
                    context.startActivity(intent);
                }
            });
            // load images with Glide
            Glide.with(context).load(match.getStrHomeBadge()).into(HomeBadge);
            Glide.with(context).load(match.getStrAwayBadge()).into(AwayBadge);

//            matchDetailsBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent;
//                    if (match.getPast()){
//                        intent = new Intent(v.getContext(), PastMatchDetailsActivity.class);
//                    } else {
//                        intent = new Intent(v.getContext(), UpcomingMatchDetailsActivity.class);
//                    }
//
//                    intent.putExtra("MATCH_DATE", matchDate.getText().toString());
//                    intent.putExtra("MATCH_HIGHLIGHTED", matchHighlighted.getText().toString());
//                    intent.putExtra("MATCH_HOME_TEAM_NAME", homeTeamName.getText().toString());
//                    intent.putExtra("MATCH_AWAY_TEAM_NAME", awayTeamName.getText().toString());
//                    intent.putExtra("MATCH_HOME_BADGE_URL", match.getHomeBadgeURL());
//                    intent.putExtra("MATCH_AWAY_BADGE_URL", match.getAwayBadgeURL());
//
//                    v.getContext().startActivity(intent);
//                }
//            });
        }
    }
}