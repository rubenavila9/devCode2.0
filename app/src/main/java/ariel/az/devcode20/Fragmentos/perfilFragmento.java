package ariel.az.devcode20.Fragmentos;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ariel.az.devcode20.configurationAndRouters.Router;
import ariel.az.devcode20.configurationAndRouters.conexion;
import ariel.az.devcode20.models.ModelsPoints;
import com.bumptech.glide.Glide;

import ariel.az.devcode20.Activities.LoginActivity;
import ariel.az.devcode20.Editarperfildelusuario;
import ariel.az.devcode20.R;
import ariel.az.devcode20.SharedPreferencesUser.SaveDataUser;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class perfilFragmento extends Fragment {


    private TextView nameUser, emailUser, numPoints, likes, roleUser;
    private SharedPreferences preferences;
    private CircleImageView photoUser;
    private Router router;
    private Integer cantLikes= 25;

    public perfilFragmento() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfilfragmento, container, false);
        preferences = this.getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        nameUser = view.findViewById(R.id.titlePublications);
        photoUser = view.findViewById(R.id.photoUser);
        emailUser = view.findViewById(R.id.emailUser);
        numPoints = view.findViewById(R.id.numPoints);
        roleUser = view.findViewById(R.id.roleUser);
        likes = view.findViewById(R.id.likes);
        nameUser.setText(SaveDataUser.getNameUser(preferences));
        emailUser.setText(SaveDataUser.getEmailUser(preferences));
        router = conexion.getApiService();
        Call<ModelsPoints> modelsPointsCall = router.setPoints(SaveDataUser.getToken(preferences));
        modelsPointsCall.enqueue(new Callback<ModelsPoints>() {
            @Override
            public void onResponse(Call<ModelsPoints> call, Response<ModelsPoints> response) {
                if (response.isSuccessful()){
                    Integer myPoints = response.body().getPointsUsers().getCantpoint();
                    Integer myLikes = response.body().getPointsUsers().getPointlimit();
                    numPoints.setText(String.valueOf(myPoints));
                    likes.setText(String.valueOf(cantLikes - myLikes));
                }
            }

            @Override
            public void onFailure(Call<ModelsPoints> call, Throwable t) {

            }
        });
        Glide.with(this.getActivity()).load(SaveDataUser.getImgUser(preferences)).into(photoUser);
        roleUser.setText(SaveDataUser.getRoleUser(preferences));
        return  view;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.editarperfil,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out_login:
                cerrarsesion();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void cerrarsesion(){
        // TODO: 12/26/2019 eliminar los datos del usuario sharedpreferences
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        getActivity().finish();
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }
}

