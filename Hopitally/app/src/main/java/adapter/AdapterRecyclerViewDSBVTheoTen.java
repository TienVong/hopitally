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

import model.BenhVien;

/**
 * Created by DucThanh on 6/19/2017.
 */

public class AdapterRecyclerViewDSBVTheoTen extends RecyclerView.Adapter<AdapterRecyclerViewDSBVTheoTen.ViewHolder> {

    ArrayList<BenhVien> dsBV = new ArrayList<>();
    private Context mContext;
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

    public AdapterRecyclerViewDSBVTheoTen(Context context, ArrayList<BenhVien> dsBV) {
        this.dsBV = dsBV;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemView = inflater.inflate(R.layout.adapter_danh_sach_bv, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String tenBV = dsBV.get(position).getTenbv();
        TextView ten = holder.tenBV;
        TextView dc = holder.diaChiBV;
        ImageView img = holder.img;
        ten.setText(tenBV);
        dc.setText(dsBV.get(position).getDiachi());
        String linkAnh = "http://hospitally.pe.hu/anh/" + dsBV.get(position).getHinhanh();
        Picasso.with(mContext).load(linkAnh).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return dsBV.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tenBV;
        public TextView diaChiBV;
        public ImageView img;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);


            tenBV = (TextView) itemView.findViewById(R.id.adapter_dsbv_txtTenBV);
            diaChiBV = (TextView) itemView.findViewById(R.id.adapter_dsbv_txtDiaChi);
            img = (ImageView) itemView.findViewById(R.id.adapter_dsbv_img);
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
