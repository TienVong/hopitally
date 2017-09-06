package com.example.tienvong.hopitally;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import adapter.AdapterDichVu;
import cz.msebera.android.httpclient.Header;
import model.BenhVienTop5;
import model.DichVu;

/**
 * Created by DucThanh on 6/12/2017.
 */

public class DichVuActivity extends Fragment {
    BenhVienTop5 benhVien;
    RecyclerView recyclerView;
    ArrayList<DichVu> dsDV = new ArrayList<>();
    AdapterDichVu adapterDichVu;
    RequestParams params = new RequestParams();

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.dichvu, parent, false);
        benhVien = (BenhVienTop5) getArguments().getSerializable("data");
        // Load cmt
        recyclerView = (RecyclerView) view.findViewById(R.id.dichVu_recycleView);
        params.put("get_service", "Hi");
        params.put("idbv", benhVien.getIdbv());
        kiemTraMang();

        return view;

    }


    public void postToHost(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://hospitally.pe.hu/get_service.php", params, new AsyncHttpResponseHandler() {
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
                Type listType = new TypeToken<ArrayList<DichVu>>() {
                }.getType();
                ArrayList<DichVu> list = (ArrayList<DichVu>) gson.fromJson(mm.toString(), listType);
                dsDV = list;

                adapterDichVu = new AdapterDichVu(getActivity(), dsDV);
                recyclerView.setAdapter(adapterDichVu);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                // Optionally customize the position you want to default scroll to
                layoutManager.scrollToPosition(0);
                // Attach layout manager to the RecyclerView
                recyclerView.setLayoutManager(layoutManager);
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
}
