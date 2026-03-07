package dao;

import model.ChucVu;
import ConnDatabase.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChucVuDAO {

    public List<ChucVu> layTatCa() {
        List<ChucVu> ds = new ArrayList<>();
        String sql = "SELECT * FROM chuc_vu ORDER BY cap_bac DESC";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) ds.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ds;
    }

    public ChucVu layTheoId(int id) {
        String sql = "SELECT * FROM chuc_vu WHERE chuc_vu_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean them(ChucVu cv) {
        String sql = "INSERT INTO chuc_vu (ma_chuc_vu,ten_chuc_vu,cap_bac,luong_co_ban_min,luong_co_ban_max,mo_ta,trang_thai) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cv.getMaChucVu());
            ps.setString(2, cv.getTenChucVu());
            ps.setInt(3, cv.getCapBac());
            ps.setBigDecimal(4, cv.getluongCoBan());
            ps.setString(6, cv.getMoTa());
            ps.setInt(7, cv.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean sua(ChucVu cv) {
        String sql = "UPDATE chuc_vu SET ma_chuc_vu=?,ten_chuc_vu=?,cap_bac=?,luong_co_ban_min=?,luong_co_ban_max=?,mo_ta=?,trang_thai=? WHERE chuc_vu_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cv.getMaChucVu());
            ps.setString(2, cv.getTenChucVu());
            ps.setInt(3, cv.getCapBac());
            ps.setBigDecimal(4, cv.getluongCoBan());
            ps.setString(5, cv.getMoTa());
            ps.setInt(6, cv.getTrangThai());
            ps.setInt(7, cv.getChucVuId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean xoa(int id) {
        String sql = "DELETE FROM chuc_vu WHERE chuc_vu_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private ChucVu mapRow(ResultSet rs) throws SQLException {
        ChucVu cv = new ChucVu();
        cv.setChucVuId(rs.getInt("chuc_vu_id"));
        cv.setMaChucVu(rs.getString("ma_chuc_vu"));
        cv.setTenChucVu(rs.getString("ten_chuc_vu"));
        cv.setCapBac(rs.getInt("cap_bac"));
        cv.setluongCoBan(rs.getBigDecimal("luong_co_ban"));

        cv.setMoTa(rs.getString("mo_ta"));
        cv.setTrangThai(rs.getInt("trang_thai"));
        return cv;
    }
}