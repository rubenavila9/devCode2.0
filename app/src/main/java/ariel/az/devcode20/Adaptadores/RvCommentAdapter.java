package ariel.az.devcode20.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import ariel.az.devcode20.R;
import ariel.az.devcode20.models.ModelsGetMessages;
import ariel.az.devcode20.models.ModelsUser;

public class RvCommentAdapter extends RecyclerView.Adapter<RvCommentAdapter.ViewHolder> {
    private Activity activity;
    private String emailUser;
    private ArrayList<ModelsGetMessages> modelsGetMessages;
    private OnItemClickListener onItemClickListener;
    private Integer idPublication;
    private ModelsUser modelsUsers;




    public RvCommentAdapter(Activity context,String roleUser, Integer idPublication ,ArrayList<ModelsGetMessages> modelsGetMessages, OnItemClickListener onItemClickListener) {
        this.activity = context;
        this.emailUser = roleUser;
        this.idPublication = idPublication;
        this.modelsGetMessages = modelsGetMessages;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_comments,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //obtenemos las posiciones por cada publicacion
        holder.setInformation(modelsGetMessages.get(position),onItemClickListener);
    }



    @Override
    public int getItemCount() {
        return modelsGetMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        private TextView commentaryUser, countLike;
        private LinearLayout linearLayoutComments;
        private ImageView photoUserMessage, photoLike;
        private ArrayList<String> likeByComment = new ArrayList<>();

        public ViewHolder( View itemView) {
            super(itemView);
            //buscar los id
            commentaryUser = itemView.findViewById(R.id.commentaryUser);
            linearLayoutComments = itemView.findViewById(R.id.linearLayoutComments);
            photoUserMessage = itemView.findViewById(R.id.photoUserMessage);
            countLike = itemView.findViewById(R.id.countLike);
            photoLike = itemView.findViewById(R.id.imageLike);
            itemView.setOnCreateContextMenuListener(this);
        }

        public void setInformation(final ModelsGetMessages modelsGetMessages, final OnItemClickListener onItemClickListener){
            //se coloca los datos del usuario

            commentaryUser.setText(modelsGetMessages.getMessageuser());
            Glide.with(activity).load(modelsGetMessages.getUser().getPhotouser()).into(photoUserMessage);
            if (modelsGetMessages.getLike() > modelsGetMessages.likeByCommentary){
                photoLike.setVisibility(View.VISIBLE);
                countLike.setText(modelsGetMessages.getLike() + "");
                likeByComment.add(modelsGetMessages.getMessageuser());
                //show();
            }


                linearLayoutComments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.OnItemClick(modelsGetMessages,getAdapterPosition());
                    }
                });
        }

        private void show() {

        }


        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.edit:
                    //obtiene la posicion con el getAdapterPosition
                    //editMessage(getAdapterPosition());
                    Toast.makeText(activity, "editar", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.delete:
                    //obtiene la posicion con el getAdapterPosition
                    //deleteMessage(getAdapterPosition());
                    Toast.makeText(activity, "delete", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.denunciar:
                    Toast.makeText(activity, "denunciar", Toast.LENGTH_SHORT).show();
                    return  true;
            }
            return false;
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //recogemos la position con el metodo getAdapterPosition
            ModelsGetMessages modelsGet = modelsGetMessages.get(this.getAdapterPosition());

            if (emailUser.equals(modelsGet.getUser().getEmailuser())){
                //establecemos el titulo para cada elemento
                menu.setHeaderTitle(modelsGet.getMessageuser());
                MenuInflater inflater = activity.getMenuInflater();
                inflater.inflate(R.menu.menumessages,menu);

            }

            if (!emailUser.equals(modelsGet.getUser().getEmailuser())){
                menu.setHeaderTitle("Delete");
                MenuInflater inflater = activity.getMenuInflater();
                inflater.inflate(R.menu.denunciar,menu);
            }

            for (int i=0; i< menu.size(); i++){
                // Por ultimo añadimos uno por uno el listener onMenuItemClick para
                // controlar las acciones en el contextMenu anteriormente lo manejabamos
                // con el metodo onContextItemSelected en el activity
                menu.getItem(i).setOnMenuItemClickListener(this);
            }

        }

        private void deleteMessage(Integer id){
            //eliminar mensaje
            modelsGetMessages.remove(id);
            notifyItemRemoved(id);

        }



        private void editMessage(Integer id){
            //editar mensaje

        }

    }

    public interface OnItemClickListener{
        void OnItemClick(ModelsGetMessages modelsGetMessages, int position);
    }
}
