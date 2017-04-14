package com.example.android.footballlivescores;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by AGUELE OSEKUEMEN JOE on 4/3/2017.
 */

public class FootballAdapter extends ArrayAdapter<Football> {


    /*use for the separation of date gotten from the url site*/
    private static final String DATE_SEPARATOR = "T";

    public FootballAdapter(Activity context, ArrayList<Football> footballs) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, footballs);
        ;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);

        }

        Football currentFootball = getItem(position);

        TextView teamOne = (TextView) listItemView.findViewById(R.id.home_team_name);
        teamOne.setText(currentFootball.getHomeTeamName());

        TextView teamTwo = (TextView) listItemView.findViewById(R.id.away_team_name);
        teamTwo.setText(currentFootball.getAwayTeamName());




        /*Here because the website shows the score board as null for both teams before the game,
        * we instruct the program to give us an empty string if it shows null and produce the scores
        * if there is a score
        * i.e before the game -- leave the score board to be empty(insteading of outputting null)
        * after the game --- give us the score*/
        TextView teamOneScore = (TextView) listItemView.findViewById(R.id.goals_home_team);
        //teamOneScore.setText(currentFootball.getGoalsHomeTeam());
        if(currentFootball.getGoalsHomeTeam()==null){
            teamOneScore.setText("?");
        }

        //The same thing done in team one score is done to team two score too.
        TextView teamTwoScore = (TextView) listItemView.findViewById(R.id.goals_away_team);
        //teamTwoScore.setText(currentFootball.getGoalsAwayTeam());
        if(currentFootball.getGoalsAwayTeam()==null){
            teamTwoScore.setText("");
        }

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        timeTextView.setText(currentFootball.getTime());


        /*Here a small break down is done. The original date that comes from the website is in the format
        * 2017-04-14T18:00:00Z
        * So we split it into the date part only for the app to use as date*/
        //Split the string --2017-04-14T18:00:00Z-- into different parts (as an array of Strings)
        // based on the "T" text. We take only the strings before the letter "T" that appears in the date.
        // The real date string will now be only 2017-04-14
        String datey =currentFootball.getDate();
        String[]parts= datey.split(DATE_SEPARATOR);
        String realDate = parts[0];

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        //dateTextView.setText(currentFootball.getDate());
        dateTextView.setText(realDate);


        // Set the proper background color on the odds2
        // Fetch the background from the TextView, which is a GradientDrawable
     /*   TextView odd1TextView = (TextView) listItemView.findViewById(R.id.o1);
        odd1TextView.setText(currentFootball.getOdd1());

        TextView odd2TextView = (TextView) listItemView.findViewById(R.id.o2);
        odd2TextView.setText(currentFootball.getOdd2());

        TextView odd3TextView = (TextView) listItemView.findViewById(R.id.o3);
        odd3TextView.setText(currentFootball.getodd3());*/


        return listItemView;
    }

    public void setFootball(ArrayList<Football> footballs) {
    }
}
