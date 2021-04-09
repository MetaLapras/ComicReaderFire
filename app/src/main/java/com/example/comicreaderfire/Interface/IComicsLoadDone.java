package com.example.comicreaderfire.Interface;

import com.example.comicreaderfire.Models.Comic;

import java.util.List;

public interface IComicsLoadDone {
    void onIComicsLoadDoneListener(List<Comic> comicList);
}
