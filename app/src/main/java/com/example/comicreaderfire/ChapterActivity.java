package com.example.comicreaderfire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comicreaderfire.Adapter.MyChapterAdapter;
import com.example.comicreaderfire.Adapter.MyComicAdapter;
import com.example.comicreaderfire.Models.Comic;
import com.example.comicreaderfire.Models.Common;

public class ChapterActivity extends AppCompatActivity {


    RecyclerView recycler_chapter;
    TextView txt_chapter;
    Toolbar toolbar;
    String TAG = "-->";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        init();

    }

    private void init() {
        txt_chapter = findViewById(R.id.txt_chapter);

        toolbar = findViewById(R.id.tool_bar);

       toolbar.setTitle(Common.selected_Comic.Name);
        toolbar.setNavigationIcon(R.drawable.ic_navigate_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish(); //Go Back
            }
        });

        recycler_chapter = findViewById(R.id.recycler_chapter);
        recycler_chapter.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler_chapter.setLayoutManager(linearLayoutManager);
        recycler_chapter.addItemDecoration(new DividerItemDecoration(this,linearLayoutManager.getOrientation()));

        fetchChapter(Common.selected_Comic);

    }

    private void fetchChapter(Comic selected_comic) {
        if(selected_comic.Chapters !=null){
            Common.chapterList  = selected_comic.Chapters;
            recycler_chapter.setAdapter(new MyChapterAdapter(this,selected_comic.Chapters));
            txt_chapter.setText(new StringBuilder("Chapters (")
                    .append(selected_comic.Chapters.size())
                    .append(")"));

        }else {
            Toast.makeText(ChapterActivity.this, "No Chapter available now...", Toast.LENGTH_SHORT).show();
        }


    }
}
