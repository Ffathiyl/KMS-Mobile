package com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.KategoriRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KategoriModel;

import java.util.List;

public class BookmarkViewModel extends ViewModel {
    private MutableLiveData<List<KategoriModel>> mMyModelListMutableLiveData;
    private KategoriRepository mKategoriRepository;
    public BookmarkViewModel(){
        mKategoriRepository = KategoriRepository.get();

        mMyModelListMutableLiveData = mKategoriRepository.getListKategoriById();
    }
    public MutableLiveData<List<KategoriModel>> getListModel(){
        return mMyModelListMutableLiveData;
    }
}
