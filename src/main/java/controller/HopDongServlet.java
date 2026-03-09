package controller;

import model.HopDong;
import model.NhanVien;
import model.TaiKhoan;
import service.HopDongService;
import service.NhanVienService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/hopdong")
public class HopDongServlet extends HttpServlet {

    private HopDongService hopDongService = new HopDongService();
    private NhanVienService nhanVienService=new NhanVienService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {

            case "them":
                showFormThem(request, response);
                break;

            case "sua":
                suaForm(request, response);
                break;

            case "xoa":
                xoaHopDong(request, response);
                break;

            case "xem":
                xemChiTiet(request, response);
                break;

            case "doiTrangThai":
                doiTrangThai(request, response);
                break;

            default:
                danhSachHopDong(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {

            case "them":
                themHopDong(request, response);
                break;

            case "sua":
                capNhatHopDong(request, response);
                break;

            default:
                response.sendRedirect("hopdong");
        }
    }

    // ================= DANH SÁCH =================

    private void danhSachHopDong(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        String loai = request.getParameter("loaiHopDong");
        String trangThai = request.getParameter("trangThai");

        List<HopDong> list = hopDongService.searchHopDong(keyword, loai, trangThai);

        request.setAttribute("list", list);
        request.setAttribute("keyword", keyword);
        request.setAttribute("loaiHopDong", loai);
        request.setAttribute("trangThai", trangThai);

        request.getRequestDispatcher("DanhSachHopDong.jsp").forward(request, response);
    }

    // ================= FORM THÊM =================

    private void showFormThem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("danhSachNhanVien", nhanVienService.layTatCa());

        request.getRequestDispatcher("ThemHopDong.jsp").forward(request, response);
    }

    // ================= THÊM =================

    private void themHopDong(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HopDong hd = new HopDong();

        hd.setSoHopDong(request.getParameter("soHopDong"));
        hd.setNhanVienId(Integer.parseInt(request.getParameter("nhanVienId")));
        hd.setLoaiHopDong(request.getParameter("loaiHopDong"));
        hd.setNgayBatDau(java.sql.Date.valueOf(request.getParameter("ngayBatDau")));

        String ngayKT = request.getParameter("ngayKetThuc");
        if (ngayKT != null && !ngayKT.isEmpty()) {
            hd.setNgayKetThuc(java.sql.Date.valueOf(ngayKT));
        }

        hd.setLuongCoBan(new BigDecimal(request.getParameter("luongCoBan")));
        hd.setPhuCap(new BigDecimal(request.getParameter("phuCap")));
        hd.setTrangThai(request.getParameter("trangThai"));
        hd.setGhiChu(request.getParameter("ghiChu"));

        hopDongService.them(hd,getSS(request,response).getNhanVienId());

        response.sendRedirect("hopdong");
    }

    // ================= FORM SỬA =================

    private void suaForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        HopDong hd = hopDongService.layTheoId(id);

        request.setAttribute("hd", hd);
        request.setAttribute("danhSachNhanVien", nhanVienService.layTatCa());

        request.getRequestDispatcher("SuaHopDong.jsp").forward(request, response);
    }

    // ================= CẬP NHẬT =================

    private void capNhatHopDong(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HopDong hd = new HopDong();

        hd.setHopDongId(Integer.parseInt(request.getParameter("hopDongId")));
        hd.setSoHopDong(request.getParameter("soHopDong"));
        hd.setLoaiHopDong(request.getParameter("loaiHopDong"));
        hd.setNgayBatDau(java.sql.Date.valueOf(request.getParameter("ngayBatDau")));

        String ngayKT = request.getParameter("ngayKetThuc");
        if (ngayKT != null && !ngayKT.isEmpty()) {
            hd.setNgayKetThuc(java.sql.Date.valueOf(ngayKT));
        }

        hd.setLuongCoBan(new BigDecimal(request.getParameter("luongCoBan")));
        hd.setPhuCap(new BigDecimal(request.getParameter("phuCap")));
        hd.setTrangThai(request.getParameter("trangThai"));
        hd.setGhiChu(request.getParameter("ghiChu"));
        HttpSession session=(HttpSession) request.getSession(false);
        TaiKhoan tk= (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
        hopDongService.sua(hd,tk.getNhanVienId());

        response.sendRedirect("hopdong");
    }

    // ================= XÓA =================

    private void xoaHopDong(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        hopDongService.xoa(id);

        response.sendRedirect("hopdong");
    }

    // ================= CHI TIẾT =================

    private void xemChiTiet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        HopDong hd = hopDongService.layTheoId(id);

        request.setAttribute("hd", hd);

        request.getRequestDispatcher("ChiTietHopDong.jsp").forward(request, response);
    }

    // ================= ĐỔI TRẠNG THÁI =================

    private void doiTrangThai(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String trangThai = request.getParameter("trangThai");

        hopDongService.sua(hopDongService.layTheoId(id),getSS(request,response).getNhanVienId() );

        response.sendRedirect("hopdong");
    }
    private TaiKhoan getSS(HttpServletRequest request, HttpServletResponse response){
        HttpSession session=(HttpSession) request.getSession(false);
        TaiKhoan tk= (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
        return tk;
    }
}