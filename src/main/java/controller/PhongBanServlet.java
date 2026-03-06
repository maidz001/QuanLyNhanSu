package controller;

import dao.PhongBanDAO;
import model.PhongBan;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/phongban")
public class PhongBanServlet extends HttpServlet {

    private PhongBanDAO dao;

    @Override
    public void init() {
        dao = new PhongBanDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {

            case "add":
                request.getRequestDispatcher("/phongban/add.jsp")
                        .forward(request, response);
                break;

            case "edit":
                hienFormSua(request, response);
                break;

            case "delete":
                xoaPhongBan(request, response);
                break;

            default:
                danhSachPhongBan(request, response);
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
  
    private void danhSachPhongBan(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {

        List<PhongBan> ds = dao.layTatCa();
        request.setAttribute("dsPhongBan", ds);
        request.getRequestDispatcher("/phongban/list.jsp")
                .forward(request, response);
    }

    private void themPhongBan(HttpServletRequest request,
                              HttpServletResponse response)
            throws IOException {

        PhongBan pb = new PhongBan();

        pb.setMaPhongBan(request.getParameter("maPhongBan"));
        pb.setTenPhongBan(request.getParameter("tenPhongBan"));
        pb.setMoTa(request.getParameter("moTa"));
        pb.setTrangThai(Integer.parseInt(request.getParameter("trangThai")));

        String pbCha = request.getParameter("phongBanChaId");
        if (pbCha != null && !pbCha.isEmpty())
            pb.setPhongBanChaId(Integer.parseInt(pbCha));
        else
            pb.setPhongBanChaId(null);

        String tp = request.getParameter("truongPhongId");
        if (tp != null && !tp.isEmpty())
            pb.setTruongPhongId(Integer.parseInt(tp));
        else
            pb.setTruongPhongId(null);

        dao.them(pb);
        response.sendRedirect("phongban");
    }

    private void hienFormSua(HttpServletRequest request,
                             HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        PhongBan pb = dao.layTheoId(id);

        request.setAttribute("phongBan", pb);
        request.getRequestDispatcher("/phongban/edit.jsp")
                .forward(request, response);
    }

    private void suaPhongBan(HttpServletRequest request,
                             HttpServletResponse response)
            throws IOException {

        PhongBan pb = new PhongBan();

        pb.setPhongBanId(Integer.parseInt(request.getParameter("phongBanId")));
        pb.setMaPhongBan(request.getParameter("maPhongBan"));
        pb.setTenPhongBan(request.getParameter("tenPhongBan"));
        pb.setMoTa(request.getParameter("moTa"));
        pb.setTrangThai(Integer.parseInt(request.getParameter("trangThai")));

        String pbCha = request.getParameter("phongBanChaId");
        if (pbCha != null && !pbCha.isEmpty())
            pb.setPhongBanChaId(Integer.parseInt(pbCha));
        else
            pb.setPhongBanChaId(null);

        String tp = request.getParameter("truongPhongId");
        if (tp != null && !tp.isEmpty())
            pb.setTruongPhongId(Integer.parseInt(tp));
        else
            pb.setTruongPhongId(null);

        dao.sua(pb);
        response.sendRedirect("phongban");
    }

    private void xoaPhongBan(HttpServletRequest request,
                             HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        dao.xoa(id);
        response.sendRedirect("phongban");
    }
}
