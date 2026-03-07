package controller;

import model.DanhGia;
import service.DanhGiaService;
import service.NhanVienService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/danhgia")
public class DanhGiaServlet extends HttpServlet {

    private DanhGiaService danhGiaService = new DanhGiaService();
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
                xoaDanhGia(request, response);
                break;

            case "xem":
                xemChiTiet(request, response);
                break;

            default:
                danhSachDanhGia(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {

            case "them":
                themDanhGia(request, response);
                break;

            case "sua":
                capNhatDanhGia(request, response);
                break;

            default:
                response.sendRedirect("danhgia");
        }
    }

    // ================= DANH SÁCH =================

    private void danhSachDanhGia(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        String thang = request.getParameter("thang");
        String nam = request.getParameter("nam");
        String xepLoai = request.getParameter("xepLoai");

        List<DanhGia> list = danhGiaService.searchDanhGia(keyword, thang, nam, xepLoai);

        request.setAttribute("list", list);
        request.setAttribute("keyword", keyword);
        request.setAttribute("thang", thang);
        request.setAttribute("nam", nam);
        request.setAttribute("xepLoai", xepLoai);

        request.getRequestDispatcher("DanhSachDanhGia.jsp").forward(request, response);
    }

    // ================= FORM THÊM =================

    private void showFormThem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("danhSachNhanVien", nhanVienService.layTatCa());

        request.getRequestDispatcher("ThemDanhGia.jsp").forward(request, response);
    }

    // ================= THÊM =================

    private void themDanhGia(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        DanhGia dg = new DanhGia();

        dg.setNhanVienId(Integer.parseInt(request.getParameter("nhanVienId")));
        dg.setThang(Integer.parseInt(request.getParameter("thang")));
        dg.setNam(Integer.parseInt(request.getParameter("nam")));
        dg.setTongDiem(new BigDecimal(request.getParameter("tongDiem")));
        dg.setXepLoai(request.getParameter("xepLoai"));
        dg.setNhanXet(request.getParameter("nhanXet"));
        dg.setNguoiDanhGia(Integer.parseInt(request.getParameter("nguoiDanhGia")));

        danhGiaService.them(dg);

        response.sendRedirect("danhgia");
    }

    // ================= FORM SỬA =================

    private void suaForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        DanhGia dg = danhGiaService.layTheoId(id);

        request.setAttribute("dg", dg);
        request.setAttribute("danhSachNhanVien", nhanVienService.layTatCa());

        request.getRequestDispatcher("SuaDanhGia.jsp").forward(request, response);
    }

    // ================= CẬP NHẬT =================

    private void capNhatDanhGia(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        DanhGia dg = new DanhGia();

        dg.setDanhGiaId(Integer.parseInt(request.getParameter("danhGiaId")));
        dg.setTongDiem(new BigDecimal(request.getParameter("tongDiem")));
        dg.setXepLoai(request.getParameter("xepLoai"));
        dg.setNhanXet(request.getParameter("nhanXet"));

        danhGiaService.sua(dg);

        response.sendRedirect("danhgia");
    }

    // ================= XÓA =================

    private void xoaDanhGia(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        danhGiaService.xoa(id);

        response.sendRedirect("danhgia");
    }

    // ================= XEM CHI TIẾT =================

    private void xemChiTiet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        DanhGia dg = danhGiaService.layTheoId(id);

        request.setAttribute("dg", dg);

        request.getRequestDispatcher("ChiTietDanhGia.jsp").forward(request, response);
    }
}