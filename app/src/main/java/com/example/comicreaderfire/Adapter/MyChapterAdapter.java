package com.example.comicreaderfire.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicreaderfire.Interface.IRecyclerOnClick;
import com.example.comicreaderfire.Models.Chapter;
import com.example.comicreaderfire.Models.Common;
import com.example.comicreaderfire.R;
import com.example.comicreaderfire.ViewDetailsActivity;

import java.util.List;

public class MyChapterAdapter extends  RecyclerView.Adapter<MyChapterAdapter.MyViewHolder>  {

    Context context;
    List<Chapter> chapterList;

    public MyChapterAdapter(Context context, List<Chapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
    }

    @NonNull
    @Override
    public MyChapterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.chapter_item,parent,false);
        return new MyChapterAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyChapterAdapter.MyViewHolder holder, int position) {
        holder.txt_chapter_number.setText(new StringBuilder(chapterList.get(position).Name));

//        Common.selected_Chapter = chapterList.get(position);
//        Common.chapter_index = position;


        holder.setiRecyclerOnClick(new IRecyclerOnClick() {
            @Override
            public void onClick(View view, int position) {
                Common.selected_Chapter = chapterList.get(position);
                Common.chapter_index = position;
                //Start new activity
                context.startActivity(new Intent(context, ViewDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });


    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_chapter_number;
        IRecyclerOnClick iRecyclerOnClick;

        public void setiRecyclerOnClick(IRecyclerOnClick iRecyclerOnClick) {
            this.iRecyclerOnClick = iRecyclerOnClick;
        }


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_chapter_number = itemView.findViewById(R.id.txt_chapter_number);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            iRecyclerOnClick.onClick(v,getAdapterPosition());
        }
    }
}