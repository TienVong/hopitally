package com.example.tienvong.hopitally;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import adapter.AdapterBinhLuan;
import cz.msebera.android.httpclient.Header;
import model.BenhVienTop5;
import model.DanhGia;

public class BinhLuanActivity extends Fragment {

    Button btn_binhLuan;
    BenhVienTop5 benhVien;
    TextInputLayout textInputLayout;
    EditText txtNhapBL;
    RecyclerView recyclerView;
    AdapterBinhLuan adapterBinhLuan;
    TextView txtKhongBL;
    public static int idbvHT;

    Boolean dangNhap;
    RequestParams params = new RequestParams();

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.activity_binh_luan, parent, false);
        benhVien = (BenhVienTop5) getArguments().getSerializable("data");
        idbvHT = benhVien.getIdbv();
        txtKhongBL = (TextView)view.findViewById(R.id.bl_khongBL);
        // Load cmt
        recyclerView = (RecyclerView)view.findViewById(R.id.dg_recycler);
        btn_binhLuan = (Button) view.findViewById(R.id.binhLuan_btnBL);
        btn_binhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ktraDangNhap())
                {
                    Intent i = new Intent(getActivity(),ActivityVietBL.class);
                    startActivity(i);
                }
                else{
                    Intent i = new Intent(getActivity(),LoginActivity.class);
                    startActivity(i);
                }
//                floatingActionButton.setVisibility(View.VISIBLE);
//                textInputLayout.setVisibility(View.VISIBLE);
//                btn_binhLuan.setVisibility(View.GONE);

            }
        });
        params.put("get_comment_byid","Hi");
        params.put("idbv",benhVien.getIdbv());
        kiemTraMang();
        return view;

    }

    public boolean ktraDangNhap() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("KiemTraDangNhap",Context.MODE_PRIVATE);
        if(sharedPreferences!=null){
            dangNhap = sharedPreferences.getBoolean("DN",false);
        }
        else {
            dangNhap = false;
        }
        return dangNhap;
    }

    public void postToHost(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://hospitally.pe.hu/get_comment.php", params, new AsyncHttpResponseHandler() {
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
                Type listType = new TypeToken<ArrayList<DanhGia>>() {
                }.getType();
                ArrayList<DanhGia> list = (ArrayList<DanhGia>) gson.fromJson(mm.toString(), listType);

                if (list.size()==0){
                    txtKhongBL.setVisibility(View.VISIBLE);
                }
                else {
                    adapterBinhLuan = new AdapterBinhLuan(getActivity(), list);
                    recyclerView.setAdapter(adapterBinhLuan);
                    txtKhongBL.setVisibility(View.GONE);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    // Attach layout manager to the RecyclerView
                    recyclerView.setLayoutManager(layoutManager);
                }

                Log.i("Success", "Header" + headers + statusCode);
            }

            @Override

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
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
                (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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

    @Override
    public void onResume()
    {
        super.onResume();
        kiemTraMang();
    }
}