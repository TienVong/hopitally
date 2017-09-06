package model;

/**
 * Created by DucThanh on 5/13/2017.
 */

public class DanhGia {
    private int IDDG;
    private int IDBV;
    private String NhanXet;
    private double ThoiGianDG;
    private double UpdateTime;
    private double DTD;
    private double DCL;
    private String UID;
    private int IDKhoa;
    private double DHQDG;
    private String TenHo;
    private int GioiTinh;
    private String Email;
    private String TenKhoa;

    public DanhGia() {
    }

    public DanhGia(int IDDG, int IDBV, String nhanXet, double thoiGianDG, double updateTime, double DTD, double DCL, String UID, int IDKhoa, double DHQDG, String tenHo, int gioiTinh, String email, String tenKhoa) {
        this.setIDDG(IDDG);
        this.setIDBV(IDBV);
        setNhanXet(nhanXet);
        setThoiGianDG(thoiGianDG);
        setUpdateTime(updateTime);
        this.setDTD(DTD);
        this.setDCL(DCL);
        this.setUID(UID);
        this.setIDKhoa(IDKhoa);
        this.setDHQDG(DHQDG);
        setTenHo(tenHo);
        setGioiTinh(gioiTinh);
        setEmail(email);
        setTenKhoa(tenKhoa);
    }


    public int getIDDG() {
        return IDDG;
    }

    public void setIDDG(int IDDG) {
        this.IDDG = IDDG;
    }

    public int getIDBV() {
        return IDBV;
    }

    public void setIDBV(int IDBV) {
        this.IDBV = IDBV;
    }

    public String getNhanXet() {
        return NhanXet;
    }

    public void setNhanXet(String nhanXet) {
        NhanXet = nhanXet;
    }

    public double getThoiGianDG() {
        return ThoiGianDG;
    }

    public void setThoiGianDG(double thoiGianDG) {
        ThoiGianDG = thoiGianDG;
    }

    public double getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(double updateTime) {
        UpdateTime = updateTime;
    }

    public double getDTD() {
        return DTD;
    }

    public void setDTD(double DTD) {
        this.DTD = DTD;
    }

    public double getDCL() {
        return DCL;
    }

    public void setDCL(double DCL) {
        this.DCL = DCL;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public int getIDKhoa() {
        return IDKhoa;
    }

    public void setIDKhoa(int IDKhoa) {
        this.IDKhoa = IDKhoa;
    }

    public double getDHQDG() {
        return DHQDG;
    }

    public void setDHQDG(double DHQDG) {
        this.DHQDG = DHQDG;
    }

    public String getTenHo() {
        return TenHo;
    }

    public void setTenHo(String tenHo) {
        TenHo = tenHo;
    }

    public int getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTenKhoa() {
        return TenKhoa;
    }

    public void setTenKhoa(String tenKhoa) {
        TenKhoa = tenKhoa;
    }
}
