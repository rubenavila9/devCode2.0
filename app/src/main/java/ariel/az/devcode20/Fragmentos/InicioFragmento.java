package ariel.az.devcode20.Fragmentos;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ariel.az.devcode20.Activities.LoginActivity;
import ariel.az.devcode20.Adaptadores.RecyclerViewAdapter;
import ariel.az.devcode20.configurationAndRouters.Router;
import ariel.az.devcode20.configurationAndRouters.conexion;
import ariel.az.devcode20.models.ItemPrueba;
import ariel.az.devcode20.models.ListPublications;
import ariel.az.devcode20.models.ModelsPublications;
import ariel.az.devcode20.models.ModelsPublicationsList;
import ariel.az.devcode20.models.Publicacion;
import ariel.az.devcode20.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InicioFragmento extends Fragment {

    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<ModelsPublicationsList> modelsPublicationsLists;
    public InicioFragmento() {
        // Required empty public constructor no borrar
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_iniciofragmento, container, false);
        Router router  = conexion.getApiService();
       Call<ListPublications> listCall= router.LIST_PUBLICATIONS_CALL();
       //aqui hace la consulta una consulta para obtener las publicaciones
        listCall.enqueue(new Callback<ListPublications>() {
            @Override
            public void onResponse(Call<ListPublications> call, Response<ListPublications> response) {
                if (response.isSuccessful()){
                    RecyclerView recyclerView = view.findViewById(R.id.recyclerHome);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    //la respuesta obtenieda lo almacena para luego usarlos en el adaptor recyclerview
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



       return view;

    }


}
