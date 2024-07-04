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

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment.KKListFragment;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment.KategoriListFragment;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KKModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KategoriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.ProdiModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.ProgramModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.R;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.KKViewModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.KategoriViewModel;

import java.util.ArrayList;
import java.util.List;

public class Materi extends AppCompatActivity {

    private TextView nama;
    private ImageButton back;
    private RecyclerView recyclerView;
    private KategoriListFragment kategoriAdapter;
    private KategoriViewModel kategoriViewModel;
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

        // Initialize Adapter with empty list
        kategoriAdapter = new KategoriListFragment(new ArrayList<>(), this);
        recyclerView.setAdapter(kategoriAdapter);

        // Initialize ViewModel
        kategoriViewModel = new ViewModelProvider(this).get(KategoriViewModel.class);

        // Observe LiveData from ViewModel
        kategoriViewModel.getListModel().observe(this, kategoriModels -> {
            // Update adapter with new data
            List<KategoriModel> filteredKategoriModels = new ArrayList<>();
            for (KategoriModel kategoriModel : kategoriModels) {

                String modifiedKey;
                if(kategoriModel.getKey().length() == 1){
                    modifiedKey = "00" + kategoriModel.getKey();
                    Log.d("Tag", "Key " + modifiedKey);
                }else if(kategoriModel.getKey().length() == 2){
                    modifiedKey = "0" + kategoriModel.getKey();
                    Log.d("Tag", "Key " + modifiedKey);
                }else {
                    modifiedKey = kategoriModel.getKey();
                }

                if (modifiedKey.equals(programModel.getKey())) {
                    filteredKategoriModels.add(kategoriModel);
                }
            }
            Log.d("KKViewModel", "Filtered KategorModels size: " + filteredKategoriModels.size());
            kategoriAdapter.setKategoriModelList(filteredKategoriModels);
            kategoriAdapter.notifyDataSetChanged();

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
