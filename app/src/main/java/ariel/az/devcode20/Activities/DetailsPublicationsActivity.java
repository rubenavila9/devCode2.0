package ariel.az.devcode20.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import ariel.az.devcode20.R;
import ariel.az.devcode20.Realm.SaveDataUser;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailsPublicationsActivity extends AppCompatActivity {
    private TextView titledescription, detailsdescription;
    private Button btnRespond;
    private SharedPreferences preferences;
    private CircleImageView imgUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        setContentView(R.layout.activity_details_publications);
        titledescription  = findViewById(R.id.title_publication);
        detailsdescription = findViewById(R.id.details_publications);
        btnRespond = findViewById(R.id.btnRespond);
        imgUser = findViewById(R.id.imageUser);
        getData();
    }
    private void getData() {
        titledescription.setText(getIntent().getExtras().getString("titlePublication"));
        detailsdescription.setText(getIntent().getExtras().getString("detailsPublications"));
        Glide.with(DetailsPublicationsActivity.this).load(SaveDataUser.getImgUser(preferences)).into(imgUser);
    }
}
