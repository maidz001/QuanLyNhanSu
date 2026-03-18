package service;

import dao.ThongBaoDAO;
import model.ThongBao;
import java.util.List;

public class ThongBaoService {
    private ThongBaoDAO thongBaoDAO = new ThongBaoDAO();

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
}