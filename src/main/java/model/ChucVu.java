package model;

import java.math.BigDecimal;

public class ChucVu {
    private int chucVuId;
    private String maChucVu;
    private String tenChucVu;
    private int capBac;
    private BigDecimal luongCoBan;
    private String moTa;
    private int trangThai;

    public ChucVu() {}

    public int getChucVuId() { return chucVuId; }
    public void setChucVuId(int chucVuId) { this.chucVuId = chucVuId; }
    public String getMaChucVu() { return maChucVu; }
    public void setMaChucVu(String maChucVu) { this.maChucVu = maChucVu; }
    public String getTenChucVu() { return tenChucVu; }
    public void setTenChucVu(String tenChucVu) { this.tenChucVu = tenChucVu; }
    public int getCapBac() { return capBac; }
    public void setCapBac(int capBac) { this.capBac = capBac; }
    public BigDecimal getluongCoBan() { return luongCoBan; }
    public void setluongCoBan(BigDecimal luongCoBan) { this.luongCoBan = luongCoBan; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }
}