package controller;

import ConnDatabase.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.ChucVu;
import service.ChucVuService;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/ChucVu")
public class ChucVu extends HttpServlet {

    private ChucVuService chucVuService = new ChucVuService();

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
                xoaChucVu(request, response);
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
            themChucVu(request, response);
        } else if ("update".equals(action)) {
            suaChucVu(request, response);
        }
    }

    private void hienThiDanhSach(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<model.ChucVu> ds = chucVuService.layTatCa(); 
        request.setAttribute("dsChucVu", ds);
        request.getRequestDispatcher("chucvu-list.jsp").forward(request, response);
    }

    private void hienThiFormSua(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            model.ChucVu cv = chucVuService.layTheoId(id); 
            request.setAttribute("chucVu", cv);
        }
        request.getRequestDispatcher("chucvu-form.jsp").forward(request, response);
    }

    private void themChucVu(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        model.ChucVu cv = mapRequestToModel(request);
        chucVuService.them(cv); 
        response.sendRedirect("ChucVu?action=list");
    }

    private void suaChucVu(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        model.ChucVu cv = mapRequestToModel(request);
        String idStr = request.getParameter("id");
        if (idStr != null) {
            cv.setChucVuId(Integer.parseInt(idStr));
            chucVuService.sua(cv); 
        }
        response.sendRedirect("ChucVu?action=list");
    }

    private void xoaChucVu(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            chucVuService.xoa(id); 
        }
        response.sendRedirect("ChucVu?action=list");
    }
  
    private model.ChucVu mapRequestToModel(HttpServletRequest request) {
        model.ChucVu cv = new model.ChucVu();
        cv.setMaChucVu(request.getParameter("maChucVu"));
        cv.setTenChucVu(request.getParameter("tenChucVu"));
        cv.setMoTa(request.getParameter("moTa"));
        
        String hs = request.getParameter("heSoPhuCap");
        if (hs != null && !hs.isEmpty()) {
            cv.setHeSoPhuCap(Double.parseDouble(hs));
        }

        String tt = request.getParameter("trangThai");
        if (tt != null && !tt.isEmpty()) {
            cv.setTrangThai(Integer.parseInt(tt));
        }
        return cv;
    }
}
