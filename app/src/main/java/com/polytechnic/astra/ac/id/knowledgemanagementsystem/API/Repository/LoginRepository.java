package com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.ApiUtils;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.LoginService;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.ProdiService;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.LoginModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.LoginSession;
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

public class LoginRepository {
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    private static final String TAG = "LoginRepository";
    private static LoginRepository INSTANCE;
    private LoginService mLoginService;
    private LoginRepository(Context context){
        mLoginService = ApiUtils.getLoginService();
    }
    public static void initialize(Context context){
        if (INSTANCE == null){
            INSTANCE = new LoginRepository(context);
        }
    }
    public static LoginRepository get(){
        return INSTANCE;
    }

    public MutableLiveData<List<LoginModel>> getLogin() {
        MutableLiveData<List<LoginModel>> data = new MutableLiveData<>();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{ \"username\": \""+username+"\"}");
        System.out.println(body.contentType());
        Call<ResponseBody> call = mLoginService.getLogin(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string();
                        JSONArray jsonArray = new JSONArray(jsonString);

                        List<LoginModel> loginList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject prodiObject = jsonArray.getJSONObject(i);

                            if (prodiObject.has("Status") && "LOGIN FAILED".equals(prodiObject.getString("Status"))) {
                                data.setValue(null);
                                Log.e(TAG, "Login failed");
                                return;
                            }

                            LoginModel prodi = new LoginModel();
                            prodi.setRoleID(prodiObject.getString("RoleID"));
                            prodi.setRole(prodiObject.getString("Role"));
                            prodi.setNama(prodiObject.getString("Nama"));
                            prodi.setKryId(prodiObject.getString("KryId"));
                            loginList.add(prodi);
                        }
                        data.setValue(loginList);
                        if (!loginList.isEmpty()) {
                            LoginSession.getInstance().setLoginModel(loginList.get(0));
                        }
                        Log.d(TAG, "Data size: " + loginList.size());

                        // Log detail data yang diterima
                        for (LoginModel loginModel : loginList) {
                            Log.d(TAG, "Received data: " + loginModel.getNama());
                        }

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
        System.out.println("data LOGIN : "+data);
        return data;
    }

    public MutableLiveData<List<LoginModel>> getUserLogin(String username) {
        MutableLiveData<List<LoginModel>> data = new MutableLiveData<>();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{ \"username\": \"" + username + "\"}");
        Call<ResponseBody> call = mLoginService.getUserLogin(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string();
                        JSONArray jsonArray = new JSONArray(jsonString);

                        List<LoginModel> loginList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject prodiObject = jsonArray.getJSONObject(i);
                            LoginModel prodi = new LoginModel();

                            if (prodiObject.has("RoleID")) {
                                prodi.setRoleID(prodiObject.getString("RoleID"));
                            }
                            if (prodiObject.has("Role")) {
                                prodi.setRole(prodiObject.getString("Role"));
                            }
                            if (prodiObject.has("Nama")) {
                                prodi.setNama(prodiObject.getString("Nama"));
                            }
                            if (prodiObject.has("kry_id")) {
                                prodi.setKryId(prodiObject.getString("kry_id"));
                            }
                            loginList.add(prodi);
                        }
                        data.setValue(loginList);

                        if (!loginList.isEmpty()) {
                            LoginSession.getInstance().setLoginModel(loginList.get(0));
                        }

                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing JSON", e);
                    }
                } else {
                    Log.e(TAG, "Response is not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error API call: ", t.getMessage());
            }
        });
        return data;
    }
}
