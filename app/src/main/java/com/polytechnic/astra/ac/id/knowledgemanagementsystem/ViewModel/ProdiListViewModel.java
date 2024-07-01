package com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.ProdiRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.ProdiModel;

import java.util.List;

public class ProdiListViewModel extends ViewModel {
    private MutableLiveData<List<ProdiModel>> mMyModelListMutableLiveData;
    private ProdiRepository mProdiRepository;
    public ProdiListViewModel(){
        mProdiRepository = ProdiRepository.get();
        mMyModelListMutableLiveData = mProdiRepository.getListProdi();
//        mMyModelListMutableLiveData = mProdiRepository.getListKK();
    }
    public MutableLiveData<List<ProdiModel>> getListModel(){
        return mMyModelListMutableLiveData;
    }
}
