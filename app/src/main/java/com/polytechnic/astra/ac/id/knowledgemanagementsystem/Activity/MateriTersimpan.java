package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.DBHelper.BookmarkDatabaseHelper;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment.BookmarkAdapter;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.KategoriRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KategoriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.R;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.KategoriViewModel;

import java.util.ArrayList;
import java.util.List;

public class MateriTersimpan extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookmarkAdapter bookmarkAdapter;
    private List<KategoriModel> kategoriModelList = new ArrayList<>();
    private BookmarkDatabaseHelper dbHelper;
    private KategoriRepository kategoriRepository;
    private ImageButton back;
    private KategoriViewModel kategoriViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.materi_tersimpan);

        back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new BookmarkDatabaseHelper(this);
        kategoriRepository = KategoriRepository.get();

        bookmarkAdapter = new BookmarkAdapter(kategoriModelList, MateriTersimpan.this);
        recyclerView.setAdapter(bookmarkAdapter);

        kategoriViewModel = new ViewModelProvider(this).get(KategoriViewModel.class);
        kategoriViewModel.getListModel().observe(this, kategoriModels -> {
            List<String> bookmarkedCategories = dbHelper.getAllBookmarks();
            List<KategoriModel> filteredKategoriModels = new ArrayList<>();

        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
