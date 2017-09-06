package com.example.tienvong.hopitally;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import adapter.AdapterRecyclerViewDSBVTheoTen;
import cz.msebera.android.httpclient.Header;
import model.BenhVien;

public class Activity_BV_Khoa extends AppCompatActivity {


    int idKhoa;
    String tenKhoa;
    TextView txtTenKhoa;
    ImageButton btnBack;
    TextView txtKhongBL;
    RequestParams params = new RequestParams();
    AdapterRecyclerViewDSBVTheoTen adapterRecyclerViewDSBVTheoTen;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__bv__khoa);
        Bundle bundle = getIntent().getExtras();
        idKhoa = bundle.getInt("idkhoa");
        tenKhoa = bundle.getString("tenkhoa");

        txtTenKhoa = (TextView) findViewById(R.id.bvKhoa_tenKhoa);
        btnBack = (ImageButton) findViewById(R.id.bvKhoa_btnBack);
        recyclerView = (RecyclerView) findViewById(R.id.bvkhoa_recyclerView);
        txtKhongBL = (TextView)findViewById(R.id.khoa_khongBL);

        txtTenKhoa.setText(tenKhoa);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        params.put("idkhoa", idKhoa);
        kiemTraMang();
    }

    public void postToHost(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://hospitally.pe.hu/get_bv_by_khoa.php", params, new AsyncHttpResponseHandler() {
            @Override

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // statusCode - the status code of the response
//            headers - return headers, if any
//            responseBody - the body of the HTTP response from the server
                String x = null; // for UTF-8 encoding
                try {
                    x = new String(responseBody, "UTF-8");


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                x = new String(responseBody);

                String mm = removeUTF8BOM(x);

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<BenhVien>>() {
                }.getType();
                final ArrayList<BenhVien> list = (ArrayList<BenhVien>) gson.fromJson(mm.toString(), listType);
                adapterRecyclerViewDSBVTheoTen = new AdapterRecyclerViewDSBVTheoTen(Activity_BV_Khoa.this, list);
                recyclerView.setAdapter(adapterRecyclerViewDSBVTheoTen);
                LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_BV_Khoa.this, LinearLayoutManager.VERTICAL, false);
                // Attach layout manager to the RecyclerView
                recyclerView.setLayoutManager(layoutManager);
                adapterRecyclerViewDSBVTheoTen.setOnItemClickListener(new AdapterRecyclerViewDSBVTheoTen.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        Intent intent = new Intent(Activity_BV_Khoa.this, ReviewDetail.class);
                        intent.putExtra("benhvien", list.get(position).getIdbv());
                        startActivity(intent);
                    }
                });
                if(list.size() == 0){
                    txtKhongBL.setVisibility(View.VISIBLE);
                }
                Log.i("Success","Header"+headers +statusCode);
        }

        @Override

        public void onFailure ( int statusCode, Header[] headers,byte[] responseBody, Throwable
        error){
//                statusCode - return HTTP status code
//                headers - return headers, if any
//                responseBody - the response body, if any
//                error - the underlying cause of the failure

            Log.i("upload", "error --> " + error);

        }
    });
}

    public static final String UTF8_BOM = "\uFEFF";

    private static String removeUTF8BOM(String s) {
        while (!s.startsWith("[") && s.length() > 0) {
            s = s.substring(1);
        }
        return s;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void kiemTraMang() {

        // kiểm tra kết nối mạng
        if (!isOnline()) {
            return;
        }
        postToHost(params);
    }
}
