package com.example.tienvong.hopitally;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    Typeface type;
    TextView txtLogo;
    TextView txtSLG;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
        type = Typeface.createFromAsset(getAssets(),
                "fonts/JamesFajardo.ttf");

        txtLogo = (TextView) findViewById(R.id.textView4);
        txtSLG = (TextView) findViewById(R.id.slg);
        txtSLG.setTypeface(type);
        txtLogo.setTypeface(type);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
