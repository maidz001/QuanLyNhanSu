package service;

import dao.PhongBanDAO;
import model.PhongBan;
import java.util.List;

public class PhongBanService {
    private PhongBanDAO phongBanDAO = new PhongBanDAO();

    public List<PhongBan> layTatCa() { return phongBanDAO.layTatCa(); }
    public PhongBan layTheoId(int id) { return phongBanDAO.layTheoId(id); }
    public boolean them(PhongBan pb) { return phongBanDAO.them(pb); }
    public boolean sua(PhongBan pb) { return phongBanDAO.sua(pb); }
    public boolean xoa(int id) { return phongBanDAO.xoa(id); }
}