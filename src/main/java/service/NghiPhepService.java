package service;

import dao.NghiPhepDAO;
import dao.ThongBaoDAO;
import model.DanhGia;
import model.NghiPhep;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NghiPhepService {
    private NghiPhepDAO nghiPhepDAO = new NghiPhepDAO();
    private ThongBaoDAO thongBaoDAO = new ThongBaoDAO();

    public List<NghiPhep> layTatCa() { return nghiPhepDAO.layTatCa(); }
    public List<NghiPhep> layTheoNhanVien(int nhanVienId) { return nghiPhepDAO.layTheoNhanVien(nhanVienId); }
    public List<NghiPhep> layChoDuyet() { return nghiPhepDAO.layChoDuyet(); }
    public NghiPhep layTheoId(int id) { return nghiPhepDAO.layTheoId(id); }

    public boolean nopDon(NghiPhep np) {
        int ngayNghiPhep=0;

        long diff = np.getNgayKetThuc().getTime() - np.getNgayBatDau().getTime();

        long soNgay = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        ngayNghiPhep += soNgay + 1;
        np.setSoNgay(BigDecimal.valueOf(ngayNghiPhep));
        return nghiPhepDAO.them(np);
    }

    public boolean duyet(int id, int nguoiDuyet) {
        NghiPhep np = nghiPhepDAO.layTheoId(id);
        boolean kq = nghiPhepDAO.duyet(id, nguoiDuyet, "Da duyet");
        if (kq && np != null) {
            thongBaoDAO.guiThongBaoChoNhanVien(nguoiDuyet, np.getNhanVienId(), "Don nghi phep duoc duyet", "Don nghi phep tu ngay " + np.getNgayBatDau() + " den " + np.getNgayKetThuc() + " da duoc duyet.", "nghi_phep");
        }
        return kq;
    }

    public boolean tuChoi(int id, int nguoiDuyet) {
        NghiPhep np = nghiPhepDAO.layTheoId(id);
        boolean kq = nghiPhepDAO.duyet(id, nguoiDuyet, "Tu choi");
        if (kq && np != null) {
            thongBaoDAO.guiThongBaoChoNhanVien(nguoiDuyet, np.getNhanVienId(), "Don nghi phep bi tu choi", "Don nghi phep tu ngay " + np.getNgayBatDau() + " den " + np.getNgayKetThuc() + " da bi tu choi.", "nghi_phep");
        }
        return kq;
    }
    public int soNgayNghiPhep(List<NghiPhep> listNghiPhep){
        int ngayNghiPhep = 0;
        LocalDate now=LocalDate.now();
        for (NghiPhep np : listNghiPhep) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(np.getNgayBatDau());

            int thang = cal.get(Calendar.MONTH) + 1;
            if(thang==now.getMonthValue()) {

                long diff = np.getNgayKetThuc().getTime() - np.getNgayBatDau().getTime();

                long soNgay = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                ngayNghiPhep += soNgay + 1;
            }
        }
        return ngayNghiPhep;
    }
    public boolean them(NghiPhep np){
        return nghiPhepDAO.them(np);
    }
    public boolean xoa(int id) { return nghiPhepDAO.xoa(id); }
}