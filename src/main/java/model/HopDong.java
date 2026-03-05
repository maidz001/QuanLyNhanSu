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
    private String hoTen;
    private String maNhanVien;

    public HopDong() {}

    public int getHopDongId() { return hopDongId; }
    public void setHopDongId(int hopDongId) { this.hopDongId = hopDongId; }
    public String getSoHopDong() { return soHopDong; }
    public void setSoHopDong(String soHopDong) { this.soHopDong = soHopDong; }
    public int getNhanVienId() { return nhanVienId; }
    public void setNhanVienId(int nhanVienId) { this.nhanVienId = nhanVienId; }
    public String getLoaiHopDong() { return loaiHopDong; }
    public void setLoaiHopDong(String loaiHopDong) { this.loaiHopDong = loaiHopDong; }
    public Date getNgayBatDau() { return ngayBatDau; }
    public void setNgayBatDau(Date ngayBatDau) { this.ngayBatDau = ngayBatDau; }
    public Date getNgayKetThuc() { return ngayKetThuc; }
    public void setNgayKetThuc(Date ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }
    public BigDecimal getLuongCoBan() { return luongCoBan; }
    public void setLuongCoBan(BigDecimal luongCoBan) { this.luongCoBan = luongCoBan; }
    public BigDecimal getPhuCap() { return phuCap; }
    public void setPhuCap(BigDecimal phuCap) { this.phuCap = phuCap; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(String maNhanVien) { this.maNhanVien = maNhanVien; }
}