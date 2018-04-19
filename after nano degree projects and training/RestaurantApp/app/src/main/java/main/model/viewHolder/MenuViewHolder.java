package main.model.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import main.model.R;
import main.model.qnterface.ItemClickLisner;

/**
 * Created by Moha on 2/24/2018.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtMenuName;
    public ImageView imageView ;

    public ItemClickLisner itemClickLisner ;



    public MenuViewHolder(View itemView) {
        super(itemView);

        txtMenuName = itemView.findViewById(R.id.menu_text);
        imageView = itemView.findViewById(R.id.menu_image);


        itemView.setOnClickListener(this );

    }

    public  void setItemClickLisner (ItemClickLisner itemClickLisner ){

        this.itemClickLisner = itemClickLisner ;
    }

    @Override
    public void onClick(View view) {

        itemClickLisner.onClick(view,getAdapterPosition(),false);
    }
}
