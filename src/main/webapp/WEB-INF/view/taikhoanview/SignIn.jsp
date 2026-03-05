<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Đăng Ký</title>
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
      width: 920px;
      min-height: 580px;
      background: #fff;
      border-radius: 16px;
      box-shadow: 0 8px 40px rgba(0,0,0,0.10);
      overflow: hidden;
    }

    .panel-form {
      flex: 1;
      padding: 48px 52px;
      display: flex;
      flex-direction: column;
      justify-content: center;
    }

    h1 { font-size: 1.5rem; font-weight: 600; color: #1e3a5f; margin-bottom: 6px; }
    .tagline { font-size: 0.83rem; color: #7a8899; margin-bottom: 32px; }

    .grid-2 {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 0 20px;
    }

    .field { margin-bottom: 18px; }
    .field.full { grid-column: 1 / -1; }

    label {
      display: block;
      font-size: 0.78rem;
      font-weight: 500;
      color: #4a5568;
      margin-bottom: 6px;
    }

    input[type="text"],
    input[type="password"],
    select {
      width: 100%;
      padding: 10px 13px;
      border: 1.5px solid #dce3ec;
      border-radius: 8px;
      font-family: 'Inter', sans-serif;
      font-size: 0.88rem;
      color: #1a1a2e;
      background: #f8fafc;
      outline: none;
      transition: border-color 0.2s, background 0.2s;
      appearance: none;
    }
    input:focus, select:focus { border-color: #2d6a9f; background: #fff; }
    input::placeholder { color: #b0bac8; }
    select {
      background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='8' viewBox='0 0 12 8'%3E%3Cpath d='M1 1l5 5 5-5' stroke='%237a8899' stroke-width='1.5' fill='none' stroke-linecap='round'/%3E%3C/svg%3E");
      background-repeat: no-repeat;
      background-position: right 12px center;
      padding-right: 32px;
    }

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
      margin-top: 8px;
      transition: background 0.2s, transform 0.15s;
    }
    .btn:hover { background: #2d6a9f; transform: translateY(-1px); }
    .btn:active { transform: translateY(0); }

    .success-box {
      background: #f0fff4;
      border: 1px solid #9ae6b4;
      color: #276749;
      border-radius: 7px;
      padding: 10px 14px;
      font-size: 0.82rem;
      margin-bottom: 18px;
    }

    .panel-right {
      width: 300px;
      background: linear-gradient(145deg, #1e3a5f 0%, #2d6a9f 100%);
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 48px 32px;
      color: #fff;
      text-align: center;
    }
    .panel-right .icon {
      width: 64px; height: 64px;
      background: rgba(255,255,255,0.15);
      border-radius: 50%;
      display: flex; align-items: center; justify-content: center;
      margin-bottom: 24px;
    }
    .panel-right .icon svg { width: 30px; height: 30px; stroke: #fff; fill: none; stroke-width: 1.8; }
    .panel-right h2 { font-size: 1.4rem; font-weight: 600; margin-bottom: 12px; }
    .panel-right p { font-size: 0.85rem; color: rgba(255,255,255,0.75); line-height: 1.65; }
    .panel-right .switch-btn {
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
    .panel-right .switch-btn:hover { background: rgba(255,255,255,0.15); }
  </style>
</head>
<body>

<div class="container">
  <div class="panel-form">
    <h1>Đăng Ký Tài Khoản</h1>
    <p class="tagline">Điền đầy đủ thông tin để tạo tài khoản mới</p>

    <%
      String message = (String) request.getAttribute("message");
      if (message != null) {
    %>
    <div class="success-box">✓ <%= message %></div>
    <% } %>

    <form action="taikhoan" method="post">
      <input type="hidden" name="action" value="dangky"/>

      <div class="grid-2">

        <div class="field">
          <label for="taiKhoanId">Mã tài khoản</label>
          <input type="text" id="taiKhoanId" name="taiKhoanId"
                 placeholder="VD: 1001" required/>
        </div>

        <div class="field">
          <label for="nhanVienId">Mã nhân viên</label>
          <input type="text" id="nhanVienId" name="nhanVienId"
                 placeholder="VD: 501" required/>
        </div>

        <div class="field">
          <label for="hoTen">Họ và tên</label>
          <input type="text" id="hoTen" name="hoTen"
                 placeholder="Nguyễn Văn A" required/>
        </div>

        <div class="field">
          <label for="tenDangNhap">Tên đăng nhập</label>
          <input type="text" id="tenDangNhap" name="tenDangNhap"
                 placeholder="Tên đăng nhập" required/>
        </div>

        <div class="field">
          <label for="matKhau">Mật khẩu</label>
          <input type="password" id="matKhau" name="matKhau"
                 placeholder="••••••••" required/>
        </div>

        <div class="field">
          <label for="xacNhanMatKhau">Xác nhận mật khẩu</label>
          <input type="password" id="xacNhanMatKhau" name="xacNhanMatKhau"
                 placeholder="••••••••" required/>
        </div>

        <div class="field">
          <label for="vaiTro">Vai trò</label>
          <select id="vaiTro" name="vaiTro" required>
            <option value="" disabled selected>Chọn vai trò</option>
            <option value="nhanvien">Nhân viên</option>
            <option value="quanly">Quản lý</option>
          </select>
        </div>

        <div class="field">
          <label for="trangThai">Trạng thái</label>
          <select id="trangThai" name="trangThai" required>
            <option value="1">Hoạt động</option>
            <option value="0">Vô hiệu hóa</option>
          </select>
        </div>

        <div class="field full">
          <button type="submit" class="btn">Tạo Tài Khoản</button>
        </div>

      </div>
    </form>
  </div>

  <div class="panel-right">
    <div class="icon">
      <svg viewBox="0 0 24 24">
        <circle cx="12" cy="8" r="4"/>
        <path d="M4 20c0-4 3.6-7 8-7s8 3 8 7"/>
        <path d="M19 3v6M16 6h6" stroke-linecap="round"/>
      </svg>
    </div>
    <h2>Đã có tài khoản?</h2>
    <p>Đăng nhập ngay để truy cập vào hệ thống quản lý.</p>
    <a href="taikhoan?action=login" class="switch-btn">Đăng Nhập</a>
  </div>
</div>

</body>
</html>