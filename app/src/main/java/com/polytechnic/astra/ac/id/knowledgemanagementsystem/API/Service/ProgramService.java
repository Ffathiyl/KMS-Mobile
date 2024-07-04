package com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ProgramService {
    @POST("Program/GetProgram")
    Call<ResponseBody> getDataProgram(
            @Body RequestBody body
    );
}
