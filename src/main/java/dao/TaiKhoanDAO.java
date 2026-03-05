package dao;

import model.TaiKhoan;
import ConnDatabase.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanDAO {

    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {

        String sql = "SELECT * FROM tai_khoan WHERE ten_dang_nhap=? AND mat_khau=? AND trang_thai=1";

        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tenDangNhap);
            ps.setString(2, matKhau);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) return mapRow(rs);

        } catch (SQLException e) { e.printStackTrace(); }

        return null;
    }


    public boolean kiemTraTenDangNhapTonTai(String tenDangNhap) {

        String sql = "SELECT COUNT(*) FROM tai_khoan WHERE ten_dang_nhap=?";

        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tenDangNhap);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getInt(1) > 0;

        } catch (SQLException e) { e.printStackTrace(); }

        return false;
    }


    public boolean them(TaiKhoan tk) {

        String sql = "INSERT INTO tai_khoan(nhan_vien_id,ten_dang_nhap,mat_khau,vai_tro,trang_thai) VALUES (?,?,?,?,?)";

        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
String vt="Nhân viên";
            ps.setInt(1, tk.getNhanVienId());
            ps.setString(2, tk.getTenDangNhap());
            ps.setString(3, tk.getMatKhau());
            if(tk.getVaiTro().equals("quanly")){
vt="Quản lý";
            }
            ps.setString(4,vt);
            ps.setInt(5, tk.getTrangThai());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }

        return false;
    }


    public boolean sua(TaiKhoan tk) {

        String sql = "UPDATE tai_khoan SET ten_dang_nhap=?,mat_khau=?,vai_tro=?,trang_thai=? WHERE tai_khoan_id=?";

        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tk.getTenDangNhap());
            ps.setString(2, tk.getMatKhau());
            ps.setString(3, tk.getVaiTro());
            ps.setInt(4, tk.getTrangThai());
            ps.setInt(5, tk.getTaiKhoanId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }

        return false;
    }


    public boolean xoa(int id) {

        String sql = "DELETE FROM tai_khoan WHERE tai_khoan_id=?";

        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) { e.printStackTrace(); }

        return false;
    }


    public List<TaiKhoan> layTatCa() {

        List<TaiKhoan> ds = new ArrayList<>();

        String sql = "SELECT * FROM tai_khoan";

        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) ds.add(mapRow(rs));

        } catch (SQLException e) { e.printStackTrace(); }

        return ds;
    }


    public TaiKhoan layTheoId(int id) {

        String sql = "SELECT * FROM tai_khoan WHERE tai_khoan_id=?";

        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) return mapRow(rs);

        } catch (SQLException e) { e.printStackTrace(); }

        return null;
    }


    private TaiKhoan mapRow(ResultSet rs) throws SQLException {

        TaiKhoan tk = new TaiKhoan();

        tk.setTaiKhoanId(rs.getInt("tai_khoan_id"));
        tk.setNhanVienId(rs.getInt("nhan_vien_id"));
        tk.setTenDangNhap(rs.getString("ten_dang_nhap"));
        tk.setMatKhau(rs.getString("mat_khau"));
        tk.setVaiTro(rs.getString("vai_tro"));
        tk.setTrangThai(rs.getInt("trang_thai"));
        tk.setNgayTao(rs.getTimestamp("ngay_tao"));

        return tk;
    }
}