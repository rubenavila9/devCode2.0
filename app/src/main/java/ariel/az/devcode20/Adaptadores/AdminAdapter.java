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

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import ariel.az.devcode20.R;
import ariel.az.devcode20.models.ArrayListDenuncias;


public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.Admin> {

    Activity activity;
    ArrayList<ArrayListDenuncias> arrayListDenuncias;
    OnItemClick onItemClick;




    public AdminAdapter( Activity activity, ArrayList<ArrayListDenuncias> modelsLikesArrays, OnItemClick onItemClick) {
        this.activity = activity;
        this.arrayListDenuncias = modelsLikesArrays;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public Admin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_denuncias,parent,false);
        Admin admin = new Admin(view);
        return admin;
    }

    @Override
    public void onBindViewHolder(@NonNull Admin holder, int position) {
        holder.data(arrayListDenuncias.get(position),onItemClick);
    }

    @Override
    public int getItemCount() {
        return arrayListDenuncias.size();
    }



    public class Admin extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        private TextView CantidadDenuncias , MensajeDenunciado, UserDenunciado;
        private ImageView profile_image_denunciado;


        public Admin(@NonNull View itemView) {
            super(itemView);
            CantidadDenuncias = itemView.findViewById(R.id.CantidadDenuncias);
            MensajeDenunciado = itemView.findViewById(R.id.MensajeDenunciado);
            UserDenunciado = itemView.findViewById(R.id.UserDenunciado);
            profile_image_denunciado = itemView.findViewById(R.id.profile_image_denunciado);
        }


        public  void data(final ArrayListDenuncias modelsLikesList, final OnItemClick onItemClick){
            this.CantidadDenuncias.setText("" + String.valueOf(modelsLikesList.getLikepublications()));
            this.MensajeDenunciado.setText(modelsLikesList.getMessageuser());
            this.UserDenunciado.setText(modelsLikesList.getIduser().getNameuser());
            Glide.with(activity).load(modelsLikesList.getIduser().getPhotouser()).into(profile_image_denunciado);


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
        void ItemClick(ArrayListDenuncias modelsGetMessages, int position);
    }
}
