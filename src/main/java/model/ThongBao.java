package model;

import java.util.Date;

public class ThongBao {
    private int thongBaoId;
    private Integer nguoiGui;
    private Integer nguoiNhan;
    private String tieuDe;
    private String noiDung;
    private String loai;
    private int daDoc;
    private Date ngayTao;

    public ThongBao() {}

    public ThongBao(int thongBaoId, Integer nguoiGui, Integer nguoiNhan, String tieuDe, String noiDung, String loai, int daDoc, Date ngayTao) {
        this.thongBaoId = thongBaoId;
        this.nguoiGui = nguoiGui;
        this.nguoiNhan = nguoiNhan;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.loai = loai;
        this.daDoc = daDoc;
        this.ngayTao = ngayTao;
    }

    public int getThongBaoId() { return thongBaoId; }
    public void setThongBaoId(int thongBaoId) { this.thongBaoId = thongBaoId; }
    public Integer getNguoiGui() { return nguoiGui; }
    public void setNguoiGui(Integer nguoiGui) { this.nguoiGui = nguoiGui; }
    public Integer getNguoiNhan() { return nguoiNhan; }
    public void setNguoiNhan(Integer nguoiNhan) { this.nguoiNhan = nguoiNhan; }
    public String getTieuDe() { return tieuDe; }
    public void setTieuDe(String tieuDe) { this.tieuDe = tieuDe; }
    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }
    public String getLoai() { return loai; }
    public void setLoai(String loai) { this.loai = loai; }
    public int getDaDoc() { return daDoc; }
    public void setDaDoc(int daDoc) { this.daDoc = daDoc; }
    public Date getNgayTao() { return ngayTao; }
    public void setNgayTao(Date ngayTao) { this.ngayTao = ngayTao; }
}