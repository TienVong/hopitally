package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tienvong.hopitally.R;

import java.util.ArrayList;

import static adapter.RoundedCornerImageView.getRoundedCornerImage;

/**
 * Created by DucThanh on 6/19/2017.
 */

public class AdapterRecyclerViewTrangChu extends RecyclerView.Adapter<AdapterRecyclerViewTrangChu.ViewHolder> {

    ArrayList<Integer> dsImageItem = new ArrayList<>();
    ArrayList<String> dsTenItem = new ArrayList<>();
    private Context mContext;
    RoundedCornerImageView roundedCornerImageView = new RoundedCornerImageView();
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

    public AdapterRecyclerViewTrangChu(Context context, ArrayList<Integer> dsAnh, ArrayList<String> dsTenItem) {
        this.dsImageItem = dsAnh;
        this.dsTenItem = dsTenItem;
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
        View itemView = inflater.inflate(R.layout.layout_adapter_recyclerview_trang_chu, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Integer linkAnh = dsImageItem.get(position);
        String tenItem = dsTenItem.get(position);
        ImageView img = holder.anhItem;
        TextView ten = holder.tenItem;
        Bitmap bitImg = BitmapFactory.decodeResource(getContext().getResources(),
                linkAnh);
        img.setImageBitmap(getRoundedCornerImage(bitImg));
        ten.setText(tenItem);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView anhItem;
        public TextView tenItem;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            Typeface type = Typeface.createFromAsset(itemView.getContext().getAssets(),
                    "fonts/NotoSansBengaliUI-Bold.ttf");
            anhItem = (ImageView) itemView.findViewById(R.id.adapter_recy_img);
            tenItem = (TextView) itemView.findViewById(R.id.adapter_txtTenItem);
            tenItem.setTypeface(type);
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

    //vẽ hình chữ nhật bo tròn cạnh(ImageView with rounded corners)
}
