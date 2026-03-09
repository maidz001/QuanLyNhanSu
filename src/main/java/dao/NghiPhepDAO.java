package dao;

import model.NghiPhep;
import ConnDatabase.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NghiPhepDAO {

    public List<NghiPhep> layTatCa() {
        List<NghiPhep> ds = new ArrayList<>();
        String sql = "SELECT np.*, nv.ho_ten, nv.ma_nhan_vien, nd.ho_ten as ten_nguoi_duyet " +
                "FROM nghi_phep np " +
                "LEFT JOIN nhan_vien nv ON np.nhan_vien_id = nv.nhan_vien_id " +
                "LEFT JOIN nhan_vien nd ON np.nguoi_duyet = nd.nhan_vien_id " +
                "WHERE np.ghi_chu IS NULL OR np.ghi_chu != 'Da xoa' " +
                "ORDER BY np.nghi_phep_id DESC";
        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) ds.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ds;
    }

    public BigDecimal demSoNgayNghiTrongNam(int nhanVienId) {

        String sql = "SELECT SUM(so_ngay) " +
                "FROM nghi_phep " +
                "WHERE nhan_vien_id=? " +
                "AND trang_thai='Da duyet' " +
                "AND YEAR(ngay_bat_dau)=YEAR(CURDATE())";

        BigDecimal tong = BigDecimal.ZERO;

        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nhanVienId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tong = rs.getBigDecimal(1);
                if (tong == null) {
                    tong = BigDecimal.ZERO;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tong;
    }
    public BigDecimal demSoNgayNghiTrongThang(int nhanVienId) {

        String sql = "SELECT SUM(so_ngay) " +
                "FROM nghi_phep " +
                "WHERE nhan_vien_id=? " +
                "AND trang_thai='Da duyet' " +
                "AND MONTH(ngay_bat_dau)=MONTH(CURDATE()) " +
                "AND YEAR(ngay_bat_dau)=YEAR(CURDATE())";

        BigDecimal tong = BigDecimal.ZERO;

        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nhanVienId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tong = rs.getBigDecimal(1);
                if (tong == null) {
                    tong = BigDecimal.ZERO;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tong;
    }

    public List<NghiPhep> layTheoNhanVien(int nhanVienId) {
        List<NghiPhep> ds = new ArrayList<>();
        String sql = "SELECT np.*, nv.ho_ten, nv.ma_nhan_vien, nd.ho_ten as ten_nguoi_duyet " +
                "FROM nghi_phep np " +
                "LEFT JOIN nhan_vien nv ON np.nhan_vien_id = nv.nhan_vien_id " +
                "LEFT JOIN nhan_vien nd ON np.nguoi_duyet = nd.nhan_vien_id " +
                "WHERE np.nhan_vien_id = ? " +
                "AND (np.ghi_chu IS NULL OR np.ghi_chu != 'Da xoa') " +
                "ORDER BY np.nghi_phep_id DESC";
        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nhanVienId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) ds.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ds;
    }

    public List<NghiPhep> layChoDuyet() {
        List<NghiPhep> ds = new ArrayList<>();
        String sql = "SELECT np.*, nv.ho_ten, nv.ma_nhan_vien FROM nghi_phep np LEFT JOIN nhan_vien nv ON np.nhan_vien_id=nv.nhan_vien_id WHERE np.trang_thai='Cho duyet' ORDER BY np.nghi_phep_id DESC";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) ds.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ds;
    }

    public NghiPhep layTheoId(int id) {
        String sql = "SELECT np.*, nv.ho_ten, nv.ma_nhan_vien, nd.ho_ten as ten_nguoi_duyet FROM nghi_phep np LEFT JOIN nhan_vien nv ON np.nhan_vien_id=nv.nhan_vien_id LEFT JOIN nhan_vien nd ON np.nguoi_duyet=nd.nhan_vien_id WHERE np.nghi_phep_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean them(NghiPhep np) {
        String sql = "INSERT INTO nghi_phep (nhan_vien_id,loai_phep,ngay_bat_dau,ngay_ket_thuc,so_ngay,ly_do,trang_thai) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, np.getNhanVienId());
            ps.setString(2, np.getLoaiPhep());
            ps.setDate(3, new java.sql.Date(np.getNgayBatDau().getTime()));
            ps.setDate(4, new java.sql.Date(np.getNgayKetThuc().getTime()));
            ps.setBigDecimal(5, np.getSoNgay());
            ps.setString(6, np.getLyDo());
            ps.setString(7, "Cho duyet");
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean duyet(int id, int nguoiDuyet, String trangThai) {
        String sql = "UPDATE nghi_phep SET trang_thai=?, nguoi_duyet=? WHERE nghi_phep_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trangThai);
            ps.setInt(2, nguoiDuyet);
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
    public boolean tuChoiTuDong(int idNghiPhep) {
        String sql = "UPDATE nghi_phep SET trang_thai=? WHERE nghi_phep_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "Tu choi");
            ps.setInt(2,idNghiPhep );
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean xoa(int id) {
        String sql = "DELETE FROM nghi_phep WHERE nghi_phep_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // DAO
    public boolean kiemTraTrungNgay(NghiPhep np) {
        String sql = "SELECT COUNT(*) FROM nghi_phep " +
                "WHERE nhan_vien_id = ? " +
                "AND trang_thai != 'Tu choi' " +
                "AND ngay_bat_dau <= ? AND ngay_ket_thuc >= ?";
        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, np.getNhanVienId());
            ps.setDate(2, new  java.sql.Date(np.getNgayKetThuc().getTime()));
            ps.setDate(3,new java.sql.Date(np.getNgayBatDau().getTime()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean xoaDonDaLau(int nhanVienId) {
        String sql = "UPDATE nghi_phep SET ghi_chu = 'Da xoa' " +
                "WHERE nhan_vien_id = ? " +
                "AND trang_thai IN ('Tu choi', 'Da duyet') " +
                "AND nghi_phep_id NOT IN (" +
                "SELECT nghi_phep_id FROM (" +
                "SELECT nghi_phep_id FROM nghi_phep " +
                "WHERE nhan_vien_id = ? " +
                "ORDER BY nghi_phep_id DESC " +
                "LIMIT 2) AS tmp)";
        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nhanVienId);
            ps.setInt(2, nhanVienId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
    private NghiPhep mapRow(ResultSet rs) throws SQLException {
        NghiPhep np = new NghiPhep();
        np.setNghiPhepId(rs.getInt("nghi_phep_id"));
        np.setNhanVienId(rs.getInt("nhan_vien_id"));
        np.setLoaiPhep(rs.getString("loai_phep"));
        np.setNgayBatDau(rs.getDate("ngay_bat_dau"));
        np.setNgayKetThuc(rs.getDate("ngay_ket_thuc"));
        np.setSoNgay(rs.getBigDecimal("so_ngay"));
        np.setLyDo(rs.getString("ly_do"));
        np.setTrangThai(rs.getString("trang_thai"));
        np.setNguoiDuyet((Integer) rs.getObject("nguoi_duyet"));
        try { np.setHoTen(rs.getString("ho_ten")); } catch (SQLException ignored) {}
        try { np.setMaNhanVien(rs.getString("ma_nhan_vien")); } catch (SQLException ignored) {}
        try { np.setTenNguoiDuyet(rs.getString("ten_nguoi_duyet")); } catch (SQLException ignored) {}
        return np;
    }
}