package com.example.android.footballlivescores;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class FootballActivity extends AppCompatActivity implements LoaderCallbacks<List<Football>> {

    /*Tag for log messages*/
    private static final String LOG_TAG = FootballActivity.class.getName();
    private static String QUERY_URL = "https://api.football-data.org/v1/fixtures";
    private FootballAdapter mAdapter;

    @Override
    public Loader<List<Football>> onCreateLoader(int i, Bundle bundle) {
        // Create a new Loader for the given URL
        Log.i(LOG_TAG, "Creating a new loader for the given URL ");
        return new FootballLoader(this, QUERY_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Football>> loader, List<Football> footballs) {
        //Update the UI with the result
        // same as onPostExecute method of the AsyncTask
        Log.i(LOG_TAG, "onLoadFinish--clearing the adapter\n");
        // Clear the adapter of previous earthquake_data
        mAdapter.clear();
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (footballs != null && !footballs.isEmpty()) {
            mAdapter.addAll(footballs);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Football>> loader) {
        //TODO:Loader reset, so we can clear out our existing data
        Log.i(LOG_TAG, "RESETTING LOADER,CLEARING EXISTING DATA");
        mAdapter.setFootball(new ArrayList<Football>());
        // Loader reset, so we can Clear out our existing data
        mAdapter.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football);



       /* ArrayList<Football>words = new ArrayList<>();
        words.add(new Football("Sevilla", "Manchester City", "4","3", "1st of March", "8:45pm"));
       words.add(new Football("Atletico Madrid", "Leicester City","3","3", "4th of May", "8:45pm"));
        words.add(new Football("Manchester United", "Chelsea", "2", "1", "11th of May", "4:00pm"));
        words.add(new Football("Juventus", "Barcelona", "2", "4","14th of April","6:00pm"));
        words.add(new Football("Arsenal", "Manchester City", "3", "3", "12th of May", "5:00pm"));
        words.add(new Football("Bayern Munich", " Real Madrid", "4","3","16th of May","8:45pm"));
        words.add(new Football("AS Monaco","Borussia Dortmund","3","5", "12th of April", "9:00pm"));
        words.add(new Football("PSV","Fulham","2","0","4th of June","2:00pm"));
*/
//        ArrayAdapter<Football> itemsAdapter = new ArrayAdapter<Football>(
//                /*context*/this,
//                /*layout file*/R.layout.list_item,
//                /*list of objects*/words);
//        ListView listView = (ListView)findViewById(R.id.list);
//        listView.setAdapter(itemsAdapter);

        mAdapter = new FootballAdapter(this, new ArrayList<Football>());
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(mAdapter);


        LoaderManager loaderManager = getLoaderManager();
        // for calling the LoaderManager in the AsyncTaskLoader class.
        loaderManager.initLoader(1, null, this).forceLoad();


    }
}
