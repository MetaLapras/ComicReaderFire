package com.example.comicreaderfire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comicreaderfire.Adapter.MyComicAdapter;
import com.example.comicreaderfire.Models.Comic;
import com.example.comicreaderfire.Models.Common;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FilterSearchActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RecyclerView recycler_FilterSearch;
    EditText edt_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_search);
        init();

    }

    private void init() {
        bottomNavigationView = findViewById(R.id.bottm_nav);
        recycler_FilterSearch = findViewById(R.id.recycler_filer_search);
        recycler_FilterSearch.setHasFixedSize(true);
        recycler_FilterSearch.setLayoutManager(new GridLayoutManager(this, 2));



        bottomNavigationView.inflateMenu(R.menu.main_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_filter:
                        showFilterDialoge();
                        break;
                    case R.id.action_Search:
                        showSearchDialoge();
                        break;
                    default:
                        break;
                }


                return true;
            }
        });

    }

    private void showSearchDialoge() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FilterSearchActivity.this);
        alertDialog.setTitle("Search");
        final LayoutInflater inflater = this.getLayoutInflater();
        View searchLayout = inflater.inflate(R.layout.dialog_search,null);

        edt_search = searchLayout.findViewById(R.id.edt_search);
        alertDialog.setView(searchLayout);
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fetchSearchComic(edt_search.getText().toString());
            }
        });
        alertDialog.show();
    }

    private void fetchSearchComic(String query) {
        List<Comic> comic_search = new ArrayList<>();
        for(Comic comic : Common.selected_comicList){
            if(comic.Name.contains(query))
                comic_search.add(comic);

        }
        if(comic_search.size()>0)
            recycler_FilterSearch.setAdapter(new MyComicAdapter(getApplicationContext(),comic_search));
        else
            Toast.makeText(this, "No Result Found", Toast.LENGTH_SHORT).show();

    }

    private void showFilterDialoge() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FilterSearchActivity.this);
        alertDialog.setTitle("Select Category");

        final LayoutInflater inflater = this.getLayoutInflater();
        View filterLayout = inflater.inflate(R.layout.dialog_option,null);

        final AutoCompleteTextView txt_category = filterLayout.findViewById(R.id.txt_cateogry);
        final ChipGroup chipGroup = filterLayout.findViewById(R.id.chipGroup);

        //Create Autocomplete
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.select_dialog_item, Common.categories);
        txt_category.setAdapter(arrayAdapter);
        txt_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Clear
                txt_category.setText("");
                //Create Tags
                Chip chip = (Chip) inflater.inflate(R.layout.chip_item,null,false);
                chip.setText(((TextView)view).getText());
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chipGroup.removeView(v);
                    }
                });
                chipGroup.addView(chip);
            }
        });
        alertDialog.setView(filterLayout);
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setPositiveButton("Filter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<String>filter_key = new ArrayList<>();
                StringBuilder filter_query = new StringBuilder("");
                for(int i =0;i<chipGroup.getChildCount();i++){
                    Chip chip= (Chip) chipGroup.getChildAt(i);
                    filter_key.add(chip.getText().toString());
                }
                //Because in our database, category will be sort from A-Z and split by,
                //We need to sort out filter key
                Collections.sort(filter_key);
                //Covert List to String
                for(String key : filter_key){
                    filter_query.append(key).append(",");
                }
                //remove last ","
                filter_query.setLength(filter_query.length()-1);
                //filter by query
                fetchFilterCategory(filter_query.toString());
            }
        });
        alertDialog.show();
    }

    private void fetchFilterCategory(String query) {
    List<Comic>comic_filter = new ArrayList<>();
    for(Comic comic : Common.selected_comicList){
        if(comic.Category!=null)
        {
            if(comic.Category.contains(query))
                comic_filter.add(comic);
        }
    }
    if(comic_filter.size()>0)
        recycler_FilterSearch.setAdapter(new MyComicAdapter(getApplicationContext(),comic_filter));
    else
        Toast.makeText(this, "No Result Found", Toast.LENGTH_SHORT).show();
    }
}
