package com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface KategoriService {
    @POST("KategoriProgram/GetKategoriByProgram")
    Call<ResponseBody> getDataKategori(
            @Body RequestBody body
    );

    @POST("KategoriProgram/GetKategoriById")
    Call<ResponseBody> getDataKategoriById(
            @Body RequestBody body
    );

    @POST("KategoriProgram/CreateBookmark")
    Call<ResponseBody> CreateBookmark(
            @Body RequestBody body
    );

    @POST("KategoriProgram/GetBookmark")
    Call<ResponseBody> GetBookmark(
            @Body RequestBody body
    );

    @POST("KategoriProgram/DeleteBookmark")
    Call<ResponseBody> DeleteBookmark(
            @Body RequestBody body
    );

    @POST("KategoriProgram/CreateRecent")
    Call<ResponseBody> CreateRecent(
            @Body RequestBody body
    );

    @POST("KategoriProgram/GetRecent")
    Call<ResponseBody> GetRecent(
            @Body RequestBody body
    );

    @POST("KategoriProgram/DeleteRecent")
    Call<ResponseBody> DeleteRecent(
            @Body RequestBody body
    );
}
