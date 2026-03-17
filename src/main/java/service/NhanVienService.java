package service;

import dao.ChucVuDAO;
import dao.NhanVienDAO;
import dao.ThongBaoDAO;
import model.ChucVu;
import model.NhanVien;
import java.util.List;

public class NhanVienService {
    private NhanVienDAO nhanVienDAO = new NhanVienDAO();
    private ThongBaoDAO thongBaoDAO = new ThongBaoDAO();
    private ChucVuDAO chucVuDAO=new ChucVuDAO();

    public List<NhanVien> layTatCa() { return nhanVienDAO.layTatCa(); }
    public NhanVien layTheoId(int id) { return nhanVienDAO.layTheoId(id); }
    public NhanVien layTheoMa(String ma) { return nhanVienDAO.layTheoMa(ma); }
    public List<NhanVien> timKiem(String tuKhoa) { return nhanVienDAO.timKiem(tuKhoa); }
    public int demTong() { return nhanVienDAO.demTongNhanVien(); }

    public boolean them(NhanVien nv, Integer nguoiThucHien) {
        boolean kq = nhanVienDAO.them(nv);
        if (kq) {
            NhanVien nvMoi = timTheoMa(nv.getMaNhanVien());
            if (nvMoi != null) {
                thongBaoDAO.guiThongBaoChoNhanVien(nguoiThucHien, nvMoi.getNhanVienId(), "Chao mung den cong ty", "Tai khoan cua ban da duoc tao. Chao mung " + nv.getHoTen() + " den voi cong ty!", "he_thong");
            }
        }
        return kq;
    }
    public boolean isConLamViec(int nhanVienId) {
        NhanVien nv = nhanVienDAO.layTheoId(nhanVienId);
        return nv != null && "Dang lam viec".equals(nv.getTrangThai());
    }

    public boolean sua(NhanVien nv, Integer nguoiThucHien) {
        boolean kq = nhanVienDAO.sua(nv);
        if (kq) {
            thongBaoDAO.guiThongBaoChoNhanVien(nguoiThucHien, nv.getNhanVienId(), "Cap nhat thong tin", "Thong tin ho so cua ban vua duoc cap nhat ", "he_thong");
        }
        return kq;
    }

    public boolean xoa(int id) { return nhanVienDAO.xoa(id); }

    private NhanVien timTheoMa(String ma) {
        List<NhanVien> ds = nhanVienDAO.timKiem(ma);
        for (NhanVien nv : ds) {
            if (nv.getMaNhanVien().equals(ma)) return nv;
        }
        return null;
    }

    public boolean setTrangThaiNghiViec(int id) {
        return nhanVienDAO.setTrangThaiNghiViec(id);
    }
    public List<NhanVien> getNhanVienKhongPhaiTruongPhong(){
        return nhanVienDAO.getNhanVienKhongPhaiTruongPhong();
    }
    public List<NhanVien> getNhanVienKhongPhaiTruongPhongTrongPB(int phongBanId){
        return nhanVienDAO.getNhanVienKhongPhaiTruongPhongTrongPB(phongBanId);
    }
    public boolean setChucVuTruongPhong(int idNhanVien){
        int idChucVu=0;
        for(ChucVu cv:chucVuDAO.layTatCa()){
            if(cv.getTenChucVu().toLowerCase().equals("truong phong"))
                idChucVu=cv.getChucVuId();
        }
        return nhanVienDAO.setChucVuTruongPhong(idNhanVien,idChucVu);
    }
    public boolean setChucVu(int idNhanVien,String chucVu){

        int idChucVu=0;
        for(ChucVu cv:chucVuDAO.layTatCa()){
            if(cv.getTenChucVu().toLowerCase().equals(chucVu.toLowerCase()))
                idChucVu=cv.getChucVuId();
        }
        return nhanVienDAO.setChucVu(idNhanVien,idChucVu);
    }
}




