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
    <title>Sửa hợp đồng</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet"/>
    <style>
        *,*::before,*::after{box-sizing:border-box;margin:0;padding:0}
        :root{--primary:#1e3a5f;--pl:#2d6a9f;--bg:#f0f2f5;--white:#fff;--border:#dce3ec;--text:#1a1a2e;--muted:#7a8899;--success:#27ae60;--danger:#e74c3c;--warning:#f39c12}
        body{font-family:'Inter',sans-serif;background:var(--bg);color:var(--text);min-height:100vh;display:flex;align-items:flex-start;justify-content:center;padding:40px 16px}

        .card{background:var(--white);border-radius:12px;border:1px solid var(--border);width:100%;max-width:700px;overflow:hidden}
        .card-header{padding:20px 28px;border-bottom:1px solid var(--border);background:linear-gradient(135deg,var(--primary) 0%,var(--pl) 100%);display:flex;align-items:center;gap:12px}
        .card-header svg{width:20px;height:20px;stroke:#fff;fill:none;stroke-width:1.8;flex-shrink:0}
        .card-header h2{font-size:1rem;font-weight:600;color:#fff}
        .card-header p{font-size:.73rem;color:rgba(255,255,255,.65);margin-top:2px}
        .card-body{padding:28px}

        .info-bar{background:#f0f4f8;border:1px solid var(--border);border-radius:8px;padding:12px 16px;margin-bottom:20px;display:flex;gap:24px;flex-wrap:wrap}
        .info-bar .ib{font-size:.78rem;color:var(--muted)}.info-bar .ib span{font-weight:600;color:var(--text)}

        .section-title{font-size:.75rem;font-weight:600;color:var(--primary);text-transform:uppercase;letter-spacing:.06em;margin-bottom:12px;padding-bottom:6px;border-bottom:2px solid var(--border)}

        .form-grid{display:grid;grid-template-columns:1fr 1fr;gap:16px}
        .form-group{display:flex;flex-direction:column;gap:4px}
        .form-group.full{grid-column:1/-1}
        .form-group label{font-size:.71rem;font-weight:600;color:var(--muted);text-transform:uppercase;letter-spacing:.05em}
        .form-group input,.form-group select,.form-group textarea{
            padding:9px 12px;border:1.5px solid var(--border);border-radius:8px;
            font-family:'Inter',sans-serif;font-size:.82rem;color:var(--text);
            background:#f8fafc;outline:none;transition:border-color .2s,background .2s
        }
        .form-group input:focus,.form-group select:focus,.form-group textarea:focus{border-color:var(--pl);background:#fff}
        .form-group input[readonly]{background:#eef2f7;color:var(--muted);cursor:not-allowed}
        .form-group textarea{resize:vertical;min-height:80px}

        .gihan-box{background:#fffbeb;border:1.5px solid #f6d860;border-radius:8px;padding:14px 16px;margin-bottom:20px}
        .gihan-box .gihan-title{font-size:.75rem;font-weight:600;color:#92620a;margin-bottom:10px;display:flex;align-items:center;gap:6px}
        .gihan-box .gihan-title svg{width:14px;height:14px;stroke:#92620a;fill:none;stroke-width:2}
        .gihan-row{display:flex;gap:10px;align-items:center}
        .gihan-row select{flex:1;padding:8px 10px;border:1.5px solid #f6d860;border-radius:7px;font-family:'Inter',sans-serif;font-size:.81rem;background:#fff;outline:none}
        .gihan-row select:focus{border-color:var(--warning)}
        .hint{font-size:.67rem;color:var(--muted);margin-top:3px}

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
        <svg viewBox="0 0 24 24"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><path d="M14 2v6h6"/></svg>
        <div>
            <h2>Sửa hợp đồng</h2>
            <p>Cập nhật thông tin hợp đồng lao động</p>
        </div>
    </div>

    <div class="card-body">

        <%-- Thông tin chỉ đọc --%>
        <div class="info-bar">
            <div class="ib">Số HĐ: <span>${hopdong.soHopDong}</span></div>
            <div class="ib">Nhân viên: <span>${not empty tenNhanVien ? tenNhanVien : 'NV#'.concat(hopdong.nhanVienId)}</span></div>
            <div class="ib">Loại HĐ: <span>${hopdong.loaiHopDong}</span></div>
            <div class="ib">Ngày bắt đầu: <span><fmt:formatDate value="${hopdong.ngayBatDau}" pattern="dd/MM/yyyy"/></span></div>
            <div class="ib">Ngày kết thúc: <span>
                <c:choose>
                    <c:when test="${not empty hopdong.ngayKetThuc}">
                        <fmt:formatDate value="${hopdong.ngayKetThuc}" pattern="dd/MM/yyyy"/>
                    </c:when>
                    <c:otherwise>Vô thời hạn</c:otherwise>
                </c:choose>
            </span></div>
        </div>

        <%-- Gia hạn --%>
        <div class="gihan-box">
            <div class="gihan-title">
                <svg viewBox="0 0 24 24"><rect x="3" y="4" width="18" height="18" rx="2"/><path d="M16 2v4M8 2v4M3 10h18"/></svg>
                Gia hạn hợp đồng
            </div>
            <div class="gihan-row">
                <select name="giaHan" id="giaHan">
                    <option value="">-- Không gia hạn --</option>
                    <option value="6">Gia hạn 6 tháng</option>
                    <option value="12">Gia hạn 1 năm</option>
                    <option value="24">Gia hạn 2 năm</option>
                    <option value="36">Gia hạn 3 năm</option>
                    <option value="60">Gia hạn 5 năm</option>
                </select>
            </div>
            <div class="hint">Chọn mốc gia hạn để tự động tính ngày kết thúc mới từ ngày kết thúc hiện tại</div>
        </div>

        <form action="${pageContext.request.contextPath}/hopdong" method="post" id="formSua">
            <input type="hidden" name="action" value="capnhat"/>
            <input type="hidden" name="id" value="${hopdong.hopDongId}"/>
            <input type="hidden" name="giaHan" id="giaHanHidden" value=""/>

            <div class="section-title">Thông tin cập nhật</div>

            <div class="form-grid">

                <div class="form-group">
                    <label>Trạng thái <span style="color:var(--danger)">*</span></label>
                    <select name="trangThai" required>
                        <option value="Hieu luc"  ${hopdong.trangThai == 'Hieu luc'  ? 'selected' : ''}>Hiệu lực</option>
                        <option value="Het han"   ${hopdong.trangThai == 'Het han'   ? 'selected' : ''}>Hết hạn</option>
                        <option value="Tam dung"  ${hopdong.trangThai == 'Tam dung'  ? 'selected' : ''}>Tạm dừng</option>
                        <option value="Da huy"    ${hopdong.trangThai == 'Da huy'    ? 'selected' : ''}>Đã hủy</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>Lương cơ bản (đ) <span style="color:var(--danger)">*</span></label>
                    <input type="number" name="luongCoBan" value="${hopdong.luongCoBan}" min="0" required/>
                </div>

                <div class="form-group">
                    <label>Phụ cấp (đ)</label>
                    <input type="number" name="phuCap" value="${hopdong.phuCap}" min="0"/>
                </div>

                <div class="form-group full">
                    <label>Ghi chú</label>
                    <textarea name="ghiChu">${hopdong.ghiChu}</textarea>
                </div>

            </div>

            <div class="form-footer">
                <a href="${pageContext.request.contextPath}/hopdong" class="btn btn-outline">
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

<script>
    // Khi submit form, copy giá trị gia hạn vào hidden input
    document.getElementById('formSua').addEventListener('submit', function() {
        document.getElementById('giaHanHidden').value = document.getElementById('giaHan').value;
    });
    document.getElementById('giaHan').addEventListener('change', function() {
        const trangThaiGroup = document.querySelector('select[name="trangThai"]').closest('.form-group');
        if (this.value !== '') {
            // Có gia hạn → ẩn ô trạng thái và set cứng Hieu luc
            trangThaiGroup.style.display = 'none';
            document.querySelector('select[name="trangThai"]').value = 'Hieu luc';
        } else {
            // Không gia hạn → hiện lại
            trangThaiGroup.style.display = '';
        }
    });
</script>

</body>
</html>