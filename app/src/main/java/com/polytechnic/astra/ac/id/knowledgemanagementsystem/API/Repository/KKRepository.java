package com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.ApiUtils;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.KKService;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.ProdiService;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KKModel;
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

public class KKRepository {
    private static final String TAG = "KKRepository";
    private static KKRepository INSTANCE;
    private KKService mKKService;
    private KKRepository(Context context){
        mKKService = ApiUtils.getKKService();
    }
    public static void initialize(Context context){
        if (INSTANCE == null){
            INSTANCE = new KKRepository(context);
        }
    }
    public static KKRepository get(){
        return INSTANCE;
    }
    public MutableLiveData<List<KKModel>> getListKK() {
        MutableLiveData<List<KKModel>> data = new MutableLiveData<>();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{ \"page\": 1, \"query\": \"\", \"sort\": \"[Created Date] desc\", \"status\": \"Aktif\" }");
        System.out.println(body.contentType());
        Call<ResponseBody> call = mKKService.getDataKK(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string();
                        JSONArray jsonArray = new JSONArray(jsonString);

                        List<KKModel> KKList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject kkObject = jsonArray.getJSONObject(i);
                            KKModel kk = new KKModel();
                            kk.setKey(kkObject.getString("Key"));

                            kk.setNamaKelompokKeahlian(kkObject.getString("Nama Kelompok Keahlian"));
                            if(kkObject.getString("Deskripsi").length() > 30){
                                kk.setDeskripsi(kkObject.getString("Deskripsi").substring(0,20)+ " ...");
                            }else{
                                kk.setDeskripsi(kkObject.getString("Deskripsi"));
                            }
                            kk.setProdi(kkObject.getString("Prodi"));
                            KKList.add(kk);
                        }
                        data.setValue(KKList);
                        Log.d(TAG, "Data size: " + KKList.size());
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
