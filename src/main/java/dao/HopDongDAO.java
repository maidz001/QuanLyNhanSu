package dao;

import model.HopDong;
import ConnDatabase.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HopDongDAO {

    public List<HopDong> layTatCa() {
        List<HopDong> ds = new ArrayList<>();
        String sql = "SELECT hd.*, nv.ho_ten, nv.ma_nhan_vien FROM hop_dong hd LEFT JOIN nhan_vien nv ON hd.nhan_vien_id=nv.nhan_vien_id ORDER BY hd.hop_dong_id DESC";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) ds.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ds;
    }

    public HopDong layTheoId(int id) {
        String sql = "SELECT hd.*, nv.ho_ten, nv.ma_nhan_vien FROM hop_dong hd LEFT JOIN nhan_vien nv ON hd.nhan_vien_id=nv.nhan_vien_id WHERE hd.hop_dong_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public HopDong layHopDongHieuLuc(int nhanVienId) {
        String sql = "SELECT hd.*, nv.ho_ten, nv.ma_nhan_vien FROM hop_dong hd LEFT JOIN nhan_vien nv ON hd.nhan_vien_id=nv.nhan_vien_id WHERE hd.nhan_vien_id=? AND hd.trang_thai='Hieu luc' LIMIT 1";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nhanVienId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean them(HopDong hd) {
        String sql = "INSERT INTO hop_dong (so_hop_dong,nhan_vien_id,loai_hop_dong,ngay_bat_dau,ngay_ket_thuc,luong_co_ban,phu_cap,trang_thai,ghi_chu) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hd.getSoHopDong());
            ps.setInt(2, hd.getNhanVienId());
            ps.setString(3, hd.getLoaiHopDong());
            ps.setDate(4, new java.sql.Date(hd.getNgayBatDau().getTime()));
            ps.setDate(5, hd.getNgayKetThuc() != null ? new java.sql.Date(hd.getNgayKetThuc().getTime()) : null);
            ps.setBigDecimal(6, hd.getLuongCoBan());
            ps.setBigDecimal(7, hd.getPhuCap());
            ps.setString(8, hd.getTrangThai());
            ps.setString(9, hd.getGhiChu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean sua(HopDong hd) {
        String sql = "UPDATE hop_dong SET so_hop_dong=?,loai_hop_dong=?,ngay_bat_dau=?,ngay_ket_thuc=?,luong_co_ban=?,phu_cap=?,trang_thai=?,ghi_chu=? WHERE hop_dong_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hd.getSoHopDong());
            ps.setString(2, hd.getLoaiHopDong());
            ps.setDate(3, new java.sql.Date(hd.getNgayBatDau().getTime()));
            ps.setDate(4, hd.getNgayKetThuc() != null ? new java.sql.Date(hd.getNgayKetThuc().getTime()) : null);
            ps.setBigDecimal(5, hd.getLuongCoBan());
            ps.setBigDecimal(6, hd.getPhuCap());
            ps.setString(7, hd.getTrangThai());
            ps.setString(8, hd.getGhiChu());
            ps.setInt(9, hd.getHopDongId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean xoa(int id) {
        String sql = "DELETE FROM hop_dong WHERE hop_dong_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private HopDong mapRow(ResultSet rs) throws SQLException {
        HopDong hd = new HopDong();
        hd.setHopDongId(rs.getInt("hop_dong_id"));
        hd.setSoHopDong(rs.getString("so_hop_dong"));
        hd.setNhanVienId(rs.getInt("nhan_vien_id"));
        hd.setLoaiHopDong(rs.getString("loai_hop_dong"));
        hd.setNgayBatDau(rs.getDate("ngay_bat_dau"));
        hd.setNgayKetThuc(rs.getDate("ngay_ket_thuc"));
        hd.setLuongCoBan(rs.getBigDecimal("luong_co_ban"));
        hd.setPhuCap(rs.getBigDecimal("phu_cap"));
        hd.setTrangThai(rs.getString("trang_thai"));
        hd.setGhiChu(rs.getString("ghi_chu"));
        try { hd.setHoTen(rs.getString("ho_ten")); } catch (SQLException ignored) {}
        try { hd.setMaNhanVien(rs.getString("ma_nhan_vien")); } catch (SQLException ignored) {}
        return hd;
    }
    public List<HopDong> searchHopDong(String keyword, String loaiHopDong, String trangThai) {

        List<HopDong> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT hd.*, nv.hoTen " +
                        "FROM HopDong hd " +
                        "JOIN NhanVien nv ON hd.nhanVienId = nv.nhanVienId " +
                        "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (hd.soHopDong LIKE ? OR nv.hoTen LIKE ?)");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
        }

        if (loaiHopDong != null && !loaiHopDong.isEmpty()) {
            sql.append(" AND hd.loaiHopDong = ?");
            params.add(loaiHopDong);
        }

        if (trangThai != null && !trangThai.isEmpty()) {
            sql.append(" AND hd.trangThai = ?");
            params.add(trangThai);
        }

        sql.append(" ORDER BY hd.ngayBatDau DESC");

        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                HopDong hd = new HopDong();

                hd.setHopDongId(rs.getInt("hopDongId"));
                hd.setSoHopDong(rs.getString("soHopDong"));
                hd.setNhanVienId(rs.getInt("nhanVienId"));
                hd.setLoaiHopDong(rs.getString("loaiHopDong"));
                hd.setNgayBatDau(rs.getDate("ngayBatDau"));
                hd.setNgayKetThuc(rs.getDate("ngayKetThuc"));
                hd.setLuongCoBan(rs.getBigDecimal("luongCoBan"));
                hd.setPhuCap(rs.getBigDecimal("phuCap"));
                hd.setTrangThai(rs.getString("trangThai"));
                hd.setGhiChu(rs.getString("ghiChu"));

                // nếu model có field tên nhân viên
                hd.setHoTen(rs.getString("hoTen"));

                list.add(hd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
