package ariel.az.devcode20;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import ariel.az.devcode20.Adaptadores.ScalerListener;
import com.bumptech.glide.Glide;

public class Editarperfildelusuario extends AppCompatActivity {

    private ScaleGestureDetector scaleGestureDetector;
    private ImageView imgView;
    private float xHegiht = 0, yHight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_editarperfildelusuario);
        getSupportActionBar().show();
        imgView  = findViewById(R.id.imageView);
        Glide.with(Editarperfildelusuario.this).load(getIntent().getExtras().getString("img")).into(imgView);
        xHegiht = imgView.getScaleX();
        yHight = imgView.getScaleY();
        scaleGestureDetector = new ScaleGestureDetector(this,new ScalerListener(imgView));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        scaleGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
