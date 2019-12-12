package ariel.az.devcode20.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Stack;

import ariel.az.devcode20.Adaptadores.RvCommentAdapter;
import ariel.az.devcode20.R;
import ariel.az.devcode20.SharedPreferencesUser.SaveDataUser;
import ariel.az.devcode20.configurationAndRouters.Router;
import ariel.az.devcode20.configurationAndRouters.conexion;
import ariel.az.devcode20.models.ModelsCreateMessages;
import ariel.az.devcode20.models.ModelsGetMessages;
import ariel.az.devcode20.models.ModelsListMessages;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsPublicationsActivity extends AppCompatActivity {
    private TextView titledescription, detailsdescription;
    private Button btnRespond;
    private SharedPreferences preferences;
    private CircleImageView imgUser;
    private Router router;
    private EditText postRespond;
    private Integer idPublication;
    private ArrayList<ModelsGetMessages> modelsGetMessages;
    private RecyclerView rvComment;
    private RvCommentAdapter commentAdapter;
    private String roleUser, photoUser;
    private Dialog myDialog;
    private Stack<Integer> pila = new Stack<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_publications);
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        titledescription  = findViewById(R.id.title_publication);
        detailsdescription = findViewById(R.id.details_publications);
        btnRespond = findViewById(R.id.btnRespond);
        imgUser = findViewById(R.id.imageUser);
        postRespond = findViewById(R.id.postRespond);
        getData();
        router = conexion.getApiService();
        getMessagesPublications();

        btnRespond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageUser = postRespond.getText().toString().trim();
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
        //agregar el menu para editar el comentario
        getMenuInflater().inflate(R.menu.edit_publications,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.changeComment:
                showEdit();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getData() {
        //obtener los datos de la pantalla principal fragment iniciofragmento
        titledescription.setText(getIntent().getExtras().getString("titlePublication"));
        detailsdescription.setText(getIntent().getExtras().getString("detailsPublications"));
        idPublication = Integer.parseInt(getIntent().getExtras().getString("idPublication"));
        roleUser= SaveDataUser.getEmailUser(preferences);
        photoUser = SaveDataUser.getImgUser(preferences);
        Glide.with(DetailsPublicationsActivity.this).load(photoUser).into(imgUser);
    }


    private void getMessagesPublications(){
        //para obtener los mensajes de cada publicacion
        Call<ModelsListMessages> modelsListMessagesCall = router.modelsListMessagesCall(idPublication);
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
                            modelsGetMessages.like(1);
                            pila.push(modelsGetMessages.getIdmessage());
                            commentAdapter.notifyItemChanged(position);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }



    private void showEdit(){
        final EditText descriptionQuestions, titleQuestions;
        Button btnUpdateQuestions;
        myDialog = new Dialog(DetailsPublicationsActivity.this);
        myDialog.setContentView(R.layout.dialog_questions_design);
        myDialog.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);

        titleQuestions = myDialog.findViewById(R.id.titleQuestions);
        descriptionQuestions = myDialog.findViewById(R.id.descriptionQuestions);
        btnUpdateQuestions = myDialog.findViewById(R.id.btnUpdateQuestions);
        titleQuestions.setText(getIntent().getExtras().getString("titlePublication"));
        descriptionQuestions.setText(getIntent().getExtras().getString("detailsPublications"));
        btnUpdateQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
            }
        });
        myDialog.show();
    }



}
