package model;

import java.util.Date;

public class PhongBan {
    private int phongBanId;
    private String maPhongBan;
    private String tenPhongBan;
    private int soLuong;
    private Integer truongPhongId;
    private String moTa;
    private int trangThai;
    private Date ngayTao;
    private Date ngayCapNhat;

    public void setPhongBanId(int phongBanId) {
        this.phongBanId = phongBanId;
    }

    public void setMaPhongBan(String maPhongBan) {
        this.maPhongBan = maPhongBan;
    }

    public void setTenPhongBan(String tenPhongBan) {
        this.tenPhongBan = tenPhongBan;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setTruongPhongId(Integer truongPhongId) {
        this.truongPhongId = truongPhongId;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public void setNgayCapNhat(Date ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }

    public int getPhongBanId() {
        return phongBanId;
    }

    public String getMaPhongBan() {
        return maPhongBan;
    }

    public String getTenPhongBan() {
        return tenPhongBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public Integer getTruongPhongId() {
        return truongPhongId;
    }

    public String getMoTa() {
        return moTa;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public Date getNgayCapNhat() {
        return ngayCapNhat;
    }

    public PhongBan(int phongBanId, String maPhongBan, String tenPhongBan, int soLuong, Integer truongPhongId, String moTa, int trangThai, Date ngayTao, Date ngayCapNhat) {

        this.phongBanId = phongBanId;
        this.maPhongBan = maPhongBan;
        this.tenPhongBan = tenPhongBan;
        this.soLuong = soLuong;
        this.truongPhongId = truongPhongId;
        this.moTa = moTa;
        this.trangThai = trangThai;
        this.ngayTao = ngayTao;
        this.ngayCapNhat = ngayCapNhat;
    }

    public PhongBan() {}


}



