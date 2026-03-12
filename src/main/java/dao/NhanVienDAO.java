package dao;

import ConnDatabase.DBConnection;
import model.NhanVien;
import ConnDatabase.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    public List<NhanVien> layTatCa() {
        List<NhanVien> ds = new ArrayList<>();
        String sql = "SELECT nv.*, pb.ten_phong_ban, cv.ten_chuc_vu FROM nhan_vien nv " +
                "LEFT JOIN phong_ban pb ON nv.phong_ban_id = pb.phong_ban_id " +
                "LEFT JOIN chuc_vu cv ON nv.chuc_vu_id = cv.chuc_vu_id ORDER BY nv.nhan_vien_id";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) ds.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ds;
    }

    public NhanVien layTheoId(int id) {
        String sql = "SELECT nv.*, pb.ten_phong_ban, cv.ten_chuc_vu FROM nhan_vien nv " +
                "LEFT JOIN phong_ban pb ON nv.phong_ban_id = pb.phong_ban_id " +
                "LEFT JOIN chuc_vu cv ON nv.chuc_vu_id = cv.chuc_vu_id WHERE nv.nhan_vien_id = ?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean them(NhanVien nv) {
        String sql = "INSERT INTO nhan_vien (ma_nhan_vien,ho_ten,email,dien_thoai,ngay_sinh,gioi_tinh,so_cmnd,dia_chi,phong_ban_id,chuc_vu_id,ngay_vao_lam,trang_thai,anh_dai_dien) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nv.getMaNhanVien());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getEmail());
            ps.setString(4, nv.getDienThoai());
            ps.setDate(5, nv.getNgaySinh() != null ? new java.sql.Date(nv.getNgaySinh().getTime()) : null);
            ps.setString(6, nv.getGioiTinh());
            ps.setString(7, nv.getSoCmnd());
            ps.setString(8, nv.getDiaChi());
            ps.setInt(9, nv.getPhongBanId());
            ps.setInt(10, nv.getChucVuId());
            ps.setDate(11, new java.sql.Date(nv.getNgayVaoLam().getTime()));
            ps.setString(12, nv.getTrangThai());
            ps.setString(13, nv.getAnhDaiDien());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean sua(NhanVien nv) {
        String sql = "UPDATE nhan_vien SET ma_nhan_vien=?,ho_ten=?,email=?,dien_thoai=?,ngay_sinh=?,gioi_tinh=?,so_cmnd=?,dia_chi=?,phong_ban_id=?,chuc_vu_id=?,ngay_vao_lam=?,trang_thai=?,anh_dai_dien=? WHERE nhan_vien_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nv.getMaNhanVien());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getEmail());
            ps.setString(4, nv.getDienThoai());
            ps.setDate(5, nv.getNgaySinh() != null ? new java.sql.Date(nv.getNgaySinh().getTime()) : null);
            ps.setString(6, nv.getGioiTinh());
            ps.setString(7, nv.getSoCmnd());
            ps.setString(8, nv.getDiaChi());
            ps.setInt(9, nv.getPhongBanId());
            ps.setInt(10, nv.getChucVuId());
            ps.setDate(11, new java.sql.Date(nv.getNgayVaoLam().getTime()));
            ps.setString(12, nv.getTrangThai());
            ps.setString(13, nv.getAnhDaiDien());
            ps.setInt(14, nv.getNhanVienId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean xoa(int id) {
        String sql = "DELETE FROM nhan_vien WHERE nhan_vien_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public List<NhanVien> timKiem(String tuKhoa) {
        List<NhanVien> ds = new ArrayList<>();
        String sql = "SELECT nv.*, pb.ten_phong_ban, cv.ten_chuc_vu FROM nhan_vien nv " +
                "LEFT JOIN phong_ban pb ON nv.phong_ban_id = pb.phong_ban_id " +
                "LEFT JOIN chuc_vu cv ON nv.chuc_vu_id = cv.chuc_vu_id " +
                "WHERE nv.ho_ten LIKE ? OR nv.ma_nhan_vien LIKE ? OR nv.email LIKE ?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            String lk = "%" + tuKhoa + "%";
            ps.setString(1, lk); ps.setString(2, lk); ps.setString(3, lk);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) ds.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ds;
    }

    public int demTongNhanVien() {
        String sql = "SELECT COUNT(*) FROM nhan_vien WHERE trang_thai='Dang lam viec'";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public NhanVien layTheoMa(String maNhanVien) {
        NhanVien nv = null;
        String sql = "SELECT * FROM nhan_vien WHERE ma_nhan_vien = ?";

        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNhanVien);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nv=mapRow(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return nv;
    }


    private NhanVien mapRow(ResultSet rs) throws SQLException {
        NhanVien nv = new NhanVien();

        nv.setNhanVienId(rs.getInt("nhan_vien_id"));
        nv.setMaNhanVien(rs.getString("ma_nhan_vien"));
        nv.setHoTen(rs.getString("ho_ten"));
        nv.setEmail(rs.getString("email"));
        nv.setDienThoai(rs.getString("dien_thoai"));
        nv.setNgaySinh(rs.getDate("ngay_sinh"));
        nv.setGioiTinh(rs.getString("gioi_tinh"));
        nv.setSoCmnd(rs.getString("so_cmnd"));
        nv.setDiaChi(rs.getString("dia_chi"));
        nv.setPhongBanId(rs.getInt("phong_ban_id"));
        nv.setChucVuId(rs.getInt("chuc_vu_id"));
        nv.setNgayVaoLam(rs.getDate("ngay_vao_lam"));
        nv.setTrangThai(rs.getString("trang_thai"));
        nv.setAnhDaiDien(rs.getString("anh_dai_dien"));
        try { nv.setTenPhongBan(rs.getString("ten_phong_ban")); } catch (SQLException ignored) {}
        try { nv.setTenChucVu(rs.getString("ten_chuc_vu")); } catch (SQLException ignored) {}
        return nv;
    }

    public boolean setTrangThaiNghiViec(int nhanVienId) {
        String sql = "UPDATE nhan_vien SET trang_thai = 'Nghi viec' WHERE nhan_vien_id = ?";

        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nhanVienId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}