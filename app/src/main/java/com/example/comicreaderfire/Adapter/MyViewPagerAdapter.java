package com.example.comicreaderfire.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.comicreaderfire.Models.Link;
import com.example.comicreaderfire.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyViewPagerAdapter extends PagerAdapter {

    Context context;
    List<String> linkList;
    LayoutInflater layoutInflater;

    public MyViewPagerAdapter(Context context, List<String> linkList) {
        this.context = context;
        this.linkList = linkList;
        layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return linkList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View image_layout = layoutInflater.inflate(R.layout.view_pager_item,container,false);

        PhotoView page_image = image_layout.findViewById(R.id.page_image);
        Picasso.get().load(linkList.get(position)).into(page_image);
        container.addView(image_layout);

        return image_layout;


    }
}
