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
    public List<NghiPhep> layTheoNhanVienDaDuyet(int nhanVienId) { return nghiPhepDAO.layTheoNhanVienDaDuyet(nhanVienId); }

    public List<NghiPhep> layTheoNhanVien(int nhanVienId) { return nghiPhepDAO.layTheoNhanVien(nhanVienId); }
    public List<NghiPhep> layChoDuyet() { return nghiPhepDAO.layChoDuyet(); }
    public NghiPhep layTheoId(int id) { return nghiPhepDAO.layTheoId(id); }

    public boolean nopDon(NghiPhep np) {
        int ngayNghiPhep=0;

        long diff = np.getNgayKetThuc().getTime() - np.getNgayBatDau().getTime();

        long soNgay = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        ngayNghiPhep += soNgay + 1;

        np.setSoNgay(BigDecimal.valueOf(ngayNghiPhep));
        if(nghiPhepDAO.demSoNgayNghiTrongThang(np.getNhanVienId()).compareTo(BigDecimal.valueOf(2))>=0){
              return false;
        }
        if(nghiPhepDAO.demSoNgayNghiTrongNam(np.getNhanVienId()).compareTo(BigDecimal.valueOf(12))>=0)
        return false;

        if(ngayNghiPhep>2){
            nghiPhepDAO.them(np);
            nghiPhepDAO.tuChoiTuDong(np.getNghiPhepId());
            thongBaoDAO.guiThongBaoTuDongTuChoiNP(np.getNhanVienId());

        }
        else{
            nghiPhepDAO.them(np);
        }
        return true;
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
    public boolean kiemTraTrungNgay(NghiPhep np){
        return nghiPhepDAO.kiemTraTrungNgay(np);
    }
    public boolean xoa(int id) { return nghiPhepDAO.xoaDonDaLau(id); }
    public boolean xoaTheoID(int id){
        return nghiPhepDAO.xoa(id);
    }
    public List<NghiPhep> layDaDuyet() { return nghiPhepDAO.layDaDuyet(); }
    public List<NghiPhep> layTuChoi()  { return nghiPhepDAO.layTuChoi(); }

    public void capNhat(NghiPhep np) {
        nghiPhepDAO.duyet(np.getNghiPhepId(),np.getNguoiDuyet(),np.getTrangThai());
    }
}