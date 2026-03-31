<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${empty sessionScope.taiKhoanDangDangNhap}">
    <c:redirect url="${pageContext.request.contextPath}/taikhoan?action=login"/>
</c:if>
<c:set var="tk" value="${sessionScope.taiKhoanDangDangNhap}"/>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Sửa đánh giá nhân viên</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet"/>
    <style>
        :root {
            --primary: #1e3a5f;
            --pl:      #2d6a9f;
            --bg:      #f0f2f5;
            --white:   #fff;
            --border:  #dce3ec;
            --text:    #1a1a2e;
            --muted:   #7a8899;
            --success: #27ae60;
            --warning: #f39c12;
            --danger:  #e74c3c;
        }
        .dark {
            --primary: #58a6ff;
            --pl:      #79c0ff;
            --bg:      #0d1117;
            --white:   #161b22;
            --border:  #30363d;
            --text:    #e6edf3;
            --muted:   #8b949e;
            --success: #3fb950;
            --warning: #d29922;
            --danger:  #f85149;
        }
        .dark body                              { background: #0d1117; color: #e6edf3; }
        .dark .page-card                        { background: #161b22; border-color: #30363d; }
        .dark .page-header                      { background: linear-gradient(135deg, #1a3a00 0%, #2d5a1a 100%); }
        .dark .fm input, .dark .fm select,
        .dark .fm textarea                      { background: #0d1117; border-color: #30363d; color: #e6edf3; }
        .dark .fm input:focus,
        .dark .fm select:focus,
        .dark .fm textarea:focus                { border-color: #58a6ff; background: #0d1117; }
        .dark .fm input[readonly]               { background: #1c2128; color: #8b949e; }
        .dark .fa                               { border-top-color: #30363d; }
        .dark .score-card                       { background: #0d1117; border-color: #30363d; }
        .dark .section-title                    { border-bottom-color: #30363d; }
        .dark .info-box                         { background: #0d1117; border-color: #30363d; }
        .dark .info-box .ib-label               { color: #8b949e; }
        .dark .info-box .ib-val                 { color: #e6edf3; }
        .dark .topbar                           { background: #161b22; border-bottom-color: #30363d; }
        .dark .topbar .pt                       { color: #58a6ff; }
        .dark .rating-badge                     { background: rgba(63,185,80,.15); color: #3fb950; }

        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: 'Inter', sans-serif; background: var(--bg); color: var(--text); min-height: 100vh; }

        /* ── TOPBAR ── */
        .topbar {
            background: var(--white);
            border-bottom: 1px solid var(--border);
            padding: 0 28px; height: 54px;
            display: flex; align-items: center; justify-content: space-between;
            position: sticky; top: 0; z-index: 50;
        }
        .topbar .pt { font-size: .92rem; font-weight: 600; color: var(--primary); display: flex; align-items: center; gap: 8px; }
        .topbar .pt svg { width: 16px; height: 16px; stroke: var(--primary); fill: none; stroke-width: 2; }
        .topbar .tr { display: flex; align-items: center; gap: 10px; }

        /* ── LAYOUT ── */
        .page-wrap { max-width: 820px; margin: 32px auto; padding: 0 20px 60px; }

        /* ── HEADER BANNER ── */
        .page-header {
            background: linear-gradient(135deg, #1a5c2a 0%, #2d9f4a 100%);
            border-radius: 14px; padding: 24px 28px; color: #fff;
            display: flex; align-items: center; justify-content: space-between;
            margin-bottom: 24px;
        }
        .page-header h2 { font-size: 1.1rem; font-weight: 700; margin-bottom: 3px; }
        .page-header p  { font-size: .77rem; color: rgba(255,255,255,.7); }
        .ph-icon {
            width: 52px; height: 52px; background: rgba(255,255,255,.15);
            border-radius: 50%; display: flex; align-items: center; justify-content: center;
        }
        .ph-icon svg { width: 24px; height: 24px; stroke: #fff; fill: none; stroke-width: 1.6; }

        /* ── CARD ── */
        .page-card { background: var(--white); border-radius: 12px; border: 1px solid var(--border); overflow: hidden; }
        .card-body { padding: 28px; }

        /* ── SECTION TITLE ── */
        .section-title {
            font-size: .72rem; font-weight: 700; color: var(--primary);
            letter-spacing: .1em; text-transform: uppercase;
            margin: 26px 0 16px; padding-bottom: 8px;
            border-bottom: 1px solid var(--border);
            display: flex; align-items: center; gap: 7px;
        }
        .section-title svg { width: 13px; height: 13px; stroke: var(--pl); fill: none; stroke-width: 2.2; }
        .section-title:first-child { margin-top: 0; }

        /* ── FORM ── */
        .fm label {
            display: block; font-size: .71rem; font-weight: 600;
            color: var(--muted); margin-bottom: 5px;
            letter-spacing: .04em; text-transform: uppercase;
        }
        .fm input, .fm select, .fm textarea {
            width: 100%; padding: 9px 12px;
            border: 1.5px solid var(--border); border-radius: 8px;
            font-family: 'Inter', sans-serif; font-size: .82rem;
            color: var(--text); background: #f8fafc;
            outline: none; transition: border-color .2s, background .2s;
        }
        .fm input:focus, .fm select:focus, .fm textarea:focus { border-color: var(--pl); background: #fff; }
        .fm input[readonly] { background: #f0f2f5; cursor: not-allowed; color: var(--muted); }
        .fm textarea { resize: vertical; min-height: 88px; }
        .req { color: var(--danger); margin-left: 2px; }

        /* ── GRID ── */
        .fg   { display: grid; grid-template-columns: 1fr 1fr; gap: 18px; }
        .full { grid-column: 1 / -1; }

        /* ── INFO BOX (chỉ đọc) ── */
        .info-box {
            background: #f8fafc; border: 1.5px solid var(--border);
            border-radius: 8px; padding: 10px 14px;
            display: flex; flex-direction: column; gap: 2px;
        }
        .info-box .ib-label { font-size: .67rem; font-weight: 600; color: var(--muted); text-transform: uppercase; letter-spacing: .05em; }
        .info-box .ib-val   { font-size: .85rem; font-weight: 600; color: var(--text); }

        /* ── SCORE PREVIEW ── */
        .score-card {
            background: #f8fafc; border: 1.5px solid var(--border);
            border-radius: 10px; padding: 16px 20px;
            display: flex; align-items: center; justify-content: space-between;
            margin-top: 6px;
        }
        .score-card .sc-label { font-size: .75rem; color: var(--muted); font-weight: 500; }
        .score-card .sc-total { font-size: 2rem; font-weight: 700; color: var(--primary); line-height: 1; transition: color .3s; }
        .sc-xep-loai {
            display: inline-block; padding: 3px 12px; border-radius: 20px;
            font-size: .7rem; font-weight: 600; margin-top: 4px; transition: all .3s;
        }
        .xl-xuatSac   { background: #eafaf1; color: #27ae60; }
        .xl-gioi      { background: rgba(30,58,95,.08); color: #1e3a5f; }
        .xl-kha       { background: #fef9e7; color: #f39c12; }
        .xl-trungBinh { background: #fdedec; color: #e74c3c; }
        .xl-yeu       { background: #fdedec; color: #c0392b; }

        /* ── BUTTONS ── */
        .btn {
            padding: 9px 20px; border-radius: 8px;
            font-family: 'Inter', sans-serif; font-size: .8rem; font-weight: 600;
            cursor: pointer; border: none; transition: all .2s;
            display: inline-flex; align-items: center; gap: 6px; text-decoration: none;
        }
        .btn svg { width: 14px; height: 14px; stroke: currentColor; fill: none; stroke-width: 2.2; }
        .bp2 { background: var(--primary); color: #fff; } .bp2:hover { background: var(--pl); }
        .bs  { background: var(--success);  color: #fff; } .bs:hover  { opacity: .88; }
        .bd  { background: var(--danger);   color: #fff; } .bd:hover  { opacity: .88; }
        .bo2 { background: transparent; border: 1.5px solid var(--border); color: var(--muted); }
        .bo2:hover { border-color: var(--pl); color: var(--primary); }

        /* ── FOOTER ACTIONS ── */
        .fa {
            display: flex; gap: 10px; justify-content: flex-end;
            margin-top: 22px; padding-top: 18px; border-top: 1px solid var(--border);
        }

        /* ── THEME BTN ── */
        #theme-btn {
            width: 32px; height: 32px; border-radius: 50%;
            background: var(--bg); border: 1px solid var(--border);
            display: flex; align-items: center; justify-content: center;
            cursor: pointer; font-size: 15px; line-height: 1;
        }

        .rating-badge {
            background: rgba(39,174,96,.12); color: #1a5c2a;
            font-size: .7rem; font-weight: 600; padding: 4px 10px; border-radius: 20px;
        }

        @media (max-width: 600px) {
            .fg { grid-template-columns: 1fr; }
            .full { grid-column: 1; }
            .page-wrap { padding: 0 12px 40px; margin-top: 18px; }
        }
    </style>
</head>
<body>

    <!-- ── TOPBAR ── -->
    <div class="topbar">
        <span class="pt">
            <svg viewBox="0 0 24 24"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/></svg>
            Sửa đánh giá —
            <span style="font-weight:400; margin-left:2px;">${nhanVien.hoTen}</span>
        </span>
        <div class="tr">
            <span class="rating-badge">✏️ Chỉnh sửa</span>
            <button id="theme-btn" onclick="toggleTheme()">🌙</button>
            <a href="${pageContext.request.contextPath}/home?panel=danhgia" class="btn bo2">
                <svg viewBox="0 0 24 24"><path d="M19 12H5M12 5l-7 7 7 7"/></svg>
                Quay về trang chủ
            </a>
        </div>
    </div>

    <div class="page-wrap">

        <!-- Header banner -->
        <div class="page-header">
            <div>
                <h2>Chỉnh sửa đánh giá ✏️</h2>
                <p>Cập nhật thông tin đánh giá — Mã #${danhGia.danhGiaId}</p>
            </div>
            <div class="ph-icon">
                <svg viewBox="0 0 24 24"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/></svg>
            </div>
        </div>

        <div class="page-card">
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/danhgia" method="post">
                    <input type="hidden" name="action"    value="sua"/>
                    <input type="hidden" name="danhGiaId" value="${danhGia.danhGiaId}"/>
                    <input type="hidden" name="nhanVienId"  value="${danhGia.nhanVienId}"/>
                    <input type="hidden" name="thang"      value="${danhGia.thang}"/>
                    <input type="hidden" name="nam"        value="${danhGia.nam}"/>
                    <input type="hidden" name="nguoiDanhGia" value="${nguoiThucHien.nhanVienId}"/>

                    <!-- ── SECTION 1: THÔNG TIN CHUNG (chỉ đọc) ── -->
                    <div class="section-title">
                        <svg viewBox="0 0 24 24"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/></svg>
                        Thông tin chung
                    </div>

                    <div class="fg">
                        <div class="full fm">
                            <label>Nhân viên được đánh giá</label>
                            <input type="text" value="${nhanVien.hoTen}" readonly/>
                        </div>
                        <div class="fm">
                            <label>Tháng đánh giá</label>
                            <input type="text" value="Tháng ${danhGia.thang}" readonly/>
                        </div>
                        <div class="fm">
                            <label>Năm đánh giá</label>
                            <input type="text" value="${danhGia.nam}" readonly/>
                        </div>
                    </div>

                    <!-- ── SECTION 2: ĐIỂM SỐ & XẾP LOẠI (có thể sửa) ── -->
                    <div class="section-title">
                        <svg viewBox="0 0 24 24"><path d="M3 3v18h18"/><path d="M18 17V9"/><path d="M13 17V5"/><path d="M8 17v-3"/></svg>
                        Điểm số & Xếp loại
                    </div>

                    <div class="fg fm">
                        <div>
                            <label>Tổng điểm (0 – 100) <span class="req">*</span></label>
                            <input
                                type="number"
                                name="tongDiem"
                                id="inp-tongdiem"
                                min="0" max="100" step="0.1"
                                value="${danhGia.tongDiem}"
                                required
                                oninput="capNhatXepLoai()"
                            />
                        </div>
                        <div>
                            <label>Xếp loại <span class="req">*</span></label>
                            <select name="xepLoai" id="sel-xeploai" required onchange="capNhatBadge()">
                                <option value="">-- Chọn xếp loại --</option>
                                <option value="Xuat sac"   ${danhGia.xepLoai == 'Xuat sac'   ? 'selected' : ''}>Xuất sắc</option>
                                <option value="Gioi"        ${danhGia.xepLoai == 'Gioi'        ? 'selected' : ''}>Giỏi</option>
                                <option value="Kha"         ${danhGia.xepLoai == 'Kha'         ? 'selected' : ''}>Khá</option>
                                <option value="Trung binh"  ${danhGia.xepLoai == 'Trung binh'  ? 'selected' : ''}>Trung bình</option>
                                <option value="Yeu"         ${danhGia.xepLoai == 'Yeu'         ? 'selected' : ''}>Yếu</option>
                            </select>
                        </div>
                    </div>

                    <!-- Score live preview -->
                    <div class="score-card" style="margin-top:14px;">
                        <div>
                            <div class="sc-label">Tổng điểm hiện tại</div>
                            <div class="sc-total" id="preview-diem">${danhGia.tongDiem}</div>
                        </div>
                        <div style="text-align:right;">
                            <div class="sc-label" style="margin-bottom:4px;">Xếp loại</div>
                            <span class="sc-xep-loai" id="preview-xeploai">—</span>
                        </div>
                    </div>

                    <!-- ── SECTION 3: NHẬN XÉT ── -->
                    <div class="section-title">
                        <svg viewBox="0 0 24 24"><path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/></svg>
                        Nhận xét
                    </div>

                    <div class="fm">
                        <label>Nội dung nhận xét</label>
                        <textarea name="nhanXet" placeholder="Nhập nhận xét chi tiết...">${danhGia.nhanXet}</textarea>
                    </div>

                    <!-- ── SECTION 4: NGƯỜI ĐÁNH GIÁ (chỉ đọc) ── -->
                    <div class="section-title">
                        <svg viewBox="0 0 24 24"><circle cx="12" cy="8" r="6"/><path d="M15.477 12.89L17 22l-5-3-5 3 1.523-9.11"/></svg>
                        Người đánh giá
                    </div>

                    <div class="fg">
                        <div class="fm">
                            <label>Người thực hiện đánh giá</label>
                            <input type="text" value="${nguoiThucHien.hoTen}" readonly/>
                        </div>

                    </div>

                    <!-- ── FORM ACTIONS ── -->
                    <div class="fa">
                        <a href="${pageContext.request.contextPath}/taikhoan?action=trangchu" class="btn bo2">
                            <svg viewBox="0 0 24 24"><path d="M19 12H5M12 5l-7 7 7 7"/></svg>
                            Hủy & Quay về
                        </a>
                        <button type="submit" class="btn bs">
                            <svg viewBox="0 0 24 24"><path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"/><polyline points="17 21 17 13 7 13 7 21"/><polyline points="7 3 7 8 15 8"/></svg>
                            Lưu thay đổi
                        </button>
                    </div>

                </form>
            </div>
        </div>

    </div>

<script>
    // ── DARK MODE ──
    if (localStorage.getItem('theme') === 'dark') {
        document.documentElement.classList.add('dark');
        document.getElementById('theme-btn').textContent = '☀️';
    }
    function toggleTheme() {
        const isDark = document.documentElement.classList.toggle('dark');
        document.getElementById('theme-btn').textContent = isDark ? '☀️' : '🌙';
        localStorage.setItem('theme', isDark ? 'dark' : 'light');
    }

    // ── XẾP LOẠI MAP ──
    const XL_MAP = {
        'Xuat sac':  { label: '⭐ Xuất sắc',  cls: 'xl-xuatSac'   },
        'Gioi':       { label: '👍 Giỏi',       cls: 'xl-gioi'      },
        'Kha':        { label: '✔ Khá',         cls: 'xl-kha'       },
        'Trung binh': { label: '⚠ Trung bình',  cls: 'xl-trungBinh' },
        'Yeu':        { label: '❌ Yếu',         cls: 'xl-yeu'       }
    };

    function setBadge(xl) {
        const badge = document.getElementById('preview-xeploai');
        const info  = XL_MAP[xl] || { label: 'Chưa chọn', cls: 'xl-gioi' };
        badge.textContent = info.label;
        badge.className   = 'sc-xep-loai ' + info.cls;
    }

    function capNhatXepLoai() {
        const diem = parseFloat(document.getElementById('inp-tongdiem').value);
        const sel  = document.getElementById('sel-xeploai');
        let xl = '';
        if (!isNaN(diem)) {
            if      (diem >= 90) xl = 'Xuat sac';
            else if (diem >= 70) xl = 'Gioi';
            else if (diem >= 50) xl = 'Kha';
            else if (diem >= 20) xl = 'Trung binh';
            else                 xl = 'Yeu';
            sel.value = xl;
            document.getElementById('preview-diem').textContent = diem.toFixed(1);
        } else {
            document.getElementById('preview-diem').textContent = '—';
        }
        setBadge(xl);
    }

    function capNhatBadge() {
        setBadge(document.getElementById('sel-xeploai').value);
    }

    // Khởi tạo badge từ giá trị hiện tại khi load
    window.addEventListener('DOMContentLoaded', () => {
        setBadge(document.getElementById('sel-xeploai').value);
    });
</script>
</body>
</html>
