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

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.API.Repository.MateriRepository;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Activity.FileMateri;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.DBHelper.BookmarkDatabaseHelper;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KategoriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.MateriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.R;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {

    private List<KategoriModel> kategoriModelList;
    private List<MateriModel> materiModelList;
    private Context context;

    public BookmarkAdapter(List<KategoriModel> kategoriModelList, List<MateriModel> materiModelList, Context context) {
        this.kategoriModelList = kategoriModelList;
        this.materiModelList = materiModelList;
        this.context = context;
    }

    public void setKategoriModelList(List<KategoriModel> kategoriModelList) {
        this.kategoriModelList = kategoriModelList;
    }
    public void setMateriModelList(List<MateriModel> materiModelList){
        this.materiModelList = materiModelList;
        notifyDataSetChanged();
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
        holder.titleTextView.setText("Program : " + kategoriModel.getNamaKategori());

        for (MateriModel materiModel : materiModelList) {
            if (materiModel.getKategori().equals(kategoriModel.getNamaKategori())) {
                holder.tanggal.setText("Tanggal : " + materiModel.getCreadate());
                holder.author.setText("Author : " + materiModel.getUploader());
                holder.program.setText("Materi : " + materiModel.getJudulKK());
                break;
            } else {
                holder.tanggal.setText("Tanggal : ");
                holder.author.setText("Author : ");
                holder.program.setText("Materi : ");
            }
        }

        BookmarkDatabaseHelper dbHelper = new BookmarkDatabaseHelper(context);

        // Check if the item is bookmarked and update the bookmark button state
        boolean isBookmarked = dbHelper.isBookmarked(kategoriModel.getKey());
        holder.bookmarkButton.setImageResource(isBookmarked ? R.drawable.ic_bookmark_fill : R.drawable.ic_bookmark_empty);

        holder.bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbHelper.isBookmarked(kategoriModel.getNamaKategori())) {
                    dbHelper.removeBookmark(kategoriModel.getNamaKategori());
                    holder.bookmarkButton.setImageResource(R.drawable.ic_bookmark_empty);
                } else {
                    dbHelper.addBookmark(kategoriModel.getKey());
                    holder.bookmarkButton.setImageResource(R.drawable.ic_bookmark_fill);
                }
            }
        });

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
        return kategoriModelList.size();
    }

    public static class BookmarkViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView, tanggal, author, program;
        ImageButton bookmarkButton;
        LinearLayout button;

        public BookmarkViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            bookmarkButton = itemView.findViewById(R.id.bookmarkButton);
            button = itemView.findViewById(R.id.materi);
            tanggal = itemView.findViewById(R.id.dateTextView);
            author = itemView.findViewById(R.id.authorTextView);
            program = itemView.findViewById(R.id.programTextView);
        }
    }
}
