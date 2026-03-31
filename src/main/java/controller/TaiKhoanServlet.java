package controller;

import dao.ThongBaoDAO;
import model.*;
import service.*;
import until.DashboardServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import static org.apache.taglibs.standard.functions.Functions.substring;

@WebServlet("/taikhoan")
public class TaiKhoanServlet extends HttpServlet {
    private EmailService emailService=new EmailService();
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
            case "xacnhanotp":xacNhanOTP(request,response);break;
            case "quenmatkhau":
                request.getRequestDispatcher("/WEB-INF/view/taikhoanview/XacNhanTaiKhoan.jsp")
                        .forward(request, response);
                break;
            case "xacnhantaikhoan":guiOTP(request,response);

            case "xoa": xoaTaiKhoan(request,response); break;
            case "khoa": khoaTaiKhoan(request,response); break;
            case "xem": xemChiTiet(request,response); break;
            case "login": moFormLogin(request,response); break;
            case "signin": moFormSignIn(request,response); break;
            case "logout": dangXuat(request,response);break;

            case "trangchu":goiDangNhapChoQuanLy(request,response,getSS(request,response));break;
            default:goiDangNhapChoQuanLy(request,response,getSS(request,response));
        }
    }



    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action=request.getParameter("action");
        if(action==null) action="";

        switch(action){
            case "doimatkhauotp":datLaiMatKhau(request,response);break;
            case "xacnhanotp":xacNhanOTP(request,response);break;
            case "xacnhantaikhoan":guiOTP(request,response);break;
            case "dangnhap": dangNhap(request,response); break;
            case "dangky": dangKy(request,response); break;
            case "doiMatKhau": capNhatTaiKhoan(request,response); break;
        }
    }



    private void dangNhap(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        String taiKhoan=request.getParameter("taiKhoan");
        String matKhau=request.getParameter("matKhau");

        TaiKhoan tk = taiKhoanService.dangNhap(taiKhoan,matKhau);

        if(tk!=null){

            HttpSession session=request.getSession();
            session.setAttribute("taiKhoanDangDangNhap",tk);
            if(tk.getVaiTro().equalsIgnoreCase("nhanvien")||tk.getVaiTro().equalsIgnoreCase("nhan vien")) {
                goiDangNhapChoNV(request,response,tk);
            }

            else
                goiDangNhapChoQuanLy(request,response,tk);


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

    private boolean capNhatTaiKhoan(HttpServletRequest request,HttpServletResponse response)
            throws IOException, ServletException {

        int id=Integer.parseInt(request.getParameter("idTaiKhoan"));
        HttpSession session=(HttpSession) request.getSession(false);
        TaiKhoan tkss= (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");


        TaiKhoan tk=taiKhoanService.layTheoId(id);
        if(!request.getParameter("matKhauCu").equals(tk.getMatKhau())){
            request.setAttribute("message","Mật khẩu cũ không đúng!");
            goiDangNhapChoNV(request,response,tk);
        }

            if(tkss.getTaiKhoanId()!=id) {
                request.setAttribute("message","không được phép tấn công tài khoản người khác!");
                session.invalidate();
                request.getRequestDispatcher("/WEB-INF/view/thongbaoview/ThongBao.jsp").forward(request,response);

            }
        String xacNhanMK=request.getParameter("xacNhanMatKhau");
       String mkM =request.getParameter("matKhauMoi");
       if(xacNhanMK.equals(mkM)){
        tk.setMatKhau(request.getParameter("matKhauMoi"));}

        taiKhoanService.sua(tk);

        goiDangNhapChoNV(request,response,tk);
        return true;
    }

    // ================= XÓA =================

    private void xoaTaiKhoan(HttpServletRequest request,HttpServletResponse response)
            throws IOException, ServletException {

        int id=Integer.parseInt(request.getParameter("taikhoanid"));

        taiKhoanService.xoa(id);

       goiDangNhapChoQuanLy(request,response,getSS(request,response));
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
    public void goiDangNhapChoNV(HttpServletRequest request,HttpServletResponse response,TaiKhoan tk)
            throws ServletException, IOException{

            NhanVien nhanVien=nhanVienService.layTheoId(tk.getNhanVienId());
            List<BangLuong> listBangLuong = bangLuongService.layTheoNhanVien(tk.getNhanVienId());
            List<ChamCong>listChamCong=chamCongService.layTheoNhanVien(tk.getNhanVienId());
            ChucVu chucVu=chucVuService.layTheoId(nhanVienService.layTheoId(tk.getNhanVienId()).getChucVuId());
            List<DanhGia> listDanhGia=danhGiaService.layTheoNhanVien(tk.getNhanVienId());
            HopDong hopdong=hopDongService.layHopDongHieuLuc(tk.getNhanVienId());
            List<NghiPhep> listNghiPhep=nghiPhepService.layTheoNhanVien(tk.getNhanVienId());
            request.setAttribute("soNgayVangKhongPhep",chamCongService.layChamCongNghiKhongPhep(tk.getNhanVienId()).size());
        LocalDate now=LocalDate.now();
            List<ThongBao> listThongBao=thongBaoService.layTheoNguoiNhan(tk.getNhanVienId());
            dashboardServlet.nhanVienDas(request,response,tk);
            request.setAttribute("bangLuong",bangLuongService.getBangLuongMoiNhatByNhanVien(tk.getNhanVienId()));
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
    public void goiDangNhapChoQuanLy(HttpServletRequest request, HttpServletResponse response, TaiKhoan tk)
            throws ServletException, IOException {

        List<NhanVien> listNhanVien = nhanVienService.layTatCa();
       dashboardServlet.quanLyDas(request,response,tk);
        request.setAttribute("listNhanVien",        listNhanVien);
        request.setAttribute("listChucVu",          chucVuService.layTatCa());
        request.setAttribute("listTaiKhoan",        taiKhoanService.layTatCa());
        request.setAttribute("listNghiPhepDaDuyet", nghiPhepService.layDaDuyet());
        request.setAttribute("listNghiPhepTuChoi",  nghiPhepService.layTuChoi());
        request.setAttribute("listHopDong",         hopDongService.layTatCa());
        request.setAttribute("listBangLuongAll",    bangLuongService.layTatCa());
        request.setAttribute("listDanhGiaAll",      danhGiaService.layTatCa());
        request.setAttribute("listThongBaoAll",     thongBaoService.layTheoNguoiNhan(tk.getNhanVienId()));
        request.setAttribute("listChamCongAll",     chamCongService.layTatCa());

        request.setAttribute("listNhanVienKhongPhaiTruongPhong",nhanVienService.getNhanVienKhongPhaiTruongPhong());
        request.getRequestDispatcher("/WEB-INF/view/trangchuview/TrangChuQuanLy.jsp")
                .forward(request, response);
    }
    public void dangXuat(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException{
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        try{
            getSS(request,response);
            System.out.println("chua dang xuat");
        } catch (Exception e) {
            System.out.println("da dang xuat");
        }
        response.sendRedirect(request.getContextPath() + "/taikhoan?action=login");
    }

    // Hàm 1 - Tìm tài khoản → gửi OTP
    private void guiOTP(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tenTaiKhoan = request.getParameter("tentaiKhoan");
        TaiKhoan tk = taiKhoanService.layTheoTenDangNhap(tenTaiKhoan);
        if (tk == null) {
            request.setAttribute("message", "Tài khoản không tồn tại");
            request.getRequestDispatcher("/WEB-INF/view/taikhoanview/XacNhanTaiKhoan.jsp")
                    .forward(request, response);
            return;
        }
        NhanVien nhanVien = nhanVienService.layTheoId(tk.getNhanVienId());
        String otp = emailService.taoMaOTP();
        emailService.guiOTP(nhanVien.getEmail(), otp);

        HttpSession session = request.getSession();
        session.setAttribute("tenDangNhap", tk.getTenDangNhap());
        session.setAttribute("otpCode", otp);
        session.setAttribute("otpExpiry", System.currentTimeMillis() + 5 * 60 * 1000);
        session.setAttribute("otpDaXacNhan", false);

        request.setAttribute("message", "Đã gửi OTP về email "
                + nhanVien.getEmail().substring(0, 2) + "**********"
                + nhanVien.getEmail().substring(nhanVien.getEmail().length() - 11) + " của bạn!");
        request.getRequestDispatcher("/WEB-INF/view/taikhoanview/XacNhanOtp.jsp")
                .forward(request, response);
    }

    // Hàm 2 - Kiểm tra OTP → nếu đúng mới qua trang đổi mật khẩu
    private void xacNhanOTP(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String otpNhap = request.getParameter("otp");
        String otpLuu  = (String) session.getAttribute("otpCode");
        Long   expiry  = (Long)   session.getAttribute("otpExpiry");

        if (expiry == null || System.currentTimeMillis() > expiry) {
            request.setAttribute("message", "Mã OTP đã hết hạn, vui lòng thử lại!");
            request.getRequestDispatcher("/WEB-INF/view/taikhoanview/XacNhanTaiKhoan.jsp")
                    .forward(request, response);
            return;
        }
        if (!otpNhap.equals(otpLuu)) {
            request.setAttribute("message", "Mã OTP không đúng!");
            request.getRequestDispatcher("/WEB-INF/view/taikhoanview/XacNhanOtp.jsp")
                    .forward(request, response);
            return;
        }

        session.setAttribute("otpDaXacNhan", true);
        request.getRequestDispatcher("/WEB-INF/view/taikhoanview/DatLaiMatKhau.jsp")
                .forward(request, response);
    }

    // Hàm 3 - Đổi mật khẩu mới
    private void datLaiMatKhau(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Chặn truy cập trực tiếp không qua OTP
        Boolean daXacNhan = (Boolean) session.getAttribute("otpDaXacNhan");
        if (daXacNhan == null || !daXacNhan) {
            response.sendRedirect(request.getContextPath() + "/taikhoan?action=quenMatKhau");
            return;
        }

        String matKhauMoi = request.getParameter("matKhauMoi");
        String xacNhan    = request.getParameter("xacNhanMatKhau");

        if (!matKhauMoi.equals(xacNhan)) {
            request.setAttribute("message", "Mật khẩu xác nhận không khớp!");
            request.getRequestDispatcher("/WEB-INF/view/taikhoanview/DatLaiMatKhau.jsp")
                    .forward(request, response);
            return;
        }

        String tenDangNhap = session.getAttribute("tenDangNhap").toString();
        TaiKhoan tk = taiKhoanService.layTheoTenDangNhap(tenDangNhap);
        tk.setMatKhau(matKhauMoi);
        taiKhoanService.sua(tk);

        session.removeAttribute("otpCode");
        session.removeAttribute("otpExpiry");
        session.removeAttribute("otpDaXacNhan");
        session.removeAttribute("tenDangNhap");

        request.setAttribute("message", "Đổi mật khẩu thành công, vui lòng đăng nhập lại!");
        request.getRequestDispatcher("/WEB-INF/view/taikhoanview/LogIn.jsp")
                .forward(request, response);
    }
    private TaiKhoan getSS(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if(session==null)
            return new TaiKhoan();

        return (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
    }
    private void khoaTaiKhoan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int taikhoanid= Integer.parseInt(request.getParameter("taikhoanid"));
        TaiKhoan tk=taiKhoanService.layTheoId(taikhoanid);
        tk.setTrangThai(0);
        taiKhoanService.sua(tk);
        goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }
}