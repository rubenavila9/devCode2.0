package ariel.az.devcode20.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ariel.az.devcode20.Fragmentos.InicioFragmento;
import ariel.az.devcode20.Fragmentos.perfilFragmento;
import ariel.az.devcode20.Fragmentos.publicacionFragmento;
import ariel.az.devcode20.R;

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        BottomNavigationView bottomNav = findViewById(R.id.navegation);

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new InicioFragmento()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.home:
                            selectedFragment = new InicioFragmento();
                            break;

                        case R.id.publicacion:
                            selectedFragment = new publicacionFragmento();
                            break;
                        case R.id.profile:
                            selectedFragment = new perfilFragmento();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, selectedFragment).commit();
                    return true;
                }
            };
}