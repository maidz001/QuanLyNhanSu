package service;

import dao.BangLuongDAO;
import dao.ChamCongDAO;
import dao.HopDongDAO;
import dao.NhanVienDAO;
import dao.NghiPhepDAO;
import dao.ThongBaoDAO;
import model.BangLuong;
import model.HopDong;
import model.NhanVien;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class BangLuongService {

    private final BangLuongDAO bangLuongDAO = new BangLuongDAO();
    private final ChamCongDAO  chamCongDAO  = new ChamCongDAO();
    private final HopDongDAO   hopDongDAO   = new HopDongDAO();
    private final NhanVienDAO  nhanVienDAO  = new NhanVienDAO();
    private final NghiPhepDAO  nghiPhepDAO  = new NghiPhepDAO();
    private final ThongBaoDAO  thongBaoDAO  = new ThongBaoDAO();

    // ── Hằng số tính lương ──────────────────────────────────
    private static final int    SO_NGAY_CHUAN  = 22;
    private static final int    SO_GIO_CHUAN   = 8;
    private static final double HE_SO_LAM_THEM = 1.5;
    private static final double TY_LE_BHXH     = 0.08;
    private static final double TY_LE_BHYT     = 0.015;

    // ════════════════════════════════════════════════════════
    // TÍNH LƯƠNG
    // ════════════════════════════════════════════════════════

    public boolean tinhLuongTuDong(int nhanVienId, int thang, int nam, Integer nguoiThucHien) {

        // Đã tính rồi thì bỏ qua
        if (bangLuongDAO.kiemTraDaTinhLuong(nhanVienId, thang, nam))
            return false;

        // Kiểm tra hợp đồng và nhân viên
        HopDong hd = hopDongDAO.layTheoNhanVienId(nhanVienId);
        if (hd == null || !"Hieu luc".equals(hd.getTrangThai()))
            return false;

        NhanVien nv = nhanVienDAO.layTheoId(nhanVienId);
        if (nv == null || !"Dang lam viec".equals(nv.getTrangThai()))
            return false;

        // ── Lấy dữ liệu chấm công ───────────────────────────
        int    soNgayDiLam  = chamCongDAO.demNgayDiLamTheoThang(nhanVienId, thang, nam);
        int    soNgayNghiPhep = nghiPhepDAO.demNgayNghiPhepDaDuyetTheoThang(nhanVienId, thang, nam);
        double gioLamThem   = chamCongDAO.tinhTongGioLamThemTheoThang(nhanVienId, thang, nam);

        // Ngày thực tế = ngày đi làm + ngày nghỉ phép có phép
        int soNgayThucTe = soNgayDiLam + soNgayNghiPhep;

        // ── Lương cơ bản và phụ cấp từ hợp đồng ────────────
        BigDecimal luongCoBan = hd.getLuongCoBan() != null ? hd.getLuongCoBan() : BigDecimal.ZERO;
        BigDecimal phuCap     = hd.getPhuCap()     != null ? hd.getPhuCap()     : BigDecimal.ZERO;

        // ── Tính lương ──────────────────────────────────────
        BigDecimal luongMoiNgay = luongCoBan
                .divide(BigDecimal.valueOf(SO_NGAY_CHUAN), 2, RoundingMode.HALF_UP);

        BigDecimal luongTheoNgay = luongMoiNgay
                .multiply(BigDecimal.valueOf(soNgayThucTe));

        BigDecimal luongMoiGio = luongMoiNgay
                .divide(BigDecimal.valueOf(SO_GIO_CHUAN), 2, RoundingMode.HALF_UP);

        BigDecimal luongLamThem = luongMoiGio
                .multiply(BigDecimal.valueOf(HE_SO_LAM_THEM))
                .multiply(BigDecimal.valueOf(gioLamThem))
                .setScale(0, RoundingMode.HALF_UP);

        BigDecimal tongThuNhap = luongTheoNgay
                .add(phuCap)
                .add(luongLamThem)
                .setScale(0, RoundingMode.HALF_UP);

        // ── Bảo hiểm ────────────────────────────────────────
        BigDecimal bhxh = luongCoBan
                .multiply(BigDecimal.valueOf(TY_LE_BHXH))
                .setScale(0, RoundingMode.HALF_UP);

        BigDecimal bhyt = luongCoBan
                .multiply(BigDecimal.valueOf(TY_LE_BHYT))
                .setScale(0, RoundingMode.HALF_UP);

        BigDecimal tongKhauTru = bhxh.add(bhyt);

        BigDecimal luongThucLanh = tongThuNhap
                .subtract(tongKhauTru)
                .setScale(0, RoundingMode.HALF_UP);

        // ── Tạo bảng lương ──────────────────────────────────
        BangLuong bl = new BangLuong();
        bl.setNhanVienId(nhanVienId);
        bl.setThang(thang);
        bl.setNam(nam);
        bl.setSoNgayLamViec(BigDecimal.valueOf(SO_NGAY_CHUAN));
        bl.setSoNgayThucTe(BigDecimal.valueOf(soNgayThucTe));
        bl.setGioLamThem(BigDecimal.valueOf(gioLamThem));
        bl.setLuongCoBan(luongCoBan);
        bl.setPhuCap(phuCap);
        bl.setLuongLamThem(luongLamThem);
        bl.setThuong(BigDecimal.ZERO);
        bl.setTamUng(BigDecimal.ZERO);
        bl.setTongThuNhap(tongThuNhap);
        bl.setBaoHiemXaHoi(bhxh);
        bl.setBaoHiemYTe(bhyt);
        bl.setTongKhauTru(tongKhauTru);
        bl.setLuongThucLanh(luongThucLanh);
        bl.setTrangThai("Cho duyet");

        boolean kq = bangLuongDAO.them(bl);

        // ── Gửi thông báo ────────────────────────────────────
        if (kq) {
            thongBaoDAO.guiThongBaoChoNhanVien(
                    nguoiThucHien,
                    nhanVienId,
                    "Bang luong thang " + thang + "/" + nam,
                    "Luong thuc lanh: " + luongThucLanh + " VND",
                    "bang_luong"
            );
        }

        return kq;
    }

    public boolean tinhLuongToanBoThang(int thang, int nam, Integer nguoiThucHien) {
        List<NhanVien> dsNV = nhanVienDAO.layTatCa();
        for (NhanVien nv : dsNV) {
            if ("Dang lam viec".equals(nv.getTrangThai())) {
                tinhLuongTuDong(nv.getNhanVienId(), thang, nam, nguoiThucHien);
            }
        }
        return true;
    }

    // ════════════════════════════════════════════════════════
    // DUYỆT & THANH TOÁN
    // ════════════════════════════════════════════════════════

    public boolean duyetBangLuong(int id, Integer nguoiThucHien) {
        BangLuong bl = bangLuongDAO.layTheoId(id);
        if (bl == null) return false;

        boolean kq = bangLuongDAO.capNhatTrangThai(id, "Da duyet");

        if (kq) {
            thongBaoDAO.guiThongBaoChoNhanVien(
                    nguoiThucHien,
                    bl.getNhanVienId(),
                    "Bang luong da duyet",
                    "Bang luong thang " + bl.getThang() + "/" + bl.getNam() + " da duoc duyet",
                    "bang_luong"
            );
        }
        return kq;
    }

    public boolean thanhToan(int id, Integer nguoiThucHien) {
        BangLuong bl = bangLuongDAO.layTheoId(id);
        if (bl == null) return false;

        boolean kq = bangLuongDAO.capNhatTrangThai(id, "Da thanh toan");

        if (kq) {
            thongBaoDAO.guiThongBaoChoNhanVien(
                    nguoiThucHien,
                    bl.getNhanVienId(),
                    "Luong da thanh toan",
                    "Luong thang " + bl.getThang() + "/" + bl.getNam()
                            + ": " + bl.getLuongThucLanh() + " VND",
                    "bang_luong"
            );
        }
        return kq;
    }

    // ════════════════════════════════════════════════════════
    // THỐNG KÊ
    // ════════════════════════════════════════════════════════

    public BigDecimal demTongLuongThangCuaCTy() {
        LocalDate now = LocalDate.now();
        List<BangLuong> list = bangLuongDAO.layTheoThangNam(now.getMonthValue(), now.getYear());
        return list.stream()
                .map(BangLuong::getLuongThucLanh)
                .filter(l -> l != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public boolean thanhToanTatCaTienMat(Integer nguoiThucHien) {
        List<BangLuong> list = bangLuongDAO.layTatCa();
        for (BangLuong bl : list) {
            if ("Cho duyet".equals(bl.getTrangThai()) || "Da duyet".equals(bl.getTrangThai())) {
                bangLuongDAO.capNhatTrangThai(bl.getBangLuongId(), "Da thanh toan tien mat");
                thongBaoDAO.guiThongBaoChoNhanVien(
                        nguoiThucHien, bl.getNhanVienId(),
                        "Luong da thanh toan",
                        "Luong thang " + bl.getThang() + "/" + bl.getNam()
                                + ": " + bl.getLuongThucLanh() + " VND (Tien mat)",
                        "bang_luong"
                );
            }
        }
        return true;
    }
    public boolean thanhToanTienMat(int id, Integer nguoiThucHien) {
        BangLuong bl = bangLuongDAO.layTheoId(id);
        if (bl == null) return false;
        boolean kq = bangLuongDAO.capNhatTrangThai(id, "Da thanh toan tien mat");
        if (kq) {
            thongBaoDAO.guiThongBaoChoNhanVien(
                    nguoiThucHien, bl.getNhanVienId(),
                    "Luong da thanh toan",
                    "Luong thang " + bl.getThang() + "/" + bl.getNam()
                            + ": " + bl.getLuongThucLanh() + " VND (Tien mat)",
                    "bang_luong"
            );
        }
        return kq;
    }

    public boolean thanhToanChuyenKhoan(int id, Integer nguoiThucHien) {
        BangLuong bl = bangLuongDAO.layTheoId(id);
        if (bl == null) return false;
        boolean kq = bangLuongDAO.capNhatTrangThai(id, "Da thanh toan chuyen khoan");
        if (kq) {
            thongBaoDAO.guiThongBaoChoNhanVien(
                    nguoiThucHien, bl.getNhanVienId(),
                    "Luong da thanh toan",
                    "Luong thang " + bl.getThang() + "/" + bl.getNam()
                            + ": " + bl.getLuongThucLanh() + " VND (Chuyen khoan)",
                    "bang_luong"
            );
        }
        return kq;
    }

    // ════════════════════════════════════════════════════════
    // CRUD CƠ BẢN
    // ════════════════════════════════════════════════════════

    public List<BangLuong> layTatCa() { return bangLuongDAO.layTatCa(); }

    public List<BangLuong> layTheoThangNam(int thang, int nam) {
        return bangLuongDAO.layTheoThangNam(thang, nam);
    }

    public List<BangLuong> layTheoNhanVien(int nhanVienId) {
        return bangLuongDAO.layTheoNhanVien(nhanVienId);
    }

    public BangLuong layTheoId(int id) { return bangLuongDAO.layTheoId(id); }

    public BangLuong getBangLuongMoiNhatByNhanVien(int id) {
        return bangLuongDAO.getBangLuongMoiNhatByNhanVien(id);
    }

    public boolean them(BangLuong bl) { return bangLuongDAO.them(bl); }

    public boolean sua(BangLuong bl)  { return bangLuongDAO.sua(bl);  }

    public boolean xoa(int id)        { return bangLuongDAO.xoa(id);  }
}