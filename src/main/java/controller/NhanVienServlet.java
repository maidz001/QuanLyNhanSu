package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.HopDong;
import model.NhanVien;
import model.TaiKhoan;
import service.*;
import until.XuatExcel;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.apache.poi.ss.util.DateParser.parseDate;

@WebServlet("/nhanvien")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,      // 1MB
        maxFileSize       = 1024 * 1024 * 5,  // 5MB
        maxRequestSize    = 1024 * 1024 * 10  // 10MB
)
public class NhanVienServlet extends HttpServlet {
private XuatExcel xuatExcel=new XuatExcel();
    private NhanVienService nhanVienService = new NhanVienService();
    private TaiKhoanServlet taiKhoanServlet = new TaiKhoanServlet();
private HopDongService hopDongService=new HopDongService();
    private PhongBanService phongBanService=new PhongBanService();
    private ChucVuService chucVuService=new ChucVuService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "them":
                moFormThem(request,response);
                break;
            case "sua":
                suaForm(request, response);
                break;
            case "xoa":
                xoaNhanVien(request, response);
                break;
            case "xemchitiet":
                xemChiTiet(request, response);
                break;
            case "xuatexcel":
                xuatExcel(request,response);
                break;
            default:
                danhSachNhanVien(request, response);
        }
    }




    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) return;

        switch (action) {
            case "capnhatthongtincanhan": capNhatCaNhan(request, response); break;
            case "them":       themNhanVien(request, response);    break;
            case "suabyquanly":

                    suaByQuanLy(request,response);

                break;
            case "capnhatanh": capNhatAnh(request, response);      break;
        }
    }

    // ================= CẬP NHẬT ẢNH ĐẠI DIỆN =================
    private void moFormThem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("listChucVu",chucVuService.layTatCa());
        request.setAttribute("listPhongBan",phongBanService.layTatCa());
        if(nhanVienService.layTatCa().size()<10)
        request.setAttribute("soNhanVien","NV0"+(nhanVienService.layTatCa().size()+1));
        else         request.setAttribute("soNhanVien","NV"+(nhanVienService.layTatCa().size()+1));

        request.getRequestDispatcher("WEB-INF/view/nhanvienview/ThemNhanVien.jsp").forward(request, response);

    }
    private void capNhatAnh(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        TaiKhoan tk = (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
        int nvId = Integer.parseInt(request.getParameter("nhanVienId"));

        Part filePart = request.getPart("anhDaiDien");
        String fileName = filePart.getSubmittedFileName();


        if (fileName == null || fileName.trim().isEmpty()) {
            taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
            return;
        }

        String ext = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        if (!ext.equals(".jpg") && !ext.equals(".jpeg")
                && !ext.equals(".png") && !ext.equals(".gif")) {
            request.setAttribute("message", "Chỉ chấp nhận file JPG, PNG, GIF!");
            taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
            return;
        }

        // Tên file theo nhanVienId
        String newFileName = "avatar_" + nvId + ext;

        // Lưu vào WebContent/uploads/avatars/
        String uploadPath = getServletContext().getRealPath("")
                + File.separator + "uploads"
                + File.separator + "avatars";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        filePart.write(uploadPath + File.separator + newFileName);

        // Lưu đường dẫn tương đối vào DB
        NhanVien nv = nhanVienService.layTheoId(nvId);
        nv.setAnhDaiDien("uploads/avatars/" + newFileName);
        nhanVienService.sua(nv, null);

        taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
    }
    public void suaByQuanLy(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        TaiKhoan tk = (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");

        int nvId = Integer.parseInt(request.getParameter("nhanVienId"));
        NhanVien cu = nhanVienService.layTheoId(nvId);

        String hoTen     = request.getParameter("hoTen");
        String email     = request.getParameter("email");
        String dienThoai = request.getParameter("dienThoai");
        String diaChi    = request.getParameter("diaChi");
        String gioiTinh  = request.getParameter("gioiTinh");
        String soCmnd    = request.getParameter("soCmnd");
        String trangThai = request.getParameter("trangThai");

        // LỖI 1: dùng || thay vì && → phải dùng && (không null VÀ không rỗng)
        if (hoTen     != null && !hoTen.trim().isEmpty())     cu.setHoTen(hoTen.trim());
        if (email     != null && !email.trim().isEmpty())     cu.setEmail(email.trim());
        if (dienThoai != null && !dienThoai.trim().isEmpty()) cu.setDienThoai(dienThoai.trim());
        if (diaChi    != null && !diaChi.trim().isEmpty())    cu.setDiaChi(diaChi.trim());
        if (gioiTinh  != null && !gioiTinh.trim().isEmpty())  cu.setGioiTinh(gioiTinh.trim());
        if (soCmnd    != null && !soCmnd.trim().isEmpty())    cu.setSoCmnd(soCmnd.trim());
        if (trangThai != null && !trangThai.trim().isEmpty()) cu.setTrangThai(trangThai.trim());

        // LỖI 2: Integer.parseInt không bao giờ trả về null → dùng helper
        String pbStr = request.getParameter("phongBanId");
        if (pbStr != null && !pbStr.trim().isEmpty())
            cu.setPhongBanId(Integer.parseInt(pbStr.trim()));

        String cvStr = request.getParameter("chucVuId");
        if (cvStr != null && !cvStr.trim().isEmpty())
            cu.setChucVuId(Integer.parseInt(cvStr.trim()));

        // LỖI 3: dùng DateParser.parseDate của POI thay vì java.sql.Date.valueOf
        // LỖI 4: .getTime() trả về long không phải Date
        String ngaySinhStr = request.getParameter("ngaySinh");
        if (ngaySinhStr != null && !ngaySinhStr.trim().isEmpty())
            cu.setNgaySinh(java.sql.Date.valueOf(ngaySinhStr.trim()));

        String ngayVaoLamStr = request.getParameter("ngayVaoLam");
        if (ngayVaoLamStr != null && !ngayVaoLamStr.trim().isEmpty())
            cu.setNgayVaoLam(java.sql.Date.valueOf(ngayVaoLamStr.trim()));

        nhanVienService.sua(cu, tk.getNhanVienId());
        taiKhoanServlet.goiDangNhapChoQuanLy(request, response, tk);
    }
    // ================= DANH SÁCH =================

    private void danhSachNhanVien(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<NhanVien> list = nhanVienService.layTatCa();
        request.setAttribute("list", list);
        request.getRequestDispatcher("DanhSachNhanVien.jsp").forward(request, response);
    }

    // ================= THÊM =================



    // ================= FORM SỬA =================

    private void suaForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        NhanVien nv = nhanVienService.layTheoId(id);
        request.setAttribute("nv", nv);
        request.getRequestDispatcher("SuaNhanVien.jsp").forward(request, response);
    }

    // ================= CẬP NHẬT =================

    private void capNhatNhanVien(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int idNhanVien=Integer.parseInt(request.getParameter("nhanVienId"));
        NhanVien nv=nhanVienService.layTheoId(idNhanVien);
        String hoTen=request.getParameter("hoTen");
        String email =request.getParameter("email");
        String sdt=request.getParameter("dienThoai");
        String diaChi=request.getParameter("diaChi");
        if(!hoTen.isEmpty()){
        nv.setHoTen(hoTen);
            System.out.println(hoTen);
        }
        else {
            System.out.println("loi");
        }
        if(!email.isEmpty())
        nv.setEmail(email);
        if(!sdt.isEmpty())
        nv.setDienThoai(sdt);
        if(!diaChi.isEmpty())
        nv.setDiaChi(request.getParameter("diaChi"));
        nhanVienService.sua(nv, getSS(request, response).getNhanVienId());
        taiKhoanServlet.goiDangNhapChoNV(request,response,getSS(request, response));
    }

    // ================= XÓA =================

    private void xoaNhanVien(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        int id = Integer.parseInt(request.getParameter("id"));
        if(nhanVienService.setTrangThaiNghiViec(id)){
            HopDong hd=hopDongService.layHopDongHieuLuc(id);
            hd.setTrangThai("Da huy");
            hopDongService.sua(hd,getSS(request,response).getNhanVienId());
            phongBanService.setSoLuong(nhanVienService.layTheoId(id).getPhongBanId(),"giam");
        request.setAttribute("message","Xóa thành công");}
        else request.setAttribute("message","Thất bại");
        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }

    // ================= CHI TIẾT =================

    private void xemChiTiet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        NhanVien nv = nhanVienService.layTheoId(id);
        request.setAttribute("listPhongBan",phongBanService.layTatCa());
        request.setAttribute("listChucVu",chucVuService.layTatCa());
        request.setAttribute("nv", nv);
        request.getRequestDispatcher("WEB-INF/view/nhanvienview/XemChiTiet.jsp").forward(request, response);
    }

    // ================= SESSION HELPER =================

    private TaiKhoan getSS(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        return (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
    }
    private void capNhatCaNhan(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        TaiKhoan tk = (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");

        int nvId = Integer.parseInt(request.getParameter("nhanVienId"));


        if (tk.getNhanVienId() != nvId) {
            request.setAttribute("message", "Không có quyền sửa thông tin người khác!");
            taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
            return;
        }

        NhanVien nv = nhanVienService.layTheoId(nvId);
        String hoTen=request.getParameter("hoTen");
        String email =request.getParameter("email");
        String sdt=request.getParameter("dienThoai");
        String diaChi=request.getParameter("diaChi");
        if(!hoTen.isEmpty()){
            nv.setHoTen(hoTen);
            System.out.println(hoTen);
        }
        else {
            System.out.println("loi");
        }
        if(!email.isEmpty())
            nv.setEmail(email);
        if(!sdt.isEmpty())
            nv.setDienThoai(sdt);
        if(!diaChi.isEmpty())
            nv.setDiaChi(request.getParameter("diaChi"));

        nhanVienService.sua(nv, tk.getNhanVienId());

        request.setAttribute("messageCapNhat", "Cập nhật thông tin thành công!");
        taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
    }
    private void themNhanVien(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // ══ NHÂN VIÊN ══
        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(request.getParameter("maNhanVien"));
        nv.setHoTen(request.getParameter("hoTen"));
        nv.setEmail(request.getParameter("email"));
        nv.setDienThoai(request.getParameter("dienThoai"));
        nv.setDiaChi(request.getParameter("diaChi"));
        nv.setGioiTinh(request.getParameter("gioiTinh"));
        nv.setSoCmnd(request.getParameter("soCmnd"));
        nv.setPhongBanId(Integer.parseInt(request.getParameter("phongBanId")));
        phongBanService.setSoLuong(Integer.parseInt(request.getParameter("phongBanId")),"tang");
        nv.setChucVuId(Integer.parseInt(request.getParameter("chucVuId")));
        nv.setTrangThai(request.getParameter("trangThai"));

        String ngaySinhStr = request.getParameter("ngaySinh");
        if (ngaySinhStr != null && !ngaySinhStr.trim().isEmpty())
            nv.setNgaySinh(java.sql.Date.valueOf(ngaySinhStr.trim()));

        String ngayVaoLamStr = request.getParameter("ngayVaoLam");
        if (ngayVaoLamStr != null && !ngayVaoLamStr.trim().isEmpty())
            nv.setNgayVaoLam(java.sql.Date.valueOf(ngayVaoLamStr.trim()));

        nhanVienService.them(nv, getSS(request,response).getNhanVienId());

        //  HỢP ĐỒNG
        NhanVien nvMoi = nhanVienService.layTheoMa(nv.getMaNhanVien());
        if (nvMoi != null) {
            HopDong hd = new HopDong();
            hd.setNhanVienId(nvMoi.getNhanVienId());
            hd.setLoaiHopDong(request.getParameter("loaiHopDong"));
            hd.setGhiChu(request.getParameter("ghiChuHD"));
            hd.setTrangThai("Hieu luc");

            String luongStr  = request.getParameter("luongCoBan");

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
if(request.getParameter("thoiHanHD")!=null&&request.getParameter("thoiHanHD")!=""){
    thoiHanStr = Integer.parseInt(request.getParameter("thoiHanHD"));}

            if(thoiHanStr>3)hd.setPhuCap(BigDecimal.ONE);
            else if (thoiHanStr==0) {
                hd.setPhuCap(BigDecimal.valueOf(500000));
            } else if (thoiHanStr==1||thoiHanStr==2) {
                hd.setPhuCap(BigDecimal.valueOf(300000));
            } else hd.setPhuCap(BigDecimal.ZERO);
            if (ngayBD != null && thoiHanStr != 0) {
                int soNam = thoiHanStr;
                if (soNam > 0) {
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    cal.setTime(ngayBD);
                    cal.add(java.util.Calendar.YEAR, soNam);
                    cal.add(java.util.Calendar.DAY_OF_MONTH, -1);
                    hd.setNgayKetThuc(new java.sql.Date(cal.getTimeInMillis()));
                }

            }

            if(!hopDongService.them(hd, getSS(request,response).getNhanVienId()))
                System.out.println("loi");
        }

        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }
    private void xuatExcel(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        xuatExcel.xuatDanhSachNhanVien(nhanVienService.layTatCa(),response);
        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }
}