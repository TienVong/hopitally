package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tienvong.hopitally.R;

import java.util.ArrayList;

import model.DichVu;

/**
 * Created by DucThanh on 6/19/2017.
 */

public class AdapterDichVu extends RecyclerView.Adapter<AdapterDichVu.ViewHolder> {

    ArrayList<DichVu> dsDV = new ArrayList<>();
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

    public AdapterDichVu(Context context, ArrayList<DichVu> dsDV) {
        this.dsDV = dsDV;
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
        View itemView = inflater.inflate(R.layout.adapter_danh_sach_dich_vu, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String tenBV = dsDV.get(position).getTenDV();
        String mieuTaDV = dsDV.get(position).getMota();
        TextView ten = holder.tenDV;
        TextView mt = holder.mieuTaDV;
        ten.setText(tenBV.toString());
        mt.setText(mieuTaDV.toString());
    }

    @Override
    public int getItemCount() {
        return dsDV.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tenDV;
        public TextView mieuTaDV;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);


            tenDV = (TextView) itemView.findViewById(R.id.adapter_dv_txtTenDV);
            mieuTaDV = (TextView) itemView.findViewById(R.id.adapter_dv_txtMieuTaDV);
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
