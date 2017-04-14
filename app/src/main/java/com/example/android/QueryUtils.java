package com.example.android.footballlivescores;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AGUELE OSEKUEMEN JOE on 4/11/2017.
 */

public final class QueryUtils {


    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    /**
     * Query the website dataset and return a list of {@link Football} objects.
     */
    public static List<Football> fetchFootballData(String requestUrl) {
        // Create URL object

        Log.i(LOG_TAG, "Creating URL Object to fetch football data");
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Football}s
        List<Football> footballs = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Football}s
        return footballs;
    }


    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }


    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the football JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    /**
     * Return a list of {@link Football} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Football> extractFeatureFromJson(String footballJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(footballJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding football scores to
        List<Football> footballs = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject root = new JSONObject(footballJSON);

            JSONArray fixtures = root.getJSONArray("fixtures");
            //
            for (int i = 0; i < fixtures.length(); i++) {

                //
                JSONObject firstFootball /*equivalent of JSONObject firstCandy which has no name*/ = fixtures.getJSONObject(i);

                // Extract the value for the key called "homeTeamName"
                String homeTeamName = firstFootball.getString("homeTeamName");

                //Extract the key value for the key called "awayTeamName"
                String awayTeamName = firstFootball.getString("awayTeamName");

                // Extract the value for the key called "date"
                String date = firstFootball.getString("date");


                //Extract the value for the key called "time"
                String time = date.substring(11, 16);

                //adding pm to the time
                time = time + "pm";

                // Since the result is in another JSONObject bracket we transverse it again to the path
                // traversal path now on the JSONObject result
                JSONObject result = firstFootball.getJSONObject("result");

                // Extract the key value for the key called "goalsHomeTeam"
                String goalsHomeTeam = result.getString("goalsHomeTeam");

                //Extract the key value for the key called "goalsAwayTeam"
                String goalsAwayTeam = result.getString("goalsAwayTeam");



                /*Since the odds is in another JSONObject (bracket) we transverse it again to the path*/
                /*JSONObject odds = firstFootball.getJSONObject("odds");

                String odd1 = odds.getString("homeWin");

                String odd2 = odds.getString("draw");

                String odd3 = odds.getString("awayWin");

*/
                // Create a new {@link Football} object with the magnitude, location, time,
                // and url from the JSON response.
                Football football = new Football(homeTeamName, awayTeamName, goalsHomeTeam, goalsAwayTeam, time, date);
                // Add the new {@link Football} to the list of footballs.
                footballs.add(football);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the football JSON results", e);
        }

        // Return the list of football games
        return footballs;
    }
}
