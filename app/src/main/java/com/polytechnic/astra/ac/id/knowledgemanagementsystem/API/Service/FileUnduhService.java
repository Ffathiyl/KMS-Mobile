package com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FileUnduhService {
    @GET("/Utilities/Upload/DownloadFile")
    Call<ResponseBody> downloadFile(@Query("namaFile") String namaFile);

    @GET("/Utilities/Upload/PreviewFile")
    Call<ResponseBody> previewFile(@Query("namaFile") String namaFile);
}
