package com.thenewboston.movies_project;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thenewboston.movies_project.data.MovieContract;

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


public class Trailersfragment extends Fragment {

    private static final String LOG_TAG = Trailersfragment.class.getSimpleName();
public static String output;
    public static String  output1 ;
    public static String output2;
    public static String[] output3;
    public static String output4;
    private MovieAdapter mMovieAdapter;


    public Trailersfragment() {
    //    FetchMovieTask movieTask = new FetchMovieTask();
      //  movieTask.execute("");
        Log.v(LOG_TAG, "Starting");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        Log.v(LOG_TAG, "starting11");
        setHasOptionsMenu(true);

    }


    private String mMovieStr;
    private String mMovieStr1;
    private String mMovieStr2;
    private String mMovieStr3;
    private String mMovieStr4;
    private String mMovieStr5;

    private String title1 = "TITLE:";
    private String description1 = "DESCRIPTION:";
    private String rate1 = "RATE:";
    private String date1 = "Release_Date:";
    private ArrayAdapter<String> mForecastAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_trailers, container, false);

        Log.v(LOG_TAG, "Do trailor first");
        // The detail Activity called via intent.  Inspect the intent for forecast data.
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            Log.v(LOG_TAG, "It is ok");
            mMovieStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            ((TextView) rootView.findViewById(R.id.textview1))
                    .setText(title1 + mMovieStr);

            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
            mMovieStr1 = intent.getStringExtra("image");
            Picasso.with(getActivity())
                    .load(mMovieStr1)
                    .into(imageView);


            ((TextView) rootView.findViewById(R.id.textview4))
                    .setText(mMovieStr1);

            mMovieStr2 = intent.getStringExtra("overview");
            ((TextView) rootView.findViewById(R.id.textview2))
                    .setText(description1 + mMovieStr2);

            mMovieStr3 = intent.getStringExtra("vote");
            ((TextView) rootView.findViewById(R.id.textview3))
                    .setText(rate1 + mMovieStr3);

            mMovieStr4 = intent.getStringExtra("date");
            ((TextView) rootView.findViewById(R.id.textview4))
                    .setText(date1 + mMovieStr4);

            ((TextView) rootView.findViewById(R.id.textview5))
                    .setText("vedio here");




            mForecastAdapter =
                    new ArrayAdapter<String>(
                            getActivity(), // The current context (this activity)
                            R.layout.list_item_forecast, // The name of the layout ID.
                            R.id.list_item_forecast_textview, // The ID of the textview to populate.
                           new ArrayList<String>());
           //Countries);


            // Get a reference to the ListView, and attach this adapter to it.
            ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
            listView.setAdapter(mForecastAdapter);




            ////////////////////////////////////////////////////////////////////////////

            FetchMovieTask movieTask = new FetchMovieTask();
            movieTask.execute("");
            FetchMovieTask1 movieTask1 = new FetchMovieTask1();
            movieTask1.execute("");




            mMovieStr5 = intent.getStringExtra("id");
            Log.v(LOG_TAG, "what is typing");
               Log.v(LOG_TAG, mMovieStr5);


            Button button;
            button = (Button) rootView.findViewById(R.id.button2);


            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Log.v(LOG_TAG, "That is rttt");
                   // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=cxLG2wtE7TM")));
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+output)));


                }
            });

            ((TextView) rootView.findViewById(R.id.textview6))
                    .setText("Reviews");


        } else {
            Log.v(LOG_TAG, "That is wrong");
        }

        Button button1;
        button1 = (Button) rootView.findViewById(R.id.button1);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues movieValues = new ContentValues();

               // movieValues.put(WeatherEntry.COLUMN_LOC_KEY, locationId);
                movieValues.put(MovieContract.MovieEntry.COLUMN_TITLE, mMovieStr);

            String moviedb= movieValues.getAsString(MovieContract.MovieEntry.COLUMN_TITLE);
                Log.v(LOG_TAG, "GET from database");
                Log.v(LOG_TAG, moviedb);

            }

        });





        return rootView;
    }



    public class FetchMovieTask extends AsyncTask<String , Void, String[]> {



        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();


        private String [] getMovieDataFromJson(String movieJsonStr)
                throws JSONException {

            Log.v(LOG_TAG, "Do asynctask first");
            Log.v(LOG_TAG, "starting2");
            // These are the names of the JSON objects that need to be extracted.

            final String OWM_LIST = "results";
            final String OWM_KEY = "key";

            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(OWM_LIST);


            String[] resultStrs = new String[1];


            for (int i = 0; i < 1; i++) {


                String key;

                JSONObject dayMovies = movieArray.getJSONObject(i);


                key = dayMovies.getString(OWM_KEY);



                resultStrs[i] = key;





            }

            for (String s : resultStrs) {
                Log.v(LOG_TAG, "starting3");
                Log.v(LOG_TAG, "Movies entry: " + s);


 output=s;
            }




            return resultStrs;

        }




        @Override
        protected String[] doInBackground(String... params) {

            Log.v(LOG_TAG, "Do in backgnd first");
            Log.v(LOG_TAG, "output is");


            // If there's no zip code, there's nothing to look up.  Verify size of params.
            if (params.length == 0) {

                return null;
            }

            // These two need to be declared outside the try/catch4
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;



           try {
                Log.v(LOG_TAG, "starting5");
          //     Intent intent = getActivity().getIntent();
            //   mMovieStr5 = intent.getStringExtra("id");
               Log.v(LOG_TAG, "Type mMovie5");
               Log.v(LOG_TAG, mMovieStr5);
                final String Trailors_BASE_URL =


                        //  "http://api.themoviedb.org/3/movie/15285/videos?";
                        "http://api.themoviedb.org/3/movie/"+mMovieStr5+"/videos?";


                Log.v(LOG_TAG, "Starting4");

                final String APPID_PARAM = "api_key";
                Uri builtUri = Uri.parse(Trailors_BASE_URL).buildUpon()

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


    }
    public  class FetchMovieTask1 extends AsyncTask<String , Void, String[]> {
        private final String LOG_TAG = FetchMovieTask1.class.getSimpleName();


        private String [] getMovieDataFromJson1(String movieJsonStr1)
                throws JSONException {

            final String OWM_LIST = "results";
            final String OWM_Author = "author";
            final String OWM_Content = "content";

            JSONObject movieJson1 = new JSONObject(movieJsonStr1);
            JSONArray movieArray1 = movieJson1.getJSONArray(OWM_LIST);


            int number1 = movieArray1.length();
            String[] reviewStrs = new String[number1];
            String[] reviewStrs1 = new String[number1];
            String[] reviewss=new String[number1];

            Log.v(LOG_TAG, "In the second asynctask");

            for (int j = 0; j < movieArray1.length(); j++) {

                String author;
                String content;


                JSONObject dayMovies1 = movieArray1.getJSONObject(j);
                Log.v(LOG_TAG, "First occured");

                author = dayMovies1.getString(OWM_Author);


                content = dayMovies1.getString(OWM_Content);


                reviewStrs[j] = author;
                reviewStrs1[j] = content;

        output2=author+":"+content;
              //  Log.v(LOG_TAG, output2);
reviewss[j]=output2;

        //       output1=reviewStrs[0];
          //     output2=reviewStrs1[0];
           // output3[j]=output2;
              //  output4=reviewStrs1[1];

                Log.v(LOG_TAG, "third occured");
            }
            Log.v(LOG_TAG, "Let's see the result");

            for (String s1 : reviewStrs) {

                Log.v(LOG_TAG, "authors: " + s1);

            }
            for (String o : reviewStrs1) {
                Log.v(LOG_TAG, "content: " + o);

            }

            ///////////////////////////////////////////


            ///////////////////////////////////////////


            return reviewss;
        }


        @Override
        protected String[] doInBackground(String... params) {
            if (params.length == 0) {

                return null;
            }

            // These two need to be declared outside the try/catch4
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr1 = null;



            try {

                //     Intent intent = getActivity().getIntent();
                //   mMovieStr5 = intent.getStringExtra("id");
                Log.v(LOG_TAG, "In the new backgnd");
                Log.v(LOG_TAG, mMovieStr5);
                final String Reviews_BASE_URL =


                        //  "http://api.themoviedb.org/3/movie/15285/videos?";
                        "http://api.themoviedb.org/3/movie/"+mMovieStr5+"/reviews?";


                Log.v(LOG_TAG, "Starting4");

                final String APPID_PARAM = "api_key";
                Uri builtUri = Uri.parse(Reviews_BASE_URL).buildUpon()

                        .appendQueryParameter(APPID_PARAM, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");

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

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr1 = buffer.toString();

                Log.v(LOG_TAG, "Reviews string: " + movieJsonStr1);
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
                return getMovieDataFromJson1(movieJsonStr1);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.

            return null;

        }
        @Override
        protected void onPostExecute(String[] result) {
            if (result != null && mForecastAdapter != null) {
                Log.e(LOG_TAG, "Here");
               mForecastAdapter.clear();
                for(String dayForecastStr : result) {
                    mForecastAdapter.add(dayForecastStr);
                    Log.e(LOG_TAG,dayForecastStr);
               }
                // New data is back from the server.  Hooray!
            }
        }
    }




}
