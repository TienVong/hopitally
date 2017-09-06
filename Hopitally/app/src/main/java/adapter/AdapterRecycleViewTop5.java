package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tienvong.hopitally.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import model.BenhVienTop5;
import model.DanhGia;

/**
 * Created by DucThanh on 6/19/2017.
 */

public class AdapterRecycleViewTop5 extends RecyclerView.Adapter<AdapterRecycleViewTop5.ViewHolder> {

    private Activity context;
    private int layout;
    private ArrayList<BenhVienTop5> list;
    BenhVienTop5 bv;
    DanhGia dg;

    // Define listener member variable
    private OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public AdapterRecycleViewTop5(Context context, ArrayList<BenhVienTop5> list) {
        this.context = (Activity) context;
        this.list = list;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemView = inflater.inflate(R.layout.adapter_recyclerview_top5, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        bv = list.get(position);
        holder.txtTenBV.setText(bv.getTenbv().toString());
        holder.txtDiaChiBV.setText(bv.getDiachi().toString());
        String linkAnh = "http://hospitally.pe.hu/anh/" + bv.getHinhanh();
        Picasso.with(context).load(linkAnh).into(holder.image);
        String diemTB = String.format("%.1f", bv.getDTB());
        String diemDV = String.format("%.1f", bv.getDtdtb());
        String diemCL = String.format("%.1f", bv.getDcltb());
        String diemHQ = String.format("%.1f", bv.getDhqtb());

        holder.txtDiem.setText(diemTB);
        holder.txtDiemDV.setText(diemDV);
        holder.txtDiemCL.setText(diemCL);
        holder.txtDiemHQ.setText(diemHQ);
        holder.txtSLDG.setText(""+ bv.getSodg());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a itemView

        TextView txtDiem;
        ImageView image;
        TextView txtTenBV;
        TextView txtDiaChiBV;
        TextView txtDiemDV;
        TextView txtDiemCL;
        TextView txtDiemHQ;
        TextView txtSLDG;

        // We also create a constructor that accepts the entire item itemView
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            Typeface type = Typeface.createFromAsset(itemView.getContext().getAssets(),
                    "fonts/NotoSansBengaliUI-Bold.ttf");


            image = (ImageView) itemView.findViewById(R.id.imgBVTop10);
            txtTenBV = (TextView) itemView.findViewById(R.id.txtTenBVTop10);
            txtDiaChiBV = (TextView) itemView.findViewById(R.id.txtDiaChiBVTop10);
            txtDiem = (TextView) itemView.findViewById(R.id.top5_txtDiem);
            txtDiemDV = (TextView) itemView.findViewById(R.id.top5_diemDV);
            txtDiemCL = (TextView) itemView.findViewById(R.id.top5_diemCL);
            txtDiemHQ = (TextView) itemView.findViewById(R.id.top5_diemHQ);
            txtSLDG = (TextView) itemView.findViewById(R.id.top5_soDG);
            txtTenBV.setTypeface(type);

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
