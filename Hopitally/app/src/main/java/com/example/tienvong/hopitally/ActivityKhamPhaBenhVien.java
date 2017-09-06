package com.example.tienvong.hopitally;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import adapter.ViewPagerAdapter;

public class ActivityKhamPhaBenhVien extends AppCompatActivity {

    ViewPager viewPager;
    ImageButton btnLogout;
    TabLayout tabLayout;
    boolean dangNhap = false;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kham_pha_benh_vien);
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), getApplicationContext()));
        tabLayout.setupWithViewPager(viewPager);
        btnLogout = (ImageButton) findViewById(R.id.logout);
        if(ktraDangNhap()){
            btnLogout.setVisibility(View.VISIBLE);
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("DN",false);
                    editor.apply();
                    btnLogout.setVisibility(View.GONE);
                    Toast.makeText(ActivityKhamPhaBenhVien.this, "Đã đăng xuất thành công!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public boolean ktraDangNhap() {
        sharedPreferences = this.getSharedPreferences("KiemTraDangNhap", Context.MODE_PRIVATE);
        if(sharedPreferences!=null){
            dangNhap = sharedPreferences.getBoolean("DN",false);
        }
        else {
            dangNhap = false;
        }
        return dangNhap;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(ktraDangNhap()){
            btnLogout.setVisibility(View.VISIBLE);
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("DN",false);
                    editor.apply();
                    btnLogout.setVisibility(View.GONE);
                    Toast.makeText(ActivityKhamPhaBenhVien.this, "Đã đăng xuất thành công!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
