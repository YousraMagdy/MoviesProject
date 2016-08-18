package com.thenewboston.movies_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;



public class MainFragment extends Fragment {
    private static final String LOG_TAG = MainFragment.class.getSimpleName();

    private MovieAdapter mMovieAdapter;


    public MainFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.

        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMovieAdapter = new
                MovieAdapter(
                getActivity(), // The current context (this activity)
                R.layout.gridview_item, // The name of the layout ID.
                new ArrayList<Extra>());


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.listview_forecast);
        gridView.setAdapter(mMovieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                                Extra forecast = mMovieAdapter.getItem(position);
                                                Intent intent = new Intent(getActivity(), DetailActivity.class)
                                                        .putExtra(Intent.EXTRA_TEXT, forecast.Title)
                                                        .putExtra("image", forecast.image)
                                                        .putExtra("overview", forecast.OverView)
                                                        .putExtra("vote", forecast.Vote)
                                                        .putExtra("date", forecast.Date);

                                                startActivity(intent);
                                            }});

        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.v(LOG_TAG, "pass here");

        FetchMovieTask movieTask = new FetchMovieTask();
        movieTask.execute("");
        Log.v(LOG_TAG, "pass here2");


    }


    public class FetchMovieTask extends AsyncTask<String , Void, Extra[]> {


        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        private Extra[] getMovieDataFromJson(String movieJsonStr)
                throws JSONException {


            // These are the names of the JSON objects that need to be extracted.

            final String OWM_LIST = "results";
            final String OWM_Image = "poster_path";
            final String OWM_OVERVIEW = "overview";
            final String OWM_TITLE = "original_title";
            final String OWM_VOTE = "vote_average";
            final String OWM_DATE = "release_date";

            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(OWM_LIST);


            int number = movieArray.length();
            String[] resultStrs = new String[number];
            String[] resultStrs1 = new String[number];
            String[] resultStrs2 = new String[number];
            String[] resultStrs3 = new String[number];
            String[] resultStrs4 = new String[number];
            //added

            final String OWM_TOTAL = "http://image.tmdb.org/t/p/w185/";

            Extra [] extra = new Extra [movieArray.length()];


            for (int i = 0; i < movieArray.length(); i++) {

                String description;
                String over;
                String title;
                String vote;
                String date;

                JSONObject dayMovies = movieArray.getJSONObject(i);


                over = dayMovies.getString(OWM_OVERVIEW);

                description = OWM_TOTAL+dayMovies.getString(OWM_Image);
                title = dayMovies.getString(OWM_TITLE);
                vote = dayMovies.getString(OWM_VOTE);
                date = dayMovies.getString(OWM_DATE);



                resultStrs[i] = description ;
                resultStrs1[i] = over;
                resultStrs2[i] = title;
                resultStrs3[i] = vote;
                resultStrs4[i] = date;


                extra[i] = new Extra(title,description,over,vote,date);

            }

            for (String s : resultStrs) {
                Log.v(LOG_TAG, "Movies entry: " + s);
            }
            for (String o : resultStrs2) {
                Log.v(LOG_TAG, "Title: " + o);
            }



            return extra;

        }




        @Override
        protected Extra[] doInBackground(String... params) {

            Log.v(LOG_TAG, "output is");

            // If there's no zip code, there's nothing to look up.  Verify size of params.
            if (params.length == 0) {

                return null;
            }

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            SharedPreferences sharedPrefs =
                    PreferenceManager.getDefaultSharedPreferences(getActivity());

            String sorting = sharedPrefs.getString(
                    getString(R.string.pref_sort_by_key),
                    getString(R.string.pref_sort_by_rates));

            Log.v(LOG_TAG, sorting);
            String output;
            if (sorting.equals("popular")){
                Log.v(LOG_TAG, "number1");
                output="popular";
            }
            else if(sorting.equals("top_rated")){
                Log.v(LOG_TAG, "Number2");
                output="top_rated";  }
            else{
                Log.v(LOG_TAG, "errorrrrrrr");

                output="top_rated";
            }

            try {


                final String FORECAST_BASE_URL =

                        //  "https://api.themoviedb.org/3/discover/movie?sort_by=rates-desc&api_key=47a83e8b6ea24d82f5e1befdee45f893";

                        "http://api.themoviedb.org/3/movie/"+output+"?";



                final String APPID_PARAM = "api_key";
                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()

                        .appendQueryParameter(APPID_PARAM, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                Log.v(LOG_TAG, "Step0");
                urlConnection.setRequestMethod("GET");
                Log.v(LOG_TAG, "Step00");
                Log.v(LOG_TAG, builtUri.toString());
                urlConnection.connect();
                Log.v(LOG_TAG, "Step1");

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    Log.v(LOG_TAG, "Step2");
                    // Nothing to do.
                    return null;

                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();

                Log.v(LOG_TAG, "Forecast string: " + movieJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error occured ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                //return getWeatherDataFromJson(forecastJsonStr, numDays);
                return getMovieDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }

        @Override
        protected void onPostExecute(Extra[] result) {
            if (result != null) {
                mMovieAdapter.clear();
                for (Extra dayMovieStr : result) {
                    mMovieAdapter.add(dayMovieStr);

                }
                // New data is back from the server.  Hooray!
            }
        }
    }




}