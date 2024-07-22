package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.ApiUtils;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.FileUnduhService;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KategoriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.MateriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.R;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.MateriViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileMateri extends AppCompatActivity {

    private TextView judulMateri;
    private TextView deskripsiMateri;
    private TextView judulFile, author, tanggal;
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
        author = findViewById(R.id.author);
        tanggal = findViewById(R.id.tanggalTextView);
        unduh = findViewById(R.id.unduh);
        back = findViewById(R.id.back);

        kategoriModel = (KategoriModel) getIntent().getSerializableExtra("kategoriModel");

        materiViewModel = new ViewModelProvider(this).get(MateriViewModel.class);

        materiViewModel.loadMateri();

        materiViewModel.getListModel().observe(this, materiModels -> {
            if (materiModels != null && !materiModels.isEmpty()) {
                MateriModel foundMateri = null;
                for (MateriModel materiModel : materiModels) {
                    if (materiModel.getKategori().equals(kategoriModel.getNamaKategori())) {
                        foundMateri = materiModel;
                        break;
                    }
                }
                if (foundMateri != null) {
                    displayMateriData(foundMateri);
                } else {
                    showNoDataMessage();
                }
            } else {
                showNoDataMessage();
            }
        });

        back.setOnClickListener(v -> finish());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    private void showNoDataMessage() {
        judulMateri.setText("Data tidak tersedia");
        deskripsiMateri.setText("");
        judulFile.setText("");
        author.setText("");
        tanggal.setText("");
        unduh.setVisibility(View.GONE); // Hide download button
    }

    private void displayMateriData(MateriModel materiModel) {
        judulMateri.setText(materiModel.getJudulKK());
        deskripsiMateri.setText(materiModel.getKeterangan());
        judulFile.setText(materiModel.getFilePdf());
        author.setText("Author : " +materiModel.getUploader());
        tanggal.setText("Diunggah pada : " +materiModel.getCreadate());


        unduh.setVisibility(View.VISIBLE); // Make sure download button is visible
        unduh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(materiModel.getFilePdf());
            }
        });
    }


    private void downloadFile(String namaFile) {
        Log.d("FileDownloader", "Download file: " + namaFile);

        FileUnduhService service = ApiUtils.getFileUnduhService();
        Call<ResponseBody> call = service.downloadFile(namaFile);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    boolean writtenToDisk = writeResponseBodyToDisk(response.body(), namaFile);
                    Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_LONG).show();
                    Log.d("FileDownloader", "File download was successful: " + writtenToDisk);
                } else {
                    Log.d("FileDownloader", "Server contact failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("FileDownloader", "Error: " + t.getMessage());
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String fileName) {
        try {
            File futureStudioIconFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

            InputStream inputStream = null;
            FileOutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);
                }

                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
