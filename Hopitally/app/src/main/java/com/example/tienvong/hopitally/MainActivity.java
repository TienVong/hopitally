package com.example.tienvong.hopitally;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import adapter.AdapterRecyclerViewTrangChu;
import adapter.CustomAdapterViewPager;
import model.BenhVien;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerViewItem;
    AdapterRecyclerViewTrangChu adapterRecyclerViewTrangChu;
    ArrayList<Integer> dsLinkAnhITem = new ArrayList<>();
    ArrayList<String> dsTenItem = new ArrayList<>();
    ArrayList<BenhVien> arrayList = new ArrayList<>();
    TextView txtLogo;
    //    AdapterTrangChu adapterTrangChu;

    Timer timer;
    String url = "http://hospitally.pe.hu/all_hospital.php";
    Button btnSearch;
    Typeface type;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trang_chu);

        txtLogo = (TextView) findViewById(R.id.logo);

        type = Typeface.createFromAsset(getAssets(),
                "fonts/JamesFajardo.ttf");
        txtLogo.setTypeface(type);
        btnSearch = (Button) findViewById(R.id.trangchu_btnSearch);
        btnSearch.setTextAppearance(this, R.style.ButtonFontStyle);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ActivitySearchView.class);
                startActivity(i);
            }
        });


        dsLinkAnhITem.add(R.drawable.anh_item_khampha);
        dsTenItem.add("Khám phá");
        recyclerViewItem = (RecyclerView) findViewById(R.id.reycleViewItem);
        adapterRecyclerViewTrangChu = new AdapterRecyclerViewTrangChu(this, dsLinkAnhITem, dsTenItem);
        recyclerViewItem.setAdapter(adapterRecyclerViewTrangChu);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewItem.setLayoutManager(gridLayoutManager);
        adapterRecyclerViewTrangChu.setOnItemClickListener(new AdapterRecyclerViewTrangChu.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                if (position == 0) {
                    Intent i = new Intent(MainActivity.this, ActivityKhamPhaBenhVien.class);
                    startActivity(i);
                }
            }
        });
        //thuc hien lay du lieu benh vien tu web ve
        kiemTraMang();

    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void kiemTraMang() {

        // kiểm tra kết nối mạng


        //Giống như 1 cái bundle
        if (!isOnline()) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Lỗi kết nối");
            dialog.setMessage("Bạn vui lòng kiểm tra lại kết nối internet. Wifi hoặc 3G");
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                    startActivity(intent);

                }
            });
            dialog.show();

            return;
        }

    }
}
