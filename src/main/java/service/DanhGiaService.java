package service;

import dao.DanhGiaDAO;
import dao.ThongBaoDAO;
import model.DanhGia;
import java.util.List;

public class DanhGiaService {
    private DanhGiaDAO danhGiaDAO = new DanhGiaDAO();
    private ThongBaoDAO thongBaoDAO = new ThongBaoDAO();

    public List<DanhGia> layTatCa() { return danhGiaDAO.layTatCa(); }
    public DanhGia layTheoId(int id) { return danhGiaDAO.layTheoId(id); }

    public boolean them(DanhGia dg) {
        boolean kq = danhGiaDAO.them(dg);
        if (kq) {
            thongBaoDAO.guiThongBaoChoNhanVien(dg.getNguoiDanhGia(), dg.getNhanVienId(), "Ket qua danh gia thang " + dg.getThang() + "/" + dg.getNam(), "Ban duoc xep loai: " + dg.getXepLoai() + ". Diem: " + dg.getTongDiem(), "danh_gia");
        }
        return kq;
    }

    public boolean sua(DanhGia dg) { return danhGiaDAO.sua(dg); }
    public boolean xoa(int id) { return danhGiaDAO.xoa(id); }
}