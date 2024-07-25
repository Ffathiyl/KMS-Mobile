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
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.DBHelper.KategoriDatabaseHelper;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment.KKListFragment;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment.KategoriListFragment;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment.ProdiListFragment;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.MainActivity;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KategoriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.LoginModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.LoginSession;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.MateriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.R;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.BookmarkViewModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.KKViewModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.KategoriViewModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.MateriViewModel;
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
    private RecentViewModel recentViewModel;
    private KategoriListFragment kategoriListFragment;
    private RecyclerView recyclerViewKK;
    private KKListFragment kkAdapter;
    private KKViewModel kkViewModel;
    private ImageButton bookmarkButton;
    private ImageButton logoutButton;
    private KategoriViewModel kategoriViewModel;
    private LinearLayout fileMateri;
    private LoginModel loginModel;
    private MateriViewModel materiViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        nama = findViewById(R.id.nama);
        program = findViewById(R.id.titleTextView);
        materi = findViewById(R.id.programTextView);
        author = findViewById(R.id.authorTextView);
        tanggal = findViewById(R.id.dateTextView);
        logoutButton = findViewById(R.id.logout);
        fileMateri = findViewById(R.id.materi);
        bookmarkButton = findViewById(R.id.bookmark);
        materiTersimpan = findViewById(R.id.materi_tersimpan);
        bookmarkButton.setTag(false);

        loginModel = (LoginModel) getIntent().getSerializableExtra("LoginModel");
        System.out.println("LOGINMODEL: " + loginModel.getKryId());
        if (loginModel != null) {
            nama.setText("Hai, " + loginModel.getNama());
        } else {
            nama.setText("Hai, " + loginModel.getNama());
        }

        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        prodiAdapter = new ProdiListFragment(new ArrayList<>(), this);
        recyclerView.setAdapter(prodiAdapter);

        prodiViewModel = new ViewModelProvider(this).get(ProdiListViewModel.class);
        recentViewModel = new ViewModelProvider(this).get(RecentViewModel.class);
        materiViewModel = new ViewModelProvider(this).get(MateriViewModel.class);

        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        materiTersimpan.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MateriTersimpan.class);
            startActivity(intent);
        });

        observeViewModels();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Panggil pemuatan ulang data saat halaman kembali aktif
        observeViewModels();
    }

    private void observeViewModels() {
        // Observe LiveData dari ViewModel
        prodiViewModel.getListModel().observe(this, prodiModels -> {
            // Perbarui adapter dengan data baru
            prodiAdapter.setProdiModelList(prodiModels);
            prodiAdapter.notifyDataSetChanged();
        });

        recentViewModel.getListModel().observe(this, materiModel -> {
            if (materiModel != null) {
                displayMateriData(materiModel);
            } else {
                showNoDataMessage();
            }
        });
    }

    private void showNoDataMessage() {
        tanggal.setText("");
        author.setText("");
        program.setText("");
        materi.setText("");
    }

    private void displayMateriData(MateriModel materiModel) {
        MateriRepository materiRepository = MateriRepository.get();

        program.setText(materiModel.getJudulKK());
        materi.setText("Kategori : " + materiModel.getKategori());
        author.setText("Author : " + materiModel.getUploader());
        tanggal.setText("Diunggah pada : " + materiModel.getCreadate());

        System.out.println("HASLSAKBSK: " + materiModel.isBookmark());
        bookmarkButton.setImageResource(materiModel.isBookmark() ? R.drawable.ic_bookmark_fill : R.drawable.ic_bookmark_empty);

        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginModel != null) {
                    if (materiModel.isBookmark()) {
                        // Hapus bookmark
                        materiRepository.deleteBookmark(materiModel.getKey(), loginModel.getKryId(), () -> {
                            materiModel.setBookmark(false);
                            bookmarkButton.setImageResource(R.drawable.ic_bookmark_empty);
                            materiViewModel.refreshData(); // Refresh data setelah perubahan
                        });
                    } else {
                        // Tambah bookmark
                        materiRepository.createBookmark(materiModel.getKey(), loginModel.getKryId(), () -> {
                            materiModel.setBookmark(true);
                            bookmarkButton.setImageResource(R.drawable.ic_bookmark_fill);
                            materiViewModel.refreshData(); // Refresh data setelah perubahan
                        });
                    }
                } else {
                    Log.e("Bookmark", "LoginModel is null");
                }
            }
        });

        fileMateri.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, FileMateri.class);
            intent.putExtra("materiModel", materiModel);
            startActivity(intent);
        });
    }
}