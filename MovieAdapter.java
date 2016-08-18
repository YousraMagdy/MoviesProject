package com.thenewboston.movies_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;



public class MovieAdapter extends ArrayAdapter<Extra> {







    private Context context;
    private List<Extra> mData;
    public MovieAdapter(Context context, int  resource, List<Extra> objects) {
        super(context,0, objects);
        mData = objects;
        this.context= context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Extra extra = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.gridview_item, parent, false);
        }
        ImageView imageView=(ImageView) convertView.findViewById(R.id.imageView);
        // TextView over_view = (TextView) convertView.findViewById(R.id.textview1);
        //TextView ti_tle = (TextView) convertView.findViewById(R.id.textview2);



        Picasso.with(context)
                .load(extra.image)
                .into(imageView);
        return convertView;
    }


}


