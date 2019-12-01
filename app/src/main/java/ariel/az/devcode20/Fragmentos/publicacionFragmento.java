package ariel.az.devcode20.Fragmentos;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import ariel.az.devcode20.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class publicacionFragmento extends Fragment {


    public publicacionFragmento() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publicacionfragmento, container, false);
        Button buttonpublicacion = view.findViewById(R.id.buttonpublication);
        buttonpublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"la publicacion ha sido subida",Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }

}
