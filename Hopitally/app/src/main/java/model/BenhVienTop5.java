package model;

import java.io.Serializable;

/**
 * Created by DucThanh on 6/27/2017.
 */

public class BenhVienTop5 implements Serializable {
    int idbv;
    String giokhambenhsang;
    String giokhambenhchieu;
    String ngaykhambenh;
    String dongia;
    String gioithieu;
    String tenbv;
    String hinhanh;
    String diachi;
    String website;
    String email;
    String sdt;
    Double kinhdo;
    Double vido;
    Double dcltb;
    Double dtdtb;
    Double dhqtb;
    int sodg;
    Double DTB;

    public BenhVienTop5(int idbv, String giokhambenhsang, String giokhambenhchieu, String ngaykhambenh, String dongia, String gioithieu, String tenbv, String hinhanh, String diachi, String website, String email, String sdt, Double kinhdo, Double vido, Double dcltb, Double dtdtb, Double dhqtb, int sodg, Double DTB) {
        this.idbv = idbv;
        this.giokhambenhsang = giokhambenhsang;
        this.giokhambenhchieu = giokhambenhchieu;
        this.ngaykhambenh = ngaykhambenh;
        this.dongia = dongia;
        this.gioithieu = gioithieu;
        this.tenbv = tenbv;
        this.hinhanh = hinhanh;
        this.diachi = diachi;
        this.website = website;
        this.email = email;
        this.sdt = sdt;
        this.kinhdo = kinhdo;
        this.vido = vido;
        this.dcltb = dcltb;
        this.dtdtb = dtdtb;
        this.dhqtb = dhqtb;
        this.sodg = sodg;
        this.DTB = DTB;
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

    public Double getKinhdo() {
        return kinhdo;
    }

    public void setKinhdo(Double kinhdo) {
        this.kinhdo = kinhdo;
    }

    public Double getVido() {
        return vido;
    }

    public void setVido(Double vido) {
        this.vido = vido;
    }

    public Double getDcltb() {
        return dcltb;
    }

    public void setDcltb(Double dcltb) {
        this.dcltb = dcltb;
    }

    public Double getDtdtb() {
        return dtdtb;
    }

    public void setDtdtb(Double dtdtb) {
        this.dtdtb = dtdtb;
    }

    public Double getDhqtb() {
        return dhqtb;
    }

    public void setDhqtb(Double dhqtb) {
        this.dhqtb = dhqtb;
    }

    public int getSodg() {
        return sodg;
    }

    public void setSodg(int sodg) {
        this.sodg = sodg;
    }

    public Double getDTB() {
        return DTB;
    }

    public void setDTB(Double DTB) {
        this.DTB = DTB;
    }
}
