package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KategoriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.MateriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.R;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.MateriViewModel;

import java.util.List;

public class FileMateri extends AppCompatActivity {

    private TextView judulMateri;
    private TextView deskripsiMateri;
    private TextView judulFile;
    private Button unduh;
    private ImageButton back;
    private MateriViewModel materiViewModel;
    private KategoriModel kategoriModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.materi_pdf);

        judulMateri = findViewById(R.id.judulMateri);
        deskripsiMateri = findViewById(R.id.deskripsiMateri);
        judulFile = findViewById(R.id.titleTextView);
        unduh = findViewById(R.id.unduh);
        back = findViewById(R.id.back);

        // Get the KategoriModel from the Intent
        kategoriModel = (KategoriModel) getIntent().getSerializableExtra("kategoriModel");

        // Initialize the ViewModel
        materiViewModel = new ViewModelProvider(this).get(MateriViewModel.class);

        // Load data into the ViewModel
        materiViewModel.loadMateri();

        // Observe the LiveData from the ViewModel
        materiViewModel.getListModel().observe(this, materiModels ->  {

                if (materiModels != null) {
                    for (MateriModel materiModel : materiModels) {
                        System.out.println("sadsakhdsakhd : " + materiModel);
                        if (materiModel.getKategori().equals(kategoriModel.getNamaKategori())) {
                            displayMateriData(materiModel);
                            break; // Break after finding the first matching item
                        }
                    }
                }

        });

        // Back button functionality
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void displayMateriData(MateriModel materiModel) {
        judulMateri.setText(materiModel.getJudulKK());
        deskripsiMateri.setText(materiModel.getKeterangan());
        judulFile.setText(materiModel.getFilePdf());

        unduh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the download action
                // For example, you can start a download service or open the PDF in a viewer
            }
        });
    }
}
