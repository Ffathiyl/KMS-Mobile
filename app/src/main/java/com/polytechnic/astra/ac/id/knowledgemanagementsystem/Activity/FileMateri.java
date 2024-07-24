package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Activity;


import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
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
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.ApiUtils;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.FileUnduhService;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.BuildConfig;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KategoriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.MateriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.R;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.MateriViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
    private ExoPlayer player;
    private PlayerView playerView;
    private MateriModel materiModel;

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

//        kategoriModel = (KategoriModel) getIntent().getSerializableExtra("kategoriModel");

        materiModel = (MateriModel) getIntent().getSerializableExtra("materiModel");

        if (materiModel != null) {
            displayMateriData(materiModel);
        }

//        materiViewModel = new ViewModelProvider(this).get(MateriViewModel.class);
//
//        materiViewModel.loadMateri();
//
//        materiViewModel.getListModel().observe(this, materiModels -> {
//            if (materiModels != null && !materiModels.isEmpty()) {
//                MateriModel foundMateri = null;
//                for (MateriModel materiModel : materiModels) {
//                    if (materiModel.getKategori().equals(kategoriModel.getNamaKategori())) {
//                        foundMateri = materiModel;
//                        break;
//                    }
//                }
//                if (foundMateri != null) {
//                    displayMateriData(foundMateri);
//                } else {
//                    showNoDataMessage();
//                }
//            } else {
//                showNoDataMessage();
//            }
//        });

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

        playerView = findViewById(R.id.player_view);
        Button btnPlayVideo = findViewById(R.id.btn_play_video);

        btnPlayVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadVideo(materiModel.getFileVideo());
            }
        });
    }

    private void initializePlayer(String videoUrl) {
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        // Build the media item
        MediaItem mediaItem = MediaItem.fromUri(videoUrl);

        // Set the media item to be played
        player.setMediaItem(mediaItem);

        // Prepare the player
        player.prepare();

        // Start the playback
        player.play();
    }

    private void downloadVideo(String namaFile) {
        FileUnduhService service = ApiUtils.getFileUnduhService();
        Call<ResponseBody> call = service.downloadFile(namaFile);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                        boolean isDownloaded = saveFileToDisk(response.body(), namaFile);
                    if (isDownloaded) {
                        File videoFile = new File(getExternalFilesDir(Environment.DIRECTORY_MOVIES), namaFile);
                        initializePlayer(videoFile.getAbsolutePath());
                    }
                } else {
                    Log.d("FileDownloader", "Server contact failed");
                    Toast.makeText(getApplicationContext(), "Download failed. Server contact failed.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("FileDownloader", "Error: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Download failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean saveFileToDisk(ResponseBody body, String fileName) {
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_MOVIES), fileName);
            System.out.println("jskfsk");
            InputStream inputStream = null;
            FileOutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;

                    Log.d("VideoDownloader", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();
                return true;
            } catch (IOException e) {
                Log.e("VideoDownloader", "IOException: " + e.getMessage());
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
            Log.e("VideoDownloader", "IOException: " + e.getMessage());
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void downloadFile(String namaFile) {
        Log.d("FileDownloader", "Filename from Model : " + namaFile);

        FileUnduhService service = ApiUtils.getFileUnduhService();
        Call<ResponseBody> call = service.downloadFile(namaFile);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    boolean writtenToDisk = writeResponseBodyToDisk(response.body(), namaFile);
                    Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_LONG).show();
                    Log.d("FileDownloader", "File download was successful: " + writtenToDisk);

                    if (writtenToDisk) {
                        Log.d("FileDownloader", "File download was successful");
                        File file = new File(getExternalFilesDir(null), "Download/" + namaFile);
                        showNotification(file);
                    }
                } else {
                    Log.d("FileDownloader", "Server contact failed");
                    Toast.makeText(getApplicationContext(), "Download failed. Server contact failed.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("FileDownloader", "Error: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Download failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String namaFile) {
        try {
            // Dapatkan direktori aplikasi yang diinginkan
            File directory = new File(getExternalFilesDir(null), "Download");

            // Buat direktori jika belum ada
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                Log.d("FileDownloader", "Download directory created: " + created);
            }

            // Buat file baru di direktori aplikasi
            File file = new File(directory, namaFile);

            // Tulis data yang diunduh ke file
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;

                    Log.d("FileDownloader", "File download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();
                return true;
            } catch (IOException e) {
                Log.e("FileDownloader", "Error writing to disk: " + e.getMessage());
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
            Log.e("FileDownloader", "IOException: " + e.getMessage());
            return false;
        }
    }

    private void showNotification(File file) {
        String channelId = "download_channel";
        String channelName = "Download Channel";

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.stat_sys_download_done)
                .setContentTitle("Unduhan Selesai")
                .setContentText("PDF berhasil diunduh")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        Uri fileUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", file);
        Log.d("FileDownloader", "File URI: " + fileUri.toString());

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(fileUri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        builder.setContentIntent(pendingIntent);

        notificationManager.notify(1, builder.build());
    }
}
