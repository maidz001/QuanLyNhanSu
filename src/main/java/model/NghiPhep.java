package model;

import java.math.BigDecimal;
import java.util.Date;

public class NghiPhep {
    private int nghiPhepId;
    private int nhanVienId;
    private String loaiPhep;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private BigDecimal soNgay;
    private String lyDo;
    private String trangThai;
    private Integer nguoiDuyet;
    private String hoTen;
    private String maNhanVien;
    private String tenNguoiDuyet;

    public NghiPhep() {}

    public int getNghiPhepId() { return nghiPhepId; }
    public void setNghiPhepId(int nghiPhepId) { this.nghiPhepId = nghiPhepId; }
    public int getNhanVienId() { return nhanVienId; }
    public void setNhanVienId(int nhanVienId) { this.nhanVienId = nhanVienId; }
    public String getLoaiPhep() { return loaiPhep; }
    public void setLoaiPhep(String loaiPhep) { this.loaiPhep = loaiPhep; }
    public Date getNgayBatDau() { return ngayBatDau; }
    public void setNgayBatDau(Date ngayBatDau) { this.ngayBatDau = ngayBatDau; }
    public Date getNgayKetThuc() { return ngayKetThuc; }
    public void setNgayKetThuc(Date ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }
    public BigDecimal getSoNgay() { return soNgay; }
    public void setSoNgay(BigDecimal soNgay) { this.soNgay = soNgay; }
    public String getLyDo() { return lyDo; }
    public void setLyDo(String lyDo) { this.lyDo = lyDo; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public Integer getNguoiDuyet() { return nguoiDuyet; }
    public void setNguoiDuyet(Integer nguoiDuyet) { this.nguoiDuyet = nguoiDuyet; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(String maNhanVien) { this.maNhanVien = maNhanVien; }
    public String getTenNguoiDuyet() { return tenNguoiDuyet; }
    public void setTenNguoiDuyet(String tenNguoiDuyet) { this.tenNguoiDuyet = tenNguoiDuyet; }
}




