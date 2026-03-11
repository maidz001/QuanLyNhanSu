<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Nhập Mã OTP</title>
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
        .card { background: var(--white); border-radius: 14px; border: 1px solid var(--border); padding: 40px 36px; width: 100%; max-width: 400px; box-shadow: 0 4px 24px rgba(30,58,95,0.08); }
        .logo { text-align: center; margin-bottom: 28px; }
        .logo .title { font-size: 1.1rem; font-weight: 700; color: var(--primary); }
        .logo .title span { color: var(--primary-light); }
        .logo .sub { font-size: 0.72rem; color: var(--muted); margin-top: 3px; letter-spacing: 0.08em; text-transform: uppercase; }
        .icon-wrap { width: 56px; height: 56px; background: #ebf4ff; border-radius: 50%; display: flex; align-items: center; justify-content: center; margin: 0 auto 20px; }
        .icon-wrap svg { width: 24px; height: 24px; stroke: var(--primary-light); fill: none; stroke-width: 1.8; }
        h2 { font-size: 1rem; font-weight: 600; color: var(--text); text-align: center; margin-bottom: 6px; }
        .desc { font-size: 0.78rem; color: var(--muted); text-align: center; margin-bottom: 20px; line-height: 1.6; }
        .alert { padding: 9px 14px; border-radius: 7px; font-size: 0.78rem; margin-bottom: 16px; }
        .alert-error   { background: #fdedec; border: 1px solid #f5b7b1; color: var(--danger); }
        .alert-success { background: #eafaf1; border: 1px solid #a9dfbf; color: var(--success); }
        .timer { text-align: center; font-size: 0.75rem; color: var(--muted); margin-bottom: 20px; }
        .timer span { font-weight: 600; color: var(--danger); }
        .form-group { margin-bottom: 16px; }
        .form-group label { display: block; font-size: 0.73rem; font-weight: 500; color: var(--muted); margin-bottom: 5px; }
        .otp-input { width: 100%; padding: 13px 12px; border: 1.5px solid var(--border); border-radius: 7px; font-family: 'Inter', sans-serif; font-size: 1.6rem; font-weight: 700; letter-spacing: 0.4em; text-align: center; color: var(--primary); background: #f8fafc; outline: none; transition: border-color 0.2s; }
        .otp-input:focus { border-color: var(--primary-light); background: #fff; }
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
        <svg viewBox="0 0 24 24"><path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"/><polyline points="22,6 12,13 2,6"/></svg>
    </div>
    <h2>Nhập mã OTP</h2>
    <p class="desc">Mã xác nhận đã được gửi về email của bạn. Vui lòng kiểm tra hộp thư.</p>

    <% String message = (String) request.getAttribute("message");
       if (message != null && !message.isEmpty()) { %>
        <div class="alert <%= message.contains("không đúng") || message.contains("hết hạn") ? "alert-error" : "alert-success" %>">
            <%= message %>
        </div>
    <% } %>

    <div class="timer">Mã hết hạn sau <span id="countdown">05:00</span></div>

    <form action="${pageContext.request.contextPath}/taikhoan" method="post">
        <input type="hidden" name="action" value="xacnhanotp"/>
        <div class="form-group">
            <label>Mã OTP (6 số)</label>
            <input type="text" name="otp" class="otp-input"
                   placeholder="• • • • • •" maxlength="6"
                   inputmode="numeric" pattern="[0-9]{6}" required autofocus/>
        </div>
        <button type="submit" class="btn">Xác nhận OTP</button>
    </form>
    <a href="${pageContext.request.contextPath}/taikhoan?action=quenmatkhau" class="back-link">← Gửi lại mã OTP</a>
</div>
<script>
    let seconds = 5 * 60;
    const el = document.getElementById('countdown');
    const t = setInterval(() => {
        seconds--;
        el.textContent = String(Math.floor(seconds/60)).padStart(2,'0') + ':' + String(seconds%60).padStart(2,'0');
        if (seconds <= 0) { clearInterval(t); el.textContent = 'Hết hạn'; }
    }, 1000);
    document.querySelector('.otp-input').addEventListener('input', function() {
        this.value = this.value.replace(/[^0-9]/g, '');
    });
</script>
</body>
</html>
