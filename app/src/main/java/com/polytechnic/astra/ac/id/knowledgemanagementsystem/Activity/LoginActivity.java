package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.LoginRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.MateriRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.DBHelper.BookmarkDatabaseHelper;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.DBHelper.KategoriDatabaseHelper;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment.KKListFragment;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment.ProdiListFragment;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.MainActivity;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KategoriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.LoginModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.MateriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.R;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.BookmarkViewModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.KKViewModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.KategoriViewModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.ProdiListViewModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.RecentViewModel;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView nama, program, materi, author, tanggal;
    private Button loginButton, materiTersimpan;
    private RecyclerView recyclerView;
    private ProdiListFragment prodiAdapter;
    private ProdiListViewModel prodiViewModel;
    private RecyclerView recyclerViewKK;
    private KKListFragment kkAdapter;
    private KKViewModel kkViewModel;
    private ImageButton bookmarkButton;
    private ImageButton logoutButton;
    private RecentViewModel recentViewModel;
    private LinearLayout filemateri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        nama = findViewById(R.id.nama);
        program = findViewById(R.id.titleTextView);
        materi = findViewById(R.id.programTextView);
        author = findViewById(R.id.authorTextView);
        tanggal = findViewById(R.id.dateTextView);
        filemateri = findViewById(R.id.materi);
        logoutButton = findViewById(R.id.logout);
        bookmarkButton = findViewById(R.id.bookmark);
        materiTersimpan = findViewById(R.id.materi_tersimpan);

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

        KategoriDatabaseHelper dbHelperKat = new KategoriDatabaseHelper(this);

        recentViewModel = new ViewModelProvider(this).get(RecentViewModel.class);

        recentViewModel.getListModel().observe(this, kategoriModels -> {
            if (kategoriModels != null && !kategoriModels.isEmpty()) {
                KategoriModel foundMateri = null;
                for (KategoriModel kategoriModel : kategoriModels) {
                    String LastData = dbHelperKat.getLastKategori();
                    System.out.println("fondbhelo: " +LastData);
                    if (LastData.equals(kategoriModel.getKey())) {
                        System.out.println("dbhelperkatat: " +dbHelperKat.isKategoriExists(kategoriModel.getKey()));
                        foundMateri = kategoriModel;
                        System.out.println("fndmtr : " + foundMateri);
                        break;
                    }
                }
                if (foundMateri != null) {
                    System.out.println("foundmater : " + foundMateri);
                    displayMateriData(foundMateri);
                } else {
                    showNoDataMessage();
                }
            } else {
                showNoDataMessage();
            }
        });


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        materiTersimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MateriTersimpan.class);
                startActivity(intent);
            }
        });

    }
    private void showNoDataMessage() {
        tanggal.setText("");
        author.setText("");
        program.setText("");
        materi.setText("");
    }

    private void displayMateriData(KategoriModel kategoriModel) {
        BookmarkDatabaseHelper dbHelper = new BookmarkDatabaseHelper(this);

        program.setText("Program : " + kategoriModel.getNamaKategori());
//        materi.setText(materiModel.getKeterangan());
//        author.setText("Author : " +materiModel.getUploader());
//        tanggal.setText("Diunggah pada : " +materiModel.getCreadate());
        boolean isBookmarked = dbHelper.isBookmarked(kategoriModel.getKey());
        bookmarkButton.setImageResource(isBookmarked ? R.drawable.ic_bookmark_fill : R.drawable.ic_bookmark_empty);

        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbHelper.isBookmarked(kategoriModel.getKey())) {
                    dbHelper.removeBookmark(kategoriModel.getKey());
                    bookmarkButton.setImageResource(R.drawable.ic_bookmark_empty);
                } else {
                    dbHelper.addBookmark(kategoriModel.getKey());
                    bookmarkButton.setImageResource(R.drawable.ic_bookmark_fill);
                }
            }
        });

        filemateri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MateriRepository materiRepository = MateriRepository.get();
                materiRepository.setKat(kategoriModel.getKey());
                Intent intent = new Intent(LoginActivity.this, FileMateri.class);
                intent.putExtra("kategoriModel", kategoriModel);
                startActivity(intent);
            }
        });

    }

}
