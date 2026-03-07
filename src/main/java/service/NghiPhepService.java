package service;

import dao.NghiPhepDAO;
import dao.ThongBaoDAO;
import model.NghiPhep;
import java.util.List;

public class NghiPhepService {
    private NghiPhepDAO nghiPhepDAO = new NghiPhepDAO();
    private ThongBaoDAO thongBaoDAO = new ThongBaoDAO();

    public List<NghiPhep> layTatCa() { return nghiPhepDAO.layTatCa(); }
    public List<NghiPhep> layTheoNhanVien(int nhanVienId) { return nghiPhepDAO.layTheoNhanVien(nhanVienId); }
    public List<NghiPhep> layChoDuyet() { return nghiPhepDAO.layChoDuyet(); }
    public NghiPhep layTheoId(int id) { return nghiPhepDAO.layTheoId(id); }

    public boolean nopDon(NghiPhep np) {
        return nghiPhepDAO.them(np);
    }

    public boolean duyet(int id, int nguoiDuyet) {
        NghiPhep np = nghiPhepDAO.layTheoId(id);
        boolean kq = nghiPhepDAO.duyet(id, nguoiDuyet, "Da duyet");
        if (kq && np != null) {
            thongBaoDAO.guiThongBaoChoNhanVien(nguoiDuyet, np.getNhanVienId(), "Don nghi phep duoc duyet", "Don nghi phep tu ngay " + np.getNgayBatDau() + " den " + np.getNgayKetThuc() + " da duoc duyet.", "nghi_phep");
        }
        return kq;
    }

    public boolean tuChoi(int id, int nguoiDuyet) {
        NghiPhep np = nghiPhepDAO.layTheoId(id);
        boolean kq = nghiPhepDAO.duyet(id, nguoiDuyet, "Tu choi");
        if (kq && np != null) {
            thongBaoDAO.guiThongBaoChoNhanVien(nguoiDuyet, np.getNhanVienId(), "Don nghi phep bi tu choi", "Don nghi phep tu ngay " + np.getNgayBatDau() + " den " + np.getNgayKetThuc() + " da bi tu choi.", "nghi_phep");
        }
        return kq;
    }
    public boolean them(NghiPhep np){
        return nghiPhepDAO.them(np);
    }
    public boolean xoa(int id) { return nghiPhepDAO.xoa(id); }
}