package com.example.comicreaderfire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comicreaderfire.Adapter.MyComicAdapter;
import com.example.comicreaderfire.Adapter.MySliderAdapter;
import com.example.comicreaderfire.Interface.IBannerLoadDone;
import com.example.comicreaderfire.Interface.IComicsLoadDone;
import com.example.comicreaderfire.Models.Comic;
import com.example.comicreaderfire.Models.Common;
import com.example.comicreaderfire.Services.PicassoImageLoadingService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import ss.com.bannerslider.Slider;

public class MainActivity extends AppCompatActivity implements IBannerLoadDone, IComicsLoadDone {
        Slider slider;
    String TAG = "-->";
    RecyclerView recyclerView_comic;
    TextView txt_comic;
    SwipeRefreshLayout swipeRefreshLayout ;

    //Database
    DatabaseReference banner,comics;
    //Listener
    IBannerLoadDone bannerListener;
    IComicsLoadDone comicsListener;
     AlertDialog dialog;
     ImageView  btn_Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    @SuppressLint("ResourceAsColor")
    private void init() {
        //init Firebase
        banner = FirebaseDatabase.getInstance().getReference("Banners");
        comics = FirebaseDatabase.getInstance().getReference("Comic");

        //init Listener
        bannerListener = this;
        comicsListener = this;

        txt_comic =findViewById(R.id.txt_comic);

        //View
        slider = findViewById(R.id.banner_slider);
        Slider.init(new PicassoImageLoadingService());

        // recycler view
        recyclerView_comic = findViewById(R.id.recycler_comic);
        recyclerView_comic.setHasFixedSize(true);
        recyclerView_comic.setLayoutManager(new GridLayoutManager(this,2));

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(
                android.R.color.holo_green_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_orange_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBanner();
                loadComic();
                /*if(Common.isConnectedToInternet(getBaseContext())){

                }else {
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        // Default load
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadBanner();
                loadComic();
                /*if(Common.isConnectedToInternet(getBaseContext())){

                }else {
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        btn_Search = findViewById(R.id.btn_show_filter_search);
        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FilterSearchActivity.class));
            }
        });

    }

    private void loadComic() {
        dialog = new SpotsDialog.Builder().setContext(this).setMessage("Please wait....").build();
        dialog.show();
        comics.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Comic> comic_load = new ArrayList<>();
                for (DataSnapshot comicSnapShots : dataSnapshot.getChildren()){
                    Comic comic = comicSnapShots.getValue(Comic.class);
                    comic_load.add(comic);
                    Log.d(TAG, "onDataChange: "+comic_load.toString());

                    // Call Listener
                    comicsListener.onIComicsLoadDoneListener(comic_load);
                    dialog.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                if(swipeRefreshLayout.isRefreshing())
                    dialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    private void loadBanner() {
        banner.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> bannerList = new ArrayList<>();
                for (DataSnapshot bannerSanpShot : dataSnapshot.getChildren()){
                    String image = bannerSanpShot.getValue(String.class);
                    bannerList.add(image);
                    Log.d(TAG, "onDataChange: "+bannerList.toString());

                    // Call Listener
                    bannerListener.onBannerLoadDoneLister(bannerList);
                        swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onBannerLoadDoneLister(List<String> banners) {
        slider.setAdapter(new MySliderAdapter(banners));
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onIComicsLoadDoneListener(List<Comic> comicList) {
        Common.selected_comicList = comicList;
        recyclerView_comic.setAdapter(new MyComicAdapter(getApplicationContext(),comicList));
        txt_comic.setText(new StringBuilder("Comics (")
                .append(comicList.size())
                .append(")"));
        if(swipeRefreshLayout.isRefreshing())
            dialog.dismiss();
        swipeRefreshLayout.setRefreshing(false);
    }
}
