package com.example.tienvong.hopitally;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import model.ChuyenKhoa;

public class ActivityVietBL extends AppCompatActivity {


    SeekBar seekBarDTD;
    SeekBar seekBarDDV;
    SeekBar seekBarHQCT;
    TextView txtDiemTong;
    TextView txtDTD;
    TextView txtDDV;
    TextView txtDHQCT;
    Spinner spinner;
    int diemDV;
    int diemCL;
    int diemHQ;
    int idKhoa;
    Float diemTB;
    FloatingActionButton fab;
    RequestParams params = new RequestParams();
    TextInputLayout inputBinhLuan;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<ChuyenKhoa> dsKhoa = new ArrayList<>();
    ArrayList<String> tenKhoa = new ArrayList<>();
    String url = "http://hospitally.pe.hu/all_khoa.php";
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viet_bl);

        fab = (FloatingActionButton) findViewById(R.id.vietBL_fab);
        inputBinhLuan = (TextInputLayout)findViewById(R.id.vietBL_input);
        txtDHQCT = (TextView) findViewById(R.id.vietBL_DHQCT);
        seekBarDTD = (SeekBar) findViewById(R.id.seekBarDTD);
        seekBarDDV = (SeekBar) findViewById(R.id.seekBarDDV);
        seekBarHQCT = (SeekBar) findViewById(R.id.vietNL_seekBarDHQCT);
        txtDDV = (TextView) findViewById(R.id.txtDDV);
        txtDTD = (TextView) findViewById(R.id.txtDTD);
        txtDiemTong = (TextView) findViewById(R.id.txtDiemTong);
        spinner = (Spinner) findViewById(R.id.vietBL_dsKhoa);

        //lấy UID
        SharedPreferences sharedPreferences = this.getSharedPreferences("KiemTraDangNhap",Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("UID","Error");


        seekBarDTD.setMax(10);
        seekBarDTD.setProgress(1);
        seekBarDDV.setMax(10);
        seekBarDDV.setProgress(1);
        seekBarHQCT.setProgress(1);
        seekBarHQCT.setMax(10);
        txtDDV.setText("1");
        txtDTD.setText("1");
        txtDHQCT.setText("1");
        diemCL = 1;
        diemDV = 1;
        diemHQ = 1;
        diemTB = (float) 1.0;
        seekBarDDV.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //txtDDV.setText(Integer.toString(progress));
                txtDDV.setText("" + progress);


                diemDV = Integer.parseInt(txtDDV.getText().toString());
                diemCL = Integer.parseInt(txtDTD.getText().toString());
                diemHQ = Integer.parseInt(txtDHQCT.getText().toString());


                diemTB = cong(diemDV, diemCL, diemHQ);
                String strDiem = String.format("%.1f", diemTB);
                txtDiemTong.setText(strDiem);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        seekBarDTD.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //txtDTD.setText(Integer.toString(progress));
                txtDTD.setText("" + progress);


                diemDV = Integer.parseInt(txtDDV.getText().toString());
                diemCL = Integer.parseInt(txtDTD.getText().toString());
                diemHQ = Integer.parseInt(txtDHQCT.getText().toString());


                diemTB = cong(diemDV, diemCL, diemHQ);
                String strDiem = String.format("%.1f", diemTB);
                txtDiemTong.setText(strDiem);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        seekBarHQCT.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                txtDHQCT.setText("" + progress);


                diemDV = Integer.parseInt(txtDDV.getText().toString());
                diemCL = Integer.parseInt(txtDTD.getText().toString());
                diemHQ = Integer.parseInt(txtDHQCT.getText().toString());


                diemTB = cong(diemDV, diemCL, diemHQ);
                String strDiem = String.format("%.1f", diemTB);
                txtDiemTong.setText(strDiem);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Lấy dữ liệu cho spinner
        kiemTraMang();

    }


    public float cong(int a, int b, int c) {
        float tb;
        tb = (float) (a + b + c) / 3;
        return tb;
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
            Type listType = new TypeToken<ArrayList<ChuyenKhoa>>() {
            }.getType();
            ArrayList<ChuyenKhoa> list = (ArrayList<ChuyenKhoa>) gson.fromJson(result, listType);
            dsKhoa = list;
            for (ChuyenKhoa khoa : dsKhoa) {
                tenKhoa.add(khoa.getTenkhoa());
            }
            arrayAdapter = new ArrayAdapter<String>(ActivityVietBL.this, android.R.layout.simple_spinner_item, tenKhoa);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            spinner.setAdapter(arrayAdapter);

            //set sự kiện khi chọn item trên spinner
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    idKhoa = dsKhoa.get(position).getIdkhoa();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //sự kiện nhấn nút gửi
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String bl = inputBinhLuan.getEditText().getText().toString();

                    params.put("action","Hi");
                    params.put("idbv",BinhLuanActivity.idbvHT);
                    params.put("nhanxet",bl);
                    params.put("dtd",diemDV);
                    params.put("dcl",diemCL);
                    params.put("dhqdg",diemHQ);
                    params.put("idkhoa",idKhoa);
                    params.put("uid",uid);
                    guiBinhLuan();
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
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void kiemTraMang() {

        // kiểm tra kết nối mạng
        if (!isOnline()) {
            return;
        }
        new HttpGetTask().execute(url);
    }

    public void guiBinhLuan() {

        // kiểm tra kết nối mạng
        if (!isOnline()) {
            Toast.makeText(this, "Chưa gửi được đánh giá! Lỗi kết nối!", Toast.LENGTH_SHORT).show();
            return;
        }
        postToHost(params);
    }

    public void postToHost(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://hospitally.pe.hu/add_comment.php", params, new AsyncHttpResponseHandler() {
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
                finish();
                Log.i("Success", "Header" + headers + statusCode);
            }

            @Override

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                statusCode - return HTTP status code
//                headers - return headers, if any
//                responseBody - the response body, if any
//                error - the underlying cause of the failure
                Toast.makeText(ActivityVietBL.this, "Lỗi! Không gửi được đánh giá của bạn!", Toast.LENGTH_SHORT).show();
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
}
