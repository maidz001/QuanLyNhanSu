package dao;

import model.BangLuong;
import ConnDatabase.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BangLuongDAO {

    public List<BangLuong> layTatCa() {
        List<BangLuong> ds = new ArrayList<>();
        String sql = "SELECT bl.*, nv.ho_ten, nv.ma_nhan_vien FROM bang_luong bl LEFT JOIN nhan_vien nv ON bl.nhan_vien_id=nv.nhan_vien_id ORDER BY bl.nam DESC, bl.thang DESC";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) ds.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ds;
    }

    public List<BangLuong> layTheoThangNam(int thang, int nam) {
        List<BangLuong> ds = new ArrayList<>();
        String sql = "SELECT bl.*, nv.ho_ten, nv.ma_nhan_vien FROM bang_luong bl LEFT JOIN nhan_vien nv ON bl.nhan_vien_id=nv.nhan_vien_id WHERE bl.thang=? AND bl.nam=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, thang); ps.setInt(2, nam);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) ds.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ds;
    }
    public boolean daCoThang(int nhanVienId, int thang, int nam) {
        String sql = "SELECT COUNT(*) FROM bang_luong WHERE nhan_vien_id=? AND thang=? AND nam=?";
        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nhanVienId);
            ps.setInt(2, thang);
            ps.setInt(3, nam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
    public BangLuong getBangLuongMoiNhatByNhanVien(int nhanVienId) {
        BangLuong bl = null;
        String sql = "SELECT * FROM bang_luong WHERE nhan_vien_id=? ORDER BY nam DESC, thang DESC LIMIT 1";

        try {
            Connection conn = DBConnection.layKetNoi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, nhanVienId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                bl = new BangLuong(
                        rs.getInt("bang_luong_id"),
                        rs.getInt("nhan_vien_id"),
                        rs.getInt("thang"),
                        rs.getInt("nam"),
                        rs.getBigDecimal("so_ngay_lam_viec"),
                        rs.getBigDecimal("so_ngay_thuc_te"),
                        rs.getBigDecimal("gio_lam_them"),
                        rs.getBigDecimal("luong_co_ban"),
                        rs.getBigDecimal("phu_cap"),
                        rs.getBigDecimal("luong_lam_them"),
                        rs.getBigDecimal("thuong"),
                        rs.getBigDecimal("tong_thu_nhap"),
                        rs.getBigDecimal("bao_hiem_xa_hoi"),
                        rs.getBigDecimal("bao_hiem_y_te"),
                        rs.getBigDecimal("tam_ung"),
                        rs.getBigDecimal("tong_khau_tru"),
                        rs.getBigDecimal("luong_thuc_lanh"),
                        rs.getDate("ngay_thanh_toan"),
                        rs.getString("trang_thai")

                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
if(bl==null)
    return new BangLuong();
        return bl;
    }

    public List<BangLuong> layTheoNhanVien(int nhanVienId) {
        List<BangLuong> ds = new ArrayList<>();
        String sql = "SELECT bl.*, nv.ho_ten, nv.ma_nhan_vien FROM bang_luong bl LEFT JOIN nhan_vien nv ON bl.nhan_vien_id=nv.nhan_vien_id WHERE bl.nhan_vien_id=? ORDER BY bl.nam DESC, bl.thang DESC";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nhanVienId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) ds.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ds;
    }

    public BangLuong layTheoId(int id) {
        String sql = "SELECT bl.*, nv.ho_ten, nv.ma_nhan_vien FROM bang_luong bl LEFT JOIN nhan_vien nv ON bl.nhan_vien_id=nv.nhan_vien_id WHERE bl.bang_luong_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean kiemTraDaTinhLuong(int nhanVienId, int thang, int nam) {
        String sql = "SELECT COUNT(*) FROM bang_luong WHERE nhan_vien_id=? AND thang=? AND nam=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nhanVienId); ps.setInt(2, thang); ps.setInt(3, nam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean them(BangLuong bl) {
        String sql = "INSERT INTO bang_luong (nhan_vien_id,thang,nam,so_ngay_lam_viec,so_ngay_thuc_te,gio_lam_them,luong_co_ban,phu_cap,luong_lam_them,thuong,tong_thu_nhap,bao_hiem_xa_hoi,bao_hiem_y_te,tam_ung,tong_khau_tru,luong_thuc_lanh,trang_thai) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bl.getNhanVienId());
            ps.setInt(2, bl.getThang());
            ps.setInt(3, bl.getNam());
            ps.setBigDecimal(4, bl.getSoNgayLamViec());
            ps.setBigDecimal(5, bl.getSoNgayThucTe());
            ps.setBigDecimal(6, bl.getGioLamThem());
            ps.setBigDecimal(7, bl.getLuongCoBan());
            ps.setBigDecimal(8, bl.getPhuCap());
            ps.setBigDecimal(9, bl.getLuongLamThem());
            ps.setBigDecimal(10, bl.getThuong());
            ps.setBigDecimal(11, bl.getTongThuNhap());
            ps.setBigDecimal(12, bl.getBaoHiemXaHoi());
            ps.setBigDecimal(13, bl.getBaoHiemYTe());
            ps.setBigDecimal(14, bl.getTamUng());
            ps.setBigDecimal(15, bl.getTongKhauTru());
            ps.setBigDecimal(16, bl.getLuongThucLanh());
            ps.setString(17, bl.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean capNhatTrangThai(int id, String trangThai) {
        String sql = "UPDATE bang_luong SET trang_thai=? WHERE bang_luong_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trangThai); ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean xoa(int id) {
        String sql = "DELETE FROM bang_luong WHERE bang_luong_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private BangLuong mapRow(ResultSet rs) throws SQLException {
        BangLuong bl = new BangLuong();
        bl.setBangLuongId(rs.getInt("bang_luong_id"));
        bl.setNhanVienId(rs.getInt("nhan_vien_id"));
        bl.setThang(rs.getInt("thang"));
        bl.setNam(rs.getInt("nam"));
        bl.setSoNgayLamViec(rs.getBigDecimal("so_ngay_lam_viec"));
        bl.setSoNgayThucTe(rs.getBigDecimal("so_ngay_thuc_te"));
        bl.setGioLamThem(rs.getBigDecimal("gio_lam_them"));
        bl.setLuongCoBan(rs.getBigDecimal("luong_co_ban"));
        bl.setPhuCap(rs.getBigDecimal("phu_cap"));
        bl.setLuongLamThem(rs.getBigDecimal("luong_lam_them"));
        bl.setThuong(rs.getBigDecimal("thuong"));
        bl.setTongThuNhap(rs.getBigDecimal("tong_thu_nhap"));
        bl.setBaoHiemXaHoi(rs.getBigDecimal("bao_hiem_xa_hoi"));
        bl.setBaoHiemYTe(rs.getBigDecimal("bao_hiem_y_te"));
        bl.setTamUng(rs.getBigDecimal("tam_ung"));
        bl.setTongKhauTru(rs.getBigDecimal("tong_khau_tru"));
        bl.setLuongThucLanh(rs.getBigDecimal("luong_thuc_lanh"));
        bl.setNgayThanhToan(rs.getDate("ngay_thanh_toan"));
        bl.setTrangThai(rs.getString("trang_thai"));


        return bl;
    }
    public boolean sua(BangLuong bl){

        String sql = "UPDATE bang_luong SET "
                + "nhan_vien_id=?, "
                + "thang=?, "
                + "nam=?, "
                + "so_ngay_lam_viec=?, "
                + "so_ngay_thuc_te=?, "
                + "gio_lam_them=?, "
                + "luong_co_ban=?, "
                + "phu_cap=?, "
                + "luong_lam_them=?, "
                + "thuong=?, "
                + "tong_thu_nhap=?, "
                + "bao_hiem_xa_hoi=?, "
                + "bao_hiem_y_te=?, "
                + "tam_ung=?, "
                + "tong_khau_tru=?, "
                + "luong_thuc_lanh=?, "
                + "ngay_thanh_toan=?, "
                + "trang_thai=? "
                + "WHERE bang_luong_id=?";

        try {

            Connection conn = DBConnection.layKetNoi();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, bl.getNhanVienId());
            ps.setInt(2, bl.getThang());
            ps.setInt(3, bl.getNam());

            ps.setBigDecimal(4, bl.getSoNgayLamViec());
            ps.setBigDecimal(5, bl.getSoNgayThucTe());
            ps.setBigDecimal(6, bl.getGioLamThem());

            ps.setBigDecimal(7, bl.getLuongCoBan());
            ps.setBigDecimal(8, bl.getPhuCap());
            ps.setBigDecimal(9, bl.getLuongLamThem());

            ps.setBigDecimal(10, bl.getThuong());
            ps.setBigDecimal(11, bl.getTongThuNhap());

            ps.setBigDecimal(12, bl.getBaoHiemXaHoi());
            ps.setBigDecimal(13, bl.getBaoHiemYTe());

            ps.setBigDecimal(14, bl.getTamUng());
            ps.setBigDecimal(15, bl.getTongKhauTru());

            ps.setBigDecimal(16, bl.getLuongThucLanh());

            ps.setDate(17, new java.sql.Date(bl.getNgayThanhToan().getTime()));

            ps.setString(18, bl.getTrangThai());

            ps.setInt(19, bl.getBangLuongId());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}