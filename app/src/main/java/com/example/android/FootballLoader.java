package com.example.android.footballlivescores;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by AGUELE OSEKUEMEN JOE on 4/11/2017.
 */

public class FootballLoader extends AsyncTaskLoader<List<Football>> {

    /*Tag for log messages*/
    private static final String LOG_TAG = FootballLoader.class.getName();
    /*Query URL*/
    private String mUrl;


    /*Constructs a new {@link FootballLoader}
    *@param context of the activity
    *@param url to load data from*/
    public FootballLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }


    @Override
    protected void onStartLoading() {
        forceLoad();

    }

    // This is on a background thread
    @Override
    public List<Football> loadInBackground() {
        Log.i(LOG_TAG, "LOAD IN BACKGROUND RUNNING");
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of football games
        List<Football> footballs = QueryUtils.fetchFootballData(mUrl);
        return footballs;
        // load in background works like the AsyncTask doInBackground method.
    }

}
