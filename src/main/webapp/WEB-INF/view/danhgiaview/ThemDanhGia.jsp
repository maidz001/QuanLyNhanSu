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
    <title>Thêm đánh giá nhân viên</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet"/>
    <style>
        /* ── ROOT VARIABLES (đồng bộ với trang chủ) ── */
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
            --sw:      260px;
        }

        /* ── DARK MODE ── */
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
        .dark body         { background: #0d1117; color: #e6edf3; }
        .dark .page-card   { background: #161b22; border-color: #30363d; }
        .dark .page-header { background: linear-gradient(135deg, #010409 0%, #1a4a7a 100%); }
        .dark .fm input,
        .dark .fm select,
        .dark .fm textarea { background: #0d1117; border-color: #30363d; color: #e6edf3; }
        .dark .fm input:focus,
        .dark .fm select:focus,
        .dark .fm textarea:focus { border-color: #58a6ff; background: #0d1117; }
        .dark .fa          { border-top-color: #30363d; }
        .dark .score-card  { background: #0d1117; border-color: #30363d; }
        .dark .score-card .sc-label { color: #8b949e; }
        .dark .score-card .sc-total { color: #58a6ff; }
        .dark .topbar      { background: #161b22; border-bottom-color: #30363d; }
        .dark .topbar .pt  { color: #58a6ff; }
        .dark .divider     { border-color: #30363d; }
        .dark .rating-badge { background: rgba(88,166,255,.15); color: #58a6ff; }

        /* ── BASE ── */
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }
        body {
            font-family: 'Inter', sans-serif;
            background: var(--bg);
            color: var(--text);
            min-height: 100vh;
        }

        /* ── TOPBAR ── */
        .topbar {
            background: var(--white);
            border-bottom: 1px solid var(--border);
            padding: 0 28px;
            height: 54px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            position: sticky;
            top: 0;
            z-index: 50;
        }
        .topbar .pt  { font-size: .92rem; font-weight: 600; color: var(--primary); display: flex; align-items: center; gap: 8px; }
        .topbar .pt svg { width: 16px; height: 16px; stroke: var(--primary); fill: none; stroke-width: 2; }
        .topbar .tr  { display: flex; align-items: center; gap: 10px; }

        /* ── LAYOUT ── */
        .page-wrap {
            max-width: 820px;
            margin: 32px auto;
            padding: 0 20px 60px;
        }

        /* ── HEADER BANNER ── */
        .page-header {
            background: linear-gradient(135deg, var(--primary) 0%, var(--pl) 100%);
            border-radius: 14px;
            padding: 24px 28px;
            color: #fff;
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 24px;
        }
        .page-header h2  { font-size: 1.1rem; font-weight: 700; margin-bottom: 3px; }
        .page-header p   { font-size: .77rem; color: rgba(255,255,255,.7); }
        .ph-icon {
            width: 52px; height: 52px;
            background: rgba(255,255,255,.15);
            border-radius: 50%;
            display: flex; align-items: center; justify-content: center;
        }
        .ph-icon svg { width: 24px; height: 24px; stroke: #fff; fill: none; stroke-width: 1.6; }

        /* ── MAIN CARD ── */
        .page-card {
            background: var(--white);
            border-radius: 12px;
            border: 1px solid var(--border);
            overflow: hidden;
        }
        .card-body { padding: 28px; }

        /* ── FORM ELEMENTS ── */
        .fm label {
            display: block;
            font-size: .71rem;
            font-weight: 600;
            color: var(--muted);
            margin-bottom: 5px;
            letter-spacing: .04em;
            text-transform: uppercase;
        }
        .fm input,
        .fm select,
        .fm textarea {
            width: 100%;
            padding: 9px 12px;
            border: 1.5px solid var(--border);
            border-radius: 8px;
            font-family: 'Inter', sans-serif;
            font-size: .82rem;
            color: var(--text);
            background: #f8fafc;
            outline: none;
            transition: border-color .2s, background .2s;
        }
        .fm input:focus,
        .fm select:focus,
        .fm textarea:focus {
            border-color: var(--pl);
            background: #fff;
        }
        .fm textarea { resize: vertical; min-height: 88px; }
        .req { color: var(--danger); margin-left: 2px; }

        /* ── GRID ── */
        .fg  { display: grid; grid-template-columns: 1fr 1fr; gap: 18px; }
        .fg3 { display: grid; grid-template-columns: 1fr 1fr 1fr; gap: 18px; }
        .full { grid-column: 1 / -1; }

        /* ── SECTION DIVIDER ── */
        .section-title {
            font-size: .72rem;
            font-weight: 700;
            color: var(--primary);
            letter-spacing: .1em;
            text-transform: uppercase;
            margin: 26px 0 16px;
            padding-bottom: 8px;
            border-bottom: 1px solid var(--border);
            display: flex;
            align-items: center;
            gap: 7px;
        }
        .section-title svg { width: 13px; height: 13px; stroke: var(--pl); fill: none; stroke-width: 2.2; }
        .divider { border: none; border-top: 1px solid var(--border); margin: 22px 0; }

        /* ── SCORE LIVE PREVIEW ── */
        .score-card {
            background: #f8fafc;
            border: 1.5px solid var(--border);
            border-radius: 10px;
            padding: 16px 20px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 6px;
        }
        .score-card .sc-label { font-size: .75rem; color: var(--muted); font-weight: 500; }
        .score-card .sc-total {
            font-size: 2rem;
            font-weight: 700;
            color: var(--primary);
            line-height: 1;
            transition: color .3s;
        }
        .sc-xep-loai {
            display: inline-block;
            padding: 3px 12px;
            border-radius: 20px;
            font-size: .7rem;
            font-weight: 600;
            margin-top: 4px;
            transition: all .3s;
        }
        .xl-xuatSac { background: #eafaf1; color: #27ae60; }
        .xl-gioi    { background: rgba(30,58,95,.08); color: #1e3a5f; }
        .xl-kha     { background: #fef9e7; color: #f39c12; }
        .xl-trungBinh { background: #fdedec; color: #e74c3c; }

        /* ── BUTTONS ── */
        .btn {
            padding: 9px 20px;
            border-radius: 8px;
            font-family: 'Inter', sans-serif;
            font-size: .8rem;
            font-weight: 600;
            cursor: pointer;
            border: none;
            transition: all .2s;
            display: inline-flex;
            align-items: center;
            gap: 6px;
            text-decoration: none;
        }
        .btn svg { width: 14px; height: 14px; stroke: currentColor; fill: none; stroke-width: 2.2; }
        .bp2 { background: var(--primary); color: #fff; }
        .bp2:hover { background: var(--pl); }
        .bo2 { background: transparent; border: 1.5px solid var(--border); color: var(--muted); }
        .bo2:hover { border-color: var(--pl); color: var(--primary); }
        .bs  { background: var(--success); color: #fff; }
        .bs:hover { opacity: .88; }

        /* ── FOOTER ACTIONS ── */
        .fa {
            display: flex;
            gap: 10px;
            justify-content: flex-end;
            margin-top: 22px;
            padding-top: 18px;
            border-top: 1px solid var(--border);
        }

        /* ── THEME TOGGLE ── */
        #theme-btn {
            width: 32px; height: 32px;
            border-radius: 50%;
            background: var(--bg);
            border: 1px solid var(--border);
            display: flex; align-items: center; justify-content: center;
            cursor: pointer;
            font-size: 15px;
            line-height: 1;
        }

        /* ── RATING BADGE (topbar) ── */
        .rating-badge {
            background: rgba(30,58,95,.08);
            color: var(--primary);
            font-size: .7rem;
            font-weight: 600;
            padding: 4px 10px;
            border-radius: 20px;
        }

        /* ── RESPONSIVE ── */
        @media (max-width: 600px) {
            .fg, .fg3 { grid-template-columns: 1fr; }
            .full { grid-column: 1; }
            .page-wrap { padding: 0 12px 40px; margin-top: 18px; }
        }
    </style>
</head>
<body>

    <!-- ── TOPBAR ── -->
    <div class="topbar">
        <span class="pt">
            <svg viewBox="0 0 24 24"><polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/></svg>
            Thêm đánh giá nhân viên
        </span>
        <div class="tr">
            <span class="rating-badge">📋 Form đánh giá</span>
            <button id="theme-btn" onclick="toggleTheme()">🌙</button>
            <a href="${pageContext.request.contextPath}/taikhoan?action=trangchu" class="btn bo2">
                <svg viewBox="0 0 24 24"><path d="M19 12H5M12 5l-7 7 7 7"/></svg>
                Quay về trang chủ
            </a>
        </div>
    </div>

    <!-- ── MAIN CONTENT ── -->
    <div class="page-wrap">

        <!-- Header banner -->
        <div class="page-header">
            <div>
                <h2>Thêm đánh giá mới 🌟</h2>
                <p>Điền đầy đủ thông tin để lưu đánh giá hiệu suất nhân viên.</p>
            </div>
            <div class="ph-icon">
                <svg viewBox="0 0 24 24"><polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/></svg>
            </div>
        </div>

        <!-- Form card -->
        <div class="page-card">
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/danhgia" method="post">
                    <input type="hidden" name="action" value="them"/>

                    <!-- ── SECTION 1: THÔNG TIN ĐÁNH GIÁ ── -->
                    <div class="section-title">
                        <svg viewBox="0 0 24 24"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/></svg>
                        Thông tin chung
                    </div>

                    <div class="fg fm">
                        <!-- Nhân viên được đánh giá -->
                        <div class="full">
                            <label>Nhân viên được đánh giá <span class="req">*</span></label>
                            <select name="nhanVienId" id="sel-nhanvien" required onchange="capNhatNguoiDanhGia()">
                                <option value="">-- Chọn nhân viên --</option>
                                <c:forEach var="nv" items="${listNhanVien}">
                                    <option value="${nv.nhanVienId}">${nv.hoTen}
                                        <c:if test="${not empty nv.tenPhongBan}"> — ${nv.tenPhongBan}</c:if>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- Tháng -->
                        <div>
                            <label>Tháng đánh giá <span class="req">*</span></label>
                            <select name="thang" required>
                                <option value="">-- Chọn tháng --</option>
                                <c:forEach begin="1" end="12" var="m">
                                    <option value="${m}">Tháng ${m}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- Năm -->
                        <div>
                            <label>Năm đánh giá <span class="req">*</span></label>
                            <select name="nam" required>
                                <option value="">-- Chọn năm --</option>
                                <c:forEach begin="2022" end="2030" var="y">
                                    <option value="${y}" ${y == 2025 ? 'selected' : ''}>${y}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <!-- ── SECTION 2: ĐIỂM SỐ & XẾP LOẠI ── -->
                    <div class="section-title" style="margin-top: 28px;">
                        <svg viewBox="0 0 24 24"><path d="M3 3v18h18"/><path d="M18 17V9"/><path d="M13 17V5"/><path d="M8 17v-3"/></svg>
                        Điểm số & Xếp loại
                    </div>

                    <div class="fg fm">
                        <!-- Tổng điểm -->
                        <div>
                            <label>Tổng điểm (0 – 100) <span class="req">*</span></label>
                            <input
                                type="number"
                                name="tongDiem"
                                id="inp-tongdiem"
                                min="0" max="100" step="0.1"
                                placeholder="VD: 85.5"
                                required
                                oninput="capNhatXepLoai()"
                            />
                        </div>

                        <!-- Xếp loại (auto-fill + editable) -->
                        <div>
                            <label>Xếp loại <span class="req">*</span></label>
                            <select name="xepLoai" id="sel-xeploai" required onchange="capNhatBadge()">
                                <option value="">-- Chọn xếp loại --</option>
                                <option value="Xuat sac">Xuất sắc</option>
                                <option value="Gioi">Giỏi</option>
                                <option value="Kha">Khá</option>

                                <option value="Trung binh">Trung bình</option>
                                <option value="Yeu">Yếu</option>
                            </select>
                        </div>
                    </div>

                    <!-- ── SECTION 3: NHẬN XÉT ── -->
                    <div class="section-title" style="margin-top: 28px;">
                        <svg viewBox="0 0 24 24"><path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/></svg>
                        Nhận xét
                    </div>

                    <div class="fm">
                        <label>Nội dung nhận xét</label>
                        <textarea
                            name="nhanXet"
                            placeholder="Nhập nhận xét chi tiết về hiệu suất làm việc, thái độ, kết quả đạt được của nhân viên..."
                        ></textarea>
                    </div>

                    <!-- ── SECTION 4: NGƯỜI ĐÁNH GIÁ ── -->
                    <div class="section-title" style="margin-top: 28px;">
                        <svg viewBox="0 0 24 24"><circle cx="12" cy="8" r="6"/><path d="M15.477 12.89L17 22l-5-3-5 3 1.523-9.11"/></svg>
                        Người đánh giá
                    </div>

                    <div class="fg fm">
                        <div>
                            <label>Người thực hiện đánh giá <span class="req">*</span></label>
                            <select name="nguoiDanhGia" id="sel-nguoidanhgia" required>
                                <option value="${nguoiThucHien.nhanVienId}">${nguoiThucHien.hoTen}</option>



                            </select>
                        </div>
                    </div>

                    <!-- ── FORM ACTIONS ── -->
                    <div class="fa">
                        <a href="${pageContext.request.contextPath}/taikhoan?action=trangchu" class="btn bo2">
                            <svg viewBox="0 0 24 24"><path d="M19 12H5M12 5l-7 7 7 7"/></svg>
                            Hủy & Quay về
                        </a>
                        <button type="reset" class="btn bo2" onclick="resetPreview()">
                            <svg viewBox="0 0 24 24"><polyline points="1 4 1 10 7 10"/><path d="M3.51 15a9 9 0 1 0 .49-3.47"/></svg>
                            Nhập lại
                        </button>
                        <button type="submit" class="btn bp2">
                            <svg viewBox="0 0 24 24"><path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"/><polyline points="17 21 17 13 7 13 7 21"/><polyline points="7 3 7 8 15 8"/></svg>
                            Lưu đánh giá
                        </button>
                    </div>

                </form>
            </div>
        </div>

    </div><!-- /page-wrap -->

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

    // ── AUTO XẾP LOẠI khi nhập điểm ──
    function capNhatXepLoai() {
        const diem = parseFloat(document.getElementById('inp-tongdiem').value);
        const sel  = document.getElementById('sel-xeploai');

        let xl = '', label = 'Chưa nhập', cls = 'xl-gioi';

        if (!isNaN(diem)) {
            if (diem >= 90)      { xl = 'Xuat sac';    label = '⭐ Xuất sắc';   cls = 'xl-xuatSac'; }
            else if (diem >= 70) { xl = 'Gioi';         label = '👍 Giỏi';       cls = 'xl-gioi'; }

            else if (diem >= 50) { xl = 'Kha';          label = '✔ Khá';         cls = 'xl-kha'; }
             else if (diem >=20 ) { xl = 'Trung binh';          label = '✔ Trung bình';         cls = 'xl-kha'; }
            else                 { xl = 'Yeu';   label = '⚠ Yếu';  cls = 'xl-trungBinh'; }

            sel.value = xl;
            document.getElementById('preview-diem').textContent = diem.toFixed(1);
        } else {
            document.getElementById('preview-diem').textContent = '—';
        }

        const badge = document.getElementById('preview-xeploai');
        badge.textContent = label;
        badge.className   = 'sc-xep-loai ' + cls;
    }

    // ── CẬP NHẬT BADGE khi chọn xếp loại thủ công ──
    function capNhatBadge() {
        const val  = document.getElementById('sel-xeploai').value;
        const badge = document.getElementById('preview-xeploai');
        const map  = {
            'Xuat sac':   { label: '⭐ Xuất sắc',  cls: 'xl-xuatSac'   },
            'Gioi':        { label: '👍 Giỏi',       cls: 'xl-gioi'      },
            'Kha':         { label: '✔ Khá',         cls: 'xl-kha'       },
            'Trung binh':  { label: '⚠ Trung bình',  cls: 'xl-trungBinh' }
        };
        const info = map[val] || { label: 'Chưa chọn', cls: 'xl-gioi' };
        badge.textContent = info.label;
        badge.className   = 'sc-xep-loai ' + info.cls;
    }

    // ── RESET preview khi bấm nhập lại ──
    function resetPreview() {
        setTimeout(() => {
            document.getElementById('preview-diem').textContent  = '—';
            const badge = document.getElementById('preview-xeploai');
            badge.textContent = 'Chưa nhập';
            badge.className   = 'sc-xep-loai xl-gioi';
        }, 50);
    }

    // ── (tuỳ chọn) Tự động chọn người đánh giá dựa trên session ──
    function capNhatNguoiDanhGia() {
        // hook có thể mở rộng nếu cần lọc danh sách
    }
</script>
</body>
</html>
