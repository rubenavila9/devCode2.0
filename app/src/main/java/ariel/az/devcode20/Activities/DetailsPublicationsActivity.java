package ariel.az.devcode20.Activities;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ariel.az.devcode20.Adaptadores.RvCommentAdapter;
import ariel.az.devcode20.R;
import ariel.az.devcode20.SharedPreferencesUser.SaveDataUser;
import ariel.az.devcode20.configurationAndRouters.Router;
import ariel.az.devcode20.configurationAndRouters.conexion;
import ariel.az.devcode20.models.ModelsCreateLikes;
import ariel.az.devcode20.models.ModelsCreateMessages;
import ariel.az.devcode20.models.ModelsGetMessages;
import ariel.az.devcode20.models.ModelsListMessages;
import ariel.az.devcode20.models.ModelsMensajes;
import ariel.az.devcode20.models.ModelsPublications;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsPublicationsActivity extends AppCompatActivity {
    private TextView titledescription, detailsdescription , nivel;
    private Button btnRespond;
    private SharedPreferences preferences;
    private CircleImageView imgUser;
    private Router router;
    private EditText postRespond;
    private Integer idPublication;
    private ArrayList<ModelsGetMessages> modelsGetMessages;
    private RecyclerView rvComment;
    private RvCommentAdapter commentAdapter;
    private String roleUser, photoUser, emailUser;
    private Dialog myDialog;
    private LinearLayout linearLayoutSendData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_details_publications);

        getSupportActionBar().show();

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        titledescription  = findViewById(R.id.nameUser);
        detailsdescription = findViewById(R.id.details_publications);
        nivel = findViewById(R.id.level);
        btnRespond = findViewById(R.id.btnRespond);
        imgUser = findViewById(R.id.imageUser);
        postRespond = findViewById(R.id.postRespond);
        linearLayoutSendData = findViewById(R.id.linearLayoutSendData);
        getData();
        router = conexion.getApiService();
        getMessagesPublications();

        if (checkToken()){
            linearLayoutSendData.setVisibility(View.INVISIBLE);
        }

        btnRespond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageUser = postRespond.getText().toString().trim();
                if (messageUser.isEmpty()){
                    Toast.makeText(DetailsPublicationsActivity.this, "escribe algo", Toast.LENGTH_SHORT).show();
                }
                ModelsCreateMessages modelsCreateMessages = new ModelsCreateMessages(messageUser,idPublication);
                Call<ModelsCreateMessages> modelsCreateMessagesCall = router.createMessages(SaveDataUser.getToken(preferences),modelsCreateMessages);
                modelsCreateMessagesCall.enqueue(new Callback<ModelsCreateMessages>() {
                    @Override
                    public void onResponse(Call<ModelsCreateMessages> call, Response<ModelsCreateMessages> response) {
                        if (response.isSuccessful()){
                            postRespond.setText("");
                            getMessagesPublications();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelsCreateMessages> call, Throwable t) {

                    }
                });


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO: 12/26/2019 validar al usuario para poder dejarlo editar 
        if (emailUser.equals(SaveDataUser.getEmailUser(preferences))){
            getMenuInflater().inflate(R.menu.edit_publications,menu);
        }

        if (TextUtils.isEmpty(SaveDataUser.getToken(preferences))){
            getMenuInflater().inflate(R.menu.menuregister,menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.changeComment:
                showEdit();
                return true;
            case  R.id.session:
                intentLogin();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getData() {
        // TODO: 12/26/2019 obtener los datos de la pantalla principal desde el fragmento 
        titledescription.setText(getIntent().getExtras().getString("titlePublication"));
        detailsdescription.setText(getIntent().getExtras().getString("detailsPublications"));
        idPublication = Integer.parseInt(getIntent().getExtras().getString("idPublication"));
        emailUser = getIntent().getExtras().getString("emailUser");
        nivel.setText("nivel " +  String.valueOf(getIntent().getExtras().getInt("level")));
        roleUser= SaveDataUser.getEmailUser(preferences);
        photoUser = SaveDataUser.getImgUser(preferences);

        Glide.with(DetailsPublicationsActivity.this).load(photoUser).into(imgUser);
    }


    private void getMessagesPublications(){
        // TODO: 12/26/2019 obtener todos los mensajes de una publicacion expecifica 
        Call<ModelsListMessages> modelsListMessagesCall = router.obtenerMensajesPorId(idPublication);
        modelsListMessagesCall.enqueue(new Callback<ModelsListMessages>() {
            @Override
            public void onResponse(Call<ModelsListMessages> call, Response<ModelsListMessages> response) {
                if (response.isSuccessful()){
                    modelsGetMessages = response.body().getModelsGetMessages();
                    rvComment = findViewById(R.id.rvCommentary);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DetailsPublicationsActivity.this);
                    commentAdapter = new RvCommentAdapter(DetailsPublicationsActivity.this, roleUser, idPublication,modelsGetMessages, new RvCommentAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(ModelsGetMessages modelsGetMessages, int position) {
                            if (!checkToken()){
                                addLike(modelsGetMessages.getIdmessage());
                                commentAdapter.notifyItemChanged(position);
                            }else {
                                intentLogin();
                            }

                        }
                    });
                    rvComment.setLayoutManager(layoutManager);
                    rvComment.setAdapter(commentAdapter);
                    rvComment.setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(Call<ModelsListMessages> call, Throwable t) {

            }
        });
    }




    private void showEdit(){
        // TODO: 12/26/2019 el dialogo para poder editar las publicaciones 
        final EditText descriptionQuestions, titleQuestions;
        Button btnUpdateQuestions, btnCancel;
        myDialog = new Dialog(DetailsPublicationsActivity.this);
        myDialog.setContentView(R.layout.dialog_questions_design);
        myDialog.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        titleQuestions = myDialog.findViewById(R.id.titleQuestions);
        descriptionQuestions = myDialog.findViewById(R.id.descriptionQuestions);
        btnUpdateQuestions = myDialog.findViewById(R.id.btnUpdateQuestions);
        btnCancel = myDialog.findViewById(R.id.btnCancel);
        titleQuestions.setText(getIntent().getExtras().getString("titlePublication"));
        descriptionQuestions.setText(getIntent().getExtras().getString("detailsPublications"));
        btnUpdateQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFielsUpdatePublications(titleQuestions.getText().toString().trim(), descriptionQuestions.getText().toString().trim());


            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
            }
        });
        myDialog.show();
    }



    private void addLike(Integer idMessage){
        // TODO: 12/26/2019 agregar like a un comentario
        ModelsCreateLikes modelsCreateLikes = new ModelsCreateLikes(idMessage);
        Call<ModelsMensajes> modelsCreateLikesCall = router.routerEnviarLikes(SaveDataUser.getToken(preferences),modelsCreateLikes);
        modelsCreateLikesCall.enqueue(new Callback<ModelsMensajes>() {
            @Override
            public void onResponse(Call<ModelsMensajes> call, Response<ModelsMensajes> response) {
                if (response.isSuccessful()){
                    String message = response.body().getMessage();
                    Toast.makeText(DetailsPublicationsActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                    getMessagesPublications();

                }
            }

            @Override
            public void onFailure(Call<ModelsMensajes> call, Throwable t) {

            }
        });
    }




    private void checkFielsUpdatePublications(String titleQuestions, String descriptionQuestions){
        // TODO: 12/26/2019 validar el campo si es que se encuentre vacia 
        if (titleQuestions.isEmpty() || descriptionQuestions.isEmpty())
            Toast.makeText(this, "llenar campos", Toast.LENGTH_SHORT).show();
        else{
            ModelsPublications modelsPublications = new ModelsPublications(idPublication,titleQuestions,descriptionQuestions);
            Call<ModelsMensajes> mensajesCall = router.routerEditarPublicaciones(modelsPublications);
            
            mensajesCall.enqueue(new Callback<ModelsMensajes>() {
                @Override
                public void onResponse(Call<ModelsMensajes> call, Response<ModelsMensajes> response) {
                    if (response.isSuccessful()){
                        myDialog.cancel();
                        startActivity(new Intent(DetailsPublicationsActivity.this,Principal.class));
                    }
                }

                @Override
                public void onFailure(Call<ModelsMensajes> call, Throwable t) {

                }
            });
            
        }
    }


    private boolean checkToken(){
        if (TextUtils.isEmpty(SaveDataUser.getToken(preferences))){
            btnRespond.setTextColor(Color.parseColor("#A5A5A5"));
            return true;
        }
        return false;
    }

    private void intentLogin(){
        startActivity(new Intent(DetailsPublicationsActivity.this,LoginActivity.class));
    }


}
