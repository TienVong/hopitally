package com.example.tienvong.hopitally;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import adapter.AdapterRecycleViewTop5;
import model.BenhVienTop5;

public class ActivityTop5 extends Fragment {


    RecyclerView recyclerView;
    AdapterRecycleViewTop5 adapterRecycleViewTop5;
    ArrayList<BenhVienTop5> arrayList = new ArrayList<>();
    String url = "http://hospitally.pe.hu/top5.php";

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.activity_top5, parent, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.tongHop_recyclerView);

        //thuc hien lay du lieu benh vien tu web ve5
        kiemTraMang();
        //Code in here

        return view;

    }


    private class HttpGetTask extends AsyncTask<String, Void, String> {


        private static final String TAG = "HttpGetTask";

        private static final String USER_NAME = "aporter";
//        private static final String URL = "http://hospitally.pe.hu/all_hospital.php";

        //Chay nen
        protected String doInBackground(String... params) {

            String data = "";
            InputStream in;
            //Declare a URL connection
            HttpURLConnection httpUrlConnection = null;

            try {
                URL url = new URL(params[0]);
                httpUrlConnection = (HttpURLConnection) url.openConnection();
                //open Input Stream to connect
                httpUrlConnection.connect();
                in = httpUrlConnection.getInputStream();
                //download and decode data
                data = readStream(in);

            } catch (MalformedURLException exception) {
                Log.e(TAG, "MalformedURLException");
            } catch (IOException exception) {
                Log.e(TAG, "IOException");
            } finally {
                if (null != httpUrlConnection)
                    httpUrlConnection.disconnect();
            }
            return data;
        }

        //Xu li khi da lay duoc du lieu\
        @Override
        protected void onPostExecute(String result) {

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<BenhVienTop5>>() {
            }.getType();
            ArrayList<BenhVienTop5> list = (ArrayList<BenhVienTop5>) gson.fromJson(result, listType);
            arrayList = list;

            adapterRecycleViewTop5 = new AdapterRecycleViewTop5(getActivity(), arrayList);
            recyclerView.setAdapter(adapterRecycleViewTop5);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            // Optionally customize the position you want to default scroll to
            layoutManager.scrollToPosition(0);
            // Attach layout manager to the RecyclerView
            recyclerView.setLayoutManager(layoutManager);
            adapterRecycleViewTop5.setOnItemClickListener(new AdapterRecycleViewTop5.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {

                    Intent intent = new Intent(getActivity(), ReviewDetail.class);
                    intent.putExtra("benhvien", arrayList.get(position).getIdbv());
                    startActivity(intent);
                }
            });
        }

        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuffer data = new StringBuffer("");
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    data.append(line);
                }
            } catch (IOException e) {
                Log.e(TAG, "IOException");
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return data.toString();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void kiemTraMang() {

        // kiểm tra kết nối mạng



        //Giống như 1 cái bundle
        if (!isOnline()) {
            return;
        }
        new HttpGetTask().execute(url);
    }

}
