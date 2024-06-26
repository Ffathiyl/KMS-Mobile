package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.ProdiModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.R;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.ProdiListViewModel;

import java.util.Collections;
import java.util.List;

public class ProdiListFragment extends Fragment {
    private ProdiListViewModel mProdiListViewModel;
    private RecyclerView mProdiRecyclerView;
    private ProdiAdapter mProdiAdapter;

    public static ProdiListFragment newInstance(){
        return new ProdiListFragment();
    }

    private class ProdiHolder extends RecyclerView.ViewHolder {

        private TextView mIdTextView;
        private TextView mUsernameTextView;
        private ProdiModel mProdiModel;

        public ProdiHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.recylerview_prodilist, parent, false));
            mIdTextView = (TextView) itemView.findViewById(R.id.txv_id);
            mUsernameTextView = (TextView) itemView.findViewById(R.id.txv_username);
        }

        public void bind(ProdiModel data){
            mProdiModel = data;
            mIdTextView.setText(mProdiModel.getId());
            mUsernameTextView.setText(mProdiModel.getNama());
        }
    }

    private class ProdiAdapter extends RecyclerView.Adapter<ProdiHolder>{
        private List<ProdiModel> mProdiModelList;
        public ProdiAdapter(List<ProdiModel> datas){
            mProdiModelList = datas;
        }

        @Override
        public ProdiHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ProdiHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ProdiHolder holder, int position){
            ProdiModel data = mProdiModelList.get(position);
            holder.bind(data);
        }

        @Override
        public int getItemCount(){
            return mProdiModelList.size();
        }
    }

    private void updateUI(List<ProdiModel> datas){
        mProdiAdapter = new ProdiAdapter(datas);
        mProdiRecyclerView.setAdapter(mProdiAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProdiListViewModel = new ViewModelProvider(this)
                .get(ProdiListViewModel.class);
        mProdiAdapter = new ProdiAdapter(Collections.<ProdiModel>emptyList());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prodilist,
                container, false);
        mProdiRecyclerView = (RecyclerView) view.findViewById(R.id.rvw_mymodel);
        mProdiRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProdiRecyclerView.setAdapter(mProdiAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mProdiListViewModel.getListModel().observe(
                getViewLifecycleOwner(),
                new Observer<List<ProdiModel>>() {
                    @Override
                    public void onChanged(List<ProdiModel> myModelList) {
                        updateUI(myModelList);
                        Log.d("TEST","Called" + myModelList.size());
                    }
                }
        );
    }
}
