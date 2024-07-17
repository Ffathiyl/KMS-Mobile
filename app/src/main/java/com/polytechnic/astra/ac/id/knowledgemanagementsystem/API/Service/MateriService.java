package com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MateriService {
    @POST("Materis/GetDataMateriByKategori")
    Call<ResponseBody> getDataMateri(
            @Body RequestBody body
    );
}
