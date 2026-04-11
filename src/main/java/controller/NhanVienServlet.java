package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.HopDong;
import model.NhanVien;
import model.TaiKhoan;
import model.ThongBao;
import service.*;
import until.XuatExcel;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.apache.poi.ss.util.DateParser.parseDate;

@WebServlet("/nhanvien")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize       = 1024 * 1024 * 5,
        maxRequestSize    = 1024 * 1024 * 10
)
public class NhanVienServlet extends HttpServlet {
    private XuatExcel xuatExcel = new XuatExcel();
    private ThongBaoService thongBaoService = new ThongBaoService();
    private NhanVienService nhanVienService = new NhanVienService();
    private TaiKhoanServlet taiKhoanServlet = new TaiKhoanServlet();
    private HopDongService hopDongService = new HopDongService();
    private PhongBanService phongBanService = new PhongBanService();
    private ChucVuService chucVuService = new ChucVuService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "them":       moFormThem(request, response);  break;
            case "sua":        suaForm(request, response);     break;
            case "xoa":        xoaNhanVien(request, response); break;
            case "xemchitiet": xemChiTiet(request, response);  break;
            case "xuatexcel":  xuatExcel(request, response);   break;
            default: taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) return;

        switch (action) {
            case "capnhatthongtincanhan": capNhatCaNhan(request, response);  break;
            case "them":                  themNhanVien(request, response);   break;
            case "suabyquanly":           suaByQuanLy(request, response);    break;
            case "capnhatanh":            capNhatAnh(request, response);     break;
        }
    }

    private void moFormThem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("listChucVu", chucVuService.layTatCa());
        request.setAttribute("listPhongBan", phongBanService.layTatCa());

        int soNV = nhanVienService.layTatCa().size();
        request.setAttribute("soNhanVien", soNV < 9 ? "NV0" + (soNV + 1) : "NV" + (soNV + 1));

        request.getRequestDispatcher("WEB-INF/view/nhanvienview/ThemNhanVien.jsp").forward(request, response);
    }

    private void capNhatAnh(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        TaiKhoan tk = (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");

        String nvIdStr = request.getParameter("nhanVienId");
        if (nvIdStr == null || nvIdStr.trim().isEmpty()) {
            request.setAttribute("message", "Không tìm thấy nhân viên!");
            taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
            return;
        }

        int nvId = Integer.parseInt(nvIdStr);
        Part filePart = request.getPart("anhDaiDien");
        String fileName = filePart.getSubmittedFileName();

        if (fileName == null || fileName.trim().isEmpty()) {
            taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
            return;
        }

        String ext = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        if (!ext.equals(".jpg") && !ext.equals(".jpeg") && !ext.equals(".png") && !ext.equals(".gif")) {
            request.setAttribute("message", "Chỉ chấp nhận file JPG, PNG, GIF!");
            taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
            return;
        }

        String newFileName = "avatar_" + nvId + ext;
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads" + File.separator + "avatars";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        filePart.write(uploadPath + File.separator + newFileName);

        NhanVien nv = nhanVienService.layTheoId(nvId);
        if (nv == null) {
            request.setAttribute("message", "Nhân viên không tồn tại!");
            taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
            return;
        }

        nv.setAnhDaiDien("uploads/avatars/" + newFileName);
        nhanVienService.sua(nv, null);
        request.setAttribute("message", "Cập nhật avatar thành công");
        taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
    }

    private void themNhanVien(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String hoTen = request.getParameter("hoTen");
        if (hoTen == null || hoTen.trim().isEmpty()) {
            request.setAttribute("message", "Họ tên nhân viên không được để trống!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        String email = request.getParameter("email");
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("message", "Email không được để trống!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        String phongBanIdStr = request.getParameter("phongBanId");
        String chucVuIdStr   = request.getParameter("chucVuId");
        if (phongBanIdStr == null || phongBanIdStr.trim().isEmpty()
                || chucVuIdStr == null || chucVuIdStr.trim().isEmpty()) {
            request.setAttribute("message", "Vui lòng chọn phòng ban và chức vụ!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(request.getParameter("maNhanVien"));
        nv.setHoTen(hoTen.trim());
        nv.setEmail(email.trim());
        nv.setDienThoai(request.getParameter("dienThoai"));
        nv.setDiaChi(request.getParameter("diaChi"));
        nv.setGioiTinh(request.getParameter("gioiTinh"));
        nv.setSoCmnd(request.getParameter("soCmnd"));
        nv.setSoTaiKhoan(request.getParameter("soTaiKhoan"));
        nv.setNganHang(request.getParameter("nganHang"));
        nv.setPhongBanId(Integer.parseInt(phongBanIdStr));
        phongBanService.setSoLuong(Integer.parseInt(phongBanIdStr), "tang");
        nv.setChucVuId(Integer.parseInt(chucVuIdStr));
        nv.setTrangThai(request.getParameter("trangThai"));

        String ngaySinhStr = request.getParameter("ngaySinh");
        if (ngaySinhStr != null && !ngaySinhStr.trim().isEmpty())
            nv.setNgaySinh(java.sql.Date.valueOf(ngaySinhStr.trim()));

        String ngayVaoLamStr = request.getParameter("ngayVaoLam");
        if (ngayVaoLamStr != null && !ngayVaoLamStr.trim().isEmpty())
            nv.setNgayVaoLam(java.sql.Date.valueOf(ngayVaoLamStr.trim()));

        LocalDate now = LocalDate.now();

        if (nhanVienService.them(nv, getSS(request, response).getNhanVienId()))
            thongBaoService.them(new ThongBao(0, getSS(request, response).getNhanVienId(), getSS(request, response).getNhanVienId(), "Thêm nhân viên", "Thêm thành công nhân viên " + nv.getHoTen() + " vào công ty", "Thêm nhân viên", 0, Date.valueOf(now)));

        NhanVien nvMoi = nhanVienService.layTheoMa(nv.getMaNhanVien());
        if (nvMoi != null) {
            HopDong hd = new HopDong();
            hd.setNhanVienId(nvMoi.getNhanVienId());
            hd.setLoaiHopDong(request.getParameter("loaiHopDong"));
            hd.setGhiChu(request.getParameter("ghiChuHD"));
            hd.setTrangThai("Hieu luc");

            String luongStr = request.getParameter("luongCoBan");
            hd.setLuongCoBan(luongStr != null && !luongStr.trim().isEmpty()
                    ? new java.math.BigDecimal(luongStr.trim())
                    : java.math.BigDecimal.ZERO);

            String ngayBDStr = request.getParameter("ngayBatDauHD");
            java.sql.Date ngayBD = null;
            if (ngayBDStr != null && !ngayBDStr.trim().isEmpty()) {
                ngayBD = java.sql.Date.valueOf(ngayBDStr.trim());
                hd.setNgayBatDau(ngayBD);
            }

            int thoiHanStr = 0;
            if (request.getParameter("thoiHanHD") != null && !request.getParameter("thoiHanHD").isEmpty())
                thoiHanStr = Integer.parseInt(request.getParameter("thoiHanHD"));

            if      (thoiHanStr > 3)                    hd.setPhuCap(BigDecimal.ONE);
            else if (thoiHanStr == 0)                   hd.setPhuCap(BigDecimal.valueOf(500000));
            else if (thoiHanStr == 1 || thoiHanStr == 2) hd.setPhuCap(BigDecimal.valueOf(300000));
            else                                         hd.setPhuCap(BigDecimal.ZERO);

            if (ngayBD != null && thoiHanStr != 0) {
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(ngayBD);
                cal.add(java.util.Calendar.YEAR, thoiHanStr);
                cal.add(java.util.Calendar.DAY_OF_MONTH, -1);
                hd.setNgayKetThuc(new java.sql.Date(cal.getTimeInMillis()));
            }

            if (!hopDongService.them(hd, getSS(request, response).getNhanVienId()))
                System.out.println("loi tao hop dong");
        }

        request.setAttribute("message", "Thêm nhân viên thành công");
        taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
    }

    public void suaByQuanLy(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        TaiKhoan tk = (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
        String nvIdStr = request.getParameter("nhanVienId");
        if (nvIdStr == null || nvIdStr.trim().isEmpty()) {
            request.setAttribute("message", "Không tìm thấy nhân viên cần sửa!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, tk);
            return;
        }

        int nvId = Integer.parseInt(nvIdStr);
        NhanVien cu = nhanVienService.layTheoId(nvId);

        if (cu == null) {
            request.setAttribute("message", "Nhân viên không tồn tại!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, tk);
            return;
        }

        String hoTen     = request.getParameter("hoTen");
        String email     = request.getParameter("email");
        String dienThoai = request.getParameter("dienThoai");
        String diaChi    = request.getParameter("diaChi");
        String gioiTinh  = request.getParameter("gioiTinh");
        String soCmnd    = request.getParameter("soCmnd");
        String trangThai = request.getParameter("trangThai");
        String soTaiKhoan = request.getParameter("soTaiKhoan");
        String nganHang   = request.getParameter("nganHang");

        if (hoTen      != null && !hoTen.trim().isEmpty())      cu.setHoTen(hoTen.trim());
        if (email      != null && !email.trim().isEmpty())      cu.setEmail(email.trim());
        if (dienThoai  != null && !dienThoai.trim().isEmpty())  cu.setDienThoai(dienThoai.trim());
        if (diaChi     != null && !diaChi.trim().isEmpty())     cu.setDiaChi(diaChi.trim());
        if (gioiTinh   != null && !gioiTinh.trim().isEmpty())   cu.setGioiTinh(gioiTinh.trim());
        if (soCmnd     != null && !soCmnd.trim().isEmpty())     cu.setSoCmnd(soCmnd.trim());
        if (trangThai  != null && !trangThai.trim().isEmpty())  cu.setTrangThai(trangThai.trim());
        if (soTaiKhoan != null && !soTaiKhoan.trim().isEmpty()) cu.setSoTaiKhoan(soTaiKhoan.trim());
        if (nganHang   != null && !nganHang.trim().isEmpty())   cu.setNganHang(nganHang.trim());

        String pbStr = request.getParameter("phongBanId");
        if (pbStr != null && !pbStr.trim().isEmpty())
            cu.setPhongBanId(Integer.parseInt(pbStr.trim()));

        String cvStr = request.getParameter("chucVuId");
        if (cvStr != null && !cvStr.trim().isEmpty())
            cu.setChucVuId(Integer.parseInt(cvStr.trim()));

        String ngaySinhStr = request.getParameter("ngaySinh");
        if (ngaySinhStr != null && !ngaySinhStr.trim().isEmpty())
            cu.setNgaySinh(java.sql.Date.valueOf(ngaySinhStr.trim()));

        String ngayVaoLamStr = request.getParameter("ngayVaoLam");
        if (ngayVaoLamStr != null && !ngayVaoLamStr.trim().isEmpty())
            cu.setNgayVaoLam(java.sql.Date.valueOf(ngayVaoLamStr.trim()));

        LocalDate now = LocalDate.now();
        nhanVienService.sua(cu, tk.getNhanVienId());
        thongBaoService.them(new ThongBao(0, getSS(request, response).getNhanVienId(), nvId, "Sửa thông tin cá nhân", "Thông tin cá nhân của bạn đã được quản lý cập nhật", "Sửa thông tin", 0, Date.valueOf(now)));
        request.setAttribute("message", "Sửa thành công");
        taiKhoanServlet.goiDangNhapChoQuanLy(request, response, tk);
    }

    private void suaForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            request.setAttribute("message", "Không tìm thấy nhân viên cần sửa!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        int id = Integer.parseInt(idStr);
        NhanVien nv = nhanVienService.layTheoId(id);

        if (nv == null) {
            request.setAttribute("message", "Nhân viên không tồn tại!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        request.setAttribute("nv", nv);
        request.getRequestDispatcher("SuaNhanVien.jsp").forward(request, response);
    }

    private void xoaNhanVien(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            request.setAttribute("message", "Không tìm thấy nhân viên cần xóa!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        int id = Integer.parseInt(idStr);
        NhanVien nv = nhanVienService.layTheoId(id);

        if (nv == null) {
            request.setAttribute("message", "Nhân viên không tồn tại!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        if (nhanVienService.setTrangThaiNghiViec(id)) {
            HopDong hd = hopDongService.layHopDongHieuLuc(id);
            if (hd != null) {
                hd.setTrangThai("Da huy");
                hopDongService.sua(hd, getSS(request, response).getNhanVienId());
            }
            phongBanService.setSoLuong(nv.getPhongBanId(), "giam");
            request.setAttribute("message", "Xóa thành công");
        } else {
            request.setAttribute("message", "Xóa thất bại, vui lòng thử lại!");
        }

        taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
    }

    private void xemChiTiet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            request.setAttribute("message", "Không tìm thấy nhân viên!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        int id = Integer.parseInt(idStr);
        NhanVien nv = nhanVienService.layTheoId(id);

        if (nv == null) {
            request.setAttribute("message", "Nhân viên không tồn tại!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        request.setAttribute("listPhongBan", phongBanService.layTatCa());
        request.setAttribute("listChucVu", chucVuService.layTatCa());
        request.setAttribute("nv", nv);
        request.getRequestDispatcher("WEB-INF/view/nhanvienview/XemChiTiet.jsp").forward(request, response);
    }

    private void capNhatCaNhan(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        TaiKhoan tk = (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");

        String nvIdStr = request.getParameter("nhanVienId");
        if (nvIdStr == null || nvIdStr.trim().isEmpty()) {
            request.setAttribute("message", "Không tìm thấy thông tin nhân viên!");
            taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
            return;
        }

        int nvId = Integer.parseInt(nvIdStr);

        if (tk.getNhanVienId() != nvId) {
            request.setAttribute("message", "Không có quyền sửa thông tin người khác!");
            taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
            return;
        }

        NhanVien nv = nhanVienService.layTheoId(nvId);
        if (nv == null) {
            request.setAttribute("message", "Nhân viên không tồn tại!");
            taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
            return;
        }

        String hoTen  = request.getParameter("hoTen");
        String email  = request.getParameter("email");
        String sdt    = request.getParameter("dienThoai");
        String diaChi = request.getParameter("diaChi");

        if (hoTen == null || hoTen.trim().isEmpty()) {
            request.setAttribute("message", "Họ tên không được để trống!");
            taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
            return;
        }

        nv.setHoTen(hoTen);
        if (email  != null && !email.isEmpty())  nv.setEmail(email);
        if (sdt    != null && !sdt.isEmpty())    nv.setDienThoai(sdt);
        if (diaChi != null && !diaChi.isEmpty()) nv.setDiaChi(diaChi);

        String soTaiKhoan = request.getParameter("soTaiKhoan");
        if (soTaiKhoan != null && !soTaiKhoan.trim().isEmpty())
            nv.setSoTaiKhoan(soTaiKhoan.trim());

        String nganHang = request.getParameter("nganHang");
        if (nganHang != null && !nganHang.trim().isEmpty())
            nv.setNganHang(nganHang.trim());

        nhanVienService.sua(nv, tk.getNhanVienId());
        LocalDate now = LocalDate.now();
        thongBaoService.them(new ThongBao(0, getSS(request, response).getNhanVienId(), getSS(request, response).getNhanVienId(), "Sửa thông tin", "Đổi thông tin cá nhân thành công", "sửa thông tin", 0, Date.valueOf(now)));
        request.setAttribute("message", "Cập nhật thông tin thành công!");
        taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
    }

    private void xuatExcel(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        xuatExcel.xuatDanhSachNhanVien(nhanVienService.layTatCa(), response);
        taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
    }

    private TaiKhoan getSS(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        return (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
    }
}