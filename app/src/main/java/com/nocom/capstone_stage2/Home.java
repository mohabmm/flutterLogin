package com.nocom.capstone_stage2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Moha on 1/25/2018.
 */



public class Home extends Fragment  {


    private static final int MOVIE_LOADER_ID = 2;
    final String BakingWebsite = "http://api.nytimes.com/svc/search/v2/articlesearch.json?q=tennis&?sort=newest&api-key=23b843ce687642739ffbbd75bc779a84";
    private final String LOG_TAG = TennisNewsLoader.class.getName();
    //  ArrayList<Recipe> mNewsData;
    public RecyclerView mrecyclerview;
    Parcelable savedRecyclerLayoutState;
    TextView emptytextview ;
    Bundle bundle;
    MainAdapter mMainAdapter;
    // the problem i think is here
    ArrayList<Tennis> recepieList ;

    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";

 //   LoaderManager loaderManager;
    TextView emptytext;
    ProgressBar progressBar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);


        progressBar = view. findViewById(R.id.loading_spinner);
        emptytext = view. findViewById(R.id.empty_view);
        mrecyclerview =  view.findViewById(R.id.mrecycle);



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        mrecyclerview.setLayoutManager(linearLayoutManager);
        mMainAdapter = new MainAdapter(recepieList,getContext());
        mrecyclerview.setAdapter(mMainAdapter);

        getLoaderManager().initLoader(MOVIE_LOADER_ID, null, loaderone);









        connect();

        return view ;
    }






    public LoaderManager.LoaderCallbacks<ArrayList<Tennis>> loaderone = new LoaderManager.LoaderCallbacks<ArrayList<Tennis>>() {
        @Override
        public android.support.v4.content.Loader<ArrayList<Tennis>> onCreateLoader(int id, Bundle args) {
            // Create a new loader for the given URL

            return new TennisNewsLoader(getActivity(),BakingWebsite);
        }

        @Override
        public void onLoadFinished(android.support.v4.content.Loader<ArrayList<Tennis>> loader, ArrayList<Tennis> data) {


            progressBar.setVisibility(View.INVISIBLE);

            if (savedRecyclerLayoutState != null) {

                mrecyclerview.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
            }

            if (data != null) {



                mMainAdapter.setWeatherData(data);




            } else {
                recepieList = data;

                Log.i("moha", "e7na gwa al else ");

            }

        }

        @Override
        public void onLoaderReset(android.support.v4.content.Loader<ArrayList<Tennis>> loader) {

        }


    };
    private Toast mToast;





    public void connect() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.restartLoader(MOVIE_LOADER_ID, bundle, loaderone);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
         //   emptytext.setText("No Internet Connection ");
            emptytextview.setText("no onternet conection ");
            progressBar.setVisibility(View.INVISIBLE);

            // Update empty state with no connection error message
        }

    }


}
