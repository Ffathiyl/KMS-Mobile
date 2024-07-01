    package com.polytechnic.astra.ac.id.knowledgemanagementsystem.Fragment;

    import androidx.fragment.app.Fragment;
    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageButton;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

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
            if (kkModelList == null) return; // Null check to avoid NullPointerException

            KKModel kkModel = kkModelList.get(position);
            holder.titleTextView.setText(kkModel.getNamaKelompokKeahlian());
            holder.descriptionTextView.setText(kkModel.getDeskripsi());
            // Set other fields as necessary

            // You can handle click events here if needed
            holder.iconImageView.setOnClickListener(v -> {
                // Handle icon click
            });

            holder.itemView.setOnClickListener(v -> {
                // Handle item click
            });
        }

        @Override
        public int getItemCount() {
            return kkModelList != null ? kkModelList.size() : 0; // Return 0 if list is null
        }

        public static class KKViewHolder extends RecyclerView.ViewHolder {

            TextView titleTextView;
            TextView descriptionTextView;
            ImageButton iconImageView;

            public KKViewHolder(@NonNull View itemView) {
                super(itemView);
                titleTextView = itemView.findViewById(R.id.titleTextView);
                descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
                iconImageView = itemView.findViewById(R.id.iconImageView);
            }

        }
    }
