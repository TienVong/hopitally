package model;

/**
 * Created by DucThanh on 6/12/2017.
 */

public class DichVu {
    private String TenDV;
    private String Mota;

    public DichVu(String tenDV, String mota) {
        setTenDV(tenDV);
        setMota(mota);
    }

    public DichVu() {
    }


    public String getTenDV() {
        return TenDV;
    }

    public void setTenDV(String tenDV) {
        TenDV = tenDV;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }
}
