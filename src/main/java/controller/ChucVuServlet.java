package controller;

import model.ChucVu;
import service.ChucVuService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/chucvu")
public class ChucVuServlet extends HttpServlet {

    private ChucVuService chucVuService = new ChucVuService();

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

    // ================= DANH SÁCH =================

    private void danhSachChucVu(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<ChucVu> ds = chucVuService.layTatCa();

        request.setAttribute("dsChucVu", ds);

        request.getRequestDispatcher("/WEB-INF/view/chucvuview/ChucVuList.jsp")
                .forward(request, response);
    }

    // ================= THÊM =================

    private void themChucVu(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        ChucVu cv = new ChucVu();

        cv.setMaChucVu(request.getParameter("maChucVu"));
        cv.setTenChucVu(request.getParameter("tenChucVu"));
        cv.setCapBac(Integer.parseInt(request.getParameter("capBac")));
        cv.setluongCoBan(new java.math.BigDecimal(request.getParameter("luongCoBan")));
        cv.setMoTa(request.getParameter("moTa"));
        cv.setTrangThai(Integer.parseInt(request.getParameter("trangThai")));

        chucVuService.them(cv);

        response.sendRedirect("chucvu");
    }

    // ================= CẬP NHẬT =================

    private void capNhatChucVu(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        ChucVu cv = new ChucVu();

        cv.setChucVuId(Integer.parseInt(request.getParameter("chucVuId")));
        cv.setMaChucVu(request.getParameter("maChucVu"));
        cv.setTenChucVu(request.getParameter("tenChucVu"));
        cv.setCapBac(Integer.parseInt(request.getParameter("capBac")));
        cv.setluongCoBan(new java.math.BigDecimal(request.getParameter("luongCoBan")));
        cv.setMoTa(request.getParameter("moTa"));
        cv.setTrangThai(Integer.parseInt(request.getParameter("trangThai")));

        chucVuService.sua(cv);

        response.sendRedirect("chucvu");
    }

    // ================= XÓA =================

    private void xoaChucVu(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        chucVuService.xoa(id);

        response.sendRedirect("chucvu");
    }

    // ================= XEM CHI TIẾT =================

    private void xemChiTiet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        ChucVu cv = chucVuService.layTheoId(id);

        request.setAttribute("cv", cv);

        request.getRequestDispatcher("/WEB-INF/view/chucvuview/ChucVuForm.jsp")
                .forward(request, response);
    }

    // ================= FORM SỬA =================

    private void moFormSua(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        xemChiTiet(request, response);
    }
}