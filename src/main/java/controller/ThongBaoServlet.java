package controller;

import ConnDatabase.DBConnection;
import model.TaiKhoan;
import model.ThongBao;
import service.TaiKhoanService;
import service.ThongBaoService;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@WebServlet(urlPatterns = "/thongbao",asyncSupported = true)
public class ThongBaoServlet extends HttpServlet {
    private TaiKhoanServlet taiKhoanServlet=new TaiKhoanServlet();

    private ThongBaoService thongBaoService = new ThongBaoService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {

            case "danhdaudadoc":
                danhDauDaDocById(request, response);
                break;

            case "danhdautatcaladadoc":
                danhDauTatCaLaDaDoc(request, response);
                break;
            case "danhdautatcaladadocchoql":
                danhDauTatCaLaDaDocChoQL(request, response);
                break;
            case "xoatatcathongbaodadoc":
                xoaTatCaThongBaoDaDoc(request, response);
                break;
            case "thongbaomoi":layThongBaoMoiHonId(request,response);
                break;

            default:
                danhSachThongBao(request, response);
        }
    }


    private void danhSachThongBao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int nguoiNhanId = (int) request.getSession(false).getAttribute("nhanVienId");

        List<ThongBao> list = thongBaoService.layTheoNguoiNhan(nguoiNhanId);

        int chuaDoc = thongBaoService.demChuaDoc(nguoiNhanId);

        request.setAttribute("list", list);
        request.setAttribute("chuaDoc", chuaDoc);

        request.getRequestDispatcher("DanhSachThongBao.jsp")
                .forward(request, response);
    }

    private void danhDauDaDocById(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        TaiKhoan tk = (TaiKhoan) request.getSession(false).getAttribute("taiKhoanDangDangNhap");
        int thongBaoId = Integer.parseInt(request.getParameter("id"));

        thongBaoService.danhDauDaDoc(thongBaoId);

        taiKhoanServlet.goiDangNhapChoNV(request,response,tk);
    }
    private void danhDauTatCaLaDaDoc(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        TaiKhoan tk = (TaiKhoan) request.getSession(false).getAttribute("taiKhoanDangDangNhap");
        int nhanVienId = tk.getNhanVienId();

        thongBaoService.docTatCa(nhanVienId);
        request.setAttribute("message","Đã dánh dấu tất cả thành đã đọc thành công");
        taiKhoanServlet.goiDangNhapChoNV(request,response,tk);
    }
    private void danhDauTatCaLaDaDocChoQL(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        TaiKhoan tk = (TaiKhoan) request.getSession(false).getAttribute("taiKhoanDangDangNhap");
        int nhanVienId = tk.getNhanVienId();

        thongBaoService.docTatCa(nhanVienId);
        request.setAttribute("message","Đã dánh dấu tất cả thành đã đọc thành công");
        taiKhoanServlet.goiDangNhapChoQuanLy(request,response,tk);
    }
    private void xoaTatCaThongBaoDaDoc(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        TaiKhoan tk = (TaiKhoan) request.getSession(false).getAttribute("taiKhoanDangDangNhap");
        int id = Integer.parseInt(request.getParameter("nhanVienId"));

        thongBaoService.xoaTatCaThongBaoDaDocChoNhanVien(id);
        request.setAttribute("message","Xóa thành công");
        taiKhoanServlet.goiDangNhapChoNV(request,response,tk);
    }
    private void layThongBaoMoiHonId(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        int userId = Integer.parseInt(req.getParameter("userId"));
        int lastId = Integer.parseInt(req.getParameter("lastId"));

        resp.setContentType("text/html;charset=UTF-8");

        AsyncContext async = req.startAsync();
        async.setTimeout(0);

        Thread.ofVirtual().start(() -> {
            try {
                while (true) {
                    List<ThongBao> ds = thongBaoService.layThongBaoMoiHonId(userId, lastId);

                    if (!ds.isEmpty()) {
                        PrintWriter out = async.getResponse().getWriter();
                        for (ThongBao tb : ds) {
                            out.print("<tr data-tbid=\"" + tb.getThongBaoId() + "\">");
                            out.print("<td><strong>" + tb.getTieuDe() + "</strong></td>");
                            out.print("<td>" + tb.getNoiDung() + "</td>");
                            out.print("<td><span class='badge badge-blue'>" + (tb.getLoai() != null ? tb.getLoai() : "--") + "</span></td>");
                            out.print("<td>" + tb.getNgayTao() + "</td>");
                            out.print("<td><span class='badge badge-orange'>Chưa đọc</span></td>");
                            out.print("<td><a href='/quanlynhansu/thongbao?action=danhdaudadoc&id=" + tb.getThongBaoId() + "' style='font-size:0.72rem;color:var(--primary-light)'>Đánh dấu đọc</a></td>");
                            out.print("</tr>");
                        }
                        out.flush();
                        break;
                    }

                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                async.complete();
            }
        });
    }
}