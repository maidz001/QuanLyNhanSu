package model;

import java.math.BigDecimal;
import java.util.Date;

public class ChamCong {
    private int chamCongId;
    private int nhanVienId;
    private Date ngayChamCong;
    private String gioVao;
    private String gioRa;
    private BigDecimal soGioLamViec;
    private BigDecimal gioLamThem;
    private String trangThai;
    private String ghiChu;
    private String hoTen;
    private String maNhanVien;

    public ChamCong() {}

    public int getChamCongId() { return chamCongId; }
    public void setChamCongId(int chamCongId) { this.chamCongId = chamCongId; }
    public int getNhanVienId() { return nhanVienId; }
    public void setNhanVienId(int nhanVienId) { this.nhanVienId = nhanVienId; }
    public Date getNgayChamCong() { return ngayChamCong; }
    public void setNgayChamCong(Date ngayChamCong) { this.ngayChamCong = ngayChamCong; }
    public String getGioVao() { return gioVao; }
    public void setGioVao(String gioVao) { this.gioVao = gioVao; }
    public String getGioRa() { return gioRa; }
    public void setGioRa(String gioRa) { this.gioRa = gioRa; }
    public BigDecimal getSoGioLamViec() { return soGioLamViec; }
    public void setSoGioLamViec(BigDecimal soGioLamViec) { this.soGioLamViec = soGioLamViec; }
    public BigDecimal getGioLamThem() { return gioLamThem; }
    public void setGioLamThem(BigDecimal gioLamThem) { this.gioLamThem = gioLamThem; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(String maNhanVien) { this.maNhanVien = maNhanVien; }
}