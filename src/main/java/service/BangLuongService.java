package service;

import dao.BangLuongDAO;
import dao.ChamCongDAO;
import dao.HopDongDAO;
import dao.NhanVienDAO;
import dao.ThongBaoDAO;
import model.BangLuong;
import model.HopDong;
import model.NhanVien;

import java.math.BigDecimal;
import java.util.List;

public class BangLuongService {

    private BangLuongDAO bangLuongDAO = new BangLuongDAO();
    private ChamCongDAO chamCongDAO = new ChamCongDAO();
    private HopDongDAO hopDongDAO = new HopDongDAO();
    private NhanVienDAO nhanVienDAO = new NhanVienDAO();
    private ThongBaoDAO thongBaoDAO = new ThongBaoDAO();


    public List<BangLuong> layTatCa() {
        return bangLuongDAO.layTatCa();
    }

    public List<BangLuong> layTheoThangNam(int thang, int nam) {
        return bangLuongDAO.layTheoThangNam(thang, nam);
    }

    public List<BangLuong> layTheoNhanVien(int nhanVienId) {
        return bangLuongDAO.layTheoNhanVien(nhanVienId);
    }

    public BangLuong layTheoId(int id) {
        return bangLuongDAO.layTheoId(id);
    }


    public boolean tinhLuongTuDong(int nhanVienId, int thang, int nam, Integer nguoiThucHien) {

        if (bangLuongDAO.kiemTraDaTinhLuong(nhanVienId, thang, nam))
            return false;

        HopDong hd = hopDongDAO.layHopDongHieuLuc(nhanVienId);
        if (hd == null)
            return false;

        NhanVien nv = nhanVienDAO.layTheoId(nhanVienId);
        if (nv == null)
            return false;


        int soNgayDiLam = chamCongDAO.demNgayDiLamTheoThang(nhanVienId, thang, nam);
        double gioLamThem = chamCongDAO.tinhTongGioLamThemTheoThang(nhanVienId, thang, nam);

        int soNgayChuanThang = 22;


        BigDecimal luongCoBan = hd.getLuongCoBan();

        BigDecimal phuCap;
        if (hd.getPhuCap() != null)
            phuCap = hd.getPhuCap();
        else
            phuCap = BigDecimal.ZERO;


        BigDecimal luongMoiNgay =
                luongCoBan.divide(BigDecimal.valueOf(soNgayChuanThang),2,BigDecimal.ROUND_HALF_UP);

        BigDecimal luongThucTe =
                luongMoiNgay.multiply(BigDecimal.valueOf(soNgayDiLam));


        BigDecimal luongMoiGio =
                luongMoiNgay.divide(BigDecimal.valueOf(8),2,BigDecimal.ROUND_HALF_UP);


        BigDecimal luongLamThem =
                luongMoiGio.multiply(BigDecimal.valueOf(1.5))
                        .multiply(BigDecimal.valueOf(gioLamThem));


        BigDecimal tongThuNhap =
                luongThucTe.add(phuCap).add(luongLamThem);


        BigDecimal bhxh =
                luongCoBan.multiply(BigDecimal.valueOf(0.08));

        BigDecimal bhyt =
                luongCoBan.multiply(BigDecimal.valueOf(0.015));


        BigDecimal tongKhauTru =
                bhxh.add(bhyt);


        BigDecimal luongThucLanh =
                tongThuNhap.subtract(tongKhauTru);



        BangLuong bl = new BangLuong();

        bl.setNhanVienId(nhanVienId);
        bl.setThang(thang);
        bl.setNam(nam);

        bl.setSoNgayLamViec(BigDecimal.valueOf(soNgayChuanThang));
        bl.setSoNgayThucTe(BigDecimal.valueOf(soNgayDiLam));
        bl.setGioLamThem(BigDecimal.valueOf(gioLamThem));

        bl.setLuongCoBan(luongCoBan);
        bl.setPhuCap(phuCap);

        bl.setLuongLamThem(luongLamThem.setScale(2,BigDecimal.ROUND_HALF_UP));

        bl.setThuong(BigDecimal.ZERO);

        bl.setTongThuNhap(tongThuNhap.setScale(2,BigDecimal.ROUND_HALF_UP));

        bl.setBaoHiemXaHoi(bhxh.setScale(2,BigDecimal.ROUND_HALF_UP));

        bl.setBaoHiemYTe(bhyt.setScale(2,BigDecimal.ROUND_HALF_UP));

        bl.setTamUng(BigDecimal.ZERO);

        bl.setTongKhauTru(tongKhauTru.setScale(2,BigDecimal.ROUND_HALF_UP));

        bl.setLuongThucLanh(luongThucLanh.setScale(2,BigDecimal.ROUND_HALF_UP));

        bl.setTrangThai("Cho duyet");


        boolean kq = bangLuongDAO.them(bl);


        if (kq) {
            thongBaoDAO.guiThongBaoChoNhanVien(
                    nguoiThucHien,
                    nhanVienId,
                    "Bang luong thang " + thang + "/" + nam,
                    "Luong thuc lanh: "
                            + luongThucLanh.setScale(0,BigDecimal.ROUND_HALF_UP)
                            + " VND",
                    "bang_luong"
            );
        }

        return kq;
    }



    public boolean tinhLuongToanBoThang(int thang, int nam, Integer nguoiThucHien) {

        List<NhanVien> dsNV = nhanVienDAO.layTatCa();

        for (NhanVien nv : dsNV) {

            if ("Dang lam viec".equals(nv.getTrangThai())) {

                if (!bangLuongDAO.kiemTraDaTinhLuong(nv.getNhanVienId(),thang,nam)) {

                    tinhLuongTuDong(nv.getNhanVienId(),thang,nam,nguoiThucHien);
                }
            }
        }

        return true;
    }



    public boolean duyetBangLuong(int id, Integer nguoiThucHien) {

        BangLuong bl = bangLuongDAO.layTheoId(id);

        boolean kq = bangLuongDAO.capNhatTrangThai(id,"Da duyet");

        if (kq && bl != null) {

            thongBaoDAO.guiThongBaoChoNhanVien(
                    nguoiThucHien,
                    bl.getNhanVienId(),
                    "Bang luong da duyet",
                    "Bang luong thang "
                            + bl.getThang() + "/" + bl.getNam()
                            + " da duoc duyet",
                    "bang_luong"
            );
        }

        return kq;
    }



    public boolean thanhToan(int id, Integer nguoiThucHien) {

        BangLuong bl = bangLuongDAO.layTheoId(id);

        boolean kq = bangLuongDAO.capNhatTrangThai(id,"Da thanh toan");

        if (kq && bl != null) {

            thongBaoDAO.guiThongBaoChoNhanVien(
                    nguoiThucHien,
                    bl.getNhanVienId(),
                    "Luong da thanh toan",
                    "Luong thang "
                            + bl.getThang() + "/" + bl.getNam()
                            + " : "
                            + bl.getLuongThucLanh()
                            + " VND",
                    "bang_luong"
            );
        }

        return kq;
    }


    public boolean xoa(int id) {
        return bangLuongDAO.xoa(id);
    }

}