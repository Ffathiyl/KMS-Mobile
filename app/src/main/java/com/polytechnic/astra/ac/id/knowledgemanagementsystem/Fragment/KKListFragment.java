    package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment;

    import androidx.fragment.app.Fragment;
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

    import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Activity.MataKuliah;
    import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Activity.ProgramKeilmuan;
    import com.polytechnic.astra.ac.id.knowledgemanagementsystem.Model.KKModel;
    import com.polytechnic.astra.ac.id.knowledgemanagementsystem.R;

    import java.util.List;

    public class KKListFragment extends RecyclerView.Adapter<KKListFragment.KKViewHolder> {

        private List<KKModel> kkModelList;
        private Context context;

        public KKListFragment(List<KKModel> kkModelList, Context context) {
            this.kkModelList = kkModelList;
            this.context = context;
        }


        public void setKKModelList(List<KKModel> kkModelList) {
            this.kkModelList = kkModelList;
            notifyDataSetChanged();
        }
        @NonNull
        @Override
        public KKViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_kk, parent, false);
            return new KKViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull KKViewHolder holder, int position) {

            KKModel kkModel = kkModelList.get(position);
            holder.titleTextView.setText(kkModel.getNamaKelompokKeahlian());
            holder.descriptionTextView.setText(kkModel.getDeskripsi());

            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MataKuliah.class);
                    intent.putExtra("kkModel", kkModel);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return kkModelList != null ? kkModelList.size() : 0;
        }

        public static class KKViewHolder extends RecyclerView.ViewHolder {

            TextView titleTextView;
            TextView descriptionTextView;
            ImageButton iconImageView;
            LinearLayout button;

            public KKViewHolder(@NonNull View itemView) {
                super(itemView);
                titleTextView = itemView.findViewById(R.id.titleTextView);
                descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
                iconImageView = itemView.findViewById(R.id.iconImageView);
                button = itemView.findViewById(R.id.buttonPanel);
            }

        }
    }
