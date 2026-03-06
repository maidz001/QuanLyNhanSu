package controller;

import ConnDatabase.DBConnection; 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.PhongBan; 
import service.PhongBanService; 

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/PhongBan")
public class PhongBan extends HttpServlet {

    private PhongBanService phongBanService = new PhongBanService(); //

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
                xoaPhongBan(request, response);
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
            themPhongBan(request, response);
        } else if ("update".equals(action)) {
            suaPhongBan(request, response);
        }
    }

    private void hienThiDanhSach(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<model.PhongBan> ds = phongBanService.layTatCa(); 
        request.setAttribute("dsPhongBan", ds);
        request.getRequestDispatcher("phongban-list.jsp").forward(request, response);
    }

    private void hienThiFormSua(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            model.PhongBan pb = phongBanService.layTheoId(id); 
            request.setAttribute("phongBan", pb);
        }
        request.getRequestDispatcher("phongban-form.jsp").forward(request, response);
    }

    private void themPhongBan(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        model.PhongBan pb = mapRequestToModel(request);
        phongBanService.them(pb); 
        response.sendRedirect("PhongBan?action=list");
    }

    private void suaPhongBan(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        model.PhongBan pb = mapRequestToModel(request);
        String idStr = request.getParameter("id");
        if (idStr != null) {
            pb.setPhongBanId(Integer.parseInt(idStr));
            phongBanService.sua(pb); 
        }
        response.sendRedirect("PhongBan?action=list");
    }

    private void xoaPhongBan(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            phongBanService.xoa(id); 
        }
        response.sendRedirect("PhongBan?action=list");
    }

    private model.PhongBan mapRequestToModel(HttpServletRequest request) {
        model.PhongBan pb = new model.PhongBan();
        pb.setMaPhongBan(request.getParameter("maPhongBan"));
        pb.setTenPhongBan(request.getParameter("tenPhongBan"));
        
        String chaId = request.getParameter("phongBanChaId");
        if (chaId != null && !chaId.isEmpty()) {
            pb.setPhongBanChaId(Integer.parseInt(chaId));
        }

        String tpId = request.getParameter("truongPhongId");
        if (tpId != null && !tpId.isEmpty()) {
            pb.setTruongPhongId(Integer.parseInt(tpId));
        }

        pb.setMoTa(request.getParameter("moTa"));
        
        String trangThai = request.getParameter("trangThai");
        if (trangThai != null) {
            pb.setTrangThai(Integer.parseInt(trangThai));
        }
        return pb;
    }
}
