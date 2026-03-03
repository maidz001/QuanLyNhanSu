package model;
public class TaiKhoan {
    private int taiKhoanId;
    private int nhanVienId;
    private String tenDangNhap;
    private String matKhau;
    private String vaiTro;
    private int trangThai;
    private String hoTen;

    public TaiKhoan() {}

    public int getTaiKhoanId() { return taiKhoanId; }
    public void setTaiKhoanId(int taiKhoanId) { this.taiKhoanId = taiKhoanId; }
    public int getNhanVienId() { return nhanVienId; }
    public void setNhanVienId(int nhanVienId) { this.nhanVienId = nhanVienId; }
    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }
    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }
    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
}