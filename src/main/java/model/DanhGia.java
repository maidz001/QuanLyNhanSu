package model;

import java.math.BigDecimal;
import java.util.Date;

public class DanhGia {
    private int danhGiaId;
    private int nhanVienId;
    private int thang;
    private int nam;
    private BigDecimal tongDiem;
    private String xepLoai;
    private String nhanXet;
    private int nguoiDanhGia;
    private Date ngayDanhGia;

    public DanhGia() {}

    public int getDanhGiaId() { return danhGiaId; }
    public void setDanhGiaId(int danhGiaId) { this.danhGiaId = danhGiaId; }
    public int getNhanVienId() { return nhanVienId; }
    public void setNhanVienId(int nhanVienId) { this.nhanVienId = nhanVienId; }
    public int getThang() { return thang; }
    public void setThang(int thang) { this.thang = thang; }
    public int getNam() { return nam; }
    public void setNam(int nam) { this.nam = nam; }
    public BigDecimal getTongDiem() { return tongDiem; }
    public void setTongDiem(BigDecimal tongDiem) { this.tongDiem = tongDiem; }
    public String getXepLoai() { return xepLoai; }
    public void setXepLoai(String xepLoai) { this.xepLoai = xepLoai; }
    public String getNhanXet() { return nhanXet; }
    public void setNhanXet(String nhanXet) { this.nhanXet = nhanXet; }
    public int getNguoiDanhGia() { return nguoiDanhGia; }
    public void setNguoiDanhGia(int nguoiDanhGia) { this.nguoiDanhGia = nguoiDanhGia; }
    public Date getNgayDanhGia() { return ngayDanhGia; }
    public void setNgayDanhGia(Date ngayDanhGia) { this.ngayDanhGia = ngayDanhGia; }
}