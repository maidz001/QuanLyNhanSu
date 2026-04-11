package controller;

import model.PhongBan;
import model.TaiKhoan;
import model.ThongBao;
import org.apache.poi.ss.formula.functions.T;
import service.NhanVienService;
import service.PhongBanService;
import service.ThongBaoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/phongban")
public class PhongBanServlet extends HttpServlet {

    private PhongBanService phongBanService = new PhongBanService();
    private TaiKhoanServlet taiKhoanServlet=new TaiKhoanServlet();
    private ThongBaoService thongBaoService=new ThongBaoService();
    NhanVienService nhanVienService=new NhanVienService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if(action == null) action = "";

        switch(action){
            case "xoa": xoaPhongBan(request,response); break;
            case "kichhoat":kichHoatPB(request,response); break;
            case "xem": xemChiTiet(request,response); break;
            case "sua": moFormSua(request,response); break;
            default: danhSachPhongBan(request,response);
        }
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if(action == null) action = "";

        switch(action){
            case "them": themPhongBan(request,response); break;
            case "capnhat": suaPhongBan(request,response); break;
        }
    }

    private void danhSachPhongBan(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        List<PhongBan> ds = phongBanService.layTatCa();
        request.setAttribute("dsPhongBan",ds);
        request.getRequestDispatcher("phongban-list.jsp").forward(request,response);
    }

    private void themPhongBan(HttpServletRequest request,HttpServletResponse response)
            throws IOException, ServletException {

        String tenPhongBan = request.getParameter("tenPhongBan");
        if (tenPhongBan == null || tenPhongBan.trim().isEmpty()) {
            request.setAttribute("message", "Tên phòng ban không được để trống!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        PhongBan pb = new PhongBan();
        pb.setMaPhongBan(request.getParameter("maPhongBan"));
        pb.setTenPhongBan(tenPhongBan);

        String soLuong = request.getParameter("soLuong");
        if(soLuong != null && !soLuong.isEmpty())
            pb.setSoLuong(Integer.parseInt(soLuong));

        String truongPhong = request.getParameter("truongPhongId");
        if(truongPhong != null && !truongPhong.isEmpty()){
            pb.setTruongPhongId(Integer.parseInt(truongPhong));
            nhanVienService.setChucVuTruongPhong(Integer.parseInt(truongPhong));
        }

        pb.setMoTa(request.getParameter("moTa"));
        pb.setTrangThai(Integer.parseInt(request.getParameter("trangThai")));

        phongBanService.them(pb);
        LocalDate now = LocalDate.now();
        thongBaoService.them(new ThongBao(0, getSS(request,response).getNhanVienId(), getSS(request,response).getNhanVienId(), "Thêm phòng ban", "Bạn vừa thêm phòng ban mới có tên: " + pb.getTenPhongBan(), "Thêm phòng ban", 0, Date.valueOf(now)));
        request.setAttribute("message","Thêm phòng ban thành công");
        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }

    private void suaPhongBan(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            request.setAttribute("message", "Không tìm thấy phòng ban cần sửa!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        int id = Integer.parseInt(idStr);
        PhongBan pb = phongBanService.layTheoId(id);

        if (pb == null) {
            request.setAttribute("message", "Phòng ban không tồn tại!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        String tenPhongBan = request.getParameter("tenPhongBan");
        if (tenPhongBan == null || tenPhongBan.trim().isEmpty()) {
            request.setAttribute("message", "Tên phòng ban không được để trống!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        pb.setTenPhongBan(tenPhongBan);
        pb.setMoTa(request.getParameter("moTa"));
        pb.setTrangThai(Integer.parseInt(request.getParameter("trangThai")));

        String truongPhong = request.getParameter("truongPhongId");
        if (truongPhong != null && !truongPhong.isEmpty()) {
            if (Integer.parseInt(truongPhong) != pb.getTruongPhongId()) {
                nhanVienService.setChucVu(pb.getTruongPhongId(), "nhan vien");
            }
            pb.setTruongPhongId(Integer.parseInt(truongPhong));
        }

        phongBanService.sua(pb);
        LocalDate now = LocalDate.now();
        thongBaoService.them(new ThongBao(0, getSS(request,response).getNhanVienId(), getSS(request,response).getNhanVienId(), "Sửa phòng ban", "Sửa phòng ban: " + pb.getTenPhongBan() + " thành công", "Sửa phòng ban", 0, Date.valueOf(now)));
        request.setAttribute("message","Sửa phòng ban thành công");
        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }

    private void xoaPhongBan(HttpServletRequest request,HttpServletResponse response)
            throws IOException, ServletException {

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            request.setAttribute("message", "Không tìm thấy phòng ban cần xóa!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        int id = Integer.parseInt(idStr);
        PhongBan pb = phongBanService.layTheoId(id);

        if (pb == null) {
            request.setAttribute("message", "Phòng ban không tồn tại!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        phongBanService.setTrangThai(id, 0);
        request.setAttribute("message","Xóa phòng ban thành công");
        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }

    private void kichHoatPB(HttpServletRequest request,HttpServletResponse response)
            throws IOException, ServletException {

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            request.setAttribute("message", "Không tìm thấy phòng ban cần kích hoạt!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        int id = Integer.parseInt(idStr);
        PhongBan pb = phongBanService.layTheoId(id);

        if (pb == null) {
            request.setAttribute("message", "Phòng ban không tồn tại!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        phongBanService.setTrangThai(id, 1);
        request.setAttribute("message","Kích hoạt phòng ban thành công");
        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }

    private void xemChiTiet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            request.setAttribute("message", "Không tìm thấy phòng ban!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        int id = Integer.parseInt(idStr);
        PhongBan pb = phongBanService.layTheoId(id);

        if (pb == null) {
            request.setAttribute("message", "Phòng ban không tồn tại!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        request.setAttribute("phongBan", pb);
        request.getRequestDispatcher("phongban-form.jsp").forward(request,response);
    }

    private void moFormSua(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            request.setAttribute("message", "Không tìm thấy phòng ban cần sửa!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        int idPhongban = Integer.parseInt(idStr);
        PhongBan pb = phongBanService.layTheoId(idPhongban);

        if (pb == null) {
            request.setAttribute("message", "Phòng ban không tồn tại!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        request.setAttribute("truongPhongHienTai", nhanVienService.layTheoId(pb.getTruongPhongId()).getHoTen());
        request.setAttribute("listNhanVien", nhanVienService.getNhanVienKhongPhaiTruongPhongTrongPB(idPhongban));
        request.setAttribute("phongban", pb);
        request.getRequestDispatcher("WEB-INF/view/phongbanview/SuaPhongBan.jsp").forward(request,response);
    }

    private TaiKhoan getSS(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        return (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
    }
}