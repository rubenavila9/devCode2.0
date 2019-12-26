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

import com.bumptech.glide.Glide;

import ariel.az.devcode20.Editarperfildelusuario;
import ariel.az.devcode20.R;
import ariel.az.devcode20.SharedPreferencesUser.SaveDataUser;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class perfilFragmento extends Fragment {


    private TextView nameUser, emailUser;
    private SharedPreferences preferences;
    private CircleImageView photoUser;

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
        nameUser.setText(SaveDataUser.getEmailUser(preferences));
        emailUser.setText(SaveDataUser.getEmailUser(preferences));

        Glide.with(this.getActivity()).load(SaveDataUser.getImgUser(preferences)).into(photoUser);
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
            case R.id.volver:
                Intent intent = new Intent(getActivity(), Editarperfildelusuario.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

