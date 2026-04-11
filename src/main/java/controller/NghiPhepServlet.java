package controller;

import model.NghiPhep;
import model.TaiKhoan;
import model.ThongBao;
import service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/nghiphep")
public class NghiPhepServlet extends HttpServlet {

    private NghiPhepService nghiPhepService = new NghiPhepService();
    private ThongBaoService thongBaoService = new ThongBaoService();
    private TaiKhoanServlet taiKhoanServlet = new TaiKhoanServlet();
    private ChamCongService chamCongService = new ChamCongService();
    NhanVienService nhanVienService = new NhanVienService();
    TaiKhoanService taiKhoanService = new TaiKhoanService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "duyet":     duyetNghiPhep(request, response);  break;
            case "tuchoi":    tuChoiNghiPhep(request, response); break;
            case "xoadondacu": xoaDonDaLau(request, response);   break;
            case "xoatheoid": xoaDonTheoId(request, response);   break;
            case "sua":       moFormSua(request, response);       break;
            default:          danhSachNghiPhep(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "xinNghiPhep": themNghiPhep(request, response); break;
        }
    }

    private void danhSachNghiPhep(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<NghiPhep> ds = nghiPhepService.layTatCa();
        request.setAttribute("dsNghiPhep", ds);
        request.getRequestDispatcher("nghiphep-list.jsp").forward(request, response);
    }

    private void themNghiPhep(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession(false);
        TaiKhoan tk = (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
        String idNhanVienStr = request.getParameter("idNhanVien");
        if (idNhanVienStr == null || idNhanVienStr.trim().isEmpty()) {
            request.setAttribute("message", "Không tìm thấy thông tin nhân viên!");
            taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
            return;
        }
        if(getSS(request,response).getNhanVienId()!=Integer.parseInt(idNhanVienStr)){
            request.setAttribute("message","không được phép xin nghỉ hộ người khác");
            taiKhoanServlet.goiDangNhapChoNV(request,response,getSS(request,response));
        }

        String ngayBatDauStr  = request.getParameter("ngayBatDau");
        String ngayKetThucStr = request.getParameter("ngayKetThuc");
        String loaiPhep       = request.getParameter("loaiPhep");
        String lyDo           = request.getParameter("lyDo");

        if (ngayBatDauStr == null || ngayBatDauStr.trim().isEmpty()
                || ngayKetThucStr == null || ngayKetThucStr.trim().isEmpty()) {
            request.setAttribute("message", "Vui lòng nhập đầy đủ ngày bắt đầu và ngày kết thúc!");
            taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
            return;
        }

        if (loaiPhep == null || loaiPhep.trim().isEmpty()) {
            request.setAttribute("message", "Vui lòng chọn loại phép!");
            taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
            return;
        }

        NghiPhep np = new NghiPhep();
        np.setNghiPhepId(nghiPhepService.layTatCa().size() + 1);
        np.setNhanVienId(Integer.parseInt(idNhanVienStr));
        np.setLoaiPhep(loaiPhep);
        np.setNgayBatDau(Date.valueOf(ngayBatDauStr));
        np.setNgayKetThuc(Date.valueOf(ngayKetThucStr));
        np.setLyDo(lyDo);
        np.setTrangThai("Cho duyet");

        if (nghiPhepService.kiemTraTrungNgay(np)) {
            request.setAttribute("message", "Đã có đơn nghỉ phép trùng ngày, vui lòng kiểm tra lại");
            taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
            return;
        }

        if (!nghiPhepService.nopDon(np)) {
            request.setAttribute("message", "Đã đạt số lượng nghỉ trong tháng hoặc năm");
            taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
        } else {
            request.setAttribute("message", "Nộp đơn thành công");
            LocalDate now = LocalDate.now();
            thongBaoService.them(new ThongBao(0, getSS(request, response).getNhanVienId(), np.getNhanVienId(), "Nộp đơn xin nghỉ", "Nộp đơn xin nghỉ phép thành công, theo dõi trang thái trong mục đơn nghỉ phép nhé.", "Xin nghỉ phép", 0, Date.valueOf(now)));
            taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
        }
    }

    private void xemChiTiet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            request.setAttribute("message", "Không tìm thấy đơn nghỉ phép!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        int id = Integer.parseInt(idStr);
        NghiPhep np = nghiPhepService.layTheoId(id);

        if (np == null) {
            request.setAttribute("message", "Đơn nghỉ phép không tồn tại!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        request.setAttribute("nghiPhep", np);
        request.getRequestDispatcher("nghiphep-form.jsp").forward(request, response);
    }

    private void moFormSua(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        xemChiTiet(request, response);
    }

    private void xoaDonDaLau(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("nhanVienId");
        if (idStr == null || idStr.trim().isEmpty()) {
            request.setAttribute("message", "Không tìm thấy nhân viên!");
            HttpSession session = request.getSession(false);
            TaiKhoan tk = (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
            taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
            return;
        }

        int idNhanVien = Integer.parseInt(idStr);
        HttpSession session = request.getSession(false);
        TaiKhoan tk = (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");

        if (nghiPhepService.xoa(idNhanVien))
            request.setAttribute("message", "Xóa thành công");
        else
            request.setAttribute("message", "Xóa không thành công");

        taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
    }

    private void xoaDonTheoId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("nghiPhepId");
        if (idStr == null || idStr.trim().isEmpty()) {
            request.setAttribute("message", "Không tìm thấy đơn nghỉ phép!");
            HttpSession session = request.getSession(false);
            TaiKhoan tk = (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
            taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
            return;
        }

        int idNghiPhep = Integer.parseInt(idStr);
        HttpSession session = request.getSession(false);
        TaiKhoan tk = (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");

        if (nghiPhepService.xoaTheoID(idNghiPhep))
            request.setAttribute("message", "Xóa thành công");
        else
            request.setAttribute("message", "Xóa không thành công");

        taiKhoanServlet.goiDangNhapChoNV(request, response, tk);
    }

    private void duyetNghiPhep(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            request.setAttribute("message", "Không tìm thấy đơn nghỉ phép!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        int id = Integer.parseInt(idStr);
        TaiKhoan tk = (TaiKhoan) request.getSession().getAttribute("taiKhoanDangDangNhap");
        NghiPhep np = nghiPhepService.layTheoId(id);
        if (np == null) {
            request.setAttribute("message", "Không tìm thấy đơn nghỉ phép!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        np.setTrangThai("Da duyet");
        np.setNguoiDuyet(tk.getNhanVienId());
        nghiPhepService.capNhat(np);

        LocalDate ngayBatDau  = LocalDate.parse(np.getNgayBatDau().toString());
        LocalDate ngayKetThuc = LocalDate.parse(np.getNgayKetThuc().toString());
        LocalDate ngay = ngayBatDau;
        while (!ngay.isAfter(ngayKetThuc)) {
            chamCongService.insertVangCoPhep(np.getNhanVienId(), ngay);
            ngay = ngay.plusDays(1);
        }

        request.setAttribute("message", "Đã duyệt đơn nghỉ phép thành công!");
        LocalDate now = LocalDate.now();
        thongBaoService.them(new ThongBao(0, getSS(request, response).getNhanVienId(), np.getNhanVienId(), "Duyệt đơn nghỉ phép", "Đơn nghỉ phép của bạn đã được duyệt, chúc bạn có 1 kì nghỉ vui vẻ.", "Duyệt nghỉ phép", 0, Date.valueOf(now)));
        taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
    }

    private void tuChoiNghiPhep(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            request.setAttribute("message", "Không tìm thấy đơn nghỉ phép!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }
        int id = Integer.parseInt(idStr);
        TaiKhoan tk = (TaiKhoan) request.getSession().getAttribute("taiKhoanDangDangNhap");
        NghiPhep np = nghiPhepService.layTheoId(id);
        if (np == null) {
            request.setAttribute("message", "Không tìm thấy đơn nghỉ phép!");
            taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
            return;
        }

        np.setTrangThai("Tu choi");
        np.setNguoiDuyet(tk.getNhanVienId());
        nghiPhepService.capNhat(np);
        request.setAttribute("message", "Đã từ chối đơn nghỉ phép!");
        LocalDate now = LocalDate.now();
        thongBaoService.them(new ThongBao(0, getSS(request, response).getNhanVienId(), np.getNhanVienId(), "Từ chối đơn xin nghỉ phép", "Đơn xin nghỉ phép của bạn bị từ chối, liên hệ phòng quản lý để biết thêm chi tiết", "Từ chối nghỉ phép", 0, Date.valueOf(now)));
        taiKhoanServlet.goiDangNhapChoQuanLy(request, response, getSS(request, response));
    }

    private TaiKhoan getSS(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        return (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
    }
}