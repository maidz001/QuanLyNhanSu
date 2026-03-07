package controller;

import ConnDatabase.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.ChamCong;

import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

@WebServlet("/chamcong")
public class ChamCongServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        String action=request.getParameter("action");
        if(action==null) action="";

        switch(action){
            case "xoa": xoaChamCong(request,response); break;
            case "xem": xemChiTiet(request,response); break;
            case "sua": moFormSua(request,response); break;
            default: danhSachChamCong(request,response);
        }
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action=request.getParameter("action");
        if(action==null) action="";

        switch(action){
            case "them": themChamCong(request,response); break;
            case "capnhat": capNhatChamCong(request,response); break;
        }
    }

    // ================= DANH SÁCH =================

    private void danhSachChamCong(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        List<ChamCong> ds=new ArrayList<>();

        try{

            Connection conn=DBConnection.layKetNoi();

            PreparedStatement pst=conn.prepareStatement(
                    "select cc.*, nv.ho_ten, nv.ma_nhan_vien " +
                            "from cham_cong cc join nhan_vien nv on cc.nhan_vien_id=nv.nhan_vien_id"
            );

            ResultSet rs=pst.executeQuery();

            while(rs.next()) ds.add(mapRow(rs));

        }catch(Exception e){
            e.printStackTrace();
        }

        request.setAttribute("dsChamCong",ds);

        request.getRequestDispatcher("chamcong-list.jsp")
                .forward(request,response);
    }

    // ================= THÊM =================

    private void themChamCong(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        try{

            Connection conn=DBConnection.layKetNoi();

            PreparedStatement pst=conn.prepareStatement(
                    "insert into cham_cong(nhan_vien_id,ngay_cham_cong,gio_vao,gio_ra,trang_thai,ghi_chu) values(?,?,?,?,?,?)"
            );

            pst.setInt(1,Integer.parseInt(request.getParameter("nhanVienId")));
            pst.setDate(2,Date.valueOf(request.getParameter("ngay")));
            pst.setString(3,request.getParameter("gioVao"));
            pst.setString(4,request.getParameter("gioRa"));
            pst.setString(5,request.getParameter("trangThai"));
            pst.setString(6,request.getParameter("ghiChu"));

            pst.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }

        response.sendRedirect("chamcong");
    }

    // ================= CẬP NHẬT =================

    private void capNhatChamCong(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        try{

            Connection conn=DBConnection.layKetNoi();

            PreparedStatement pst=conn.prepareStatement(
                    "update cham_cong set nhan_vien_id=?,ngay_cham_cong=?,gio_vao=?,gio_ra=?,trang_thai=?,ghi_chu=? where cham_cong_id=?"
            );

            pst.setInt(1,Integer.parseInt(request.getParameter("nhanVienId")));
            pst.setDate(2, Date.valueOf(request.getParameter("ngay")));
            pst.setString(3,request.getParameter("gioVao"));
            pst.setString(4,request.getParameter("gioRa"));
            pst.setString(5,request.getParameter("trangThai"));
            pst.setString(6,request.getParameter("ghiChu"));
            pst.setInt(7,Integer.parseInt(request.getParameter("chamCongId")));

            pst.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }

        response.sendRedirect("chamcong");
    }

    // ================= XÓA =================

    private void xoaChamCong(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        try{

            int id=Integer.parseInt(request.getParameter("id"));

            Connection conn=DBConnection.layKetNoi();

            PreparedStatement pst=conn.prepareStatement(
                    "delete from cham_cong where cham_cong_id=?"
            );

            pst.setInt(1,id);

            pst.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }

        response.sendRedirect("chamcong");
    }

    // ================= XEM CHI TIẾT =================

    private void xemChiTiet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        int id=Integer.parseInt(request.getParameter("id"));

        ChamCong cc=null;

        try{

            Connection conn=DBConnection.layKetNoi();

            PreparedStatement pst=conn.prepareStatement(
                    "select * from cham_cong where cham_cong_id=?"
            );

            pst.setInt(1,id);

            ResultSet rs=pst.executeQuery();

            if(rs.next()) cc=mapRow(rs);

        }catch(Exception e){
            e.printStackTrace();
        }

        request.setAttribute("chamCong",cc);

        request.getRequestDispatcher("chamcong-form.jsp")
                .forward(request,response);
    }

    // ================= FORM SỬA =================

    private void moFormSua(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        xemChiTiet(request,response);
    }

    // ================= MAP DATA =================

    private ChamCong mapRow(ResultSet rs) throws SQLException{

        ChamCong cc=new ChamCong();

        cc.setChamCongId(rs.getInt("cham_cong_id"));
        cc.setNhanVienId(rs.getInt("nhan_vien_id"));
        cc.setNgayChamCong(rs.getDate("ngay_cham_cong"));
        cc.setGioVao(rs.getString("gio_vao"));
        cc.setGioRa(rs.getString("gio_ra"));
        cc.setTrangThai(rs.getString("trang_thai"));
        cc.setGhiChu(rs.getString("ghi_chu"));

        try{
            cc.setHoTen(rs.getString("ho_ten"));
            cc.setMaNhanVien(rs.getString("ma_nhan_vien"));
        }catch(Exception ignored){}

        return cc;
    }
}