package com.example.jitu.searchexample1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by jitu on 10/31/2016.
 */

public class CardAdapter1 extends RecyclerView.Adapter<CardAdapter1.ViewHolder> {
    //Imageloader to load image
    private ImageLoader imageLoader;
    private Context context;
    //List to store all superheroes
    List<SuperHeroes> superHeroes1;

    //Constructor of this class
    public CardAdapter1(List<SuperHeroes> superHeroes1, Context context) {
        super();
        //Getting all superheroes
        this.superHeroes1 = superHeroes1;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.superheroes_list1, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Getting the particular item from the list
        final SuperHeroes superHero1 = superHeroes1.get(position);

        //Loading image from url
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(superHero1.getImageUrl(), ImageLoader.getImageListener(holder.imageView, R.drawable.image, android.R.drawable.ic_dialog_alert));

        //Showing data on the views
        holder.imageView.setImageUrl(superHero1.getImageUrl(), imageLoader);
        holder.textViewName.setText(superHero1.getName());
        holder.textViewPublisher.setText(superHero1.getPublisher());
        holder.textViewUserinfo.setText(superHero1.getUserinfo());

//***********************************************************************************************************************************
//event listener of each item in the recycle view//calling of ProviderDetails class
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context.getApplicationContext(), "Name is "+superHero.getName()+"service is "+superHero.getPublisher()+"hbfgh"+superHero.getImageUrl(), Toast.LENGTH_LONG).show();
                Intent newintent = new Intent();
                newintent.setClass(context, ProviderDetails.class);
                newintent.putExtra("Value1", superHero1.getName());
                newintent.putExtra("Value2", superHero1.getPublisher());
                newintent.putExtra("Value3", superHero1.getImageUrl());
                context.startActivity(newintent);
            }
        });
//**************************************************************************************************************************************
    }

    @Override
    public int getItemCount() {
        return superHeroes1.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //Views
        public NetworkImageView imageView;
        public TextView textViewName;
        public TextView textViewPublisher;
        public TextView textViewUserinfo;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewHero1);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName1);
            textViewPublisher = (TextView) itemView.findViewById(R.id.textViewPublisher1);
            textViewUserinfo = (TextView) itemView.findViewById(R.id.textViewUserinfo1);
        }
    }
}
