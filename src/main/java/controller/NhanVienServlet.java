package controller;

import ConnDatabase.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.NhanVien;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/nhanvien")
public class NhanVienServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String action=request.getParameter("action");
        if(action==null) action="list";
        switch(action){
            case "them": request.getRequestDispatcher("ThemNhanVien.jsp").forward(request,response); break;
            case "sua": suaForm(request,response); break;
            case "xoa": xoaNhanVien(request,response); break;
            case "xem": xemChiTiet(request,response); break;
            default: danhSachNhanVien(request,response);
        }
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String action=request.getParameter("action");
        switch(action){
            case "them": themNhanVien(request,response); break;
            case "sua": capNhatNhanVien(request,response); break;
        }
    }

    private void danhSachNhanVien(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        List<NhanVien> list=new ArrayList<>();
        try{
            Connection conn=DBConnection.layKetNoi();
            PreparedStatement pstt=conn.prepareStatement("select * from nhan_vien");
            ResultSet rs=pstt.executeQuery();
            list=mapList(rs);
        }catch(Exception e){e.printStackTrace();}
        request.setAttribute("list",list);
        request.getRequestDispatcher("DanhSachNhanVien.jsp").forward(request,response);
    }

    private void themNhanVien(HttpServletRequest request,HttpServletResponse response) throws IOException {
        try{
            Connection conn=DBConnection.layKetNoi();
            PreparedStatement pstt=conn.prepareStatement("insert into nhan_vien(maNhanVien,hoTen,email,dienThoai,diaChi,phongBanId,chucVuId,trangThai) values(?,?,?,?,?,?,?,?)");
            pstt.setString(1,request.getParameter("maNhanVien"));
            pstt.setString(2,request.getParameter("hoTen"));
            pstt.setString(3,request.getParameter("email"));
            pstt.setString(4,request.getParameter("dienThoai"));
            pstt.setString(5,request.getParameter("diaChi"));
            pstt.setInt(6,Integer.parseInt(request.getParameter("phongBanId")));
            pstt.setInt(7,Integer.parseInt(request.getParameter("chucVuId")));
            pstt.setString(8,"hoatdong");
            pstt.executeUpdate();
        }catch(Exception e){e.printStackTrace();}
        response.sendRedirect("nhanvien");
    }

    private void suaForm(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        int id=Integer.parseInt(request.getParameter("id"));
        NhanVien nv=new NhanVien();
        try{
            Connection conn=DBConnection.layKetNoi();
            PreparedStatement pstt=conn.prepareStatement("select * from nhan_vien where nhanVienId=?");
            pstt.setInt(1,id);
            ResultSet rs=pstt.executeQuery();
            if(rs.next()) nv=mapRow(rs);
        }catch(Exception e){e.printStackTrace();}
        request.setAttribute("nv",nv);
        request.getRequestDispatcher("SuaNhanVien.jsp").forward(request,response);
    }

    private void capNhatNhanVien(HttpServletRequest request,HttpServletResponse response) throws IOException {
        try{
            Connection conn=DBConnection.layKetNoi();
            PreparedStatement pstt=conn.prepareStatement("update nhan_vien set hoTen=?,email=?,dienThoai=?,diaChi=? where nhanVienId=?");
            pstt.setString(1,request.getParameter("hoTen"));
            pstt.setString(2,request.getParameter("email"));
            pstt.setString(3,request.getParameter("dienThoai"));
            pstt.setString(4,request.getParameter("diaChi"));
            pstt.setInt(5,Integer.parseInt(request.getParameter("nhanVienId")));
            pstt.executeUpdate();
        }catch(Exception e){e.printStackTrace();}
        response.sendRedirect("nhanvien");
    }

    private void xoaNhanVien(HttpServletRequest request,HttpServletResponse response) throws IOException {
        try{
            int id=Integer.parseInt(request.getParameter("id"));
            Connection conn=DBConnection.layKetNoi();
            PreparedStatement pstt=conn.prepareStatement("delete from nhan_vien where nhanVienId=?");
            pstt.setInt(1,id);
            pstt.executeUpdate();
        }catch(Exception e){e.printStackTrace();}
        response.sendRedirect("nhanvien");
    }

    private void xemChiTiet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        int id=Integer.parseInt(request.getParameter("id"));
        NhanVien nv=new NhanVien();
        try{
            Connection conn=DBConnection.layKetNoi();
            PreparedStatement pstt=conn.prepareStatement("select * from nhan_vien where nhanVienId=?");
            pstt.setInt(1,id);
            ResultSet rs=pstt.executeQuery();
            if(rs.next()) nv=mapRow(rs);
        }catch(Exception e){e.printStackTrace();}
        request.setAttribute("nv",nv);
        request.getRequestDispatcher("ChiTietNhanVien.jsp").forward(request,response);
    }
    private NhanVien mapRow(ResultSet rs) throws SQLException {
        NhanVien nv=new NhanVien();
        nv.setNhanVienId(rs.getInt("nhanVienId"));
        nv.setMaNhanVien(rs.getString("maNhanVien"));
        nv.setHoTen(rs.getString("hoTen"));
        nv.setEmail(rs.getString("email"));
        nv.setDienThoai(rs.getString("dienThoai"));
        nv.setDiaChi(rs.getString("diaChi"));
        return nv;
    }

    private List<NhanVien> mapList(ResultSet rs) throws SQLException {
        List<NhanVien> list=new ArrayList<>();
        while(rs.next()) list.add(mapRow(rs));
        return list;
    }
}