package controller;

import model.PhongBan;
import service.PhongBanService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/phongban")
public class PhongBanServlet extends HttpServlet {

    private PhongBanService phongBanService = new PhongBanService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if(action == null) action = "";

        switch(action){
            case "xoa": xoaPhongBan(request,response); break;
            case "xem": xemChiTiet(request,response); break;
            case "sua": moFormSua(request,response); break;
            default: danhSachPhongBan(request,response);
        }
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if(action == null) action = "";

        switch(action){
            case "them": themPhongBan(request,response); break;
            case "capnhat": capNhatPhongBan(request,response); break;
        }
    }

    // ================= DANH SÁCH =================

    private void danhSachPhongBan(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        List<PhongBan> ds = phongBanService.layTatCa();

        request.setAttribute("dsPhongBan",ds);

        request.getRequestDispatcher("phongban-list.jsp")
                .forward(request,response);
    }

    // ================= THÊM =================

    private void themPhongBan(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        PhongBan pb = new PhongBan();

        pb.setMaPhongBan(request.getParameter("maPhongBan"));
        pb.setTenPhongBan(request.getParameter("tenPhongBan"));

        String soLuong = request.getParameter("soLuong");
        if(soLuong != null && !soLuong.isEmpty())
            pb.setSoLuong(Integer.parseInt(soLuong));

        String truongPhong = request.getParameter("truongPhongId");
        if(truongPhong != null && !truongPhong.isEmpty())
            pb.setTruongPhongId(Integer.parseInt(truongPhong));

        pb.setMoTa(request.getParameter("moTa"));
        pb.setTrangThai(Integer.parseInt(request.getParameter("trangThai")));

        phongBanService.them(pb);

        response.sendRedirect("phongban");
    }

    // ================= CẬP NHẬT =================

    private void capNhatPhongBan(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        PhongBan pb = new PhongBan();

        pb.setPhongBanId(Integer.parseInt(request.getParameter("phongBanId")));
        pb.setMaPhongBan(request.getParameter("maPhongBan"));
        pb.setTenPhongBan(request.getParameter("tenPhongBan"));

        String soLuong = request.getParameter("soLuong");
        if(soLuong != null && !soLuong.isEmpty())
            pb.setSoLuong(Integer.parseInt(soLuong));

        String truongPhong = request.getParameter("truongPhongId");
        if(truongPhong != null && !truongPhong.isEmpty())
            pb.setTruongPhongId(Integer.parseInt(truongPhong));

        pb.setMoTa(request.getParameter("moTa"));
        pb.setTrangThai(Integer.parseInt(request.getParameter("trangThai")));

        phongBanService.sua(pb);

        response.sendRedirect("phongban");
    }

    // ================= XÓA =================

    private void xoaPhongBan(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        phongBanService.xoa(id);

        response.sendRedirect("phongban");
    }

    // ================= XEM CHI TIẾT =================

    private void xemChiTiet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        PhongBan pb = phongBanService.layTheoId(id);

        request.setAttribute("phongBan",pb);

        request.getRequestDispatcher("phongban-form.jsp")
                .forward(request,response);
    }

    // ================= FORM SỬA =================

    private void moFormSua(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        xemChiTiet(request,response);
    }
}