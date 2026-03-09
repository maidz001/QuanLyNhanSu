package controller;

import model.ChamCong;
import service.ChamCongService;
import service.TaiKhoanService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/chamcong")
public class ChamCongServlet extends HttpServlet {
    private TaiKhoanService taiKhoanService=new TaiKhoanService();
private  ChamCongService chamCongService=new ChamCongService();
    private final TaiKhoanServlet taiKhoanServlet=new TaiKhoanServlet();

    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        String action=request.getParameter("action");
        if(action==null) action="";

        switch(action){


            case "xoa":
                xoaChamCong(request,response);
                break;

            case "checkin":
                checkIn(request,response);
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
            case "checkin":
                checkIn(request,response);
                break;
            case "checkout":
                checkOut(request,response);
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



    // ================= SỬA =================







    // ================= XÓA =================

    private void xoaChamCong(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        int id=Integer.parseInt(request.getParameter("id"));

        chamCongService.xoa(id);

        response.sendRedirect("chamcong");
    }
    private void checkIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id=Integer.parseInt(request.getParameter("idNhanVien"));
        if(!chamCongService.checkIn(id)){
            request.setAttribute("message","Hôm nay đã CheckIn, vui lòng không spam");
        }
        else
            request.setAttribute("message","CheckIn Thành Công");
        taiKhoanServlet.goiDangNhapChoNV(request,response,taiKhoanService.layTheoId(id));
    }
    private void checkOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id=Integer.parseInt(request.getParameter("idNhanVien"));
        if(!chamCongService.checkOut(id)){
            request.setAttribute("message","Hôm nay đã CheckOut, vui lòng không spam");
        }
        else
            request.setAttribute("message","CheckOut Thành Công");
        taiKhoanServlet.goiDangNhapChoNV(request,response,taiKhoanService.layTheoId(id));
    }

}