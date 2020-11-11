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
import com.example.bolasepak.model.MatchEvent;

import java.util.List;

public class ShotRecyclerAdapter extends RecyclerView.Adapter<ShotRecyclerAdapter.ViewHolder> {
    private List<MatchEvent> matchList;


    public ShotRecyclerAdapter(List<MatchEvent> matchList){
        this.matchList = matchList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shots_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShotRecyclerAdapter.ViewHolder holder, int position) {
        holder.bindMatch(this.matchList.get(0));
    }

    @Override
    public int getItemCount() {
        return this.matchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView home_shots;
        public TextView away_shots;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            home_shots = itemView.findViewById(R.id.home_shots);
            away_shots = itemView.findViewById(R.id.away_shots);
        }


        public void bindMatch(final MatchEvent match) {
            home_shots.setText(Integer.toString(match.getIntHomeShots()));
            away_shots.setText(Integer.toString(match.getIntAwayShots()));
        }
    }
}