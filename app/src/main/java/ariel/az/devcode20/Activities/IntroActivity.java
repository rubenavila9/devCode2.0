package ariel.az.devcode20.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import ariel.az.devcode20.Adaptadores.IntroViewPagerAdapter;
import ariel.az.devcode20.R;
import ariel.az.devcode20.ScreenItem;

public class IntroActivity extends AppCompatActivity {


    private ViewPager screenPager;

    IntroViewPagerAdapter introViewPagerAdapter ;
    TabLayout tabIndicator;
    Button btnNext;
    Button btnGetStarted;
    Animation btnAnim;
    int position =  0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // make the activity on full screen

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // is about

        if(restorePrefData()){

            Intent inicioFragmento = new Intent(IntroActivity.this,Principal.class);
            startActivity(inicioFragmento);
            finish();
        }

        setContentView(R.layout.activity_intro);
        //hide the action bar

        getSupportActionBar().hide();

        //ini views

        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);

        //fill list screen

        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Informacion Compartida", "Modulo de una biblioteca virtual. ",R.drawable.imga));
        mList.add(new ScreenItem("Publicaciones", "Una nueva forma de compartir tu conocimientos.",R.drawable.imgc));
        mList.add(new ScreenItem("Comenta lo que piensas", "Has conocer tu opinion a los demas.",R.drawable.imgb));
        //setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);


        //setup tabla layout with viewpager

        tabIndicator.setupWithViewPager(screenPager);

        //set button Listener

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                position = screenPager.getCurrentItem();
                if (position < mList.size()){
                position ++;
                screenPager.setCurrentItem(position);


                }
                if(position == mList.size()-1){//when we rech to the last screen
                    //TODO: show the GETSTARTED button and hide the indicator and the next button

                    loadLastScreen();

                }

            }
        });

        //tablelayout add cahnger listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition()== mList.size()-1){
                    loadLastScreen();
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        //get started button click listener
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open main activity


                Intent inicioFragmento = new Intent(IntroActivity.this, Principal.class);
                startActivity(inicioFragmento);

                savePrefsData();
                finish();
            }
        });

    }

    private boolean restorePrefData() {


        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnenBefore = pref.getBoolean("isIntroOpenend", false);
        return isIntroActivityOpnenBefore;
    }

    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref .edit();
        editor.putBoolean("isIntroOpenend",true);
        editor.commit();

    }

    //show the GETSTARTED button and hide the indicator and the next button
    private void loadLastScreen() {

        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);

        //TODO: add the animation getstarted button

        //setup animation

        btnGetStarted.setAnimation(btnAnim);


    }
}
