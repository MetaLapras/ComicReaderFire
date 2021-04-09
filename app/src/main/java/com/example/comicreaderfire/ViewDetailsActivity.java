package com.example.comicreaderfire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comicreaderfire.Adapter.MyViewPagerAdapter;
import com.example.comicreaderfire.Models.Chapter;
import com.example.comicreaderfire.Models.Common;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer;

import dmax.dialog.SpotsDialog;

public class ViewDetailsActivity extends AppCompatActivity {

    TextView txt_chapter_name;
    ViewPager myViewPager;
    String TAG = "-->";
    View next,back;
    MyViewPagerAdapter adapter;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        init();

    }

    private void init() {
        txt_chapter_name = findViewById(R.id.txt_chapter_name);
        myViewPager = findViewById(R.id.view_pager);
        next = findViewById(R.id.chapter_next);
        back = findViewById(R.id.chapter_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Common.chapter_index == 0) // if user in first chapter but press back
                {
                    Toast.makeText(ViewDetailsActivity.this, "You are Reading First Chapter", Toast.LENGTH_SHORT).show();
                }else{
                    Common.chapter_index--;
                    fetchLinks(Common.chapterList.get(Common.chapter_index));
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Common.chapter_index == Common.chapterList.size()-1) // if user in Last chapter but press next
                {
                    Toast.makeText(ViewDetailsActivity.this, "You are Reading Last Chapter", Toast.LENGTH_SHORT).show();
                }else{
                    Common.chapter_index++;
                    fetchLinks(Common.chapterList.get(Common.chapter_index));
                }
            }
        });

        fetchLinks(Common.selected_Chapter);

    }

    private void fetchLinks(Chapter chapter) {

        dialog = new SpotsDialog.Builder().setContext(this).setMessage("Please wait....").build();
        dialog.show();
        txt_chapter_name.setText(Common.formString(chapter.Name));


        if(chapter.Links != null){
            if(chapter.Links.size()>0){
                adapter= new MyViewPagerAdapter(this,chapter.Links);
                myViewPager.setAdapter(adapter);


                //Create book flip page
                BookFlipPageTransformer bookFlipPageTransformer = new BookFlipPageTransformer();
                bookFlipPageTransformer.setScaleAmountPercent(10f);
                myViewPager.setPageTransformer(true,bookFlipPageTransformer);
                dialog.dismiss();

            }else{
                Toast.makeText(this, "No Images Here ...", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

        }else
        {
            Toast.makeText(this, "This Chapter Translating ...", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }

    }
}
