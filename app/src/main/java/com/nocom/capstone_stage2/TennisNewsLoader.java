package com.nocom.capstone_stage2;

import android.content.Context;
import android.os.Looper;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONException;

import java.util.ArrayList;

public  class TennisNewsLoader extends AsyncTaskLoader<ArrayList<Tennis>> {
    private static final String LOG_TAG = TennisNewsLoader.class.getName();
    ArrayList<Tennis> mNewsData;
    private String murl;

    public TennisNewsLoader(Context context, String url) {
        super(context);
        murl = url;
    }
    @Override
    protected void onStartLoading() {

        if (mNewsData != null) {
            deliverResult(mNewsData);
        } else {
            forceLoad();

        }
    }
    @Override
    public ArrayList<Tennis> loadInBackground() {

        if (Looper.myLooper()==null)
            Looper.prepare();
        // Perform the network request, parse the response, and extract a list of movies.
        ArrayList<Tennis> movies = null;
        try {
            movies = QueryUtlis.featchrecipedata(murl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
