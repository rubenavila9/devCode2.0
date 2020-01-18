package ariel.az.devcode20.Activities;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import ariel.az.devcode20.SharedPreferencesUser.SaveDataUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import ariel.az.devcode20.Fragmentos.InicioFragmento;
import ariel.az.devcode20.Fragmentos.perfilFragmento;
import ariel.az.devcode20.Fragmentos.publicacionFragmento;
import ariel.az.devcode20.R;

public class Principal extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        BottomNavigationView bottomNav = findViewById(R.id.navegation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        preferences = this.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
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
                            if (!TextUtils.isEmpty(SaveDataUser.getToken(preferences))){
                                selectedFragment = new publicacionFragmento();
                                break;
                            }
                            redirectHome();
                            break;
                        case R.id.profile:
                            if (!TextUtils.isEmpty(SaveDataUser.getToken(preferences))){
                                selectedFragment = new perfilFragmento();
                                break;
                            }
                            redirectHome();
                            break;
                    }
                    if (selectedFragment == null){
                        redirectHome();
                        return true;
                    }else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, selectedFragment).commit();
                        return true;
                    }
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (TextUtils.isEmpty(SaveDataUser.getToken(preferences))){
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menuregister,menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.session:
                startActivity(new Intent(Principal.this,LoginActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void redirectHome(){
        startActivity(new Intent(Principal.this, RegisterActivity.class));

    }
}