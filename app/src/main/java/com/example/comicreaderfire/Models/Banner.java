package com.example.comicreaderfire.Models;

import java.util.List;

public class Banner {
    public List<String> Banners;
    public List<Comic> Comic;

    public Banner() {
    }

    public Banner(List<String> banners, List<com.example.comicreaderfire.Models.Comic> comic) {
        Banners = banners;
        Comic = comic;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "Banners=" + Banners +
                ", Comic=" + Comic +
                '}';
    }
}
