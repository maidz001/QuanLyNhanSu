<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    .panel-left p  { font-size: 0.88rem; color: rgba(255,255,255,0.75); line-height: 1.65; }
    .panel-right {
      flex: 1;
      padding: 52px 48px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: flex-start;
    }
    h1       { font-size: 1.5rem; font-weight: 600; color: #1e3a5f; margin-bottom: 6px; }
    .tagline { font-size: 0.83rem; color: #7a8899; margin-bottom: 36px; }
    .message-box {
      width: 100%;
      border-radius: 8px;
      padding: 16px 20px;
      font-size: 0.9rem;
      line-height: 1.6;
      margin-bottom: 32px;
      display: flex;
      align-items: flex-start;
      gap: 10px;
    }
    .message-box.error   { background:#fff0f0; border:1px solid #f5c6c6; color:#c0392b; }
    .message-box.warning { background:#fffbe6; border:1px solid #ffe58f; color:#856404; }
    .message-box.info    { background:#e8f4fd; border:1px solid #b8daff; color:#1e3a5f; }
    .message-box.success { background:#f0fff4; border:1px solid #b2dfdb; color:#1b6b45; }
    .msg-icon { font-size: 1.1rem; margin-top: 1px; flex-shrink: 0; }
    .status-code { font-size: 0.78rem; color: #aab; margin-bottom: 20px; margin-top: -28px; }
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
    .btn:hover  { background: #2d6a9f; transform: translateY(-1px); }
    .btn:active { transform: translateY(0); }
  </style>
</head>
<body>
<%
    String message     = (String)  request.getAttribute("message");
    String msgType     = "error";
    String icon        = "⚠";
    String title       = "Có lỗi xảy ra";
    String tagline     = "Vui lòng đọc thông báo bên dưới";
    Integer statusCode = (Integer)   request.getAttribute("javax.servlet.error.status_code");
    Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");

    if (message == null) {
        if (statusCode != null) {
            switch (statusCode) {
                case 404:
                    message = "Trang bạn tìm kiếm không tồn tại hoặc đã bị xóa.";
                    title   = "Không tìm thấy trang";
                    icon    = "🔍";
                    msgType = "warning";
                    break;
                case 403:
                    message = "Bạn không có quyền truy cập trang này.";
                    title   = "Truy cập bị từ chối";
                    icon    = "🚫";
                    msgType = "error";
                    break;
                case 500:
                    message = "Máy chủ gặp sự cố. Vui lòng thử lại sau.";
                    title   = "Lỗi máy chủ";
                    icon    = "🔧";
                    msgType = "error";
                    break;
                default:
                    message = "Đã xảy ra lỗi HTTP " + statusCode + ".";
            }
        } else if (throwable != null) {
            message = "Lỗi hệ thống: " + throwable.getMessage();
        } else {
            message = "Đã xảy ra lỗi không xác định.";
        }
    }

    String attrType = (String) request.getAttribute("msgType");
    if (attrType != null) msgType = attrType;

    if      (msgType.equals("success")) { icon = "✅"; title = "Thành công"; tagline = "Thao tác đã được thực hiện."; }
    else if (msgType.equals("info"))    { icon = "ℹ";  title = "Thông báo";  tagline = ""; }
    else if (msgType.equals("warning")) { icon = "⚠"; }

    boolean daDangNhap = session != null && session.getAttribute("taiKhoanDangDangNhap") != null;
    String backUrl     = request.getContextPath() + (daDangNhap ? "/" : "/taikhoan?action=login");
    String backLabel   = daDangNhap ? "← Quay về trang chủ" : "OK, Quay về đăng nhập";
%>

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
    <h1><%= title %></h1>
    <% if (!tagline.isEmpty()) { %>
      <p class="tagline"><%= tagline %></p>
    <% } %>
    <% if (statusCode != null) { %>
      <p class="status-code">Mã lỗi: <%= statusCode %></p>
    <% } %>
    <div class="message-box <%= msgType %>">
      <span class="msg-icon"><%= icon %></span>
      <span><%= message %></span>
    </div>
    <a href="<%= backUrl %>" class="btn"><%= backLabel %></a>
  </div>
</div>

</body>
</html>