package ariel.az.devcode20.Fragmentos;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ariel.az.devcode20.models.ModelsPublications;
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
import com.tiper.MaterialSpinner;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InicioFragmento extends Fragment  {

    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<ModelsPublicationsList> modelsPublicationsLists;
    private FloatingActionButton floatingAdmin;
    private SharedPreferences sharedPreferences;
    private Dialog dialog;
    private MaterialSpinner material_spinnerLevel;
    RadioButton radioButtonOne,  radioButtonTwo;
    private Boolean flag = false;
    private View view;
    private RecyclerView recyclerView;
    private Router router;
    public InicioFragmento() {
        // Required empty public constructor no borrar
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        router  = conexion.getApiService();
        sharedPreferences = this.getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
    }



    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_iniciofragmento, container, false);
        floatingAdmin = view.findViewById(R.id.floatingAdmin);
        material_spinnerLevel = view.findViewById(R.id.material_spinnerLevel);
        if (TextUtils.isEmpty(SaveDataUser.getToken(sharedPreferences))){
            material_spinnerLevel.setVisibility(View.INVISIBLE);
            LinearLayout.LayoutParams params = new ActionMenuView.LayoutParams(0,0);
            material_spinnerLevel.setLayoutParams(params);
        }
        material_spinnerLevel = view.findViewById(R.id.material_spinnerLevel);
        material_spinnerLevel.setBackgroundColor(Color.parseColor("#f4f4f4"));

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.nivel,android.R.layout.simple_list_item_1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        material_spinnerLevel.setAdapter(arrayAdapter);

        material_spinnerLevel.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(@NotNull MaterialSpinner materialSpinner, @org.jetbrains.annotations.Nullable View view, int i, long l) {
                switch (i){
                    case 0:
                        action(1);
                        break;
                    case 1:
                        action(2);
                        break;
                    case 2:
                        action(3);
                        break;
                    case 3:
                        action(4);
                        break;
                    case 4:
                        action(5);
                        break;
                    case 5:
                        action(6);
                        break;
                    case 6:
                        action(7);
                        break;
                    case 7:
                        action(8);
                        break;
                    case 8:
                        action(9);
                        break;
                    case 9:
                        action(10);
                        break;
                }
            }

            @Override
            public void onNothingSelected(@NotNull MaterialSpinner materialSpinner) {

            }
        });




        Call<ListPublications> listCall= router.obtenerPublcaciones();
        // TODO: 12/26/2019 se realiza la consulta a la base de datos para obtener todas las publicaciones

        listCall.enqueue(new Callback<ListPublications>() {
            @Override
            public void onResponse(Call<ListPublications> call, Response<ListPublications> response) {
                if (response.isSuccessful()){
                    // TODO: 12/26/2019 se aplica el recycletview para prsentarlo en la pantalla principal
                    recyclerView = view.findViewById(R.id.recyclerHome);
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





    private void action(Integer idLevel){
        Call<ListPublications> getPublicationsId = router.obtenerPublicacionesId(idLevel);
        getPublicationsId.enqueue(new Callback<ListPublications>() {
            @Override
            public void onResponse(Call<ListPublications> call, Response<ListPublications> response) {
                modelsPublicationsLists = response.body().getModelsPublicationsLists();
                recyclerViewAdapter = new RecyclerViewAdapter(modelsPublicationsLists,getContext());
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<ListPublications> call, Throwable t) {

            }
        });

    }
}

