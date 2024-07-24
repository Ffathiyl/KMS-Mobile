package com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.KategoriRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.MateriRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KategoriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.MateriModel;

import java.io.File;
import java.util.List;

public class MateriViewModel extends ViewModel {
    private MutableLiveData<List<MateriModel>> mMyModelListMutableLiveData;
    private MateriRepository mMateriRepository;
    public MateriViewModel(){
        mMateriRepository = MateriRepository.get();
//        mMyModelListMutableLiveData = mProdiRepository.getListProdi();
        mMyModelListMutableLiveData = mMateriRepository.getListMateri();
    }
    public MutableLiveData<List<MateriModel>> getListModel(){
        return mMyModelListMutableLiveData;
    }
    public void loadMateri() {
        mMateriRepository.getListMateri().observeForever(materiModels -> mMyModelListMutableLiveData.postValue(materiModels));
    }
}
