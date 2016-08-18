package com.thenewboston.movies_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {

        private static final String LOG_TAG = DetailFragment.class.getSimpleName();

        private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
        private String mMovieStr;
        private String mMovieStr1;
        private String mMovieStr2;
        private String mMovieStr3;
        private String mMovieStr4;
        private String title1="TITLE:";
        private String description1="DESCRIPTION:";
        private String rate1="RATE:";
        private String date1="Release_Date:";
        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            // The detail Activity called via intent.  Inspect the intent for forecast data.
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                Log.v(LOG_TAG, "It is ok" );
                mMovieStr = intent.getStringExtra(Intent.EXTRA_TEXT);
                ((TextView) rootView.findViewById(R.id.textview1))
                        .setText(title1+mMovieStr);

             ImageView imageView=(ImageView)rootView.findViewById(R.id.imageView);
                mMovieStr1 = intent.getStringExtra("image");
                Picasso.with(getActivity())
                        .load(mMovieStr1)
                        .into(imageView);


              //  ((TextView) rootView.findViewById(R.id.textview4))
                //        .setText(mMovieStr1);

                mMovieStr2 = intent.getStringExtra("overview");
                ((TextView) rootView.findViewById(R.id.textview2))
                        .setText(description1+mMovieStr2);

                mMovieStr3 = intent.getStringExtra("vote");
                ((TextView) rootView.findViewById(R.id.textview3))
                        .setText(rate1+mMovieStr3);

                mMovieStr4 = intent.getStringExtra("date");
                ((TextView) rootView.findViewById(R.id.textview4))
                        .setText(date1+mMovieStr4);




            }
            else {
                Log.v(LOG_TAG, "That is wrong");
            }

            return rootView;
        }


    }
}