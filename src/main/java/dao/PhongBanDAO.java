package dao;

import model.PhongBan;
import ConnDatabase.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhongBanDAO {

    public List<PhongBan> layTatCa() {
        List<PhongBan> ds = new ArrayList<>();
        String sql = "SELECT pb.*, nv.ho_ten as ten_truong_phong FROM phong_ban pb LEFT JOIN nhan_vien nv ON pb.truong_phong_id=nv.nhan_vien_id ORDER BY pb.phong_ban_id";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) ds.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ds;
    }

    public PhongBan layTheoId(int id) {
        String sql = "SELECT pb.*, nv.ho_ten as ten_truong_phong FROM phong_ban pb LEFT JOIN nhan_vien nv ON pb.truong_phong_id=nv.nhan_vien_id WHERE pb.phong_ban_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean them(PhongBan pb) {
        String sql = "INSERT INTO phong_ban (ma_phong_ban,ten_phong_ban,phong_ban_cha_id,truong_phong_id,mo_ta,trang_thai) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pb.getMaPhongBan());
            ps.setString(2, pb.getTenPhongBan());
            if (pb.getPhongBanChaId() != null) ps.setInt(3, pb.getPhongBanChaId()); else ps.setNull(3, Types.INTEGER);
            if (pb.getTruongPhongId() != null) ps.setInt(4, pb.getTruongPhongId()); else ps.setNull(4, Types.INTEGER);
            ps.setString(5, pb.getMoTa());
            ps.setInt(6, pb.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean sua(PhongBan pb) {
        String sql = "UPDATE phong_ban SET ma_phong_ban=?,ten_phong_ban=?,phong_ban_cha_id=?,truong_phong_id=?,mo_ta=?,trang_thai=? WHERE phong_ban_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pb.getMaPhongBan());
            ps.setString(2, pb.getTenPhongBan());
            if (pb.getPhongBanChaId() != null) ps.setInt(3, pb.getPhongBanChaId()); else ps.setNull(3, Types.INTEGER);
            if (pb.getTruongPhongId() != null) ps.setInt(4, pb.getTruongPhongId()); else ps.setNull(4, Types.INTEGER);
            ps.setString(5, pb.getMoTa());
            ps.setInt(6, pb.getTrangThai());
            ps.setInt(7, pb.getPhongBanId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean xoa(int id) {
        String sql = "DELETE FROM phong_ban WHERE phong_ban_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private PhongBan mapRow(ResultSet rs) throws SQLException {
        PhongBan pb = new PhongBan();
        pb.setPhongBanId(rs.getInt("phong_ban_id"));
        pb.setMaPhongBan(rs.getString("ma_phong_ban"));
        pb.setTenPhongBan(rs.getString("ten_phong_ban"));
        pb.setPhongBanChaId((Integer) rs.getObject("phong_ban_cha_id"));
        pb.setTruongPhongId((Integer) rs.getObject("truong_phong_id"));
        pb.setMoTa(rs.getString("mo_ta"));
        pb.setTrangThai(rs.getInt("trang_thai"));
        try { pb.setTenTruongPhong(rs.getString("ten_truong_phong")); } catch (SQLException ignored) {}
        return pb;
    }
}
