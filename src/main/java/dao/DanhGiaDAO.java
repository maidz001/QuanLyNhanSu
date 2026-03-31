package dao;

import model.DanhGia;
import ConnDatabase.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DanhGiaDAO {

    public List<DanhGia> layTatCa() {
        List<DanhGia> ds = new ArrayList<>();
        String sql = "SELECT dg.*, nv.ho_ten, nd.ho_ten as ten_nguoi_danh_gia FROM danh_gia dg LEFT JOIN nhan_vien nv ON dg.nhan_vien_id=nv.nhan_vien_id LEFT JOIN nhan_vien nd ON dg.nguoi_danh_gia=nd.nhan_vien_id ORDER BY dg.danh_gia_id DESC";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) ds.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ds;
    }

    public DanhGia layTheoId(int id) {
        String sql = "SELECT dg.*, nv.ho_ten, nd.ho_ten as ten_nguoi_danh_gia FROM danh_gia dg LEFT JOIN nhan_vien nv ON dg.nhan_vien_id=nv.nhan_vien_id LEFT JOIN nhan_vien nd ON dg.nguoi_danh_gia=nd.nhan_vien_id WHERE dg.danh_gia_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean them(DanhGia dg) {
        String sql = "INSERT INTO danh_gia (nhan_vien_id,thang,nam,tong_diem,xep_loai,nhan_xet,nguoi_danh_gia) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dg.getNhanVienId());
            ps.setInt(2, dg.getThang());
            ps.setInt(3, dg.getNam());
            ps.setBigDecimal(4, dg.getTongDiem());
            ps.setString(5, dg.getXepLoai());
            ps.setString(6, dg.getNhanXet());
            ps.setInt(7, dg.getNguoiDanhGia());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean sua(DanhGia dg) {
        String sql = "UPDATE danh_gia SET tong_diem=?,xep_loai=?,nhan_xet=? WHERE danh_gia_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, dg.getTongDiem());
            ps.setString(2, dg.getXepLoai());
            ps.setString(3, dg.getNhanXet());
            ps.setInt(4, dg.getDanhGiaId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean xoa(int id) {
        String sql = "DELETE FROM danh_gia WHERE danh_gia_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
    public List<DanhGia> searchDanhGia(String keyword, String thangStr, String namStr, String xepLoai) {

        List<DanhGia> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT dg.*, nv.ho_ten, nd.ho_ten AS ten_nguoi_danh_gia " +
                        "FROM danh_gia dg " +
                        "LEFT JOIN nhan_vien nv ON dg.nhan_vien_id = nv.nhan_vien_id " +
                        "LEFT JOIN nhan_vien nd ON dg.nguoi_danh_gia = nd.nhan_vien_id " +
                        "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND nv.ho_ten LIKE ? ");
            params.add("%" + keyword.trim() + "%");
        }

        if (thangStr != null && !thangStr.isEmpty()) {
            sql.append("AND dg.thang = ? ");
            params.add(Integer.parseInt(thangStr));
        }

        if (namStr != null && !namStr.isEmpty()) {
            sql.append("AND dg.nam = ? ");
            params.add(Integer.parseInt(namStr));
        }

        if (xepLoai != null && !xepLoai.isEmpty()) {
            sql.append("AND dg.xep_loai = ? ");
            params.add(xepLoai);
        }

        sql.append("ORDER BY dg.danh_gia_id DESC");

        try {

            Connection conn = DBConnection.layKetNoi();

            PreparedStatement pstt = conn.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                pstt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = pstt.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<DanhGia> layTheoNhanVienId(int nhanVienId) {
        List<DanhGia> list = new ArrayList<>();

        String sql = "SELECT dg.*, nv.ho_ten, ndg.ho_ten AS ten_nguoi_danh_gia " +
                "FROM danh_gia dg " +
                "LEFT JOIN nhan_vien nv ON dg.nhan_vien_id = nv.nhan_vien_id " +
                "LEFT JOIN nhan_vien ndg ON dg.nguoi_danh_gia = ndg.nhan_vien_id " +
                "WHERE dg.nhan_vien_id = ? " +
                "ORDER BY dg.nam DESC, dg.thang DESC";

        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nhanVienId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DanhGia dg = new DanhGia();

                dg.setDanhGiaId(rs.getInt("danh_gia_id"));
                dg.setNhanVienId(rs.getInt("nhan_vien_id"));
                dg.setThang(rs.getInt("thang"));
                dg.setNam(rs.getInt("nam"));
                dg.setTongDiem(rs.getBigDecimal("tong_diem"));
                dg.setXepLoai(rs.getString("xep_loai"));
                dg.setNhanXet(rs.getString("nhan_xet"));
                dg.setNguoiDanhGia(rs.getInt("nguoi_danh_gia"));
                dg.setNgayDanhGia(rs.getDate("ngay_danh_gia"));


                list.add(dg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private DanhGia mapRow(ResultSet rs) throws SQLException {
        DanhGia dg = new DanhGia();
        dg.setDanhGiaId(rs.getInt("danh_gia_id"));
        dg.setNhanVienId(rs.getInt("nhan_vien_id"));
        dg.setThang(rs.getInt("thang"));
        dg.setNam(rs.getInt("nam"));
        dg.setTongDiem(rs.getBigDecimal("tong_diem"));
        dg.setXepLoai(rs.getString("xep_loai"));
        dg.setNhanXet(rs.getString("nhan_xet"));
        dg.setNguoiDanhGia(rs.getInt("nguoi_danh_gia"));
        dg.setNgayDanhGia(rs.getTimestamp("ngay_danh_gia"));
        return dg;
    }
}

