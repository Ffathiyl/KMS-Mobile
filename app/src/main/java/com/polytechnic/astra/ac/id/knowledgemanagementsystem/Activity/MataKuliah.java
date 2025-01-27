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
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.LoginRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.MateriRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.ProgramRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment.KKListFragment;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment.ProgramListFragment;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KKModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.ProdiModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.ProgramModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.R;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.KKViewModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.KategoriViewModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.MateriViewModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.ProgramViewModel;

import java.util.ArrayList;
import java.util.List;

public class MataKuliah extends AppCompatActivity {
    private boolean isBookmark;
    private boolean x;
    private TextView judul;
    private ImageButton mback;
    private RecyclerView recyclerView;
    private ProgramListFragment programListFragment;
    private ProgramViewModel programViewModel;
    private KKModel kkModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mata_kuliah);
        mback = findViewById(R.id.back);
        judul = findViewById(R.id.materi);
        recyclerView = findViewById(R.id.matkul);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        kkModel = (KKModel) getIntent().getSerializableExtra("kkModel");
        if (kkModel != null) {
            judul.setText(kkModel.getNamaKelompokKeahlian());
        }else {
            Log.e("MataKuliah", "kkModel is null");
            finish();
            return;
        }

        // Initialize Adapter with empty list
        programListFragment = new ProgramListFragment(new ArrayList<>(), new ArrayList<>(), this);
        recyclerView.setAdapter(programListFragment);


        // Initialize ViewModel
        programViewModel = new ViewModelProvider(this).get(ProgramViewModel.class);

        // Observe LiveData from ViewModel
        ProgramRepository programRepository = ProgramRepository.get();
        System.out.println(": "+kkModel.getKey());
        programRepository.setKk(kkModel.getKey());

        programViewModel.getListModel().observe(this, programModels -> {
            // Update adapter with new data
            List<ProgramModel> filteredProgramModels = new ArrayList<>();
            for (ProgramModel programModel : programModels) {
//                System.out.println("PROGRAMMODEL_KKID " + programModel.getKKID());
//                String modifiedKey;
//                if(kkModel.getKey().length() == 1){
//                    modifiedKey = "00" + kkModel.getKey();
//                    Log.d("Tag", "Key " + modifiedKey);
//                }else if(kkModel.getKey().length() == 2){
//                    modifiedKey = "0" + kkModel.getKey();
//                    Log.d("Tag", "Key " + modifiedKey);
//                }else {
//                    modifiedKey = kkModel.getKey();
//                }
//                if (modifiedKey.equals(programModel.getKKID())) {
//                    filteredProgramModels.add(programModel);
//                }
//                KategoriViewModel kategoriViewModel = new ViewModelProvider(this).get(KategoriViewModel.class);
//
//                KategoriRepository kategoriRepository = KategoriRepository.get();
//                System.out.println("kategoriRepo : " + programModel.getKey());
//                kategoriRepository.setProdi(programModel.getKey());
//                kategoriViewModel.getListModel().observe(this, kategoriModels -> {
//                    programListFragment.setKategoriModelList(kategoriModels);
//                    programListFragment.notifyDataSetChanged();
//                });
                filteredProgramModels.add(programModel);

                KategoriViewModel kategoriViewModel = new ViewModelProvider(this).get(KategoriViewModel.class);

                KategoriRepository kategoriRepository = KategoriRepository.get();
                System.out.println("mtrRepo : " + programModel.getKey());
                kategoriRepository.setProdi(programModel.getKey());

                kategoriViewModel.getListModel().observe(this, kategoriModels -> {
                    programListFragment.setKategoriModelList(kategoriModels);
                    programListFragment.notifyDataSetChanged();
                });
            }
            Log.d("KKViewModel", "Filtered ProgramModels size: " + filteredProgramModels.size());
            programListFragment.setProgramModelList(filteredProgramModels);
            programListFragment.notifyDataSetChanged();
        });

        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
