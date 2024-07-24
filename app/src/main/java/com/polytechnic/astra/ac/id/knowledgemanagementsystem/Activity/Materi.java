package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.KategoriRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.MateriRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.ProgramRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment.KKListFragment;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment.KategoriListFragment;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KKModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KategoriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.MateriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.ProdiModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.ProgramModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.R;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.BookmarkViewModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.KKViewModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.KategoriViewModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.MateriViewModel;

import java.util.ArrayList;
import java.util.List;

public class Materi extends AppCompatActivity {

    private TextView nama;
    private ImageButton back;
    private RecyclerView recyclerView;
    private KategoriListFragment kategoriListFragment;
    private MateriViewModel materiViewModel;
    private BookmarkViewModel bookmarkViewModel;
    private ProgramModel programModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.materi);
        back = findViewById(R.id.back);
        nama = findViewById(R.id.materi);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        programModel = (ProgramModel) getIntent().getSerializableExtra("programModel");
        if (programModel != null) {
            nama.setText(programModel.getNamaProgram()); // 011
        }

        // Initialize Adapter with empty list
        kategoriListFragment = new KategoriListFragment(new ArrayList<>(), this);
        recyclerView.setAdapter(kategoriListFragment);

        // Initialize ViewModel
        materiViewModel = new ViewModelProvider(this).get(MateriViewModel.class);

//        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);

        // Initialize Repository
        MateriRepository materiRepository = MateriRepository.get();
        materiRepository.setPro(programModel.getKey());

//        List<String> listBookmark = new ArrayList<>();
//
//        bookmarkViewModel.getListModel().observe(this, bookmarks -> {
//            listBookmark.clear(); // Clear previous data
//            listBookmark.addAll(bookmarks);
//            System.out.println("Bookmark List Updated: " + listBookmark.size());
//
//            // Now observe materi data after bookmarks are fetched
//            observeMateriData(listBookmark);
//        });

        materiViewModel.getListModel().observe(this, materiModels -> {
            List<MateriModel> listMateri = new ArrayList<>();
            System.out.println("MMM:"+materiModels.size());
            for (MateriModel mm : materiModels) {
                listMateri.add(mm);
                System.out.println("MATERISHS:" + mm.isBookmark());
            }

            kategoriListFragment.setMateriModelList(listMateri);
            kategoriListFragment.notifyDataSetChanged();
        });

//        KategoriRepository kategoriRepository = KategoriRepository.get();
//        kategoriRepository.setProdi(programModel.getKey());
//
//        // Observe LiveData from ViewModel
//        kategoriViewModel.getListModel().observe(this, kategoriModels -> {
//            List<KategoriModel> filteredKategoriModels = new ArrayList<>();
//            List<MateriModel> listMateri = new ArrayList<>();
//
//            // Ensure we clear the previous data
//            kategoriListFragment.setKategoriModelList(new ArrayList<>());
//            kategoriListFragment.setMateriModelList(new ArrayList<>());
//            kategoriListFragment.notifyDataSetChanged();
//
//            // Loop through each kategori and fetch the corresponding materi
//            for (KategoriModel kategoriModel : kategoriModels) {
//                filteredKategoriModels.add(kategoriModel);
//
//                // Fetch materi for each kategori
//                MateriRepository materiRepository = MateriRepository.get();
//                materiRepository.setKat(kategoriModel.getKey());
//
//                materiViewModel.getListModel().observe(this, materiModels -> {
//                    for (MateriModel matMod : materiModels) {
//                        if (matMod.getKategori().equals(kategoriModel.getNamaKategori())) {
//                            listMateri.add(matMod);
//                            System.out.println("Materi: " + matMod.getJudulKK());
//                        }
//                    }
//                    kategoriListFragment.setMateriModelList(listMateri);
//                    kategoriListFragment.notifyDataSetChanged();
//                });
//            }
//
//            kategoriListFragment.setKategoriModelList(filteredKategoriModels);
//            kategoriListFragment.notifyDataSetChanged();
//        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void observeMateriData(List<String> listBookmark) {
        materiViewModel.getListModel().observe(this, materiModels -> {
            List<MateriModel> listMateri = new ArrayList<>();
            System.out.println("MMM: " + materiModels.size());

            for (MateriModel mm : materiModels) {
                System.out.println("BBB: " + listBookmark.size());
                for (String b : listBookmark) {
                    if (mm.getKey().equals(b)) {
                        mm.setBookmark(true);
                        break; // Stop loop if bookmark is found
                    } else {
                        mm.setBookmark(false);
                    }
                }

                listMateri.add(mm);
                System.out.println("MATERI: " + mm.getJudulKK() + " Bookmark: " + mm.isBookmark());
            }

            kategoriListFragment.setMateriModelList(listMateri);
            kategoriListFragment.notifyDataSetChanged();
        });
    }
}
