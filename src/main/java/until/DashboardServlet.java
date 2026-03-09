package until;

import model.*;
import ConnDatabase.DBConnection;
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
    private BangLuongService bangLuongService=new BangLuongService();
    private ChamCongService chamCongService=new ChamCongService();
    private ChucVuService chucVuService=new ChucVuService();
    private DanhGiaService danhGiaService=new DanhGiaService();
    private HopDongService hopDongService=new HopDongService();
    private NghiPhepService nghiPhepService=new NghiPhepService();
    private NhanVienService nhanVienService=new NhanVienService();
    private PhongBanService phongBanService=new PhongBanService();
    private ThongBaoService thongBaoService=new ThongBaoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        // Lấy tháng/năm lọc (mặc định = tháng hiện tại)
        Calendar cal = Calendar.getInstance();
        int thang = cal.get(Calendar.MONTH) + 1;
        int nam   = cal.get(Calendar.YEAR);
        try { thang = Integer.parseInt(request.getParameter("thang")); } catch (Exception ignored) {}
        try { nam   = Integer.parseInt(request.getParameter("nam"));   } catch (Exception ignored) {}

        request.setAttribute("thang", thang);
        request.setAttribute("nam",   nam);

        try (Connection conn = DBConnection.layKetNoi()) {

           
            int tongNV = 0, canBo = 0, nvMoi = 0;

            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) FROM nhan_vien WHERE trang_thai = 'Đang làm việc'");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) tongNV = rs.getInt(1);
            }

            // Cán bộ = chức vụ có cap_bac >= 3
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) FROM nhan_vien nv " +
                            "JOIN chuc_vu cv ON nv.chuc_vu_id = cv.chuc_vu_id " +
                            "WHERE nv.trang_thai = 'Đang làm việc' AND cv.cap_bac >= 3");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) canBo = rs.getInt(1);
            }

           
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT COUNT(*) FROM nhan_vien " +
                            "WHERE MONTH(ngay_vao_lam) = ? AND YEAR(ngay_vao_lam) = ?")) {
                ps.setInt(1, thang); ps.setInt(2, nam);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) nvMoi = rs.getInt(1);
                }
            }

            request.setAttribute("tongNV",   tongNV);
            request.setAttribute("canBo",    canBo);
            request.setAttribute("nvThuong", tongNV - canBo);
            request.setAttribute("nvMoi",    nvMoi);

     
            List<Map<String, Object>> dsPhongBan = new ArrayList<>();

            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT pb.ten_phong_ban, pb.ma_phong_ban, " +
                            "       COUNT(nv.nhan_vien_id) AS so_nv, " +
                            "       COALESCE(SUM(bl.luong_thuc_lanh), 0) AS tong_luong " +
                            "FROM phong_ban pb " +
                            "LEFT JOIN nhan_vien nv ON nv.phong_ban_id = pb.phong_ban_id " +
                            "                      AND nv.trang_thai = 'Đang làm việc' " +
                            "LEFT JOIN bang_luong bl ON bl.nhan_vien_id = nv.nhan_vien_id " +
                            "                       AND bl.thang = ? AND bl.nam = ? " +
                            "WHERE pb.trang_thai = 1 " +
                            "GROUP BY pb.phong_ban_id, pb.ten_phong_ban, pb.ma_phong_ban " +
                            "ORDER BY so_nv DESC")) {
                ps.setInt(1, thang); ps.setInt(2, nam);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> row = new LinkedHashMap<>();
                        row.put("tenPhongBan", rs.getString("ten_phong_ban"));
                        row.put("maPhongBan",  rs.getString("ma_phong_ban"));
                        row.put("soNV",        rs.getInt("so_nv"));
                        row.put("tongLuong",   rs.getBigDecimal("tong_luong"));
                        dsPhongBan.add(row);
                    }
                }
            }

            request.setAttribute("dsPhongBan", dsPhongBan);

          
            int choDuyet = 0, daDuyet = 0, tuChoi = 0;
            double tongNgayNghi = 0;

            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT trang_thai, COUNT(*) AS so_don, COALESCE(SUM(so_ngay),0) AS tong_ngay " +
                            "FROM nghi_phep " +
                            "WHERE MONTH(ngay_bat_dau) = ? AND YEAR(ngay_bat_dau) = ? " +
                            "GROUP BY trang_thai")) {
                ps.setInt(1, thang); ps.setInt(2, nam);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String tt = rs.getString("trang_thai");
                        if ("Chờ duyệt".equals(tt))  choDuyet    = rs.getInt("so_don");
                        if ("Đã duyệt".equals(tt))  { daDuyet    = rs.getInt("so_don");
                            tongNgayNghi = rs.getDouble("tong_ngay"); }
                        if ("Từ chối".equals(tt))    tuChoi       = rs.getInt("so_don");
                    }
                }
            }

            request.setAttribute("npChoDuyet",   choDuyet);
            request.setAttribute("npDaDuyet",    daDuyet);
            request.setAttribute("npTuChoi",     tuChoi);
            request.setAttribute("tongNgayNghi", tongNgayNghi);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi truy vấn: " + e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp")
                .forward(request, response);
    }
    public void nhanVienDas(HttpServletRequest request,HttpServletResponse response,TaiKhoan tk){
        NhanVien nhanVien=nhanVienService.layTheoId(tk.getNhanVienId());
        List<BangLuong> listBangLuong = bangLuongService.layTheoNhanVien(tk.getNhanVienId());
        List<ChamCong>listChamCong=chamCongService.layTheoNhanVien(tk.getNhanVienId());
        ChucVu chucVu=chucVuService.layTheoId(nhanVienService.layTheoId(tk.getNhanVienId()).getChucVuId());
        List<DanhGia> listDanhGia=danhGiaService.layTheoNhanVien(tk.getNhanVienId());
        HopDong hopdong=hopDongService.layHopDongHieuLuc(tk.getNhanVienId());
        List<NghiPhep> listNghiPhep=nghiPhepService.layTheoNhanVien(tk.getNhanVienId());
        PhongBan phongBan=phongBanService.layTheoId(nhanVienService.layTheoId(tk.getNhanVienId()).getPhongBanId());
        List<ThongBao> listThongBao=thongBaoService.layTheoNguoiNhan(tk.getNhanVienId());
        LocalDate now=LocalDate.now();
        request.setAttribute("soNgayCong",chamCongService.demNgayDiLam(tk.getNhanVienId(),now.getMonthValue(),now.getYear()));
        request.setAttribute("luongGanNhat", bangLuongService.getBangLuongMoiNhatByNhanVien(tk.getNhanVienId()).getLuongThucLanh());
request.setAttribute("soDonNghiPhep",nghiPhepService.soNgayNghiPhep(listNghiPhep));
request.setAttribute("diemDanhGia",danhGiaService.tongDiemTheoNhanVien(listDanhGia)/listDanhGia.size());
request.setAttribute("soGioLamThem",chamCongService.tinhGioLamThem(tk.getNhanVienId(), now.getMonthValue(), now.getYear()));
    }
}
