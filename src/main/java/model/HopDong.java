package model;

import java.math.BigDecimal;
import java.util.Date;

public class HopDong {
    private int hopDongId;
    private String soHopDong;
    private int nhanVienId;
    private String loaiHopDong;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private BigDecimal luongCoBan;
    private BigDecimal phuCap;
    private String trangThai;
    private String ghiChu;
    private Date ngayTao;


    public void setHopDongId(int hopDongId) {
        this.hopDongId = hopDongId;
    }

    public void setSoHopDong(String soHopDong) {
        this.soHopDong = soHopDong;
    }

    public void setNhanVienId(int nhanVienId) {
        this.nhanVienId = nhanVienId;
    }

    public void setLoaiHopDong(String loaiHopDong) {
        this.loaiHopDong = loaiHopDong;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public void setLuongCoBan(BigDecimal luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    public void setPhuCap(BigDecimal phuCap) {
        this.phuCap = phuCap;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getHopDongId() {
        return hopDongId;
    }

    public String getSoHopDong() {
        return soHopDong;
    }

    public int getNhanVienId() {
        return nhanVienId;
    }

    public String getLoaiHopDong() {
        return loaiHopDong;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public BigDecimal getLuongCoBan() {
        return luongCoBan;
    }

    public BigDecimal getPhuCap() {
        return phuCap;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public HopDong(int hopDongId, String soHopDong, int nhanVienId, String loaiHopDong, Date ngayBatDau, Date ngayKetThuc, BigDecimal luongCoBan, BigDecimal phuCap, String trangThai, String ghiChu, Date ngayTao) {
        this.hopDongId = hopDongId;
        this.soHopDong = soHopDong;
        this.nhanVienId = nhanVienId;
        this.loaiHopDong = loaiHopDong;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.luongCoBan = luongCoBan;
        this.phuCap = phuCap;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
        this.ngayTao = ngayTao;
    }

    public HopDong() {

    }
}