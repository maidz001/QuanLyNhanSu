package controller;

import ConnDatabase.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.ThongBao;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/thongbao")
public class ThongBaoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";
        switch (action) {
            case "xem":      xemChiTiet(request, response);   break;
            case "docTatCa": docTatCa(request, response);     break;
            default:         danhSachThongBao(request, response);
        }
    }

    private void danhSachThongBao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ThongBao> list = new ArrayList<>();
        int chuaDoc = 0;
        try {
            int nguoiNhanId = (int) request.getSession(false).getAttribute("nhanVienId");
            Connection conn = DBConnection.layKetNoi();

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT tb.*, ng.ho_ten as ten_nguoi_gui FROM thong_bao tb " +
                            "LEFT JOIN nhan_vien ng ON tb.nguoi_gui=ng.nhan_vien_id " +
                            "WHERE tb.nguoi_nhan=? ORDER BY tb.ngay_tao DESC");
            ps.setInt(1, nguoiNhanId);
            list = mapList(ps.executeQuery());

            PreparedStatement psCnt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM thong_bao WHERE nguoi_nhan=? AND da_doc=0");
            psCnt.setInt(1, nguoiNhanId);
            ResultSet rsCnt = psCnt.executeQuery();
            if (rsCnt.next()) chuaDoc = rsCnt.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        request.setAttribute("list", list);
        request.setAttribute("chuaDoc", chuaDoc);
        request.getRequestDispatcher("DanhSachThongBao.jsp").forward(request, response);
    }

    private void xemChiTiet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ThongBao tb = new ThongBao();
        try {
            Connection conn = DBConnection.layKetNoi();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT tb.*, ng.ho_ten as ten_nguoi_gui FROM thong_bao tb " +
                            "LEFT JOIN nhan_vien ng ON tb.nguoi_gui=ng.nhan_vien_id " +
                            "WHERE tb.thong_bao_id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) tb = mapRow(rs);

            PreparedStatement psUpd = conn.prepareStatement(
                    "UPDATE thong_bao SET da_doc=1 WHERE thong_bao_id=?");
            psUpd.setInt(1, id);
            psUpd.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        request.setAttribute("tb", tb);
        request.getRequestDispatcher("ChiTietThongBao.jsp").forward(request, response);
    }

    private void docTatCa(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int nguoiNhanId = (int) request.getSession(false).getAttribute("nhanVienId");
            Connection conn = DBConnection.layKetNoi();
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE thong_bao SET da_doc=1 WHERE nguoi_nhan=?");
            ps.setInt(1, nguoiNhanId);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        response.sendRedirect("thongbao");
    }

    private ThongBao mapRow(ResultSet rs) throws SQLException {
        ThongBao tb = new ThongBao();
        tb.setThongBaoId(rs.getInt("thong_bao_id"));
        tb.setNguoiGui((Integer) rs.getObject("nguoi_gui"));
        tb.setNguoiNhan((Integer) rs.getObject("nguoi_nhan"));
        tb.setTieuDe(rs.getString("tieu_de"));
        tb.setNoiDung(rs.getString("noi_dung"));
        tb.setLoai(rs.getString("loai"));
        tb.setDaDoc(rs.getInt("da_doc"));
        tb.setNgayTao(rs.getTimestamp("ngay_tao"));
        try { tb.setTenNguoiGui(rs.getString("ten_nguoi_gui")); } catch (SQLException ignored) {}
        return tb;
    }

    private List<ThongBao> mapList(ResultSet rs) throws SQLException {
        List<ThongBao> list = new ArrayList<>();
        while (rs.next()) list.add(mapRow(rs));
        return list;
    }
}
