package com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.ProgramRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.ProgramModel;

import java.util.List;

public class ProgramViewModel extends ViewModel {
    private MutableLiveData<List<ProgramModel>> mMyModelListMutableLiveData;
    private ProgramRepository mProgramRepository;
    public ProgramViewModel(){
        mProgramRepository = ProgramRepository.get();
        mMyModelListMutableLiveData = mProgramRepository.getListProgram();
//        mMyModelListMutableLiveData = mProdiRepository.getListKK();
    }
    public MutableLiveData<List<ProgramModel>> getListModel(){
        return mMyModelListMutableLiveData;
    }
}
