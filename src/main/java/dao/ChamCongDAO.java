package dao;

import model.ChamCong;
import ConnDatabase.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChamCongDAO {

    public List<ChamCong> layTatCa() {
        List<ChamCong> ds = new ArrayList<>();
        String sql = "SELECT cc.*, nv.ho_ten, nv.ma_nhan_vien FROM cham_cong cc LEFT JOIN nhan_vien nv ON cc.nhan_vien_id=nv.nhan_vien_id ORDER BY cc.ngay_cham_cong DESC";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) ds.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ds;
    }

    public List<ChamCong> layTheoNhanVien(int nhanVienId) {
        List<ChamCong> ds = new ArrayList<>();
        String sql = "SELECT cc.*, nv.ho_ten, nv.ma_nhan_vien FROM cham_cong cc LEFT JOIN nhan_vien nv ON cc.nhan_vien_id=nv.nhan_vien_id WHERE cc.nhan_vien_id=? ORDER BY cc.ngay_cham_cong DESC";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nhanVienId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) ds.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ds;
    }

    public List<ChamCong> layTheoThang(int thang, int nam) {
        List<ChamCong> ds = new ArrayList<>();
        String sql = "SELECT cc.*, nv.ho_ten, nv.ma_nhan_vien FROM cham_cong cc LEFT JOIN nhan_vien nv ON cc.nhan_vien_id=nv.nhan_vien_id WHERE MONTH(cc.ngay_cham_cong)=? AND YEAR(cc.ngay_cham_cong)=? ORDER BY cc.nhan_vien_id, cc.ngay_cham_cong";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, thang); ps.setInt(2, nam);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) ds.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ds;
    }

    public ChamCong layTheoId(int id) {
        String sql = "SELECT cc.*, nv.ho_ten, nv.ma_nhan_vien FROM cham_cong cc LEFT JOIN nhan_vien nv ON cc.nhan_vien_id=nv.nhan_vien_id WHERE cc.cham_cong_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean them(ChamCong cc) {
        String sql = "INSERT INTO cham_cong (nhan_vien_id,ngay_cham_cong,gio_vao,gio_ra,so_gio_lam_viec,gio_lam_them,trang_thai,ghi_chu) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cc.getNhanVienId());
            ps.setDate(2, new java.sql.Date(cc.getNgayChamCong().getTime()));
            ps.setString(3, cc.getGioVao());
            ps.setString(4, cc.getGioRa());
            ps.setBigDecimal(5, cc.getSoGioLamViec());
            ps.setBigDecimal(6, cc.getGioLamThem());
            ps.setString(7, cc.getTrangThai());
            ps.setString(8, cc.getGhiChu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean sua(ChamCong cc) {
        String sql = "UPDATE cham_cong SET gio_vao=?,gio_ra=?,so_gio_lam_viec=?,gio_lam_them=?,trang_thai=?,ghi_chu=? WHERE cham_cong_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cc.getGioVao());
            ps.setString(2, cc.getGioRa());
            ps.setBigDecimal(3, cc.getSoGioLamViec());
            ps.setBigDecimal(4, cc.getGioLamThem());
            ps.setString(5, cc.getTrangThai());
            ps.setString(6, cc.getGhiChu());
            ps.setInt(7, cc.getChamCongId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean xoa(int id) {
        String sql = "DELETE FROM cham_cong WHERE cham_cong_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public int demNgayDiLamTheoThang(int nhanVienId, int thang, int nam) {
        String sql = "SELECT COUNT(*) FROM cham_cong WHERE nhan_vien_id=? AND MONTH(ngay_cham_cong)=? AND YEAR(ngay_cham_cong)=? AND trang_thai='Di lam'";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nhanVienId); ps.setInt(2, thang); ps.setInt(3, nam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public double tinhTongGioLamThemTheoThang(int nhanVienId, int thang, int nam) {
        String sql = "SELECT COALESCE(SUM(gio_lam_them),0) FROM cham_cong WHERE nhan_vien_id=? AND MONTH(ngay_cham_cong)=? AND YEAR(ngay_cham_cong)=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nhanVienId); ps.setInt(2, thang); ps.setInt(3, nam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    private ChamCong mapRow(ResultSet rs) throws SQLException {
        ChamCong cc = new ChamCong();
        cc.setChamCongId(rs.getInt("cham_cong_id"));
        cc.setNhanVienId(rs.getInt("nhan_vien_id"));
        cc.setNgayChamCong(rs.getDate("ngay_cham_cong"));
        cc.setGioVao(rs.getString("gio_vao"));
        cc.setGioRa(rs.getString("gio_ra"));
        cc.setSoGioLamViec(rs.getBigDecimal("so_gio_lam_viec"));
        cc.setGioLamThem(rs.getBigDecimal("gio_lam_them"));
        cc.setTrangThai(rs.getString("trang_thai"));
        cc.setGhiChu(rs.getString("ghi_chu"));
        try { cc.setHoTen(rs.getString("ho_ten")); } catch (SQLException ignored) {}
        try { cc.setMaNhanVien(rs.getString("ma_nhan_vien")); } catch (SQLException ignored) {}
        return cc;
    }
}