package controller;

import ConnDatabase.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.NghiPhep;
import service.NghiPhepService;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/NghiPhep")
public class NghiPhep extends HttpServlet {

    private NghiPhepService nghiPhepService = new NghiPhepService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                hienThiDanhSach(request, response);
                break;
            case "edit":
                hienThiFormSua(request, response);
                break;
            case "delete":
                xoaNghiPhep(request, response);
                break;
            default:
                hienThiDanhSach(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("insert".equals(action)) {
            themNghiPhep(request, response);
        } else if ("update".equals(action)) {
            suaNghiPhep(request, response);
        }
    }

    private void hienThiDanhSach(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<model.NghiPhep> ds = nghiPhepService.layTatCa();
        request.setAttribute("dsNghiPhep", ds);
        request.getRequestDispatcher("nghiphep-list.jsp").forward(request, response);
    }

    private void hienThiFormSua(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            model.NghiPhep np = nghiPhepService.layTheoId(id);
            request.setAttribute("nghiPhep", np);
        }
        request.getRequestDispatcher("nghiphep-form.jsp").forward(request, response);
    }

    private void themNghiPhep(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        model.NghiPhep np = mapRequestToModel(request);
        nghiPhepService.them(np);
        response.sendRedirect("NghiPhep?action=list");
    }

    private void suaNghiPhep(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        model.NghiPhep np = mapRequestToModel(request);
        String idStr = request.getParameter("id");
        if (idStr != null) {
            np.setNghiPhepId(Integer.parseInt(idStr));
            nghiPhepService.sua(np);
        }
        response.sendRedirect("NghiPhep?action=list");
    }

    private void xoaNghiPhep(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            nghiPhepService.xoa(id);
        }
        response.sendRedirect("NghiPhep?action=list");
    }
    private model.NghiPhep mapRequestToModel(HttpServletRequest request) {
        model.NghiPhep np = new model.NghiPhep();
        
        String nvId = request.getParameter("nhanVienId");
        if (nvId != null && !nvId.isEmpty()) {
            np.setNhanVienId(Integer.parseInt(nvId));
        }

        String ngayBatDau = request.getParameter("ngayBatDau");
        if (ngayBatDau != null && !ngayBatDau.isEmpty()) {
            np.setNgayBatDau(Date.valueOf(ngayBatDau));
        }

        String ngayKetThuc = request.getParameter("ngayKetThuc");
        if (ngayKetThuc != null && !ngayKetThuc.isEmpty()) {
            np.setNgayKetThuc(Date.valueOf(ngayKetThuc));
        }

        np.setLoaiNghiPhep(request.getParameter("loaiNghiPhep"));
        np.setLyDo(request.getParameter("lyDo"));
        
        String tt = request.getParameter("trangThai");
        if (tt != null && !tt.isEmpty()) {
            np.setTrangThai(Integer.parseInt(tt));
        }
        
        return np;
    }
}
