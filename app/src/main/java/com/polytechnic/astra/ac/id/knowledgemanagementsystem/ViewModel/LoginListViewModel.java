package com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.LoginRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.ProdiRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.LoginModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.ProdiModel;

import java.util.List;

public class LoginListViewModel extends ViewModel {

    private MutableLiveData<List<LoginModel>> mMyModelListMutableLiveData;
    private LoginRepository mLoginRepository;
    public LoginListViewModel(){
        mLoginRepository = LoginRepository.get();
//        mMyModelListMutableLiveData = mProdiRepository.getListProdi();
        mMyModelListMutableLiveData = mLoginRepository.getLogin();
    }
    public MutableLiveData<List<LoginModel>> getLogin(){
        return mMyModelListMutableLiveData;
    }
}
