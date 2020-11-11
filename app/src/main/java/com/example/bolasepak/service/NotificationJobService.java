package com.example.bolasepak.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.bolasepak.R;
import com.example.bolasepak.database.AppDatabaseHelper;
import com.example.bolasepak.model.MatchEvent;
import com.example.bolasepak.model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NotificationJobService extends JobService {
    private static final String TAG = "NotificationJobService";
    public boolean jobCancelled;
    public AppDatabaseHelper dbhelp;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.e(TAG, "run: BARU MASUK");

        listenNewMatch(jobParameters);

        return true;
    }

    public void listenNewMatch(JobParameters jobParameters){
        Log.e(TAG, "run: SEBELYM THREAD");

        new Thread(new Runnable() {
            @Override
            public void run() {
                    if (jobCancelled){
                        return;
                    }

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

                    List<Team> teams = new ArrayList<Team>();

                    dbhelp = new AppDatabaseHelper(getApplicationContext());

                    Context context = getApplicationContext();
                    SharedPreferences sharedPref = context.getSharedPreferences(context.getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();

                    teams = dbhelp.getSubscibedTeam();

                    int currentPosition = 0;
                    for (Team itemTeam :teams) {

                        int currentValue = sharedPref.getInt("TEAMNAME", 0);
                        int newCount = dbhelp.countAllEventByTeamID(itemTeam.getIdTeam());

                        editor.putInt("TEAMNAME", newCount);
                        editor.commit();

                        if (newCount > currentValue){
                            Log.e(TAG, "This Team is bebega" + itemTeam.getStrTeam());

                            Notification notification = new NotificationCompat.Builder(getApplicationContext(), App.CHANNEL_1_ID)
                                    .setSmallIcon(R.drawable.ic_search_black_24dp)
                                    .setContentTitle("Soccer team new match!")
                                    .setContentText("Team " + itemTeam.getStrTeam() + " has a new upcoming match, Take a look!")
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                    .build();

                            notificationManager.notify(currentPosition, notification);

                        }

                        currentPosition++;
                    }
            }
        }).start();
        jobFinished(jobParameters, false);
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        jobCancelled = true;
        return false;
    }
}
