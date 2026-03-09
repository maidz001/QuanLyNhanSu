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

    public ChamCong(int chamCongId, int nhanVienId, Date ngayChamCong, String gioVao, String gioRa, BigDecimal soGioLamViec, BigDecimal gioLamThem, String trangThai, String ghiChu) {
        this.chamCongId = chamCongId;
        this.nhanVienId = nhanVienId;
        this.ngayChamCong = ngayChamCong;
        this.gioVao = gioVao;
        this.gioRa = gioRa;
        this.soGioLamViec = soGioLamViec;
        this.gioLamThem = gioLamThem;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

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

}