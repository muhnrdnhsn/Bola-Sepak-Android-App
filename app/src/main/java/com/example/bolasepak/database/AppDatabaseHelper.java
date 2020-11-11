package com.example.bolasepak.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.service.autofill.FieldClassification;
import android.util.Log;

import androidx.room.Ignore;

import com.example.bolasepak.model.MatchEvent;
import com.example.bolasepak.model.Team;

import java.util.ArrayList;
import java.util.List;

public class AppDatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "appdb";


    public AppDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("CREAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAATE", "DDDDDDDDDDDDDDDDDDDEBE: " + DATABASE_NAME);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create teams table
        db.execSQL(Team.CREATE_TABLE);
        Log.e("CREAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAATE", Team.CREATE_TABLE);
        db.execSQL(MatchEvent.CREATE_TABLE);
        Log.e("CREAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAATE", MatchEvent.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Team.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MatchEvent.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertEvent(MatchEvent event){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(MatchEvent.COLUMN_DATEEVENT, event.getDateEvent());
        values.put(MatchEvent.COLUMN_IDLEAGUE, event.getIdLeague());
        values.put(MatchEvent.COLUMN_IDAWAYTEAM, event.getIdAwayTeam());
        values.put(MatchEvent.COLUMN_IDEVENT, event.getIdEvent());
        values.put(MatchEvent.COLUMN_IDHOMETEAM, event.getIdHomeTeam());
        values.put(MatchEvent.COLUMN_INTAWAYSCORE, event.getIntAwayScore());
        values.put(MatchEvent.COLUMN_INTAWAYSHOTS, event.getIntAwayShots());
        values.put(MatchEvent.COLUMN_INTHOMESCORE, event.getIntHomeScore());
        values.put(MatchEvent.COLUMN_INTHOMESHOTS, event.getIntHomeShots());
        values.put(MatchEvent.COLUMN_STRAWAYBADGE, event.getStrAwayBadge());
        values.put(MatchEvent.COLUMN_STRAWAYGOALDETAILS, event.getStrAwayGoalDetails());
        values.put(MatchEvent.COLUMN_STRAWAYTEAM, event.getStrAwayTeam());
        values.put(MatchEvent.COLUMN_STRHOMEBADGE, event.getStrHomeBadge());
        values.put(MatchEvent.COLUMN_STRHOMEGOALDETAILS, event.getStrHomeGoalDetails());
        values.put(MatchEvent.COLUMN_STRHOMETEAM, event.getStrHomeTeam());
        values.put(MatchEvent.COLUMN_STRSCOREAWAY, event.getStrScoreAway());
        values.put(MatchEvent.COLUMN_STRSCOREHOME, event.getStrScoreHome());
        values.put(MatchEvent.COLUMN_TIMEEVENT, event.getTimeEvent());

        // insert row
        long id = db.insertWithOnConflict(MatchEvent.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long insertTeam(Team teaminsert) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them

        values.put(Team.COLUMN_IDTEAM, teaminsert.getIdTeam());
        values.put(Team.COLUMN_STRALTERNATE, teaminsert.getStrAlternate());
        values.put(Team.COLUMN_STRTEAM, teaminsert.getStrTeam());
        values.put(Team.COLUMN_STRCOUNTRY, teaminsert.getStrAlternate());
        values.put(Team.COLUMN_INTFORMEDYEAR, teaminsert.getIntFormedYear());
        values.put(Team.COLUMN_STRLEAGUE, teaminsert.getStrLeague());
        values.put(Team.COLUMN_STRSPORT, teaminsert.getStrSport());
        values.put(Team.COLUMN_IDLEAGUE, teaminsert.getIdTeam());
        values.put(Team.COLUMN_STRSTADIUM, teaminsert.getStrStadium());
        values.put(Team.COLUMN_STRSTADIUMLOCATION, teaminsert.getStrStadiumLocation());
        values.put(Team.COLUMN_STRTEAMBADGE, teaminsert.getStrTeamBadge());
        values.put(Team.COLUMN_SUBSCRIBED, teaminsert.isSubscribed());


        // insert row
        long id = db.insertWithOnConflict(Team.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public void subscribe(String teamID) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them

        values.put(Team.COLUMN_SUBSCRIBED, 1);

        db.update(Team.TABLE_NAME, values, Team.COLUMN_IDTEAM + "=" + teamID, null);

        // close db connection
        db.close();
    }

    public void unsubscribe(String teamID) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them

        values.put(Team.COLUMN_SUBSCRIBED, 0);

        db.update(Team.TABLE_NAME, values, Team.COLUMN_IDTEAM + "=" + teamID, null);

        // close db connection
        db.close();
    }

    public boolean isSubscribed(String teamID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + Team.TABLE_NAME + " WHERE "+ Team.COLUMN_IDTEAM + " = " + teamID;
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();

            int ret = cursor.getInt(cursor.getColumnIndex(Team.COLUMN_SUBSCRIBED));

            cursor.close();

            db.close();

            return (ret == 1);
        }else{
            db.close();

            return false;
        }
    }

    public List<MatchEvent> getAllEventByTeamID(String TeamID) {

        List<MatchEvent> retval = new ArrayList<MatchEvent>();

        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + MatchEvent.TABLE_NAME + " WHERE " + MatchEvent.COLUMN_IDAWAYTEAM + " = " + TeamID + " OR " + MatchEvent.COLUMN_IDHOMETEAM + " = " + TeamID ;
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        if (cursor != null){
            if(cursor.moveToFirst()){

                do{ // prepare team object
                    MatchEvent matchEvent = new MatchEvent();
                    matchEvent.setDateEvent(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_DATEEVENT)));
                    matchEvent.setIdLeague(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_IDLEAGUE)));
                    matchEvent.setIdAwayTeam(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_IDAWAYTEAM)));
                    matchEvent.setIdEvent(cursor.getInt(cursor.getColumnIndex(matchEvent.COLUMN_IDEVENT)));
                    matchEvent.setIdHomeTeam(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_IDHOMETEAM)));
                    matchEvent.setIntAwayScore(cursor.getInt(cursor.getColumnIndex(matchEvent.COLUMN_INTAWAYSCORE)));
                    matchEvent.setIntAwayShots(cursor.getInt(cursor.getColumnIndex(matchEvent.COLUMN_INTAWAYSHOTS)));
                    matchEvent.setIntHomeScore(cursor.getInt(cursor.getColumnIndex(matchEvent.COLUMN_INTHOMESCORE)));
                    matchEvent.setIntHomeShots(cursor.getInt(cursor.getColumnIndex(matchEvent.COLUMN_INTHOMESHOTS)));
                    matchEvent.setStrAwayBadge(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRAWAYBADGE)));
                    matchEvent.setStrAwayGoalDetails(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRAWAYGOALDETAILS)));
                    matchEvent.setStrAwayTeam(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRAWAYTEAM)));
                    matchEvent.setStrHomeBadge(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRHOMEBADGE)));
                    matchEvent.setStrHomeGoalDetails(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRHOMEGOALDETAILS)));
                    matchEvent.setStrHomeTeam(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRHOMETEAM)));
                    matchEvent.setStrScoreAway(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRSCOREAWAY)));
                    matchEvent.setStrScoreHome(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRSCOREHOME)));
                    matchEvent.setTimeEvent(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_TIMEEVENT)));
                    retval.add(matchEvent);
                }while(cursor.moveToNext());
            }
        }

        // close the db connection
        cursor.close();

        return retval;
    }

    public int countAllEventByTeamID(String TeamID) {

        List<MatchEvent> retval = new ArrayList<MatchEvent>();

        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + MatchEvent.TABLE_NAME + " WHERE " + MatchEvent.COLUMN_IDAWAYTEAM + " = " + TeamID + " OR " + MatchEvent.COLUMN_IDHOMETEAM + " = " + TeamID ;
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        // close the db connection
        cursor.close();

        return count;
    }

    public List<MatchEvent> getAllEvent() {

        List<MatchEvent> retval = new ArrayList<MatchEvent>();

        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + MatchEvent.TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        if (cursor != null){
            if(cursor.moveToFirst()){

                do{ // prepare team object
                    MatchEvent matchEvent = new MatchEvent();
                    matchEvent.setDateEvent(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_DATEEVENT)));
                    matchEvent.setIdLeague(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_IDLEAGUE)));
                    matchEvent.setIdAwayTeam(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_IDAWAYTEAM)));
                    matchEvent.setIdEvent(cursor.getInt(cursor.getColumnIndex(matchEvent.COLUMN_IDEVENT)));
                    matchEvent.setIdHomeTeam(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_IDHOMETEAM)));
                    matchEvent.setIntAwayScore(cursor.getInt(cursor.getColumnIndex(matchEvent.COLUMN_INTAWAYSCORE)));
                    matchEvent.setIntAwayShots(cursor.getInt(cursor.getColumnIndex(matchEvent.COLUMN_INTAWAYSHOTS)));
                    matchEvent.setIntHomeScore(cursor.getInt(cursor.getColumnIndex(matchEvent.COLUMN_INTHOMESCORE)));
                    matchEvent.setIntHomeShots(cursor.getInt(cursor.getColumnIndex(matchEvent.COLUMN_INTHOMESHOTS)));
                    matchEvent.setStrAwayBadge(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRAWAYBADGE)));
                    matchEvent.setStrAwayGoalDetails(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRAWAYGOALDETAILS)));
                    matchEvent.setStrAwayTeam(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRAWAYTEAM)));
                    matchEvent.setStrHomeBadge(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRHOMEBADGE)));
                    matchEvent.setStrHomeGoalDetails(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRHOMEGOALDETAILS)));
                    matchEvent.setStrHomeTeam(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRHOMETEAM)));
                    matchEvent.setStrScoreAway(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRSCOREAWAY)));
                    matchEvent.setStrScoreHome(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRSCOREHOME)));
                    matchEvent.setTimeEvent(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_TIMEEVENT)));
                    retval.add(matchEvent);
                }while(cursor.moveToNext());
            }
        }

        // close the db connection
        cursor.close();

        return retval;
    }

    public List<MatchEvent> getEventByEventID(int EventID) {

        List<MatchEvent> retval = new ArrayList<MatchEvent>();

        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + MatchEvent.TABLE_NAME + " WHERE " + MatchEvent.COLUMN_IDEVENT + " = " + EventID;
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        if (cursor != null){
            if(cursor.moveToFirst()){

                do{ // prepare team object
                    MatchEvent matchEvent = new MatchEvent();
                    matchEvent.setDateEvent(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_DATEEVENT)));
                    matchEvent.setIdAwayTeam(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_IDAWAYTEAM)));
                    matchEvent.setIdLeague(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_IDLEAGUE)));
                    matchEvent.setIdEvent(cursor.getInt(cursor.getColumnIndex(matchEvent.COLUMN_IDEVENT)));
                    matchEvent.setIdHomeTeam(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_IDHOMETEAM)));
                    matchEvent.setIntAwayScore(cursor.getInt(cursor.getColumnIndex(matchEvent.COLUMN_INTAWAYSCORE)));
                    matchEvent.setIntAwayShots(cursor.getInt(cursor.getColumnIndex(matchEvent.COLUMN_INTAWAYSHOTS)));
                    matchEvent.setIntHomeScore(cursor.getInt(cursor.getColumnIndex(matchEvent.COLUMN_INTHOMESCORE)));
                    matchEvent.setIntHomeShots(cursor.getInt(cursor.getColumnIndex(matchEvent.COLUMN_INTHOMESHOTS)));
                    matchEvent.setStrAwayBadge(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRAWAYBADGE)));
                    matchEvent.setStrAwayGoalDetails(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRAWAYGOALDETAILS)));
                    matchEvent.setStrAwayTeam(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRAWAYTEAM)));
                    matchEvent.setStrHomeBadge(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRHOMEBADGE)));
                    matchEvent.setStrHomeGoalDetails(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRHOMEGOALDETAILS)));
                    matchEvent.setStrHomeTeam(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRHOMETEAM)));
                    matchEvent.setStrScoreAway(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRSCOREAWAY)));
                    matchEvent.setStrScoreHome(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRSCOREHOME)));
                    matchEvent.setTimeEvent(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_TIMEEVENT)));
                    retval.add(matchEvent);
                }while(cursor.moveToNext());
            }
        }

        // close the db connection
        cursor.close();

        return retval;
    }

    public List<MatchEvent> getEventByLeagueID(int LeagueID) {

        List<MatchEvent> retval = new ArrayList<MatchEvent>();

        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + MatchEvent.TABLE_NAME + " WHERE " + MatchEvent.COLUMN_IDLEAGUE + " = " + LeagueID;
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        if (cursor != null){
            if(cursor.moveToFirst()){

                do{ // prepare team object
                    MatchEvent matchEvent = new MatchEvent();
                    matchEvent.setDateEvent(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_DATEEVENT)));
                    matchEvent.setIdAwayTeam(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_IDAWAYTEAM)));
                    matchEvent.setIdEvent(cursor.getInt(cursor.getColumnIndex(matchEvent.COLUMN_IDEVENT)));
                    matchEvent.setIdLeague(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_IDLEAGUE)));
                    matchEvent.setIdHomeTeam(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_IDHOMETEAM)));
                    matchEvent.setIntAwayScore(cursor.getInt(cursor.getColumnIndex(matchEvent.COLUMN_INTAWAYSCORE)));
                    matchEvent.setIntAwayShots(cursor.getInt(cursor.getColumnIndex(matchEvent.COLUMN_INTAWAYSHOTS)));
                    matchEvent.setIntHomeScore(cursor.getInt(cursor.getColumnIndex(matchEvent.COLUMN_INTHOMESCORE)));
                    matchEvent.setIntHomeShots(cursor.getInt(cursor.getColumnIndex(matchEvent.COLUMN_INTHOMESHOTS)));
                    matchEvent.setStrAwayBadge(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRAWAYBADGE)));
                    matchEvent.setStrAwayGoalDetails(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRAWAYGOALDETAILS)));
                    matchEvent.setStrAwayTeam(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRAWAYTEAM)));
                    matchEvent.setStrHomeBadge(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRHOMEBADGE)));
                    matchEvent.setStrHomeGoalDetails(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRHOMEGOALDETAILS)));
                    matchEvent.setStrHomeTeam(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRHOMETEAM)));
                    matchEvent.setStrScoreAway(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRSCOREAWAY)));
                    matchEvent.setStrScoreHome(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_STRSCOREHOME)));
                    matchEvent.setTimeEvent(cursor.getString(cursor.getColumnIndex(matchEvent.COLUMN_TIMEEVENT)));
                    retval.add(matchEvent);
                }while(cursor.moveToNext());
            }
        }

        // close the db connection
        cursor.close();

        return retval;
    }

    public Team getTeam(String id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + Team.TABLE_NAME + " WHERE "+ Team.COLUMN_IDTEAM + " = " + id;
        Cursor cursor = db.rawQuery(countQuery, null);

//        SELECT  * FROM teams WHERE idTeam = 133602
//        SELECT  * FROM teams WHERE idTeam = 133729

        int count = cursor.getCount();

        Team team = new Team();

        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();

                // prepare team object
                team.setIdTeam(cursor.getString(cursor.getColumnIndex(Team.COLUMN_IDTEAM)));
                team.setStrAlternate(cursor.getString(cursor.getColumnIndex(Team.COLUMN_STRALTERNATE)));
                team.setStrTeam(cursor.getString(cursor.getColumnIndex(Team.COLUMN_STRTEAM)));
                team.setStrCountry(cursor.getString(cursor.getColumnIndex(Team.COLUMN_STRCOUNTRY)));
                team.setIntFormedYear(cursor.getString(cursor.getColumnIndex(Team.COLUMN_INTFORMEDYEAR)));
                team.setStrLeague(cursor.getString(cursor.getColumnIndex(Team.COLUMN_STRLEAGUE)));
                team.setStrSport(cursor.getString(cursor.getColumnIndex(Team.COLUMN_STRSPORT)));
                team.setIdLeague(cursor.getString(cursor.getColumnIndex(Team.COLUMN_IDLEAGUE)));
                team.setStrStadium(cursor.getString(cursor.getColumnIndex(Team.COLUMN_STRSTADIUM)));
                team.setStrStadiumLocation(cursor.getString(cursor.getColumnIndex(Team.COLUMN_STRSTADIUMLOCATION)));
                team.setStrTeamBadge(cursor.getString(cursor.getColumnIndex(Team.COLUMN_STRTEAMBADGE)));
                team.setSubscribed(cursor.getInt(cursor.getColumnIndex(Team.COLUMN_SUBSCRIBED)));

                // close the db connection
                cursor.close();

            return team;
        }else{
            return  null;
        }
    }

    public List<Team> getSubscibedTeam() {

        List<Team> retval = new ArrayList<Team>();

        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + Team.TABLE_NAME + " WHERE " + Team.COLUMN_SUBSCRIBED + " = " + 1;
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();


        if (cursor != null){
            if(cursor.moveToFirst()){
                do{
                    Team team = new Team();
                    // prepare team object
                    team.setIdTeam(cursor.getString(cursor.getColumnIndex(Team.COLUMN_IDTEAM)));
                    team.setStrAlternate(cursor.getString(cursor.getColumnIndex(Team.COLUMN_STRALTERNATE)));
                    team.setStrTeam(cursor.getString(cursor.getColumnIndex(Team.COLUMN_STRTEAM)));
                    team.setStrCountry(cursor.getString(cursor.getColumnIndex(Team.COLUMN_STRCOUNTRY)));
                    team.setIntFormedYear(cursor.getString(cursor.getColumnIndex(Team.COLUMN_INTFORMEDYEAR)));
                    team.setStrLeague(cursor.getString(cursor.getColumnIndex(Team.COLUMN_STRLEAGUE)));
                    team.setStrSport(cursor.getString(cursor.getColumnIndex(Team.COLUMN_STRSPORT)));
                    team.setIdLeague(cursor.getString(cursor.getColumnIndex(Team.COLUMN_IDLEAGUE)));
                    team.setStrStadium(cursor.getString(cursor.getColumnIndex(Team.COLUMN_STRSTADIUM)));
                    team.setStrStadiumLocation(cursor.getString(cursor.getColumnIndex(Team.COLUMN_STRSTADIUMLOCATION)));
                    team.setStrTeamBadge(cursor.getString(cursor.getColumnIndex(Team.COLUMN_STRTEAMBADGE)));
                    team.setSubscribed(cursor.getInt(cursor.getColumnIndex(Team.COLUMN_SUBSCRIBED)));

                    retval.add(team);
                }while(cursor.moveToNext());
            }
        }

        // close the db connection
        cursor.close();

        return retval;
    }

    public ArrayList<Cursor> getData(String Query){
    //get writable database
    SQLiteDatabase sqlDB = this.getWritableDatabase();
    String[] columns = new String[] { "message" };
    //an array list of cursor to save two cursors one has results from the query
    //other cursor stores error message if any errors are triggered
    ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
    MatrixCursor Cursor2= new MatrixCursor(columns);
    alc.add(null);
    alc.add(null);

    try{
        String maxQuery = Query ;
        //execute the query results will be save in Cursor c
        Cursor c = sqlDB.rawQuery(maxQuery, null);

        //add value to cursor2
        Cursor2.addRow(new Object[] { "Success" });

        alc.set(1,Cursor2);
        if (null != c && c.getCount() > 0) {

            alc.set(0,c);
            c.moveToFirst();

            return alc ;
        }
        return alc;
    } catch(SQLException sqlEx){
        Log.d("printing exception", sqlEx.getMessage());
        //if any exceptions are triggered save the error message to cursor an return the arraylist
        Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
        alc.set(1,Cursor2);
        return alc;
    } catch(Exception ex){
        Log.d("printing exception", ex.getMessage());

        //if any exceptions are triggered save the error message to cursor an return the arraylist
        Cursor2.addRow(new Object[] { ""+ex.getMessage() });
        alc.set(1,Cursor2);
        return alc;
    }
}

}