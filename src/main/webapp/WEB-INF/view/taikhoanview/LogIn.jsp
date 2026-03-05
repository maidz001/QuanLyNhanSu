<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Đăng Nhập</title>
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600&display=swap" rel="stylesheet"/>
  <style>
    *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

    body {
      min-height: 100vh;
      display: flex;
      align-items: center;
      justify-content: center;
      background: #f0f2f5;
      font-family: 'Inter', sans-serif;
      color: #1a1a2e;
    }

    .container {
      display: flex;
      width: 860px;
      min-height: 520px;
      background: #fff;
      border-radius: 16px;
      box-shadow: 0 8px 40px rgba(0,0,0,0.10);
      overflow: hidden;
    }

    .panel-left {
      width: 340px;
      background: linear-gradient(145deg, #1e3a5f 0%, #2d6a9f 100%);
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 48px 36px;
      color: #fff;
      text-align: center;
    }
    .panel-left .icon {
      width: 64px; height: 64px;
      background: rgba(255,255,255,0.15);
      border-radius: 50%;
      display: flex; align-items: center; justify-content: center;
      margin-bottom: 24px;
    }
    .panel-left .icon svg { width: 32px; height: 32px; stroke: #fff; fill: none; stroke-width: 1.8; }
    .panel-left h2 { font-size: 1.6rem; font-weight: 600; margin-bottom: 12px; }
    .panel-left p { font-size: 0.88rem; color: rgba(255,255,255,0.75); line-height: 1.65; }
    .panel-left .switch-btn {
      margin-top: 32px;
      padding: 10px 28px;
      border: 1.5px solid rgba(255,255,255,0.6);
      border-radius: 24px;
      color: #fff;
      font-size: 0.82rem;
      font-weight: 500;
      text-decoration: none;
      transition: background 0.2s;
    }
    .panel-left .switch-btn:hover { background: rgba(255,255,255,0.15); }

    .panel-right {
      flex: 1;
      padding: 52px 48px;
      display: flex;
      flex-direction: column;
      justify-content: center;
    }

    h1 { font-size: 1.5rem; font-weight: 600; color: #1e3a5f; margin-bottom: 6px; }
    .tagline { font-size: 0.83rem; color: #7a8899; margin-bottom: 36px; }

    .field { margin-bottom: 20px; }
    label {
      display: block;
      font-size: 0.78rem;
      font-weight: 500;
      color: #4a5568;
      margin-bottom: 7px;
    }
    input[type="text"],
    input[type="password"] {
      width: 100%;
      padding: 11px 14px;
      border: 1.5px solid #dce3ec;
      border-radius: 8px;
      font-family: 'Inter', sans-serif;
      font-size: 0.9rem;
      color: #1a1a2e;
      background: #f8fafc;
      outline: none;
      transition: border-color 0.2s, background 0.2s;
    }
    input:focus { border-color: #2d6a9f; background: #fff; }
    input::placeholder { color: #b0bac8; }

    .options-row {
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-bottom: 28px;
    }
    .checkbox-wrap {
      display: flex; align-items: center; gap: 7px;
      font-size: 0.8rem; color: #4a5568; cursor: pointer;
    }
    .checkbox-wrap input { accent-color: #2d6a9f; width: 14px; height: 14px; }
    .forgot { font-size: 0.8rem; color: #2d6a9f; text-decoration: none; }
    .forgot:hover { text-decoration: underline; }

    .btn {
      width: 100%;
      padding: 12px;
      background: #1e3a5f;
      color: #fff;
      border: none;
      border-radius: 8px;
      font-family: 'Inter', sans-serif;
      font-size: 0.88rem;
      font-weight: 500;
      cursor: pointer;
      transition: background 0.2s, transform 0.15s;
    }
    .btn:hover { background: #2d6a9f; transform: translateY(-1px); }
    .btn:active { transform: translateY(0); }

    .error-box {
      background: #fff0f0;
      border: 1px solid #f5c6c6;
      color: #c0392b;
      border-radius: 7px;
      padding: 10px 14px;
      font-size: 0.82rem;
      margin-bottom: 20px;
    }
  </style>
</head>
<body>

<div class="container">
  <div class="panel-left">
    <div class="icon">
      <svg viewBox="0 0 24 24">
        <circle cx="12" cy="8" r="4"/>
        <path d="M4 20c0-4 3.6-7 8-7s8 3 8 7"/>
      </svg>
    </div>
    <h2>Xin chào!</h2>
    <p>Chưa có tài khoản?<br/>Đăng ký ngay để bắt đầu sử dụng hệ thống.</p>
    <a href="signin.jsp" class="switch-btn">Đăng Ký</a>
  </div>

  <div class="panel-right">
    <h1>Đăng Nhập</h1>
    <p class="tagline">Vui lòng nhập thông tin tài khoản của bạn</p>

    <%
      String message = (String) request.getAttribute("message");
      if (message != null) {
    %>
    <div class="error-box">⚠ <%= message %></div>
    <% } %>

    <form action="hello" method="post">
      <input type="hidden" name="action" value="dangnhap"/>

      <div class="field">
        <label for="taiKhoan">Tên đăng nhập</label>
        <input type="text" id="taiKhoan" name="taiKhoan"
               placeholder="Nhập tên đăng nhập"
               value="<%= request.getParameter("taiKhoan") != null ? request.getParameter("taiKhoan") : "" %>"
               required/>
      </div>

      <div class="field">
        <label for="matKhau">Mật khẩu</label>
        <input type="password" id="matKhau" name="matKhau"
               placeholder="Nhập mật khẩu" required/>
      </div>

      <div class="options-row">
        <label class="checkbox-wrap">
          <input type="checkbox" name="remember" value="true"/> Ghi nhớ đăng nhập
        </label>
        <a href="#" class="forgot">Quên mật khẩu?</a>
      </div>

      <button type="submit" class="btn">Đăng Nhập</button>
    </form>
  </div>
</div>

</body>
</html>