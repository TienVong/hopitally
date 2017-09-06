package com.example.tienvong.hopitally;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import adapter.AdapterBinhLuan;
import adapter.DetailViewPagerAdapter;
import cz.msebera.android.httpclient.Header;
import model.BenhVienTop5;
import model.DanhGia;

public class ReviewDetail extends AppCompatActivity {


    ViewPager viewPager;
    Button detail_btnBack;
    Button detail_btnGoToBL;
    TabLayout tabLayout;
    TextView txt_detail_TenBV;
    ArrayList<DanhGia> ds = new ArrayList<>();
    AdapterBinhLuan adapterBinhLuan;
    public BenhVienTop5 bv;
    int idbv;
    private DetailViewPagerAdapter adapterViewPager;
    RequestParams params = new RequestParams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_detail);

        //bắt bệnh viện từ Activity top 10 gửi qua
        idbv = getIntent().getExtras().getInt("benhvien");
        params.put("idbv",idbv);
        kiemTraMang();

        //set text font
        Typeface type = Typeface.createFromAsset(getAssets(),
                "fonts/NotoSansBengaliUI-Bold.ttf");
        //Nhận bệnh viện từ Activity top 10

    }


    public void postToHost(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://hospitally.pe.hu/hospital_detail.php", params, new AsyncHttpResponseHandler() {
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
                Type listType = new TypeToken<ArrayList<BenhVienTop5>>() {
                }.getType();
                ArrayList<BenhVienTop5> list = (ArrayList<BenhVienTop5>) gson.fromJson(mm.toString(), listType);
                if (list.size()!=0){
                    bv = list.get(0);
                    txt_detail_TenBV = (TextView) findViewById(R.id.txt_DetailTenBV);
                    txt_detail_TenBV.setText(bv.getTenbv().toString());
                    adapterViewPager = new DetailViewPagerAdapter(getSupportFragmentManager(),bv);
                    viewPager = (ViewPager) findViewById(R.id.detail_pager);
                    tabLayout = (TabLayout) findViewById(R.id.detail_tabLayout);
                    viewPager.setAdapter(adapterViewPager);
                    tabLayout.setupWithViewPager(viewPager);
                    //Truy cập fragment thong tin benh vien
                    ActivityThongTinBV fragment_thongtin = (ActivityThongTinBV) adapterViewPager.getRegisteredFragment(0);

                    Log.i("Success", "Header" + headers + statusCode);
                }
              else {
                    Toast.makeText(ReviewDetail.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                statusCode - return HTTP status code
//                headers - return headers, if any
//                responseBody - the response body, if any
//                error - the underlying cause of the failure

                Toast.makeText(ReviewDetail.this, "Lỗi! Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Lỗi! Vui lòng kiểm tra kết nối Wifi hoặc 3G!", Toast.LENGTH_LONG).show();
            return;
        }
        postToHost(params);
    }

    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                // Respond to the action bar's Up/Home button
                case android.R.id.home:
                    onBackPressed();
                    return true;
            }
            return super.onOptionsItemSelected(item);
    }

}

