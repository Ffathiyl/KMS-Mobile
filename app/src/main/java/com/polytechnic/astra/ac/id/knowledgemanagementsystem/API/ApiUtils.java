package com.polytechnic.astra.ac.id.knowledgemanagementsystem.API;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.KKService;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.KategoriService;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.MateriService;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.ProdiService;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.LoginService;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.ProgramService;

public class ApiUtils {
    public static final String API_URL = "http://10.1.6.178:8080/";

    public ApiUtils() {
    }

    public static ProdiService getProdiService(){
        return RetrofitClient.getClient(API_URL).create(ProdiService.class);
    }

    public static LoginService getLoginService(){
        return RetrofitClient.getClient(API_URL).create(LoginService.class);
    }

    public static KKService getKKService(){
        return RetrofitClient.getClient(API_URL).create(KKService.class);
    }

    public static ProgramService getProgramService(){
        return RetrofitClient.getClient(API_URL).create(ProgramService.class);
    }

    public static KategoriService getKategoriService(){
        return RetrofitClient.getClient(API_URL).create(KategoriService.class);
    }

    public static MateriService getMateriService(){
        return RetrofitClient.getClient(API_URL).create(MateriService.class);
    }
}
