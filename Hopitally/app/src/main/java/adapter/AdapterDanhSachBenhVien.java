package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tienvong.hopitally.R;

import java.util.ArrayList;

import model.BenhVien;
import model.DanhGia;

public class AdapterDanhSachBenhVien extends ArrayAdapter<BenhVien> {
    private Activity context;
    private int layout;

    private ArrayList<BenhVien> list;
    BenhVien bv;
    DanhGia dg;
    Holder holder = new Holder();
    Typeface type = Typeface.createFromAsset(getContext().getAssets(),
            "fonts/NotoSansBengaliUI-Bold.ttf");
    //    private float diem;
    View row;

    public AdapterDanhSachBenhVien(Context context, int textViewResourceId, ArrayList<BenhVien> objects) {
        super(context, textViewResourceId, objects);
        this.context = (Activity) context;
        this.layout = textViewResourceId;
        this.list = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        row = convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layout, parent, false);

            holder.txtTenBV = (TextView) row.findViewById(R.id.adapter_dsbv_txtTenBV);

            row.setTag(holder);
        } else {
            holder = (Holder) row.getTag();
        }

        bv = list.get(position);

        holder.txtTenBV.setText(bv.getTenbv().toString());

        return row;
    }

    public static class Holder {
        TextView txtTenBV;

    }
}