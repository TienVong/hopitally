package com.example.tienvong.hopitally;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class SignupActivity extends AppCompatActivity {

    Spinner spinner_ngay;
    Spinner spinner_thang;
    Spinner spinner_nam;
    TextInputLayout inputEmail;
    TextInputLayout inputID;
    TextInputLayout inputMK;
    TextInputLayout inputTen;
    RadioGroup radioGT;
    RadioButton radioButtonNam;
    RadioButton radioButtonNu;
    int gt = 2;
    Button btnDK;
    Button btnHuy;
    Date date;
    RequestParams params = new RequestParams();
    String uid;
    String email;
    String mk;
    String ten;
    String ngay;
    String thang;
    String nam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        inputEmail = (TextInputLayout)findViewById(R.id.signup_email);
        inputID = (TextInputLayout)findViewById(R.id.signup_UID);
        inputMK = (TextInputLayout)findViewById(R.id.signup_MK);
        inputTen = (TextInputLayout)findViewById(R.id.signup_hoTen);

        radioGT = (RadioGroup)findViewById(R.id.sigup_radioGT);
        radioButtonNam = (RadioButton)findViewById(R.id.signup_nam);
        radioButtonNu = (RadioButton)findViewById(R.id.signup_nu);

        spinner_ngay = (Spinner)findViewById(R.id.sigup_pinner_ngay);
        spinner_thang = (Spinner)findViewById(R.id.sigup_pinner_thang);
        spinner_nam = (Spinner)findViewById(R.id.sigup_pinner_nam);

        btnDK = (Button) findViewById(R.id.signup_btnSignup);
        btnHuy = (Button) findViewById(R.id.signup_btnCancel);

        radioButtonNam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                gt = 1;
            }
        });

        radioButtonNu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                gt = 0;
            }
        });
        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = inputEmail.getEditText().getText().toString();
                uid = inputID.getEditText().getText().toString();
                mk = inputMK.getEditText().getText().toString();
                ten = inputTen.getEditText().getText().toString();
                ngay = spinner_ngay.getSelectedItem().toString();
                thang = spinner_thang.getSelectedItem().toString();
                nam = spinner_nam.getSelectedItem().toString();

                String ngSinh = ngay+ "-" +  thang + "-" + nam;

                SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("dd-MM-yyyy");
                try {
                     date = simpleDateFormat.parse(ngSinh);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(email.equals("") || uid.equals("") || mk.equals("") || ten.equals("") || gt == 2 || date==null)
                {
                    Toast.makeText(SignupActivity.this, "Vui lòng điền dầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }

                else{
                    params.put("action", "dangky");
                    params.put("email",email);
                    params.put("UID",uid);
                    params.put("uid",uid);
                    params.put("tenho",ten);
                    params.put("password",mk);
                    params.put("gioitinh",gt);
                    params.put("ngaysinh",date.getTime());
                    kiemTraMang();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        spinner_ngay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_thang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_nam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void postToHost(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://hospitally.pe.hu/user.php", params, new AsyncHttpResponseHandler() {
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
                String existEmail = "Regisred mail";
                String extUID = "Existing uid";
                if(mm.toLowerCase().contains(existEmail.toLowerCase())){
                    Toast.makeText(SignupActivity.this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mm.toLowerCase().contains(extUID.toLowerCase())){
                    Toast.makeText(SignupActivity.this, "ID đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    SharedPreferences sharedPreferences = SignupActivity.this.getSharedPreferences("KiemTraDangNhap",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("DN",true);
                    editor.putString("UID",uid);
                    editor.apply();
                    Intent i = new Intent(SignupActivity.this,ActivityVietBL.class);
                    startActivity(i);
                    finish();
                }
            }

            @Override

            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable
                    error) {
//                statusCode - return HTTP status code
//                headers - return headers, if any
//                responseBody - the response body, if any
//                error - the underlying cause of the failure
                Toast.makeText(SignupActivity.this, "Xảy ra lỗi! Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
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

    private void kiemTraMang() {
        if (!isOnline()) {
            Toast.makeText(this, "Không có kết nối Internet!", Toast.LENGTH_SHORT).show();
            return;
        }
        postToHost(params);
    }

}
