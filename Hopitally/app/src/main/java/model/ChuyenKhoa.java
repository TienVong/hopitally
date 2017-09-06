package model;

/**
 * Created by DucThanh on 6/26/2017.
 */

public class ChuyenKhoa {
    private int idkhoa;
    private String tenkhoa;
    private String mota;

    public ChuyenKhoa() {
    }

    public ChuyenKhoa(int idkhoa, String tenkhoa, String mota) {
        this.setIdkhoa(idkhoa);
        this.setTenkhoa(tenkhoa);
        this.setMota(mota);
    }


    public int getIdkhoa() {
        return idkhoa;
    }

    public void setIdkhoa(int idkhoa) {
        this.idkhoa = idkhoa;
    }

    public String getTenkhoa() {
        return tenkhoa;
    }

    public void setTenkhoa(String tenkhoa) {
        this.tenkhoa = tenkhoa;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
