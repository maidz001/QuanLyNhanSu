package controller;

import model.TaiKhoan;
import model.ThongBao;
import service.TaiKhoanService;
import service.ThongBaoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/thongbao")
public class ThongBaoServlet extends HttpServlet {
    private TaiKhoanServlet taiKhoanServlet=new TaiKhoanServlet();

    private ThongBaoService thongBaoService = new ThongBaoService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {

            case "danhdaudadoc":
                danhDauDaDocById(request, response);
                break;

            case "danhdautatcaladadoc":
                danhDauTatCaLaDaDoc(request, response);
                break;
            case "xoatatcathongbaodadoc":
                xoaTatCaThongBaoDaDoc(request, response);
                break;


            default:
                danhSachThongBao(request, response);
        }
    }

    // ================= DANH SÁCH =================

    private void danhSachThongBao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int nguoiNhanId = (int) request.getSession(false).getAttribute("nhanVienId");

        List<ThongBao> list = thongBaoService.layTheoNguoiNhan(nguoiNhanId);

        int chuaDoc = thongBaoService.demChuaDoc(nguoiNhanId);

        request.setAttribute("list", list);
        request.setAttribute("chuaDoc", chuaDoc);

        request.getRequestDispatcher("DanhSachThongBao.jsp")
                .forward(request, response);
    }

    // ================= CHI TIẾT =================

    private void xemChiTiet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        ThongBao tb = thongBaoService.layTheoId(id);

        thongBaoService.danhDauDaDoc(id);

        request.setAttribute("tb", tb);

        request.getRequestDispatcher("ChiTietThongBao.jsp")
                .forward(request, response);
    }

    // ================= ĐỌC TẤT CẢ =================

    private void docTatCa(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int nguoiNhanId = (int) request.getSession(false).getAttribute("nhanVienId");

        thongBaoService.docTatCa(nguoiNhanId);

        response.sendRedirect("thongbao");
    }
    private void danhDauDaDocById(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        TaiKhoan tk = (TaiKhoan) request.getSession(false).getAttribute("taiKhoanDangDangNhap");
        int thongBaoId = Integer.parseInt(request.getParameter("id"));

        thongBaoService.danhDauDaDoc(thongBaoId);
        taiKhoanServlet.goiDangNhapChoNV(request,response,tk);
    }
    private void danhDauTatCaLaDaDoc(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        TaiKhoan tk = (TaiKhoan) request.getSession(false).getAttribute("taiKhoanDangDangNhap");
        int thongBaoId = Integer.parseInt(request.getParameter("nhanVienId"));

        thongBaoService.docTatCa(thongBaoId);
        taiKhoanServlet.goiDangNhapChoNV(request,response,tk);
    }
    private void xoaTatCaThongBaoDaDoc(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        TaiKhoan tk = (TaiKhoan) request.getSession(false).getAttribute("taiKhoanDangDangNhap");
        int id = Integer.parseInt(request.getParameter("nhanVienId"));

        thongBaoService.xoaTatCaThongBaoDaDocChoNhanVien(id);
        request.setAttribute("message","Xóa thành công");
        taiKhoanServlet.goiDangNhapChoNV(request,response,tk);
    }
}