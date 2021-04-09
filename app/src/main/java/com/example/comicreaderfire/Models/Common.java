package com.example.comicreaderfire.Models;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.util.ArrayList;
import java.util.List;

public class Common {

    public static Comic selected_Comic;
    public static Chapter selected_Chapter;
    public static List<Chapter> chapterList = new ArrayList<>();
    public static int chapter_index = -1;

    public static List<Comic> selected_comicList = new ArrayList<>();

    public static String formString(String name) {
        // If Character are too long just substring
        StringBuilder finalreuslt = new StringBuilder(name.length()>20 ?name.substring(0,20)+"....":name);
        return finalreuslt.toString();
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager != null)
        {
            NetworkInfo[] infos = connectivityManager.getAllNetworkInfo();
            if(infos!=null){
                for (int i = 0;i<infos.length;i++){
                    if(infos[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }

        }return false;
    }

    public static String[] categories = {
            "Action",
            "Adult",
            "Adventure",
            "Comedy",
            "Completed",
            "Cooking",
            "Doujinshi",
            "Drama",
            "Drop",
            "Ecchi",
            "Fantasy",
            "Gender bender",
            "Harem",
            "Historical",
            "Horror",
            "Jose",
            "Latest",
            "Manhua",
            "Manhwa",
            "Material arts",
            "Mature",
            "Mecha",
            "Medical",
            "Mystery",
            "Newest",
            "One shot",
            "Ongoing",
            "Psychological",
            "Romance",
            "School life",
            "Sci fi",
            "Seinen",
            "Shoujo",
            "Shoujo a",
            "Shounen",
            "Shounen ai",
            "Slice of life",
            "Smut",
            "Sports",
            "Superhero",
            "Supernatural",
            "Top Read",
            "Tragedy",
            "Webtoons",
            "Yaoi",
            "Yuri"
    };

}
