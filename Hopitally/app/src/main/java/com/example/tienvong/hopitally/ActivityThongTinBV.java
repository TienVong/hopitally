package com.example.tienvong.hopitally;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import model.BenhVienTop5;

public class ActivityThongTinBV extends Fragment{

    TextView tvUser;
    TextView txtGioiThieu;
    ImageView imgBV;
    TextView txtNLV;
    TextView txtGLVSang;
    TextView txtGLVChieu;
    TextView txtDiaChi;
    TextView txtEmail;
    TextView txtWeb;
    TextView txtSDT;
    TextView txtDiem;
    ImageView imgDonGia;
    String linkAnh = "http://hospitally.pe.hu/anh/";
    private BenhVienTop5 benhVien;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.thongtinbenhvien, parent, false);
        benhVien = (BenhVienTop5) getArguments().getSerializable("data");


        txtGioiThieu = (TextView) view.findViewById(R.id.txtGioiThieu);
        txtGioiThieu.setText(benhVien.getGioithieu().toString());
        txtNLV = (TextView) view.findViewById(R.id.txtNgayLamViec);
        txtNLV.setText(benhVien.getNgaykhambenh().toString());
        txtGLVSang = (TextView) view.findViewById(R.id.txtGLVSang);
        txtGLVSang.setText("Sáng " + benhVien.getGiokhambenhsang().toString());
        txtGLVChieu = (TextView) view.findViewById(R.id.txtGLVChieu);
        txtGLVChieu.setText("Chiều: " + benhVien.getGiokhambenhchieu().toString());
        txtDiaChi = (TextView)view.findViewById(R.id.ttbv_txtDiaChi);
        txtDiaChi.setText(benhVien.getDiachi().toString());
        txtEmail = (TextView)view.findViewById(R.id.ttbv_txtEmail);
        txtEmail.setText(benhVien.getEmail().toString());
        txtWeb = (TextView)view.findViewById(R.id.ttbv_txtWeb);
        txtWeb.setText(benhVien.getWebsite().toString());
        txtSDT = (TextView)view.findViewById(R.id.ttbv_txtSDT);
        txtSDT.setText(benhVien.getSdt().toString());
        txtDiem = (TextView)view.findViewById(R.id.ttbv_diem);
        Double diem = benhVien.getDTB();
        if(diem==null){
            txtDiem.setText("Chưa được chấm điểm");
        } else{
            txtDiem.setText(""+diem);
        }
        String donGia = linkAnh + benhVien.getDongia();
        linkAnh = linkAnh + benhVien.getHinhanh();
        imgBV = (ImageView) view.findViewById(R.id.imgBV_detail);
        Picasso.with(getContext()).load(linkAnh).into(imgBV);
        imgDonGia = (ImageView) view.findViewById(R.id.ttbv_imgDonGia);
        Picasso.with(getContext()).load(donGia).into(imgDonGia);

        txtSDT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String phone_no= txtSDT.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+phone_no));
                startActivity(callIntent);
            }
        });
        return view;
    }
}

