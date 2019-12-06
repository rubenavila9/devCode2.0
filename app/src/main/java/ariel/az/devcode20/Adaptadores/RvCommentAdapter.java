package ariel.az.devcode20.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import ariel.az.devcode20.R;
import ariel.az.devcode20.models.ModelsGetMessages;

public class RvCommentAdapter extends RecyclerView.Adapter<RvCommentAdapter.ViewHolder> {
    private Activity context;
    private ArrayList<ModelsGetMessages> modelsGetMessages;
    private OnItemClickListener onItemClickListener;


    public RvCommentAdapter(Activity context, ArrayList<ModelsGetMessages> modelsGetMessages, OnItemClickListener onItemClickListener) {
        this.context = context;
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

        public ViewHolder( View itemView) {
            super(itemView);
            //buscar los id
            commentaryUser = itemView.findViewById(R.id.commentaryUser);
            linearLayoutComments = itemView.findViewById(R.id.linearLayoutComments);
            itemView.setOnCreateContextMenuListener(this);
        }

        public void setInformation(final ModelsGetMessages modelsGetMessages, final OnItemClickListener onItemClickListener){
            //se coloca los datos del usuario
            commentaryUser.setText(modelsGetMessages.getMessageuser());
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
                    Toast.makeText(context, "editar", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.delete:
                    Toast.makeText(context, "eliminar", Toast.LENGTH_SHORT).show();
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




    }


    public interface OnItemClickListener{
        void OnItemClick(ModelsGetMessages modelsGetMessages, int position);
    }
}
