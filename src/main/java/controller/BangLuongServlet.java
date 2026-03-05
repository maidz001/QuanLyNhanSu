package controller;

import ConnDatabase.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.BangLuong;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;

@WebServlet("/bangluong")
public class BangLuongServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        request.setCharacterEncoding("UTF-8");
        String action=request.getParameter("action");
        if(action==null)action="list";
        switch(action){
            case "moformthem": showFormThem(request,response);break;
            case "moformsuasua": suaForm(request,response);break;
            case "xoa": xoaBangLuong(request,response);break;
            case "xem": xemChiTiet(request,response);break;
            default: danhSachBangLuong(request,response);
        }
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        request.setCharacterEncoding("UTF-8");
        String action=request.getParameter("action");
        if(action==null)action="";
        switch(action){
            case "them": themBangLuong(request,response);break;
            case "sua": capNhatBangLuong(request,response);break;
            default: response.sendRedirect("bangluong");
        }
    }

    private void danhSachBangLuong(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        List<BangLuong> list=new ArrayList<>();
        String keyword=request.getParameter("keyword");
        String thangStr=request.getParameter("thang");
        String namStr=request.getParameter("nam");

        StringBuilder sql=new StringBuilder(
                "SELECT bl.*,nv.ho_ten,nv.ma_nhan_vien FROM bang_luong bl " +
                        "LEFT JOIN nhan_vien nv ON bl.nhan_vien_id=nv.nhan_vien_id WHERE 1=1 "
        );

        List<Object> params=new ArrayList<>();

        if(keyword!=null && !keyword.trim().isEmpty()){
            sql.append("AND nv.ho_ten LIKE ? ");
            params.add("%"+keyword.trim()+"%");
        }

        if(thangStr!=null && !thangStr.isEmpty()){
            sql.append("AND bl.thang=? ");
            params.add(Integer.parseInt(thangStr));
        }

        if(namStr!=null && !namStr.isEmpty()){
            sql.append("AND bl.nam=? ");
            params.add(Integer.parseInt(namStr));
        }

        sql.append("ORDER BY bl.bang_luong_id DESC");

        try{
            Connection conn= DBConnection.layKetNoi();
            PreparedStatement pstt=conn.prepareStatement(sql.toString());
            for(int i=0;i<params.size();i++) pstt.setObject(i+1,params.get(i));
            ResultSet rs=pstt.executeQuery();
            list=mapList(rs);
        }catch(Exception e){e.printStackTrace();}

        request.setAttribute("list",list);
        request.getRequestDispatcher("DanhSachBangLuong.jsp").forward(request,response);
    }

    private void showFormThem(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        request.setAttribute("danhSachNhanVien",layDanhSachNhanVien());
        request.getRequestDispatcher("ThemBangLuong.jsp").forward(request,response);
    }

    private void themBangLuong(HttpServletRequest request,HttpServletResponse response) throws IOException{
        try{
            Connection conn=DBConnection.layKetNoi();
            PreparedStatement pstt=conn.prepareStatement(
                    "INSERT INTO bang_luong(nhan_vien_id,thang,nam,so_ngay_lam_viec,so_ngay_thuc_te,gio_lam_them,luong_co_ban,phu_cap,luong_lam_them,thuong,tong_thu_nhap,bao_hiem_xa_hoi,bao_hiem_y_te,tam_ung,tong_khau_tru,luong_thuc_lanh,ngay_thanh_toan,trang_thai) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
            );

            pstt.setInt(1,Integer.parseInt(request.getParameter("nhanVienId")));
            pstt.setInt(2,Integer.parseInt(request.getParameter("thang")));
            pstt.setInt(3,Integer.parseInt(request.getParameter("nam")));
            pstt.setBigDecimal(4,new BigDecimal(request.getParameter("soNgayLamViec")));
            pstt.setBigDecimal(5,new BigDecimal(request.getParameter("soNgayThucTe")));
            pstt.setBigDecimal(6,new BigDecimal(request.getParameter("gioLamThem")));
            pstt.setBigDecimal(7,new BigDecimal(request.getParameter("luongCoBan")));
            pstt.setBigDecimal(8,new BigDecimal(request.getParameter("phuCap")));
            pstt.setBigDecimal(9,new BigDecimal(request.getParameter("luongLamThem")));
            pstt.setBigDecimal(10,new BigDecimal(request.getParameter("thuong")));
            pstt.setBigDecimal(11,new BigDecimal(request.getParameter("tongThuNhap")));
            pstt.setBigDecimal(12,new BigDecimal(request.getParameter("baoHiemXaHoi")));
            pstt.setBigDecimal(13,new BigDecimal(request.getParameter("baoHiemYTe")));
            pstt.setBigDecimal(14,new BigDecimal(request.getParameter("tamUng")));
            pstt.setBigDecimal(15,new BigDecimal(request.getParameter("tongKhauTru")));
            pstt.setBigDecimal(16,new BigDecimal(request.getParameter("luongThucLanh")));
            pstt.setDate(17, Date.valueOf(request.getParameter("ngayThanhToan")));
            pstt.setString(18,request.getParameter("trangThai"));

            pstt.executeUpdate();
        }catch(Exception e){e.printStackTrace();}
        response.sendRedirect("bangluong");
    }

    private void suaForm(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        int id=Integer.parseInt(request.getParameter("id"));
        BangLuong bl=new BangLuong();
        try{
            Connection conn=DBConnection.layKetNoi();
            PreparedStatement pstt=conn.prepareStatement(
                    "SELECT bl.*,nv.ho_ten,nv.ma_nhan_vien FROM bang_luong bl LEFT JOIN nhan_vien nv ON bl.nhan_vien_id=nv.nhan_vien_id WHERE bang_luong_id=?"
            );
            pstt.setInt(1,id);
            ResultSet rs=pstt.executeQuery();
            if(rs.next()) bl=mapRow(rs);
        }catch(Exception e){e.printStackTrace();}

        request.setAttribute("bl",bl);
        request.setAttribute("danhSachNhanVien",layDanhSachNhanVien());
        request.getRequestDispatcher("SuaBangLuong.jsp").forward(request,response);
    }

    private void capNhatBangLuong(HttpServletRequest request,HttpServletResponse response) throws IOException{
        try{
            Connection conn=DBConnection.layKetNoi();
            PreparedStatement pstt=conn.prepareStatement(
                    "UPDATE bang_luong SET thuong=?,tong_thu_nhap=?,tong_khau_tru=?,luong_thuc_lanh=?,trang_thai=? WHERE bang_luong_id=?"
            );

            pstt.setBigDecimal(1,new BigDecimal(request.getParameter("thuong")));
            pstt.setBigDecimal(2,new BigDecimal(request.getParameter("tongThuNhap")));
            pstt.setBigDecimal(3,new BigDecimal(request.getParameter("tongKhauTru")));
            pstt.setBigDecimal(4,new BigDecimal(request.getParameter("luongThucLanh")));
            pstt.setString(5,request.getParameter("trangThai"));
            pstt.setInt(6,Integer.parseInt(request.getParameter("bangLuongId")));

            pstt.executeUpdate();
        }catch(Exception e){e.printStackTrace();}
        response.sendRedirect("bangluong");
    }

    private void xoaBangLuong(HttpServletRequest request,HttpServletResponse response) throws IOException{
        try{
            int id=Integer.parseInt(request.getParameter("id"));
            Connection conn=DBConnection.layKetNoi();
            PreparedStatement pstt=conn.prepareStatement("DELETE FROM bang_luong WHERE bang_luong_id=?");
            pstt.setInt(1,id);
            pstt.executeUpdate();
        }catch(Exception e){e.printStackTrace();}
        response.sendRedirect("bangluong");
    }

    private void xemChiTiet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        int id=Integer.parseInt(request.getParameter("id"));
        BangLuong bl=new BangLuong();
        try{
            Connection conn=DBConnection.layKetNoi();
            PreparedStatement pstt=conn.prepareStatement(
                    "SELECT bl.*,nv.ho_ten,nv.ma_nhan_vien FROM bang_luong bl LEFT JOIN nhan_vien nv ON bl.nhan_vien_id=nv.nhan_vien_id WHERE bang_luong_id=?"
            );
            pstt.setInt(1,id);
            ResultSet rs=pstt.executeQuery();
            if(rs.next()) bl=mapRow(rs);
        }catch(Exception e){e.printStackTrace();}
        request.setAttribute("bl",bl);
        request.getRequestDispatcher("ChiTietBangLuong.jsp").forward(request,response);
    }

    private List<Map<String,Object>> layDanhSachNhanVien(){
        List<Map<String,Object>> list=new ArrayList<>();
        try{
            Connection conn=DBConnection.layKetNoi();
            PreparedStatement pstt=conn.prepareStatement("SELECT nhan_vien_id,ma_nhan_vien,ho_ten FROM nhan_vien ORDER BY ho_ten");
            ResultSet rs=pstt.executeQuery();
            while(rs.next()){
                Map<String,Object> nv=new HashMap<>();
                nv.put("nhanVienId",rs.getInt("nhan_vien_id"));
                nv.put("maNhanVien",rs.getString("ma_nhan_vien"));
                nv.put("hoTen",rs.getString("ho_ten"));
                list.add(nv);
            }
        }catch(Exception e){e.printStackTrace();}
        return list;
    }

    private BangLuong mapRow(ResultSet rs) throws SQLException{
        BangLuong bl=new BangLuong();
        bl.setBangLuongId(rs.getInt("bang_luong_id"));
        bl.setNhanVienId(rs.getInt("nhan_vien_id"));
        bl.setThang(rs.getInt("thang"));
        bl.setNam(rs.getInt("nam"));
        bl.setLuongCoBan(rs.getBigDecimal("luong_co_ban"));
        bl.setTongThuNhap(rs.getBigDecimal("tong_thu_nhap"));
        bl.setTongKhauTru(rs.getBigDecimal("tong_khau_tru"));
        bl.setLuongThucLanh(rs.getBigDecimal("luong_thuc_lanh"));
        bl.setTrangThai(rs.getString("trang_thai"));
        try{bl.setHoTen(rs.getString("ho_ten"));}catch(Exception ignored){}
        try{bl.setMaNhanVien(rs.getString("ma_nhan_vien"));}catch(Exception ignored){}
        return bl;
    }

    private List<BangLuong> mapList(ResultSet rs) throws SQLException{
        List<BangLuong> list=new ArrayList<>();
        while(rs.next()) list.add(mapRow(rs));
        return list;
    }
}