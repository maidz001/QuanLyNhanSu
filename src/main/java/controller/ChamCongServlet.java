package controller;

import model.ChamCong;
import model.TaiKhoan;
import model.ThongBao;
import service.ChamCongService;
import service.NhanVienService;
import service.TaiKhoanService;
import service.ThongBaoService;
import until.XuatExcel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/chamcong")
public class ChamCongServlet extends HttpServlet {
    private XuatExcel xuatExcel=new XuatExcel();
    private ThongBaoService thongBaoService=new ThongBaoService();
    NhanVienService nhanVienService=new NhanVienService();
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
            case "xuatexcel":xuatExcel(request,response);
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


    private void xoaChamCong(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        int id=Integer.parseInt(request.getParameter("id"));

        chamCongService.xoa(id);

        response.sendRedirect("chamcong");
    }
    private void checkIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id=Integer.parseInt(request.getParameter("idNhanVien"));
        if(getSS(request,response).getNhanVienId()!=id){
            request.setAttribute("message","không được phép checkin hộ");
            taiKhoanServlet.goiDangNhapChoNV(request,response,getSS(request,response));
        }
        if(!chamCongService.checkIn(id)){
            request.setAttribute("message","Hôm nay đã CheckIn, vui lòng không spam");
        }
        else {
            request.setAttribute("message", "CheckIn Thành Công");
            LocalDate now=LocalDate.now();
            thongBaoService.them(new ThongBao(0,getSS(request,response).getNhanVienId(),id,"CheckIn","Buổi sáng vui vẻ nhé bạn yêu dấu","Check In thành công",0, Date.valueOf(now)));
        }
        taiKhoanServlet.goiDangNhapChoNV(request,response,taiKhoanService.layTheoId(id));
    }
    private void checkOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id=Integer.parseInt(request.getParameter("idNhanVien"));
        if(getSS(request,response).getNhanVienId()!=id){
            request.setAttribute("message","không được phép checkout hộ");
            taiKhoanServlet.goiDangNhapChoNV(request,response,getSS(request,response));
        }
        if(!chamCongService.checkOut(id)){
            request.setAttribute("message","Hôm nay đã CheckOut, vui lòng không spam");
        }
        else {
            request.setAttribute("message", "CheckOut Thành Công");
            LocalDate now=LocalDate.now();
            thongBaoService.them(new ThongBao(0,getSS(request,response).getNhanVienId(),id,"Check Out","Cảm ơn vì 1 ngày đóng góp cho công ty hết mình nhé bạn yêu.","Check Out thành công",0, Date.valueOf(now)));
        }
        taiKhoanServlet.goiDangNhapChoNV(request,response,taiKhoanService.layTheoId(id));
    }
    private TaiKhoan getSS(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        return (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
    }
    private void xuatExcel(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }

}