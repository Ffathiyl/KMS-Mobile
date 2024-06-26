package com.polytechnic.astra.ac.id.knowledgemanagementsystem;

import android.app.Application;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.LoginRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.ProdiRepository;

public class KnowledgeManagementSystemApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        ProdiRepository.initialize(this);
        LoginRepository.initialize(this);
    }
}
