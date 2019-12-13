package ariel.az.devcode20.Adaptadores;

import android.app.Activity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import ariel.az.devcode20.R;
import ariel.az.devcode20.models.ModelsReconstruir;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.Admin> {

    Activity activity;
    ArrayList<ModelsReconstruir> modelsLikesArrays;
    OnItemClick onItemClick;
    String email;



    public AdminAdapter(String email, Activity activity, ArrayList<ModelsReconstruir> modelsLikesArrays, OnItemClick onItemClick) {
        this.email = email;
        this.activity = activity;
        this.modelsLikesArrays = modelsLikesArrays;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public Admin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_comments,parent,false);
        Admin admin = new Admin(view);
        return admin;
    }

    @Override
    public void onBindViewHolder(@NonNull Admin holder, int position) {
        holder.data(modelsLikesArrays.get(position),onItemClick);
    }

    @Override
    public int getItemCount() {
        return modelsLikesArrays.size();
    }



    public class Admin extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        TextView title , countLike;
        private LinearLayout linearLayoutComments;
        ImageView photoLike;


        public Admin(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.commentaryUser);
            countLike = itemView.findViewById(R.id.countLike);
            linearLayoutComments = itemView.findViewById(R.id.linearLayoutComments);
            photoLike = itemView.findViewById(R.id.imageLike);
        }


        public  void data(final ModelsReconstruir modelsLikesList, final OnItemClick onItemClick){
            //the magic
            title.setText(modelsLikesList.getModelsGetMessages().getMessageuser());
            if (modelsLikesList.getIdlike() > 0){
                photoLike.setVisibility(View.VISIBLE);
                countLike.setText(modelsLikesList.getModelsGetMessages().getLikepublication().toString());
            }





            linearLayoutComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClick.ItemClick(modelsLikesList,getAdapterPosition());
                }
            });

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            return false;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }

    public interface OnItemClick{
        void ItemClick(ModelsReconstruir modelsGetMessages, int position);
    }
}
