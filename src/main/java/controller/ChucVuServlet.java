package controller;

import model.ChucVu;
import model.TaiKhoan;
import service.ChucVuService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/chucvu")
public class ChucVuServlet extends HttpServlet {
    private TaiKhoanServlet taiKhoanServlet=new TaiKhoanServlet();

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
            case "kichhoat":kichHoatCV(request,response);
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
            throws IOException, ServletException {

        ChucVu cv = new ChucVu();

        cv.setMaChucVu(request.getParameter("maChucVu"));
        cv.setTenChucVu(request.getParameter("tenChucVu"));

        String capBac = request.getParameter("capBac");
        if (capBac != null && !capBac.isEmpty())
            cv.setCapBac(Integer.parseInt(capBac));

        String luongCoBan = request.getParameter("luongCoBanMin");
        if (luongCoBan != null && !luongCoBan.isEmpty())
            cv.setluongCoBan(new BigDecimal(luongCoBan));

        cv.setMoTa(request.getParameter("moTa"));
        cv.setTrangThai(1);

        chucVuService.them(cv);
        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));


    }

    // ================= CẬP NHẬT =================



    // ================= XÓA =================

    private void xoaChucVu(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        int id = Integer.parseInt(request.getParameter("id"));

        chucVuService.xoa(id);

        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }
    private void kichHoatCV(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        int id = Integer.parseInt(request.getParameter("id"));

        chucVuService.kichHoat(id);

        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
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
        int id = Integer.parseInt(request.getParameter("id"));
        ChucVu cv = chucVuService.layTheoId(id);
        request.setAttribute("chucvu", cv);
        request.getRequestDispatcher("/WEB-INF/view/chucvuview/SuaChucVu.jsp").forward(request, response);
    }

    private void capNhatChucVu(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        ChucVu cv = chucVuService.layTheoId(id);

        cv.setTenChucVu(request.getParameter("tenChucVu"));

        String capBac = request.getParameter("capBac");
        if (capBac != null && !capBac.isEmpty())
            cv.setCapBac(Integer.parseInt(capBac));

        String luongCoBan = request.getParameter("luongCoBan");
        if (luongCoBan != null && !luongCoBan.isEmpty())
            cv.setluongCoBan(new BigDecimal(luongCoBan));

        cv.setMoTa(request.getParameter("moTa"));
        cv.setTrangThai(Integer.parseInt(request.getParameter("trangThai")));

        chucVuService.sua(cv);
        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }
    private TaiKhoan getSS(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        return (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
    }
}