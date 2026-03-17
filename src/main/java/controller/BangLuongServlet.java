package controller;

import dao.BangLuongDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.BangLuong;
import model.NhanVien;
import model.TaiKhoan;
import service.BangLuongService;
import service.NhanVienService;
import until.XuatExcel;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@WebServlet("/bangluong")
public class BangLuongServlet extends HttpServlet {
    private XuatExcel xuatExcel=new XuatExcel();
   private NhanVienService nhanVienService=new NhanVienService();
  private   BangLuongService bangLuongService = new BangLuongService();
 private TaiKhoanServlet taiKhoanServlet=new TaiKhoanServlet();
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

            case "tao":
                tao(request,response);
                break;

            case "xem":
                xemChiTiet(request,response);
                break;
            case "xuatexcel":xuatExcel(request,response);
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

    private void xemChiTiet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        BangLuong bl = bangLuongService.layTheoId(id);

        if (bl == null) {
            response.sendRedirect("bangluong");
            return;
        }

        // Lấy thông tin nhân viên
        NhanVien nv = nhanVienService.layTheoId(bl.getNhanVienId());
        if (nv != null) {
            request.setAttribute("tenNhanVien", nv.getHoTen());
            request.setAttribute("maNhanVien",  nv.getMaNhanVien());
            request.setAttribute("tenPhongBan", nv.getTenPhongBan());
            request.setAttribute("tenChucVu",   nv.getTenChucVu());
        }

        request.setAttribute("bangLuong", bl);
        request.getRequestDispatcher("/WEB-INF/view/luongview/XemChiTietBangLuong.jsp")
                .forward(request, response);
    }
    private void tao(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        int thang = LocalDate.now().getMonthValue();
        int nam   = LocalDate.now().getYear();
        List<Integer> dsNVId = nhanVienService.layTatCa()
                .stream()
                .filter(nv -> "Dang lam viec".equals(nv.getTrangThai()))
                .map(nv -> nv.getNhanVienId())
                .collect(java.util.stream.Collectors.toList());
        bangLuongService.tinhLuongToanBoThang( thang, nam,getSS(request,response).getNhanVienId());
        request.setAttribute("message", "Đã tính lương tháng " + thang + "/" + nam);
taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }
    private TaiKhoan getSS(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        return (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
    }

    private void xuatExcel(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        LocalDate now=LocalDate.now();

        xuatExcel.xuatBangLuong(bangLuongService.layTheoThangNam(now.getMonthValue(),now.getYear()),now.getMonthValue(),now.getYear(),nhanVienService.layTatCa(),response);
        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));

    }
}