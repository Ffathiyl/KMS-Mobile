package com.polytechnic.astra.ac.id.knowledgemanagementsystem.API;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.ProdiService;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Service.LoginService;

public class ApiUtils {
    public static final String API_URL = "http://10.1.17.199:8080/";

    public ApiUtils() {
    }

    public static ProdiService getProdiService(){
        return RetrofitClient.getClient(API_URL).create(ProdiService.class);
    }

    public static LoginService getLoginService(){
        return RetrofitClient.getClient(API_URL).create(LoginService.class);
    }
}
