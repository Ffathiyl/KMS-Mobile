package com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.ApiUtils;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.KKService;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.KategoriService;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.DBHelper.BookmarkDatabaseHelper;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KKModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KategoriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.LoginModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.LoginSession;

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

public class KategoriRepository {
    private static final String TAG = "KategoriRepository";
    private static KategoriRepository INSTANCE;
    private BookmarkDatabaseHelper dbHelper;
    private KategoriService mKategoriService;
    private String pro;

    public void setProdi(String pro) {
        this.pro = pro;
    }

    private KategoriRepository(Context context) {
        mKategoriService = ApiUtils.getKategoriService();
        dbHelper = new BookmarkDatabaseHelper(context);
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new KategoriRepository(context);
        }
    }

    public static KategoriRepository get() {
        return INSTANCE;
    }

    public MutableLiveData<List<KategoriModel>> getListKategori() {
        System.out.println("MEMEK: " + pro);
        MutableLiveData<List<KategoriModel>> data = new MutableLiveData<>();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{ \"page\": 1, \"query\": \"\", \"sort\": \"[Nama Kategori] asc\", \"Status\": \"Aktif\", \"Program\": \"" + pro + "\" }");
        System.out.println(body.contentType());
        Call<ResponseBody> call = mKategoriService.getDataKategori(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string();
                        JSONArray jsonArray = new JSONArray(jsonString);

                        List<KategoriModel> KategoriList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject kategoriObject = jsonArray.getJSONObject(i);
                            KategoriModel kategori = new KategoriModel();
                            kategori.setKey(kategoriObject.getString("Key"));
                            kategori.setNamaKategori(kategoriObject.getString("Nama Kategori"));
                            if (kategoriObject.getString("Deskripsi").length() > 30) {
                                kategori.setDeskripsi(kategoriObject.getString("Deskripsi").substring(0, 20) + " ...");
                            } else {
                                kategori.setDeskripsi(kategoriObject.getString("Deskripsi"));
                            }
                            kategori.setMaterialCount(kategoriObject.getString("MateriCount"));
                            kategori.setProID(kategoriObject.getString("ProID"));
//                            kk.setDeskripsi(kkObject.getString("Deskripsi"));
                            KategoriList.add(kategori);
                        }
                        data.setValue(KategoriList);
                        Log.d(TAG, "Data size: " + KategoriList.size());
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

    public MutableLiveData<List<KategoriModel>> getListKategoriById() {
        MutableLiveData<List<KategoriModel>> data = new MutableLiveData<>();
        List<KategoriModel> allKategoriList = new ArrayList<>();
        int[] completedCalls = {0};

        // Ambil daftar kategori yang dibookmark
        MutableLiveData<List<String>> bookmarkedCategoriesLiveData = getBookmark();
        bookmarkedCategoriesLiveData.observeForever(new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> bookmarkedCategories) {
                if (bookmarkedCategories != null && !bookmarkedCategories.isEmpty()) {
                    // Log isi bookmarkedCategories
                    Log.d(TAG, "Bookmarked PILIR: " + bookmarkedCategories);

                    // Fetch kategori berdasarkan id yang dibookmark
                    for (String categoryName : bookmarkedCategories) {
                        fetchKategoriById(categoryName, new KategoriCallback() {
                            @Override
                            public void onSuccess(List<KategoriModel> kategoriList) {
                                allKategoriList.addAll(kategoriList);
                                completedCalls[0]++;
                                if (completedCalls[0] == bookmarkedCategories.size()) {
                                    data.setValue(allKategoriList);
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                completedCalls[0]++;
                                if (completedCalls[0] == bookmarkedCategories.size()) {
                                    data.setValue(allKategoriList); // still update even if some calls fail
                                }
                            }
                        });
                    }
                } else {
                    // Handle case where there are no bookmarked categories
                    data.setValue(new ArrayList<>()); // Return empty list if no categories
                }
            }
        });

        return data;
    }



    private void fetchKategoriById(String categoryName, KategoriCallback callback) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{ \"page\": \"" + categoryName + "\"}");
        Call<ResponseBody> call = mKategoriService.getDataKategoriById(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string();
                        JSONArray jsonArray = new JSONArray(jsonString);

                        List<KategoriModel> KategoriList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject kategoriObject = jsonArray.getJSONObject(i);
                            KategoriModel kategori = new KategoriModel();
                            kategori.setKey(kategoriObject.getString("Key"));
                            kategori.setNamaKategori(kategoriObject.getString("Nama Kategori"));
                            if (kategoriObject.getString("Deskripsi").length() > 30) {
                                kategori.setDeskripsi(kategoriObject.getString("Deskripsi").substring(0, 20) + " ...");
                            } else {
                                kategori.setDeskripsi(kategoriObject.getString("Deskripsi"));
                            }
                            kategori.setProID(kategoriObject.getString("idProgram"));
                            System.out.println("POLOR:"+kategoriObject.getString("Nama Kategori"));
                            KategoriList.add(kategori);
                        }
                        callback.onSuccess(KategoriList);
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing JSON", e);
                        callback.onFailure(e);
                    }
                } else {
                    Log.e(TAG, "Response is not successful or body is null");
                    callback.onFailure(new Exception("Response is not successful or body is null"));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error API call : ", t.getMessage());
                callback.onFailure(t);
            }
        });
    }

    interface KategoriCallback {
        void onSuccess(List<KategoriModel> kategoriList);

        void onFailure(Throwable t);
    }

    public void createBookmark(String kat, String kry) {
        // Buat JSON request body
        String jsonBody = "{ \"kategori\": \"" + kat + "\", \"kry\": \"" + kry + "\"}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonBody);

        // Panggil endpoint createBookmark
        Call<ResponseBody> call = mKategoriService.CreateBookmark(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("TAG", "Bookmark created successfully");
                } else {
                    // Tangani kesalahan respon
                    Log.e("TAG", "Response is not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error API call: ", t.getMessage());
            }
        });
    }

    public MutableLiveData<List<String>> getBookmark() {
        // Ambil LoginModel dari LoginSession
        LoginModel loginModel = LoginSession.getInstance().getLoginModel();
        if (loginModel == null) {
            Log.e(TAG, "LoginModel is null");
            MutableLiveData<List<String>> data = new MutableLiveData<>();
            data.setValue(new ArrayList<>()); // Return empty list
            return data;
        }

        String kry = loginModel.getKryId();
        if (kry == null || kry.isEmpty()) {
            Log.e(TAG, "kry_id is null or empty");
            MutableLiveData<List<String>> data = new MutableLiveData<>();
            data.setValue(new ArrayList<>()); // Return empty list
            return data;
        }

        Log.d(TAG, "TITIKRY: " + kry);

        MutableLiveData<List<String>> data = new MutableLiveData<>();
        List<String> smpIds = new ArrayList<>();

        // Format JSON request body
        String jsonBody = "{\"kry\": \""+kry+"\"}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonBody);

        // Call API
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
                            // Convert smpId to String
                            String smpId = String.valueOf(jsonObject.getInt("smpId"));
                            smpIds.add(smpId);
                            System.out.println("TETEK:"+ smpId);
                        }

                        // Update LiveData with the list of smpIds
                        data.setValue(smpIds);
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing JSON", e);
                        data.setValue(new ArrayList<>()); // Ensure data is set even if parsing fails
                    }
                } else {
                    Log.e(TAG, "Response is not successful or body is null");
                    data.setValue(new ArrayList<>()); // Ensure data is set even if API response fails
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error API call: ", t.getMessage());
                data.setValue(new ArrayList<>()); // Ensure data is set even if API call fails
            }
        });

        return data;
    }



    private void fetchKategoriBySmpId(Integer smpId, KategoriCallback callback) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{ \"smpId\": " + smpId + " }");
        Call<ResponseBody> call = mKategoriService.GetBookmark(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonString = response.body().string();
                        JSONArray jsonArray = new JSONArray(jsonString);

                        List<KategoriModel> kategoriList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject kategoriObject = jsonArray.getJSONObject(i);
                            KategoriModel kategori = new KategoriModel();
                            kategori.setKey(kategoriObject.getString("matId")); // Assuming 'matId' is the key for the category
                            kategori.setNamaKategori(kategoriObject.getString("matId")); // Assuming 'matId' is used for the name
                            if (kategoriObject.getString("Deskripsi").length() > 30) {
                                kategori.setDeskripsi(kategoriObject.getString("Deskripsi").substring(0, 20) + " ...");
                            } else {
                                kategori.setDeskripsi(kategoriObject.getString("Deskripsi"));
                            }
                            kategori.setProID(kategoriObject.getString("kryId")); // Assuming 'kryId' is used for the program ID
                            kategoriList.add(kategori);
                        }
                        callback.onSuccess(kategoriList);
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing JSON", e);
                        callback.onFailure(e);
                    }
                } else {
                    Log.e(TAG, "Response is not successful or body is null");
                    callback.onFailure(new Exception("Response is not successful or body is null"));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error API call : ", t.getMessage());
                callback.onFailure(t);
            }
        });
    }

}
