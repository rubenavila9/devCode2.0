package ariel.az.devcode20.Adaptadores;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ariel.az.devcode20.Activities.accionendenuncias;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import ariel.az.devcode20.R;
import ariel.az.devcode20.models.ArrayListDenuncias;


public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.Admin> {

    Activity activity;
    ArrayList<ArrayListDenuncias> arrayListDenuncias;


    public ArrayList<ArrayListDenuncias> getArrayListDenuncias() {
        return arrayListDenuncias;
    }

    public AdminAdapter(Activity activity, ArrayList<ArrayListDenuncias> modelsLikesArrays) {
        this.activity = activity;
        this.arrayListDenuncias = modelsLikesArrays;
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
        holder.data(arrayListDenuncias.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayListDenuncias.size();
    }



    public class Admin extends RecyclerView.ViewHolder {

        private TextView CantidadDenuncias , MensajeDenunciado, UserDenunciado;
        private ImageView profile_image_denunciado;
        private LinearLayout linearDenunn;


        public Admin(@NonNull View itemView) {
            super(itemView);
            CantidadDenuncias = itemView.findViewById(R.id.CantidadDenuncias);
            MensajeDenunciado = itemView.findViewById(R.id.MensajeDenunciado);
            UserDenunciado = itemView.findViewById(R.id.UserDenunciado);
            profile_image_denunciado = itemView.findViewById(R.id.profile_image_denunciado);
            linearDenunn = itemView.findViewById(R.id.linearDenuncias);

            linearDenunn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 1/24/2020 cruzar el dato a la nueva pantalla
                    Intent intent = new Intent(activity, accionendenuncias.class);
                    intent.putExtra("idUserDen", arrayListDenuncias.get(getAdapterPosition()).getUserIduser());
                    intent.putExtra("imgUserDen", arrayListDenuncias.get(getAdapterPosition()).getIduser().getPhotouser());
                    intent.putExtra("emailUserDen", arrayListDenuncias.get(getAdapterPosition()).getIduser().getEmailuser());
                    intent.putExtra("nameUseDen", arrayListDenuncias.get(getAdapterPosition()).getIduser().getNameuser());
                    intent.putExtra("rolUseDen", arrayListDenuncias.get(getAdapterPosition()).getIduser().getRoleuser());
                    activity.startActivity(intent);
                }
            });
        }


        public  void data(final ArrayListDenuncias modelsLikesList){
            this.CantidadDenuncias.setText("" + String.valueOf(modelsLikesList.getComplemeints()));
            this.MensajeDenunciado.setText(modelsLikesList.getMessageuser());
            this.UserDenunciado.setText(modelsLikesList.getIduser().getNameuser());
            Glide.with(activity).load(modelsLikesList.getIduser().getPhotouser()).into(profile_image_denunciado);
        }
    }

    public void removeItem(int position){
        // TODO: 12/27/2019 eliminar el comentario
        arrayListDenuncias.remove(position);
        notifyItemRemoved(position);
    }

    private void restoreItem(String item, int position){
        // TODO: 12/27/2019 restaurar comentario
    }







}
