package service;

import dao.ChamCongDAO;
import model.ChamCong;
import java.util.List;

public class ChamCongService {
    private ChamCongDAO chamCongDAO = new ChamCongDAO();

    public List<ChamCong> layTatCa() { return chamCongDAO.layTatCa(); }
    public List<ChamCong> layTheoNhanVien(int nhanVienId) { return chamCongDAO.layTheoNhanVien(nhanVienId); }
    public List<ChamCong> layTheoThang(int thang, int nam) { return chamCongDAO.layTheoThang(thang, nam); }
    public ChamCong layTheoId(int id) { return chamCongDAO.layTheoId(id); }
    public boolean them(ChamCong cc) { return chamCongDAO.them(cc); }
    public boolean sua(ChamCong cc) { return chamCongDAO.sua(cc); }
    public boolean xoa(int id) { return chamCongDAO.xoa(id); }
    public int demNgayDiLam(int nhanVienId, int thang, int nam) { return chamCongDAO.demNgayDiLamTheoThang(nhanVienId, thang, nam); }
    public double tinhGioLamThem(int nhanVienId, int thang, int nam) { return chamCongDAO.tinhTongGioLamThemTheoThang(nhanVienId, thang, nam); }
}




