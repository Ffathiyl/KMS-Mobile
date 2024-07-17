package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.KategoriRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.MateriRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.ProgramRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Activity.FileMateri;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Activity.MataKuliah;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KKModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KategoriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.R;

import java.util.List;

public class KategoriListFragment extends RecyclerView.Adapter<KategoriListFragment.KategoriViewHolder> {

    private List<KategoriModel> kategoriModelList;
    private Context context;

    public KategoriListFragment(List<KategoriModel> kategoriModelList, Context context) {
        this.kategoriModelList = kategoriModelList;
        this.context = context;
    }


    public void setKategoriModelList(List<KategoriModel> kategoriModelList) {
        this.kategoriModelList = kategoriModelList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public KategoriListFragment.KategoriViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_materi, parent, false);
        return new KategoriListFragment.KategoriViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KategoriListFragment.KategoriViewHolder holder, int position) {

        KategoriModel kategoriModel = kategoriModelList.get(position);
        holder.titleTextView.setText("Materi : " + kategoriModel.getNamaKategori());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MateriRepository materiRepository = MateriRepository.get();
                materiRepository.setKat(kategoriModel.getKey());
                Intent intent = new Intent(context, FileMateri.class);
                intent.putExtra("kategoriModel", kategoriModel);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return kategoriModelList != null ? kategoriModelList.size() : 0;
    }

    public static class KategoriViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView descriptionTextView;
        ImageButton iconImageView;
        LinearLayout button;

        public KategoriViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
            button = itemView.findViewById(R.id.materi);
        }

    }

}
