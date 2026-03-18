package dao;

import ConnDatabase.DBConnection;
import model.NhanVien;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String sql = "INSERT INTO nhan_vien " +
                "(ma_nhan_vien,ho_ten,email,dien_thoai,ngay_sinh,gioi_tinh,so_cmnd," +
                "dia_chi,phong_ban_id,chuc_vu_id,ngay_vao_lam,trang_thai,anh_dai_dien," +
                "so_tai_khoan,ngan_hang) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,  nv.getMaNhanVien());
            ps.setString(2,  nv.getHoTen());
            ps.setString(3,  nv.getEmail());
            ps.setString(4,  nv.getDienThoai());
            ps.setDate(5,    nv.getNgaySinh() != null ? new java.sql.Date(nv.getNgaySinh().getTime()) : null);
            ps.setString(6,  nv.getGioiTinh());
            ps.setString(7,  nv.getSoCmnd());
            ps.setString(8,  nv.getDiaChi());
            ps.setInt(9,     nv.getPhongBanId());
            ps.setInt(10,    nv.getChucVuId());
            ps.setDate(11,   nv.getNgayVaoLam() != null ? new java.sql.Date(nv.getNgayVaoLam().getTime()) : null);
            ps.setString(12, nv.getTrangThai());
            ps.setString(13, nv.getAnhDaiDien());
            ps.setString(14, nv.getSoTaiKhoan());
            ps.setString(15, nv.getNganHang());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean sua(NhanVien nv) {
        String sql = "UPDATE nhan_vien SET ma_nhan_vien=?,ho_ten=?,email=?,dien_thoai=?," +
                "ngay_sinh=?,gioi_tinh=?,so_cmnd=?,dia_chi=?,phong_ban_id=?,chuc_vu_id=?," +
                "ngay_vao_lam=?,trang_thai=?,anh_dai_dien=?,so_tai_khoan=?,ngan_hang=? " +
                "WHERE nhan_vien_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,  nv.getMaNhanVien());
            ps.setString(2,  nv.getHoTen());
            ps.setString(3,  nv.getEmail());
            ps.setString(4,  nv.getDienThoai());
            ps.setDate(5,    nv.getNgaySinh() != null ? new java.sql.Date(nv.getNgaySinh().getTime()) : null);
            ps.setString(6,  nv.getGioiTinh());
            ps.setString(7,  nv.getSoCmnd());
            ps.setString(8,  nv.getDiaChi());
            ps.setInt(9,     nv.getPhongBanId());
            ps.setInt(10,    nv.getChucVuId());
            ps.setDate(11,   nv.getNgayVaoLam() != null ? new java.sql.Date(nv.getNgayVaoLam().getTime()) : null);
            ps.setString(12, nv.getTrangThai());
            ps.setString(13, nv.getAnhDaiDien());
            ps.setString(14, nv.getSoTaiKhoan());
            ps.setString(15, nv.getNganHang());
            ps.setInt(16,    nv.getNhanVienId());
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
        String sql = "SELECT * FROM nhan_vien WHERE ma_nhan_vien = ?";
        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNhanVien);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public boolean setTrangThaiNghiViec(int nhanVienId) {
        String sql = "UPDATE nhan_vien SET trang_thai = 'Nghi viec' WHERE nhan_vien_id = ?";
        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nhanVienId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public List<NhanVien> getNhanVienKhongPhaiTruongPhong() {
        List<NhanVien> list = new ArrayList<>();
        String sql = """
                SELECT nv.*, pb.ten_phong_ban, cv.ten_chuc_vu
                FROM nhan_vien nv
                LEFT JOIN phong_ban pb ON nv.phong_ban_id = pb.phong_ban_id
                LEFT JOIN chuc_vu   cv ON nv.chuc_vu_id   = cv.chuc_vu_id
                WHERE nv.nhan_vien_id NOT IN (
                    SELECT truong_phong_id FROM phong_ban WHERE truong_phong_id IS NOT NULL
                )
                ORDER BY nv.ho_ten
                """;
        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<NhanVien> getNhanVienKhongPhaiTruongPhongTrongPB(int phongBanId) {
        List<NhanVien> list = new ArrayList<>();
        String sql = """
                SELECT nv.*, pb.ten_phong_ban, cv.ten_chuc_vu
                FROM nhan_vien nv
                LEFT JOIN phong_ban pb ON nv.phong_ban_id = pb.phong_ban_id
                LEFT JOIN chuc_vu   cv ON nv.chuc_vu_id   = cv.chuc_vu_id
                WHERE nv.phong_ban_id = ?
                AND nv.nhan_vien_id NOT IN (
                    SELECT truong_phong_id FROM phong_ban WHERE truong_phong_id IS NOT NULL
                )
                ORDER BY nv.ho_ten
                """;
        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, phongBanId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean setChucVuTruongPhong(int nhanVienId, int chucVuTruongPhongId) {
        String sql = "UPDATE nhan_vien SET chuc_vu_id = ? WHERE nhan_vien_id = ?";
        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, chucVuTruongPhongId);
            ps.setInt(2, nhanVienId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean setChucVu(int nhanVienId, int chucVuId) {
        String sql = "UPDATE nhan_vien SET chuc_vu_id = ? WHERE nhan_vien_id = ?";
        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, chucVuId);
            ps.setInt(2, nhanVienId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public Map<String, Integer> getThongKeGioiTinh() {
        Map<String, Integer> result = new HashMap<>();
        result.put("nam", 0);
        result.put("nu", 0);
        result.put("khac", 0);

        String sql = "SELECT gioi_tinh, COUNT(*) as so_luong " +
                "FROM nhan_vien " +
                "WHERE trang_thai = 'Dang lam viec' " +
                "GROUP BY gioi_tinh";

        try (Connection conn=DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String gioiTinh = rs.getString("gioi_tinh");
                int soLuong = rs.getInt("so_luong");

                if (gioiTinh != null) {
                    String gt = gioiTinh.toLowerCase().trim();
                    if (gt.equals("nam")) {
                        result.put("nam", soLuong);
                    } else if (gt.equals("nữ") || gt.equals("nu")) {
                        result.put("nu", soLuong);
                    } else {
                        result.put("khac", result.get("khac") + soLuong);
                    }
                } else {
                    result.put("khac", result.get("khac") + soLuong);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public List<Integer> getAllNamSinh() {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT YEAR(ngay_sinh) as ngay_sinh " +
                "FROM nhan_vien " +
                "WHERE ngay_sinh IS NOT NULL AND trang_thai = 'Dang lam viec'";

        try (Connection conn=DBConnection.layKetNoi();PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getInt("ngay_sinh"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
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
        try { nv.setSoTaiKhoan(rs.getString("so_tai_khoan")); } catch (SQLException ignored) {}
        try { nv.setNganHang(rs.getString("ngan_hang"));      } catch (SQLException ignored) {}
        try { nv.setTenPhongBan(rs.getString("ten_phong_ban")); } catch (SQLException ignored) {}
        try { nv.setTenChucVu(rs.getString("ten_chuc_vu"));    } catch (SQLException ignored) {}
        return nv;
    }
}