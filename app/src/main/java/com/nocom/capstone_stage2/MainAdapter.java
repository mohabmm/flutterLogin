
package com.nocom.capstone_stage2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class MainAdapter extends RecyclerView.Adapter<MainAdapter.adapterViewHolder>  {
    static private ListItemClickListener mOnClickListener;


    Context mContext;
    ArrayList<Tennis> recipes;


    public MainAdapter( ArrayList<Tennis> items, Context context) {

        mContext = context;
        recipes = items;
    }

    @Override
    public adapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.main_adapter;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new adapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(adapterViewHolder holder, final int position) {

        final Tennis currentRecipie = recipes.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(currentRecipie.getMweburl()));
                mContext.startActivity(intent);

            }
        });








        holder.shortdes.setText(currentRecipie.getMarticleheadline());
        holder.describtion.setText(currentRecipie.getMsnippet());


        String temp ="https://static01.nyt.com/images/2018/01/17/sports/17tennis-men1/merlin_132343739_8018a288-07ae-48ed-ac71-deedcd7ad757-master768.jpg";


        String myimage ="https://static01.nyt.com/"+currentRecipie.getMimageurl().replace("master768","articleLarge");
        Log.i("that it pls ",myimage);

            Picasso
                    .with(holder.image.getContext())
                    .load(myimage)
                    .into(holder.image);


        }



    @Override
    public int getItemCount() {

        if (null == recipes) return 0;
        return recipes.size();

    }

    public void setWeatherData(ArrayList<Tennis> weatherData) {
        recipes = weatherData;
        notifyDataSetChanged();
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


    public  class adapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        public TextView shortdes;
        public  TextView describtion;
        public adapterViewHolder(View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.thumbnail);
            shortdes = (TextView) itemView.findViewById(R.id.title);
            describtion = (TextView) itemView.findViewById(R.id.subtitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }

    }
}
