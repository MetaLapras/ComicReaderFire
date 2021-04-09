package com.example.comicreaderfire.Models;

public class Link {
    public int ID ;
    public String Link ;
    public int ChapterID ;

    public Link() {
    }

    public Link(int ID, String link, int chapterID) {
        this.ID = ID;
        Link = link;
        ChapterID = chapterID;
    }

    @Override
    public String toString() {
        return "Link{" +
                "ID=" + ID +
                ", Link='" + Link + '\'' +
                ", ChapterID=" + ChapterID +
                '}';
    }
}
