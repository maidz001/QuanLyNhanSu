package service;

import dao.ChamCongDAO;
import model.ChamCong;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

public class ChamCongService {
    private ChamCongDAO chamCongDAO = new ChamCongDAO();

    public List<ChamCong> layTatCa() { return chamCongDAO.layTatCa(); }
    public List<ChamCong> layTheoNhanVien(int nhanVienId) { return chamCongDAO.layTheoNhanVien(nhanVienId); }
    public List<ChamCong> layTheoThang(int thang, int nam) { return chamCongDAO.layTheoThang(thang, nam); }
    public ChamCong layTheoId(int id) { return chamCongDAO.layTheoId(id); }
    public boolean them(ChamCong cc) { return chamCongDAO.them(cc); }
    public boolean xoa(int id) { return chamCongDAO.xoa(id); }
    public int demNgayDiLam(int nhanVienId, int thang, int nam) { return chamCongDAO.demNgayDiLamTheoThang(nhanVienId, thang, nam); }
    public double tinhGioLamThem(int nhanVienId, int thang, int nam) { return chamCongDAO.tinhTongGioLamThemTheoThang(nhanVienId, thang, nam); }
    public double tinhGioLamChinh(int nhanVienId, int thang, int nam) { return chamCongDAO.tinhTongGioLamChinhTheoThang(nhanVienId, thang, nam); }
    public boolean checkIn(int idNhanVien){
        LocalDate today = LocalDate.now();
        if(chamCongDAO.daChamCong(idNhanVien, today)){
            return false;
        }
        else {
            java.sql.Date date = java.sql.Date.valueOf(today);
            LocalTime now = LocalTime.now();
            List<ChamCong> lCC = chamCongDAO.layTatCa();
            LocalTime gioQuyDinh = LocalTime.of(8, 0);
            String ghiChu;
            if (now.isBefore(gioQuyDinh)) {
                ghiChu = "Đi làm đúng giờ";
            } else {
                ghiChu = "Đi muộn";
            }
            ChamCong cc = new ChamCong(lCC.size(), idNhanVien, date, now.toString(), "00:00:00", BigDecimal.ZERO, BigDecimal.ZERO, "Di lam", ghiChu);
            return chamCongDAO.checkIn(cc);
        }
    }
    public boolean checkOut(int idNhanVien){
        ChamCong chamCong=chamCongDAO.layRaChamCongHomNay(idNhanVien,LocalDate.now());
        if(!chamCong.getGioRa().toLowerCase(Locale.ROOT).equals("00:00:00")){
            return false;
        }
        Duration duration = Duration.between(LocalTime.parse(chamCong.getGioVao()), LocalTime.now());

        long minutes = duration.toMinutes();
        double soGio = minutes / 60.0;
        chamCong.setSoGioLamViec(BigDecimal.valueOf(soGio));
        chamCong.setGioRa(LocalTime.now().toString());
        if(soGio>8)
        chamCong.setGioLamThem(BigDecimal.valueOf(soGio-8));
        else chamCong.setGioLamThem(BigDecimal.ZERO);
        return chamCongDAO.checkOut(chamCong);
    }

    public List<ChamCong> layChamCongNghiKhongPhep(int nhanVienId) {
        return chamCongDAO.layChamCongNghiKhongPhep(nhanVienId);
    }

    public Boolean insertVangCoPhep(int nhanVienId, LocalDate ngay) {
        return chamCongDAO.insertVangCoPhep(nhanVienId,ngay);
    }
}




