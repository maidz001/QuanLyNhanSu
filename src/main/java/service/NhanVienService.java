package service;

import dao.ChucVuDAO;
import dao.NhanVienDAO;
import dao.ThongBaoDAO;
import model.ChucVu;
import model.NhanVien;

import java.util.*;

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



        public Map<String, Object> tinhThongKeGioiTinh() {
            Map<String, Object> result = new HashMap<>();

            // Lấy số liệu từ DAO
            Map<String, Integer> data = nhanVienDAO.getThongKeGioiTinh();

            int nam = data.get("nam");
            int nu = data.get("nu");
            int khac = data.get("khac");
            int total = nam + nu + khac;

            // Đưa vào result
            result.put("nam", nam);
            result.put("nu", nu);
            result.put("khac", khac);
            result.put("tongSo", total);

            // Tính phần trăm
            if (total > 0) {
                result.put("phanTramNam", Math.round((nam * 100.0 / total) * 10) / 10.0);
                result.put("phanTramNu", Math.round((nu * 100.0 / total) * 10) / 10.0);
                result.put("phanTramKhac", Math.round((khac * 100.0 / total) * 10) / 10.0);
            } else {
                result.put("phanTramNam", 0.0);
                result.put("phanTramNu", 0.0);
                result.put("phanTramKhac", 0.0);
            }

            return result;
        }


        // ════════════════════════════════════════════════════════════
        // Tính thống kê độ tuổi theo khoảng
        // ════════════════════════════════════════════════════════════
        public Map<String, Integer> tinhThongKeDoTuoi() {
            // Khởi tạo kết quả với LinkedHashMap để giữ thứ tự
            Map<String, Integer> result = new LinkedHashMap<>();
            result.put("18-25", 0);
            result.put("26-35", 0);
            result.put("36-45", 0);
            result.put("46-55", 0);
            result.put("56+", 0);
            List<Integer> danhSachNamSinh = nhanVienDAO.getAllNamSinh();

            // Năm hiện tại
            int namHienTai = Calendar.getInstance().get(Calendar.YEAR);

            // Tính tuổi và phân loại
            for (Integer namSinh : danhSachNamSinh) {
                if (namSinh != null && namSinh > 0) {
                    int tuoi = namHienTai - namSinh;

                    if (tuoi >= 18 && tuoi <= 25) {
                        result.put("18-25", result.get("18-25") + 1);
                    } else if (tuoi >= 26 && tuoi <= 35) {
                        result.put("26-35", result.get("26-35") + 1);
                    } else if (tuoi >= 36 && tuoi <= 45) {
                        result.put("36-45", result.get("36-45") + 1);
                    } else if (tuoi >= 46 && tuoi <= 55) {
                        result.put("46-55", result.get("46-55") + 1);
                    } else if (tuoi >= 56) {
                        result.put("56+", result.get("56+") + 1);
                    }
                }
            }

            return result;
        }



        public Map<String, Object> layTatCaThongKe() {
            Map<String, Object> result = new HashMap<>();
            result.put("gioiTinh", tinhThongKeGioiTinh());
            result.put("doTuoi", tinhThongKeDoTuoi());
            return result;
        }
}




