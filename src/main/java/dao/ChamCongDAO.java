package dao;

import model.ChamCong;
import ConnDatabase.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChamCongDAO {

    public List<ChamCong> layTatCa() {
        List<ChamCong> ds = new ArrayList<>();
        String sql = "SELECT cc.*, nv.ho_ten, nv.ma_nhan_vien FROM cham_cong cc LEFT JOIN nhan_vien nv ON cc.nhan_vien_id=nv.nhan_vien_id ORDER BY cc.ngay_cham_cong DESC";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) ds.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ds;
    }

    public List<ChamCong> layTheoNhanVien(int nhanVienId) {
        List<ChamCong> ds = new ArrayList<>();
        String sql = "SELECT cc.*, nv.ho_ten, nv.ma_nhan_vien FROM cham_cong cc LEFT JOIN nhan_vien nv ON cc.nhan_vien_id=nv.nhan_vien_id WHERE cc.nhan_vien_id=? ORDER BY cc.ngay_cham_cong DESC";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nhanVienId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) ds.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ds;
    }

    public List<ChamCong> layTheoThang(int thang, int nam) {
        List<ChamCong> ds = new ArrayList<>();
        String sql = "SELECT cc.*, nv.ho_ten, nv.ma_nhan_vien FROM cham_cong cc LEFT JOIN nhan_vien nv ON cc.nhan_vien_id=nv.nhan_vien_id WHERE MONTH(cc.ngay_cham_cong)=? AND YEAR(cc.ngay_cham_cong)=? ORDER BY cc.nhan_vien_id, cc.ngay_cham_cong";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, thang); ps.setInt(2, nam);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) ds.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ds;
    }

    public ChamCong layTheoId(int id) {
        String sql = "SELECT cc.*, nv.ho_ten, nv.ma_nhan_vien FROM cham_cong cc LEFT JOIN nhan_vien nv ON cc.nhan_vien_id=nv.nhan_vien_id WHERE cc.cham_cong_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean them(ChamCong cc) {
        String sql = "INSERT INTO cham_cong (nhan_vien_id,ngay_cham_cong,gio_vao,gio_ra,so_gio_lam_viec,gio_lam_them,trang_thai,ghi_chu) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cc.getNhanVienId());
            ps.setDate(2, new java.sql.Date(cc.getNgayChamCong().getTime()));
            ps.setString(3, cc.getGioVao());
            ps.setString(4, cc.getGioRa());
            ps.setBigDecimal(5, cc.getSoGioLamViec());
            ps.setBigDecimal(6, cc.getGioLamThem());
            ps.setString(7, cc.getTrangThai());
            ps.setString(8, cc.getGhiChu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }



    public boolean xoa(int id) {
        String sql = "DELETE FROM cham_cong WHERE cham_cong_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public int demNgayDiLamTheoThang(int nhanVienId, int thang, int nam) {
        String sql = "SELECT COUNT(*) FROM cham_cong WHERE nhan_vien_id=? AND MONTH(ngay_cham_cong)=? AND YEAR(ngay_cham_cong)=? AND trang_thai='Di lam'";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nhanVienId); ps.setInt(2, thang); ps.setInt(3, nam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public double tinhTongGioLamThemTheoThang(int nhanVienId, int thang, int nam) {
        String sql = "SELECT COALESCE(SUM(gio_lam_them),0) FROM cham_cong WHERE nhan_vien_id=? AND MONTH(ngay_cham_cong)=? AND YEAR(ngay_cham_cong)=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nhanVienId); ps.setInt(2, thang); ps.setInt(3, nam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    private ChamCong mapRow(ResultSet rs) throws SQLException {
        ChamCong cc = new ChamCong();
        cc.setChamCongId(rs.getInt("cham_cong_id"));
        cc.setNhanVienId(rs.getInt("nhan_vien_id"));
        cc.setNgayChamCong(rs.getDate("ngay_cham_cong"));
        cc.setGioVao(rs.getString("gio_vao"));
        cc.setGioRa(rs.getString("gio_ra"));
        cc.setSoGioLamViec(rs.getBigDecimal("so_gio_lam_viec"));
        cc.setGioLamThem(rs.getBigDecimal("gio_lam_them"));
        cc.setTrangThai(rs.getString("trang_thai"));
        cc.setGhiChu(rs.getString("ghi_chu"));

        return cc;
    }

    public double tinhTongGioLamChinhTheoThang(int nhanVienId, int thang, int nam) {
        String sql = "SELECT COALESCE(SUM(so_gio_lam_viec),0) FROM cham_cong WHERE nhan_vien_id=? AND MONTH(ngay_cham_cong)=? AND YEAR(ngay_cham_cong)=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nhanVienId); ps.setInt(2, thang); ps.setInt(3, nam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
    public boolean checkIn(ChamCong cc){
        String sql="INSERT INTO cham_cong ( nhan_vien_id, ngay_cham_cong, gio_vao, gio_ra, so_gio_lam_viec, gio_lam_them,trang_thai, ghi_chu) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
                Connection conn=DBConnection.layKetNoi();
                PreparedStatement pstt=conn.prepareStatement(sql);
            pstt.setInt(1, cc.getNhanVienId());
            pstt.setDate(2, new java.sql.Date(cc.getNgayChamCong().getTime()));
            pstt.setString(3, cc.getGioVao());
            pstt.setString(4, cc.getGioRa());
            pstt.setBigDecimal(5, cc.getSoGioLamViec());
            pstt.setBigDecimal(6, cc.getGioLamThem());
            pstt.setString(7, cc.getTrangThai());
            pstt.setString(8, cc.getGhiChu());
            pstt.executeUpdate();
            return true;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean daChamCong(int nhanVienId, LocalDate ngay) {
        String sql = "SELECT * FROM cham_cong WHERE nhan_vien_id=? AND ngay_cham_cong=?";

        try (Connection conn = DBConnection.layKetNoi();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nhanVienId);
            ps.setDate(2, java.sql.Date.valueOf(ngay));

            ResultSet rs = ps.executeQuery();

             while (rs.next()){
                 return true;
             }
             return false;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public boolean checkOut(ChamCong cc) {
        String sql = "UPDATE cham_cong SET gio_ra=?,so_gio_lam_viec=?,gio_lam_them=? WHERE ngay_cham_cong=? AND nhan_vien_id=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cc.getGioRa());
            ps.setBigDecimal(2, cc.getSoGioLamViec());
            ps.setBigDecimal(3, cc.getGioLamThem());
            ps.setDate(4,  new java.sql.Date(cc.getNgayChamCong().getTime()));
            ps.setInt(5, cc.getNhanVienId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
    public ChamCong layRaChamCongHomNay(int idNhanVien , LocalDate ngay) {
        String sql = "SELECT * FROM cham_cong WHERE nhan_vien_id=? AND ngay_cham_cong=?";
        try (Connection conn = DBConnection.layKetNoi(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idNhanVien);
            ps.setDate(2, java.sql.Date.valueOf(ngay));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
    public List<ChamCong> layChamCongNghiKhongPhep(int idNhanVien) {

        List<ChamCong> list = new ArrayList<>();

        String sql = "SELECT * FROM cham_cong WHERE trang_thai = ? AND nhan_vien_id=?";

        try {
            Connection connection=DBConnection.layKetNoi();
            PreparedStatement pstt=connection.prepareStatement(sql);
            pstt.setString(1,"Nghi phep");
            pstt.setInt(2,idNhanVien);
            ResultSet rs=pstt.executeQuery();
            while (rs.next())
            {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;
    }
    // kiem tra hom qua cos di lam khong
    public boolean daCoRecord(int nhanVienId, LocalDate ngay) {
        String sql = "SELECT COUNT(*) FROM cham_cong WHERE nhan_vien_id = ? AND ngay_cham_cong = ?";
        try (Connection con = DBConnection.layKetNoi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, nhanVienId);
            ps.setDate(2, java.sql.Date.valueOf(ngay));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // them ban ghi nghi khong phep
    public void insertNghiKhongPhep(int nhanVienId, LocalDate ngay) {
        String sql = "INSERT INTO cham_cong (nhan_vien_id, ngay_cham_cong, trang_thai) VALUES (?, ?, 'Nghi khong phep')";
        try (Connection con = DBConnection.layKetNoi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, nhanVienId);
            ps.setDate(2, java.sql.Date.valueOf(ngay));
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

}