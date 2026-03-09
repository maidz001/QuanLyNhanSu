<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Thông Báo</title>
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

    .panel-right {
      flex: 1;
      padding: 52px 48px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: flex-start;
    }

    h1 { font-size: 1.5rem; font-weight: 600; color: #1e3a5f; margin-bottom: 6px; }
    .tagline { font-size: 0.83rem; color: #7a8899; margin-bottom: 36px; }

    .message-box {
      width: 100%;
      background: #fff0f0;
      border: 1px solid #f5c6c6;
      color: #c0392b;
      border-radius: 8px;
      padding: 16px 20px;
      font-size: 0.9rem;
      line-height: 1.6;
      margin-bottom: 32px;
      display: flex;
      align-items: flex-start;
      gap: 10px;
    }
    .message-box .msg-icon {
      font-size: 1.1rem;
      margin-top: 1px;
      flex-shrink: 0;
    }

    .btn {
      padding: 12px 36px;
      background: #1e3a5f;
      color: #fff;
      border: none;
      border-radius: 8px;
      font-family: 'Inter', sans-serif;
      font-size: 0.88rem;
      font-weight: 500;
      cursor: pointer;
      text-decoration: none;
      display: inline-block;
      transition: background 0.2s, transform 0.15s;
    }
    .btn:hover { background: #2d6a9f; transform: translateY(-1px); }
    .btn:active { transform: translateY(0); }
  </style>
</head>
<body>

<div class="container">
  <div class="panel-left">
    <div class="icon">
      <svg viewBox="0 0 24 24">
        <path d="M12 9v4M12 17h.01"/>
        <path d="M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z"/>
      </svg>
    </div>
    <h2>Thông Báo</h2>
    <p>Hệ thống quản lý nhân sự</p>
  </div>

  <div class="panel-right">
    <h1>Có lỗi xảy ra</h1>
    <p class="tagline">Vui lòng đọc thông báo bên dưới</p>

    <div class="message-box">
      <span class="msg-icon">⚠</span>
      <span>
        <%
          String message = (String) request.getAttribute("message");
          out.print(message != null ? message : "Đã xảy ra lỗi không xác định.");
        %>
      </span>
    </div>

    <a href="${pageContext.request.contextPath}/taikhoan?action=login" class="btn">OK, Quay về</a>
  </div>
</div>

</body>
</html>