package controller;

import model.HopDong;
import model.NhanVien;
import model.TaiKhoan;
import model.ThongBao;
import service.HopDongService;
import service.NhanVienService;
import service.ThongBaoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/hopdong")
public class HopDongServlet extends HttpServlet {

    private HopDongService hopDongService = new HopDongService();
    private ThongBaoService thongBaoService=new ThongBaoService();
    private TaiKhoanServlet taiKhoanServlet=new TaiKhoanServlet();
    private NhanVienService nhanVienService=new NhanVienService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {

            case "them":
                showFormThem(request, response);
                break;

            case "sua":
                suaForm(request, response);
                break;

            case "xoa":
                xoaHopDong(request, response);
                break;

            case "xem":
                xemChiTiet(request, response);
                break;

            case "doiTrangThai":
                doiTrangThai(request, response);
                break;
            case "huy":
                huyHopDong(request, response);
                break;
            case "kichhoat":
                kichHoatHopDong(request, response);
                break;

            default:

        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {

            case "them":
                themHopDong(request, response);
                break;

            case "capnhat":
                capNhatHopDong(request, response);
                break;

            default:
                response.sendRedirect("hopdong");
        }
    }

    // ================= DANH SÁCH =================


    // ================= FORM THÊM =================

    private void showFormThem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("danhSachNhanVien", nhanVienService.layTatCa());

        request.getRequestDispatcher("ThemHopDong.jsp").forward(request, response);
    }

    // ================= THÊM =================

    private void themHopDong(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HopDong hd = new HopDong();

        hd.setSoHopDong(request.getParameter("soHopDong"));
        hd.setNhanVienId(Integer.parseInt(request.getParameter("nhanVienId")));
        hd.setLoaiHopDong(request.getParameter("loaiHopDong"));
        hd.setNgayBatDau(java.sql.Date.valueOf(request.getParameter("ngayBatDau")));

        String ngayKT = request.getParameter("ngayKetThuc");
        if (ngayKT != null && !ngayKT.isEmpty()) {
            hd.setNgayKetThuc(java.sql.Date.valueOf(ngayKT));
        }

        hd.setLuongCoBan(new BigDecimal(request.getParameter("luongCoBan")));
        hd.setPhuCap(new BigDecimal(request.getParameter("phuCap")));
        hd.setTrangThai(request.getParameter("trangThai"));
        hd.setGhiChu(request.getParameter("ghiChu"));

        hopDongService.them(hd,getSS(request,response).getNhanVienId());

        response.sendRedirect("hopdong");
    }

    // ================= FORM SỬA =================

    private void suaForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        HopDong hd = hopDongService.layTheoId(id);

        if (hd == null) {
            response.sendRedirect("hopdong");
            return;
        }

        // Lấy tên nhân viên để hiển thị
        NhanVien nv = nhanVienService.layTheoId(hd.getNhanVienId());
        if (nv != null) {
            request.setAttribute("tenNhanVien", nv.getHoTen());
        }

        request.setAttribute("hopdong", hd);
        request.getRequestDispatcher("/WEB-INF/view/hopdongview/SuaHopDong.jsp")
                .forward(request, response);
    }

    // ================= CẬP NHẬT =================

    private void capNhatHopDong(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        int id = Integer.parseInt(request.getParameter("id"));
        HopDong hd = hopDongService.layTheoId(id);

        // Xử lý gia hạn
        String giaHan = request.getParameter("giaHan");
        if (giaHan != null && !giaHan.isEmpty()) {
            int soThang = Integer.parseInt(giaHan);

            // Tính ngày kết thúc mới từ ngày kết thúc hiện tại
            // Nếu chưa có ngày kết thúc thì tính từ hôm nay
            java.time.LocalDate ngayGoc;
            if (hd.getNgayKetThuc() != null) {
                ngayGoc = LocalDate.parse(hd.getNgayKetThuc().toString());
            } else {
                ngayGoc = LocalDate.now();
            }
            LocalDate ngayMoi = ngayGoc.plusMonths(soThang);
            hd.setNgayKetThuc(java.sql.Date.valueOf(ngayMoi));
        }

        hd.setTrangThai(request.getParameter("trangThai"));
        hd.setLuongCoBan(new java.math.BigDecimal(request.getParameter("luongCoBan")));

        String phuCap = request.getParameter("phuCap");
        if (phuCap != null && !phuCap.isEmpty())
            hd.setPhuCap(new java.math.BigDecimal(phuCap));

        hd.setGhiChu(request.getParameter("ghiChu"));

        hopDongService.sua(hd,getSS(request,response).getNhanVienId());

        request.getSession().setAttribute("message", "Cập nhật hợp đồng thành công!");
        LocalDate now=LocalDate.now();
        thongBaoService.them(new ThongBao(0,getSS(request,response).getNhanVienId(),hd.getNhanVienId(),"Hợp đồng","Hợp đồng của bạn đã được cập nhật.","Cập nhật hợp đồng",0, Date.valueOf(now)));
taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }

    // ================= XÓA =================

    private void xoaHopDong(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        hopDongService.xoa(id);

        response.sendRedirect("hopdong");
    }

    // ================= CHI TIẾT =================

    private void xemChiTiet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        HopDong hd = hopDongService.layTheoId(id);

        request.setAttribute("hd", hd);

        request.getRequestDispatcher("ChiTietHopDong.jsp").forward(request, response);
    }

    // ================= ĐỔI TRẠNG THÁI =================

    private void doiTrangThai(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String trangThai = request.getParameter("trangThai");

        hopDongService.sua(hopDongService.layTheoId(id),getSS(request,response).getNhanVienId() );

        response.sendRedirect("hopdong");
    }
    private void huyHopDong(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        int id = Integer.parseInt(request.getParameter("id"));
        HopDong hd = hopDongService.layTheoId(id);

        if (hd == null) {
            request.getSession().setAttribute("errorMessage", "Không tìm thấy hợp đồng!");
            response.sendRedirect("hopdong");
            return;
        }

        hd.setTrangThai("Da huy");
        hopDongService.sua(hd,getSS(request,response).getNhanVienId());

        request.setAttribute("message", "Đã hủy hợp đồng thành công!");
taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }


    private void kichHoatHopDong(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        int id = Integer.parseInt(request.getParameter("id"));
        HopDong hd = hopDongService.layTheoId(id);

        if (hd == null) {
            request.setAttribute("message", "Không tìm thấy hợp đồng!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
            return;
        }

        if (!nhanVienService.isConLamViec(hd.getNhanVienId())) {
            request.setAttribute("message",
                    "Không thể kích hoạt! Nhân viên không còn làm việc.");
            taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));

            return;
        }

        hd.setTrangThai("Hieu luc");
        hopDongService.sua(hd,getSS(request,response).getNhanVienId());

        request.setAttribute("message", "Đã kích hoạt hợp đồng thành công!");
        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }

    private TaiKhoan getSS(HttpServletRequest request, HttpServletResponse response){
        HttpSession session=(HttpSession) request.getSession(false);
        TaiKhoan tk= (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
        return tk;
    }
}