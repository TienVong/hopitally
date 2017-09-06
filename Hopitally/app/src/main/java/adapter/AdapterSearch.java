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

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.ViewHolder> {

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

    public AdapterSearch(Context context, ArrayList<BenhVien> dsBV) {
        this.dsBV = dsBV;
        this.mContext = context;
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
        View itemView = inflater.inflate(R.layout.adapter_search, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;

    }


    //Set lại giá trị cho các thành phần của 1 dòng item
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Lấy một đối tượng Bệnh Viện, gán tên theo từng position
        BenhVien bv = dsBV.get(position);
        //lấy thuộc tính là tên bệnh viện
        String tenBV =bv.getTenbv();
        //Khai báo textview cần xét lại giá trị
        TextView ten = holder.tenBV;
        ten.setText(tenBV);
        holder.DC.setText(bv.getDiachi().toString());

        String linkAnh = "http://hospitally.pe.hu/anh/"+bv.getHinhanh();
        ImageView anh = holder.img;

        //Dùng Picasso để load ảnh vô imgview(ảnh) với link trên web
        Picasso.with(mContext).load(linkAnh).into(anh);
    }

    @Override
    public int getItemCount() {
        return dsBV.size();
    }


    // Khai báo một item có những gì, ánh xạ id của item
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView img;
        public TextView tenBV;
        public TextView DC;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.img);
            tenBV = (TextView) itemView.findViewById(R.id.text);
            DC = (TextView) itemView.findViewById(R.id.textDC);
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

    public void setFilter (ArrayList<BenhVien> newList)
    {
        dsBV = new ArrayList<>();
        dsBV.addAll(newList);
        notifyDataSetChanged();
    }
}
