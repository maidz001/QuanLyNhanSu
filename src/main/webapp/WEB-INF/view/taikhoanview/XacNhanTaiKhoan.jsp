<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Xác Nhận Tài Khoản</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet"/>
    <style>
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }
        :root {
            --primary: #1e3a5f; --primary-light: #2d6a9f;
            --bg: #f0f2f5; --white: #ffffff; --border: #dce3ec;
            --text: #1a1a2e; --muted: #7a8899;
            --danger: #e74c3c; --success: #27ae60;
        }
        body { font-family: 'Inter', sans-serif; background: var(--bg); min-height: 100vh; display: flex; align-items: center; justify-content: center; }
        .card { background: var(--white); border-radius: 14px; border: 1px solid var(--border); padding: 40px 36px; width: 100%; max-width: 420px; box-shadow: 0 4px 24px rgba(30,58,95,0.08); }
        .logo { text-align: center; margin-bottom: 28px; }
        .logo .title { font-size: 1.1rem; font-weight: 700; color: var(--primary); }
        .logo .title span { color: var(--primary-light); }
        .logo .sub { font-size: 0.72rem; color: var(--muted); margin-top: 3px; letter-spacing: 0.08em; text-transform: uppercase; }
        .icon-wrap { width: 56px; height: 56px; background: #ebf4ff; border-radius: 50%; display: flex; align-items: center; justify-content: center; margin: 0 auto 20px; }
        .icon-wrap svg { width: 24px; height: 24px; stroke: var(--primary-light); fill: none; stroke-width: 1.8; }
        h2 { font-size: 1rem; font-weight: 600; color: var(--text); text-align: center; margin-bottom: 6px; }
        .desc { font-size: 0.78rem; color: var(--muted); text-align: center; margin-bottom: 24px; line-height: 1.6; }
        .alert { padding: 9px 14px; border-radius: 7px; font-size: 0.78rem; margin-bottom: 16px; }
        .alert-error   { background: #fdedec; border: 1px solid #f5b7b1; color: var(--danger); }
        .alert-success { background: #eafaf1; border: 1px solid #a9dfbf; color: var(--success); }
        .form-group { margin-bottom: 16px; }
        .form-group label { display: block; font-size: 0.73rem; font-weight: 500; color: var(--muted); margin-bottom: 5px; }
        .form-group input { width: 100%; padding: 9px 12px; border: 1.5px solid var(--border); border-radius: 7px; font-family: 'Inter', sans-serif; font-size: 0.83rem; color: var(--text); background: #f8fafc; outline: none; transition: border-color 0.2s; }
        .form-group input:focus { border-color: var(--primary-light); background: #fff; }
        .btn { width: 100%; padding: 10px; background: var(--primary); color: #fff; border: none; border-radius: 7px; font-family: 'Inter', sans-serif; font-size: 0.85rem; font-weight: 500; cursor: pointer; transition: background 0.2s; }
        .btn:hover { background: var(--primary-light); }
        .back-link { display: block; text-align: center; margin-top: 16px; font-size: 0.78rem; color: var(--primary-light); text-decoration: none; }
        .back-link:hover { text-decoration: underline; }
    </style>
</head>
<body>
<div class="card">
    <div class="logo">
        <div class="title">Quản Lý <span>Nhân Sự</span></div>
        <div class="sub">Hệ thống quản lý nhân sự</div>
    </div>
    <div class="icon-wrap">
        <svg viewBox="0 0 24 24"><circle cx="12" cy="8" r="4"/><path d="M4 20c0-4 3.6-7 8-7s8 3 8 7"/></svg>
    </div>
    <h2>Xác nhận tài khoản</h2>
    <p class="desc">Nhập tên đăng nhập của bạn. Chúng tôi sẽ gửi mã OTP về email đã đăng ký.</p>

    <% String message = (String) request.getAttribute("message");
       if (message != null && !message.isEmpty()) { %>
        <div class="alert <%= message.contains("tồn tại") ? "alert-error" : "alert-success" %>">
            <%= message %>
        </div>
    <% } %>

    <form action="${pageContext.request.contextPath}/taikhoan" method="post">
        <input type="hidden" name="action" value="xacnhantaikhoan"/>
        <div class="form-group">
            <label>Tên đăng nhập</label>
            <input type="text" name="tentaiKhoan" placeholder="Nhập tên đăng nhập..." required autofocus/>
        </div>
        <button type="submit" class="btn">Xác nhận & Gửi OTP</button>
    </form>
    <a href="${pageContext.request.contextPath}/taikhoan?action=login" class="back-link">← Quay lại đăng nhập</a>
</div>
</body>
</html>
