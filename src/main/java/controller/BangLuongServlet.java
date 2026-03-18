package controller;

import dao.BangLuongDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.BangLuong;
import model.NhanVien;
import model.TaiKhoan;
import model.ThongBao;
import service.BangLuongService;
import service.NhanVienService;
import service.ThongBaoService;
import until.XuatExcel;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/bangluong")
public class BangLuongServlet extends HttpServlet {

    private XuatExcel xuatExcel = new XuatExcel();
    private ThongBaoService thongBaoService=new ThongBaoService();
    private NhanVienService nhanVienService = new NhanVienService();
    private BangLuongService bangLuongService = new BangLuongService();
    private TaiKhoanServlet taiKhoanServlet = new TaiKhoanServlet();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "them":                        showFormThem(request, response);            break;
            case "sua":                         suaForm(request, response);                break;
            case "tao":                         tao(request, response);                    break;
            case "xem":                         xemChiTiet(request, response);             break;
            case "xuatexcel":                   xuatExcel(request, response);              break;
            case "duyet":                       duyetBangLuong(request, response);         break;
            case "thanh-toan-tien-mat":         thanhToanTienMat(request, response);       break;
            case "thanh-toan-chuyen-khoan":     thanhToanChuyenKhoan(request, response);   break;
            case "thanh-toan-tat-ca-tien-mat":  thanhToanTatCaTienMat(request, response);  break;
            default:                            danhSachBangLuong(request, response);      break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "them":    themBangLuong(request, response);      break;
            case "sua":     capNhatBangLuong(request, response);   break;
            default:        response.sendRedirect("bangluong");    break;
        }
    }

    private void danhSachBangLuong(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<BangLuong> list = bangLuongService.layTatCa();
        request.setAttribute("list", list);
        request.getRequestDispatcher("/WEB-INF/view/luongview/DanhSachBangLuong.jsp")
                .forward(request, response);
    }

    private void showFormThem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/view/luongview/ThemBangLuong.jsp")
                .forward(request, response);
    }

    private void themBangLuong(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        BangLuong bl = new BangLuong();
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

    private void suaForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        BangLuong bl = bangLuongService.layTheoId(id);
        request.setAttribute("bl", bl);
        request.setAttribute("danhSachNhanVien", nhanVienService.layTatCa());
        request.getRequestDispatcher("/WEB-INF/view/luongview/SuaBangLuong.jsp")
                .forward(request, response);
    }

    private void capNhatBangLuong(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        BangLuong bl = new BangLuong();
        bl.setBangLuongId(Integer.parseInt(request.getParameter("bangLuongId")));
        bl.setThuong(new BigDecimal(request.getParameter("thuong")));
        bl.setTongThuNhap(new BigDecimal(request.getParameter("tongThuNhap")));
        bl.setTongKhauTru(new BigDecimal(request.getParameter("tongKhauTru")));
        bl.setLuongThucLanh(new BigDecimal(request.getParameter("luongThucLanh")));
        bl.setTrangThai(request.getParameter("trangThai"));
        bangLuongService.sua(bl);
        response.sendRedirect("bangluong");
    }

    private void xoaBangLuong(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        bangLuongService.xoa(id);
        response.sendRedirect("bangluong");
    }

    private void xemChiTiet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        BangLuong bl = bangLuongService.layTheoId(id);

        if (bl == null) {
            response.sendRedirect("bangluong");
            return;
        }

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

    private void tao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int thang = LocalDate.now().getMonthValue();
        int nam   = LocalDate.now().getYear();
        bangLuongService.tinhLuongToanBoThang(thang, nam, getSS(request, response).getNhanVienId());
        request.setAttribute("message", "Đã tính lương tháng " + thang + "/" + nam);
        LocalDate now=LocalDate.now();
        thongBaoService.thongBaoTinhLuongChoNhanVien(getSS(request,response).getNhanVienId());

        taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
    }

    private void duyetBangLuong(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        int id = Integer.parseInt(request.getParameter("id"));
        boolean kq = bangLuongService.duyetBangLuong(id, getSS(request, response).getNhanVienId());
        if(kq)
            thongBaoService.them(new ThongBao(0,getSS(request,response).getNhanVienId(),getSS(request,response).getNhanVienId(),"Sửa thông tin","Đổi thông tin cá nhân thành công","sửa thông tin",0, Date.valueOf("00:00:00")));

        request.setAttribute("message",
                kq ? "Đã duyệt bảng lương thành công!" : "Duyệt thất bại!");
        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }

    private void thanhToanTienMat(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        int id = Integer.parseInt(request.getParameter("id"));
        boolean kq = bangLuongService.thanhToanTienMat(id, getSS(request, response).getNhanVienId());
        request.setAttribute("message",
                kq ? "Đã thanh toán tiền mặt thành công!" : "Thanh toán thất bại!");
        LocalDate now=LocalDate.now();
        thongBaoService.them(new ThongBao(0,getSS(request,response).getNhanVienId(),bangLuongService.layTheoId(id).getNhanVienId(),"Thanh toán lương","Đã thanh toán bảng lương tháng "+bangLuongService.layTheoId(id).getThang()+" cho bạn bằng phương thức thanh toán tiền mặt, cảm ơn sự đóng góp của bạn cho công ty trong tháng vừa qua.","Thanh toán lương",0, Date.valueOf(now)));
        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));

    }

    private void thanhToanChuyenKhoan(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        int id = Integer.parseInt(request.getParameter("id"));
        boolean kq = bangLuongService.thanhToanChuyenKhoan(id, getSS(request, response).getNhanVienId());
        request.setAttribute("message",
                kq ? "Đã xác nhận thanh toán chuyển khoản!" : "Xác nhận thất bại!");
        LocalDate now=LocalDate.now();
        thongBaoService.them(new ThongBao(0,getSS(request,response).getNhanVienId(),bangLuongService.layTheoId(id).getNhanVienId(),"Thanh toán lương","Đã thanh toán bảng lương tháng "+bangLuongService.layTheoId(id).getThang()+" cho bạn bằng phương thức thanh toán chuyển khoản online, cảm ơn sự đóng góp của bạn cho công ty trong tháng vừa qua.","Thanh toán lương",0, Date.valueOf(now)));
        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }

    private void thanhToanTatCaTienMat(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        bangLuongService.thanhToanTatCaTienMat(getSS(request, response).getNhanVienId());
        request.setAttribute("message",
                "Đã thanh toán tiền mặt tất cả bảng lương chờ duyệt!");
        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }

    private void xuatExcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LocalDate now = LocalDate.now();
        xuatExcel.xuatBangLuong(
                bangLuongService.layTheoThangNam(now.getMonthValue(), now.getYear()),
                now.getMonthValue(),
                now.getYear(),
                nhanVienService.layTatCa(),
                response
        );
        taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
    }

    private TaiKhoan getSS(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        return (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
    }
}