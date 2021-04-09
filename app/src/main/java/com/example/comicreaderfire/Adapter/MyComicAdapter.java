package com.example.comicreaderfire.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicreaderfire.ChapterActivity;
import com.example.comicreaderfire.Interface.IRecyclerOnClick;
import com.example.comicreaderfire.Models.Comic;
import com.example.comicreaderfire.Models.Common;
import com.example.comicreaderfire.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyComicAdapter extends RecyclerView.Adapter<MyComicAdapter.MyViewHolder> {


    Context context;
    List<Comic> comicList;
    LayoutInflater inflater;

    public MyComicAdapter(Context context, List<Comic> comicList) {
        this.context = context;
        this.comicList = comicList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyComicAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.comic_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyComicAdapter.MyViewHolder holder, int position) {
        Picasso.get().load(comicList.get(position).Image).into(holder.imageView);
        holder.manga_name.setText(comicList.get(position).Name );



        holder.setiRecyclerOnClick(new IRecyclerOnClick() {
            @Override
            public void onClick(View view, int position) {
                //Start new Activity
                context.startActivity(new Intent(context, ChapterActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                Common.selected_Comic = comicList.get(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView manga_name;

        IRecyclerOnClick iRecyclerOnClick;

        public void setiRecyclerOnClick(IRecyclerOnClick iRecyclerOnClick) {
            this.iRecyclerOnClick = iRecyclerOnClick;
        }


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view);
            manga_name = itemView.findViewById(R.id.manga_name);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            iRecyclerOnClick.onClick(v,getAdapterPosition());
        }
    }
}