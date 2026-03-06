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
public class PhongBanServlet extends HttpServlet {
    
    private PhongBanService phongBanService = new PhongBanService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "list":
                    hienThiDanhSach(request, response);
                    break;
                case "delete":
                    xoaPhongBan(request, response);
                    break;
                case "edit":
                    hienThiFormSua(request, response);
                    break;
                default:
                    hienThiDanhSach(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        try {
            if ("insert".equals(action)) {
                themPhongBan(request, response);
            } else if ("update".equals(action)) {
                suaPhongBan(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hienThiDanhSach(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<PhongBan> ds = phongBanService.layTatCa();
        request.setAttribute("dsPhongBan", ds);
        request.getRequestDispatcher("phong-ban-list.jsp").forward(request, response);
    }

    private void themPhongBan(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        PhongBan pb = new PhongBan();
        pb.setMaPhongBan(request.getParameter("maPhongBan"));
        pb.setTenPhongBan(request.getParameter("tenPhongBan"));
        
        String chaId = request.getParameter("phongBanChaId");
        if (chaId != null && !chaId.isEmpty()) pb.setPhongBanChaId(Integer.parseInt(chaId));
        
        String tpId = request.getParameter("truongPhongId");
        if (tpId != null && !tpId.isEmpty()) pb.setTruongPhongId(Integer.parseInt(tpId));
        
        pb.setMoTa(request.getParameter("moTa"));
        pb.setTrangThai(Integer.parseInt(request.getParameter("trangThai")));

        phongBanService.them(pb);
        response.sendRedirect("PhongBanServlet?action=list");
    }

    private void suaPhongBan(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        PhongBan pb = new PhongBan();
        pb.setPhongBanId(Integer.parseInt(request.getParameter("id")));
        pb.setMaPhongBan(request.getParameter("maPhongBan"));
        pb.setTenPhongBan(request.getParameter("tenPhongBan"));
        
        String chaId = request.getParameter("phongBanChaId");
        pb.setPhongBanChaId((chaId != null && !chaId.isEmpty()) ? Integer.parseInt(chaId) : null);
        
        String tpId = request.getParameter("truongPhongId");
        pb.setTruongPhongId((tpId != null && !tpId.isEmpty()) ? Integer.parseInt(tpId) : null);

        pb.setMoTa(request.getParameter("moTa"));
        pb.setTrangThai(Integer.parseInt(request.getParameter("trangThai")));

        phongBanService.sua(pb);
        response.sendRedirect("PhongBanServlet?action=list");
    }

    private void xoaPhongBan(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        phongBanService.xoa(id);
        response.sendRedirect("PhongBanServlet?action=list");
    }

    private void hienThiFormSua(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        PhongBan pb = phongBanService.layTheoId(id);
        request.setAttribute("phongBan", pb);
        request.getRequestDispatcher("phong-ban-form.jsp").forward(request, response);
    }
}

