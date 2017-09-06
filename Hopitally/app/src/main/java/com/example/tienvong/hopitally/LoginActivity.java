package com.example.tienvong.hopitally;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    TextView txt_login_logo;
    TextInputLayout inputTen;
    TextInputLayout inputPass;
    Button btnSigup;
    String uid;
    String pass;
    Button btnLogin;
    RequestParams params = new RequestParams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txt_login_logo = (TextView) findViewById(R.id.login_logo);
        Typeface type = Typeface.createFromAsset(getAssets(),
                "fonts/JamesFajardo.ttf");
        txt_login_logo.setTypeface(type);
        btnSigup = (Button) findViewById(R.id.login_btnSignUp);
        btnLogin = (Button) findViewById(R.id.login_btnLogin);
        inputTen = (TextInputLayout) findViewById(R.id.login_input_ten);
        inputPass = (TextInputLayout) findViewById(R.id.login_input_password);
        btnSigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid = inputTen.getEditText().getText().toString();
                pass = inputPass.getEditText().getText().toString();
                if (uid.equals("") || pass.equals("")) {
                    Toast.makeText(LoginActivity.this, "Bạn không được để trống ID hoặc mật khẩu!", Toast.LENGTH_SHORT).show();

                } else {
                    params.put("action", "dangnhap");
                    params.put("uid", "" + uid);
                    params.put("password", "" + pass);
                    kiemTraMang();
                }

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
                String kq = mm.substring(2,mm.length()-2);
                if (kq.equals("Login failed, wrong uid or password")) {
                    Toast.makeText(LoginActivity.this,"ID hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("KiemTraDangNhap",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("DN",true);
                    editor.putString("UID",uid);
                    editor.apply();
                    Intent i = new Intent(LoginActivity.this,ActivityVietBL.class);
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
                Toast.makeText(LoginActivity.this, "Xảy ra lỗi! Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
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


