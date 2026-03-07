package controller;

import dao.ThongBaoDAO;
import model.*;
import service.*;
import until.DashboardServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/taikhoan")
public class TaiKhoanServlet extends HttpServlet {
   private final DashboardServlet dashboardServlet=new DashboardServlet();
    private TaiKhoanService taiKhoanService = new TaiKhoanService();
    private BangLuongService bangLuongService=new BangLuongService();
   private ChamCongService chamCongService=new ChamCongService();
    private ChucVuService chucVuService=new ChucVuService();
    private DanhGiaService danhGiaService=new DanhGiaService();
    private HopDongService hopDongService=new HopDongService();
    private NghiPhepService nghiPhepService=new NghiPhepService();
    private NhanVienService nhanVienService=new NhanVienService();
    private PhongBanService phongBanService=new PhongBanService();
    private ThongBaoService thongBaoService=new ThongBaoService();


    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        String action=request.getParameter("action");
        if(action==null) action="";

        switch(action){
            case "xoa": xoaTaiKhoan(request,response); break;
            case "xem": xemChiTiet(request,response); break;
            case "login": moFormLogin(request,response); break;
            case "signin": moFormSignIn(request,response); break;
            default: moFormLogin(request,response);
        }
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action=request.getParameter("action");
        if(action==null) action="";

        switch(action){
            case "dangnhap": dangNhap(request,response); break;
            case "dangky": dangKy(request,response); break;
            case "sua": capNhatTaiKhoan(request,response); break;
        }
    }

    // ================= ĐĂNG NHẬP =================

    private void dangNhap(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        String taiKhoan=request.getParameter("taiKhoan");
        String matKhau=request.getParameter("matKhau");

        TaiKhoan tk = taiKhoanService.dangNhap(taiKhoan,matKhau);

        if(tk!=null){

            HttpSession session=request.getSession();
            session.setAttribute("taiKhoanDangDangNhap",tk);
            if(tk.getVaiTro().equalsIgnoreCase("nhanvien")) {
                NhanVien nhanVien=nhanVienService.layTheoId(tk.getNhanVienId());
                List<BangLuong> listBangLuong = bangLuongService.layTheoNhanVien(tk.getNhanVienId());
                List<ChamCong>listChamCong=chamCongService.layTheoNhanVien(tk.getNhanVienId());
                ChucVu chucVu=chucVuService.layTheoId(nhanVienService.layTheoId(tk.getNhanVienId()).getChucVuId());
                List<DanhGia> listDanhGia=danhGiaService.layTheoNhanVien(tk.getNhanVienId());
                HopDong hopdong=hopDongService.layHopDongHieuLuc(tk.getNhanVienId());
                List<NghiPhep> listNghiPhep=nghiPhepService.layTheoNhanVien(tk.getNhanVienId());
                PhongBan phongBan=phongBanService.layTheoId(nhanVienService.layTheoId(tk.getNhanVienId()).getPhongBanId());
                List<ThongBao> listThongBao=thongBaoService.layTheoNguoiNhan(tk.getNhanVienId());
                dashboardServlet.nhanVienDas(request,response,tk);
                request.setAttribute("nhanVien",nhanVien);
                request.setAttribute("listBangLuong",listBangLuong);
                request.setAttribute("listChamCong",listChamCong);
                request.setAttribute("chucVu",chucVu);
                request.setAttribute("listDanhGia",listDanhGia);
                request.setAttribute("hopDong",hopdong);
                request.setAttribute("listNghiPhep",listNghiPhep);
                request.setAttribute("listThongBao",listThongBao);
                request.getRequestDispatcher("/WEB-INF/view/trangchuview/TrangChuNhanVien.jsp").forward(request,response);
            }

            else
                response.sendRedirect(request.getContextPath()+"/WEB-INF/view/trangchuview/TrangChuQuanLy.jsp");

        }else{

            request.setAttribute("message","Tài khoản không tồn tại trên hệ thống");

            request.getRequestDispatcher("/WEB-INF/view/taikhoanview/LogIn.jsp")
                    .forward(request,response);
        }
    }

    // ================= MỞ FORM LOGIN =================

    private void moFormLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        request.getRequestDispatcher("/WEB-INF/view/taikhoanview/LogIn.jsp")
                .forward(request, response);
    }

    // ================= MỞ FORM SIGNIN =================

    private void moFormSignIn(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        request.getRequestDispatcher("/WEB-INF/view/taikhoanview/SignIn.jsp")
                .forward(request, response);
    }

    // ================= ĐĂNG KÝ =================

    private void dangKy(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        TaiKhoan tk = new TaiKhoan();

        tk.setNhanVienId(Integer.parseInt(request.getParameter("nhanVienId")));
        tk.setTenDangNhap(request.getParameter("tenDangNhap"));
        tk.setMatKhau(request.getParameter("matKhau"));
        tk.setVaiTro(request.getParameter("vaiTro"));
        tk.setTrangThai(Integer.parseInt(request.getParameter("trangThai")));

        taiKhoanService.them(tk);

        response.sendRedirect(request.getContextPath()+"/taikhoan?action=login");
    }

    // ================= CẬP NHẬT =================

    private void capNhatTaiKhoan(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        TaiKhoan tk = new TaiKhoan();

        tk.setTaiKhoanId(Integer.parseInt(request.getParameter("taiKhoanId")));
        tk.setTenDangNhap(request.getParameter("tenDangNhap"));
        tk.setMatKhau(request.getParameter("matKhau"));
        tk.setVaiTro(request.getParameter("vaiTro"));
        tk.setTrangThai(Integer.parseInt(request.getParameter("trangThai")));

        taiKhoanService.sua(tk);

        response.sendRedirect("taikhoan");
    }

    // ================= XÓA =================

    private void xoaTaiKhoan(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        int id=Integer.parseInt(request.getParameter("id"));

        taiKhoanService.xoa(id);

        response.sendRedirect("taikhoan");
    }

    // ================= XEM CHI TIẾT =================

    private void xemChiTiet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        int id=Integer.parseInt(request.getParameter("id"));

        TaiKhoan tk = taiKhoanService.layTheoId(id);

        request.setAttribute("tk",tk);

        request.getRequestDispatcher("ChiTietTaiKhoan.jsp")
                .forward(request,response);
    }
}