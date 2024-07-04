package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Activity.MataKuliah;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Activity.Materi;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.ProgramModel;
import com.polytechnic.astra.ac.id.knowledgemanagementsystem.R;

import java.util.List;

public class ProgramListFragment extends RecyclerView.Adapter<ProgramListFragment.ProgramViewHolder> {

    private List<ProgramModel> programModelList;
    private Context context;

    public ProgramListFragment(List<ProgramModel> programModelList, Context context) {
        this.programModelList = programModelList;
        this.context = context;
    }


    public void setProgramModelList(List<ProgramModel> programModelList) {
        this.programModelList = programModelList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ProgramListFragment.ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_matkul, parent, false);
        return new ProgramListFragment.ProgramViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramListFragment.ProgramViewHolder holder, int position) {

        ProgramModel programModel = programModelList.get(position);
        System.out.println(programModel.getNamaProgram());
        holder.JudulMatkul.setText(programModel.getNamaProgram());
        holder.DeskripsiMatkul.setText(programModel.getDeskripsi());
        holder.totalMateri.setText("Total : 0");

        holder.materiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Materi.class);
                intent.putExtra("programModel", programModel);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return programModelList != null ? programModelList.size() : 0;
    }

    public static class ProgramViewHolder extends RecyclerView.ViewHolder {

        TextView JudulMatkul;
        TextView DeskripsiMatkul;
        TextView totalMateri;
        Button materiButton;


        public ProgramViewHolder(@NonNull View itemView) {
            super(itemView);
            JudulMatkul = itemView.findViewById(R.id.courseTitle);
            DeskripsiMatkul = itemView.findViewById(R.id.courseDescription);
            totalMateri = itemView.findViewById(R.id.courseMaterialCount);
            materiButton = itemView.findViewById(R.id.viewMaterialsButton);
        }

    }
}