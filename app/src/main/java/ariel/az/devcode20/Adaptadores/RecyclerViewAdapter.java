package ariel.az.devcode20.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ariel.az.devcode20.models.ModelsPublications;
import ariel.az.devcode20.models.ModelsPublicationsList;
import ariel.az.devcode20.models.Publicacion;
import ariel.az.devcode20.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{


    private ArrayList<ModelsPublicationsList> publicacion;
    private Context mContext;

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

        CircleImageView profile_image;
        TextView nameUser;
        TextView publicacionUser;
        LinearLayout layoutRecyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.profile_image);
            nameUser = itemView.findViewById(R.id.nameUser);
            publicacionUser = itemView.findViewById(R.id.publicationUser);
            layoutRecyclerView = itemView.findViewById(R.id.layoutRecyclerView);
        }

        public void dim (ModelsPublicationsList publicacion){
            this.nameUser.setText(publicacion.getNamepublication());
            this.publicacionUser.setText(publicacion.getDescriptpublication());
            //Glide.with(mContext).load(publicacion.getImage_user()).into(profile_image);
        }



    }

}
