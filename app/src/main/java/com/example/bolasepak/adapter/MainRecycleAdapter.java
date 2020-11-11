package com.example.bolasepak.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bolasepak.R;
import com.example.bolasepak.activity.EventDetailActivity;
import com.example.bolasepak.activity.TeamDetailActivity;
import com.example.bolasepak.model.MatchEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainRecycleAdapter extends RecyclerView.Adapter<MainRecycleAdapter.ViewHolder> implements Filterable {
    private static final String TAG = "MainRecycleAdapter";
    List<MatchEvent> listAll;
    List<MatchEvent> list;
    public Context context;

    public MainRecycleAdapter(List<MatchEvent> list){
        this.list = list;
        this.listAll = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.match_event_recyclerview, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MatchEvent model = list.get(position);
        holder.homeName.setText(model.getStrHomeTeam());
        holder.awayName.setText(model.getStrAwayTeam());
        holder.homeScore.setText(model.getStrScoreAway());
        holder.awayScore.setText(model.getStrScoreAway());
        holder.dateEvent.setText(model.getDateEvent());
        Glide.with(context).load(model.getStrHomeBadge()).into(holder.homeLogo);
        Glide.with(context).load(model.getStrAwayBadge()).into(holder.awayLogo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventDetailActivity.class);
                Log.e(TAG, "GEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEGEEEEEEEEEEEEEEEP: "+ model.getIdEvent() );
                intent.putExtra("event", Integer.toString(model.getIdEvent()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<MatchEvent> filteredList = new ArrayList<>();

            if(charSequence.toString().isEmpty()){
                filteredList.addAll(list);
            }else{
                for(MatchEvent event:listAll){
                    if(event.getStrHomeTeam().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(event);
                    }else if(event.getStrAwayTeam().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(event);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((Collection<? extends MatchEvent>) results.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView homeLogo, awayLogo;
        TextView homeName, awayName, dateEvent, homeScore, awayScore;
        ConstraintLayout constraint;

        public ViewHolder(@NonNull View view){
            super(view);
            homeLogo = view.findViewById(R.id.HomeBadge);
            awayLogo = view.findViewById(R.id.AwayBadge);
            homeName = view.findViewById(R.id.EventTeamHome);
            awayName = view.findViewById(R.id.EventTeamAway);
            dateEvent = view.findViewById(R.id.EventDate);
            homeScore = view.findViewById(R.id.EventScoreHome);
            awayScore = view.findViewById(R.id.EventScoreAway);
//
//            view.setOnClickListener(this);


        }

//        @Override
//        public void onClick(View v) {
//            //GO TO...
//
//            //FOR SAMPLE
//            //Send Message
//            Toast.makeText(v.getContext(), "Success", Toast.LENGTH_SHORT).show();
//        }
    }
}
