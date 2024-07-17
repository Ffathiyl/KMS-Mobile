package com.polytechnic.astra.ac.id.knowledgemanagementsystem;

import android.app.Application;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.KKRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.KategoriRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.LoginRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.MateriRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.ProdiRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.ProgramRepository;

public class KnowledgeManagementSystemApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        ProdiRepository.initialize(this);
        KKRepository.initialize(this);
        LoginRepository.initialize(this);
        ProgramRepository.initialize(this);
        KategoriRepository.initialize(this);
        MateriRepository.initialize(this);
    }
}
