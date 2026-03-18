package service;

import dao.NhanVienDAO;
import dao.ThongBaoDAO;
import model.NhanVien;
import model.ThongBao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class ThongBaoService {
    private ThongBaoDAO thongBaoDAO = new ThongBaoDAO();
    private NhanVienDAO nhanVienDAO=new NhanVienDAO();

    public List<ThongBao> layTheoNguoiNhan(int nguoiNhan) { return thongBaoDAO.layTheoNguoiNhan(nguoiNhan); }
    public int demChuaDoc(int nguoiNhan) { return thongBaoDAO.demChuaDoc(nguoiNhan); }
    public boolean danhDauDaDoc(int id) { return thongBaoDAO.danhDauDaDoc(id); }
    public boolean docTatCa(int nguoiNhan) { return thongBaoDAO.docTatCa(nguoiNhan); }
    public boolean them(ThongBao tb) { return thongBaoDAO.them(tb); }

    public ThongBao layTheoId(int id) {
        return thongBaoDAO.layTheoId(id);
    }

    public boolean xoaTatCaThongBaoDaDocChoNhanVien(int id) {
        return thongBaoDAO.xoaTatCaThongBaoDaDocChoNhanVien(id);
    }

    public List<ThongBao> layThongBaoMoiHonId(int userId, int lastId) {
        return thongBaoDAO.layThongBaoMoiHonId(userId, lastId);
    }
    public void thongBaoTinhLuongChoNhanVien(int nguoiThucHien){
        List<NhanVien> n=nhanVienDAO.layTatCa();
        LocalDate now=LocalDate.now();
        for(NhanVien nv:n){
        thongBaoDAO.them(new ThongBao(0,nguoiThucHien,nv.getNhanVienId(),"Đã có bảng lương tháng này","Bảng lương tháng này của bạn đã được cập nhật, kiểm tra ngay nhé","Cập nhật bảng Lương",0, Date.valueOf(now)));


        }}
}