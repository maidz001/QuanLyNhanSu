package controller;

import model.PhongBan;
import model.TaiKhoan;
import service.NhanVienService;
import service.PhongBanService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/phongban")
public class PhongBanServlet extends HttpServlet {

    private PhongBanService phongBanService = new PhongBanService();
    private TaiKhoanServlet taiKhoanServlet=new TaiKhoanServlet();
    NhanVienService nhanVienService=new NhanVienService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if(action == null) action = "";

        switch(action){
            case "xoa": xoaPhongBan(request,response); break;
            case "kichhoat":kichHoatPB(request,response);
                break;
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
            case "capnhat": suaPhongBan(request,response); break;
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
            throws IOException, ServletException {

        PhongBan pb = new PhongBan();

        pb.setMaPhongBan(request.getParameter("maPhongBan"));
        pb.setTenPhongBan(request.getParameter("tenPhongBan"));

        String soLuong = request.getParameter("soLuong");
        if(soLuong != null && !soLuong.isEmpty())
            pb.setSoLuong(Integer.parseInt(soLuong));

        String truongPhong = request.getParameter("truongPhongId");
        if(truongPhong != null && !truongPhong.isEmpty()){
            pb.setTruongPhongId(Integer.parseInt(truongPhong));
        nhanVienService.setChucVuTruongPhong(Integer.parseInt(truongPhong));
        }


        pb.setMoTa(request.getParameter("moTa"));
        pb.setTrangThai(Integer.parseInt(request.getParameter("trangThai")));

        phongBanService.them(pb);
        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }

    // ================= CẬP NHẬT =================

    private void suaPhongBan(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        int id = Integer.parseInt(request.getParameter("id"));

        PhongBan pb = phongBanService.layTheoId(id);

        pb.setTenPhongBan(request.getParameter("tenPhongBan"));
        pb.setMoTa(request.getParameter("moTa"));
        pb.setTrangThai(Integer.parseInt(request.getParameter("trangThai")));

        String truongPhong = request.getParameter("truongPhongId");
        if(Integer.parseInt(truongPhong)!=pb.getTruongPhongId()){
           nhanVienService.setChucVu(pb.getTruongPhongId(),"nhan vien");
        }
        if (truongPhong != null && !truongPhong.isEmpty())
            pb.setTruongPhongId(Integer.parseInt(truongPhong));
        else
            pb.setTruongPhongId(pb.getTruongPhongId());

        phongBanService.sua(pb);

 taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }

    // ================= XÓA =================

    private void xoaPhongBan(HttpServletRequest request,HttpServletResponse response)
            throws IOException, ServletException {

        int id = Integer.parseInt(request.getParameter("id"));

        phongBanService.setTrangThai(id,0);

        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }

    private void kichHoatPB(HttpServletRequest request,HttpServletResponse response)
            throws IOException, ServletException {

        int id = Integer.parseInt(request.getParameter("id"));

        phongBanService.setTrangThai(id,1);

        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
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
        int idPhongban=Integer.parseInt(request.getParameter("id"));
        request.setAttribute("truongPhongHienTai",nhanVienService.layTheoId(phongBanService.layTheoId(idPhongban).getTruongPhongId()).getHoTen());
        request.setAttribute("listNhanVien", nhanVienService.getNhanVienKhongPhaiTruongPhongTrongPB(idPhongban));
        request.setAttribute("phongban", phongBanService.layTheoId(idPhongban));
        request.getRequestDispatcher("WEB-INF/view/phongbanview/SuaPhongBan.jsp").forward(request,response);
    }
    private TaiKhoan getSS(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        return (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
    }
}