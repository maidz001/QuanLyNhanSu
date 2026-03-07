package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.NhanVien;
import model.TaiKhoan;
import service.NhanVienService;

import java.io.IOException;
import java.util.List;

@WebServlet("/nhanvien")
public class NhanVienServlet extends HttpServlet {

    private NhanVienService nhanVienService = new NhanVienService();

    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        String action=request.getParameter("action");
        if(action==null) action="list";

        switch(action){

            case "them":
                request.getRequestDispatcher("ThemNhanVien.jsp")
                        .forward(request,response);
                break;

            case "sua":
                suaForm(request,response);
                break;

            case "xoa":
                xoaNhanVien(request,response);
                break;

            case "xem":
                xemChiTiet(request,response);
                break;

            default:
                danhSachNhanVien(request,response);
        }
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action=request.getParameter("action");

        if(action==null) return;

        switch(action){

            case "them":
                themNhanVien(request,response);
                break;

            case "sua":
                capNhatNhanVien(request,response);
                break;
        }
    }

    // ================= DANH SÁCH =================

    private void danhSachNhanVien(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        List<NhanVien> list = nhanVienService.layTatCa();

        request.setAttribute("list",list);

        request.getRequestDispatcher("DanhSachNhanVien.jsp")
                .forward(request,response);
    }

    // ================= THÊM =================

    private void themNhanVien(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        NhanVien nv = new NhanVien();

        nv.setMaNhanVien(request.getParameter("maNhanVien"));
        nv.setHoTen(request.getParameter("hoTen"));
        nv.setEmail(request.getParameter("email"));
        nv.setDienThoai(request.getParameter("dienThoai"));
        nv.setDiaChi(request.getParameter("diaChi"));
        nv.setPhongBanId(Integer.parseInt(request.getParameter("phongBanId")));
        nv.setChucVuId(Integer.parseInt(request.getParameter("chucVuId")));
        nv.setTrangThai("hoatdong");

        nhanVienService.them(nv,getSS(request,response).getNhanVienId());

        response.sendRedirect("nhanvien");
    }

    // ================= FORM SỬA =================

    private void suaForm(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        int id=Integer.parseInt(request.getParameter("id"));

        NhanVien nv = nhanVienService.layTheoId(id);

        request.setAttribute("nv",nv);

        request.getRequestDispatcher("SuaNhanVien.jsp")
                .forward(request,response);
    }

    // ================= CẬP NHẬT =================

    private void capNhatNhanVien(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        NhanVien nv = new NhanVien();

        nv.setNhanVienId(Integer.parseInt(request.getParameter("nhanVienId")));
        nv.setHoTen(request.getParameter("hoTen"));
        nv.setEmail(request.getParameter("email"));
        nv.setDienThoai(request.getParameter("dienThoai"));
        nv.setDiaChi(request.getParameter("diaChi"));

        nhanVienService.sua(nv,getSS(request,response).getNhanVienId());

        response.sendRedirect("nhanvien");
    }

    // ================= XÓA =================

    private void xoaNhanVien(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        int id=Integer.parseInt(request.getParameter("id"));

        nhanVienService.xoa(id);

        response.sendRedirect("nhanvien");
    }

    // ================= CHI TIẾT =================

    private void xemChiTiet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        int id=Integer.parseInt(request.getParameter("id"));

        NhanVien nv = nhanVienService.layTheoId(id);

        request.setAttribute("nv",nv);

        request.getRequestDispatcher("ChiTietNhanVien.jsp")
                .forward(request,response);
    }
    private TaiKhoan getSS(HttpServletRequest request, HttpServletResponse response){
        HttpSession session=(HttpSession) request.getSession(false);
        TaiKhoan tk= (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
        return tk;
    }
}