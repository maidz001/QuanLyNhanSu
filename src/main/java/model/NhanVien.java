package model;

import java.util.Date;

public class NhanVien {
    private int nhanVienId;
    private String maNhanVien;
    private String hoTen;
    private String email;
    private String dienThoai;
    private Date ngaySinh;
    private String gioiTinh;
    private String soCmnd;
    private String diaChi;
    private int phongBanId;
    private int chucVuId;
    private Date ngayVaoLam;
    private String trangThai;
    private String anhDaiDien;
    private String tenPhongBan;
    private String tenChucVu;
    private String soTaiKhoan;
    private String nganHang;

    public NhanVien() {}

    public int getNhanVienId() { return nhanVienId; }
    public void setNhanVienId(int nhanVienId) { this.nhanVienId = nhanVienId; }
    public String getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(String maNhanVien) { this.maNhanVien = maNhanVien; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDienThoai() { return dienThoai; }
    public void setDienThoai(String dienThoai) { this.dienThoai = dienThoai; }
    public Date getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }
    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }
    public String getSoCmnd() { return soCmnd; }
    public void setSoCmnd(String soCmnd) { this.soCmnd = soCmnd; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public int getPhongBanId() { return phongBanId; }
    public void setPhongBanId(int phongBanId) { this.phongBanId = phongBanId; }
    public int getChucVuId() { return chucVuId; }
    public void setChucVuId(int chucVuId) { this.chucVuId = chucVuId; }
    public Date getNgayVaoLam() { return ngayVaoLam; }
    public void setNgayVaoLam(Date ngayVaoLam) { this.ngayVaoLam = ngayVaoLam; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getAnhDaiDien() { return anhDaiDien; }
    public void setAnhDaiDien(String anhDaiDien) { this.anhDaiDien = anhDaiDien; }
    public String getTenPhongBan() { return tenPhongBan; }
    public void setTenPhongBan(String tenPhongBan) { this.tenPhongBan = tenPhongBan; }
    public String getTenChucVu() { return tenChucVu; }
    public void setTenChucVu(String tenChucVu) { this.tenChucVu = tenChucVu; }
    public String getSoTaiKhoan() { return soTaiKhoan; }
    public void setSoTaiKhoan(String soTaiKhoan) { this.soTaiKhoan = soTaiKhoan; }
    public String getNganHang() { return nganHang; }
    public void setNganHang(String nganHang) { this.nganHang = nganHang; }
}