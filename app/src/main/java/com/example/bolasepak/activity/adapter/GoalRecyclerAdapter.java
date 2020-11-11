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

public class GoalRecyclerAdapter extends RecyclerView.Adapter<GoalRecyclerAdapter.ViewHolder> {
    private List<String> goalList;


    public GoalRecyclerAdapter(List<String> goallist){
        this.goalList = goallist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.goal_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalRecyclerAdapter.ViewHolder holder, int position) {
        holder.bindMatch(this.goalList.get(position));
    }

    @Override
    public int getItemCount() {
        return this.goalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView goal;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            goal = itemView.findViewById(R.id.goal);
        }


        public void bindMatch(final String goalstr){
            goal.setText(goalstr);
        }
    }
}