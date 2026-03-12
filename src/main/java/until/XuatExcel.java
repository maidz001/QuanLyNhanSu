package until;
import model.BangLuong;
import model.NhanVien;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
public class XuatExcel {




    public void xuatDanhSachNhanVien(List<NhanVien> ds, HttpServletResponse response) throws IOException {

        // Tạo workbook vào ByteArrayOutputStream TRƯỚC
        // rồi mới đụng vào response — tránh response bị commit giữa chừng
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Danh sach nhan vien");

            // ── Header style ──
            CellStyle headerStyle = wb.createCellStyle();
            Font font = wb.createFont();
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(font);
            headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // ── Date style ──
            CellStyle dateStyle = wb.createCellStyle();
            CreationHelper ch = wb.getCreationHelper();
            dateStyle.setDataFormat(ch.createDataFormat().getFormat("dd/MM/yyyy"));

            // ── Tiêu đề ──
            String[] tieuDe = {"Ma NV","Ho ten","Email","Dien thoai","Gioi tinh",
                    "Phong ban","Chuc vu","Ngay vao lam","Trang thai"};
            Row header = sheet.createRow(0);
            for (int i = 0; i < tieuDe.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(tieuDe[i]);
                cell.setCellStyle(headerStyle);
            }

            // ── Dữ liệu ──
            int rowNum = 1;
            for (NhanVien nv : ds) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(nv.getMaNhanVien()   != null ? nv.getMaNhanVien()   : "");
                row.createCell(1).setCellValue(nv.getHoTen()        != null ? nv.getHoTen()        : "");
                row.createCell(2).setCellValue(nv.getEmail()        != null ? nv.getEmail()        : "");
                row.createCell(3).setCellValue(nv.getDienThoai()    != null ? nv.getDienThoai()    : "");
                row.createCell(4).setCellValue(nv.getGioiTinh()     != null ? nv.getGioiTinh()     : "");
                row.createCell(5).setCellValue(nv.getTenPhongBan()  != null ? nv.getTenPhongBan()  : "");
                row.createCell(6).setCellValue(nv.getTenChucVu()    != null ? nv.getTenChucVu()    : "");

                // Ngày vào làm — dùng Date cell thay vì toString()
                Cell dateCell = row.createCell(7);
                if (nv.getNgayVaoLam() != null) {
                    dateCell.setCellValue(nv.getNgayVaoLam());
                    dateCell.setCellStyle(dateStyle);
                } else {
                    dateCell.setCellValue("");
                }

                row.createCell(8).setCellValue(nv.getTrangThai() != null ? nv.getTrangThai() : "");
            }

            // ── Column width ──
            int[] colWidths = {15, 25, 30, 15, 10, 20, 20, 15, 18};
            for (int i = 0; i < colWidths.length; i++) {
                sheet.setColumnWidth(i, colWidths[i] * 256);
            }

            // ── Ghi vào buffer ──
            wb.write(baos);

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Loi xuat Excel: " + e.getMessage(), e);
        }

        // ── Chỉ set response SAU KHI đã có đủ dữ liệu ──
        byte[] bytes = baos.toByteArray();
        response.reset();                          // xóa mọi header/output cũ
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"danh_sach_nhan_vien.xlsx\"");
        response.setContentLength(bytes.length);   // báo trước kích thước → trình duyệt không bị cắt
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");

        try (OutputStream out = response.getOutputStream()) {
            out.write(bytes);
            out.flush();
        }
    }

        public  void xuatBangLuong(List<BangLuong> ds, int thang, int nam, HttpServletResponse response) throws IOException {
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Bang luong " + thang + "-" + nam);
            CellStyle headerStyle = wb.createCellStyle();
            Font font = wb.createFont();
            font.setBold(true);
            headerStyle.setFont(font);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Row header = sheet.createRow(0);
            String[] tieuDe = {"Ma NV", "Ho ten", "So ngay LC", "So ngay TT", "Gio OT", "Luong CB", "Phu cap", "Luong OT", "Thuong", "Tong TN", "BHXH", "BHYT", "Tong KT", "Luong TL", "Trang thai"};
            for (int i = 0; i < tieuDe.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(tieuDe[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (BangLuong bl : ds) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(2).setCellValue(bl.getSoNgayLamViec() != null ? bl.getSoNgayLamViec().doubleValue() : 0);
                row.createCell(3).setCellValue(bl.getSoNgayThucTe() != null ? bl.getSoNgayThucTe().doubleValue() : 0);
                row.createCell(4).setCellValue(bl.getGioLamThem() != null ? bl.getGioLamThem().doubleValue() : 0);
                row.createCell(5).setCellValue(bl.getLuongCoBan() != null ? bl.getLuongCoBan().doubleValue() : 0);
                row.createCell(6).setCellValue(bl.getPhuCap() != null ? bl.getPhuCap().doubleValue() : 0);
                row.createCell(7).setCellValue(bl.getLuongLamThem() != null ? bl.getLuongLamThem().doubleValue() : 0);
                row.createCell(8).setCellValue(bl.getThuong() != null ? bl.getThuong().doubleValue() : 0);
                row.createCell(9).setCellValue(bl.getTongThuNhap() != null ? bl.getTongThuNhap().doubleValue() : 0);
                row.createCell(10).setCellValue(bl.getBaoHiemXaHoi() != null ? bl.getBaoHiemXaHoi().doubleValue() : 0);
                row.createCell(11).setCellValue(bl.getBaoHiemYTe() != null ? bl.getBaoHiemYTe().doubleValue() : 0);
                row.createCell(12).setCellValue(bl.getTongKhauTru() != null ? bl.getTongKhauTru().doubleValue() : 0);
                row.createCell(13).setCellValue(bl.getLuongThucLanh() != null ? bl.getLuongThucLanh().doubleValue() : 0);
                row.createCell(14).setCellValue(bl.getTrangThai() != null ? bl.getTrangThai() : "");
            }

            for (int i = 0; i < tieuDe.length; i++) sheet.autoSizeColumn(i);

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=bang_luong_" + thang + "_" + nam + ".xlsx");
            OutputStream out = response.getOutputStream();
            wb.write(out);
            wb.close();
        }
    }

