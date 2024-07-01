package com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.ProdiRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KKModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.KKRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.ProdiModel;

import java.util.List;

public class KKViewModel extends ViewModel {
    private MutableLiveData<List<KKModel>> mMyModelListMutableLiveData;
    private KKRepository mKKRepository;
    public KKViewModel(){
        mKKRepository = KKRepository.get();
//        mMyModelListMutableLiveData = mProdiRepository.getListProdi();
        mMyModelListMutableLiveData = mKKRepository.getListKK();
    }
    public MutableLiveData<List<KKModel>> getListModel(){
        return mMyModelListMutableLiveData;
    }

}
