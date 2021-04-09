package com.example.comicreaderfire.Models;

import java.util.List;

public class Chapter {
    public String Name ;
    public List<String> Links ;

    public Chapter() {
    }


    @Override
    public String toString() {
        return "Chapter{" +
                "Name='" + Name + '\'' +
                ", Links=" + Links +
                '}';
    }
}
