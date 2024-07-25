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
    private KategoriListFragment kategoriListFragmentAdapter;
    private MateriViewModel materiViewModel;
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
            nama.setText(programModel.getNamaProgram());
        }

        // Initialize ViewModel
        materiViewModel = new ViewModelProvider(this).get(MateriViewModel.class);

        // Initialize Adapter with empty list and ViewModel
        kategoriListFragmentAdapter = new KategoriListFragment(new ArrayList<>(), this, materiViewModel);
        recyclerView.setAdapter(kategoriListFragmentAdapter);

        // Initialize Repository
        MateriRepository materiRepository = MateriRepository.get();
        materiRepository.setPro(programModel.getKey());

        materiViewModel.getListModel().observe(this, materiModels -> {
            List<MateriModel> listMateri = new ArrayList<>();
            for (MateriModel mm : materiModels) {
                listMateri.add(mm);
            }
            kategoriListFragmentAdapter.setMateriModelList(listMateri);
            kategoriListFragmentAdapter.notifyDataSetChanged();
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

//
//    private void observeMateriData(List<String> listBookmark) {
//        materiViewModel.getListModel().observe(this, materiModels -> {
//            List<MateriModel> listMateri = new ArrayList<>();
//            System.out.println("MMM: " + materiModels.size());
//
//            for (MateriModel mm : materiModels) {
//                System.out.println("BBB: " + listBookmark.size());
//                for (String b : listBookmark) {
//                    if (mm.getKey().equals(b)) {
//                        mm.setBookmark(true);
//                        break; // Stop loop if bookmark is found
//                    } else {
//                        mm.setBookmark(false);
//                    }
//                }
//
//                listMateri.add(mm);
//                System.out.println("MATERI: " + mm.getJudulKK() + " Bookmark: " + mm.isBookmark());
//            }
//
//            kategoriListFragment.setMateriModelList(listMateri);
//            kategoriListFragment.notifyDataSetChanged();
//        });
//    }
//}
