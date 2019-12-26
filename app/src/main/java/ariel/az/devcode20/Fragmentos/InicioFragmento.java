package ariel.az.devcode20.Fragmentos;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import ariel.az.devcode20.Activities.AdminActivity;
import ariel.az.devcode20.Adaptadores.RecyclerViewAdapter;
import ariel.az.devcode20.SharedPreferencesUser.SaveDataUser;
import ariel.az.devcode20.configurationAndRouters.Router;
import ariel.az.devcode20.configurationAndRouters.conexion;
import ariel.az.devcode20.models.ListPublications;
import ariel.az.devcode20.models.ModelsPublicationsList;
import ariel.az.devcode20.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InicioFragmento extends Fragment {

    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<ModelsPublicationsList> modelsPublicationsLists;
    private FloatingActionButton floatingAdmin;
    private SharedPreferences sharedPreferences;
    public InicioFragmento() {
        // Required empty public constructor no borrar
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_iniciofragmento, container, false);
        floatingAdmin = view.findViewById(R.id.floatingAdmin);
        Router router  = conexion.getApiService();
        sharedPreferences = this.getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        Call<ListPublications> listCall= router.obtenerPublcaciones();
        // TODO: 12/26/2019 se realiza la consulta a la base de datos para obtener todas las publicaciones 
        listCall.enqueue(new Callback<ListPublications>() {
            @Override
            public void onResponse(Call<ListPublications> call, Response<ListPublications> response) {
                if (response.isSuccessful()){
                    // TODO: 12/26/2019 se aplica el recycletview para prsentarlo en la pantalla principal 
                    RecyclerView recyclerView = view.findViewById(R.id.recyclerHome);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    modelsPublicationsLists = response.body().getModelsPublicationsLists();
                    recyclerViewAdapter = new RecyclerViewAdapter(modelsPublicationsLists,getContext());
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerView.setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(Call<ListPublications> call, Throwable t) {

            }
        });

        
        if (SaveDataUser.getRoleUser(sharedPreferences).equals("admin")){
            // TODO: 12/26/2019 validar el usuario para permitir el acceso a la pantalla administrador 
            floatingAdmin.setVisibility(View.VISIBLE);
            floatingAdmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), AdminActivity.class));
                }
            });
        }



       return view;

    }


}
