package com.example.jitu.searchexample1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by jitu on 10/27/2016.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private ImageLoader imageLoader;
    public Context context;
    List<SuperHeroes> superHeroes;

    public CardAdapter(List<SuperHeroes> superHeroes, Context context) {
        super();
        this.superHeroes = superHeroes;
        this.context = context;
        Toast.makeText(context,""+superHeroes,Toast.LENGTH_LONG).show();
        Log.d("fff", String.valueOf(superHeroes));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.superheroes_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final SuperHeroes superHero = superHeroes.get(position);

        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(superHero.getImageUrl(), ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        holder.imageView.setImageUrl(superHero.getImageUrl(), imageLoader);
        holder.textViewName.setText(superHero.getName());
        holder.textViewRank.setText(String.valueOf(superHero.getRank()));

        //////////////////////////////////////////////////////////////////////////
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context.getApplicationContext(), "Name is "+superHero.getName()+"service is "+superHero.getPublisher()+"hbfgh"+superHero.getImageUrl(), Toast.LENGTH_LONG).show();
                Intent newintent = new Intent();
                //SuperHeroes s=new SuperHeroes();
                //String s1=s.getName();
                newintent.setClass(context, DataRetriveRecycle.class);
                newintent.putExtra("Value11", superHero.getImageUrl());
                newintent.putExtra("Value21", superHero.getName());
                newintent.putExtra("Value31", superHero.getRank());
                newintent.putExtra("Value41", MainActivity.str);
                context.startActivity(newintent);
                // Toast.makeText(context, superHero.getName()+" " + MainActivity.str, Toast.LENGTH_LONG).show();


            }
        });
        //////////////////////////////////////////////////
    }

    @Override
    public int getItemCount() {
        return superHeroes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public NetworkImageView imageView;
        public TextView textViewName;
        public TextView textViewRank;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewHero);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewRank = (TextView) itemView.findViewById(R.id.textViewRank);
        }
    }
}
