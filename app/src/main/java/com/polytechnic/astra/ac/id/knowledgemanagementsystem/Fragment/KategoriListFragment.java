package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.DBHelper.BookmarkDatabaseHelper;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.DBHelper.KategoriDatabaseHelper;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KKModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KategoriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.LoginModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.LoginSession;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.MateriModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.R;

import java.util.List;

public class KategoriListFragment extends RecyclerView.Adapter<KategoriListFragment.KategoriViewHolder> {

    private List<MateriModel> materiModelList;
    private Context context;

    public KategoriListFragment(List<MateriModel> materiModelList, Context context) {
        this.materiModelList = materiModelList;
        this.context = context;
    }

    public void setMateriModelList(List<MateriModel> materiModelList){
        this.materiModelList = materiModelList;
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
        MateriModel materiModel = materiModelList.get(position);
        holder.titleTextView.setText("" + materiModel.getJudulKK());
        holder.tanggal.setText("Tanggal : " + materiModel.getCreadate());
        holder.author.setText("Author : " + materiModel.getUploader());
        holder.program.setText("Kategori : " + materiModel.getKategori());

        MateriRepository materiRepository = MateriRepository.get();

        LoginModel loginModel = LoginSession.getInstance().getLoginModel();

        holder.bookmarkButton.setImageResource(materiModel.isBookmark() ? R.drawable.ic_bookmark_fill : R.drawable.ic_bookmark_empty);

        holder.bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginModel != null) {
                    if (materiModel.isBookmark()) {
                        // Hapus bookmark
                        materiRepository.deleteBookmark(materiModel.getKey(), loginModel.getKryId());
                        holder.bookmarkButton.setImageResource(R.drawable.ic_bookmark_empty);
                    } else {
                        // Tambah bookmark
                        materiRepository.createBookmark(materiModel.getKey(), loginModel.getKryId());
                        holder.bookmarkButton.setImageResource(R.drawable.ic_bookmark_fill);
                    }
                } else {
                    Log.e("Bookmark", "LoginModel is null");
                }
            }
        });

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KategoriRepository kategoriRepository = KategoriRepository.get();
                kategoriRepository.createRecent(materiModel.getKey(), loginModel.getKryId());

                Intent intent = new Intent(context, FileMateri.class);
                intent.putExtra("materiModel", materiModel);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return materiModelList != null ? materiModelList.size() : 0;
    }

    public static class KategoriViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView descriptionTextView, tanggal, author, program;
        ImageButton bookmarkButton;
        LinearLayout button;

        public KategoriViewHolder(@NonNull View itemView) {
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
