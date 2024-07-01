package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.LoginRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment.KKListFragment;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment.ProdiListFragment;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.MainActivity;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.LoginModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.R;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.KKViewModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.ProdiListViewModel;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;

    private TextView nama;

    private Button loginButton;

    private RecyclerView recyclerView;
    private ProdiListFragment prodiAdapter;
    private ProdiListViewModel prodiViewModel;
    private RecyclerView recyclerViewKK;
    private KKListFragment kkAdapter;
    private KKViewModel kkViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        nama = findViewById(R.id.nama);
        LoginModel loginModel = (LoginModel) getIntent().getSerializableExtra("LoginModel");
        if (loginModel != null) {
            nama.setText("Hai, " + loginModel.getNama());
        }else{
            nama.setText("Hai, " + loginModel.getNama());
        }
        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Adapter with empty list
        prodiAdapter = new ProdiListFragment(new ArrayList<>(), this);
        recyclerView.setAdapter(prodiAdapter);

        // Initialize ViewModel
        prodiViewModel = new ViewModelProvider(this).get(ProdiListViewModel.class);

        // Observe LiveData from ViewModel
        prodiViewModel.getListModel().observe(this, prodiModels -> {
            // Update adapter with new data
            prodiAdapter.setProdiModelList(prodiModels);
            prodiAdapter.notifyDataSetChanged();

        });
//        recyclerViewKK = findViewById(R.id.recyclerView2);
//        recyclerViewKK.setLayoutManager(new LinearLayoutManager(this));
//        kkAdapter = new KKListFragment(new ArrayList<>(), this);
//        recyclerViewKK.setAdapter(kkAdapter);
//
//        // Initialize ViewModel for KKListFragment
//        kkViewModel = new ViewModelProvider(this).get(KKViewModel.class);
//        kkViewModel.getListModel().observe(this, kkModels -> {
//            kkAdapter.setKKModelList(kkModels);
//            kkAdapter.notifyDataSetChanged();
//        });
    }
}
