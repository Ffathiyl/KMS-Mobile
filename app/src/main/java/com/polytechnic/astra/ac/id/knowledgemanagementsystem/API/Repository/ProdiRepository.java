package com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.ApiUtils;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.ProdiService;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.ProdiModel;

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

public class ProdiRepository {
    private static final String TAG = "ProdiRepository";
    private static ProdiRepository INSTANCE;
    private ProdiService mProdiService;
    private ProdiRepository(Context context){
        mProdiService = ApiUtils.getProdiService();
    }
    public static void initialize(Context context){
        if (INSTANCE == null){
            INSTANCE = new ProdiRepository(context);
        }
    }
    public static ProdiRepository get(){
        return INSTANCE;
    }

    public MutableLiveData<List<ProdiModel>> getListProdi() {
        MutableLiveData<List<ProdiModel>> data = new MutableLiveData<>();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{}");
        System.out.println(body.contentType());
        Call<ResponseBody> call = mProdiService.getDataProdi(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string();
                        JSONArray jsonArray = new JSONArray(jsonString);

                        List<ProdiModel> prodiList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject prodiObject = jsonArray.getJSONObject(i);
                            ProdiModel prodi = new ProdiModel();
                            prodi.setId(prodiObject.getString("Value"));
//                            prodi.setNama(prodiObject.getString("Text"));
                            if(prodiObject.getString("Text").length() > 22){
                                prodi.setNama(prodiObject.getString("Text").substring(0,22)+ " ...");
                            }else{
                                prodi.setNama(prodiObject.getString("Text"));
                            }
                            prodiList.add(prodi);
                        }
                        data.setValue(prodiList);
                        System.out.println("data prodi: "+data);
                        Log.d(TAG, "Data size: " + prodiList.size());
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

    public MutableLiveData<List<ProdiModel>> getListKK() {
        MutableLiveData<List<ProdiModel>> data = new MutableLiveData<>();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{ \"page\": 1, \"query\": \"\", \"sort\": \"[Nama Kelompok Keahlian] asc\" }");
        System.out.println(body.contentType());
        Call<ResponseBody> call = mProdiService.getDataKK(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string();
                        JSONArray jsonArray = new JSONArray(jsonString);

                        List<ProdiModel> prodiList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject prodiObject = jsonArray.getJSONObject(i);
                            ProdiModel prodi = new ProdiModel();
                            prodi.setId(prodiObject.getString("Key"));
                            prodi.setNama(prodiObject.getString("Nama Kelompok Keahlian"));
                            prodiList.add(prodi);
                        }
                        data.setValue(prodiList);
                        Log.d(TAG, "Data size: " + prodiList.size());
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
