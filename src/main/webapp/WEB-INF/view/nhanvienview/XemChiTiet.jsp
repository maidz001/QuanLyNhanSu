<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Chi tiết — ${nv.hoTen}</title>
    <link href="https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:wght@300;400;500;600;700&display=swap" rel="stylesheet"/>
    <style>
        *,*::before,*::after{box-sizing:border-box;margin:0;padding:0}
        :root{--primary:#1e3a5f;--pl:#2d6a9f;--bg:#f0f2f5;--white:#fff;--border:#dce3ec;--text:#1a1a2e;--muted:#7a8899;--success:#27ae60;--danger:#e74c3c;--warning:#f39c12}
        body{font-family:'Be Vietnam Pro',sans-serif;background:var(--bg);color:var(--text);min-height:100vh}

        .topbar{background:var(--white);border-bottom:1px solid var(--border);padding:0 32px;height:56px;display:flex;align-items:center;justify-content:space-between;position:sticky;top:0;z-index:50}
        .topbar-left{display:flex;align-items:center;gap:12px}
        .back-btn{display:flex;align-items:center;gap:6px;color:var(--muted);font-size:.8rem;text-decoration:none;padding:6px 12px;border-radius:7px;border:1.5px solid var(--border);transition:all .2s}
        .back-btn:hover{border-color:var(--pl);color:var(--primary)}
        .back-btn svg{width:14px;height:14px;stroke:currentColor;fill:none;stroke-width:2}
        .breadcrumb{font-size:.78rem;color:var(--muted)}
        .breadcrumb span{color:var(--primary);font-weight:600}
        .topbar-actions{display:flex;gap:8px}

        .page{max-width:1100px;margin:0 auto;padding:28px 32px}
        .layout{display:grid;grid-template-columns:290px 1fr;gap:20px;align-items:start}

        .card{background:var(--white);border-radius:12px;border:1px solid var(--border);overflow:hidden;margin-bottom:16px}
        .card:last-child{margin-bottom:0}
        .card-header{padding:14px 20px;border-bottom:1px solid var(--border);display:flex;align-items:center;justify-content:space-between}
        .card-header h3{font-size:.86rem;font-weight:600;color:var(--primary);display:flex;align-items:center;gap:7px}
        .card-header h3 svg{width:13px;height:13px;stroke:var(--pl);fill:none;stroke-width:2}
        .card-body{padding:18px 20px}

        .profile-card{text-align:center;padding:26px 20px}
        .avatar-wrap{position:relative;display:inline-block;margin-bottom:14px}
        .avatar-img{width:96px;height:96px;border-radius:50%;object-fit:cover;border:3px solid var(--border)}
        .avatar-placeholder{width:96px;height:96px;border-radius:50%;background:linear-gradient(135deg,#ebf4ff,#c5d8f0);border:3px solid var(--border);display:flex;align-items:center;justify-content:center;font-size:1.9rem;font-weight:700;color:var(--pl)}
        .status-dot{position:absolute;bottom:3px;right:3px;width:14px;height:14px;border-radius:50%;border:2px solid var(--white)}
        .status-dot.active{background:var(--success)}.status-dot.inactive{background:var(--danger)}
        .profile-name{font-size:1.05rem;font-weight:700;margin-bottom:3px}
        .profile-role{font-size:.76rem;color:var(--muted);margin-bottom:10px}
        .profile-badge{display:inline-flex;align-items:center;gap:4px;padding:3px 11px;border-radius:20px;font-size:.71rem;font-weight:500}
        .profile-badge.active{background:#eafaf1;color:var(--success)}.profile-badge.inactive{background:#fdedec;color:var(--danger)}

        .info-list{list-style:none}
        .info-item{display:flex;align-items:center;justify-content:space-between;padding:9px 0;border-bottom:1px solid var(--border);font-size:.79rem}
        .info-item:last-child{border-bottom:none}
        .info-label{color:var(--muted);display:flex;align-items:center;gap:5px}
        .info-label svg{width:11px;height:11px;stroke:currentColor;fill:none;stroke-width:2}
        .info-value{font-weight:500;text-align:right;max-width:55%}

        .tab-nav{display:flex;border-bottom:2px solid var(--border);margin-bottom:20px}
        .tab-btn{padding:9px 18px;font-size:.8rem;font-weight:500;color:var(--muted);cursor:pointer;border:none;background:none;border-bottom:2px solid transparent;margin-bottom:-2px;transition:all .2s;font-family:'Be Vietnam Pro',sans-serif}
        .tab-btn.active{color:var(--primary);border-bottom-color:var(--primary)}
        .tab-pane{display:none}.tab-pane.active{display:block}

        .section-title{font-size:.69rem;font-weight:600;color:var(--muted);text-transform:uppercase;letter-spacing:.08em;margin:16px 0 10px;padding-bottom:6px;border-bottom:1px solid var(--border)}
        .section-title:first-child{margin-top:0}

        .form-grid{display:grid;grid-template-columns:1fr 1fr;gap:12px}
        .form-group{display:flex;flex-direction:column;gap:4px}
        .form-group.full{grid-column:1/-1}
        .form-group label{font-size:.69rem;font-weight:500;color:var(--muted);text-transform:uppercase;letter-spacing:.05em}
        .form-group input,.form-group select{padding:8px 11px;border:1.5px solid var(--border);border-radius:7px;font-family:'Be Vietnam Pro',sans-serif;font-size:.81rem;color:var(--text);background:#f8fafc;outline:none;transition:border-color .2s}
        .form-group input:focus,.form-group select:focus{border-color:var(--pl);background:#fff}
        .form-group input[readonly]{background:#f0f2f5;color:var(--muted);cursor:not-allowed}
        .form-actions{display:flex;gap:8px;justify-content:flex-end;margin-top:16px;padding-top:14px;border-top:1px solid var(--border)}

        .btn{padding:7px 16px;border-radius:8px;font-family:'Be Vietnam Pro',sans-serif;font-size:.79rem;font-weight:600;cursor:pointer;border:none;transition:all .2s;display:inline-flex;align-items:center;gap:5px;text-decoration:none}
        .btn svg{width:13px;height:13px;stroke:currentColor;fill:none;stroke-width:2.2}
        .btn-primary{background:var(--primary);color:#fff}.btn-primary:hover{background:var(--pl)}
        .btn-success{background:var(--success);color:#fff}.btn-success:hover{opacity:.88}
        .btn-danger{background:var(--danger);color:#fff}.btn-danger:hover{opacity:.88}
        .btn-outline{background:transparent;border:1.5px solid var(--border);color:var(--muted)}.btn-outline:hover{border-color:var(--pl);color:var(--primary)}
        .btn-sm{padding:5px 11px;font-size:.74rem}

        .alert{padding:10px 14px;border-radius:8px;font-size:.8rem;margin-bottom:16px;display:flex;align-items:center;gap:8px}
        .alert-success{background:#eafaf1;color:var(--success);border:1px solid #a9dfbf}
        .alert-danger{background:#fdedec;color:var(--danger);border:1px solid #f5b7b1}
        .alert svg{width:14px;height:14px;stroke:currentColor;fill:none;stroke-width:2;flex-shrink:0}

        .modal-overlay{position:fixed;inset:0;background:rgba(0,0,0,.45);z-index:200;display:none;align-items:center;justify-content:center}
        .modal-overlay.show{display:flex}
        .modal{background:var(--white);border-radius:14px;padding:28px;max-width:380px;width:90%;text-align:center}
        .modal h4{font-size:1rem;font-weight:700;margin-bottom:8px}
        .modal p{font-size:.82rem;color:var(--muted);margin-bottom:20px}
        .modal-actions{display:flex;gap:8px;justify-content:center}
    </style>
</head>
<body>

<div class="topbar">
    <div class="topbar-left">
        <a href="${pageContext.request.contextPath}/nhanvien" class="back-btn">
            <svg viewBox="0 0 24 24"><polyline points="15 18 9 12 15 6"/></svg>Quay lại
        </a>
        <div class="breadcrumb">Nhân viên / <span>${nv.hoTen}</span></div>
    </div>
    <div class="topbar-actions">
        <button class="btn btn-danger btn-sm" onclick="document.getElementById('modal-xoa').classList.add('show')">
            <svg viewBox="0 0 24 24"><polyline points="3 6 5 6 21 6"/><path d="M19 6l-1 14H6L5 6"/><path d="M10 11v6M14 11v6"/></svg>Xóa nhân viên
        </button>
    </div>
</div>

<div class="page">
    <c:if test="${not empty message}">
        <div class="alert alert-success"><svg viewBox="0 0 24 24"><polyline points="20 6 9 17 4 12"/></svg>${message}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger"><svg viewBox="0 0 24 24"><circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/></svg>${error}</div>
    </c:if>

    <div class="layout">

        <!-- TRÁI -->
        <div>
            <div class="card">
                <div class="profile-card">
                    <div class="avatar-wrap">
                        <c:choose>
                            <c:when test="${not empty nv.anhDaiDien}">
                                <img src="${pageContext.request.contextPath}/${nv.anhDaiDien}" class="avatar-img" alt="avatar"/>
                            </c:when>
                            <c:otherwise>
                                <div class="avatar-placeholder">${fn:substring(nv.hoTen,0,1)}</div>
                            </c:otherwise>
                        </c:choose>
                        <span class="status-dot ${nv.trangThai == 'Dang lam viec' ? 'active' : 'inactive'}"></span>
                    </div>
                    <div class="profile-name">${nv.hoTen}</div>
                    <div class="profile-role">${not empty nv.tenChucVu ? nv.tenChucVu : '--'} • ${not empty nv.tenPhongBan ? nv.tenPhongBan : '--'}</div>
                    <span class="profile-badge ${nv.trangThai == 'Dang lam viec' ? 'active' : 'inactive'}">
                        ${nv.trangThai == 'Dang lam viec' ? '● Đang làm việc' : '● Nghỉ việc'}
                    </span>
                </div>
            </div>

            <div class="card">
                <div class="card-header"><h3><svg viewBox="0 0 24 24"><circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/></svg>Thông tin nhanh</h3></div>
                <div class="card-body" style="padding:10px 18px">
                    <ul class="info-list">
                        <li class="info-item">
                            <span class="info-label"><svg viewBox="0 0 24 24"><rect x="2" y="7" width="20" height="14" rx="2"/><path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16"/></svg>Mã NV</span>
                            <span class="info-value"><strong>${nv.maNhanVien}</strong></span>
                        </li>
                        <li class="info-item">
                            <span class="info-label"><svg viewBox="0 0 24 24"><path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"/><polyline points="22,6 12,13 2,6"/></svg>Email</span>
                            <span class="info-value">${not empty nv.email ? nv.email : '--'}</span>
                        </li>
                        <li class="info-item">
                            <span class="info-label"><svg viewBox="0 0 24 24"><path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07A19.5 19.5 0 0 1 4.69 13a19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 3.52 2h3a2 2 0 0 1 2 1.72c.127.96.361 1.903.7 2.81a2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45c.907.339 1.85.573 2.81.7A2 2 0 0 1 22 16.92z"/></svg>SĐT</span>
                            <span class="info-value">${not empty nv.dienThoai ? nv.dienThoai : '--'}</span>
                        </li>
                        <li class="info-item">
                            <span class="info-label"><svg viewBox="0 0 24 24"><rect x="3" y="4" width="18" height="18" rx="2"/><path d="M16 2v4M8 2v4M3 10h18"/></svg>Vào làm</span>
                            <span class="info-value"><fmt:formatDate value="${nv.ngayVaoLam}" pattern="dd/MM/yyyy"/></span>
                        </li>
                        <li class="info-item">
                            <span class="info-label"><svg viewBox="0 0 24 24"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>Giới tính</span>
                            <span class="info-value">${not empty nv.gioiTinh ? nv.gioiTinh : '--'}</span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <!-- PHẢI -->
        <div class="card">
            <div class="card-body">
                <div class="tab-nav">
                    <button class="tab-btn active" onclick="switchTab('tab-chitiet',this)">📋 Chi tiết</button>
                    <button class="tab-btn" onclick="switchTab('tab-sua',this)">✏️ Chỉnh sửa</button>
                </div>

                <!-- TAB CHI TIẾT -->
                <div id="tab-chitiet" class="tab-pane active">
                    <div class="section-title">Thông tin cá nhân</div>
                    <div class="form-grid">
                        <div class="form-group"><label>Họ tên</label><input readonly value="${nv.hoTen}"/></div>
                        <div class="form-group"><label>Mã nhân viên</label><input readonly value="${nv.maNhanVien}"/></div>
                        <div class="form-group"><label>Email</label><input readonly value="${not empty nv.email ? nv.email : ''}"/></div>
                        <div class="form-group"><label>Số điện thoại</label><input readonly value="${not empty nv.dienThoai ? nv.dienThoai : ''}"/></div>
                        <div class="form-group"><label>Ngày sinh</label><input readonly value="<fmt:formatDate value='${nv.ngaySinh}' pattern='dd/MM/yyyy'/>"/></div>
                        <div class="form-group"><label>Giới tính</label><input readonly value="${not empty nv.gioiTinh ? nv.gioiTinh : ''}"/></div>
                        <div class="form-group"><label>CMND / CCCD</label><input readonly value="${not empty nv.soCmnd ? nv.soCmnd : ''}"/></div>
                        <div class="form-group"><label>Trạng thái</label><input readonly value="${nv.trangThai}"/></div>
                        <div class="form-group full"><label>Địa chỉ</label><input readonly value="${not empty nv.diaChi ? nv.diaChi : ''}"/></div>
                    </div>

                    <div class="section-title">Công việc</div>
                    <div class="form-grid">
                        <div class="form-group"><label>Phòng ban</label><input readonly value="${not empty nv.tenPhongBan ? nv.tenPhongBan : '--'}"/></div>
                        <div class="form-group"><label>Chức vụ</label><input readonly value="${not empty nv.tenChucVu ? nv.tenChucVu : '--'}"/></div>
                        <div class="form-group"><label>Ngày vào làm</label><input readonly value="<fmt:formatDate value='${nv.ngayVaoLam}' pattern='dd/MM/yyyy'/>"/></div>
                    </div>
                </div>

                <!-- TAB SỬA -->
                <div id="tab-sua" class="tab-pane">
                    <form action="${pageContext.request.contextPath}/nhanvien" method="post">
                        <input type="hidden" name="action" value="suabyquanly"/>
                        <input type="hidden" name="nhanVienId" value="${nv.nhanVienId}"/>

                        <div class="section-title">Thông tin cá nhân</div>
                        <div class="form-grid">
                            <div class="form-group"><label>Họ tên</label><input type="text" name="hoTen" value="${nv.hoTen}" required/></div>
                            <div class="form-group"><label>Mã NV (không đổi)</label><input readonly value="${nv.maNhanVien}"/></div>
                            <div class="form-group"><label>Email</label><input type="email" name="email" value="${not empty nv.email ? nv.email : ''}"/></div>
                            <div class="form-group"><label>Số điện thoại</label><input type="text" name="dienThoai" value="${not empty nv.dienThoai ? nv.dienThoai : ''}"/></div>
                            <div class="form-group"><label>Ngày sinh</label><input type="date" name="ngaySinh" value="<fmt:formatDate value='${nv.ngaySinh}' pattern='yyyy-MM-dd'/>"/></div>
                            <div class="form-group">
                                <label>Giới tính</label>
                                <select name="gioiTinh">
                                    <option value="Nam"  ${nv.gioiTinh == 'Nam'  ? 'selected' : ''}>Nam</option>
                                    <option value="Nu"   ${nv.gioiTinh == 'Nu'   ? 'selected' : ''}>Nữ</option>
                                    <option value="Khac" ${nv.gioiTinh == 'Khac' ? 'selected' : ''}>Khác</option>
                                </select>
                            </div>
                            <div class="form-group"><label>CMND / CCCD</label><input type="text" name="soCmnd" value="${not empty nv.soCmnd ? nv.soCmnd : ''}"/></div>
                            <div class="form-group">
                                <label>Trạng thái</label>
                                <select name="trangThai">
                                    <option value="Dang lam viec" ${nv.trangThai == 'Dang lam viec' ? 'selected' : ''}>Đang làm việc</option>
                                    <option value="Nghi viec"     ${nv.trangThai == 'Nghi viec'     ? 'selected' : ''}>Nghỉ việc</option>
                                </select>
                            </div>
                            <div class="form-group full"><label>Địa chỉ</label><input type="text" name="diaChi" value="${not empty nv.diaChi ? nv.diaChi : ''}"/></div>
                        </div>

                        <div class="section-title">Công việc</div>
                        <div class="form-grid">
                            <div class="form-group">
                                <label>Phòng ban</label>
                                <select name="phongBanId">
                                    <c:forEach var="pb" items="${listPhongBan}">
                                        <option value="${pb.phongBanId}" ${pb.phongBanId == nv.phongBanId ? 'selected' : ''}>${pb.tenPhongBan}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Chức vụ</label>
                                <select name="chucVuId">
                                    <c:forEach var="cv" items="${listChucVu}">
                                        <option value="${cv.chucVuId}" ${cv.chucVuId == nv.chucVuId ? 'selected' : ''}>${cv.tenChucVu}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group"><label>Ngày vào làm</label><input type="date" name="ngayVaoLam" value="<fmt:formatDate value='${nv.ngayVaoLam}' pattern='yyyy-MM-dd'/>"/></div>
                        </div>

                        <div class="form-actions">
                            <button type="reset" class="btn btn-outline">Hoàn tác</button>
                            <button type="submit" class="btn btn-success">
                                <svg viewBox="0 0 24 24"><polyline points="20 6 9 17 4 12"/></svg>Lưu thay đổi
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- MODAL XÓA -->
<div class="modal-overlay" id="modal-xoa">
    <div class="modal">
        <h4>⚠️ Xác nhận xóa</h4>
        <p>Bạn có chắc muốn xóa nhân viên <strong>${nv.hoTen}</strong>?<br/>Hành động này không thể hoàn tác.</p>
        <div class="modal-actions">
            <button class="btn btn-outline" onclick="document.getElementById('modal-xoa').classList.remove('show')">Hủy</button>
            <a href="${pageContext.request.contextPath}/nhanvien?action=xoa&id=${nv.nhanVienId}" class="btn btn-danger">Xác nhận xóa</a>
        </div>
    </div>
</div>

<script>
    function switchTab(id, btn) {
        document.querySelectorAll('.tab-pane').forEach(p => p.classList.remove('active'));
        document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
        document.getElementById(id).classList.add('active');
        btn.classList.add('active');
    }
</script>
</body>
</html>
