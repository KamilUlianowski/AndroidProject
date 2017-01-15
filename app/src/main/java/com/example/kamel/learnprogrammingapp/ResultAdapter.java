package com.example.kamel.learnprogrammingapp;

/**
 * Created by kamel on 15/01/2017.
 */


import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kamel on 08/11/2016.
 */

public class ResultAdapter  extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Result> mDataSource;

    public ResultAdapter(Context context, ArrayList<Result> items) {
        Context mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    //2
    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    //3
    @Override
    public long getItemId(int position) {
        return position;
    }

    //4
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = mInflater.inflate(R.layout.list_of_scores, parent, false);

        TextView index =
                (TextView) rowView.findViewById(R.id.index);
        TextView username =
                (TextView) rowView.findViewById(R.id.name);
        TextView course =
                (TextView) rowView.findViewById(R.id.course);
        TextView score =
                (TextView) rowView.findViewById(R.id.result);

        Result recipe = (Result) getItem(position);

// 2
        index.setText(position);
        username.setText(recipe.getUsername());
        course.setText(recipe.getCourse());
        score.setText(recipe.getResult());

// 3

        return rowView;
    }
}