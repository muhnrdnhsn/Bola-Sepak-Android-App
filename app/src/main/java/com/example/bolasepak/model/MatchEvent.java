package com.example.bolasepak.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MatchEvent {
    public static final String TABLE_NAME = "matches";

    public static final String COLUMN_IDLEAGUE = "idLeague";
    private String idLeague;
    public static final String COLUMN_IDEVENT = "idEvent";
    private int idEvent;
    public static final String COLUMN_INTHOMESHOTS = "intHomeShots";
    private int intHomeShots;
    public static final String COLUMN_INTAWAYSHOTS = "intAwayShots";
    private int intAwayShots;
    public static final String COLUMN_INTHOMESCORE = "intHomeScore";
    private int intHomeScore;
    public static final String COLUMN_INTAWAYSCORE = "intAwayScore";
    private int intAwayScore;
    public static final String COLUMN_DATETYPEDATEEVENT = "dateTypeDateEvent";
    private Date dateTypeDateEvent;
    public static final String COLUMN_STRHOMETEAM = "strHomeTeam";
    private String strHomeTeam;
    public static final String COLUMN_STRAWAYTEAM = "strAwayTeam";
    private String strAwayTeam;
    public static final String COLUMN_DATEEVENT = "dateEvent";
    private String dateEvent;
    public static final String COLUMN_STRHOMEBADGE = "strHomeBadge";
    private String strHomeBadge;
    public static final String COLUMN_STRAWAYBADGE = "strAwayBadge";
    private String strAwayBadge;
    public static final String COLUMN_IDHOMETEAM = "idHomeTeam";
    private String idHomeTeam;
    public static final String COLUMN_IDAWAYTEAM = "idAwayTeam";
    private String idAwayTeam;
    public static final String COLUMN_TIMEEVENT = "timeEvent";
    private String timeEvent;
    public static final String COLUMN_STRSCOREAWAY = "strScoreAway";
    private String strScoreAway;
    public static final String COLUMN_STRSCOREHOME = "strScoreHome";
    private String strScoreHome;
    public static final String COLUMN_STRHOMEGOALDETAILS = "strHomeGoalDetails";
    private String strHomeGoalDetails;
    public static final String COLUMN_STRAWAYGOALDETAILS = "strAwayGoalDetails";
    private String strAwayGoalDetails;
    public static final String COLUMN_ISPAST = "isPast";
    private boolean isPast;
    private List<String> listHomeGoalDetail;
    private List<String> listAwayGoalDetail;



    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_IDEVENT + " INTEGER PRIMARY KEY,"
                    + COLUMN_INTHOMESHOTS + " INTEGER,"
                    + COLUMN_INTAWAYSHOTS + " INTEGER,"
                    + COLUMN_INTHOMESCORE + " INTEGER,"
                    + COLUMN_INTAWAYSCORE + " INTEGER,"
                    + COLUMN_DATETYPEDATEEVENT + " TEXT,"
                    + COLUMN_IDLEAGUE + " TEXT,"
                    + COLUMN_STRHOMETEAM + " TEXT,"
                    + COLUMN_STRAWAYTEAM + " TEXT,"
                    + COLUMN_DATEEVENT + " TEXT,"
                    + COLUMN_STRHOMEBADGE + " TEXT,"
                    + COLUMN_STRAWAYBADGE + " TEXT,"
                    + COLUMN_IDHOMETEAM + " TEXT,"
                    + COLUMN_IDAWAYTEAM + " TEXT,"
                    + COLUMN_TIMEEVENT + " TEXT,"
                    + COLUMN_STRSCOREAWAY + " TEXT,"
                    + COLUMN_STRSCOREHOME + " TEXT,"
                    + COLUMN_STRHOMEGOALDETAILS + " TEXT,"
                    + COLUMN_STRAWAYGOALDETAILS + " TEXT"
                    + ")";

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public int getIntHomeShots() {
        return intHomeShots;
    }

    public void setIntHomeShots(int intHomeShots) {
        this.intHomeShots = intHomeShots;
    }

    public int getIntAwayShots() {
        return intAwayShots;
    }

    public void setIntAwayShots(int intAwayShots) {
        this.intAwayShots = intAwayShots;
    }

    public int getIntHomeScore() {
        return intHomeScore;
    }

    public void setIntHomeScore(int intHomeScore) {
        this.intHomeScore = intHomeScore;
    }

    public int getIntAwayScore() {
        return intAwayScore;
    }

    public void setIntAwayScore(int intAwayScore) {
        this.intAwayScore = intAwayScore;
    }

    public Date getDateTypeDateEvent() {
        return dateTypeDateEvent;
    }

    public void setDateTypeDateEvent(Date dateTypeDateEvent) {
        this.dateTypeDateEvent = dateTypeDateEvent;
    }

    public String getIdLeague() {
        return idLeague;
    }

    public void setIdLeague(String idLeague) {
        this.idLeague = idLeague;
    }

    public String getStrHomeTeam() {
        return strHomeTeam;
    }

    public void setStrHomeTeam(String strHomeTeam) {
        this.strHomeTeam = strHomeTeam;
    }

    public String getStrAwayTeam() {
        return strAwayTeam;
    }

    public void setStrAwayTeam(String strAwayTeam) {
        this.strAwayTeam = strAwayTeam;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getStrHomeBadge() {
        return strHomeBadge;
    }

    public void setStrHomeBadge(String strHomeBadge) {
        this.strHomeBadge = strHomeBadge;
    }

    public String getStrAwayBadge() {
        return strAwayBadge;
    }

    public void setStrAwayBadge(String strAwayBadge) {
        this.strAwayBadge = strAwayBadge;
    }

    public String getIdHomeTeam() {
        return idHomeTeam;
    }

    public void setIdHomeTeam(String idHomeTeam) {
        this.idHomeTeam = idHomeTeam;
    }

    public String getIdAwayTeam() {
        return idAwayTeam;
    }

    public void setIdAwayTeam(String idAwayTeam) {
        this.idAwayTeam = idAwayTeam;
    }

    public String getTimeEvent() {
        return timeEvent;
    }

    public void setTimeEvent(String timeEvent) {
        this.timeEvent = timeEvent;
    }

    public String getStrHomeGoalDetails() {
        return strHomeGoalDetails;
    }

    public void setStrHomeGoalDetails(String strHomeGoalDetails) {
        this.strHomeGoalDetails = strHomeGoalDetails;
    }

    public String getStrAwayGoalDetails() {
        return strAwayGoalDetails;
    }

    public void setStrAwayGoalDetails(String strAwayGoalDetails) {
        this.strAwayGoalDetails = strAwayGoalDetails;
    }

    public void setPast(boolean past) {
        isPast = past;
    }

    public List<String> getListHomeGoalDetail() {
        return listHomeGoalDetail;
    }

    public void setListHomeGoalDetail(List<String> listHomeGoalDetail) {
        this.listHomeGoalDetail = listHomeGoalDetail;
    }

    public List<String> getListAwayGoalDetail() {
        return listAwayGoalDetail;
    }

    public void setListAwayGoalDetail(List<String> listAwayGoalDetail) {
        this.listAwayGoalDetail = listAwayGoalDetail;
    }

    public String getStrScoreAway() {
        return strScoreAway;
    }

    public void setStrScoreAway(String strScoreAway) {
        this.strScoreAway = strScoreAway;
    }

    public String getStrScoreHome() {
        return strScoreHome;
    }

    public void setStrScoreHome(String strScoreHome) {
        this.strScoreHome = strScoreHome;
    }

    public static List<String> separateGoals(String strin){
        String str[] = strin.split(";");
        List<String> list = Arrays.asList(str);
        return list;
    }

    public MatchEvent() {
    }

    public void Format(){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = formatter.parse(this.dateEvent);

            this.dateTypeDateEvent = date1;

            if (this.dateTypeDateEvent.before(new Date())){
                this.isPast = true;
                this.strScoreHome = Integer.toString(this.intHomeScore);
                this.strScoreAway = Integer.toString(this.intAwayScore);
            }else{
                this.isPast = false;
                this.strScoreAway = "-";
                this.strScoreHome = "-";
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (strHomeGoalDetails != null){
            this.listHomeGoalDetail = separateGoals(strHomeGoalDetails);}

        if (strAwayGoalDetails != null){
            this.listAwayGoalDetail = separateGoals(strAwayGoalDetails);}

    }

    public boolean isPast() {
        return isPast;
    }
}
