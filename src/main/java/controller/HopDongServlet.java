package controller;

import ConnDatabase.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.HopDong;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

@WebServlet("/hopdong")
public class HopDongServlet extends HttpServlet {

 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "list";
        switch (action) {
            case "them":         showFormThem(request, response);  break;
            case "sua":          suaForm(request, response);       break;
            case "xoa":          xoaHopDong(request, response);    break;
            case "xem":          xemChiTiet(request, response);    break;
            case "doiTrangThai": doiTrangThai(request, response);  break;
            default:             danhSachHopDong(request, response);
        }
    }

   
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "";
        switch (action) {
            case "them": themHopDong(request, response);    break;
            case "sua":  capNhatHopDong(request, response); break;
            default:     response.sendRedirect("hopdong");
        }
    }

    private void danhSachHopDong(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<HopDong> list = new ArrayList<>();

        String keyword   = request.getParameter("keyword");
        String loai      = request.getParameter("loaiHopDong");
        String trangThai = request.getParameter("trangThai");

        StringBuilder sql = new StringBuilder(
            "SELECT hd.*, nv.ho_ten, nv.ma_nhan_vien " +
            "FROM hop_dong hd " +
            "LEFT JOIN nhan_vien nv ON hd.nhan_vien_id = nv.nhan_vien_id " +
            "WHERE 1=1 "
        );
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND (nv.ho_ten LIKE ? OR hd.so_hop_dong LIKE ?) ");
            params.add("%" + keyword.trim() + "%");
            params.add("%" + keyword.trim() + "%");
        }
        if (loai != null && !loai.isEmpty()) {
            sql.append("AND hd.loai_hop_dong = ? ");
            params.add(loai);
        }
        if (trangThai != null && !trangThai.isEmpty()) {
            sql.append("AND hd.trang_thai = ? ");
            params.add(trangThai);
        }
        sql.append("ORDER BY hd.hop_dong_id DESC");

        try {
            Connection conn = DBConnection.layKetNoi();
            PreparedStatement pstt = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) pstt.setObject(i + 1, params.get(i));
            ResultSet rs = pstt.executeQuery();
            list = mapList(rs);
        } catch (Exception e) { e.printStackTrace(); }

        request.setAttribute("list", list);
        request.setAttribute("keyword", keyword);
        request.setAttribute("loaiHopDong", loai);
        request.setAttribute("trangThai", trangThai);
        request.getRequestDispatcher("DanhSachHopDong.jsp").forward(request, response);
    }

    private void showFormThem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("danhSachNhanVien", layDanhSachNhanVien());
        request.getRequestDispatcher("ThemHopDong.jsp").forward(request, response);
    }

 
    private void themHopDong(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            Connection conn = DBConnection.layKetNoi();
            PreparedStatement pstt = conn.prepareStatement(
                "INSERT INTO hop_dong(so_hop_dong, nhan_vien_id, loai_hop_dong, " +
                "ngay_bat_dau, ngay_ket_thuc, luong_co_ban, phu_cap, trang_thai, ghi_chu) " +
                "VALUES(?,?,?,?,?,?,?,?,?)"
            );
            pstt.setString(1, request.getParameter("soHopDong"));
            pstt.setInt(2, Integer.parseInt(request.getParameter("nhanVienId")));
            pstt.setString(3, request.getParameter("loaiHopDong"));
            pstt.setDate(4, Date.valueOf(request.getParameter("ngayBatDau")));
            String ngayKT = request.getParameter("ngayKetThuc");
            pstt.setDate(5, (ngayKT != null && !ngayKT.isEmpty()) ? Date.valueOf(ngayKT) : null);
            pstt.setBigDecimal(6, new BigDecimal(request.getParameter("luongCoBan")));
            pstt.setBigDecimal(7, new BigDecimal(request.getParameter("phuCap")));
            pstt.setString(8, request.getParameter("trangThai"));
            pstt.setString(9, request.getParameter("ghiChu"));
            pstt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        response.sendRedirect("hopdong");
    }


    private void suaForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        HopDong hd = new HopDong();
        try {
            Connection conn = DBConnection.layKetNoi();
            PreparedStatement pstt = conn.prepareStatement(
                "SELECT hd.*, nv.ho_ten, nv.ma_nhan_vien " +
                "FROM hop_dong hd " +
                "LEFT JOIN nhan_vien nv ON hd.nhan_vien_id = nv.nhan_vien_id " +
                "WHERE hd.hop_dong_id = ?"
            );
            pstt.setInt(1, id);
            ResultSet rs = pstt.executeQuery();
            if (rs.next()) hd = mapRow(rs);
        } catch (Exception e) { e.printStackTrace(); }
        request.setAttribute("hd", hd);
        request.setAttribute("danhSachNhanVien", layDanhSachNhanVien());
        request.getRequestDispatcher("SuaHopDong.jsp").forward(request, response);
    }

 
    private void capNhatHopDong(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            Connection conn = DBConnection.layKetNoi();
            PreparedStatement pstt = conn.prepareStatement(
                "UPDATE hop_dong SET so_hop_dong=?, loai_hop_dong=?, ngay_bat_dau=?, " +
                "ngay_ket_thuc=?, luong_co_ban=?, phu_cap=?, trang_thai=?, ghi_chu=? " +
                "WHERE hop_dong_id=?"
            );
            pstt.setString(1, request.getParameter("soHopDong"));
            pstt.setString(2, request.getParameter("loaiHopDong"));
            pstt.setDate(3, Date.valueOf(request.getParameter("ngayBatDau")));
            String ngayKT = request.getParameter("ngayKetThuc");
            pstt.setDate(4, (ngayKT != null && !ngayKT.isEmpty()) ? Date.valueOf(ngayKT) : null);
            pstt.setBigDecimal(5, new BigDecimal(request.getParameter("luongCoBan")));
            pstt.setBigDecimal(6, new BigDecimal(request.getParameter("phuCap")));
            pstt.setString(7, request.getParameter("trangThai"));
            pstt.setString(8, request.getParameter("ghiChu"));
            pstt.setInt(9, Integer.parseInt(request.getParameter("hopDongId")));
            pstt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        response.sendRedirect("hopdong");
    }

   
    private void xoaHopDong(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Connection conn = DBConnection.layKetNoi();
            PreparedStatement pstt = conn.prepareStatement(
                "DELETE FROM hop_dong WHERE hop_dong_id = ?"
            );
            pstt.setInt(1, id);
            pstt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        response.sendRedirect("hopdong");
    }

  
    private void xemChiTiet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        HopDong hd = new HopDong();
        try {
            Connection conn = DBConnection.layKetNoi();
            PreparedStatement pstt = conn.prepareStatement(
                "SELECT hd.*, nv.ho_ten, nv.ma_nhan_vien " +
                "FROM hop_dong hd " +
                "LEFT JOIN nhan_vien nv ON hd.nhan_vien_id = nv.nhan_vien_id " +
                "WHERE hd.hop_dong_id = ?"
            );
            pstt.setInt(1, id);
            ResultSet rs = pstt.executeQuery();
            if (rs.next()) hd = mapRow(rs);
        } catch (Exception e) { e.printStackTrace(); }
        request.setAttribute("hd", hd);
        request.getRequestDispatcher("ChiTietHopDong.jsp").forward(request, response);
    }

  
    private void doiTrangThai(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id           = Integer.parseInt(request.getParameter("id"));
            String trangThai = request.getParameter("trangThai");
            Connection conn  = DBConnection.layKetNoi();
            PreparedStatement pstt = conn.prepareStatement(
                "UPDATE hop_dong SET trang_thai = ? WHERE hop_dong_id = ?"
            );
            pstt.setString(1, trangThai);
            pstt.setInt(2, id);
            pstt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        response.sendRedirect("hopdong");
    }

  
    private List<Map<String, Object>> layDanhSachNhanVien() {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            Connection conn = DBConnection.layKetNoi();
            PreparedStatement pstt = conn.prepareStatement(
                "SELECT nhan_vien_id, ma_nhan_vien, ho_ten FROM nhan_vien ORDER BY ho_ten"
            );
            ResultSet rs = pstt.executeQuery();
            while (rs.next()) {
                Map<String, Object> nv = new HashMap<>();
                nv.put("nhanVienId", rs.getInt("nhan_vien_id"));
                nv.put("maNhanVien", rs.getString("ma_nhan_vien"));
                nv.put("hoTen",      rs.getString("ho_ten"));
                list.add(nv);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
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
        try { hd.setHoTen(rs.getString("ho_ten")); }            catch (SQLException ignored) {}
        try { hd.setMaNhanVien(rs.getString("ma_nhan_vien")); } catch (SQLException ignored) {}
        return hd;
    }

    private List<HopDong> mapList(ResultSet rs) throws SQLException {
        List<HopDong> list = new ArrayList<>();
        while (rs.next()) list.add(mapRow(rs));
        return list;
    }
}

