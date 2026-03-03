package service;

import dao.HopDongDAO;
import dao.ThongBaoDAO;
import model.HopDong;
import java.util.List;

public class HopDongService {
    private HopDongDAO hopDongDAO = new HopDongDAO();
    private ThongBaoDAO thongBaoDAO = new ThongBaoDAO();

    public List<HopDong> layTatCa() { return hopDongDAO.layTatCa(); }
    public HopDong layTheoId(int id) { return hopDongDAO.layTheoId(id); }
    public HopDong layHopDongHieuLuc(int nhanVienId) { return hopDongDAO.layHopDongHieuLuc(nhanVienId); }

    public boolean them(HopDong hd, Integer nguoiThucHien) {
        boolean kq = hopDongDAO.them(hd);
        if (kq) {
            thongBaoDAO.guiThongBaoChoNhanVien(nguoiThucHien, hd.getNhanVienId(), "Hop dong moi", "Hop dong " + hd.getSoHopDong() + " loai " + hd.getLoaiHopDong() + " da duoc tao cho ban.", "hop_dong");
        }
        return kq;
    }

    public boolean sua(HopDong hd, Integer nguoiThucHien) {
        boolean kq = hopDongDAO.sua(hd);
        if (kq) {
            thongBaoDAO.guiThongBaoChoNhanVien(nguoiThucHien, hd.getNhanVienId(), "Hop dong cap nhat", "Hop dong " + hd.getSoHopDong() + " cua ban da duoc cap nhat.", "hop_dong");
        }
        return kq;
    }

    public boolean xoa(int id) { return hopDongDAO.xoa(id); }
}