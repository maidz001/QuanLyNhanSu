<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${empty sessionScope.taiKhoanDangDangNhap}">
    <c:redirect url="${pageContext.request.contextPath}/taikhoan?action=login"/>
</c:if>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Chi tiết bảng lương</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet"/>
    <style>
        *,*::before,*::after{box-sizing:border-box;margin:0;padding:0}
        :root{--primary:#1e3a5f;--pl:#2d6a9f;--bg:#f0f2f5;--white:#fff;--border:#dce3ec;--text:#1a1a2e;--muted:#7a8899;--success:#27ae60;--warning:#f39c12;--danger:#e74c3c;--info:#2980b9}
        body{font-family:'Inter',sans-serif;background:var(--bg);color:var(--text);min-height:100vh;display:flex;align-items:flex-start;justify-content:center;padding:40px 16px}

        .card{background:var(--white);border-radius:12px;border:1px solid var(--border);width:100%;max-width:780px;overflow:hidden}

        .card-header{padding:20px 28px;background:linear-gradient(135deg,var(--primary) 0%,var(--pl) 100%);display:flex;align-items:center;justify-content:space-between}
        .card-header-left{display:flex;align-items:center;gap:12px}
        .card-header svg{width:20px;height:20px;stroke:#fff;fill:none;stroke-width:1.8}
        .card-header h2{font-size:1rem;font-weight:600;color:#fff}
        .card-header p{font-size:.73rem;color:rgba(255,255,255,.65);margin-top:2px}

        .badge{display:inline-block;padding:4px 12px;border-radius:20px;font-size:.72rem;font-weight:600}
        .bg{background:#eafaf1;color:var(--success)}
        .bb2{background:#ebf4ff;color:var(--info)}
        .bo{background:#fef9e7;color:var(--warning)}

        .card-body{padding:28px}

        /* Info nhân viên */
        .nv-info{background:#f0f4f8;border:1px solid var(--border);border-radius:10px;padding:16px 20px;margin-bottom:24px;display:grid;grid-template-columns:1fr 1fr 1fr;gap:12px}
        .nv-info .field{display:flex;flex-direction:column;gap:2px}
        .nv-info .field label{font-size:.67rem;font-weight:600;color:var(--muted);text-transform:uppercase;letter-spacing:.05em}
        .nv-info .field span{font-size:.85rem;font-weight:500;color:var(--text)}

        /* Section title */
        .sec{font-size:.72rem;font-weight:700;color:var(--primary);text-transform:uppercase;letter-spacing:.08em;margin:20px 0 10px;padding-bottom:6px;border-bottom:2px solid var(--border);display:flex;align-items:center;gap:6px}
        .sec svg{width:13px;height:13px;stroke:var(--pl);fill:none;stroke-width:2.2}

        /* Bảng chi tiết */
        .dt{width:100%;border-collapse:collapse;font-size:.81rem}
        .dt tr{border-bottom:1px solid var(--border)}
        .dt tr:last-child{border-bottom:none}
        .dt td{padding:10px 14px;vertical-align:middle}
        .dt td:first-child{color:var(--muted);font-size:.78rem;width:55%}
        .dt td:last-child{text-align:right;font-weight:500;color:var(--text)}
        .dt tr.highlight td{background:#f8fafc}
        .dt tr.total{background:linear-gradient(135deg,var(--primary),var(--pl))}
        .dt tr.total td{color:#fff;font-weight:700;font-size:.9rem;padding:14px}
        .dt tr.total td:first-child{color:rgba(255,255,255,.85)}

        .plus{color:var(--success)}
        .minus{color:var(--danger)}

        /* Footer */
        .card-footer{padding:18px 28px;border-top:1px solid var(--border);display:flex;gap:10px;justify-content:flex-end;background:#fafbfc}
        .btn{padding:8px 20px;border-radius:8px;font-family:'Inter',sans-serif;font-size:.8rem;font-weight:500;cursor:pointer;border:none;transition:all .2s;display:inline-flex;align-items:center;gap:6px;text-decoration:none}
        .btn svg{width:14px;height:14px;stroke:currentColor;fill:none;stroke-width:2}
        .btn-primary{background:var(--primary);color:#fff}.btn-primary:hover{background:var(--pl)}
        .btn-success{background:var(--success);color:#fff}.btn-success:hover{opacity:.88}
        .btn-outline{background:transparent;border:1.5px solid var(--border);color:var(--muted)}.btn-outline:hover{border-color:var(--pl);color:var(--primary)}
    </style>
</head>
<body>

<div class="card">

    <%-- Header --%>
    <div class="card-header">
        <div class="card-header-left">
            <svg viewBox="0 0 24 24"><path d="M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/></svg>
            <div>
                <h2>Chi tiết bảng lương</h2>
                <p>Tháng ${bangLuong.thang}/${bangLuong.nam}</p>
            </div>
        </div>
        <span class="badge ${bangLuong.trangThai == 'Da thanh toan' ? 'bg' : bangLuong.trangThai == 'Da duyet' ? 'bb2' : 'bo'}">
            ${bangLuong.trangThai}
        </span>
    </div>

    <div class="card-body">

        <%-- Thông tin nhân viên --%>
        <div class="nv-info">
            <div class="field">
                <label>Nhân viên</label>
                <span>${not empty tenNhanVien ? tenNhanVien : 'NV#'.concat(bangLuong.nhanVienId)}</span>
            </div>
            <div class="field">
                <label>Mã nhân viên</label>
                <span>${not empty maNhanVien ? maNhanVien : '--'}</span>
            </div>
            <div class="field">
                <label>Tháng / Năm</label>
                <span>Tháng ${bangLuong.thang} / ${bangLuong.nam}</span>
            </div>
            <div class="field">
                <label>Phòng ban</label>
                <span>${not empty tenPhongBan ? tenPhongBan : '--'}</span>
            </div>
            <div class="field">
                <label>Chức vụ</label>
                <span>${not empty tenChucVu ? tenChucVu : '--'}</span>
            </div>
            <div class="field">
                <label>Ngày thanh toán</label>
                <span>
                    <c:choose>
                        <c:when test="${not empty bangLuong.ngayThanhToan}">
                            <fmt:formatDate value="${bangLuong.ngayThanhToan}" pattern="dd/MM/yyyy"/>
                        </c:when>
                        <c:otherwise>--</c:otherwise>
                    </c:choose>
                </span>
            </div>
        </div>

        <%-- Chấm công --%>
        <div class="sec">
            <svg viewBox="0 0 24 24"><rect x="3" y="4" width="18" height="18" rx="2"/><path d="M16 2v4M8 2v4M3 10h18"/></svg>
            Thông tin chấm công
        </div>
        <table class="dt">
            <tr class="highlight">
                <td>Số ngày làm việc chuẩn</td>
                <td><fmt:formatNumber value="${bangLuong.soNgayLamViec}" pattern="#,##0.##"/> ngày</td>
            </tr>
            <tr>
                <td>Số ngày thực tế</td>
                <td><fmt:formatNumber value="${bangLuong.soNgayThucTe}" pattern="#,##0.##"/> ngày</td>
            </tr>
            <tr class="highlight">
                <td>Giờ làm thêm</td>
                <td><fmt:formatNumber value="${bangLuong.gioLamThem}" pattern="#,##0.##"/> giờ</td>
            </tr>
        </table>

        <%-- Thu nhập --%>
        <div class="sec">
            <svg viewBox="0 0 24 24"><path d="M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/></svg>
            Thu nhập
        </div>
        <table class="dt">
            <tr class="highlight">
                <td>Lương cơ bản</td>
                <td class="plus"><fmt:formatNumber value="${bangLuong.luongCoBan}" pattern="#,###"/> đ</td>
            </tr>
            <tr>
                <td>Phụ cấp</td>
                <td class="plus"><fmt:formatNumber value="${bangLuong.phuCap}" pattern="#,###"/> đ</td>
            </tr>
            <tr class="highlight">
                <td>Lương làm thêm (×1.5)</td>
                <td class="plus"><fmt:formatNumber value="${bangLuong.luongLamThem}" pattern="#,###"/> đ</td>
            </tr>
            <tr>
                <td>Thưởng</td>
                <td class="plus"><fmt:formatNumber value="${bangLuong.thuong}" pattern="#,###"/> đ</td>
            </tr>
            <tr style="border-top:2px solid var(--border)">
                <td><strong>Tổng thu nhập</strong></td>
                <td><strong><fmt:formatNumber value="${bangLuong.tongThuNhap}" pattern="#,###"/> đ</strong></td>
            </tr>
        </table>

        <%-- Khấu trừ --%>
        <div class="sec">
            <svg viewBox="0 0 24 24"><circle cx="12" cy="12" r="10"/><line x1="8" y1="12" x2="16" y2="12"/></svg>
            Khấu trừ
        </div>
        <table class="dt">
            <tr class="highlight">
                <td>Bảo hiểm xã hội (8%)</td>
                <td class="minus">- <fmt:formatNumber value="${bangLuong.baoHiemXaHoi}" pattern="#,###"/> đ</td>
            </tr>
            <tr>
                <td>Bảo hiểm y tế (1.5%)</td>
                <td class="minus">- <fmt:formatNumber value="${bangLuong.baoHiemYTe}" pattern="#,###"/> đ</td>
            </tr>
            <tr class="highlight">
                <td>Tạm ứng</td>
                <td class="minus">- <fmt:formatNumber value="${bangLuong.tamUng}" pattern="#,###"/> đ</td>
            </tr>
            <tr style="border-top:2px solid var(--border)">
                <td><strong>Tổng khấu trừ</strong></td>
                <td><strong class="minus">- <fmt:formatNumber value="${bangLuong.tongKhauTru}" pattern="#,###"/> đ</strong></td>
            </tr>
        </table>

        <%-- Thực lãnh --%>
        <table class="dt" style="margin-top:16px;border-radius:10px;overflow:hidden">
            <tr class="total">
                <td>💰 Lương thực lãnh</td>
                <td><fmt:formatNumber value="${bangLuong.luongThucLanh}" pattern="#,###"/> đ</td>
            </tr>
        </table>

    </div>

    <%-- Footer actions --%>
    <div class="card-footer">
        <a href="${pageContext.request.contextPath}/taikhoan?action=trangchu" class="btn btn-outline">
            <svg viewBox="0 0 24 24"><line x1="19" y1="12" x2="5" y2="12"/><polyline points="12 19 5 12 12 5"/></svg>
            Quay lại
        </a>
        <c:if test="${bangLuong.trangThai == 'Cho duyet'}">
            <a href="${pageContext.request.contextPath}/bangluong?action=duyet&id=${bangLuong.bangLuongId}"
               class="btn btn-primary"
               onclick="return confirm('Xác nhận duyệt bảng lương này?')">
                <svg viewBox="0 0 24 24"><path d="M20 6L9 17l-5-5"/></svg>
                Duyệt
            </a>
        </c:if>
        <c:if test="${bangLuong.trangThai == 'Da duyet'}">
            <a href="${pageContext.request.contextPath}/bangluong?action=thanh-toan&id=${bangLuong.bangLuongId}"
               class="btn btn-success"
               onclick="return confirm('Xác nhận thanh toán lương?')">
                <svg viewBox="0 0 24 24"><path d="M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/></svg>
                Thanh toán
            </a>
        </c:if>
    </div>

</div>

</body>
</html>