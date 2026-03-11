<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Đặt Lại Mật Khẩu</title>
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
        .icon-wrap { width: 56px; height: 56px; background: #eafaf1; border-radius: 50%; display: flex; align-items: center; justify-content: center; margin: 0 auto 20px; }
        .icon-wrap svg { width: 24px; height: 24px; stroke: var(--success); fill: none; stroke-width: 1.8; }
        h2 { font-size: 1rem; font-weight: 600; color: var(--text); text-align: center; margin-bottom: 6px; }
        .desc { font-size: 0.78rem; color: var(--muted); text-align: center; margin-bottom: 24px; line-height: 1.6; }
        .alert { padding: 9px 14px; border-radius: 7px; font-size: 0.78rem; margin-bottom: 16px; }
        .alert-error   { background: #fdedec; border: 1px solid #f5b7b1; color: var(--danger); }
        .alert-success { background: #eafaf1; border: 1px solid #a9dfbf; color: var(--success); }
        .form-group { margin-bottom: 14px; }
        .form-group label { display: block; font-size: 0.73rem; font-weight: 500; color: var(--muted); margin-bottom: 5px; }
        .input-wrap { position: relative; }
        .form-group input { width: 100%; padding: 9px 36px 9px 12px; border: 1.5px solid var(--border); border-radius: 7px; font-family: 'Inter', sans-serif; font-size: 0.83rem; color: var(--text); background: #f8fafc; outline: none; transition: border-color 0.2s; }
        .form-group input:focus { border-color: var(--primary-light); background: #fff; }
        .toggle-pw { position: absolute; right: 10px; top: 50%; transform: translateY(-50%); cursor: pointer; background: none; border: none; padding: 0; }
        .toggle-pw svg { width: 15px; height: 15px; stroke: var(--muted); fill: none; stroke-width: 1.8; }
        .strength-bar { height: 4px; border-radius: 4px; background: var(--border); margin-top: 6px; overflow: hidden; }
        .strength-fill { height: 100%; border-radius: 4px; transition: width 0.3s, background 0.3s; width: 0%; }
        .strength-text { font-size: 0.68rem; color: var(--muted); margin-top: 3px; }
        .btn { width: 100%; padding: 10px; background: var(--primary); color: #fff; border: none; border-radius: 7px; font-family: 'Inter', sans-serif; font-size: 0.85rem; font-weight: 500; cursor: pointer; transition: background 0.2s; margin-top: 6px; }
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
        <svg viewBox="0 0 24 24"><rect x="3" y="11" width="18" height="11" rx="2"/><path d="M7 11V7a5 5 0 0 1 10 0v4"/></svg>
    </div>
    <h2>Đặt lại mật khẩu</h2>
    <p class="desc">OTP đã xác nhận. Nhập mật khẩu mới cho tài khoản của bạn.</p>

    <% String message = (String) request.getAttribute("message");
       if (message != null && !message.isEmpty()) { %>
        <div class="alert <%= message.contains("không khớp") ? "alert-error" : "alert-success" %>">
            <%= message %>
        </div>
    <% } %>

    <form action="${pageContext.request.contextPath}/taikhoan" method="post">
        <input type="hidden" name="action" value="doimatkhauotp"/>

        <div class="form-group">
            <label>Mật khẩu mới</label>
            <div class="input-wrap">
                <input type="password" name="matKhauMoi" id="matKhauMoi" placeholder="••••••••" required/>
                <button type="button" class="toggle-pw" onclick="togglePw('matKhauMoi', this)">
                    <svg viewBox="0 0 24 24"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg>
                </button>
            </div>
            <div class="strength-bar"><div class="strength-fill" id="strengthFill"></div></div>
            <div class="strength-text" id="strengthText"></div>
        </div>

        <div class="form-group">
            <label>Xác nhận mật khẩu mới</label>
            <div class="input-wrap">
                <input type="password" name="xacNhanMatKhau" id="xacNhanMatKhau" placeholder="••••••••" required/>
                <button type="button" class="toggle-pw" onclick="togglePw('xacNhanMatKhau', this)">
                    <svg viewBox="0 0 24 24"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg>
                </button>
            </div>
        </div>

        <button type="submit" class="btn">Đặt lại mật khẩu</button>
    </form>
    <a href="${pageContext.request.contextPath}/taikhoan?action=login" class="back-link">← Quay lại đăng nhập</a>
</div>
<script>
    function togglePw(id, btn) {
        const inp = document.getElementById(id);
        inp.type = inp.type === 'password' ? 'text' : 'password';
    }
    document.getElementById('matKhauMoi').addEventListener('input', function() {
        const v = this.value;
        const fill = document.getElementById('strengthFill');
        const text = document.getElementById('strengthText');
        let score = 0;
        if (v.length >= 6) score++;
        if (v.length >= 10) score++;
        if (/[A-Z]/.test(v)) score++;
        if (/[0-9]/.test(v)) score++;
        if (/[^A-Za-z0-9]/.test(v)) score++;
        const levels = [
            { w: '0%',   bg: '',                    t: '' },
            { w: '25%',  bg: 'var(--danger)',        t: 'Rất yếu' },
            { w: '50%',  bg: '#f39c12',              t: 'Yếu' },
            { w: '75%',  bg: '#f1c40f',              t: 'Trung bình' },
            { w: '90%',  bg: '#2ecc71',              t: 'Mạnh' },
            { w: '100%', bg: 'var(--success)',       t: 'Rất mạnh' },
        ];
        fill.style.width = levels[score].w;
        fill.style.background = levels[score].bg;
        text.textContent = levels[score].t;
        text.style.color = levels[score].bg;
    });
</script>
</body>
</html>
