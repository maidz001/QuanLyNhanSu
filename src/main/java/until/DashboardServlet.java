package until;

import model.*;
import ConnDatabase.DBConnection;
import org.apache.commons.math3.geometry.spherical.oned.LimitAngle;
import service.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private TaiKhoanService taiKhoanService = new TaiKhoanService();
    private BangLuongService bangLuongService = new BangLuongService();
    private ChamCongService chamCongService = new ChamCongService();
    private ChucVuService chucVuService = new ChucVuService();
    private DanhGiaService danhGiaService = new DanhGiaService();
    private HopDongService hopDongService = new HopDongService();
    private NghiPhepService nghiPhepService = new NghiPhepService();
    private NhanVienService nhanVienService = new NhanVienService();
    private PhongBanService phongBanService = new PhongBanService();
    private ThongBaoService thongBaoService = new ThongBaoService();

    // THÊM MỚI: Service thống kê

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy tài khoản đang đăng nhập từ session
        HttpSession session = request.getSession();
        TaiKhoan tk = (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");

        if (tk == null) {
            response.sendRedirect(request.getContextPath() + "/taikhoan?action=login");
            return;
        }

        // Phân quyền hiển thị dashboard
        if ("Nhân viên".equals(tk.getVaiTro())) {
            nhanVienDas(request, response, tk);
        } else if ("Quản lý".equals(tk.getVaiTro())) {
            quanLyDas(request, response, tk);
        }

        // Forward đến JSP
        request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
    }


    // ════════════════════════════════════════════════════════════
    // Dashboard cho Nhân viên
    // ════════════════════════════════════════════════════════════
    public void nhanVienDas(HttpServletRequest request, HttpServletResponse response, TaiKhoan tk) {
        NhanVien nhanVien = nhanVienService.layTheoId(tk.getNhanVienId());
        List<DanhGia> listDanhGia = danhGiaService.layTheoNhanVien(tk.getNhanVienId());
        List<NghiPhep> listNghiPhep = nghiPhepService.layTheoNhanVienDaDuyet(tk.getNhanVienId());
        LocalDate now = LocalDate.now();

        request.setAttribute("soNgayCong", chamCongService.demNgayDiLam(tk.getNhanVienId(), now.getMonthValue(), now.getYear()));
        request.setAttribute("luongGanNhat", bangLuongService.getBangLuongMoiNhatByNhanVien(tk.getNhanVienId()).getLuongThucLanh());
        request.setAttribute("soDonNghiPhep", nghiPhepService.soNgayNghiPhep(listNghiPhep));
        request.setAttribute("diemDanhGia", danhGiaService.tongDiemTheoNhanVien(listDanhGia) / listDanhGia.size());
        request.setAttribute("soGioLamThem", chamCongService.tinhGioLamThem(tk.getNhanVienId(), now.getMonthValue(), now.getYear()));
    }


    // ════════════════════════════════════════════════════════════
    // Dashboard cho Quản lý
    // ════════════════════════════════════════════════════════════
    public void quanLyDas(HttpServletRequest request, HttpServletResponse response, TaiKhoan tk) {

        // ─── Dữ liệu cũ (giữ nguyên) ───
        List<NhanVien> listNhanVien = nhanVienService.layTatCa();
        List<NghiPhep> listChoDuyet = nghiPhepService.layChoDuyet();
        List<PhongBan> listPhongBan = phongBanService.layTatCa();

        request.setAttribute("tongNhanVien", listNhanVien.size());
        request.setAttribute("nvDangLam", nhanVienService.demTong());
        request.setAttribute("tongPhongBan", listPhongBan.size());
        request.setAttribute("donChoDuyet", listChoDuyet.size());
        request.setAttribute("tongQuyLuong", bangLuongService.demTongLuongThangCuaCTy());
        request.setAttribute("listPhongBan", listPhongBan);
        request.setAttribute("listNghiPhepChoDuyet", listChoDuyet);


        // ═══════════════════════════════════════════════════════
        // THÊM MỚI: Thống kê cho biểu đồ
        // ═══════════════════════════════════════════════════════

        // 1. Thống kê giới tính
        Map<String, Object> thongKeGioiTinh = nhanVienService.tinhThongKeGioiTinh();
        request.setAttribute("thongKeGioiTinh", thongKeGioiTinh);

        // 2. Thống kê độ tuổi
        Map<String, Integer> thongKeDoTuoi = nhanVienService.tinhThongKeDoTuoi();
        request.setAttribute("thongKeDoTuoi", thongKeDoTuoi);
    }
}