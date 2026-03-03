package model;

import java.math.BigDecimal;
import java.util.Date;

public class BangLuong {
    private int bangLuongId;
    private int nhanVienId;
    private int thang;
    private int nam;
    private BigDecimal soNgayLamViec;
    private BigDecimal soNgayThucTe;
    private BigDecimal gioLamThem;
    private BigDecimal luongCoBan;
    private BigDecimal phuCap;
    private BigDecimal luongLamThem;
    private BigDecimal thuong;
    private BigDecimal tongThuNhap;
    private BigDecimal baoHiemXaHoi;
    private BigDecimal baoHiemYTe;
    private BigDecimal tamUng;
    private BigDecimal tongKhauTru;
    private BigDecimal luongThucLanh;
    private Date ngayThanhToan;
    private String trangThai;
    private String hoTen;
    private String maNhanVien;

    public BangLuong() {}

    public int getBangLuongId() { return bangLuongId; }
    public void setBangLuongId(int bangLuongId) { this.bangLuongId = bangLuongId; }
    public int getNhanVienId() { return nhanVienId; }
    public void setNhanVienId(int nhanVienId) { this.nhanVienId = nhanVienId; }
    public int getThang() { return thang; }
    public void setThang(int thang) { this.thang = thang; }
    public int getNam() { return nam; }
    public void setNam(int nam) { this.nam = nam; }
    public BigDecimal getSoNgayLamViec() { return soNgayLamViec; }
    public void setSoNgayLamViec(BigDecimal soNgayLamViec) { this.soNgayLamViec = soNgayLamViec; }
    public BigDecimal getSoNgayThucTe() { return soNgayThucTe; }
    public void setSoNgayThucTe(BigDecimal soNgayThucTe) { this.soNgayThucTe = soNgayThucTe; }
    public BigDecimal getGioLamThem() { return gioLamThem; }
    public void setGioLamThem(BigDecimal gioLamThem) { this.gioLamThem = gioLamThem; }
    public BigDecimal getLuongCoBan() { return luongCoBan; }
    public void setLuongCoBan(BigDecimal luongCoBan) { this.luongCoBan = luongCoBan; }
    public BigDecimal getPhuCap() { return phuCap; }
    public void setPhuCap(BigDecimal phuCap) { this.phuCap = phuCap; }
    public BigDecimal getLuongLamThem() { return luongLamThem; }
    public void setLuongLamThem(BigDecimal luongLamThem) { this.luongLamThem = luongLamThem; }
    public BigDecimal getThuong() { return thuong; }
    public void setThuong(BigDecimal thuong) { this.thuong = thuong; }
    public BigDecimal getTongThuNhap() { return tongThuNhap; }
    public void setTongThuNhap(BigDecimal tongThuNhap) { this.tongThuNhap = tongThuNhap; }
    public BigDecimal getBaoHiemXaHoi() { return baoHiemXaHoi; }
    public void setBaoHiemXaHoi(BigDecimal baoHiemXaHoi) { this.baoHiemXaHoi = baoHiemXaHoi; }
    public BigDecimal getBaoHiemYTe() { return baoHiemYTe; }
    public void setBaoHiemYTe(BigDecimal baoHiemYTe) { this.baoHiemYTe = baoHiemYTe; }
    public BigDecimal getTamUng() { return tamUng; }
    public void setTamUng(BigDecimal tamUng) { this.tamUng = tamUng; }
    public BigDecimal getTongKhauTru() { return tongKhauTru; }
    public void setTongKhauTru(BigDecimal tongKhauTru) { this.tongKhauTru = tongKhauTru; }
    public BigDecimal getLuongThucLanh() { return luongThucLanh; }
    public void setLuongThucLanh(BigDecimal luongThucLanh) { this.luongThucLanh = luongThucLanh; }
    public Date getNgayThanhToan() { return ngayThanhToan; }
    public void setNgayThanhToan(Date ngayThanhToan) { this.ngayThanhToan = ngayThanhToan; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(String maNhanVien) { this.maNhanVien = maNhanVien; }
}