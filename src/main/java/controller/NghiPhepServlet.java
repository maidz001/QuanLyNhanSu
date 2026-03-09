package controller;

import model.NghiPhep;
import service.NghiPhepService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/nghiphep")
public class NghiPhepServlet extends HttpServlet {

    private NghiPhepService nghiPhepService = new NghiPhepService();

    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        String action=request.getParameter("action");
        if(action==null) action="";

        switch(action){
            case "xoa": xoaNghiPhep(request,response); break;
            case "xem": xemChiTiet(request,response); break;
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
            case "them": themNghiPhep(request,response); break;
            case "capnhat":; break;
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
            throws IOException {

        NghiPhep np = new NghiPhep();

        np.setNhanVienId(Integer.parseInt(request.getParameter("nhanVienId")));
        np.setLoaiPhep(request.getParameter("loaiPhep"));
        np.setNgayBatDau(Date.valueOf(request.getParameter("ngayBatDau")));
        np.setNgayKetThuc(Date.valueOf(request.getParameter("ngayKetThuc")));
        np.setLyDo(request.getParameter("lyDo"));
        np.setTrangThai(request.getParameter("trangThai"));

        nghiPhepService.them(np);

        response.sendRedirect("nghiphep");
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
}