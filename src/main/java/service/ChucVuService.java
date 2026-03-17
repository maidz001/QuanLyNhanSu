package service;

import dao.ChucVuDAO;
import model.ChucVu;
import java.util.List;

public class ChucVuService {
    private ChucVuDAO chucVuDAO = new ChucVuDAO();

    public List<ChucVu> layTatCa() { return chucVuDAO.layTatCa(); }
    public ChucVu layTheoId(int id) { return chucVuDAO.layTheoId(id); }
    public boolean them(ChucVu cv) { return chucVuDAO.them(cv); }
    public boolean sua(ChucVu cv) { return chucVuDAO.sua(cv); }
    public boolean xoa(int id) { return chucVuDAO.xoa(id); }
    public boolean kichHoat(int id){
        return chucVuDAO.kichHoat(id);
    }
}