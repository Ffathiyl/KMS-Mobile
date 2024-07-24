package com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.ApiUtils;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.KKService;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.ProgramService;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KKModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.ProgramModel;

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

public class ProgramRepository {

    private static final String TAG = "ProgramRepository";
    private static ProgramRepository INSTANCE;
    private ProgramService mProgramService;
    private String kk;

    public void setKk(String kk) {
        this.kk = kk;
    }

    private ProgramRepository(Context context){
        mProgramService = ApiUtils.getProgramService();
    }
    public static void initialize(Context context){
        if (INSTANCE == null){
            INSTANCE = new ProgramRepository(context);
        }
    }
    public static ProgramRepository get(){
        return INSTANCE;
    }
    public MutableLiveData<List<ProgramModel>> getListProgram() {
        MutableLiveData<List<ProgramModel>> data = new MutableLiveData<>();
        System.out.println("PELER: "+kk);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{ \"page\": 1, \"query\": \"\", \"sort\": \"[Nama Program] asc\",\"Status\" : \"Aktif\", \"KKID\" : \""+kk+"\"  }");
        System.out.println(body.contentType());
        Call<ResponseBody> call = mProgramService.getDataProgram(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string();
                        JSONArray jsonArray = new JSONArray(jsonString);

                        List<ProgramModel> ProgramList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject programObject = jsonArray.getJSONObject(i);
                            ProgramModel programModel = new ProgramModel();
                            programModel.setKey(programObject.getString("Key"));
                            programModel.setKKID(programObject.getString("KKiD"));
                            programModel.setNamaProgram(programObject.getString("Nama Program"));
                            if(programObject.getString("Deskripsi").length() > 50){
                                programModel.setDeskripsi(programObject.getString("Deskripsi").substring(0,20)+ " ...");
                            }else{
                                programModel.setDeskripsi(programObject.getString("Deskripsi"));
                            }
//                            kk.setDeskripsi(kkObject.getString("Deskripsi"));
                            ProgramList.add(programModel);
//                            System.out.println("SSSSSSSSSS : "+ProgramList);
                        }
                        data.setValue(ProgramList);
                        Log.d(TAG, "Data size: " + ProgramList.size());
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
