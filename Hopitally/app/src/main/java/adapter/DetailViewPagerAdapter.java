package adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.tienvong.hopitally.ActivityThongTinBV;
import com.example.tienvong.hopitally.BinhLuanActivity;
import com.example.tienvong.hopitally.DichVuActivity;

import model.BenhVienTop5;

/**
 * Created by DucThanh on 6/5/2017.
 */

public class DetailViewPagerAdapter extends SmartFragmentStatePagerAdapter {
    private BenhVienTop5 bv;
    private String fragment[] = {"Thông tin", "Bình luận", "Dịch vụ"};

    public DetailViewPagerAdapter(FragmentManager supportFragmentManager, BenhVienTop5 data) {
        super(supportFragmentManager);
        this.bv = data;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",this.bv);
                ActivityThongTinBV activityThongTinBV = new ActivityThongTinBV();
                activityThongTinBV.setArguments(bundle);
                return activityThongTinBV;
            case 1:
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("data",this.bv);
                BinhLuanActivity binhLuanActivity = new BinhLuanActivity();
                binhLuanActivity.setArguments(bundle1);
                return binhLuanActivity;

            case 2:
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("data",this.bv);
                DichVuActivity dichVuActivity = new DichVuActivity();
                dichVuActivity.setArguments(bundle2);
                return dichVuActivity;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return fragment.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragment[position];
    }
}