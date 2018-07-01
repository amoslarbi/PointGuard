package morelife.pointguard;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
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

