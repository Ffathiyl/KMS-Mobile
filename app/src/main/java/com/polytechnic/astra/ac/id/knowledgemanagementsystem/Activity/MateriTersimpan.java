package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.MateriRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.DBHelper.BookmarkDatabaseHelper;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment.BookmarkAdapter;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.KategoriRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KategoriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.R;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.BookmarkViewModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.KategoriViewModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.MateriViewModel;

import java.util.ArrayList;
import java.util.List;

public class MateriTersimpan extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookmarkAdapter bookmarkAdapter;
    private List<KategoriModel> kategoriModelList = new ArrayList<>();
    private BookmarkDatabaseHelper dbHelper;
    private KategoriRepository kategoriRepository;
    private ImageButton back;
    private BookmarkViewModel bookmarkViewModel;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.materi_tersimpan);

        back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new BookmarkDatabaseHelper(this);
        kategoriRepository = KategoriRepository.get();

        bookmarkAdapter = new BookmarkAdapter(new ArrayList<>(), new ArrayList<>(), this);
        recyclerView.setAdapter(bookmarkAdapter);

        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);

        bookmarkViewModel.getListModel().observe(this, bookmarkModels -> {
            // Update adapter with new data
            List<KategoriModel> filteredKategoriModels = new ArrayList<>();
            for (KategoriModel kategoriModel : bookmarkModels) {
                System.out.println("ktmodel : " + kategoriModel);

                    filteredKategoriModels.add(kategoriModel);

                MateriViewModel materiViewModel = new ViewModelProvider(this).get(MateriViewModel.class);

                MateriRepository materiRepository = MateriRepository.get();
                System.out.println("mtrRepo : " + kategoriModel.getKey());
                materiRepository.setKat(kategoriModel.getKey());

                materiViewModel.getListModel().observe(this, materiModels -> {
                    bookmarkAdapter.setMateriModelList(materiModels);
                    bookmarkAdapter.notifyDataSetChanged();
                });

            }
            Log.d("KKViewModel", "Filtered KategorModels size: " + filteredKategoriModels.size());
            bookmarkAdapter.setKategoriModelList(filteredKategoriModels);
            bookmarkAdapter.notifyDataSetChanged();

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
