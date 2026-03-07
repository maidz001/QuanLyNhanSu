package controller;

import ConnDatabase.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.PhongBan;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/phongban")
public class PhongBanServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if(action == null) action = "";

        switch(action){
            case "xoa": xoaPhongBan(request,response); break;
            case "xem": xemChiTiet(request,response); break;
            case "sua": moFormSua(request,response); break;
            default: danhSachPhongBan(request,response);
        }
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if(action == null) action = "";

        switch(action){
            case "them": themPhongBan(request,response); break;
            case "capnhat": capNhatPhongBan(request,response); break;
        }
    }

    // ================= DANH SÁCH =================

    private void danhSachPhongBan(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        List<PhongBan> ds = new ArrayList<>();

        try{

            Connection conn = DBConnection.layKetNoi();

            PreparedStatement pst = conn.prepareStatement(
                    "select pb.*, nv.ho_ten as ten_truong_phong " +
                            "from phong_ban pb " +
                            "left join nhan_vien nv on pb.truong_phong_id = nv.nhan_vien_id"
            );

            ResultSet rs = pst.executeQuery();

            while(rs.next()) ds.add(mapRow(rs));

        }catch(Exception e){
            e.printStackTrace();
        }

        request.setAttribute("dsPhongBan",ds);

        request.getRequestDispatcher("phongban-list.jsp")
                .forward(request,response);
    }

    // ================= THÊM =================

    private void themPhongBan(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        try{

            Connection conn = DBConnection.layKetNoi();

            PreparedStatement pst = conn.prepareStatement(
                    "insert into phong_ban(ma_phong_ban,ten_phong_ban,phong_ban_cha_id,truong_phong_id,mo_ta,trang_thai) values(?,?,?,?,?,?)"
            );

            pst.setString(1,request.getParameter("maPhongBan"));
            pst.setString(2,request.getParameter("tenPhongBan"));
            pst.setObject(3,request.getParameter("phongBanChaId"));
            pst.setObject(4,request.getParameter("truongPhongId"));
            pst.setString(5,request.getParameter("moTa"));
            pst.setInt(6,Integer.parseInt(request.getParameter("trangThai")));

            pst.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }

        response.sendRedirect("phongban");
    }

    // ================= CẬP NHẬT =================

    private void capNhatPhongBan(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        try{

            Connection conn = DBConnection.layKetNoi();

            PreparedStatement pst = conn.prepareStatement(
                    "update phong_ban set ma_phong_ban=?,ten_phong_ban=?,phong_ban_cha_id=?,truong_phong_id=?,mo_ta=?,trang_thai=? where phong_ban_id=?"
            );

            pst.setString(1,request.getParameter("maPhongBan"));
            pst.setString(2,request.getParameter("tenPhongBan"));
            pst.setObject(3,request.getParameter("phongBanChaId"));
            pst.setObject(4,request.getParameter("truongPhongId"));
            pst.setString(5,request.getParameter("moTa"));
            pst.setInt(6,Integer.parseInt(request.getParameter("trangThai")));
            pst.setInt(7,Integer.parseInt(request.getParameter("phongBanId")));

            pst.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }

        response.sendRedirect("phongban");
    }

    // ================= XÓA =================

    private void xoaPhongBan(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        try{

            int id = Integer.parseInt(request.getParameter("id"));

            Connection conn = DBConnection.layKetNoi();

            PreparedStatement pst = conn.prepareStatement(
                    "delete from phong_ban where phong_ban_id=?"
            );

            pst.setInt(1,id);

            pst.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }

        response.sendRedirect("phongban");
    }

    // ================= XEM CHI TIẾT =================

    private void xemChiTiet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        PhongBan pb = null;

        try{

            Connection conn = DBConnection.layKetNoi();

            PreparedStatement pst = conn.prepareStatement(
                    "select * from phong_ban where phong_ban_id=?"
            );

            pst.setInt(1,id);

            ResultSet rs = pst.executeQuery();

            if(rs.next()) pb = mapRow(rs);

        }catch(Exception e){
            e.printStackTrace();
        }

        request.setAttribute("phongBan",pb);

        request.getRequestDispatcher("phongban-form.jsp")
                .forward(request,response);
    }

    // ================= FORM SỬA =================

    private void moFormSua(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        xemChiTiet(request,response);
    }

    // ================= MAP DATA =================

    private PhongBan mapRow(ResultSet rs) throws SQLException {

        PhongBan pb = new PhongBan();

        pb.setPhongBanId(rs.getInt("phong_ban_id"));
        pb.setMaPhongBan(rs.getString("ma_phong_ban"));
        pb.setTenPhongBan(rs.getString("ten_phong_ban"));
        pb.setPhongBanChaId((Integer)rs.getObject("phong_ban_cha_id"));
        pb.setTruongPhongId((Integer)rs.getObject("truong_phong_id"));
        pb.setMoTa(rs.getString("mo_ta"));
        pb.setTrangThai(rs.getInt("trang_thai"));

        try{
            pb.setTenTruongPhong(rs.getString("ten_truong_phong"));
        }catch(Exception ignored){}

        return pb;
    }
}