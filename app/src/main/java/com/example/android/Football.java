package com.example.android.footballlivescores;

import static com.example.android.footballlivescores.R.color.odd1;
import static com.example.android.footballlivescores.R.color.odd2;

/**
 * Created by AGUELE OSEKUEMEN JOE on 4/3/2017.
 */

public class Football {

    // name of the first team
    private String homeTeamName;

    // name of the second team
    private String awayTeamName;

    //score of the first team
    private String goalsHomeTeam;

    // score of the second team
    private String goalsAwayTeam;

    // time of the game
    private String mTimeInMilliseconds;

    // date of the game
    private String mdate;

    private String modd1;
    private String modd2;
    private String modd3;


    public Football(String teamOneName, String teamTwoName, String teamOneScore, String teamTwoScore, String time, String date) {

        homeTeamName = teamOneName;
        goalsHomeTeam = teamOneScore;
        awayTeamName = teamTwoName;
        goalsAwayTeam = teamTwoScore;
        mTimeInMilliseconds = time;
        mdate = date;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public String getGoalsHomeTeam() {
        return goalsHomeTeam;
    }

    public String getGoalsAwayTeam() {
        return goalsAwayTeam;
    }

    public String getTime() {
        return mTimeInMilliseconds;
    }


    public String getDate() {
        return mdate;
    }

/*
    public String getOdd1() {
        return modd1;
    }

    public String getOdd2() {
        return modd2;
    }

    public String getodd3() {
        return modd3;
    }*/
}
