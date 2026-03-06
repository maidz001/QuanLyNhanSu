package controller;

import ConnDatabase.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.ChamCong;
import service.ChamCongService;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/ChamCong")
public class ChamCong extends HttpServlet {

    private ChamCongService chamCongService = new ChamCongService();

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
                xoaChamCong(request, response);
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
            themChamCong(request, response);
        } else if ("update".equals(action)) {
            suaChamCong(request, response);
        }
    }

    private void hienThiDanhSach(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<model.ChamCong> ds = chamCongService.layTatCa();
        request.setAttribute("dsChamCong", ds);
        request.getRequestDispatcher("chamcong-list.jsp").forward(request, response);
    }

    private void hienThiFormSua(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            model.ChamCong cc = chamCongService.layTheoId(id);
            request.setAttribute("chamCong", cc);
        }
        request.getRequestDispatcher("chamcong-form.jsp").forward(request, response);
    }

    private void themChamCong(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        model.ChamCong cc = mapRequestToModel(request);
        chamCongService.them(cc);
        response.sendRedirect("ChamCong?action=list");
    }

    private void suaChamCong(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        model.ChamCong cc = mapRequestToModel(request);
        String idStr = request.getParameter("id");
        if (idStr != null) {
            cc.setChamCongId(Integer.parseInt(idStr));
            chamCongService.sua(cc);
        }
        response.sendRedirect("ChamCong?action=list");
    }

    private void xoaChamCong(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            chamCongService.xoa(id);
        }
        response.sendRedirect("ChamCong?action=list");
    }

    private model.ChamCong mapRequestToModel(HttpServletRequest request) {
        model.ChamCong cc = new model.ChamCong();
        
        String nvId = request.getParameter("nhanVienId");
        if (nvId != null && !nvId.isEmpty()) {
            cc.setNhanVienId(Integer.parseInt(nvId));
        }

        String ngayStr = request.getParameter("ngay");
        if (ngayStr != null && !ngayStr.isEmpty()) {
            cc.setNgay(java.sql.Date.valueOf(ngayStr));
        }

        cc.setGioVao(request.getParameter("gioVao"));
        cc.setGioRa(request.getParameter("gioRa"));
        
        String tt = request.getParameter("trangThai");
        if (tt != null && !tt.isEmpty()) {
            cc.setTrangThai(Integer.parseInt(tt));
        }
        
        cc.setGhiChu(request.getParameter("ghiChu"));
        
        return cc;
    }
}
