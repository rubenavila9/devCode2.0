package ariel.az.devcode20.Fragmentos;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ariel.az.devcode20.Adaptadores.RecyclerViewAdapter;
import ariel.az.devcode20.Modelos.Publicacion;
import ariel.az.devcode20.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class iniciofragmento extends Fragment {
    RecyclerViewAdapter recyclerViewAdapter;
    List<Publicacion>publicacion;

    public iniciofragmento() {
        // Required empty public constructor no borrar
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_iniciofragmento, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerHome);
        publicacion.add(new Publicacion("Publicacion numero 1", "Deiby vera", R.drawable.profile));
        recyclerViewAdapter = new RecyclerViewAdapter(publicacion, getContext() );
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        return view;




    }

}
