package ariel.az.devcode20.Adaptadores;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.Stack;

import ariel.az.devcode20.R;
import ariel.az.devcode20.SharedPreferencesUser.SaveDataUser;
import ariel.az.devcode20.configurationAndRouters.Router;
import ariel.az.devcode20.configurationAndRouters.conexion;
import ariel.az.devcode20.models.ModelsGetMessages;
import ariel.az.devcode20.models.ModelsMensajes;
import ariel.az.devcode20.models.ModelsSendLikes;
import ariel.az.devcode20.models.ModelsSendReportes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RvCommentAdapter extends RecyclerView.Adapter<RvCommentAdapter.ViewHolder> {
    private Activity activity;
    private String emailUser;
    private ArrayList<ModelsGetMessages> modelsGetMessages;
    private OnItemClickListener onItemClickListener;
    private Integer idPublication;
    private Router router;




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
        private ModelsGetMessages modelsGet;
        private Dialog myDialog;
        private Stack<String> commentaryStack = new Stack<>();


        public ViewHolder( View itemView) {
            super(itemView);
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
            if(modelsGetMessages.getLikepublication() > 0) {
                photoLike.setVisibility(View.VISIBLE);
                countLike.setText(modelsGetMessages.getLikepublication()+"");
            }

            if (modelsGetMessages.getComplemeints() > 0){
                photoLike.setColorFilter(Color.RED);
            }


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
                    editMessage(getAdapterPosition());
                    return true;
                case R.id.delete:
                    deleteMessage(getAdapterPosition());
                    return true;
                case R.id.denunciar:
                    denunciar(getAdapterPosition());
                    photoLike.setColorFilter(Color.RED);
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
                menu.setHeaderTitle(modelsGet.getMessageuser());
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

        private void editMessage(Integer id){
            //editar mensaje
            final EditText commentaryPublication;
            final TextView userCommentary;
            Button btnUpdateCommentary;
            myDialog = new Dialog(activity);
            myDialog.setContentView(R.layout.dialog_commentary_design);
            myDialog.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
            //obtener los campo
            commentaryPublication = myDialog.findViewById(R.id.commentaryPublication);
            userCommentary = myDialog.findViewById(R.id.userCommentary);
            btnUpdateCommentary = myDialog.findViewById(R.id.btnUpdateCommentary);
            userCommentary.setText("Está a punto de editar su comentario " + modelsGetMessages.get(id).getUser().getNameuser());
            commentaryPublication.setText(String.valueOf(modelsGetMessages.get(id).getMessageuser()));
            btnUpdateCommentary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentaryStack.push(commentaryPublication.getText().toString());
                    myDialog.cancel();
                }
            });
            myDialog.show();
        }


        private void deleteMessage(Integer id){
            final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(R.string.advertencia).setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Integer position = modelsGetMessages.get(getAdapterPosition()).getIdmessage();
                    deleteDatabase(position);
                    modelsGetMessages.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    builder.setCancelable(true);
                }
            });
            builder.show();
        }

        private void deleteDatabase(final Integer id){
            //enviar la informacion a la base de datos para la eliminacion de un comentario
            router = conexion.getApiService();
            Call<ModelsGetMessages> modelsGetMessagesCall = router.deleteCommentary(id);
            modelsGetMessagesCall.enqueue(new Callback<ModelsGetMessages>() {
                @Override
                public void onResponse(Call<ModelsGetMessages> call, Response<ModelsGetMessages> response) {
                    if (response.isSuccessful()){
                    }
                }

                @Override
                public void onFailure(Call<ModelsGetMessages> call, Throwable t) {

                }
            });
        }









        private void denunciar(Integer position){
            router = conexion.getApiService();
            SharedPreferences preferences = activity.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
            ModelsSendReportes modelsSendReportes = new ModelsSendReportes(modelsGetMessages.get(position).getIdmessage());

            Call<ModelsMensajes> modelsGetMessagesCall  = router.MODELS_MENSAJES_CALL(SaveDataUser.getToken(preferences),modelsSendReportes);
            modelsGetMessagesCall.enqueue(new Callback<ModelsMensajes>() {
                @Override
                public void onResponse(Call<ModelsMensajes> call, Response<ModelsMensajes> response) {
                    if (response.isSuccessful()){
                        String message = response.body().getMessage();
                        Toast.makeText(activity, "" + message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelsMensajes> call, Throwable t) {

                }
            });

        }


    }

    public interface OnItemClickListener{
        void OnItemClick(ModelsGetMessages modelsGetMessages, int position);
    }
}
