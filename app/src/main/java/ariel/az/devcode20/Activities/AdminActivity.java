package ariel.az.devcode20.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import ariel.az.devcode20.Adaptadores.AdminAdapter;
import ariel.az.devcode20.Adaptadores.SwipeToDeleteCallback;
import ariel.az.devcode20.R;
import ariel.az.devcode20.configurationAndRouters.Router;
import ariel.az.devcode20.configurationAndRouters.conexion;
import ariel.az.devcode20.models.ArrayListDenuncias;
import ariel.az.devcode20.models.ListDenuncias;
import ariel.az.devcode20.models.ModelsMensajes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Router router;
    private AdminAdapter adminAdapter;
    private ArrayList<ArrayListDenuncias> modelsLikesLists;
    private Snackbar snackbar;
    private LinearLayoutCompat linearLayoutAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        linearLayoutAdmin = findViewById(R.id.linearLayoutAdmin);
        obtenerDenuncias();

    }
    
    
    private void obtenerDenuncias(){
        // TODO: 12/27/2019 obtener las denuncias de los usuarios
        router = conexion.getApiService();
        Call<ListDenuncias> listDenunciasCall = router.routerDenuncias();
        
        listDenunciasCall.enqueue(new Callback<ListDenuncias>() {
            @Override
            public void onResponse(Call<ListDenuncias> call, Response<ListDenuncias> response) {
                if (response.isSuccessful()){
                    modelsLikesLists = response.body().getArrayListDenuncias();
                    recyclerView = findViewById(R.id.recyclerAdmin);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AdminActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    adminAdapter = new AdminAdapter(AdminActivity.this, modelsLikesLists);
                    recyclerView.setAdapter(adminAdapter);
                    recyclerView.setHasFixedSize(true);
                    enableSwipeToDeleteAndUndo();

                }  
            }
            @Override
            public void onFailure(Call<ListDenuncias> call, Throwable t) {

            }
        });
    }




    private void enableSwipeToDeleteAndUndo(){
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                deleteDatabase(modelsLikesLists.get(position).getIdmessage());
                adminAdapter.removeItem(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    private void deleteDatabase(final Integer id){
        // TODO: 12/26/2019 eliminar un mensaje
        router = conexion.getApiService();
        Call<ModelsMensajes> modelsGetMessagesCall = router.eliminarComentarios(id);
        modelsGetMessagesCall.enqueue(new Callback<ModelsMensajes>() {
            @Override
            public void onResponse(Call<ModelsMensajes> call, Response<ModelsMensajes> response) {
                if (response.isSuccessful()){
                    String message = response.body().getMessage();
                    snackbar = Snackbar.make(linearLayoutAdmin,message + " el mensaje",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<ModelsMensajes> call, Throwable t) {

            }
        });
    }

}
