package controller;

import ConnDatabase.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.ChucVu;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/chucvu")
public class ChucVuServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "xoa":
                xoaChucVu(request, response);
                break;
            case "xem":
                xemChiTiet(request, response);
                break;
            case "sua":
                moFormSua(request, response);
                break;
            default:
                danhSachChucVu(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "them":
                themChucVu(request, response);
                break;
            case "capnhat":
                capNhatChucVu(request, response);
                break;
        }
    }

    // ===================== DANH SÁCH =====================

    private void danhSachChucVu(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<ChucVu> ds = new ArrayList<>();

        try {

            Connection conn = DBConnection.layKetNoi();

            PreparedStatement pst = conn.prepareStatement(
                    "select * from chuc_vu"
            );

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                ds.add(mapRow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("dsChucVu", ds);

        request.getRequestDispatcher("/WEB-INF/view/chucvuview/ChucVuList.jsp")
                .forward(request, response);
    }

    // ===================== THÊM =====================

    private void themChucVu(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {

            Connection conn = DBConnection.layKetNoi();

            PreparedStatement pst = conn.prepareStatement(
                    "insert into chuc_vu(ma_chuc_vu,ten_chuc_vu,cap_bac,luong_co_ban,mo_ta,trang_thai) values(?,?,?,?,?,?)"
            );

            pst.setString(1, request.getParameter("maChucVu"));
            pst.setString(2, request.getParameter("tenChucVu"));
            pst.setInt(3, Integer.parseInt(request.getParameter("capBac")));
            pst.setBigDecimal(4, new java.math.BigDecimal(request.getParameter("luongCoBan")));
            pst.setString(5, request.getParameter("moTa"));
            pst.setInt(6, Integer.parseInt(request.getParameter("trangThai")));

            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("chucvu");
    }

    // ===================== CẬP NHẬT =====================

    private void capNhatChucVu(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {

            Connection conn = DBConnection.layKetNoi();

            PreparedStatement pst = conn.prepareStatement(
                    "update chuc_vu set ma_chuc_vu=?,ten_chuc_vu=?,cap_bac=?,luong_co_ban=?,mo_ta=?,trang_thai=? where chuc_vu_id=?"
            );

            pst.setString(1, request.getParameter("maChucVu"));
            pst.setString(2, request.getParameter("tenChucVu"));
            pst.setInt(3, Integer.parseInt(request.getParameter("capBac")));
            pst.setBigDecimal(4, new java.math.BigDecimal(request.getParameter("luongCoBan")));
            pst.setString(5, request.getParameter("moTa"));
            pst.setInt(6, Integer.parseInt(request.getParameter("trangThai")));
            pst.setInt(7, Integer.parseInt(request.getParameter("chucVuId")));

            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("chucvu");
    }

    // ===================== XÓA =====================

    private void xoaChucVu(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {

            int id = Integer.parseInt(request.getParameter("id"));

            Connection conn = DBConnection.layKetNoi();

            PreparedStatement pst = conn.prepareStatement(
                    "delete from chuc_vu where chuc_vu_id=?"
            );

            pst.setInt(1, id);

            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("chucvu");
    }

    // ===================== XEM CHI TIẾT =====================

    private void xemChiTiet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        ChucVu cv = null;

        try {

            Connection conn = DBConnection.layKetNoi();

            PreparedStatement pst = conn.prepareStatement(
                    "select * from chuc_vu where chuc_vu_id=?"
            );

            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                cv = mapRow(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("cv", cv);

        request.getRequestDispatcher("/WEB-INF/view/chucvuview/ChucVuForm.jsp")
                .forward(request, response);
    }

    // ===================== FORM SỬA =====================

    private void moFormSua(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        xemChiTiet(request, response);
    }

    // ===================== MAP DATA =====================

    private ChucVu mapRow(ResultSet rs) throws SQLException {

        ChucVu cv = new ChucVu();

        cv.setChucVuId(rs.getInt("chuc_vu_id"));
        cv.setMaChucVu(rs.getString("ma_chuc_vu"));
        cv.setTenChucVu(rs.getString("ten_chuc_vu"));
        cv.setCapBac(rs.getInt("cap_bac"));
        cv.setluongCoBan(rs.getBigDecimal("luong_co_ban"));
        cv.setMoTa(rs.getString("mo_ta"));
        cv.setTrangThai(rs.getInt("trang_thai"));

        return cv;
    }
}