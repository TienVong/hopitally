package adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tienvong.hopitally.ActivityChuyenKhoa;
import com.example.tienvong.hopitally.ActivityDanhSachBV;
import com.example.tienvong.hopitally.ActivityTop5;

/**
 * Created by DucThanh on 4/26/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private String fragment[] = {"Top 5","Danh Sách BV", "Chuyên Khoa"};
    public ViewPagerAdapter(FragmentManager supportFragmentManager, Context applicationContext){
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new ActivityTop5();
            case 1: return  new ActivityDanhSachBV();
            case 2: return new ActivityChuyenKhoa();
            default: return null;
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
