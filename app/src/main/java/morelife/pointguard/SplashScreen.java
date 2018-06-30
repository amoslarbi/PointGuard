package morelife.pointguard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import morelife.pointguard.MainActivity;
import morelife.pointguard.R;


public class SplashScreen extends AppCompatActivity {

    Animation rotate;
    Animation translate;
    ImageView lolo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);


        Thread myThread = new Thread(){

            @Override
            public void run(){

                try {
//                    lolo.setAnimation(translate);
//                    lolo.setAnimation(rotate);

                    sleep(1500);
                    Intent startMainScreen = new Intent(getApplicationContext(),WelcomeActivity.class);
                    startActivity(startMainScreen);
                    finish();
                }
                catch (InterruptedException e){
                    e.printStackTrace();

                }

            }

        };
        myThread.start();

    }


}

