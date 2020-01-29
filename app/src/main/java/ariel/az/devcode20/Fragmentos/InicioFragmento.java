package ariel.az.devcode20.Fragmentos;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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


public class InicioFragmento extends Fragment implements View.OnClickListener {

    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<ModelsPublicationsList> modelsPublicationsLists;
    private FloatingActionButton floatingAdmin;
    private SharedPreferences sharedPreferences;
    private Dialog dialog;
    private SwipeRefreshLayout swipe;
    private RadioButton radioButtonOne, radioButtonTwo,
            radioButtonThree, radioButtonFour, radioButtonFive , radioButtonSix, radioButtonSeven,
            radioButtonEight, radioButtonNine, radioButtonExtra;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_iniciofragmento, container, false);
        setHasOptionsMenu(true);
        floatingAdmin = view.findViewById(R.id.floatingAdmin);
        swipe = view.findViewById(R.id.swipe);
    getDataDB();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                getDataDB();
            }
        });

        if (SaveDataUser.getRoleUser(sharedPreferences).equals("admin")){
            // TODO: 12/26/2019 validar el usuario para permitir el acceso a la pantalla administrador
            floatingAdmin.setVisibility(View.VISIBLE);
            floatingAdmin.setOnClickListener(this);
        }



       return view;
    }


    private void getDataDB(){
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
                    swipe.setRefreshing(false);
                }
            }
            @Override
            public void onFailure(Call<ListPublications> call, Throwable t) {

            }
        });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (!TextUtils.isEmpty(SaveDataUser.getToken(sharedPreferences))){
            inflater.inflate(R.menu.menusearch,menu);
            super.onCreateOptionsMenu(menu, inflater);
        }
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                searchLevels();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void searchLevels() {
        // TODO: 1/22/2020 creacion del dialog para seleccionar nivel de busqueda
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_select_nivel);
        dialog.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        radioButtonOne = dialog.findViewById(R.id.radioButtonLevelOne);
        radioButtonTwo = dialog.findViewById(R.id.radioButtonLevelTwo);
        radioButtonThree = dialog.findViewById(R.id.radioButtonLevelThree);
        radioButtonFour = dialog.findViewById(R.id.radioButtonLevelFour);
        radioButtonFive = dialog.findViewById(R.id.radioButtonLevelFive);
        radioButtonSix = dialog.findViewById(R.id.radioButtonLevelSix);
        radioButtonSeven = dialog.findViewById(R.id.radioButtonLevelSeven);
        radioButtonEight = dialog.findViewById(R.id.radioButtonLevelEight);
        radioButtonNine = dialog.findViewById(R.id.radioButtonLevelNine);
        radioButtonExtra = dialog.findViewById(R.id.radioButtonLevelExtras);
        radioButtonOne.setOnClickListener(this);
        radioButtonTwo.setOnClickListener(this);
        radioButtonThree.setOnClickListener(this);
        radioButtonFour.setOnClickListener(this);
        radioButtonFive.setOnClickListener(this);
        radioButtonSix.setOnClickListener(this);
        radioButtonSeven.setOnClickListener(this);
        radioButtonEight.setOnClickListener(this);
        radioButtonNine.setOnClickListener(this);
        radioButtonExtra.setOnClickListener(this);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floatingAdmin:
                startActivity(new Intent(getActivity(),AdminActivity.class));
                break;
            case R.id.radioButtonLevelOne:
                action(1);
                break;
            case R.id.radioButtonLevelTwo:
                action(2);
                break;
            case R.id.radioButtonLevelThree:
                action(3);
                break;
            case R.id.radioButtonLevelFour:
                action(4);
                break;
            case R.id.radioButtonLevelFive:
                action(5);
                break;
            case R.id.radioButtonLevelSix:
                action(6);
                break;
            case R.id.radioButtonLevelSeven:
                action(7);
                break;
            case R.id.radioButtonLevelEight:
                action(8);
                break;
            case R.id.radioButtonLevelNine:
                action(9);
                break;
            case R.id.radioButtonLevelExtras:
                action(10);
                break;
        }
    }



    private void action(Integer idLevel){
        Call<ListPublications> getPublicationsId = router.obtenerPublicacionesId(idLevel);
        getPublicationsId.enqueue(new Callback<ListPublications>() {
            @Override
            public void onResponse(Call<ListPublications> call, Response<ListPublications> response) {
                dialog.dismiss();
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

