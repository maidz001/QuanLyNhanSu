package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.NhanVien;
import model.TaiKhoan;
import service.NhanVienService;
import service.TaiKhoanService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/nhanvien")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,      // 1MB
        maxFileSize       = 1024 * 1024 * 5,  // 5MB
        maxRequestSize    = 1024 * 1024 * 10  // 10MB
)
public class NhanVienServlet extends HttpServlet {

    private NhanVienService nhanVienService = new NhanVienService();
    private TaiKhoanServlet taiKhoanServlet = new TaiKhoanServlet();
    private TaiKhoanService taiKhoanService = new TaiKhoanService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "them":
                request.getRequestDispatcher("ThemNhanVien.jsp").forward(request, response);
                break;
            case "sua":
                suaForm(request, response);
                break;
            case "xoa":
                xoaNhanVien(request, response);
                break;
            case "xem":
                xemChiTiet(request, response);
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
            case "sua":        capNhatNhanVien(request, response); break;
            case "capnhatanh": capNhatAnh(request, response);      break;
        }
    }

    // ================= CẬP NHẬT ẢNH ĐẠI DIỆN =================

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

        // Kiểm tra định dạng
        String ext = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        if (!ext.equals(".jpg") && !ext.equals(".jpeg")
                && !ext.equals(".png") && !ext.equals(".gif")) {
            request.setAttribute("message", "Chỉ chấp nhận file JPG, PNG, GIF!");
            taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
            return;
        }

        // Tên file theo nhanVienId → tránh trùng, ghi đè ảnh cũ
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

    // ================= DANH SÁCH =================

    private void danhSachNhanVien(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<NhanVien> list = nhanVienService.layTatCa();
        request.setAttribute("list", list);
        request.getRequestDispatcher("DanhSachNhanVien.jsp").forward(request, response);
    }

    // ================= THÊM =================

    private void themNhanVien(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(request.getParameter("maNhanVien"));
        nv.setHoTen(request.getParameter("hoTen"));
        nv.setEmail(request.getParameter("email"));
        nv.setDienThoai(request.getParameter("dienThoai"));
        nv.setDiaChi(request.getParameter("diaChi"));
        nv.setPhongBanId(Integer.parseInt(request.getParameter("phongBanId")));
        nv.setChucVuId(Integer.parseInt(request.getParameter("chucVuId")));
        nv.setTrangThai("hoatdong");

        nhanVienService.them(nv, getSS(request, response).getNhanVienId());
        response.sendRedirect("nhanvien");
    }

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
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        nhanVienService.xoa(id);
        response.sendRedirect("nhanvien");
    }

    // ================= CHI TIẾT =================

    private void xemChiTiet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        NhanVien nv = nhanVienService.layTheoId(id);
        request.setAttribute("nv", nv);
        request.getRequestDispatcher("ChiTietNhanVien.jsp").forward(request, response);
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
}