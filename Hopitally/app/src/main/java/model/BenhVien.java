package model;

import java.io.Serializable;

/**
 * Created by DucThanh on 5/13/2017.
 */

public class BenhVien implements Serializable {
    private int idbv;
    private String giokhambenhsang;
    private String giokhambenhchieu;
    private String ngaykhambenh;
    private String dongia;
    private String gioithieu;
    private String tenbv;
    private String hinhanh;
    private String diachi;
    private String website;
    private String email;
    private String sdt;

    public BenhVien(int idbv, String giokhambenhsang, String giokhambenhchieu, String ngaykhambenh, String dongia, String gioithieu, String tenbv, String hinhanh, String diachi, String website, String email, String sdt) {
        this.setIdbv(idbv);
        this.setGiokhambenhsang(giokhambenhsang);
        this.setGiokhambenhchieu(giokhambenhchieu);
        this.setNgaykhambenh(ngaykhambenh);
        this.setDongia(dongia);
        this.setGioithieu(gioithieu);
        this.setTenbv(tenbv);
        this.setHinhanh(hinhanh);
        this.setDiachi(diachi);
        this.setWebsite(website);
        this.setEmail(email);
        this.setSdt(sdt);
    }

    public BenhVien() {
    }

    public int getIdbv() {
        return idbv;
    }

    public void setIdbv(int idbv) {
        this.idbv = idbv;
    }

    public String getGiokhambenhsang() {
        return giokhambenhsang;
    }

    public void setGiokhambenhsang(String giokhambenhsang) {
        this.giokhambenhsang = giokhambenhsang;
    }

    public String getGiokhambenhchieu() {
        return giokhambenhchieu;
    }

    public void setGiokhambenhchieu(String giokhambenhchieu) {
        this.giokhambenhchieu = giokhambenhchieu;
    }

    public String getNgaykhambenh() {
        return ngaykhambenh;
    }

    public void setNgaykhambenh(String ngaykhambenh) {
        this.ngaykhambenh = ngaykhambenh;
    }

    public String getDongia() {
        return dongia;
    }

    public void setDongia(String dongia) {
        this.dongia = dongia;
    }

    public String getGioithieu() {
        return gioithieu;
    }

    public void setGioithieu(String gioithieu) {
        this.gioithieu = gioithieu;
    }

    public String getTenbv() {
        return tenbv;
    }

    public void setTenbv(String tenbv) {
        this.tenbv = tenbv;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
