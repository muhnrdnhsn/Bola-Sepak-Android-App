package com.example.bolasepak.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


public class Team
{
    public static final String TABLE_NAME = "teams";

    public static final String COLUMN_IDTEAM = "idTeam";
    private String idTeam;
    public static final String COLUMN_STRALTERNATE = "strAlternate";
    private String strAlternate;
    public static final String COLUMN_STRTEAM = "strTeam";
    private String strTeam;
    public static final String COLUMN_STRCOUNTRY = "strCountry";
    private String strCountry;
    public static final String COLUMN_INTFORMEDYEAR = "intFormedYear";
    private String intFormedYear;
    public static final String COLUMN_STRLEAGUE = "strLeague";
    private String strLeague;
    public static final String COLUMN_STRSPORT = "strSport";
    private String strSport;
    public static final String COLUMN_IDLEAGUE = "idLeague";
    private String idLeague;
    public static final String COLUMN_STRSTADIUM = "strStadium";
    private String strStadium;
    public static final String COLUMN_STRSTADIUMLOCATION = "strStadiumLocation";
    private String strStadiumLocation;
    public static final String COLUMN_STRTEAMBADGE = "strTeamBadge";
    private String strTeamBadge;
    public static final String COLUMN_SUBSCRIBED = "subscribed";
    private int subscribed;

    public int isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(int subscribed) {
        this.subscribed = subscribed;
    }

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_IDTEAM + " TEXT PRIMARY KEY,"
                    + COLUMN_STRALTERNATE + " TEXT,"
                    + COLUMN_STRTEAM + " TEXT,"
                    + COLUMN_STRCOUNTRY + " TEXT,"
                    + COLUMN_INTFORMEDYEAR + " TEXT,"
                    + COLUMN_STRLEAGUE + " TEXT,"
                    + COLUMN_STRSPORT + " TEXT,"
                    + COLUMN_IDLEAGUE + " TEXT,"
                    + COLUMN_STRSTADIUM + " TEXT,"
                    + COLUMN_STRSTADIUMLOCATION + " TEXT,"
                    + COLUMN_STRTEAMBADGE + " TEXT,"
                    + COLUMN_SUBSCRIBED + " INTEGER"
                    + ")";

    public String getStrAlternate ()
    {
        return strAlternate;
    }

    public void setStrAlternate (String strAlternate)
    {
        this.strAlternate = strAlternate;
    }

    public String getStrTeam ()
    {
        return strTeam;
    }

    public void setStrTeam (String strTeam)
    {
        this.strTeam = strTeam;
    }

    public String getStrCountry ()
    {
        return strCountry;
    }

    public void setStrCountry (String strCountry)
    {
        this.strCountry = strCountry;
    }

    public String getIntFormedYear ()
    {
        return intFormedYear;
    }

    public void setIntFormedYear (String intFormedYear)
    {
        this.intFormedYear = intFormedYear;
    }

    public String getStrLeague ()
    {
        return strLeague;
    }

    public void setStrLeague (String strLeague)
    {
        this.strLeague = strLeague;
    }

    public String getStrSport ()
    {
        return strSport;
    }

    public void setStrSport (String strSport)
    {
        this.strSport = strSport;
    }

    public String getIdTeam ()
    {
        return idTeam;
    }

    public void setIdTeam (String idTeam)
    {
        this.idTeam = idTeam;
    }

    public String getIdLeague ()
    {
        return idLeague;
    }

    public void setIdLeague (String idLeague)
    {
        this.idLeague = idLeague;
    }

    public String getStrStadium ()
    {
        return strStadium;
    }

    public void setStrStadium (String strStadium)
    {
        this.strStadium = strStadium;
    }

    public String getStrStadiumLocation ()
    {
        return strStadiumLocation;
    }

    public void setStrStadiumLocation (String strStadiumLocation)
    {
        this.strStadiumLocation = strStadiumLocation;
    }

    public String getStrTeamBadge ()
    {
        return strTeamBadge;
    }

    public void setStrTeamBadge (String strTeamBadge)
    {
        this.strTeamBadge = strTeamBadge;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [strAlternate = "+strAlternate+", strTeam = "+strTeam+", strCountry = "+strCountry+", intFormedYear = "+intFormedYear+", strLeague = "+strLeague+", strSport = "+strSport+", idTeam = "+idTeam+", idLeague = "+idLeague+", strStadium = "+strStadium+", strStadiumLocation = "+strStadiumLocation+", strTeamBadge = "+strTeamBadge+"]";
    }
}