package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment.KKListFragment;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment.ProdiListFragment;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.MainActivity;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KKModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.LoginModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.LoginSession;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.ProdiModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.R;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.KKViewModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.ProdiListViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProgramKeilmuan extends AppCompatActivity {

    private TextView nama;
    private ImageButton back;
    private RecyclerView recyclerView;
    private KKListFragment kkAdapter;
    private KKViewModel kkViewModel;
    private ProdiModel prodiModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_keilmuan);
        back = findViewById(R.id.back);
        nama = findViewById(R.id.judulProdi);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        prodiModel = (ProdiModel) getIntent().getSerializableExtra("prodiModel");
        if (prodiModel != null) {
            nama.setText(prodiModel.getNama());
        }

        // Initialize Adapter with empty list
        kkAdapter = new KKListFragment(new ArrayList<>(), this);
        recyclerView.setAdapter(kkAdapter);

        // Initialize ViewModel
        kkViewModel = new ViewModelProvider(this).get(KKViewModel.class);

        // Observe LiveData from ViewModel
        kkViewModel.getListModel().observe(this, kkModels -> {
            // Update adapter with new data
            List<KKModel> filteredKKModels = new ArrayList<>();
            for (KKModel kkModel : kkModels) {

                if (kkModel.getProdi().equals(prodiModel.getNama())) {
                    filteredKKModels.add(kkModel);
                }
            }
            Log.d("KKViewModel", "Filtered KKModels size: " + filteredKKModels.size());
            kkAdapter.setKKModelList(filteredKKModels);
            kkAdapter.notifyDataSetChanged();

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginModel loginModel = LoginSession.getInstance().getLoginModel();

                Intent intent = new Intent(ProgramKeilmuan.this, LoginActivity.class);
                intent.putExtra("LoginModel", loginModel);
                startActivity(intent);
            }
        });
    }
}
