package service;

import dao.NhanVienDAO;
import dao.ThongBaoDAO;
import model.NhanVien;
import java.util.List;

public class NhanVienService {
    private NhanVienDAO nhanVienDAO = new NhanVienDAO();
    private ThongBaoDAO thongBaoDAO = new ThongBaoDAO();

    public List<NhanVien> layTatCa() { return nhanVienDAO.layTatCa(); }
    public NhanVien layTheoId(int id) { return nhanVienDAO.layTheoId(id); }
    public List<NhanVien> timKiem(String tuKhoa) { return nhanVienDAO.timKiem(tuKhoa); }
    public int demTong() { return nhanVienDAO.demTongNhanVien(); }

    public boolean them(NhanVien nv, Integer nguoiThucHien) {
        boolean kq = nhanVienDAO.them(nv);
        if (kq) {
            NhanVien nvMoi = timTheoMa(nv.getMaNhanVien());
            if (nvMoi != null) {
                thongBaoDAO.guiThongBaoChoNhanVien(nguoiThucHien, nvMoi.getNhanVienId(), "Chao mung den cong ty", "Tai khoan cua ban da duoc tao. Chao mung " + nv.getHoTen() + " den voi cong ty!", "he_thong");
            }
        }
        return kq;
    }

    public boolean sua(NhanVien nv, Integer nguoiThucHien) {
        boolean kq = nhanVienDAO.sua(nv);
        if (kq) {
            thongBaoDAO.guiThongBaoChoNhanVien(nguoiThucHien, nv.getNhanVienId(), "Cap nhat thong tin", "Thong tin ho so cua ban vua duoc cap nhat boi quan tri vien.", "he_thong");
        }
        return kq;
    }

    public boolean xoa(int id) { return nhanVienDAO.xoa(id); }

    private NhanVien timTheoMa(String ma) {
        List<NhanVien> ds = nhanVienDAO.timKiem(ma);
        for (NhanVien nv : ds) {
            if (nv.getMaNhanVien().equals(ma)) return nv;
        }
        return null;
    }
}




