package controller;

import ConnDatabase.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.TaiKhoan;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/taikhoan")
public class TaiKhoanServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        String action=request.getParameter("action");
        if(action==null) action="";

        switch(action){
            case "xoa": xoaTaiKhoan(request,response); break;
            case "xem": xemChiTiet(request,response); break;
            case "login": moFormLogin(request,response); break;
            case "signin": moFormSignIn(request,response); break;
            default: moFormLogin(request,response);
        }
    }



    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        String action=request.getParameter("action");
        if(action==null) action="";

        switch(action){
            case "dangnhap": dangNhap(request,response); break;
            case "dangky": dangKy(request,response); break;
            case "sua": capNhatTaiKhoan(request,response); break;
        }
    }


    private void dangNhap(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        try{

            String taiKhoan=request.getParameter("taiKhoan");
            String matKhau=request.getParameter("matKhau");

            Connection conn=DBConnection.layKetNoi();

            PreparedStatement pstt=conn.prepareStatement(
                    "select * from tai_khoan where ten_dang_nhap=? and mat_khau=?"
            );

            pstt.setString(1,taiKhoan);
            pstt.setString(2,matKhau);

            ResultSet rs=pstt.executeQuery();

            TaiKhoan tk=null;

            if(rs.next()) tk=mapRow(rs);

            if(tk!=null){

                HttpSession session=request.getSession();
                session.setAttribute("taiKhoanDangDangNhap",tk);

                if(tk.getVaiTro().equalsIgnoreCase("nhanvien"))
                    response.sendRedirect(request.getContextPath()+"/TrangChuNhanVien.jsp");
                else
                    response.sendRedirect(request.getContextPath()+"/TrangChuQuanLy.jsp");

            }else{

                request.setAttribute("message","tai khoan khong ton tai");

                request.getRequestDispatcher("/WEB-INF/view/taikhoanview/LogIn.jsp")
                        .forward(request,response);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void moFormSignIn(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        request.getRequestDispatcher("/WEB-INF/view/taikhoanview/SignIn.jsp")
                .forward(request, response);
    }

    private void dangKy(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {

        try{

            Connection conn=DBConnection.layKetNoi();

            PreparedStatement pstt=conn.prepareStatement(
                    "insert into tai_khoan(nhan_vien_id,ten_dang_nhap,mat_khau,vai_tro,trang_thai) values(?,?,?,?,?)"
            );

            pstt.setInt(1,Integer.parseInt(request.getParameter("nhanVienId")));
            pstt.setString(2,request.getParameter("tenDangNhap"));
            pstt.setString(3,request.getParameter("matKhau"));
            pstt.setString(4,request.getParameter("vaiTro"));
            pstt.setInt(5,Integer.parseInt(request.getParameter("trangThai")));

            pstt.executeUpdate();

            response.sendRedirect(request.getContextPath()+"/taikhoan?action=login");

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private void moFormLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        request.getRequestDispatcher("/WEB-INF/view/taikhoanview/LogIn.jsp")
                .forward(request, response);
    }


    private void capNhatTaiKhoan(HttpServletRequest request,HttpServletResponse response) throws IOException {

        try{

            Connection conn=DBConnection.layKetNoi();

            PreparedStatement pstt=conn.prepareStatement(
                    "update tai_khoan set ten_dang_nhap=?,mat_khau=?,vai_tro=?,trang_thai=? where tai_khoan_id=?"
            );

            pstt.setString(1,request.getParameter("tenDangNhap"));
            pstt.setString(2,request.getParameter("matKhau"));
            pstt.setString(3,request.getParameter("vaiTro"));
            pstt.setInt(4,Integer.parseInt(request.getParameter("trangThai")));
            pstt.setInt(5,Integer.parseInt(request.getParameter("taiKhoanId")));

            pstt.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }

        response.sendRedirect("taikhoan");
    }


    private void xoaTaiKhoan(HttpServletRequest request,HttpServletResponse response) throws IOException {

        try{

            int id=Integer.parseInt(request.getParameter("id"));

            Connection conn=DBConnection.layKetNoi();

            PreparedStatement pstt=conn.prepareStatement(
                    "delete from tai_khoan where tai_khoan_id=?"
            );

            pstt.setInt(1,id);

            pstt.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }

        response.sendRedirect("taikhoan");
    }


    private void xemChiTiet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        int id=Integer.parseInt(request.getParameter("id"));

        TaiKhoan tk=null;

        try{

            Connection conn=DBConnection.layKetNoi();

            PreparedStatement pstt=conn.prepareStatement(
                    "select * from tai_khoan where tai_khoan_id=?"
            );

            pstt.setInt(1,id);

            ResultSet rs=pstt.executeQuery();

            if(rs.next()) tk=mapRow(rs);

        }catch(Exception e){
            e.printStackTrace();
        }

        request.setAttribute("tk",tk);

        request.getRequestDispatcher("ChiTietTaiKhoan.jsp")
                .forward(request,response);
    }


    private List<TaiKhoan> mapList(ResultSet rs) throws SQLException {

        List<TaiKhoan> listTK=new ArrayList<>();

        while(rs.next()) listTK.add(mapRow(rs));

        return listTK;
    }


    private TaiKhoan mapRow(ResultSet rs) throws SQLException {

        return new TaiKhoan(

                rs.getInt("tai_khoan_id"),
                rs.getInt("nhan_vien_id"),
                rs.getString("ten_dang_nhap"),
                rs.getString("mat_khau"),
                rs.getString("vai_tro"),
                rs.getInt("trang_thai"),
                rs.getTimestamp("ngay_tao")

        );
    }
}