package ariel.az.devcode20.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ariel.az.devcode20.Activities.DetailsPublicationsActivity;
import ariel.az.devcode20.configurationAndRouters.Router;
import ariel.az.devcode20.configurationAndRouters.conexion;
import ariel.az.devcode20.models.ModelsPublicationsList;
import ariel.az.devcode20.models.ModelsUser;
import ariel.az.devcode20.R;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{


    private ArrayList<ModelsPublicationsList> publicacion;
    private Context mContext;
    private ModelsUser modelsUsers;

    public RecyclerViewAdapter(ArrayList<ModelsPublicationsList> publicacion, Context mContext) {
        this.publicacion = publicacion;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.publicaionhome, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.dim(publicacion.get(position));
    }


    @Override
    public int getItemCount() {
        return publicacion.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        Router router = conexion.getApiService();
        CircleImageView profile_image;
        TextView nameUser, title, publicacionUser;
        LinearLayout layoutRecyclerView;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.profile_image);
            nameUser = itemView.findViewById(R.id.nameUser);
            publicacionUser = itemView.findViewById(R.id.publicationUser);
            layoutRecyclerView = itemView.findViewById(R.id.layoutRecyclerView);
            title = itemView.findViewById(R.id.title_publication);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //les coloco un id a cada dato para luego cruzarlo a ptro activity
                    int position = getAdapterPosition();
                    Intent intent = new Intent(mContext, DetailsPublicationsActivity.class);
                    intent.putExtra("idPublication",publicacion.get(position).getIdpublication().toString());
                    intent.putExtra("titlePublication",publicacion.get(position).getNamepublication());
                    intent.putExtra("detailsPublications",publicacion.get(position).getDescriptpublication());
                    intent.putExtra("idUser",publicacion.get(position).getUserid());
                    mContext.startActivity(intent);
                }
            });
        }
        public void dim (ModelsPublicationsList publicacion){
            this.nameUser.setText(publicacion.getNamepublication());
            this.publicacionUser.setText(publicacion.getDescriptpublication());
            cosultTwo(publicacion.getUserid());
        }

        private void cosultTwo(int id) {
            //aqui recibe el id del usuario para hacer otra consulta y obtener los datos del
            //del usuario y colocar fotos y su nombre
            final Call<ModelsUser> listUserCall = router.setId(id);
            listUserCall.enqueue(new Callback<ModelsUser>() {
                @Override
                public void onResponse(Call<ModelsUser> call, Response<ModelsUser> response) {
                    if (response.isSuccessful()){
                        modelsUsers = response.body();
                        title.setText(modelsUsers.getNameuser());
                        Glide.with(mContext).load(modelsUsers.getPhotouser()).into(profile_image);
                    }
                }

                @Override
                public void onFailure(Call<ModelsUser> call, Throwable t) {

                }
            });

        }



    }

}
