package controller;

import dao.BangLuongDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.BangLuong;
import service.BangLuongService;
import service.NhanVienService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/bangluong")
public class BangLuongServlet extends HttpServlet {
    NhanVienService nhanVienService=new NhanVienService();
    BangLuongService bangLuongService = new BangLuongService();

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{

        request.setCharacterEncoding("UTF-8");

        String action=request.getParameter("action");
        if(action==null) action="list";

        switch(action){

            case "them":
                showFormThem(request,response);
                break;

            case "sua":
                suaForm(request,response);
                break;

            case "xoa":
                xoaBangLuong(request,response);
                break;

            case "xem":
                xemChiTiet(request,response);
                break;

            default:
                danhSachBangLuong(request,response);
        }
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{

        request.setCharacterEncoding("UTF-8");

        String action=request.getParameter("action");
        if(action==null) action="";

        switch(action){

            case "them":
                themBangLuong(request,response);
                break;

            case "sua":
                capNhatBangLuong(request,response);
                break;

            default:
                response.sendRedirect("bangluong");
        }
    }

    // ================================
    // DANH SÁCH
    // ================================

    private void danhSachBangLuong(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{

        List<BangLuong> list = bangLuongService.layTatCa();

        request.setAttribute("list",list);

        request.getRequestDispatcher("/WEB-INF/view/luongview/DanhSachBangLuong.jsp")
                .forward(request,response);
    }

    // ================================
    // FORM THÊM
    // ================================

    private void showFormThem(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{



        request.getRequestDispatcher("/WEB-INF/view/luongview/ThemBangLuong.jsp")
                .forward(request,response);
    }

    // ================================
    // THÊM BẢNG LƯƠNG
    // ================================

    private void themBangLuong(HttpServletRequest request,HttpServletResponse response) throws IOException{

        BangLuong bl=new BangLuong();

        bl.setNhanVienId(Integer.parseInt(request.getParameter("nhanVienId")));
        bl.setThang(Integer.parseInt(request.getParameter("thang")));
        bl.setNam(Integer.parseInt(request.getParameter("nam")));

        bl.setSoNgayLamViec(new BigDecimal(request.getParameter("soNgayLamViec")));
        bl.setSoNgayThucTe(new BigDecimal(request.getParameter("soNgayThucTe")));
        bl.setGioLamThem(new BigDecimal(request.getParameter("gioLamThem")));

        bl.setLuongCoBan(new BigDecimal(request.getParameter("luongCoBan")));
        bl.setPhuCap(new BigDecimal(request.getParameter("phuCap")));
        bl.setLuongLamThem(new BigDecimal(request.getParameter("luongLamThem")));

        bl.setThuong(new BigDecimal(request.getParameter("thuong")));
        bl.setTongThuNhap(new BigDecimal(request.getParameter("tongThuNhap")));

        bl.setBaoHiemXaHoi(new BigDecimal(request.getParameter("baoHiemXaHoi")));
        bl.setBaoHiemYTe(new BigDecimal(request.getParameter("baoHiemYTe")));

        bl.setTamUng(new BigDecimal(request.getParameter("tamUng")));
        bl.setTongKhauTru(new BigDecimal(request.getParameter("tongKhauTru")));

        bl.setLuongThucLanh(new BigDecimal(request.getParameter("luongThucLanh")));

        bl.setTrangThai(request.getParameter("trangThai"));

        bangLuongService.them(bl);

        response.sendRedirect("bangluong");
    }

    // ================================
    // FORM SỬA
    // ================================

    private void suaForm(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{

        int id=Integer.parseInt(request.getParameter("id"));

        BangLuong bl=bangLuongService.layTheoId(id);

        request.setAttribute("bl",bl);
        request.setAttribute("danhSachNhanVien",nhanVienService.layTatCa());

        request.getRequestDispatcher("/WEB-INF/view/luongview/SuaBangLuong.jsp")
                .forward(request,response);
    }

    // ================================
    // CẬP NHẬT
    // ================================

    private void capNhatBangLuong(HttpServletRequest request,HttpServletResponse response) throws IOException{

        BangLuong bl=new BangLuong();

        bl.setBangLuongId(Integer.parseInt(request.getParameter("bangLuongId")));

        bl.setThuong(new BigDecimal(request.getParameter("thuong")));
        bl.setTongThuNhap(new BigDecimal(request.getParameter("tongThuNhap")));
        bl.setTongKhauTru(new BigDecimal(request.getParameter("tongKhauTru")));
        bl.setLuongThucLanh(new BigDecimal(request.getParameter("luongThucLanh")));

        bl.setTrangThai(request.getParameter("trangThai"));

        bangLuongService.sua(bl);

        response.sendRedirect("bangluong");
    }

    // ================================
    // XÓA
    // ================================

    private void xoaBangLuong(HttpServletRequest request,HttpServletResponse response) throws IOException{

        int id=Integer.parseInt(request.getParameter("id"));

        bangLuongService.xoa(id);

        response.sendRedirect("bangluong");
    }

    // ================================
    // CHI TIẾT
    // ================================

    private void xemChiTiet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{

        int id=Integer.parseInt(request.getParameter("id"));

        BangLuong bl=bangLuongService.layTheoId(id);

        request.setAttribute("bl",bl);

        request.getRequestDispatcher("/WEB-INF/view/luongview/ChiTietBangLuong.jsp")
                .forward(request,response);
    }

}