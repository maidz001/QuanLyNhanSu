package controller;

import model.ChamCong;
import service.ChamCongService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/chamcong")
public class ChamCongServlet extends HttpServlet {

    private ChamCongService chamCongService = new ChamCongService();

    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        String action=request.getParameter("action");
        if(action==null) action="";

        switch(action){

            case "sua":
                formSua(request,response);
                break;

            case "xoa":
                xoaChamCong(request,response);
                break;

            default:
                danhSachChamCong(request,response);
        }
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action=request.getParameter("action");

        switch(action){

            case "them":
                themChamCong(request,response);
                break;

            case "capnhat":
                capNhatChamCong(request,response);
                break;
        }
    }

    // ================= DANH SÁCH =================

    private void danhSachChamCong(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        List<ChamCong> list = chamCongService.layTatCa();

        request.setAttribute("dsChamCong",list);

        request.getRequestDispatcher("chamcong-list.jsp")
                .forward(request,response);
    }

    // ================= THÊM =================

    private void themChamCong(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        ChamCong cc=new ChamCong();

        cc.setNhanVienId(Integer.parseInt(request.getParameter("nhanVienId")));
        cc.setNgayChamCong(java.sql.Date.valueOf(request.getParameter("ngay")));
        cc.setGioVao(request.getParameter("gioVao"));
        cc.setGioRa(request.getParameter("gioRa"));
        cc.setTrangThai(request.getParameter("trangThai"));
        cc.setGhiChu(request.getParameter("ghiChu"));

        chamCongService.them(cc);

        response.sendRedirect("chamcong");
    }

    // ================= SỬA =================

    private void formSua(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        int id=Integer.parseInt(request.getParameter("id"));

        ChamCong cc=chamCongService.layTheoId(id);

        request.setAttribute("chamCong",cc);

        request.getRequestDispatcher("chamcong-form.jsp")
                .forward(request,response);
    }

    // ================= CẬP NHẬT =================

    private void capNhatChamCong(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        ChamCong cc=new ChamCong();

        cc.setChamCongId(Integer.parseInt(request.getParameter("chamCongId")));
        cc.setNhanVienId(Integer.parseInt(request.getParameter("nhanVienId")));
        cc.setNgayChamCong(java.sql.Date.valueOf(request.getParameter("ngay")));
        cc.setGioVao(request.getParameter("gioVao"));
        cc.setGioRa(request.getParameter("gioRa"));
        cc.setTrangThai(request.getParameter("trangThai"));
        cc.setGhiChu(request.getParameter("ghiChu"));

        chamCongService.sua(cc);

        response.sendRedirect("chamcong");
    }

    // ================= XÓA =================

    private void xoaChamCong(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        int id=Integer.parseInt(request.getParameter("id"));

        chamCongService.xoa(id);

        response.sendRedirect("chamcong");
    }
}