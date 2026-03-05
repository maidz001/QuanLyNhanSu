package controller;

import ConnDatabase.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.DanhGia;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

@WebServlet("/danhgia")
public class DanhGiaServlet extends HttpServlet {

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "list";
        switch (action) {
            case "them": showFormThem(request, response); break;
            case "sua":  suaForm(request, response);      break;
            case "xoa":  xoaDanhGia(request, response);   break;
            case "xem":  xemChiTiet(request, response);   break;
            default:     danhSachDanhGia(request, response);
        }
    }

   
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "";
        switch (action) {
            case "them": themDanhGia(request, response);    break;
            case "sua":  capNhatDanhGia(request, response); break;
            default:     response.sendRedirect("danhgia");
        }
    }

 
    private void danhSachDanhGia(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<DanhGia> list = new ArrayList<>();

        String keyword  = request.getParameter("keyword");  
        String thangStr = request.getParameter("thang");
        String namStr   = request.getParameter("nam");
        String xepLoai  = request.getParameter("xepLoai");

        StringBuilder sql = new StringBuilder(
            "SELECT dg.*, nv.ho_ten, nd.ho_ten as ten_nguoi_danh_gia " +
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
            for (int i = 0; i < params.size(); i++) pstt.setObject(i + 1, params.get(i));
            ResultSet rs = pstt.executeQuery();
            list = mapList(rs);
        } catch (Exception e) { e.printStackTrace(); }

        request.setAttribute("list", list);
        request.setAttribute("keyword", keyword);
        request.setAttribute("thang", thangStr);
        request.setAttribute("nam", namStr);
        request.setAttribute("xepLoai", xepLoai);
        request.getRequestDispatcher("DanhSachDanhGia.jsp").forward(request, response);
    }

 
    private void showFormThem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("danhSachNhanVien", layDanhSachNhanVien());
        request.getRequestDispatcher("ThemDanhGia.jsp").forward(request, response);
    }

   
    private void themDanhGia(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            Connection conn = DBConnection.layKetNoi();
            PreparedStatement pstt = conn.prepareStatement(
                "INSERT INTO danh_gia(nhan_vien_id, thang, nam, tong_diem, xep_loai, nhan_xet, nguoi_danh_gia) " +
                "VALUES(?,?,?,?,?,?,?)"
            );
            pstt.setInt(1, Integer.parseInt(request.getParameter("nhanVienId")));
            pstt.setInt(2, Integer.parseInt(request.getParameter("thang")));
            pstt.setInt(3, Integer.parseInt(request.getParameter("nam")));
            pstt.setBigDecimal(4, new BigDecimal(request.getParameter("tongDiem")));
            pstt.setString(5, request.getParameter("xepLoai"));
            pstt.setString(6, request.getParameter("nhanXet"));
            pstt.setInt(7, Integer.parseInt(request.getParameter("nguoiDanhGia")));
            pstt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        response.sendRedirect("danhgia");
    }

   
    private void suaForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        DanhGia dg = new DanhGia();
        try {
            Connection conn = DBConnection.layKetNoi();
            PreparedStatement pstt = conn.prepareStatement(
                "SELECT dg.*, nv.ho_ten, nd.ho_ten as ten_nguoi_danh_gia " +
                "FROM danh_gia dg " +
                "LEFT JOIN nhan_vien nv ON dg.nhan_vien_id = nv.nhan_vien_id " +
                "LEFT JOIN nhan_vien nd ON dg.nguoi_danh_gia = nd.nhan_vien_id " +
                "WHERE dg.danh_gia_id = ?"
            );
            pstt.setInt(1, id);
            ResultSet rs = pstt.executeQuery();
            if (rs.next()) dg = mapRow(rs);
        } catch (Exception e) { e.printStackTrace(); }
        request.setAttribute("dg", dg);
        request.setAttribute("danhSachNhanVien", layDanhSachNhanVien());
        request.getRequestDispatcher("SuaDanhGia.jsp").forward(request, response);
    }

 
    private void capNhatDanhGia(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            Connection conn = DBConnection.layKetNoi();
            PreparedStatement pstt = conn.prepareStatement(
                "UPDATE danh_gia SET tong_diem=?, xep_loai=?, nhan_xet=? WHERE danh_gia_id=?"
            );
            pstt.setBigDecimal(1, new BigDecimal(request.getParameter("tongDiem")));
            pstt.setString(2, request.getParameter("xepLoai"));
            pstt.setString(3, request.getParameter("nhanXet"));
            pstt.setInt(4, Integer.parseInt(request.getParameter("danhGiaId")));
            pstt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        response.sendRedirect("danhgia");
    }

   
    private void xoaDanhGia(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Connection conn = DBConnection.layKetNoi();
            PreparedStatement pstt = conn.prepareStatement(
                "DELETE FROM danh_gia WHERE danh_gia_id = ?"
            );
            pstt.setInt(1, id);
            pstt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        response.sendRedirect("danhgia");
    }

    private void xemChiTiet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        DanhGia dg = new DanhGia();
        try {
            Connection conn = DBConnection.layKetNoi();
            PreparedStatement pstt = conn.prepareStatement(
                "SELECT dg.*, nv.ho_ten, nd.ho_ten as ten_nguoi_danh_gia " +
                "FROM danh_gia dg " +
                "LEFT JOIN nhan_vien nv ON dg.nhan_vien_id = nv.nhan_vien_id " +
                "LEFT JOIN nhan_vien nd ON dg.nguoi_danh_gia = nd.nhan_vien_id " +
                "WHERE dg.danh_gia_id = ?"
            );
            pstt.setInt(1, id);
            ResultSet rs = pstt.executeQuery();
            if (rs.next()) dg = mapRow(rs);
        } catch (Exception e) { e.printStackTrace(); }
        request.setAttribute("dg", dg);
        request.getRequestDispatcher("ChiTietDanhGia.jsp").forward(request, response);
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
        try { dg.setHoTen(rs.getString("ho_ten")); }                        catch (SQLException ignored) {}
        try { dg.setTenNguoiDanhGia(rs.getString("ten_nguoi_danh_gia")); }  catch (SQLException ignored) {}
        return dg;
    }

    private List<DanhGia> mapList(ResultSet rs) throws SQLException {
        List<DanhGia> list = new ArrayList<>();
        while (rs.next()) list.add(mapRow(rs));
        return list;
    }
}

