<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${empty sessionScope.taiKhoanDangDangNhap}">
    <c:redirect url="${pageContext.request.contextPath}/taikhoan?action=login"/>
</c:if>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Sửa chức vụ</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet"/>
    <style>
        *,*::before,*::after{box-sizing:border-box;margin:0;padding:0}
        :root{--primary:#1e3a5f;--pl:#2d6a9f;--bg:#f0f2f5;--white:#fff;--border:#dce3ec;--text:#1a1a2e;--muted:#7a8899;--success:#27ae60;--danger:#e74c3c}
        body{font-family:'Inter',sans-serif;background:var(--bg);color:var(--text);min-height:100vh;display:flex;align-items:flex-start;justify-content:center;padding:40px 16px}

        .card{background:var(--white);border-radius:12px;border:1px solid var(--border);width:100%;max-width:640px;overflow:hidden}

        .card-header{padding:20px 28px;border-bottom:1px solid var(--border);background:linear-gradient(135deg,var(--primary) 0%,var(--pl) 100%);display:flex;align-items:center;gap:12px}
        .card-header svg{width:20px;height:20px;stroke:#fff;fill:none;stroke-width:1.8;flex-shrink:0}
        .card-header h2{font-size:1rem;font-weight:600;color:#fff}
        .card-header p{font-size:.73rem;color:rgba(255,255,255,.65);margin-top:2px}

        .card-body{padding:28px}

        .form-grid{display:grid;grid-template-columns:1fr 1fr;gap:16px}
        .form-group{display:flex;flex-direction:column;gap:4px}
        .form-group.full{grid-column:1/-1}
        .form-group label{font-size:.71rem;font-weight:600;color:var(--muted);text-transform:uppercase;letter-spacing:.05em}

        .form-group input,
        .form-group select,
        .form-group textarea{
            padding:9px 12px;
            border:1.5px solid var(--border);
            border-radius:8px;
            font-family:'Inter',sans-serif;
            font-size:.82rem;
            color:var(--text);
            background:#f8fafc;
            outline:none;
            transition:border-color .2s,background .2s
        }
        .form-group input:focus,
        .form-group select:focus,
        .form-group textarea:focus{border-color:var(--pl);background:#fff}
        .form-group input[readonly]{background:#eef2f7;color:var(--muted);cursor:not-allowed}
        .form-group textarea{resize:vertical;min-height:88px}

        .form-footer{display:flex;gap:10px;justify-content:flex-end;margin-top:24px;padding-top:18px;border-top:1px solid var(--border)}

        .btn{padding:8px 20px;border-radius:8px;font-family:'Inter',sans-serif;font-size:.8rem;font-weight:500;cursor:pointer;border:none;transition:all .2s;display:inline-flex;align-items:center;gap:6px;text-decoration:none}
        .btn svg{width:14px;height:14px;stroke:currentColor;fill:none;stroke-width:2}
        .btn-primary{background:var(--primary);color:#fff}.btn-primary:hover{background:var(--pl)}
        .btn-outline{background:transparent;border:1.5px solid var(--border);color:var(--muted)}.btn-outline:hover{border-color:var(--pl);color:var(--primary)}
    </style>
</head>
<body>

<div class="card">
    <div class="card-header">
        <svg viewBox="0 0 24 24"><circle cx="12" cy="8" r="6"/><path d="M15.477 12.89L17 22l-5-3-5 3 1.523-9.11"/></svg>
        <div>
            <h2>Sửa chức vụ</h2>
            <p>Cập nhật thông tin chức vụ</p>
        </div>
    </div>

    <div class="card-body">
        <form action="${pageContext.request.contextPath}/chucvu" method="post">
            <input type="hidden" name="action" value="capnhat"/>
            <input type="hidden" name="id" value="${chucvu.chucVuId}"/>

            <div class="form-grid">

                <div class="form-group">
                    <label>Mã chức vụ</label>
                    <input type="text" value="${chucvu.maChucVu}" readonly/>
                </div>

                <div class="form-group">
                    <label>Tên chức vụ <span style="color:var(--danger)">*</span></label>
                    <input type="text" name="tenChucVu" value="${chucvu.tenChucVu}" required/>
                </div>

                <div class="form-group">
                    <label>Cấp bậc</label>
                    <input type="number" name="capBac" min="1" value="${chucvu.capBac}"/>
                </div>

                <div class="form-group">
                    <label>Lương cơ bản</label>
                    <input type="number" name="luongCoBan" value="${chucvu.luongCoBan}"/>
                </div>

                <div class="form-group">
                    <label>Trạng thái <span style="color:var(--danger)">*</span></label>
                    <select name="trangThai" required>
                        <option value="1" ${chucvu.trangThai == 1 ? 'selected' : ''}>Hoạt động</option>
                        <option value="0" ${chucvu.trangThai == 0 ? 'selected' : ''}>Ngưng hoạt động</option>
                    </select>
                </div>

                <div class="form-group full">
                    <label>Mô tả</label>
                    <textarea name="moTa">${chucvu.moTa}</textarea>
                </div>

            </div>

            <div class="form-footer">
                <a href="${pageContext.request.contextPath}/chucvu" class="btn btn-outline">
                    <svg viewBox="0 0 24 24"><line x1="19" y1="12" x2="5" y2="12"/><polyline points="12 19 5 12 12 5"/></svg>
                    Quay lại
                </a>
                <button type="submit" class="btn btn-primary">
                    <svg viewBox="0 0 24 24"><path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"/><polyline points="17 21 17 13 7 13 7 21"/><polyline points="7 3 7 8 15 8"/></svg>
                    Cập nhật
                </button>
            </div>
        </form>
    </div>
</div>

</body>
</html>