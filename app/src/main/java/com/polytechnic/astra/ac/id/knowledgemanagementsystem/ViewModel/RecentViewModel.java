package com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.KategoriRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.MateriRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KategoriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.MateriModel;

import java.util.List;

public class RecentViewModel extends ViewModel {
    private MutableLiveData<MateriModel> mMyModelListMutableLiveData;
    private MateriRepository materiRepository;

    public RecentViewModel() {
        materiRepository = MateriRepository.get();
    }

    public MutableLiveData<MateriModel> getListModel() {
        // Load the most recent data every time this method is called
        mMyModelListMutableLiveData = materiRepository.getListRecent();
        return mMyModelListMutableLiveData;
    }
}
