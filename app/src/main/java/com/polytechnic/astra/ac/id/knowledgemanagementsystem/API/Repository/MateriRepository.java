package com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.ApiUtils;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.KategoriService;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.MateriService;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KategoriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.LoginModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.LoginSession;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.MateriModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private KategoriService mKategoriService;
    private String kat;
    private String pro;
    private List<String> listArrayBookmark = new ArrayList<>();
    private String IdRecent;

    public String getIdRecent() {
        return IdRecent;
    }

    public void setIdRecent(String idRecent) {
        IdRecent = idRecent;
    }

    public void setKat(String kat) {
        this.kat = kat;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    private MateriRepository(Context context) {
        mKateriService = ApiUtils.getMateriService();
        mKategoriService = ApiUtils.getKategoriService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MateriRepository(context);
        }
    }

    public static MateriRepository get() {
        return INSTANCE;
    }

    public MutableLiveData<List<MateriModel>> getListMateri() {
        MutableLiveData<List<MateriModel>> data = new MutableLiveData<>();

        if (listArrayBookmark.isEmpty()) getBookmark();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{ \"pro\": \"" + pro + "\" }");
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
                        System.out.println("jsonarray length : " + jsonArray.length());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject materiObject = jsonArray.getJSONObject(i);
                            System.out.println("Materi_ID: " + materiObject.getString("mat_id"));
                            MateriModel materi = new MateriModel();
                            materi.setKey(materiObject.getString("mat_id"));
                            materi.setKat_ID(materiObject.getString("kat_id"));
                            materi.setKategori(materiObject.getString("kat_nama"));
                            materi.setJudulKK(materiObject.getString("mat_judul"));
                            materi.setFilePdf(materiObject.getString("mat_file_pdf"));
                            materi.setFileVideo(materiObject.getString("mat_file_video"));
                            materi.setKeterangan(materiObject.getString("mat_keterangan"));
                            materi.setUploader(materiObject.getString("mat_created_by"));

                            String originalDateStr = materiObject.getString("mat_created_date");
                            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");

                            Date date = new Date();
                            try {
                                date = originalFormat.parse(originalDateStr);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            String formattedDateStr = targetFormat.format(date);
                            materi.setCreadate(formattedDateStr + " ");

                            materi.setBookmark(listArrayBookmark.contains(materi.getKey()));

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

    public MutableLiveData<List<MateriModel>> getListMateriTersimpan() {
        MutableLiveData<List<MateriModel>> data = new MutableLiveData<>();
        List<MateriModel> MateriList = new ArrayList<>();

//        listArrayBookmark.clear();
//        getBookmark();

        if (listArrayBookmark.isEmpty()) {
            getBookmark();
        }
        if (listArrayBookmark.isEmpty()) {
            data.setValue(MateriList);
            return data;
        }

        for (String mat_id : listArrayBookmark) {
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{ \"mat_id\": \"" + mat_id + "\" }");
            Call<ResponseBody> call = mKateriService.getDataMateriById(body);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        try {
                            String jsonString = response.body().string();
                            JSONArray jsonArray = new JSONArray(jsonString);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject materiObject = jsonArray.getJSONObject(i);
                                MateriModel materi = new MateriModel();
                                materi.setKey(materiObject.getString("mat_id"));
                                materi.setKat_ID(materiObject.getString("kat_id"));
                                materi.setKategori(materiObject.getString("kat_nama"));
                                materi.setJudulKK(materiObject.getString("mat_judul"));
                                materi.setFilePdf(materiObject.getString("mat_file_pdf"));
                                materi.setFileVideo(materiObject.getString("mat_file_video"));
                                materi.setKeterangan(materiObject.getString("mat_keterangan"));
                                materi.setUploader(materiObject.getString("mat_created_by"));

                                String originalDateStr = materiObject.getString("mat_created_date");
                                SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");

                                Date date = new Date();
                                try {
                                    date = originalFormat.parse(originalDateStr);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                String formattedDateStr = targetFormat.format(date);
                                materi.setCreadate(formattedDateStr + " ");

                                // Set bookmark status
                                materi.setBookmark(listArrayBookmark.contains(materi.getKey()));

                                MateriList.add(materi);
                            }
                            // Update LiveData with the new data
                            data.postValue(MateriList);
                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing JSON", e);
                        }
                    } else {
                        // Handle unsuccessful response
                        Log.e(TAG, "Response is not successful or body is null");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e(TAG, "Error API call: ", t);
                }
            });
        }

        return data;
    }

    public MutableLiveData<MateriModel> getListRecent() {
        MutableLiveData<MateriModel> data = new MutableLiveData<>();
        MateriModel materiList = new MateriModel();

        LoginModel loginModel = LoginSession.getInstance().getLoginModel();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{ \"kry_id\": \""+loginModel.getKryId()+"\" }");
        Call<ResponseBody> call = mKategoriService.GetRecent(body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string();
                        JSONArray jsonArray = new JSONArray(jsonString);

                        if (jsonArray.length() > 0) {
                            JSONObject materiObject = jsonArray.getJSONObject(0);
                            MateriModel materi = new MateriModel();
                            materi.setKey(materiObject.getString("mat_id"));
                            materi.setKat_ID(materiObject.getString("kat_id"));
                            materi.setKategori(materiObject.getString("kat_nama"));
                            materi.setJudulKK(materiObject.getString("mat_judul"));
                            materi.setFilePdf(materiObject.getString("mat_file_pdf"));
                            materi.setFileVideo(materiObject.getString("mat_file_video"));
                            materi.setKeterangan(materiObject.getString("mat_keterangan"));
                            materi.setUploader(materiObject.getString("mat_created_by"));
                            materi.setBookmark(materiObject.getString("isBookmark").equals("true") ? true : false);

                            String originalDateStr = materiObject.getString("mat_created_date");
                            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");

                            Date date = new Date();
                            try {
                                date = originalFormat.parse(originalDateStr);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            String formattedDateStr = targetFormat.format(date);
                            materi.setCreadate(formattedDateStr + " ");

                            // Update LiveData with the new data
                            data.postValue(materi);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing JSON", e);
                    }
                } else {
                    // Handle unsuccessful response
                    Log.e(TAG, "Response is not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Error API call: ", t);
            }
        });

        return data;
    }

    public void getBookmark() {
        LoginModel loginModel = LoginSession.getInstance().getLoginModel();

        String jsonBody = "{\"kry\":\""+loginModel.getKryId()+"\"}";

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonBody);

        Call<ResponseBody> call = mKategoriService.GetBookmark(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful() && response.body() != null) {

                    try {
                        String jsonString = response.body().string();
                        JSONArray jsonArray = new JSONArray(jsonString);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String smpId = String.valueOf(jsonObject.getString("matId"));
                            listArrayBookmark.add(smpId);
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
//                data.setValue(new ArrayList<>()); // Ensure data is set even if API call fails
            }
        });

//        return data;
    }

    public void deleteBookmark(String mat, String kry, Runnable onComplete) {
        LoginModel loginModel = LoginSession.getInstance().getLoginModel();
        String jsonBody = "{ \"kategori\": \"" + mat + "\", \"kry\":\""+loginModel.getKryId()+"\"}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonBody);

        Call<ResponseBody> call = mKategoriService.DeleteBookmark(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("TAG", "Bookmark deleted successfully");
                    if (listArrayBookmark != null) {
                        listArrayBookmark.remove(mat);
                    }
                    onComplete.run(); // Callback setelah operasi selesai
                } else {
                    Log.e("TAG", "Response is not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error API call: ", t.getMessage());
            }
        });
    }

    public void createBookmark(String kat, String kry, Runnable onComplete) {
        LoginModel loginModel = LoginSession.getInstance().getLoginModel();
        if (listArrayBookmark.isEmpty()) {
            getBookmark();
        }

        String jsonBody = "{ \"kategori\": \"" + kat + "\", \"kry\":\""+loginModel.getKryId()+"\"}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonBody);

        Call<ResponseBody> call = mKategoriService.CreateBookmark(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("TAG", "Bookmark created successfully");
                    if (listArrayBookmark != null && !listArrayBookmark.contains(kat)) {
                        listArrayBookmark.add(kat);
                    }
                    onComplete.run(); // Callback setelah operasi selesai
                } else {
                    Log.e("TAG", "Response is not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error API call: ", t.getMessage());
            }
        });
    }
}
