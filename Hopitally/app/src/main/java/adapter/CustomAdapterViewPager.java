package adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tienvong.hopitally.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import model.BenhVien;

/**
 * Created by DucThanh on 6/17/2017.
 */


public class CustomAdapterViewPager extends PagerAdapter {

    //    private ArrayList img = new ArrayList();
    private ArrayList<BenhVien> dsbv = new ArrayList();
    private LayoutInflater inflater;
    private Context context;

    public CustomAdapterViewPager(Context context,ArrayList<BenhVien> dsbv) {
        this.context = context;
        this.dsbv = dsbv;
    }

    @Override
    public int getCount() {
        return dsbv.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.view_pager_layout, container, false);
        ImageView img1 = (ImageView) v.findViewById(R.id.view_pager_imageView);
        //TextView tv1 = (TextView) v.findViewById(R.id.textView);
        BenhVien bv = dsbv.get(position);
        String linkAnh = "http://hospitally.pe.hu/anh/" + bv.getHinhanh();
        Picasso.with(context).load(linkAnh).into(img1);
        //tv1.setText("Image: " + position);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }
}