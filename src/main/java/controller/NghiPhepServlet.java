package controller;

import ConnDatabase.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.NghiPhep;

import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

@WebServlet("/nghiphep")
public class NghiPhepServlet extends HttpServlet {

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
            case "capnhat": capNhatNghiPhep(request,response); break;
        }
    }

    // ================= DANH SÁCH =================

    private void danhSachNghiPhep(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        List<NghiPhep> ds=new ArrayList<>();

        try{

            Connection conn=DBConnection.layKetNoi();

            PreparedStatement pst=conn.prepareStatement(
                    "select np.*, nv.ho_ten, nv.ma_nhan_vien " +
                            "from nghi_phep np join nhan_vien nv on np.nhan_vien_id=nv.nhan_vien_id"
            );

            ResultSet rs=pst.executeQuery();

            while(rs.next()) ds.add(mapRow(rs));

        }catch(Exception e){
            e.printStackTrace();
        }

        request.setAttribute("dsNghiPhep",ds);

        request.getRequestDispatcher("nghiphep-list.jsp")
                .forward(request,response);
    }

    // ================= THÊM =================

    private void themNghiPhep(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        try{

            Connection conn=DBConnection.layKetNoi();

            PreparedStatement pst=conn.prepareStatement(
                    "insert into nghi_phep(nhan_vien_id,loai_phep,ngay_bat_dau,ngay_ket_thuc,ly_do,trang_thai) values(?,?,?,?,?,?)"
            );

            pst.setInt(1,Integer.parseInt(request.getParameter("nhanVienId")));
            pst.setString(2,request.getParameter("loaiPhep"));
            pst.setDate(3,Date.valueOf(request.getParameter("ngayBatDau")));
            pst.setDate(4,Date.valueOf(request.getParameter("ngayKetThuc")));
            pst.setString(5,request.getParameter("lyDo"));
            pst.setString(6,request.getParameter("trangThai"));

            pst.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }

        response.sendRedirect("nghiphep");
    }

    // ================= CẬP NHẬT =================

    private void capNhatNghiPhep(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        try{

            Connection conn=DBConnection.layKetNoi();

            PreparedStatement pst=conn.prepareStatement(
                    "update nghi_phep set nhan_vien_id=?,loai_phep=?,ngay_bat_dau=?,ngay_ket_thuc=?,ly_do=?,trang_thai=? where nghi_phep_id=?"
            );

            pst.setInt(1,Integer.parseInt(request.getParameter("nhanVienId")));
            pst.setString(2,request.getParameter("loaiPhep"));
            pst.setDate(3, Date.valueOf(request.getParameter("ngayBatDau")));
            pst.setDate(4,Date.valueOf(request.getParameter("ngayKetThuc")));
            pst.setString(5,request.getParameter("lyDo"));
            pst.setString(6,request.getParameter("trangThai"));
            pst.setInt(7,Integer.parseInt(request.getParameter("nghiPhepId")));

            pst.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }

        response.sendRedirect("nghiphep");
    }

    // ================= XÓA =================

    private void xoaNghiPhep(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        try{

            int id=Integer.parseInt(request.getParameter("id"));

            Connection conn=DBConnection.layKetNoi();

            PreparedStatement pst=conn.prepareStatement(
                    "delete from nghi_phep where nghi_phep_id=?"
            );

            pst.setInt(1,id);

            pst.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }

        response.sendRedirect("nghiphep");
    }

    // ================= XEM CHI TIẾT =================

    private void xemChiTiet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        int id=Integer.parseInt(request.getParameter("id"));

        NghiPhep np=null;

        try{

            Connection conn=DBConnection.layKetNoi();

            PreparedStatement pst=conn.prepareStatement(
                    "select * from nghi_phep where nghi_phep_id=?"
            );

            pst.setInt(1,id);

            ResultSet rs=pst.executeQuery();

            if(rs.next()) np=mapRow(rs);

        }catch(Exception e){
            e.printStackTrace();
        }

        request.setAttribute("nghiPhep",np);

        request.getRequestDispatcher("nghiphep-form.jsp")
                .forward(request,response);
    }

    // ================= FORM SỬA =================

    private void moFormSua(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        xemChiTiet(request,response);
    }

    // ================= MAP DATA =================

    private NghiPhep mapRow(ResultSet rs) throws SQLException{

        NghiPhep np=new NghiPhep();

        np.setNghiPhepId(rs.getInt("nghi_phep_id"));
        np.setNhanVienId(rs.getInt("nhan_vien_id"));
        np.setLoaiPhep(rs.getString("loai_phep"));
        np.setNgayBatDau(rs.getDate("ngay_bat_dau"));
        np.setNgayKetThuc(rs.getDate("ngay_ket_thuc"));
        np.setLyDo(rs.getString("ly_do"));
        np.setTrangThai(rs.getString("trang_thai"));

        try{
            np.setHoTen(rs.getString("ho_ten"));
            np.setMaNhanVien(rs.getString("ma_nhan_vien"));
        }catch(Exception ignored){}

        return np;
    }
}