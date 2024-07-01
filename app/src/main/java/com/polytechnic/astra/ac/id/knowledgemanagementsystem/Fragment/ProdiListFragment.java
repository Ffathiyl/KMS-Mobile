package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KKModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.ProdiModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.R;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.ViewModel.KKViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProdiListFragment extends RecyclerView.Adapter<ProdiListFragment.ProdiViewHolder> {

    private List<ProdiModel> prodiModelList;
    private Context context;

    public ProdiListFragment(List<ProdiModel> prodiModelList, Context context) {
        this.prodiModelList = prodiModelList;
        this.context = context;
    }

    public void setProdiModelList(List<ProdiModel> prodiModelList) {
        this.prodiModelList = prodiModelList;
    }

    @NonNull
    @Override
    public ProdiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recylerview_prodilist, parent, false);
        return new ProdiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdiViewHolder holder, int position) {
        ProdiModel prodiModel = prodiModelList.get(position);

        holder.prodi.setText(prodiModel.getNama());

        holder.recyclerViewKK.setHasFixedSize(true);
        holder.recyclerViewKK.setLayoutManager(new LinearLayoutManager(context));

        // Create KKListFragment adapter and set KKModelList
        KKListFragment kkAdapter = new KKListFragment(prodiModel.getKkModelList(), context);
        holder.recyclerViewKK.setAdapter(kkAdapter);

        // Initialize ViewModel for KKListFragment
        KKViewModel kkViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(KKViewModel.class);
        kkViewModel.getListModel().observe((LifecycleOwner) context, kkModels -> {
            List<KKModel> top2KkModels = new ArrayList<>();
            for (int i = 0; i < Math.min(kkModels.size(), 2); i++) {
                top2KkModels.add(kkModels.get(i));
            }
            kkAdapter.setKKModelList(top2KkModels);
            kkAdapter.notifyDataSetChanged();
        });

        // Set other fields as necessary
        holder.itemView.setOnClickListener(v -> {
            // Handle item click
        });
    }

    @Override
    public int getItemCount() {
        return prodiModelList.size();
    }

    public static class ProdiViewHolder extends RecyclerView.ViewHolder {

        TextView prodi;
        RecyclerView recyclerViewKK;

        public ProdiViewHolder(@NonNull View itemView) {
            super(itemView);
            prodi = itemView.findViewById(R.id.prodi);
            recyclerViewKK = itemView.findViewById(R.id.kklist);
        }
    }
}
