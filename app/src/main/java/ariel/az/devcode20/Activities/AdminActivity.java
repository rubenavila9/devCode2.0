package ariel.az.devcode20.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;

import ariel.az.devcode20.Adaptadores.AdminAdapter;
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
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
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
                    adminAdapter = new AdminAdapter(AdminActivity.this, modelsLikesLists, new AdminAdapter.OnItemClick() {
                        @Override
                        public void ItemClick(ArrayListDenuncias modelsGetMessages, int position) {

                        }
                    });
                    recyclerView.setAdapter(adminAdapter);
                    recyclerView.setHasFixedSize(true);


                }  
            }
            @Override
            public void onFailure(Call<ListDenuncias> call, Throwable t) {

            }
        });
    }



}
