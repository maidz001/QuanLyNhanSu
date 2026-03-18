<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${empty sessionScope.taiKhoanDangDangNhap}">
    <c:redirect url="${pageContext.request.contextPath}/taikhoan?action=login"/>
</c:if>
<c:set var="tk" value="${sessionScope.taiKhoanDangDangNhap}"/>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Trang Chủ Nhân Viên</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet"/>
    <style>
    .chuong {
        margin: 24px 0;
        padding-bottom: 16px;
        border-bottom: 1px solid var(--border);
    }
    .chuong:last-child {
        border-bottom: none;
    }

    /* Tiêu đề chương */
    .tieu-de-chuong {
        font-size: .88rem;
        font-weight: 700;
        color: var(--primary);
        text-transform: uppercase;
        margin: 0 0 16px 0;
        padding-bottom: 8px;
        border-bottom: 2px solid var(--primary);
        letter-spacing: .02em;
    }

    /* Điều */
    .dieu {
        margin: 16px 0;
    }

    /* Tiêu đề điều */
    .tieu-de-dieu {
        font-size: .82rem;
        font-weight: 600;
        color: var(--pl);
        margin-bottom: 8px;
        padding-left: 8px;
        border-left: 3px solid var(--pl);
    }

    /* Nội dung */
    .noi-dung p {
        margin: 6px 0;
        text-align: justify;
    }

    .noi-dung strong {
        color: var(--primary);
    }

    .noi-dung u {
        text-decoration: none;
        color: var(--pl);
        font-weight: 500;
    }

    /* Highlight box */
    .highlight-box {
        background: #f8fafc;
        padding: 12px;
        border-left: 4px solid var(--primary);
        border-radius: 6px;
        margin: 8px 0;
    }

    /* Scroll smooth */
    html {
        scroll-behavior: smooth;
    }

    /* Link mục lục */
    #chuong1:target,
    #chuong2:target,
    #chuong3:target,
    #chuong4:target,
    #chuong5:target,
    #chuong6:target {
        animation: highlight 1.5s ease;
    }

    @keyframes highlight {
        0% { background: rgba(45, 106, 159, 0.15); }
        100% { background: transparent; }
    }
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }
        :root {
            --primary: #1e3a5f;
            --primary-light: #2d6a9f;
            --bg: #f0f2f5;
            --white: #ffffff;
            --border: #dce3ec;
            --text: #1a1a2e;
            --muted: #7a8899;
            --success: #27ae60;
            --warning: #f39c12;
            --danger: #e74c3c;
            --info: #2980b9;
            --sidebar-w: 250px;
        }
        body { font-family: 'Inter', sans-serif; background: var(--bg); color: var(--text); min-height: 100vh; display: flex; }

        /* SIDEBAR */
        .sidebar { width: var(--sidebar-w); background: var(--primary); min-height: 100vh; display: flex; flex-direction: column; position: fixed; top: 0; left: 0; z-index: 100; overflow-y: auto; }
        .sidebar-header { padding: 24px 20px 16px; border-bottom: 1px solid rgba(255,255,255,0.1); }
        .sidebar-header .logo { font-size: 0.95rem; font-weight: 700; color: #fff; }
        .sidebar-header .logo span { color: #7ab3d9; }
        .sidebar-header .role-badge { margin-top: 4px; font-size: 0.68rem; color: rgba(255,255,255,0.45); letter-spacing: 0.1em; text-transform: uppercase; }
        .sidebar-user { padding: 14px 20px; display: flex; align-items: center; gap: 10px; border-bottom: 1px solid rgba(255,255,255,0.1); }
        .avatar { width: 34px; height: 34px; background: rgba(255,255,255,0.15); border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 0.82rem; font-weight: 600; color: #fff; flex-shrink: 0; }
        .sidebar-user .info .name { font-size: 0.8rem; font-weight: 500; color: #fff; }
        .sidebar-user .info .sub { font-size: 0.68rem; color: rgba(255,255,255,0.48); }
        .sidebar-nav { flex: 1; padding: 8px 0; }
        .nav-section { padding: 10px 20px 3px; font-size: 0.62rem; color: rgba(255,255,255,0.3); letter-spacing: 0.12em; text-transform: uppercase; }
        .nav-item { display: flex; align-items: center; gap: 9px; padding: 9px 20px; color: rgba(255,255,255,0.68); font-size: 0.81rem; cursor: pointer; transition: all 0.18s; border-left: 3px solid transparent; text-decoration: none; }
        .nav-item:hover, .nav-item.active { background: rgba(255,255,255,0.08); color: #fff; border-left-color: #7ab3d9; }
        .nav-item svg { width: 15px; height: 15px; stroke: currentColor; fill: none; stroke-width: 1.8; flex-shrink: 0; }
        .sidebar-footer { padding: 14px 20px; border-top: 1px solid rgba(255,255,255,0.1); }
        .logout-btn { display: flex; align-items: center; gap: 8px; color: rgba(255,255,255,0.55); font-size: 0.8rem; cursor: pointer; text-decoration: none; transition: color 0.2s; }
        .logout-btn:hover { color: #fff; }
        .logout-btn svg { width: 15px; height: 15px; stroke: currentColor; fill: none; stroke-width: 1.8; }

        /* MAIN */
        .main { margin-left: var(--sidebar-w); flex: 1; display: flex; flex-direction: column; min-height: 100vh; }
        .topbar { background: var(--white); border-bottom: 1px solid var(--border); padding: 0 28px; height: 56px; display: flex; align-items: center; justify-content: space-between; position: sticky; top: 0; z-index: 50; }
        .topbar .page-title { font-size: 0.95rem; font-weight: 600; color: var(--primary); }
        .topbar-right { display: flex; align-items: center; gap: 12px; }
        .date-info { font-size: 0.75rem; color: var(--muted); }
        .notif-btn { position: relative; width: 34px; height: 34px; border-radius: 50%; background: var(--bg); border: 1px solid var(--border); display: flex; align-items: center; justify-content: center; cursor: pointer; }
        .notif-btn svg { width: 15px; height: 15px; stroke: var(--muted); fill: none; stroke-width: 1.8; }
        .notif-dot { position: absolute; top: 5px; right: 5px; width: 7px; height: 7px; background: var(--danger); border-radius: 50%; }
        .content { padding: 24px 28px; flex: 1; }

        /* PANELS */
        .panel { display: none; }
        .panel.active { display: block; }

        /* WELCOME */
        .welcome-banner { background: linear-gradient(135deg, var(--primary) 0%, var(--primary-light) 100%); border-radius: 12px; padding: 24px 28px; color: #fff; display: flex; align-items: center; justify-content: space-between; margin-bottom: 20px; }
        .welcome-banner h2 { font-size: 1.2rem; font-weight: 600; margin-bottom: 4px; }
        .welcome-banner p { font-size: 0.8rem; color: rgba(255,255,255,0.72); }
        .banner-icon { width: 56px; height: 56px; background: rgba(255,255,255,0.15); border-radius: 50%; display: flex; align-items: center; justify-content: center; }
        .banner-icon svg { width: 26px; height: 26px; stroke: #fff; fill: none; stroke-width: 1.6; }

        /* STATS */
        .stats-grid { display: grid; grid-template-columns: repeat(4,1fr); gap: 14px; margin-bottom: 20px; }
        .stat-card { background: var(--white); border-radius: 10px; padding: 18px; border: 1px solid var(--border); display: flex; align-items: center; gap: 12px; }
        .stat-icon { width: 42px; height: 42px; border-radius: 9px; display: flex; align-items: center; justify-content: center; flex-shrink: 0; }
        .stat-icon svg { width: 18px; height: 18px; fill: none; stroke-width: 1.8; }
        .stat-icon.blue { background: #ebf4ff; } .stat-icon.blue svg { stroke: var(--info); }
        .stat-icon.green { background: #eafaf1; } .stat-icon.green svg { stroke: var(--success); }
        .stat-icon.orange { background: #fef9e7; } .stat-icon.orange svg { stroke: var(--warning); }
        .stat-icon.red { background: #fdedec; } .stat-icon.red svg { stroke: var(--danger); }
        .stat-info .val { font-size: 1.3rem; font-weight: 700; color: var(--text); line-height: 1; }
        .stat-info .lbl { font-size: 0.7rem; color: var(--muted); margin-top: 3px; }

        /* BOXES */
        .grid-2 { display: grid; grid-template-columns: 1fr 1fr; gap: 18px; margin-bottom: 18px; }
        .grid-3 { display: grid; grid-template-columns: 1fr 1fr 1fr; gap: 18px; margin-bottom: 18px; }
        .box { background: var(--white); border-radius: 10px; border: 1px solid var(--border); overflow: hidden; margin-bottom: 18px; }
        .box:last-child { margin-bottom: 0; }
        .box-header { padding: 14px 18px; border-bottom: 1px solid var(--border); display: flex; align-items: center; justify-content: space-between; }
        .box-header h3 { font-size: 0.85rem; font-weight: 600; color: var(--primary); display: flex; align-items: center; gap: 7px; }
        .box-header h3 svg { width: 14px; height: 14px; stroke: var(--primary-light); fill: none; stroke-width: 2; }
        .box-header .view-all { font-size: 0.72rem; color: var(--primary-light); cursor: pointer; text-decoration: none; }
        .box-body { padding: 18px; }

        /* PROFILE */
        .profile-row { display: flex; align-items: center; padding: 9px 0; border-bottom: 1px solid var(--border); }
        .profile-row:last-child { border-bottom: none; padding-bottom: 0; }
        .profile-row .lbl { font-size: 0.73rem; color: var(--muted); width: 130px; flex-shrink: 0; }
        .profile-row .val { font-size: 0.82rem; color: var(--text); font-weight: 500; }

        /* BADGE */
        .badge { display: inline-block; padding: 2px 9px; border-radius: 20px; font-size: 0.68rem; font-weight: 500; }
        .badge-green { background: #eafaf1; color: var(--success); }
        .badge-orange { background: #fef9e7; color: var(--warning); }
        .badge-red { background: #fdedec; color: var(--danger); }
        .badge-blue { background: #ebf4ff; color: var(--info); }
        .badge-gray { background: #f4f6f8; color: var(--muted); }
        .badge-purple { background: #f3e8ff; color: #7c3aed; }

        /* TABLE */
        .data-table { width: 100%; border-collapse: collapse; font-size: 0.8rem; }
        .data-table th { padding: 9px 13px; text-align: left; font-size: 0.67rem; font-weight: 600; color: var(--muted); letter-spacing: 0.06em; text-transform: uppercase; border-bottom: 1px solid var(--border); background: #f8fafc; }
        .data-table td { padding: 9px 13px; border-bottom: 1px solid var(--border); color: var(--text); }
        .data-table tr:last-child td { border-bottom: none; }
        .data-table tr:hover td { background: #f8fafc; }

        /* FORM */
        .form-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 14px; }
        .form-group { }
        .form-group.full { grid-column: 1 / -1; }
        .form-group label { display: block; font-size: 0.73rem; font-weight: 500; color: var(--muted); margin-bottom: 5px; }
        .form-group input, .form-group select, .form-group textarea {
            width: 100%; padding: 8px 11px; border: 1.5px solid var(--border); border-radius: 7px;
            font-family: 'Inter', sans-serif; font-size: 0.83rem; color: var(--text); background: #f8fafc; outline: none; transition: border-color 0.2s;
        }
        .form-group input:focus, .form-group select:focus, .form-group textarea:focus { border-color: var(--primary-light); background: #fff; }
        .form-group textarea { resize: vertical; min-height: 76px; }
        .btn { padding: 8px 18px; border-radius: 7px; font-family: 'Inter', sans-serif; font-size: 0.8rem; font-weight: 500; cursor: pointer; border: none; transition: all 0.2s; }
        .btn-primary { background: var(--primary); color: #fff; } .btn-primary:hover { background: var(--primary-light); }
        .btn-success { background: var(--success); color: #fff; } .btn-success:hover { opacity: 0.88; }
        .btn-danger { background: var(--danger); color: #fff; } .btn-danger:hover { opacity: 0.88; }
        .btn-outline { background: transparent; border: 1.5px solid var(--border); color: var(--muted); } .btn-outline:hover { border-color: var(--primary-light); color: var(--primary); }
        .form-actions { display: flex; gap: 8px; justify-content: flex-end; margin-top: 18px; padding-top: 14px; border-top: 1px solid var(--border); }

        /* CHECKIN */
        .checkin-box { text-align: center; padding: 32px 20px; }
        .clock-display { font-size: 3rem; font-weight: 700; color: var(--primary); letter-spacing: 0.05em; margin-bottom: 6px; font-variant-numeric: tabular-nums; }
        .date-display { font-size: 0.85rem; color: var(--muted); margin-bottom: 28px; }
        .checkin-btns { display: flex; gap: 14px; justify-content: center; }
        .btn-checkin { padding: 13px 36px; font-size: 0.9rem; font-weight: 600; border-radius: 8px; cursor: pointer; border: none; transition: all 0.2s; }
        .btn-checkin.in { background: var(--success); color: #fff; } .btn-checkin.in:hover { opacity: 0.88; transform: translateY(-1px); }
        .btn-checkin.out { background: var(--danger); color: #fff; } .btn-checkin.out:hover { opacity: 0.88; transform: translateY(-1px); }
        .btn-checkin:disabled { opacity: 0.4; cursor: not-allowed; transform: none; }
        .checkin-status { margin-top: 20px; padding: 12px 20px; border-radius: 8px; font-size: 0.82rem; }

        /* CALENDAR */
        .cc-legend { display: flex; gap: 14px; margin-bottom: 12px; flex-wrap: wrap; }
        .cc-legend-item { display: flex; align-items: center; gap: 5px; font-size: 0.72rem; color: var(--muted); }
        .cc-dot { width: 9px; height: 9px; border-radius: 50%; }
        .cc-grid { display: grid; grid-template-columns: repeat(7,1fr); gap: 3px; }
        .cc-day-header { text-align: center; font-size: 0.62rem; color: var(--muted); font-weight: 600; padding: 3px 0; }
        .cc-day { aspect-ratio: 1; border-radius: 5px; display: flex; align-items: center; justify-content: center; font-size: 0.7rem; font-weight: 500; background: #f8fafc; color: var(--muted); border: 1px solid var(--border); cursor: default; }
        .cc-day.dilam { background: #eafaf1; color: var(--success); border-color: #a9dfbf; }
        .cc-day.nghiphep { background: #fef9e7; color: var(--warning); border-color: #f9e79f; }
        .cc-day.nghikhongphep { background: #fdedec; color: var(--danger); border-color: #f5b7b1; }
        .cc-day.today { border: 2px solid var(--primary); font-weight: 700; }
        .cc-day.empty { background: transparent; border-color: transparent; }
        .cc-day.weekend { opacity: 0.5; }

        /* LUONG */
        .luong-row { display: flex; justify-content: space-between; align-items: center; padding: 8px 0; border-bottom: 1px solid var(--border); font-size: 0.81rem; }
        .luong-row:last-child { border-bottom: none; }
        .luong-row .luong-lbl { color: var(--muted); }
        .luong-row .luong-val { font-weight: 500; }
        .luong-row.deduct .luong-val { color: var(--danger); }
        .luong-row.total { margin-top: 4px; padding-top: 12px; border-top: 2px solid var(--primary); border-bottom: none; }
        .luong-row.total .luong-lbl { font-weight: 700; color: var(--primary); font-size: 0.88rem; }
        .luong-row.total .luong-val { font-weight: 700; color: var(--success); font-size: 1.05rem; }

        /* THONG BAO ITEM */
        .tb-item { display: flex; gap: 12px; padding: 12px 0; border-bottom: 1px solid var(--border); cursor: pointer; }
        .tb-item:last-child { border-bottom: none; }
        .tb-item .tb-icon { width: 36px; height: 36px; border-radius: 50%; background: #ebf4ff; display: flex; align-items: center; justify-content: center; flex-shrink: 0; }
        .tb-item .tb-icon svg { width: 15px; height: 15px; stroke: var(--info); fill: none; stroke-width: 1.8; }
        .tb-item .tb-content .tb-title { font-size: 0.82rem; font-weight: 500; color: var(--text); margin-bottom: 2px; }
        .tb-item .tb-content .tb-sub { font-size: 0.72rem; color: var(--muted); }
        .tb-item.unread .tb-title { color: var(--primary); }
        .tb-item.unread .tb-icon { background: #eafaf1; }
        .tb-item.unread .tb-icon svg { stroke: var(--success); }

        /* HOP DONG CARD */
        .hd-card { background: linear-gradient(135deg, #f8fafc, #fff); border: 1px solid var(--border); border-radius: 10px; padding: 18px 20px; margin-bottom: 12px; }
        .hd-card:last-child { margin-bottom: 0; }
        .hd-card .hd-title { font-size: 0.88rem; font-weight: 600; color: var(--primary); margin-bottom: 10px; display: flex; justify-content: space-between; align-items: center; }
        .hd-info-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 6px 20px; }
        .hd-info-item .hd-lbl { font-size: 0.68rem; color: var(--muted); text-transform: uppercase; letter-spacing: 0.06em; }
        .hd-info-item .hd-val { font-size: 0.82rem; font-weight: 500; color: var(--text); margin-top: 1px; }

        /* EMPTY */
        .empty-state { text-align: center; padding: 28px 20px; color: var(--muted); font-size: 0.8rem; }
        .empty-state svg { width: 36px; height: 36px; stroke: #dce3ec; fill: none; stroke-width: 1.2; margin-bottom: 8px; display: block; margin-left: auto; margin-right: auto; }

        /* ALERT */
        .alert { padding: 9px 14px; border-radius: 7px; font-size: 0.8rem; margin-bottom: 14px; }
        .alert-success { background: #eafaf1; border: 1px solid #a9dfbf; color: var(--success); }
        .alert-error { background: #fdedec; border: 1px solid #f5b7b1; color: var(--danger); }
        .alert-info { background: #ebf4ff; border: 1px solid #aed6f1; color: var(--info); }

        /* PROGRESS */
        .progress-bar { height: 6px; background: var(--border); border-radius: 4px; overflow: hidden; }
        .progress-fill { height: 100%; background: var(--primary-light); border-radius: 4px; transition: width 0.4s; }
        .progress-fill.green { background: var(--success); }
        .progress-fill.orange { background: var(--warning); }
    </style>
</head>
<body>

<!-- SIDEBAR -->
<aside class="sidebar">
    <div class="sidebar-header">
        <div class="logo">Quản Lý <span>Nhân Sự</span></div>
        <div class="role-badge">Nhân viên</div>
    </div>
    <div class="sidebar-user">
        <div class="avatar" style="text-transform:uppercase">${fn:substring(tk.tenDangNhap, 0, 1)}</div>
        <div class="info">
            <div class="name">${tk.tenDangNhap}</div>
            <div class="sub">${tk.vaiTro}</div>
        </div>
    </div>
    <nav class="sidebar-nav">
        <div class="nav-section">Tổng quan</div>
        <a class="nav-item active" onclick="showPanel('dashboard', this)">
            <svg viewBox="0 0 24 24"><rect x="3" y="3" width="7" height="7"/><rect x="14" y="3" width="7" height="7"/><rect x="3" y="14" width="7" height="7"/><rect x="14" y="14" width="7" height="7"/></svg>Dashboard
        </a>
        <div class="nav-section">Cá nhân</div>
        <a class="nav-item" onclick="showPanel('profile', this)">
            <svg viewBox="0 0 24 24"><circle cx="12" cy="8" r="4"/><path d="M4 20c0-4 3.6-7 8-7s8 3 8 7"/></svg>Thông tin cá nhân
        </a>
        <a class="nav-item" onclick="showPanel('doimatkhau', this)">
            <svg viewBox="0 0 24 24"><rect x="3" y="11" width="18" height="11" rx="2"/><path d="M7 11V7a5 5 0 0 1 10 0v4"/></svg>Đổi mật khẩu
        </a>
        <div class="nav-section">Chấm công</div>
        <a class="nav-item" onclick="showPanel('checkin', this)">
            <svg viewBox="0 0 24 24"><circle cx="12" cy="12" r="10"/><polyline points="12 6 12 12 16 14"/></svg>Check-in / Check-out
        </a>
        <a class="nav-item" onclick="showPanel('lichcong', this)">
            <svg viewBox="0 0 24 24"><rect x="3" y="4" width="18" height="18" rx="2"/><path d="M16 2v4M8 2v4M3 10h18"/></svg>Lịch chấm công
        </a>
        <div class="nav-section">Tài chính</div>
        <a class="nav-item" onclick="showPanel('luong', this)">
            <svg viewBox="0 0 24 24"><path d="M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/></svg>Bảng lương
        </a>
        <div class="nav-section">Yêu cầu</div>
        <a class="nav-item" onclick="showPanel('xinnghiphep', this)">
            <svg viewBox="0 0 24 24"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><path d="M14 2v6h6M16 13H8M16 17H8"/></svg>Xin nghỉ phép
        </a>
        <a class="nav-item" onclick="showPanel('danhsachnghiphep', this)">
            <svg viewBox="0 0 24 24"><path d="M8 6h13M8 12h13M8 18h13M3 6h.01M3 12h.01M3 18h.01"/></svg>Danh sách đơn phép
        </a>
        <div class="nav-section">Hồ sơ</div>
        <a class="nav-item" onclick="showPanel('hopdong', this)">
            <svg viewBox="0 0 24 24"><path d="M9 11l3 3L22 4"/><path d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11"/></svg>Hợp đồng
        </a>
        <a class="nav-item" onclick="showPanel('danhgia', this)">
            <svg viewBox="0 0 24 24"><polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/></svg>Đánh giá
        </a>
        <a class="nav-item" onclick="showPanel('hosotonghop', this)">
            <svg viewBox="0 0 24 24"><path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"/><path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"/></svg>Hồ sơ tổng hợp
        </a>
        <div class="nav-section">Thông báo</div>
        <a class="nav-item" onclick="showPanel('thongbao', this)">
            <svg viewBox="0 0 24 24"><path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/><path d="M13.73 21a2 2 0 0 1-3.46 0"/></svg>Thông báo
        </a>
    </nav>
    <div class="sidebar-footer">
        <a href="${pageContext.request.contextPath}/taikhoan?action=logout" class="logout-btn">
            <svg viewBox="0 0 24 24"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/><polyline points="16 17 21 12 16 7"/><line x1="21" y1="12" x2="9" y2="12"/></svg>Đăng xuất
        </a>
    </div>
</aside>

<!-- MAIN -->
<div class="main">
    <div class="topbar">
        <span class="page-title" id="topbarTitle">Dashboard</span>
        <div class="topbar-right">
            <span class="date-info" id="currentDate"></span>
            <div class="notif-btn" onclick="showPanel('thongbao', null)">
                <svg viewBox="0 0 24 24"><path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/><path d="M13.73 21a2 2 0 0 1-3.46 0"/></svg>
                <span class="notif-dot"></span>
            </div>
        </div>
    </div>

    <div class="content">

        <!-- ═══ DASHBOARD ═══ -->
        <div class="panel active" id="panel-dashboard">
            <div class="welcome-banner">
                <div>
                    <h2>Xin chào, ${nhanVien.hoTen}! 👋</h2>
                    <p>Chào mừng bạn quay trở lại hệ thống quản lý nhân sự.</p>
                </div>

                <div style="display:flex;justify-content:flex-end;margin-top:4px;">
                    <a href="${pageContext.request.contextPath}/taikhoan?action=logout"
                       style="display:flex;align-items:center;gap:6px;padding:5px 13px;border-radius:7px;background:#fdedec;border:1px solid #f5b7b1;color:#e74c3c;font-size:0.74rem;font-weight:500;text-decoration:none;transition:all 0.2s;margin-left:auto;"
                       onmouseover="this.style.background='#e74c3c';this.style.color='#fff'"
                       onmouseout="this.style.background='#fdedec';this.style.color='#e74c3c'">
                        <svg viewBox="0 0 24 24" style="width:13px;height:13px;stroke:currentColor;fill:none;stroke-width:1.8;">
                            <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/>
                            <polyline points="16 17 21 12 16 7"/>
                            <line x1="21" y1="12" x2="9" y2="12"/>
                        </svg>
                        Đăng xuất
                    </a>
                </div>
            </div>

            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-icon blue"><svg viewBox="0 0 24 24"><rect x="3" y="4" width="18" height="18" rx="2"/><path d="M16 2v4M8 2v4M3 10h18"/></svg></div>
                    <div class="stat-info"><div class="val">${soNgayCong != "" ? soNgayCong : '0'}</div><div class="lbl">Ngày công tháng này</div></div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon green"><svg viewBox="0 0 24 24"><path d="M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/></svg></div>
                    <div class="stat-info"><div class="val">${luongGanNhat != null ? luongGanNhat : '--'}</div><div class="lbl">Lương tháng gần nhất</div></div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon orange"><svg viewBox="0 0 24 24"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><path d="M14 2v6h6"/></svg></div>
                    <div class="stat-info"><div class="val">${soDonNghiPhep != null ? soDonNghiPhep : '--'}</div><div class="lbl">Số ngày nghỉ phép</div></div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon red"><svg viewBox="0 0 24 24"><polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/></svg></div>
                    <div class="stat-info"><div class="val">${diemDanhGia != null ? diemDanhGia : '--'}</div><div class="lbl">Điểm đánh giá</div></div>
                </div>
            </div>

            <div class="grid-2">
                <!-- Thông tin tài khoản -->
                <div class="box">
                    <div class="box-header">
                        <h3><svg viewBox="0 0 24 24"><circle cx="12" cy="8" r="4"/><path d="M4 20c0-4 3.6-7 8-7s8 3 8 7"/></svg>Thông tin tài khoản</h3>
                        <a class="view-all" onclick="showPanel('profile',null)">Chi tiết</a>
                    </div>
                    <div class="box-body">
                        <div class="profile-row"><span class="lbl">Tên đăng nhập</span><span class="val">${tk.tenDangNhap}</span></div>
                        <div class="profile-row"><span class="lbl">Vai trò</span><span class="val"><span class="badge badge-blue">${tk.vaiTro}</span></span></div>
                        <div class="profile-row"><span class="lbl">Trạng thái</span><span class="val"><span class="badge badge-green">Hoạt động</span></span></div>
                        <div class="profile-row"><span class="lbl">Mã nhân viên</span><span class="val">#${tk.nhanVienId}</span></div>
                        <div class="profile-row"><span class="lbl">Ngày tạo TK</span><span class="val">
                            <c:choose><c:when test="${not empty tk.ngayTao}"><fmt:formatDate value="${tk.ngayTao}" pattern="yyyy-MM-dd"/></c:when><c:otherwise>--</c:otherwise></c:choose>
                        </span></div>
                    </div>
                </div>

                <!-- Thông báo gần đây -->
                <div class="box">
                    <div class="box-header">
                        <h3><svg viewBox="0 0 24 24"><path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/><path d="M13.73 21a2 2 0 0 1-3.46 0"/></svg>Thông báo gần đây</h3>
                        <a class="view-all" onclick="showPanel('thongbao',null)">Tất cả</a>
                    </div>
                    <div class="box-body">
                        <c:choose>
                            <c:when test="${not empty listThongBao}">
                                <c:forEach var="tb" items="${listThongBao}" end="3">
                                    <div class="tb-item ${tb.daDoc == 0 ? 'unread' : ''}">
                                        <div class="tb-icon"><svg viewBox="0 0 24 24"><path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/><path d="M13.73 21a2 2 0 0 1-3.46 0"/></svg></div>
                                        <div class="tb-content">
                                            <div class="tb-title">${tb.tieuDe}</div>
                                            <div class="tb-sub">
                                                <c:if test="${not empty tb.ngayTao}"><fmt:formatDate value="${tb.ngayTao}" pattern="yyyy-MM-dd"/></c:if>
                                                &bull; ${tb.daDoc == 0 ? 'Chưa đọc' : 'Đã đọc'}
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="empty-state">
                                    <svg viewBox="0 0 24 24"><path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/></svg>
                                    <p>Chưa có thông báo mới</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
<!-- QUY ĐỊNH CÔNG TY - ĐẦY ĐỦ TRONG 1 BOX -->
<!-- ═══════════════════════════════════════════════════════ -->
<div class="box" style="margin-top: 18px;">

    <div class="bb" style="max-height:600px;overflow-y:auto;">

        <!-- Header văn bản -->
        <div style="text-align:center;padding:16px 0;border-bottom:2px solid var(--primary);">
            <div style="font-size:.7rem;color:var(--muted);text-transform:uppercase;letter-spacing:.05em;margin-bottom:4px;">
                CÔNG TY TNHH QUẢN LÝ NHÂN SỰ
            </div>
            <h3 style="font-size:1rem;font-weight:700;color:var(--primary);margin:6px 0;">
                QUY CHẾ LÀM VIỆC NỘI BỘ
            </h3>
            <div style="font-size:.68rem;color:var(--muted);font-style:italic;">
                (Ban hành kèm theo Quyết định số 01/QĐ-CT ngày 01/01/2025)
            </div>
        </div>

        <!-- Mục lục -->
        <div style="background:#f8fafc;padding:14px;margin:16px 0;border-left:4px solid var(--primary);border-radius:6px;">
            <div style="font-size:.76rem;font-weight:600;color:var(--primary);margin-bottom:8px;">
                📑 MỤC LỤC
            </div>
            <div style="font-size:.72rem;line-height:1.8;color:var(--text);">
                <a href="#chuong1" style="color:var(--text);text-decoration:none;display:block;">
                    <strong>Chương I:</strong> Quy định chung
                </a>
                <a href="#chuong2" style="color:var(--text);text-decoration:none;display:block;">
                    <strong>Chương II:</strong> Chế độ nghỉ phép
                </a>
                <a href="#chuong3" style="color:var(--text);text-decoration:none;display:block;">
                    <strong>Chương III:</strong> Chế độ lương thưởng
                </a>
                <a href="#chuong4" style="color:var(--text);text-decoration:none;display:block;">
                    <strong>Chương IV:</strong> Kỷ luật lao động
                </a>
                <a href="#chuong5" style="color:var(--text);text-decoration:none;display:block;">
                    <strong>Chương V:</strong> Đánh giá nhân viên
                </a>
                <a href="#chuong6" style="color:var(--text);text-decoration:none;display:block;">
                    <strong>Chương VI:</strong> Điều khoản thi hành
                </a>
            </div>
        </div>

        <!-- Nội dung chính -->
        <div style="font-size:.79rem;line-height:1.8;color:var(--text);">

            <!-- CHƯƠNG I -->
            <div id="chuong1" class="chuong">
                <h4 class="tieu-de-chuong">CHƯƠNG I: QUY ĐỊNH CHUNG</h4>

                <div class="dieu">
                    <div class="tieu-de-dieu">Điều 1: Phạm vi và đối tượng áp dụng</div>
                    <div class="noi-dung">
                        <p><strong>1.</strong> Quy chế này quy định về chế độ làm việc, kỷ luật lao động và các quy định nội bộ tại Công ty.</p>
                        <p><strong>2.</strong> Áp dụng đối với toàn thể cán bộ, nhân viên đang làm việc tại Công ty.</p>
                    </div>
                </div>

                <div class="dieu">
                    <div class="tieu-de-dieu">Điều 2: Giờ làm việc</div>
                    <div class="noi-dung">
                        <div class="highlight-box">
                            <p><strong>1.</strong> Giờ làm việc hành chính: <span class="badge bg">8:00 - 17:00</span> (nghỉ trưa 12:00 - 13:00)</p>
                            <p><strong>2.</strong> Làm việc 5 ngày/tuần (Thứ 2 - Thứ 6), nghỉ Thứ 7, Chủ nhật và các ngày lễ theo quy định của Nhà nước</p>
                            <p><strong>3.</strong> Tổng số giờ làm việc: <strong>40 giờ/tuần</strong></p>
                        </div>
                    </div>
                </div>

                <div class="dieu">
                    <div class="tieu-de-dieu">Điều 3: Chấm công</div>
                    <div class="noi-dung">
                        <p><strong>1.</strong> Nhân viên phải chấm công đúng giờ khi đến và khi về.</p>
                        <p><strong>2.</strong> Chấm công muộn trên <span class="badge bo">15 phút</span> được coi là đi muộn.</p>
                        <p><strong>3.</strong> Đi muộn quá <span class="badge br">3 lần/tháng</span> sẽ bị xử lý kỷ luật.</p>
                    </div>
                </div>
            </div>

            <!-- CHƯƠNG II -->
            <div id="chuong2" class="chuong">
                <h4 class="tieu-de-chuong">CHƯƠNG II: CHẾ ĐỘ NGHỈ PHÉP</h4>

                <div class="dieu">
                    <div class="tieu-de-dieu">Điều 4: Nghỉ phép năm</div>
                    <div class="noi-dung">
                        <div class="highlight-box" style="background:#eafaf1;">
                            <p><strong>1.</strong> Nhân viên có thâm niên dưới 5 năm: <span class="badge bg">12 ngày/năm</span></p>
                            <p><strong>2.</strong> Nhân viên có thâm niên từ 5 năm trở lên: <span class="badge bg">14 ngày/năm</span></p>
                            <p><strong>3.</strong> Phải đăng ký trước ít nhất <strong>3 ngày làm việc</strong></p>
                        </div>
                    </div>
                </div>

                <div class="dieu">
                    <div class="tieu-de-dieu">Điều 5: Nghỉ ốm, nghỉ việc riêng</div>
                    <div class="noi-dung">
                        <p><strong>1.</strong> <u>Nghỉ ốm:</u> Phải có giấy xác nhận của cơ sở y tế. Được hưởng 100% lương nếu có giấy bác sĩ.</p>
                        <p><strong>2.</strong> <u>Nghỉ việc riêng:</u> Tối đa 3 ngày/năm (không hưởng lương). Phải được quản lý trực tiếp phê duyệt.</p>
                        <p><strong>3.</strong> Thông báo cho quản lý trực tiếp trước khi nghỉ.</p>
                    </div>
                </div>

                <div class="dieu">
                    <div class="tieu-de-dieu">Điều 6: Nghỉ lễ, Tết</div>
                    <div class="noi-dung">
                        <p><strong>1.</strong> Được nghỉ đầy đủ theo quy định của Bộ luật Lao động.</p>
                        <p><strong>2.</strong> Làm việc ngày lễ được trả lương <span class="badge bo">gấp 200%</span> + 1 ngày nghỉ bù.</p>
                    </div>
                </div>
            </div>

            <!-- CHƯƠNG III -->
            <div id="chuong3" class="chuong">
                <h4 class="tieu-de-chuong">CHƯƠNG III: CHẾ ĐỘ LƯƠNG THƯỞNG</h4>

                <div class="dieu">
                    <div class="tieu-de-dieu">Điều 7: Lương cơ bản</div>
                    <div class="noi-dung">
                        <div class="highlight-box">
                            <p><strong>1.</strong> Lương được trả vào <span class="badge bg">ngày 10 hàng tháng</span></p>
                            <p><strong>2.</strong> Lương tháng 13 được tính theo số tháng làm việc thực tế trong năm</p>
                            <p><strong>3.</strong> Tăng lương: Xét duyệt hàng năm dựa trên kết quả đánh giá hiệu suất</p>
                        </div>
                    </div>
                </div>

                <div class="dieu">
                    <div class="tieu-de-dieu">Điều 8: Phụ cấp</div>
                    <div class="noi-dung">
                        <table class="dt" style="margin:8px 0;">
                            <thead>
                                <tr>
                                    <th>Loại phụ cấp</th>
                                    <th>Mức</th>
                                    <th>Điều kiện</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td><strong>Ăn trưa</strong></td>
                                    <td><span class="badge bg">30.000đ/ngày</span></td>
                                    <td>Tính theo ngày làm việc thực tế</td>
                                </tr>
                                <tr>
                                    <td><strong>Xăng xe</strong></td>
                                    <td>Theo chức vụ</td>
                                    <td>Từ Phó phòng trở lên</td>
                                </tr>
                                <tr>
                                    <td><strong>Điện thoại</strong></td>
                                    <td>Theo vị trí</td>
                                    <td>Vị trí yêu cầu liên lạc thường xuyên</td>
                                </tr>
                                <tr>
                                    <td><strong>Làm đêm</strong></td>
                                    <td><span class="badge bo">+30% lương giờ</span></td>
                                    <td>22:00 - 6:00 sáng hôm sau</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="dieu">
                    <div class="tieu-de-dieu">Điều 9: Thưởng</div>
                    <div class="noi-dung">
                        <p><strong>1.</strong> <u>Thưởng theo hiệu quả công việc:</u> Đánh giá hàng quý, mức thưởng từ 500.000đ - 5.000.000đ</p>
                        <p><strong>2.</strong> <u>Thưởng các ngày lễ:</u> Tết Nguyên Đán, 30/4, 2/9 (tối thiểu 500.000đ/dịp)</p>
                        <p><strong>3.</strong> <u>Thưởng đột xuất:</u> Theo thành tích xuất sắc, sáng kiến cải tiến</p>
                        <p><strong>4.</strong> <u>Thưởng thâm niên:</u> 5 năm, 10 năm, 15 năm làm việc tại Công ty</p>
                    </div>
                </div>

                <div class="dieu">
                    <div class="tieu-de-dieu">Điều 10: Các khoản khấu trừ</div>
                    <div class="noi-dung">
                        <table class="dt" style="margin:8px 0;">
                            <thead>
                                <tr>
                                    <th>Khoản khấu trừ</th>
                                    <th>Tỷ lệ</th>
                                    <th>Ghi chú</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>Bảo hiểm xã hội</td>
                                    <td><span class="badge bb2">8%</span></td>
                                    <td>Theo lương đóng BHXH</td>
                                </tr>
                                <tr>
                                    <td>Bảo hiểm y tế</td>
                                    <td><span class="badge bb2">1.5%</span></td>
                                    <td>Theo lương đóng BHXH</td>
                                </tr>
                                <tr>
                                    <td>Bảo hiểm thất nghiệp</td>
                                    <td><span class="badge bb2">1%</span></td>
                                    <td>Theo lương đóng BHXH</td>
                                </tr>
                                <tr>
                                    <td>Thuế TNCN</td>
                                    <td>Theo bậc lũy tiến</td>
                                    <td>Từ 5% - 35%</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- CHƯƠNG IV -->
            <div id="chuong4" class="chuong">
                <h4 class="tieu-de-chuong">CHƯƠNG IV: KỶ LUẬT LAO ĐỘNG</h4>

                <div class="dieu">
                    <div class="tieu-de-dieu">Điều 11: Các hành vi bị nghiêm cấm</div>
                    <div class="noi-dung">
                        <div class="highlight-box" style="background:#fdedec;border-left-color:var(--danger);">
                            <p><strong>1.</strong> Đi làm muộn, về sớm không lý do chính đáng</p>
                            <p><strong>2.</strong> Sử dụng tài sản, thiết bị của Công ty cho mục đích cá nhân</p>
                            <p><strong>3.</strong> Tiết lộ thông tin mật, bí mật kinh doanh của Công ty</p>
                            <p><strong>4.</strong> Gây mất đoàn kết nội bộ, xúc phạm đồng nghiệp</p>
                            <p><strong>5.</strong> Làm việc riêng trong giờ làm việc</p>
                            <p><strong>6.</strong> Làm giả hồ sơ, tài liệu, chứng từ</p>
                        </div>
                    </div>
                </div>

                <div class="dieu">
                    <div class="tieu-de-dieu">Điều 12: Hình thức kỷ luật</div>
                    <div class="noi-dung">
                        <table class="dt" style="margin:8px 0;">
                            <thead>
                                <tr>
                                    <th>Hình thức</th>
                                    <th>Mức xử phạt</th>
                                    <th>Áp dụng</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td><strong>Khiển trách</strong></td>
                                    <td>Nhắc nhở bằng văn bản</td>
                                    <td>Vi phạm nhẹ lần đầu</td>
                                </tr>
                                <tr>
                                    <td><strong>Cảnh cáo</strong></td>
                                    <td><span class="badge bo">Trừ 20% lương tháng</span></td>
                                    <td>Vi phạm lặp lại hoặc vi phạm nghiêm trọng</td>
                                </tr>
                                <tr>
                                    <td><strong>Tạm đình công</strong></td>
                                    <td>Ngưng việc không quá 3 tháng</td>
                                    <td>Vi phạm rất nghiêm trọng</td>
                                </tr>
                                <tr>
                                    <td><strong>Sa thải</strong></td>
                                    <td><span class="badge br">Chấm dứt hợp đồng</span></td>
                                    <td>Vi phạm đặc biệt nghiêm trọng</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- CHƯƠNG V -->
            <div id="chuong5" class="chuong">
                <h4 class="tieu-de-chuong">CHƯƠNG V: ĐÁNH GIÁ NHÂN VIÊN</h4>

                <div class="dieu">
                    <div class="tieu-de-dieu">Điều 13: Tiêu chí đánh giá</div>
                    <div class="noi-dung">
                        <div class="highlight-box">
                            <p><strong>1.</strong> Kết quả công việc: <span class="badge bg">40%</span> (KPI đạt được, chất lượng sản phẩm/dịch vụ)</p>
                            <p><strong>2.</strong> Thái độ làm việc: <span class="badge bg">30%</span> (Tinh thần trách nhiệm, sáng tạo, chủ động)</p>
                            <p><strong>3.</strong> Kỷ luật lao động: <span class="badge bg">20%</span> (Chấm công, tuân thủ nội quy)</p>
                            <p><strong>4.</strong> Tinh thần đồng đội: <span class="badge bg">10%</span> (Hỗ trợ đồng nghiệp, hợp tác)</p>
                        </div>
                    </div>
                </div>

                <div class="dieu">
                    <div class="tieu-de-dieu">Điều 14: Chu kỳ đánh giá</div>
                    <div class="noi-dung">
                        <p><strong>1.</strong> <u>Đánh giá hiệu suất:</u> Hàng tháng (đánh giá KPI, năng suất làm việc)</p>
                        <p><strong>2.</strong> <u>Đánh giá năng lực:</u> Hàng quý (đánh giá kỹ năng, năng lực chuyên môn)</p>
                        <p><strong>3.</strong> <u>Đánh giá tổng thể:</u> Cuối năm (xét tăng lương, thăng chức)</p>
                    </div>
                </div>

                <div class="dieu">
                    <div class="tieu-de-dieu">Điều 15: Xếp loại đánh giá</div>
                    <div class="noi-dung">
                        <table class="dt" style="margin:8px 0;">
                            <thead>
                                <tr>
                                    <th>Xếp loại</th>
                                    <th>Điểm</th>
                                    <th>Mô tả</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td><span class="badge bg">Xuất sắc</span></td>
                                    <td>90 - 100</td>
                                    <td>Hoàn thành vượt mức, có sáng kiến xuất sắc</td>
                                </tr>
                                <tr>
                                    <td><span class="badge bb2">Giỏi</span></td>
                                    <td>80 - 89</td>
                                    <td>Hoàn thành tốt nhiệm vụ, vượt chỉ tiêu</td>
                                </tr>
                                <tr>
                                    <td><span class="badge bo">Khá</span></td>
                                    <td>70 - 79</td>
                                    <td>Hoàn thành nhiệm vụ đúng yêu cầu</td>
                                </tr>
                                <tr>
                                    <td><span class="badge bgr">Trung bình</span></td>
                                    <td>50 - 69</td>
                                    <td>Hoàn thành một phần nhiệm vụ</td>
                                </tr>
                                <tr>
                                    <td><span class="badge br">Yếu</span></td>
                                    <td>< 50</td>
                                    <td>Không hoàn thành nhiệm vụ</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- CHƯƠNG VI -->
            <div id="chuong6" class="chuong">
                <h4 class="tieu-de-chuong">CHƯƠNG VI: ĐIỀU KHOẢN THI HÀNH</h4>

                <div class="dieu">
                    <div class="tieu-de-dieu">Điều 16: Hiệu lực</div>
                    <div class="noi-dung">
                        <p><strong>1.</strong> Quy chế này có hiệu lực từ ngày ký và thay thế các quy định trước đây trái với quy chế này.</p>
                        <p><strong>2.</strong> Trong quá trình thực hiện, nếu có vướng mắc, các đơn vị, cá nhân báo cáo Ban Giám đốc để xem xét, giải quyết.</p>
                    </div>
                </div>

                <div class="dieu">
                    <div class="tieu-de-dieu">Điều 17: Trách nhiệm thi hành</div>
                    <div class="noi-dung">
                        <p><strong>1.</strong> Trưởng các phòng ban có trách nhiệm phổ biến, hướng dẫn và kiểm tra việc thực hiện quy chế này.</p>
                        <p><strong>2.</strong> Toàn thể cán bộ, nhân viên có trách nhiệm nghiêm túc thực hiện quy chế này.</p>
                        <p><strong>3.</strong> Mọi sửa đổi, bổ sung quy chế phải được Ban Giám đốc phê duyệt bằng văn bản.</p>
                    </div>
                </div>
            </div>

        </div>

        <!-- Chữ ký -->
        <div style="margin-top:32px;padding-top:20px;border-top:3px double var(--primary);text-align:right;">
            <div style="font-size:.72rem;color:var(--muted);margin-bottom:6px;">
                Ngày 01 tháng 01 năm 2025
            </div>
            <div style="font-size:.82rem;font-weight:700;color:var(--primary);text-transform:uppercase;">
                GIÁM ĐỐC CÔNG TY
            </div>
            <div style="font-size:.68rem;color:var(--muted);font-style:italic;margin-top:4px;">
                (Ký, ghi rõ họ tên, đóng dấu)
            </div>
            <div style="height:60px;"></div>
            <div style="font-size:.78rem;font-weight:600;color:var(--text);">
                [Nguyễn Hồng Mai]
            </div>
        </div>

    </div>
</div>
        </div>

        <!-- ═══ THÔNG TIN CÁ NHÂN ═══ -->

        <div class="panel" id="panel-profile">


            <div class="box" style="max-width:320px;margin-bottom:18px;">
                            <div class="box-header">
                                <h3><svg viewBox="0 0 24 24"><circle cx="12" cy="8" r="4"/><path d="M4 20c0-4 3.6-7 8-7s8 3 8 7"/></svg>Ảnh đại diện</h3>
                            </div>
                            <div class="box-body" style="display:flex;flex-direction:column;align-items:center;gap:16px;padding:24px;">

                                <c:choose>
                                    <c:when test="${not empty nhanVien.anhDaiDien}">
                                        <img src="${pageContext.request.contextPath}/${nhanVien.anhDaiDien}"
                                             alt="Ảnh đại diện"
                                             style="width:110px;height:110px;border-radius:50%;object-fit:cover;border:3px solid var(--border);box-shadow:0 2px 12px rgba(30,58,95,0.1);"/>
                                    </c:when>
                                    <c:otherwise>
                                        <div style="width:110px;height:110px;border-radius:50%;background:#ebf4ff;border:3px solid var(--border);display:flex;align-items:center;justify-content:center;">
                                            <svg viewBox="0 0 24 24" style="width:48px;height:48px;stroke:#2d6a9f;fill:none;stroke-width:1.4;">
                                                <circle cx="12" cy="8" r="4"/><path d="M4 20c0-4 3.6-7 8-7s8 3 8 7"/>
                                            </svg>
                                        </div>
                                    </c:otherwise>
                                </c:choose>

                                <div style="text-align:center;">
                                    <div style="font-size:0.9rem;font-weight:600;color:var(--text);">${nhanVien.hoTen}</div>
                                    <div style="font-size:0.75rem;color:var(--muted);margin-top:3px;">${nhanVien.tenChucVu} • ${nhanVien.tenPhongBan}</div>
                                </div>

                                <form action="${pageContext.request.contextPath}/nhanvien" method="post"
                                      enctype="multipart/form-data"
                                      style="width:100%;display:flex;flex-direction:column;gap:8px;">
                                    <input type="hidden" name="action" value="capnhatanh"/>
                                    <input type="hidden" name="nhanVienId" value="${nhanVien.nhanVienId}"/>
                                    <input type="file" name="anhDaiDien" accept="image/*"
                                           style="padding:7px 10px;border:1.5px solid var(--border);border-radius:7px;font-size:0.78rem;background:#f8fafc;width:100%;"/>
                                    <button type="submit"
                                            style="padding:7px 14px;background:var(--primary);color:#fff;border:none;border-radius:7px;font-size:0.78rem;font-weight:500;cursor:pointer;">
                                        Cập nhật ảnh
                                    </button>
                                </form>

                            </div>
            </div>
            <div class="box">
                            <div class="box-header"><h3><svg viewBox="0 0 24 24"><circle cx="12" cy="8" r="4"/><path d="M4 20c0-4 3.6-7 8-7s8 3 8 7"/></svg>Thông tin tài khoản</h3></div>
                            <div class="box-body">
                                <div class="grid-2" style="margin-bottom:0">
                                    <div>
                                        <div class="profile-row"><span class="lbl">Mã nhân viên</span><span class="val">#${tk.nhanVienId}</span></div>
                                        <div class="profile-row"><span class="lbl">Tên đăng nhập</span><span class="val">${tk.tenDangNhap}</span></div>
                                        <div class="profile-row"><span class="lbl">Vai trò</span><span class="val"><span class="badge badge-blue">${tk.vaiTro}</span></span></div>
                                    </div>
                                    <div>
                                        <div class="profile-row">
                                            <span class="lbl">Trạng thái TK</span>
                                            <span class="val">
                                                <span class="badge ${tk.trangThai == 1 ? 'badge-green' : 'badge-red'}">${tk.trangThai == 1 ? 'Hoạt động' : 'Vô hiệu'}</span>
                                            </span>
                                        </div>
                                        <div class="profile-row"><span class="lbl">Ngày tạo</span><span class="val">
                                            <c:choose><c:when test="${not empty tk.ngayTao}"><fmt:formatDate value="${tk.ngayTao}" pattern="yyyy-MM-dd"/></c:when><c:otherwise>--</c:otherwise></c:choose>
                                        </span></div>
                                    </div>
                                </div>
                            </div>
                        </div>
            <c:if test="${not empty nhanVien}">
            <div class="box">
                <div class="box-header"><h3><svg viewBox="0 0 24 24"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>Thông tin nhân viên</h3></div>
                <div class="box-body">
                    <div class="grid-2" style="margin-bottom:0">
                        <div>
                            <div class="profile-row"><span class="lbl">Mã NV</span><span class="val">${nhanVien.maNhanVien}</span></div>
                            <div class="profile-row"><span class="lbl">Họ tên</span><span class="val">${nhanVien.hoTen}</span></div>
                            <div class="profile-row"><span class="lbl">Email</span><span class="val">${not empty nhanVien.email ? nhanVien.email : '--'}</span></div>
                            <div class="profile-row"><span class="lbl">Điện thoại</span><span class="val">${not empty nhanVien.dienThoai ? nhanVien.dienThoai : '--'}</span></div>
                            <div class="profile-row"><span class="lbl">Ngày sinh</span><span class="val">${not empty nhanVien.ngaySinh ? nhanVien.ngaySinh : '--'}</span></div>
                            <div class="profile-row"><span class="lbl">Địa chỉ</span><span class="val">${not empty nhanVien.diaChi ? nhanVien.diaChi : '--'}</span></div>
                        <div class="profile-row"><span class="lbl">Giới tính</span><span class="val">${nhanVien.gioiTinh}</span></div>
                                                    <div class="profile-row"><span class="lbl">CMND/CCCD</span><span class="val">${not empty nhanVien.soCmnd ? nhanVien.soCmnd : '--'}</span></div>
                        </div>
                        <div>

                            <div class="profile-row"><span class="lbl">Giới tính</span><span class="val">${nhanVien.gioiTinh}</span></div>
                            <div class="profile-row"><span class="lbl">CMND/CCCD</span><span class="val">${not empty nhanVien.soCmnd ? nhanVien.soCmnd : '--'}</span></div>
                            <div class="profile-row"><span class="lbl">Phòng ban</span><span class="val">${not empty nhanVien.tenPhongBan ? nhanVien.tenPhongBan : '--'}</span></div>
                            <div class="profile-row"><span class="lbl">Chức vụ</span><span class="val">${not empty nhanVien.tenChucVu ? nhanVien.tenChucVu : '--'}</span></div>
                            <div class="profile-row"><span class="lbl">Ngày vào làm</span><span class="val">${not empty nhanVien.ngayVaoLam ? nhanVien.ngayVaoLam : '--'}</span></div>
                            <div class="profile-row"><span class="lbl">Trạng thái</span><span class="val">${not empty nhanVien.trangThai ? nhanVien.trangThai : '--'}</span></div>
<div class="profile-row"><span class="lbl">Số tài khoản NH</span><span class="val">${not empty nhanVien.soTaiKhoan ? nhanVien.soTaiKhoan : '--'}</span></div>
<div class="profile-row"><span class="lbl">Ngân hàng</span><span class="val">${not empty nhanVien.nganHang ? nhanVien.nganHang : '--'}</span></div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- ═══ CẬP NHẬT THÔNG TIN CÁ NHÂN ═══ -->
            <div class="box" style="max-width:560px;">
                <div class="box-header">
                    <h3>
                        <svg viewBox="0 0 24 24"><path d="M12 20h9"/><path d="M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z"/></svg>
                        Cập nhật thông tin
                    </h3>
                </div>
                <div class="box-body">
                    <c:if test="${not empty messageCapNhat}">
                        <div class="alert alert-success">✓ ${messageCapNhat}</div>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/nhanvien" method="post">
                        <input type="hidden" name="action" value="capnhatthongtincanhan"/>
                        <input type="hidden" name="nhanVienId" value="${nhanVien.nhanVienId}"/>
                        <div class="form-grid">
                            <div class="form-group">
                                <label>Số điện thoại</label>
                                <input type="text" name="dienThoai"
                                       value="${not empty nhanVien.dienThoai ? nhanVien.dienThoai : ''}"
                                       placeholder="Nhập số điện thoại..."/>
                            </div>
                            <div class="form-group">
                                                            <label>Họ tên</label>
                                                            <input type="text" name="hoTen"
                                                                   value="${not empty nhanVien.hoTen ? nhanVien.hoTen : ''}"
                                                                   placeholder="Nhập số họ tên..."/>
                                                        </div>
                            <div class="form-group">
                                <label>Email</label>
                                <label>Email</label>
                                <input type="email" name="email"
                                       value="${not empty nhanVien.email ? nhanVien.email : ''}"
                                       placeholder="Nhập email..."/>
                            </div>
                            <div class="form-group full">
                                <label>Địa chỉ</label>
                                <input type="text" name="diaChi"
                                       value="${not empty nhanVien.diaChi ? nhanVien.diaChi : ''}"
                                       placeholder="Nhập địa chỉ..."/>
                            </div>
                            <%-- Thêm vào form-grid sau ô địa chỉ --%>
                            <div class="form-group">
                                <label>Số tài khoản NH</label>
                                <input type="text" name="soTaiKhoan"
                                       value="${not empty nhanVien.soTaiKhoan ? nhanVien.soTaiKhoan : ''}"
                                       placeholder="Nhập số tài khoản..."/>
                            </div>
                            <div class="form-group">
                                <label>Ngân hàng</label>
                                <input type="text" name="nganHang"
                                       value="${not empty nhanVien.nganHang ? nhanVien.nganHang : ''}"
                                       placeholder="VD: Vietcombank..."/>
                            </div>
                        </div>
                        <div class="form-actions">
                            <button type="reset" class="btn btn-outline">Hủy thay đổi</button>
                            <button type="submit" class="btn btn-primary">Lưu thông tin</button>
                        </div>
                    </form>
                </div>
            </div>
            </c:if>
        </div>

        <!-- ═══ ĐỔI MẬT KHẨU ═══ -->
        <div class="panel" id="panel-doimatkhau">
            <div class="box" style="max-width:480px">
                <div class="box-header"><h3><svg viewBox="0 0 24 24"><rect x="3" y="11" width="18" height="11" rx="2"/><path d="M7 11V7a5 5 0 0 1 10 0v4"/></svg>Đổi mật khẩu</h3></div>
                <div class="box-body">
                    <c:if test="${not empty messageDMK}">
                        <div class="alert alert-success">✓ ${messageDMK}</div>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/taikhoan" method="post">
                        <input type="hidden" name="action" value="doiMatKhau"/>
                        <input type="hidden" name="idTaiKhoan" value="${tk.taiKhoanId}"/>
                        <div class="form-group" style="margin-bottom:14px"><label>Mật khẩu hiện tại</label><input type="password" name="matKhauCu" placeholder="••••••••" required/></div>
                        <div class="form-group" style="margin-bottom:14px"><label>Mật khẩu mới</label><input type="password" name="matKhauMoi" placeholder="••••••••" required/></div>
                        <div class="form-group" style="margin-bottom:14px"><label>Xác nhận mật khẩu mới</label><input type="password" name="xacNhanMatKhau" placeholder="••••••••" required/></div>
                        <div class="form-actions"><button type="submit" class="btn btn-primary">Cập nhật mật khẩu</button></div>
                    </form>
                </div>
            </div>
        </div>

        <!-- ═══ CHECK-IN / CHECK-OUT ═══ -->
        <div class="panel" id="panel-checkin">
            <div class="grid-2">
                <div class="box">
                    <div class="box-header"><h3><svg viewBox="0 0 24 24"><circle cx="12" cy="12" r="10"/><polyline points="12 6 12 12 16 14"/></svg>Chấm công hôm nay</h3></div>
                    <div class="box-body">
                        <div class="checkin-box">
                            <div class="clock-display" id="liveClock">00:00:00</div>
                            <div class="date-display" id="liveDate"></div>
                            <div class="checkin-btns">
                                <form action="${pageContext.request.contextPath}/chamcong" method="post" style="display:inline">
                                    <input type="hidden" name="action" value="checkin"/>
                                    <input type="hidden" name="idNhanVien" value="${tk.nhanVienId}"/>
                                    <button type="submit" class="btn-checkin in">✓ Check-in</button>
                                </form>
                                <form action="${pageContext.request.contextPath}/chamcong" method="post" style="display:inline">
                                    <input type="hidden" name="action" value="checkout"/>
                                    <input type="hidden" name="idNhanVien" value="${tk.nhanVienId}"/>
                                    <button type="submit" class="btn-checkin out">✗ Check-out</button>
                                </form>
                            </div>
                            <c:if test="${not empty chamCongHomNay}">
                                <div class="checkin-status alert-success alert" style="margin-top:18px">
                                    ✓ Đã check-in lúc <strong>${not empty chamCongHomNay.gioVao ? chamCongHomNay.gioVao : '--'}</strong>
                                    <c:if test="${not empty chamCongHomNay.gioRa}">
                                        &nbsp;| Check-out lúc <strong>${chamCongHomNay.gioRa}</strong>
                                    </c:if>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="box">
                    <div class="box-header"><h3><svg viewBox="0 0 24 24"><polyline points="22 12 18 12 15 21 9 3 6 12 2 12"/></svg>Thống kê tháng này</h3></div>
                    <div class="box-body">
                        <div class="luong-row"><span class="luong-lbl">Tổng ngày đi làm</span><span class="luong-val">${soNgayCong != null ? soNgayCong : '--'}</span></div>
                        <div class="luong-row"><span class="luong-lbl">Ngày nghỉ phép</span><span class="luong-val">${soDonNghiPhep != null ? soDonNghiPhep : '--'}</span></div>
                        <div class="luong-row"><span class="luong-lbl">Nghỉ không phép</span><span class="luong-val">${soNgayVangKhongPhep != null ? soNgayVangKhongPhep : '--'}</span></div>
                        <div class="luong-row"><span class="luong-lbl">Giờ làm thêm</span><span class="luong-val">${soGioLamThem != null ? soGioLamThem : '--'} giờ</span></div>
                        <div style="margin-top:14px">
                            <div style="display:flex;justify-content:space-between;font-size:0.72rem;color:var(--muted);margin-bottom:5px">
                                <span>Tiến độ ngày công</span><span>${soNgayCong != null ? soNgayCong : '0'}/22 ngày</span>
                            </div>
                            <div class="progress-bar"><div class="progress-fill green" style="width:60%"></div></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- ═══ LỊCH CHẤM CÔNG ═══ -->
        <div class="panel" id="panel-lichcong">
            <div class="box">
                <div class="box-header">
                    <h3><svg viewBox="0 0 24 24"><rect x="3" y="4" width="18" height="18" rx="2"/><path d="M16 2v4M8 2v4M3 10h18"/></svg>Lịch chấm công tháng này</h3>
                    <form action="${pageContext.request.contextPath}/chamcong" method="get" style="display:flex;gap:8px;align-items:center">
                        <input type="hidden" name="action" value="lich-ca-nhan"/>
                        <input type="month" name="thang" style="padding:5px 10px;border:1px solid var(--border);border-radius:6px;font-size:0.78rem;"/>
                        <button type="submit" class="btn btn-outline" style="padding:5px 12px;font-size:0.78rem">Xem</button>
                    </form>
                </div>
                <div class="box-body">
                    <div class="cc-legend">
                        <div class="cc-legend-item"><div class="cc-dot" style="background:var(--success)"></div>Đi làm</div>
                        <div class="cc-legend-item"><div class="cc-dot" style="background:var(--warning)"></div>Nghỉ phép</div>
                        <div class="cc-legend-item"><div class="cc-dot" style="background:var(--danger)"></div>Nghỉ không phép</div>
                        <div class="cc-legend-item"><div class="cc-dot" style="background:var(--border)"></div>Chưa có dữ liệu</div>
                    </div>
                    <div class="cc-grid" id="calendarGrid">
                        <div class="cc-day-header">T2</div><div class="cc-day-header">T3</div><div class="cc-day-header">T4</div>
                        <div class="cc-day-header">T5</div><div class="cc-day-header">T6</div><div class="cc-day-header">T7</div>
                        <div class="cc-day-header">CN</div>
                    </div>
                </div>
            </div>

            <div class="box">
                <div class="box-header"><h3><svg viewBox="0 0 24 24"><path d="M12 20h9"/><path d="M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z"/></svg>Chi tiết chấm công</h3></div>
                <div class="box-body" style="padding:0">
                    <table class="data-table">
                        <thead>
                            <tr><th>Ngày</th><th>Giờ vào</th><th>Giờ ra</th><th>Số giờ làm</th><th>Giờ làm thêm</th><th>Trạng thái</th><th>Ghi chú</th></tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty listChamCong}">
                                    <c:forEach var="cc" items="${listChamCong}">
                                    <tr>
                                        <td>${cc.ngayChamCong}</td>
                                        <td>${not empty cc.gioVao ? cc.gioVao : '--'}</td>
                                        <td>${not empty cc.gioRa ? cc.gioRa : '--'}</td>
                                        <td>${cc.soGioLamViec}</td>
                                        <td>${cc.gioLamThem}</td>
                                        <td><span class="badge badge-green">${cc.trangThai}</span></td>
                                        <td>${not empty cc.ghiChu ? cc.ghiChu : '--'}</td>
                                    </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="7"><div class="empty-state">Chưa có dữ liệu chấm công</div></td></tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- ═══ BẢNG LƯƠNG ═══ -->
        <div class="panel" id="panel-luong">
            <div class="grid-2">
                <div class="box">
                    <div class="box-header"><h3><svg viewBox="0 0 24 24"><path d="M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/></svg>Chi tiết lương tháng nhận gần đây nhất</h3></div>
                    <div class="box-body">
                        <c:choose>
                            <c:when test="${not empty bangLuong}">
                                <div class="luong-row"><span class="luong-lbl">Tháng/Năm</span><span class="luong-val">Tháng ${bangLuong.thang}/${bangLuong.nam}</span></div>
                                <div class="luong-row"><span class="luong-lbl">Số ngày làm việc</span><span class="luong-val">${bangLuong.soNgayThucTe}/${bangLuong.soNgayLamViec} ngày</span></div>
                                <div class="luong-row"><span class="luong-lbl">Giờ làm thêm</span><span class="luong-val">${bangLuong.gioLamThem} giờ</span></div>
                                <div class="luong-row" style="margin-top:8px"><span class="luong-lbl">Lương cơ bản</span><span class="luong-val"><fmt:formatNumber value="${bangLuong.luongCoBan}" pattern="#,###"/> đ</span></div>
                                <div class="luong-row"><span class="luong-lbl">Phụ cấp</span><span class="luong-val"><fmt:formatNumber value="${bangLuong.phuCap}" pattern="#,###"/> đ</span></div>
                                <div class="luong-row"><span class="luong-lbl">Lương làm thêm</span><span class="luong-val"><fmt:formatNumber value="${bangLuong.luongLamThem}" pattern="#,###"/> đ</span></div>
                                <div class="luong-row"><span class="luong-lbl">Thưởng</span><span class="luong-val"><fmt:formatNumber value="${bangLuong.thuong}" pattern="#,###"/> đ</span></div>
                                <div class="luong-row deduct"><span class="luong-lbl">Bảo hiểm XH</span><span class="luong-val">- <fmt:formatNumber value="${bangLuong.baoHiemXaHoi}" pattern="#,###"/> đ</span></div>
                                <div class="luong-row deduct"><span class="luong-lbl">Bảo hiểm YT</span><span class="luong-val">- <fmt:formatNumber value="${bangLuong.baoHiemYTe}" pattern="#,###"/> đ</span></div>
                                <div class="luong-row deduct"><span class="luong-lbl">Tạm ứng</span><span class="luong-val">- <fmt:formatNumber value="${bangLuong.tamUng}" pattern="#,###"/> đ</span></div>
                                <div class="luong-row total"><span class="luong-lbl">Thực lãnh</span><span class="luong-val"><fmt:formatNumber value="${bangLuong.luongThucLanh}" pattern="#,###"/> đ</span></div>
                            </c:when>
                            <c:otherwise>
                                <div class="empty-state">
                                    <svg viewBox="0 0 24 24"><path d="M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/></svg>
                                    <p>Chưa có dữ liệu lương</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="box">
                    <div class="box-header"><h3><svg viewBox="0 0 24 24"><polyline points="22 12 18 12 15 21 9 3 6 12 2 12"/></svg>Lịch sử lương</h3></div>
                    <div class="box-body" style="padding:0">
                        <table class="data-table">
                            <thead><tr><th>Tháng</th><th>Năm</th><th>Thực lãnh</th><th>Trạng thái</th></tr></thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty listBangLuong}">
                                        <c:forEach var="b" items="${listBangLuong}">
                                        <tr>
                                            <td>Tháng ${b.thang}</td>
                                            <td>${b.nam}</td>
                                            <td><strong><fmt:formatNumber value="${b.luongThucLanh}" pattern="#,###"/> đ</strong></td>
                                            <td>
                                                <span class="badge ${b.trangThai == 'Da thanh toan' ? 'badge-green' : b.trangThai == 'Da duyet' ? 'badge-blue' : 'badge-orange'}">
                                                    ${b.trangThai}
                                                </span>
                                            </td>
                                        </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr><td colspan="4"><div class="empty-state">Chưa có lịch sử lương</div></td></tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        </div>

        <!-- ═══ XIN NGHỈ PHÉP ═══ -->
        <div class="panel" id="panel-xinnghiphep">
            <div class="box" style="max-width:600px">
                <div class="box-header"><h3><svg viewBox="0 0 24 24"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><path d="M14 2v6h6M16 13H8M16 17H8"/></svg>Tạo đơn xin nghỉ phép</h3></div>
                <div class="box-body">
                    <c:if test="${not empty messageNP}">
                        <div class="alert alert-success">✓ ${messageNP}</div>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/nghiphep" method="post">
                        <input type="hidden" name="action" value="xinNghiPhep"/>
                        <input type="hidden" name="idNhanVien" value="${tk.nhanVienId}"/>
                        <div class="form-grid">
                            <div class="form-group">
                                <label>Loại phép</label>
                                <select name="loaiPhep" required>
                                    <option value="">-- Chọn loại --</option>

                                    <option value="Phep thang">Phép tháng(không quá 2 ngày 1 tháng/không quá 12 ngày 1 năm)</option>
                                    <option value="Phep khac">Phép khác</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Người xin nghỉ</label>
                                <input type="text" name="soNgay" value="${nhanVien.hoTen}" placeholder="1.0" required/>
                            </div>
                            <div class="form-group">
                                <label>Ngày bắt đầu</label>
                                <input type="date" name="ngayBatDau" required/>
                            </div>
                            <div class="form-group">
                                <label>Ngày kết thúc</label>
                                <input type="date" name="ngayKetThuc" required/>
                            </div>
                            <div class="form-group full">
                                <label>Lý do</label>
                                <textarea name="lyDo" placeholder="Nhập lý do xin nghỉ phép..." required></textarea>
                            </div>
                        </div>
                        <div class="form-actions">
                            <button type="reset" class="btn btn-outline">Xóa trắng</button>
                            <button type="submit" class="btn btn-primary">Gửi đơn</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- ═══ DANH SÁCH ĐƠN NGHỈ PHÉP ═══ -->
        <div class="panel" id="panel-danhsachnghiphep">
            <div class="box">
                <div class="box-header">
                    <h3><svg viewBox="0 0 24 24"><path d="M8 6h13M8 12h13M8 18h13M3 6h.01M3 12h.01M3 18h.01"/></svg>Danh sách đơn nghỉ phép</h3>
                    <a class="view-all" href="${pageContext.request.contextPath}/nghiphep?action=xoadondacu&nhanVienId=${tk.nhanVienId}" >- Xóa các đơn cũ</a>
                    <a class="view-all" onclick="showPanel('xinnghiphep',null)">+ Tạo đơn mới</a>

                </div>
                <div class="box-body" style="padding:0">
                    <table class="data-table">
                        <thead><tr><th>#</th><th>Loại phép</th><th>Từ ngày</th><th>Đến ngày</th><th>Số ngày</th><th>Lý do</th><th>Trạng thái</th><th>Người duyệt</th><th>Khác</th></tr></thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty listNghiPhep}">
                                    <c:forEach var="np" items="${listNghiPhep}" varStatus="st">
                                    <tr>
                                        <td>${st.index + 1}</td>
                                        <td>${np.loaiPhep}</td>
                                        <td>${np.ngayBatDau}</td>
                                        <td>${np.ngayKetThuc}</td>
                                        <td>${np.soNgay} ngày</td>
                                        <td style="max-width:160px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap">${np.lyDo}</td>
                                        <td>
                                            <span class="badge ${np.trangThai == 'Da duyet' ? 'badge-green' : np.trangThai == 'Tu choi' ? 'badge-red' : 'badge-orange'}">
                                                ${np.trangThai}
                                            </span>
                                        </td>
                                        <td>${not empty np.tenNguoiDuyet ? np.tenNguoiDuyet : '--'}</td>
<td>
    <c:if test="${np.trangThai == 'Cho duyet'}">
        <a href="${pageContext.request.contextPath}/nghiphep?action=xoatheoid&nghiPhepId=${np.nghiPhepId}"
           style="font-size:0.72rem;color:var(--primary-light)">Hủy</a>
    </c:if>
</td>                                    </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="8"><div class="empty-state">Chưa có đơn nghỉ phép nào</div></td></tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

<!-- ═══ HỢP ĐỒNG ═══ -->
<div class="panel" id="panel-hopdong">
    <div class="box">
        <div class="box-header">
            <h3>
                <svg viewBox="0 0 24 24">
                    <path d="M9 11l3 3L22 4"/>
                    <path d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11"/>
                </svg>
                Hợp đồng lao động
            </h3>
        </div>

        <div class="box-body">
            <c:choose>

                <c:when test="${not empty hopDong}">
                    <div class="hd-card">

                        <div class="hd-title">
                            <span>📄 Hợp đồng: ${hopDong.soHopDong}</span>

                            <span class="badge
                                ${hopDong.trangThai == 'Hieu luc' ? 'badge-green' :
                                  hopDong.trangThai == 'Het han' ? 'badge-orange' :
                                  'badge-red'}">

                                ${hopDong.trangThai}
                            </span>
                        </div>

                        <div class="hd-info-grid">

                            <div class="hd-info-item">
                                <div class="hd-lbl">Loại hợp đồng</div>
                                <div class="hd-val">${hopDong.loaiHopDong}</div>
                            </div>

                            <div class="hd-info-item">
                                <div class="hd-lbl">Lương cơ bản</div>
                                <div class="hd-val">
                                    <fmt:formatNumber value="${hopDong.luongCoBan}" pattern="#,###"/> đ
                                </div>
                            </div>

                            <div class="hd-info-item">
                                <div class="hd-lbl">Ngày bắt đầu</div>
                                <div class="hd-val">${hopDong.ngayBatDau}</div>
                            </div>

                            <div class="hd-info-item">
                                <div class="hd-lbl">Phụ cấp</div>
                                <div class="hd-val">
                                    <fmt:formatNumber value="${hopDong.phuCap}" pattern="#,###"/> đ
                                </div>
                            </div>

                            <div class="hd-info-item">
                                <div class="hd-lbl">Ngày kết thúc</div>
                                <div class="hd-val">
                                    ${not empty hopDong.ngayKetThuc ? hopDong.ngayKetThuc : 'Không xác định'}
                                </div>
                            </div>

                            <div class="hd-info-item">
                                <div class="hd-lbl">Ghi chú</div>
                                <div class="hd-val">
                                    ${not empty hopDong.ghiChu ? hopDong.ghiChu : '--'}
                                </div>
                            </div>

                        </div>

                    </div>
                </c:when>

                <c:otherwise>
                    <div class="empty-state">
                        <svg viewBox="0 0 24 24">
                            <path d="M9 11l3 3L22 4"/>
                        </svg>
                        <p>Chưa có hợp đồng</p>
                    </div>
                </c:otherwise>

            </c:choose>
        </div>
    </div>
</div>
        <!-- ═══ ĐÁNH GIÁ ═══ -->
        <div class="panel" id="panel-danhgia">
            <div class="box">
                <div class="box-header"><h3><svg viewBox="0 0 24 24"><polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/></svg>Kết quả đánh giá</h3></div>
                <div class="box-body" style="padding:0">
                    <table class="data-table">
                        <thead><tr><th>Tháng</th><th>Năm</th><th>Tổng điểm</th><th>Xếp loại</th><th>Nhận xét</th><th>Người đánh giá</th><th>Ngày</th></tr></thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty listDanhGia}">
                                    <c:forEach var="dg" items="${listDanhGia}">
                                    <tr>
                                        <td>Tháng ${dg.thang}</td>
                                        <td>${dg.nam}</td>
                                        <td><strong>${dg.tongDiem}</strong></td>
                                        <td>
                                            <span class="badge ${dg.xepLoai == 'Xuat sac' ? 'badge-green' : dg.xepLoai == 'Gioi' ? 'badge-blue' : dg.xepLoai == 'Kha' ? 'badge-orange' : 'badge-red'}">
                                                ${not empty dg.xepLoai ? dg.xepLoai : '--'}
                                            </span>
                                        </td>
                                        <td>${not empty dg.nhanXet ? dg.nhanXet : '--'}</td>
                                        <td>${not empty dg.tenNguoiDanhGia ? dg.tenNguoiDanhGia : '--'}</td>
                                        <td><c:choose><c:when test="${not empty dg.ngayDanhGia}"><fmt:formatDate value="${dg.ngayDanhGia}" pattern="yyyy-MM-dd"/></c:when><c:otherwise>--</c:otherwise></c:choose></td>
                                    </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="7"><div class="empty-state">Chưa có kết quả đánh giá</div></td></tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- ═══ HỒ SƠ TỔNG HỢP ═══ -->
        <div class="panel" id="panel-hosotonghop">
            <div class="box">
                <div class="box-header"><h3><svg viewBox="0 0 24 24"><path d="M2 3h6a4 4 0 0 1 4 4v14a3 3 0 0 0-3-3H2z"/><path d="M22 3h-6a4 4 0 0 0-4 4v14a3 3 0 0 1 3-3h7z"/></svg>Hồ sơ tổng hợp</h3></div>
                <div class="box-body">
                    <div class="grid-3" style="margin-bottom:0">
                        <!-- Thông tin cá nhân -->
                        <div>
                            <div style="font-size:0.75rem;font-weight:600;color:var(--muted);text-transform:uppercase;letter-spacing:0.08em;margin-bottom:10px">Thông tin cá nhân</div>
                            <c:choose>
                                <c:when test="${not empty nhanVien}">
                                    <div class="profile-row"><span class="lbl">Họ tên</span><span class="val">${nhanVien.hoTen}</span></div>
                                    <div class="profile-row"><span class="lbl">Phòng ban</span><span class="val">${not empty nhanVien.tenPhongBan ? nhanVien.tenPhongBan : '--'}</span></div>
                                    <div class="profile-row"><span class="lbl">Chức vụ</span><span class="val">${not empty nhanVien.tenChucVu ? nhanVien.tenChucVu : '--'}</span></div>
                                    <div class="profile-row"><span class="lbl">Trạng thái</span><span class="val"><span class="badge badge-green">${nhanVien.trangThai}</span></span></div>
                                </c:when>
                                <c:otherwise>
                                    <div class="empty-state" style="padding:10px 0">Chưa có dữ liệu</div>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <!-- Hợp đồng hiện tại -->
                        <div>
                            <div style="font-size:0.75rem;font-weight:600;color:var(--muted);text-transform:uppercase;letter-spacing:0.08em;margin-bottom:10px">
                                Hợp đồng hiện tại
                            </div>

                            <c:choose>

                                <c:when test="${not empty hopDong}">

                                    <div class="profile-row">
                                        <span class="lbl">Số HĐ</span>
                                        <span class="val">${hopDong.soHopDong}</span>
                                    </div>

                                    <div class="profile-row">
                                        <span class="lbl">Loại</span>
                                        <span class="val">${hopDong.loaiHopDong}</span>
                                    </div>

                                    <div class="profile-row">
                                        <span class="lbl">Lương CB</span>
                                        <span class="val">
                                            <fmt:formatNumber value="${hopDong.luongCoBan}" pattern="#,###"/> đ
                                        </span>
                                    </div>

                                    <div class="profile-row">
                                        <span class="lbl">Trạng thái</span>
                                        <span class="val">
                                            <span class="badge badge-green">${hopDong.trangThai}</span>
                                        </span>
                                    </div>

                                </c:when>

                                <c:otherwise>
                                    <div class="empty-state" style="padding:10px 0">
                                        Chưa có hợp đồng
                                    </div>
                                </c:otherwise>

                            </c:choose>
                        </div>

                        <!-- Lương gần nhất -->
                        <div>
                            <div style="font-size:0.75rem;font-weight:600;color:var(--muted);text-transform:uppercase;letter-spacing:0.08em;margin-bottom:10px">Lương gần nhất</div>
                            <c:choose>
                                <c:when test="${not empty bangLuong}">
                                    <div class="profile-row"><span class="lbl">Tháng/Năm</span><span class="val">${bangLuong.thang}/${bangLuong.nam}</span></div>
                                    <div class="profile-row"><span class="lbl">Lương CB</span><span class="val"><fmt:formatNumber value="${bangLuong.luongCoBan}" pattern="#,###"/> đ</span></div>
                                    <div class="profile-row"><span class="lbl">Thực lãnh</span><span class="val" style="color:var(--success);font-weight:600"><fmt:formatNumber value="${bangLuong.luongThucLanh}" pattern="#,###"/> đ</span></div>
                                    <div class="profile-row"><span class="lbl">Trạng thái</span><span class="val"><span class="badge badge-orange">${bangLuong.trangThai}</span></span></div>
                                </c:when>
                                <c:otherwise>
                                    <div class="empty-state" style="padding:10px 0">Chưa có dữ liệu</div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- ═══ THÔNG BÁO ═══ -->
        <div class="panel" id="panel-thongbao">
            <div class="box">
                <div class="box-header">
                <h3>Tất cả thông báo</h3>

                <a class="view-all"
                href="${pageContext.request.contextPath}/thongbao?action=danhdautatcaladadoc&nhanVienId=${tk.nhanVienId}">
                Đánh dấu tất cả là đã đọc
                </a>

                <a class="view-all"
                href="${pageContext.request.contextPath}/thongbao?action=xoatatcathongbaodadoc&nhanVienId=${tk.nhanVienId}"
                style="color:red">
                Xóa tất cả thông báo đã đọc
                </a>

                </div>

                <div class="box-body" style="padding:0">
                    <table class="data-table">
<thead>
<tr>
<th>Tiêu đề</th>
<th>Nội dung</th>
<th>Loại</th>
<th>Ngày</th>
<th>Trạng thái</th>
<th>Khác</th>
</tr>
</thead>                        <tbody>
                            <c:choose>
                                <c:when test="${not empty listThongBao}">
                                    <c:forEach var="tb" items="${listThongBao}">
                                    <tr>
                                        <td><strong>${tb.tieuDe}</strong></td>
                                        <td>${tb.noiDung}</td>
                                        <td><span class="badge badge-blue">${not empty tb.loai ? tb.loai : '--'}</span></td>
                                        <td><c:choose><c:when test="${not empty tb.ngayTao}"><fmt:formatDate value="${tb.ngayTao}" pattern="yyyy-MM-dd"/></c:when><c:otherwise>--</c:otherwise></c:choose></td>
                                        <td><span class="badge ${tb.daDoc == 1 ? 'badge-green' : 'badge-orange'}">${tb.daDoc == 1 ? 'Đã đọc' : 'Chưa đọc'}</span></td>

                                        <td>
                                            <c:if test="${tb.daDoc == 0}">
                                                <a href="${pageContext.request.contextPath}/thongbao?action=danhdaudadoc&id=${tb.thongBaoId}"
                                                   style="font-size:0.72rem;color:var(--primary-light)">Đánh dấu đọc</a>
                                            </c:if>

                                            <c:if test="${tb.daDoc == 1}">
                                                <a href="${pageContext.request.contextPath}/thongbao?action=xoathongbao&id=${tb.thongBaoId}"
                                                   style="font-size:0.72rem;color:red">Xóa</a>
                                            </c:if>
                                        </td>
                                    </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="6"><div class="empty-state">Chưa có thông báo</div></td></tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </div><!-- /content -->
</div><!-- /main -->

<script>
    // ── Clock ──
    function updateClock() {
        const n = new Date();
        const pad = v => String(v).padStart(2, '0');
        document.getElementById('liveClock').textContent = pad(n.getHours()) + ':' + pad(n.getMinutes()) + ':' + pad(n.getSeconds());
        const days = ['Chủ nhật','Thứ hai','Thứ ba','Thứ tư','Thứ năm','Thứ sáu','Thứ bảy'];
        const liveDate = document.getElementById('liveDate');
        if (liveDate) liveDate.textContent = days[n.getDay()] + ', ' + n.getDate() + '/' + (n.getMonth() + 1) + '/' + n.getFullYear();
        document.getElementById('currentDate').textContent = n.getDate() + '/' + (n.getMonth() + 1) + '/' + n.getFullYear();
    }
    updateClock();
    setInterval(updateClock, 1000);

    // ── Panel switcher ──
    const titles = {
        dashboard: 'Dashboard', profile: 'Thông tin cá nhân', doimatkhau: 'Đổi mật khẩu',
        checkin: 'Check-in / Check-out', lichcong: 'Lịch chấm công', luong: 'Bảng lương',
        xinnghiphep: 'Xin nghỉ phép', danhsachnghiphep: 'Danh sách đơn phép',
        hopdong: 'Hợp đồng', danhgia: 'Đánh giá', hosotonghop: 'Hồ sơ tổng hợp', thongbao: 'Thông báo'
    };
    function showPanel(name, el) {
        document.querySelectorAll('.panel').forEach(p => p.classList.remove('active'));
        document.querySelectorAll('.nav-item').forEach(n => n.classList.remove('active'));
        const p = document.getElementById('panel-' + name);
        if (p) p.classList.add('active');
        document.getElementById('topbarTitle').textContent = titles[name] || name;
        if (el) el.classList.add('active');
    }

    // ── Message alert ──
    var msg = "<%= request.getAttribute("message") %>";
    if (msg != "null") { alert(msg); }

    // ── Calendar ──
    const chamCongData = {};
    <c:forEach var="cc" items="${listChamCong}">
        chamCongData["${cc.ngayChamCong}"] = "${cc.trangThai}";
    </c:forEach>

    (function renderCalendar() {
        const grid = document.getElementById('calendarGrid');
        if (!grid) return;
        const now = new Date();
        const y = now.getFullYear(), m = now.getMonth();
        const firstDay = new Date(y, m, 1).getDay();
        const totalDays = new Date(y, m + 1, 0).getDate();
        const offset = firstDay === 0 ? 6 : firstDay - 1;

        for (let i = 0; i < offset; i++) {
            const d = document.createElement('div');
            d.className = 'cc-day empty';
            grid.appendChild(d);
        }

        for (let d = 1; d <= totalDays; d++) {
            const cell = document.createElement('div');
            const dow = new Date(y, m, d).getDay();
            const dateKey = y + '-' + String(m + 1).padStart(2, '0') + '-' + String(d).padStart(2, '0');
            const trangThai = chamCongData[dateKey];

            let cls = 'cc-day';
            if (dow === 0 || dow === 6) cls += ' weekend';
            if (d === now.getDate()) cls += ' today';

            if (trangThai === 'Di lam')              cls += ' dilam';
            else if (trangThai === 'Nghi phep')       cls += ' nghiphep';
            else if (trangThai === 'Nghi khong phep') cls += ' nghikhongphep';

            cell.className = cls;
            cell.textContent = d;
            cell.title = trangThai || (dow === 0 || dow === 6 ? 'Cuối tuần' : 'Chưa có dữ liệu');
            grid.appendChild(cell);
        }
    })();
</script>
</body>
</html>

