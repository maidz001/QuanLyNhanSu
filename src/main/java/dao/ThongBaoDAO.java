package dao;

import model.ThongBao;
import ConnDatabase.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThongBaoDAO {

    public List<ThongBao> layTheoNguoiNhan(int nguoiNhan) {
        List<ThongBao> ds = new ArrayList<>();
        String sql = "SELECT tb.*, ng.ho_ten as ten_nguoi_gui FROM thong_bao tb LEFT JOIN nhan_vien ng ON tb.nguoi_gui=ng.nhan_vien_id WHERE tb.nguoi_nhan=? ORDER BY tb.ngay_tao DESC";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nguoiNhan);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) ds.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ds;
    }
    public ThongBao layTheoId(int id) {

        ThongBao tb = null;

        String sql = """
        SELECT tb.*, nv.ho_ten AS ten_nguoi_gui
        FROM thong_bao tb
        LEFT JOIN nhan_vien nv 
            ON tb.nguoi_gui = nv.nhan_vien_id
        WHERE tb.thong_bao_id = ?
        """;

        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    tb = mapRow(rs);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tb;
    }

    public int demChuaDoc(int nguoiNhan) {
        String sql = "SELECT COUNT(*) FROM thong_bao WHERE nguoi_nhan=? AND da_doc=0";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nguoiNhan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public boolean them(ThongBao tb) {
        String sql = "INSERT INTO thong_bao (nguoi_gui,nguoi_nhan,tieu_de,noi_dung,loai,da_doc) VALUES (?,?,?,?,?,0)";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            if (tb.getNguoiGui() != null) ps.setInt(1, tb.getNguoiGui()); else ps.setNull(1, Types.INTEGER);
            if (tb.getNguoiNhan() != null) ps.setInt(2, tb.getNguoiNhan()); else ps.setNull(2, Types.INTEGER);
            ps.setString(3, tb.getTieuDe());
            ps.setString(4, tb.getNoiDung());
            ps.setString(5, tb.getLoai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean danhDauDaDoc(int thongBaoId) {
        String sql = "UPDATE thong_bao SET da_doc=1 WHERE thong_bao_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, thongBaoId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean docTatCa(int nguoiNhan) {
        String sql = "UPDATE thong_bao SET da_doc=1 WHERE nguoi_nhan=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nguoiNhan);
            return ps.executeUpdate() >= 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public void guiThongBaoChoNhanVien(Integer nguoiGui, int nguoiNhan, String tieuDe, String noiDung, String loai) {
        ThongBao tb = new ThongBao();
        tb.setNguoiGui(nguoiGui);
        tb.setNguoiNhan(nguoiNhan);
        tb.setTieuDe(tieuDe);
        tb.setNoiDung(noiDung);
        tb.setLoai(loai);
        them(tb);
    }

    private ThongBao mapRow(ResultSet rs) throws SQLException {
        ThongBao tb = new ThongBao();
        tb.setThongBaoId(rs.getInt("thong_bao_id"));
        tb.setNguoiGui((Integer) rs.getObject("nguoi_gui"));
        tb.setNguoiNhan((Integer) rs.getObject("nguoi_nhan"));
        tb.setTieuDe(rs.getString("tieu_de"));
        tb.setNoiDung(rs.getString("noi_dung"));
        tb.setLoai(rs.getString("loai"));
        tb.setDaDoc(rs.getInt("da_doc"));
        tb.setNgayTao(rs.getTimestamp("ngay_tao"));
        try { tb.setTenNguoiGui(rs.getString("ten_nguoi_gui")); } catch (SQLException ignored) {}
        return tb;
    }
}