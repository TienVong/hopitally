package com.example.tienvong.hopitally;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

import adapter.AdapterSearch;
import model.BenhVien;

public class ActivitySearchView extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private SearchView searchView;
    RecyclerView recyclerView;
    AdapterSearch adapterSearch;
    ArrayList<BenhVien> arrayList = new ArrayList<>();
    String url = "http://hospitally.pe.hu/all_hospital.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        kiemTraMang();
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

            //Xu li khi da lay duoc du lieu
            @Override
            protected void onPostExecute(String result) {

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<BenhVien>>() {
                }.getType();
                ArrayList<BenhVien> list = (ArrayList<BenhVien>) gson.fromJson(result, listType);
                arrayList = list;

                adapterSearch = new AdapterSearch(ActivitySearchView.this, arrayList);
                recyclerView.setAdapter(adapterSearch);


                LinearLayoutManager layoutManager = new LinearLayoutManager(ActivitySearchView.this, LinearLayoutManager.VERTICAL, false);
                // Attach layout manager to the RecyclerView
                recyclerView.setLayoutManager(layoutManager);

                adapterSearch.setOnItemClickListener(new AdapterSearch.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        Intent intent = new Intent(ActivitySearchView.this, ReviewDetail.class);
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


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.search_view, menu);
            MenuItem itemSearch = menu.findItem(R.id.search_view);
            searchView = (SearchView) itemSearch.getActionView();
            //set OnQueryTextListener cho search view để thực hiện search theo text
            searchView.setOnQueryTextListener(this);

            return true;
        }

        //SearchView.OnQueryTextListener
        @Override
        public boolean onQueryTextSubmit(String query) {
            return true;
        }

        //SearchView.OnQueryTextListener
        @Override
        public boolean onQueryTextChange(String newText) {
             newText = newText.toLowerCase();
            ArrayList<BenhVien> newList = new ArrayList<>();

            for (BenhVien benhVien : arrayList) {
                String name = benhVien.getTenbv().toLowerCase();
                String dc = benhVien.getDiachi().toLowerCase();

                if (deAccent(name).contains(deAccent(newText)) || deAccent(dc).contains(deAccent(newText))) {
                    newList.add(benhVien);
                }
            }
            adapterSearch.setFilter(newList);
            return true;
        }


    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
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
                    startActivityForResult(intent, 0);

                }
            });
            dialog.show();

            return;
        }

        new HttpGetTask().execute(url);
    }
}
