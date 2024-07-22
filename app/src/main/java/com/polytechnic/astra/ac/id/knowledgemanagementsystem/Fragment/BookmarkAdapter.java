package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KategoriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.R;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {

    private List<KategoriModel> kategoriModelList;
    private Context context;

    public BookmarkAdapter(List<KategoriModel> kategoriModelList, Context context) {
        this.kategoriModelList = kategoriModelList;
        this.context = context;
    }

    public void setKategoriModelList(List<KategoriModel> kategoriModelList) {
        this.kategoriModelList = kategoriModelList;
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_materi, parent, false);
        return new BookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        KategoriModel kategoriModel = kategoriModelList.get(position);
        holder.titleTextView.setText("Materi : " + kategoriModel.getNamaKategori());
        // Populate other views if necessary
    }

    @Override
    public int getItemCount() {
        return kategoriModelList.size();
    }

    public static class BookmarkViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;

        public BookmarkViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
        }
    }
}
