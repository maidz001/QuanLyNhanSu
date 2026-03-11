package service;

import dao.TaiKhoanDAO;
import model.TaiKhoan;
import java.util.List;

public class TaiKhoanService {
    private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {
        return taiKhoanDAO.dangNhap(tenDangNhap, matKhau);
    }

    public boolean dangKy(TaiKhoan tk) {
        if (taiKhoanDAO.kiemTraTenDangNhapTonTai(tk.getTenDangNhap())) return false;
        tk.setVaiTro("Nhan vien");
        tk.setTrangThai(1);
        return taiKhoanDAO.them(tk);
    }

    public boolean them(TaiKhoan tk) {
        if (taiKhoanDAO.kiemTraTenDangNhapTonTai(tk.getTenDangNhap())) return false;
        return taiKhoanDAO.them(tk);
    }
    public TaiKhoan layTheoTenDangNhap(String tenDangNhap) {
        return taiKhoanDAO.layTheoTenDangNhap(tenDangNhap);
    }
    public boolean sua(TaiKhoan tk) { return taiKhoanDAO.sua(tk); }
    public boolean xoa(int id) { return taiKhoanDAO.xoa(id); }
    public List<TaiKhoan> layTatCa() { return taiKhoanDAO.layTatCa(); }
    public TaiKhoan layTheoId(int id) { return taiKhoanDAO.layTheoId(id); }
}