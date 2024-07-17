package com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.ApiUtils;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.KategoriService;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.MateriService;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KategoriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.MateriModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MateriRepository {
    private static final String TAG = "MateriRepository";
    private static MateriRepository INSTANCE;
    private MateriService mKateriService;
    private String kat;
    public void setKat(String kat) {
        this.kat = kat;
    }

    private MateriRepository(Context context){
        mKateriService = ApiUtils.getMateriService();
    }
    public static void initialize(Context context){
        if (INSTANCE == null){
            INSTANCE = new MateriRepository(context);
        }
    }
    public static MateriRepository get(){
        return INSTANCE;
    }
    public MutableLiveData<List<MateriModel>> getListMateri() {
        System.out.println("kattt: "+kat);
        MutableLiveData<List<MateriModel>> data = new MutableLiveData<>();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{ \"page\": 1, \"query\": \"Semua\", \"sort\": \"\", \"Judul\": \"Judul\", \"filter\": \"ASC\", \"kategori\": \""+kat+"\" }");
        System.out.println(body.contentType());
        Call<ResponseBody> call = mKateriService.getDataMateri(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string();
                        JSONArray jsonArray = new JSONArray(jsonString);

                        List<MateriModel> MateriList = new ArrayList<>();
                        System.out.println("jsonarray : " + jsonArray.length());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject materiObject = jsonArray.getJSONObject(i);
                            MateriModel materi = new MateriModel();
                            materi.setKey(materiObject.getString("Key"));
                            materi.setKategori(materiObject.getString("Kategori"));
                            materi.setJudulKK(materiObject.getString("Judul"));
                            materi.setFilePdf(materiObject.getString("File_pdf"));
                            materi.setFileVideo(materiObject.getString("File_video"));
                            materi.setKeterangan(materiObject.getString("Keterangan"));
                            materi.setUploader(materiObject.getString("Uploader").substring(0,10) + " ");
                            materi.setCreadate(materiObject.getString("Creadate"));
                            System.out.println("sadljsakd : "+ materi);
                            MateriList.add(materi);
                        }
                        data.setValue(MateriList);
                        Log.d(TAG, "Data size: " + MateriList.size());
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing JSON", e);
                    }
                } else {
                    // Tangani kesalahan respon
                    Log.e(TAG, "Response is not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error API call : ", t.getMessage());
            }
        });
        return data;
    }
}
