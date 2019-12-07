package ariel.az.devcode20.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import ariel.az.devcode20.R;
import ariel.az.devcode20.models.ModelsGetMessages;
import ariel.az.devcode20.models.ModelsUser;


import static android.content.Context.MODE_PRIVATE;

public class RvCommentAdapter extends RecyclerView.Adapter<RvCommentAdapter.ViewHolder> {
    private Activity context;
    private ArrayList<ModelsGetMessages> modelsGetMessages;
    private OnItemClickListener onItemClickListener;
    private Integer idPublication;
    private ModelsUser modelsUsers;



    public RvCommentAdapter(Activity context,Integer idPublication ,ArrayList<ModelsGetMessages> modelsGetMessages, OnItemClickListener onItemClickListener) {
        this.context = context;
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

        private TextView commentaryUser;
        private LinearLayout linearLayoutComments;
        private ImageView photoUserMessage;


        public ViewHolder( View itemView) {
            super(itemView);
            //buscar los id
            commentaryUser = itemView.findViewById(R.id.commentaryUser);
            linearLayoutComments = itemView.findViewById(R.id.linearLayoutComments);
            photoUserMessage = itemView.findViewById(R.id.photoUserMessage);
            itemView.setOnCreateContextMenuListener(this);
        }

        public void setInformation(final ModelsGetMessages modelsGetMessages, final OnItemClickListener onItemClickListener){
            //se coloca los datos del usuario
            commentaryUser.setText(modelsGetMessages.getMessageuser());
            Glide.with(context).load(modelsGetMessages.getUser().getPhotouser()).into(photoUserMessage);
                linearLayoutComments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.OnItemClick(modelsGetMessages,getAdapterPosition());
                    }
                });
        }




        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.edit:
                    //obtiene la posicion con el getAdapterPosition
                    editMessage(getAdapterPosition());
                    return true;
                case R.id.delete:
                    //obtiene la posicion con el getAdapterPosition
                    deleteMessage(getAdapterPosition());
                    return true;
            }
            return false;
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //recogemos la position con el metodo getAdapterPosition
            ModelsGetMessages modelsGet = modelsGetMessages.get(this.getAdapterPosition());
            //establecemos el titulo para cada elemento
                menu.setHeaderTitle(modelsGet.getMessageuser());
                MenuInflater inflater = context.getMenuInflater();
                //inflamos el menu
                inflater.inflate(R.menu.menumessages,menu);
                // Por ultimo añadimos uno por uno el listener onMenuItemClick para
                // controlar las acciones en el contextMenu anteriormente lo manejabamos
                // con el metodo onContextItemSelected en el activity
                for (int i=0; i< menu.size(); i++){
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
