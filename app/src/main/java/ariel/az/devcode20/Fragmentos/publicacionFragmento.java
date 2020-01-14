package ariel.az.devcode20.Fragmentos;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.tiper.MaterialSpinner;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import ariel.az.devcode20.Activities.RegisterActivity;
import ariel.az.devcode20.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class publicacionFragmento extends Fragment {

    private Spinner spinnerNivels;
    private ArrayList<String> nivels;
    private MaterialSpinner materialSpinner;
    public publicacionFragmento() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publicacionfragmento, container, false);
        Button buttonpublicacion = view.findViewById(R.id.buttonpublication);
        materialSpinner = view.findViewById(R.id.material_spinner);

        nivels = new ArrayList<>();
        nivels.add("ok");

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.nivel,android.R.layout.simple_list_item_1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materialSpinner.setAdapter(arrayAdapter);

        materialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(@NotNull MaterialSpinner materialSpinner, @Nullable View view, int i, long l) {
                switch (i){
                    case 0:
                        Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();
                        return;
                    case 1:
                        Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                        return;
                }
            }

            @Override
            public void onNothingSelected(@NotNull MaterialSpinner materialSpinner) {

            }
        });



        buttonpublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"La publicación ha sido subida",Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }

}
