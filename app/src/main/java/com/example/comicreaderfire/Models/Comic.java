package com.example.comicreaderfire.Models;

import java.util.List;

public class Comic {

    public String Name ;
    public String Image ;
    public String Category ;
    public List<Chapter> Chapters ;

    public Comic() {
    }

    @Override
    public String toString() {
        return "Comic{" +
                "Name='" + Name + '\'' +
                ", Image='" + Image + '\'' +
                ", Category='" + Category + '\'' +
                ", Chapters=" + Chapters +
                '}';
    }


}
