package com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.KategoriRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.MateriRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KategoriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.MateriModel;

import java.util.List;

public class BookmarkViewModel extends ViewModel {
    private MutableLiveData<List<MateriModel>> mMyModelListMutableLiveData;
    private MateriRepository materiRepository;
    public BookmarkViewModel(){
        materiRepository = MateriRepository.get();

        mMyModelListMutableLiveData = materiRepository.getListMateriTersimpan();
    }
    public MutableLiveData<List<MateriModel>> getListModel(){
        return mMyModelListMutableLiveData;
    }
}
