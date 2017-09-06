package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tienvong.hopitally.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import model.DanhGia;

/**
 * Created by Tien Vong on 6/23/2017.
 */

public class AdapterBinhLuan extends RecyclerView.Adapter<AdapterBinhLuan.ViewHolder>{
    ArrayList<DanhGia> dsDG = new ArrayList<>();
    private Context mContext;
    // Define listener member variable
    private AdapterBinhLuan.OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(AdapterBinhLuan.OnItemClickListener listener) {
        this.listener = listener;
    }

    public AdapterBinhLuan(Context context, ArrayList<DanhGia> dsDG) {
        this.dsDG = dsDG;
        this.mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public AdapterBinhLuan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemView = inflater.inflate(R.layout.item, parent, false);
        // Return a new holder instance
        AdapterBinhLuan.ViewHolder viewHolder = new AdapterBinhLuan.ViewHolder(itemView);
        return viewHolder;

    }


    //Set lại giá trị cho các thành phần của 1 dòng item
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Lấy một đối tượng Bệnh Viện, gán tên theo từng position
        DanhGia dg = dsDG.get(position);
        //lấy thuộc tính là tên bệnh viện
//        String hoTen =dg.getHoTen();
        String nhanXet = dg.getNhanXet();
        //Khai báo textview cần xét lại giá trị
        TextView txtCmt = holder.txtCmt;
        TextView txtTen = holder.txtTen;
        txtTen.setText(dg.getTenHo().toString());
        txtCmt.setText(dg.getNhanXet().toString());
        double diem = (dg.getDCL()+dg.getDTD()+dg.getDHQDG())/3;
        String strDiem = String.format("%.1f", diem);
        holder.txtDiem.setText(strDiem);
        holder.txtTenKhoa.setText("Khoa: "+dg.getTenKhoa());
//        TextView ten = holder.txtTen;
////        ten.setText(hoTen);

        String linkAnh = "https://maxcdn.icons8.com/Share/icon/Business//businessman1600.png";
        ImageView anh = holder.img;

        //Dùng Picasso để load ảnh vô imgview(ảnh) với link trên web
        Picasso.with(mContext).load(linkAnh).into(anh);
    }



    @Override
    public int getItemCount() {
        return dsDG.size();
    }


    // Khai báo một item có những gì, ánh xạ id của item
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView img;
//        public TextView txtTen;
        public TextView txtCmt;
        public TextView txtTen;
        public TextView txtTenKhoa;
        public TextView txtDiem;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.img);
//            txtTen = (TextView) itemView.findViewById(R.id.dg_cmt);
            txtCmt = (TextView) itemView.findViewById(R.id.dg_cmt);
            txtTen = (TextView) itemView.findViewById(R.id.dg_txtTen);
            txtTenKhoa = (TextView) itemView.findViewById(R.id.dg_khoaKham);
            txtDiem = (TextView) itemView.findViewById(R.id.bl_diem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }
    }
}
