package com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.KKRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.KategoriRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KKModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KategoriModel;

import java.util.List;

public class KategoriViewModel extends ViewModel {
    private MutableLiveData<List<KategoriModel>> mMyModelListMutableLiveData;
    private KategoriRepository mKategoriRepository;
    public KategoriViewModel(){
        mKategoriRepository = KategoriRepository.get();
//        mMyModelListMutableLiveData = mProdiRepository.getListProdi();
        mMyModelListMutableLiveData = mKategoriRepository.getListKategori();
    }
    public MutableLiveData<List<KategoriModel>> getListModel(){
        return mMyModelListMutableLiveData;
    }

}
