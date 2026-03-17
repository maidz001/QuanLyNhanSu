<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${empty sessionScope.taiKhoanDangDangNhap}">
    <c:redirect url="${pageContext.request.contextPath}/taikhoan?action=login"/>
</c:if>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>Thêm nhân viên</title>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet"/>
<style>
    *,*::before,*::after{box-sizing:border-box;margin:0;padding:0}
    :root{--primary:#1e3a5f;--pl:#2d6a9f;--bg:#f0f2f5;--white:#fff;--border:#dce3ec;--text:#1a1a2e;--muted:#7a8899;--success:#27ae60;--danger:#e74c3c;--info:#2980b9}
    body{font-family:'Inter',sans-serif;background:var(--bg);color:var(--text);min-height:100vh}
    .tb{background:var(--white);border-bottom:1px solid var(--border);padding:0 28px;height:54px;display:flex;align-items:center;justify-content:space-between;position:sticky;top:0;z-index:50}
    .tb-left{display:flex;align-items:center;gap:10px}
    .tb .pt{font-size:.92rem;font-weight:600;color:var(--primary)}
    .bc{font-size:.73rem;color:var(--muted);display:flex;align-items:center;gap:5px}
    .bc a{color:var(--pl);text-decoration:none}.bc a:hover{text-decoration:underline}
    .page{max-width:780px;margin:28px auto;padding:0 20px 60px}
    .alert{padding:10px 14px;border-radius:7px;font-size:.79rem;margin-bottom:14px;display:flex;align-items:center;gap:7px;background:#fdedec;color:var(--danger);border:1px solid #f5b7b1}
    .alert svg{width:13px;height:13px;stroke:currentColor;fill:none;stroke-width:2;flex-shrink:0}
    .box{background:var(--white);border-radius:10px;border:1px solid var(--border);overflow:hidden;margin-bottom:14px}
    .bh{padding:12px 16px;border-bottom:1px solid var(--border);display:flex;align-items:center;gap:8px}
    .bh h3{font-size:.83rem;font-weight:600;color:var(--primary)}
    .bh svg{width:14px;height:14px;stroke:var(--pl);fill:none;stroke-width:2;flex-shrink:0}
    .bb{padding:18px 16px}
    .fg{display:grid;grid-template-columns:1fr 1fr;gap:13px}
    .full{grid-column:1/-1}
    label{display:block;font-size:.7rem;font-weight:500;color:var(--muted);margin-bottom:4px}
    label .req{color:var(--danger)}
    input,select,textarea{width:100%;padding:8px 10px;border:1.5px solid var(--border);border-radius:7px;font-family:'Inter',sans-serif;font-size:.81rem;color:var(--text);background:#f8fafc;outline:none;transition:border-color .18s,background .18s}
    input:focus,select:focus,textarea:focus{border-color:var(--pl);background:#fff}
    textarea{resize:vertical;min-height:68px}
    .hint{font-size:.67rem;color:var(--muted);margin-top:3px}
    .sep{grid-column:1/-1;height:1px;background:var(--border);margin:2px 0}
    .lp{background:var(--primary);border-radius:7px;padding:12px 16px;display:flex;align-items:center;justify-content:space-between;margin-top:4px}
    .lp.hidden{display:none}
    .lp .lp-lbl{font-size:.7rem;color:rgba(255,255,255,.55)}
    .lp .lp-val{font-size:1.1rem;font-weight:700;color:#fff}
    .fa{display:flex;align-items:center;justify-content:space-between;padding:13px 16px;border-top:1px solid var(--border);background:#fafbfc}
    .fa-r{display:flex;gap:8px}
    .btn{padding:7px 16px;border-radius:7px;font-family:'Inter',sans-serif;font-size:.78rem;font-weight:500;cursor:pointer;border:none;transition:all .2s;display:inline-flex;align-items:center;gap:5px;text-decoration:none}
    .btn svg{width:12px;height:12px;stroke:currentColor;fill:none;stroke-width:2.2}
    .btn-primary{background:var(--primary);color:#fff}.btn-primary:hover{background:var(--pl)}
    .btn-outline{background:transparent;border:1.5px solid var(--border);color:var(--muted)}.btn-outline:hover{border-color:var(--pl);color:var(--primary)}
</style>
</head>
<body>

<div class="tb">
    <div class="tb-left"><span class="pt">Thêm nhân viên mới</span></div>
    <div class="bc"><span>Thêm mới</span></div>
</div>

<div class="page">

    <c:if test="${not empty error}">
        <div class="alert">
            <svg viewBox="0 0 24 24"><circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/></svg>
            ${error}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/nhanvien" method="post" id="mainForm">
    <input type="hidden" name="action" value="them"/>

    <!-- CÁ NHÂN -->
    <div class="box">
        <div class="bh">
            <svg viewBox="0 0 24 24"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
            <h3>Thông tin cá nhân</h3>
        </div>
        <div class="bb">
            <div class="fg">
                <div>
                    <label>Mã nhân viên <span class="req">*</span></label>
                    <input type="text" name="maNhanVien" required value="${soNhanVien}"/>
                </div>
                <div>
                    <label>Họ và tên <span class="req">*</span></label>
                    <input type="text" name="hoTen" placeholder="Nguyễn Văn A" required value="${param.hoTen}"/>
                </div>
                <div>
                    <label>Email</label>
                    <input type="email" name="email" placeholder="email@gmail.com" value="${param.email}"/>
                </div>
                <div>
                    <label>Số điện thoại</label>
                    <input type="text" name="dienThoai" placeholder="0912 345 678" value="${param.dienThoai}"/>
                </div>
                <div>
                    <label>Ngày sinh</label>
                    <input type="date" name="ngaySinh" value="${param.ngaySinh}"/>
                </div>
                <div>
                    <label>Giới tính</label>
                    <select name="gioiTinh">
                        <option value="">-- Chọn --</option>
                        <option value="Nam"  ${param.gioiTinh=='Nam'  ?'selected':''}>Nam</option>
                        <option value="Nu"   ${param.gioiTinh=='Nu'   ?'selected':''}>Nữ</option>
                        <option value="Khac" ${param.gioiTinh=='Khac' ?'selected':''}>Khác</option>
                    </select>
                </div>
                <div>
                    <label>CMND / CCCD</label>
                    <input type="text" name="soCmnd" placeholder="012345678901" value="${param.soCmnd}"/>
                </div>
                <div class="full">
                    <label>Địa chỉ</label>
                    <input type="text" name="diaChi" placeholder="Số nhà, đường, phường/xã, tỉnh/thành" value="${param.diaChi}"/>
                </div>
            </div>
        </div>
    </div>

    <!-- CÔNG VIỆC -->
    <div class="box">
        <div class="bh">
            <svg viewBox="0 0 24 24"><rect x="2" y="7" width="20" height="14" rx="2"/><path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16"/></svg>
            <h3>Thông tin công việc</h3>
        </div>
        <div class="bb">
            <div class="fg">
                <div>
                    <label>Phòng ban <span class="req">*</span></label>
                    <select name="phongBanId" required>
                        <option value="">-- Chọn phòng ban --</option>
                        <c:forEach var="pb" items="${listPhongBan}">
                            <option value="${pb.phongBanId}" ${param.phongBanId==pb.phongBanId?'selected':''}>${pb.tenPhongBan}</option>
                        </c:forEach>
                    </select>
                </div>
                <div>
                    <label>Chức vụ <span class="req">*</span></label>
                    <select name="chucVuId" required>
                        <option value="">-- Chọn chức vụ --</option>
                        <c:forEach var="cv" items="${listChucVu}">
                            <option value="${cv.chucVuId}" ${param.chucVuId==cv.chucVuId?'selected':''}>${cv.tenChucVu}</option>
                        </c:forEach>
                    </select>
                </div>
                <div>
                    <label>Ngày vào làm <span class="req">*</span></label>
                    <input type="date" name="ngayVaoLam" required value="${param.ngayVaoLam}"/>
                </div>
                <div><label>Số tài khoản NH</label><input type="text" name="soTaiKhoan" placeholder="VD: 1234567890"/></div>
                <div><label>Ngân hàng</label><input type="text" name="nganHang" placeholder="VD: Vietcombank"/></div>
                <div>
                    <label>Trạng thái</label>
                    <select name="trangThai">
                        <option value="Dang lam viec">Đang làm việc</option>
                    </select>
                </div>
            </div>
        </div>
    </div>

    <!-- HỢP ĐỒNG -->
    <div class="box">
        <div class="bh">
            <svg viewBox="0 0 24 24"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><path d="M14 2v6h6"/><line x1="16" y1="13" x2="8" y2="13"/><line x1="16" y1="17" x2="8" y2="17"/></svg>
            <h3>Hợp đồng lao động</h3>
        </div>
        <div class="bb">
            <div class="fg">
                <div>
                    <label>Loại hợp đồng <span class="req">*</span></label>
                    <select name="loaiHopDong" id="loaiHopDong" required onchange="toggleThoiHan(this.value)">
                        <option value="">-- Chọn loại --</option>
                        <option value="Thu viec"                 ${param.loaiHopDong=='Thu viec'                 ?'selected':''}>Thử việc</option>
                        <option value="Co dinh thoi han"         ${param.loaiHopDong=='Co dinh thoi han'         ?'selected':''}>Có thời hạn</option>
                        <option value="Khong xac dinh thoi han"  ${param.loaiHopDong=='Khong xac dinh thoi han'  ?'selected':''}>Không xác định thời hạn</option>
                    </select>
                </div>

                <!-- THỜI HẠN — chỉ hiện khi loại là Thử việc hoặc Có thời hạn -->
                <div id="thoiHanDiv" style="display:none">
                    <label>Thời hạn hợp đồng <span class="req">*</span></label>
                    <select name="thoiHanHD" id="thoiHanHD" onchange="updateNgayKetThuc()">
                        <option value="">-- Chọn thời hạn --</option>
                        <option value="1">1 năm</option>
                        <option value="2">2 năm</option>
                        <option value="3">3 năm</option>
                        <option value="4">4 năm</option>
                        <option value="5">5 năm</option>
                    </select>
                    <div class="hint" id="hdKetThucHint"></div>
                </div>

                <div>
                    <label>Ngày bắt đầu HĐ <span class="req">*</span></label>
                    <input type="date" name="ngayBatDauHD" id="ngayBatDauHD" required
                           value="${param.ngayBatDauHD}" onchange="updateNgayKetThuc()"/>
                </div>

                <!-- Hidden: ngày kết thúc tính tự động -->
                <input type="hidden" name="ngayKetThucHD" id="ngayKetThucHD"/>

                <div class="sep"></div>

                <div>
                    <label>Lương cơ bản (VNĐ) <span class="req">*</span></label>
                    <input type="number" name="luongCoBan" placeholder="10000000" min="0" step="500000" required
                           value="${param.luongCoBan}" oninput="calcLuong()"/>
                </div>
                <div>
                    <label>Phụ cấp (VNĐ)</label>
                    <input type="number" name="phuCap" placeholder="tính theo thời hạn hợp đồng" min="0" step="100000"/>
                </div>

                <div class="full">
                    <label>Ghi chú</label>
                    <textarea name="ghiChuHD" placeholder="Điều khoản, cam kết...">${param.ghiChuHD}</textarea>
                </div>
            </div>
        </div>
    </div>

    <!-- ACTIONS -->
    <div class="fa">
        <a href="${pageContext.request.contextPath}/nhanvien" class="btn btn-outline">
            <svg viewBox="0 0 24 24"><polyline points="15 18 9 12 15 6"/></svg>Hủy
        </a>
        <div class="fa-r">
            <button type="reset" class="btn btn-outline"
                    onclick="setTimeout(()=>{calcLuong();updateNgayKetThuc();toggleThoiHan('');},10)">
                Nhập lại
            </button>
            <button type="submit" class="btn btn-primary">
                <svg viewBox="0 0 24 24"><path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v14a2 2 0 0 1-2 2z"/><polyline points="17 21 17 13 7 13 7 21"/></svg>
                Lưu nhân viên
            </button>
        </div>
    </div>

    </form>
</div>

<script>
// ── ẨN / HIỆN THỜI HẠN HỢP ĐỒNG ──
function toggleThoiHan(loai) {
    const thoiHanDiv    = document.getElementById('thoiHanDiv');
    const thoiHanSelect = document.getElementById('thoiHanHD');
    const hintEl        = document.getElementById('hdKetThucHint');
    const hiddenEl      = document.getElementById('ngayKetThucHD');

    const canHaveTerm = (loai === 'Thu viec' || loai === 'Co dinh thoi han');

    if (canHaveTerm) {
        thoiHanDiv.style.display  = 'block';
        thoiHanSelect.required    = true;
    } else {
        thoiHanDiv.style.display  = 'none';
        thoiHanSelect.required    = false;
        thoiHanSelect.value       = '';
        hintEl.textContent        = '';
        hiddenEl.value            = '';
    }
}

// ── TÍNH NGÀY KẾT THÚC từ ngày bắt đầu + số năm ──
function updateNgayKetThuc() {
    const bdVal    = document.getElementById('ngayBatDauHD').value;
    const namVal   = document.getElementById('thoiHanHD').value;
    const hintEl   = document.getElementById('hdKetThucHint');
    const hiddenEl = document.getElementById('ngayKetThucHD');

    if (!bdVal || !namVal) {
        hiddenEl.value     = '';
        hintEl.textContent = '';
        return;
    }

    const bd = new Date(bdVal);
    bd.setFullYear(bd.getFullYear() + parseInt(namVal));
    bd.setDate(bd.getDate() - 1);

    const yyyy  = bd.getFullYear();
    const mm    = String(bd.getMonth() + 1).padStart(2, '0');
    const dd    = String(bd.getDate()).padStart(2, '0');
    const ktStr = yyyy + '-' + mm + '-' + dd;

    hiddenEl.value     = ktStr;
    hintEl.textContent = 'Kết thúc: ' + dd + '/' + mm + '/' + yyyy;
}

// ── PREVIEW LƯƠNG ──
function calcLuong() {
    const l = parseFloat(document.querySelector('[name="luongCoBan"]').value) || 0;
    const p = parseFloat(document.querySelector('[name="phuCap"]').value)     || 0;
    const t = l + p;
    const el = document.getElementById('lp');
    if (el) {
        if (t > 0) {
            el.classList.remove('hidden');
            document.getElementById('lpVal').textContent = t.toLocaleString('vi-VN') + ' đ';
        } else {
            el.classList.add('hidden');
        }
    }
}

// ── VALIDATE ──
document.getElementById('mainForm').addEventListener('submit', function(e) {
    const ngaySinh   = document.querySelector('[name="ngaySinh"]').value;
    const ngayVaoLam = document.querySelector('[name="ngayVaoLam"]').value;
    const loai       = document.getElementById('loaiHopDong').value;
    const thoiHan    = document.getElementById('thoiHanHD').value;

    if (ngaySinh && ngayVaoLam && ngaySinh >= ngayVaoLam) {
        alert('Ngày sinh phải trước ngày vào làm!');
        e.preventDefault();
        return;
    }

    // Chỉ bắt buộc thời hạn khi loại HĐ là thử việc hoặc có thời hạn
    if ((loai === 'Thu viec' || loai === 'Co dinh thoi han') && !thoiHan) {
        alert('Vui lòng chọn thời hạn hợp đồng!');
        e.preventDefault();
        return;
    }

    // Tính lại ngày kết thúc lần cuối trước khi submit
    updateNgayKetThuc();
});

// ── KHỞI TẠO khi load lại trang (có param cũ) ──
(function init() {
    const loai = document.getElementById('loaiHopDong').value;
    if (loai) toggleThoiHan(loai);
})();
</script>
</body>
</html>
