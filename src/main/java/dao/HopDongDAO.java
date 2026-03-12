package dao;

import model.HopDong;
import ConnDatabase.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HopDongDAO {

    public HopDong layTheoNhanVienId(int nhanVienId) {

        HopDong list = new HopDong();

        String sql = "SELECT * FROM hop_dong WHERE nhan_vien_id = ? ORDER BY ngay_bat_dau DESC";

        try (
                Connection conn = DBConnection.layKetNoi();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, nhanVienId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list=mapRow(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public List<HopDong> searchHopDong(String keyword, String loai, String trangThai) {

        List<HopDong> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT * FROM hop_dong WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND so_hop_dong LIKE ?");
            params.add("%" + keyword.trim() + "%");
        }

        if (loai != null && !loai.trim().isEmpty()) {
            sql.append(" AND loai_hop_dong = ?");
            params.add(loai.trim());
        }

        if (trangThai != null && !trangThai.trim().isEmpty()) {
            sql.append(" AND trang_thai = ?");
            params.add(trangThai.trim());
        }

        sql.append(" ORDER BY ngay_bat_dau DESC");

        try (
                Connection conn = DBConnection.layKetNoi();
                PreparedStatement ps = conn.prepareStatement(sql.toString())
        ) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<HopDong> layHopDongConHieuLuc() {

        List<HopDong> list = new ArrayList<>();

        String sql = "SELECT * FROM hop_dong WHERE trang_thai = 'Hieu luc' ORDER BY ngay_bat_dau DESC";

        try (
                Connection conn = DBConnection.layKetNoi();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public List<HopDong> layTatCa() {

        List<HopDong> list = new ArrayList<>();

        String sql = "SELECT * FROM hop_dong ORDER BY hop_dong_id DESC";

        try (
                Connection conn = DBConnection.layKetNoi();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public HopDong layTheoId(int id) {

        String sql = "SELECT * FROM hop_dong WHERE hop_dong_id=?";

        try (
                Connection conn = DBConnection.layKetNoi();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public boolean them(HopDong hd) {

        String sql =
                "INSERT INTO hop_dong (" +
                        "so_hop_dong," +
                        "nhan_vien_id," +
                        "loai_hop_dong," +
                        "ngay_bat_dau," +
                        "ngay_ket_thuc," +
                        "luong_co_ban," +
                        "phu_cap," +
                        "trang_thai," +
                        "ghi_chu" +
                        ") VALUES (?,?,?,?,?,?,?,?,?)";

        try (
                Connection conn = DBConnection.layKetNoi();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, hd.getSoHopDong());
            ps.setInt(2, hd.getNhanVienId());
            ps.setString(3, hd.getLoaiHopDong());

            ps.setDate(4, new java.sql.Date(hd.getNgayBatDau().getTime()));

            if (hd.getNgayKetThuc() != null) {
                ps.setDate(5, new java.sql.Date(hd.getNgayKetThuc().getTime()));
            } else {
                ps.setNull(5, Types.DATE);
            }

            ps.setBigDecimal(6, hd.getLuongCoBan());
            ps.setBigDecimal(7, hd.getPhuCap());
            ps.setString(8, hd.getTrangThai());
            ps.setString(9, hd.getGhiChu());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean sua(HopDong hd) {

        String sql =
                "UPDATE hop_dong SET " +
                        "so_hop_dong=?," +
                        "loai_hop_dong=?," +
                        "ngay_bat_dau=?," +
                        "ngay_ket_thuc=?," +
                        "luong_co_ban=?," +
                        "phu_cap=?," +
                        "trang_thai=?," +
                        "ghi_chu=? " +
                        "WHERE hop_dong_id=?";

        try (
                Connection conn = DBConnection.layKetNoi();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, hd.getSoHopDong());
            ps.setString(2, hd.getLoaiHopDong());

            ps.setDate(3, new java.sql.Date(hd.getNgayBatDau().getTime()));

            if (hd.getNgayKetThuc() != null) {
                ps.setDate(4, new java.sql.Date(hd.getNgayKetThuc().getTime()));
            } else {
                ps.setNull(4, Types.DATE);
            }

            ps.setBigDecimal(5, hd.getLuongCoBan());
            ps.setBigDecimal(6, hd.getPhuCap());
            ps.setString(7, hd.getTrangThai());
            ps.setString(8, hd.getGhiChu());

            ps.setInt(9, hd.getHopDongId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean xoa(int id) {

        String sql = "DELETE FROM hop_dong WHERE hop_dong_id=?";

        try (
                Connection conn = DBConnection.layKetNoi();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

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

        return hd;
    }
}