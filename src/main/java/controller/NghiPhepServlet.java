package controller;

import model.NghiPhep;
import model.TaiKhoan;
import service.ChamCongService;
import service.NghiPhepService;
import service.NhanVienService;
import service.TaiKhoanService;

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
private TaiKhoanServlet taiKhoanServlet= new TaiKhoanServlet();
private ChamCongService chamCongService=new ChamCongService();
NhanVienService nhanVienService=new NhanVienService();
TaiKhoanService taiKhoanService=new TaiKhoanService();

    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        String action=request.getParameter("action");
        if(action==null) action="";

        switch(action){
            case "duyet":
                duyetNghiPhep(request,response);
                break;
            case "tuchoi":
                tuChoiNghiPhep(request,response);
                break;
            case "xoadondacu": xoaDonDaLau(request,response); break;
            case "xoatheoid": xoaDonTheoId(request,response); break;
            case "sua": moFormSua(request,response); break;
            default: danhSachNghiPhep(request,response);
        }
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action=request.getParameter("action");
        if(action==null) action="";

        switch(action){
            case "xinNghiPhep":
                themNghiPhep(request,response);
                break;

        }
    }

    private void danhSachNghiPhep(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        List<NghiPhep> ds = nghiPhepService.layTatCa();

        request.setAttribute("dsNghiPhep",ds);

        request.getRequestDispatcher("nghiphep-list.jsp")
                .forward(request,response);
    }

    private void themNghiPhep(HttpServletRequest request,HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session=(HttpSession) request.getSession(false);
        TaiKhoan tk=(TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
                NghiPhep np = new NghiPhep();
        np.setNghiPhepId(nghiPhepService.layTatCa().size()+1);
        np.setNhanVienId(Integer.parseInt(request.getParameter("idNhanVien")));
        np.setLoaiPhep(request.getParameter("loaiPhep"));
        np.setNgayBatDau(Date.valueOf(request.getParameter("ngayBatDau")));
        np.setNgayKetThuc(Date.valueOf(request.getParameter("ngayKetThuc")));
        np.setLyDo(request.getParameter("lyDo"));
        np.setTrangThai("Cho duyet");
        if(nghiPhepService.kiemTraTrungNgay(np)){
            request.setAttribute("message","Đã có đơn nghỉ phép trùng ngày, vui lòng kiểm tra lại");
            taiKhoanServlet.goiDangNhapChoNV(request,response,tk);
        }
        if(!nghiPhepService.nopDon(np)){
           request.setAttribute("message","Đã đạt số lượng nghỉ trong tháng hoặc năm");
           taiKhoanServlet.goiDangNhapChoNV(request,response,tk);
        }
        else{
            request.setAttribute("message","Nộp đơn thành công");
            taiKhoanServlet.goiDangNhapChoNV(request,response,tk);
        }

    }



    private void xoaNghiPhep(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        int id=Integer.parseInt(request.getParameter("id"));

        nghiPhepService.xoa(id);

        response.sendRedirect("nghiphep");
    }

    private void xemChiTiet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        int id=Integer.parseInt(request.getParameter("id"));

        NghiPhep np = nghiPhepService.layTheoId(id);

        request.setAttribute("nghiPhep",np);

        request.getRequestDispatcher("nghiphep-form.jsp")
                .forward(request,response);
    }

    private void moFormSua(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        xemChiTiet(request,response);
    }
    private void xoaDonDaLau(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
        int idNhanVien=Integer.parseInt(request.getParameter("nhanVienId"));
        HttpSession session=(HttpSession) request.getSession(false);
        TaiKhoan tk= (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
        if(nghiPhepService.xoa(idNhanVien))
            request.setAttribute("message","Xóa thành công");
        else
            request.setAttribute("message","Xóa không thành công");
        taiKhoanServlet.goiDangNhapChoNV(request,response,tk);
    }
    private void xoaDonTheoId(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
        int idNhanVien=Integer.parseInt(request.getParameter("nghiPhepId"));
        HttpSession session=(HttpSession) request.getSession(false);
        TaiKhoan tk= (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
        if(nghiPhepService.xoaTheoID(idNhanVien))
            request.setAttribute("message","Xóa thành công");
        else
            request.setAttribute("message","Xóa không thành công");
        taiKhoanServlet.goiDangNhapChoNV(request,response,tk);
    }

    private void duyetNghiPhep(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        int id = Integer.parseInt(request.getParameter("id"));

        // Lấy tài khoản đang đăng nhập
        TaiKhoan tk = (TaiKhoan) request.getSession().getAttribute("taiKhoanDangDangNhap");

        NghiPhep np = nghiPhepService.layTheoId(id);
        if (np == null) {
            request.getSession().setAttribute("errorMessage", "Không tìm thấy đơn nghỉ phép!");
            response.sendRedirect("nghiphep");
            return;
        }

        np.setTrangThai("Da duyet");
        np.setNguoiDuyet(tk.getNhanVienId()); // set người duyệt là NV đang đăng nhập

        nghiPhepService.capNhat(np);

        // Tạo bản ghi chấm công vắng có phép
        LocalDate ngayBatDau  = LocalDate.parse(np.getNgayBatDau().toString());
        LocalDate ngayKetThuc = LocalDate.parse(np.getNgayKetThuc().toString());

        LocalDate ngay = ngayBatDau;
        while (!ngay.isAfter(ngayKetThuc)) {
            chamCongService.insertVangCoPhep(np.getNhanVienId(), ngay);
            ngay = ngay.plusDays(1);
        }

        request.setAttribute("message", "Đã duyệt đơn nghỉ phép thành công!");
        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }

    private void tuChoiNghiPhep(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        int id = Integer.parseInt(request.getParameter("id"));

        TaiKhoan tk = (TaiKhoan) request.getSession().getAttribute("taiKhoanDangDangNhap");

        NghiPhep np = nghiPhepService.layTheoId(id);
        if (np == null) {
            request.setAttribute("errorMessage", "Không tìm thấy đơn nghỉ phép!");
            response.sendRedirect("nghiphep");
            return;
        }

        np.setTrangThai("Tu choi");
        np.setNguoiDuyet(tk.getNhanVienId());

        nghiPhepService.capNhat(np);

        request.setAttribute("message", "Đã từ chối đơn nghỉ phép!");
        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,getSS(request,response));
    }
    private TaiKhoan getSS(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        return (TaiKhoan) session.getAttribute("taiKhoanDangDangNhap");
    }
}