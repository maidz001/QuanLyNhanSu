<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${empty sessionScope.taiKhoanDangDangNhap}">
    <c:redirect url="${pageContext.request.contextPath}/taikhoan?action=login"/>
</c:if>
<c:set var="tk" value="${sessionScope.taiKhoanDangDangNhap}"/>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Trang Chủ Quản Lý</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet"/>
    <style>
        *,*::before,*::after{box-sizing:border-box;margin:0;padding:0}
        :root{--primary:#1e3a5f;--pl:#2d6a9f;--bg:#f0f2f5;--white:#fff;--border:#dce3ec;--text:#1a1a2e;--muted:#7a8899;--success:#27ae60;--warning:#f39c12;--danger:#e74c3c;--info:#2980b9;--sw:260px}
        body{font-family:'Inter',sans-serif;background:var(--bg);color:var(--text);min-height:100vh;display:flex}
        /* SIDEBAR */
        .sidebar{width:var(--sw);background:var(--primary);min-height:100vh;display:flex;flex-direction:column;position:fixed;top:0;left:0;z-index:100;overflow-y:auto}
        .sh{padding:22px 20px 14px;border-bottom:1px solid rgba(255,255,255,.1)}
        .sh .logo{font-size:.95rem;font-weight:700;color:#fff}.sh .logo span{color:#7ab3d9}
        .sh .rb{margin-top:4px;font-size:.67rem;color:rgba(255,255,255,.4);letter-spacing:.1em;text-transform:uppercase}
        .su{padding:12px 20px;display:flex;align-items:center;gap:10px;border-bottom:1px solid rgba(255,255,255,.1)}
        .av{width:34px;height:34px;background:rgba(255,255,255,.15);border-radius:50%;display:flex;align-items:center;justify-content:center;font-size:.82rem;font-weight:600;color:#fff;flex-shrink:0;text-transform:uppercase}
        .su .nm{font-size:.8rem;font-weight:500;color:#fff}.su .sb{font-size:.67rem;color:rgba(255,255,255,.45)}
        .snav{flex:1;padding:6px 0}
        .ns{padding:9px 20px 3px;font-size:.61rem;color:rgba(255,255,255,.28);letter-spacing:.12em;text-transform:uppercase}
        .ni{display:flex;align-items:center;gap:9px;padding:8px 20px;color:rgba(255,255,255,.66);font-size:.8rem;cursor:pointer;transition:all .18s;border-left:3px solid transparent;text-decoration:none}
        .ni:hover,.ni.active{background:rgba(255,255,255,.08);color:#fff;border-left-color:#7ab3d9}
        .ni svg{width:14px;height:14px;stroke:currentColor;fill:none;stroke-width:1.8;flex-shrink:0}
        .sf{padding:12px 20px;border-top:1px solid rgba(255,255,255,.1)}
        .lb{display:flex;align-items:center;gap:8px;color:rgba(255,255,255,.52);font-size:.79rem;cursor:pointer;text-decoration:none;transition:color .2s}
        .lb:hover{color:#fff}.lb svg{width:14px;height:14px;stroke:currentColor;fill:none;stroke-width:1.8}
        /* MAIN */
        .main{margin-left:var(--sw);flex:1;display:flex;flex-direction:column}
        .tb{background:var(--white);border-bottom:1px solid var(--border);padding:0 28px;height:54px;display:flex;align-items:center;justify-content:space-between;position:sticky;top:0;z-index:50}
        .tb .pt{font-size:.92rem;font-weight:600;color:var(--primary)}
        .tr{display:flex;align-items:center;gap:12px}
        .di{font-size:.73rem;color:var(--muted)}
        .nb{position:relative;width:32px;height:32px;border-radius:50%;background:var(--bg);border:1px solid var(--border);display:flex;align-items:center;justify-content:center;cursor:pointer}
        .nb svg{width:14px;height:14px;stroke:var(--muted);fill:none;stroke-width:1.8}
        .nd{position:absolute;top:5px;right:5px;width:6px;height:6px;background:var(--danger);border-radius:50%}
        .cnt{padding:22px 28px;flex:1}
        /* PANELS */
        .panel{display:none}.panel.active{display:block}
        /* WELCOME */
        .wb{background:linear-gradient(135deg,var(--primary) 0%,var(--pl) 100%);border-radius:12px;padding:22px 28px;color:#fff;display:flex;align-items:center;justify-content:space-between;margin-bottom:18px}
        .wb h2{font-size:1.15rem;font-weight:600;margin-bottom:3px}.wb p{font-size:.78rem;color:rgba(255,255,255,.7)}
        .bi{width:52px;height:52px;background:rgba(255,255,255,.15);border-radius:50%;display:flex;align-items:center;justify-content:center}
        .bi svg{width:24px;height:24px;stroke:#fff;fill:none;stroke-width:1.6}
        /* STATS */
        .sg{display:grid;grid-template-columns:repeat(5,1fr);gap:12px;margin-bottom:18px}
        .sc{background:var(--white);border-radius:10px;padding:16px;border:1px solid var(--border)}
        .sc .sct{display:flex;align-items:center;justify-content:space-between;margin-bottom:10px}
        .si{width:38px;height:38px;border-radius:8px;display:flex;align-items:center;justify-content:center}
        .si svg{width:17px;height:17px;fill:none;stroke-width:1.8}
        .si.blue{background:#ebf4ff}.si.blue svg{stroke:var(--info)}
        .si.green{background:#eafaf1}.si.green svg{stroke:var(--success)}
        .si.orange{background:#fef9e7}.si.orange svg{stroke:var(--warning)}
        .si.red{background:#fdedec}.si.red svg{stroke:var(--danger)}
        .si.purple{background:#f3e8ff}.si.purple svg{stroke:#7c3aed}
        .sc .val{font-size:1.5rem;font-weight:700;color:var(--text);line-height:1}
        .sc .lbl{font-size:.69rem;color:var(--muted);margin-top:3px}
        /* BOXES */
        .g2{display:grid;grid-template-columns:1fr 1fr;gap:16px;margin-bottom:16px}
        .g3{display:grid;grid-template-columns:1fr 1fr 1fr;gap:16px;margin-bottom:16px}
        .box{background:var(--white);border-radius:10px;border:1px solid var(--border);overflow:hidden;margin-bottom:16px}
        .box:last-child{margin-bottom:0}
        .bh{padding:13px 18px;border-bottom:1px solid var(--border);display:flex;align-items:center;justify-content:space-between}
        .bh h3{font-size:.84rem;font-weight:600;color:var(--primary);display:flex;align-items:center;gap:7px}
        .bh h3 svg{width:13px;height:13px;stroke:var(--pl);fill:none;stroke-width:2}
        .bh .va{font-size:.71rem;color:var(--pl);cursor:pointer;text-decoration:none}
        .bh .ac{display:flex;gap:8px;align-items:center}
        .bb{padding:16px}
        /* SEARCH BAR */
        .search-bar{display:flex;gap:8px;padding:12px 16px;border-bottom:1px solid var(--border);flex-wrap:wrap;align-items:center;background:#fafbfc}
        .search-bar input,.search-bar select{padding:7px 10px;border:1.5px solid var(--border);border-radius:7px;font-size:.79rem;outline:none;background:#fff;font-family:'Inter',sans-serif;color:var(--text)}
        .search-bar input:focus,.search-bar select:focus{border-color:var(--pl)}
        .search-bar input[type="text"]{min-width:220px}
        .search-count{font-size:.72rem;color:var(--muted);margin-left:auto;padding:4px 8px;background:#ebf4ff;border-radius:20px;display:none}
        .search-count.show{display:inline-block}
        /* TABLE */
        .dt{width:100%;border-collapse:collapse;font-size:.79rem}
        .dt th{padding:8px 12px;text-align:left;font-size:.65rem;font-weight:600;color:var(--muted);letter-spacing:.06em;text-transform:uppercase;border-bottom:1px solid var(--border);background:#f8fafc}
        .dt td{padding:8px 12px;border-bottom:1px solid var(--border);color:var(--text);vertical-align:middle}
        .dt tr:last-child td{border-bottom:none}
        .dt tr:hover td{background:#f8fafc}
        .no-results{text-align:center;padding:24px;color:var(--muted);font-size:.78rem;display:none}
        /* BADGE */
        .badge{display:inline-block;padding:2px 8px;border-radius:20px;font-size:.66rem;font-weight:500}
        .bg{background:#eafaf1;color:var(--success)}.bo{background:#fef9e7;color:var(--warning)}
        .br{background:#fdedec;color:var(--danger)}.bb2{background:#ebf4ff;color:var(--info)}
        .bgr{background:#f4f6f8;color:var(--muted)}.bp{background:#f3e8ff;color:#7c3aed}
        /* FORM */
        .fg{display:grid;grid-template-columns:1fr 1fr;gap:13px}
        .fm label{display:block;font-size:.71rem;font-weight:500;color:var(--muted);margin-bottom:4px}
        .fm input,.fm select,.fm textarea{width:100%;padding:8px 10px;border:1.5px solid var(--border);border-radius:7px;font-family:'Inter',sans-serif;font-size:.81rem;color:var(--text);background:#f8fafc;outline:none;transition:border-color .2s}
        .fm input:focus,.fm select:focus,.fm textarea:focus{border-color:var(--pl);background:#fff}
        .fm textarea{resize:vertical;min-height:72px}
        .full{grid-column:1/-1}
        .btn{padding:7px 16px;border-radius:7px;font-family:'Inter',sans-serif;font-size:.78rem;font-weight:500;cursor:pointer;border:none;transition:all .2s;display:inline-flex;align-items:center;gap:5px;text-decoration:none}
        .btn svg{width:13px;height:13px;stroke:currentColor;fill:none;stroke-width:2}
        .bp2{background:var(--primary);color:#fff}.bp2:hover{background:var(--pl)}
        .bs{background:var(--success);color:#fff}.bs:hover{opacity:.88}
        .bd{background:var(--danger);color:#fff}.bd:hover{opacity:.88}
        .bw{background:var(--warning);color:#fff}.bw:hover{opacity:.88}
        .bo2{background:transparent;border:1.5px solid var(--border);color:var(--muted)}.bo2:hover{border-color:var(--pl);color:var(--primary)}
        .bsm{padding:4px 10px;font-size:.72rem}
        .fa{display:flex;gap:8px;justify-content:flex-end;margin-top:16px;padding-top:13px;border-top:1px solid var(--border)}
        /* TABS */
        .tabb{display:flex;border-bottom:2px solid var(--border);margin-bottom:16px}
        .tbt{padding:8px 18px;font-size:.8rem;font-weight:500;color:var(--muted);cursor:pointer;border:none;background:none;border-bottom:2px solid transparent;margin-bottom:-2px;transition:all .2s}
        .tbt.active{color:var(--primary);border-bottom-color:var(--primary)}
        .tc{display:none}.tc.active{display:block}
        /* EMPTY */
        .es{text-align:center;padding:24px;color:var(--muted);font-size:.78rem}
        .es svg{width:32px;height:32px;stroke:#dce3ec;fill:none;stroke-width:1.2;margin:0 auto 7px;display:block}
        /* DEPT ITEM */
        .di2{display:flex;align-items:center;justify-content:space-between;padding:9px 0;border-bottom:1px solid var(--border)}
        .di2:last-child{border-bottom:none}
        .di2 .dn{font-size:.81rem;font-weight:500;color:var(--text)}
    </style>
</head>
<body>

<!-- SIDEBAR -->
<aside class="sidebar">
    <div class="sh">
        <div class="logo">Quản Lý <span>Nhân Sự</span></div>
        <div class="rb">Quản lý hệ thống</div>
    </div>
    <div class="su">
        <div class="av">${fn:substring(tk.tenDangNhap, 0, 1)}</div>
        <div>
            <div class="nm">${tk.tenDangNhap}</div>
            <div class="sb">${tk.vaiTro}</div>
        </div>
    </div>
    <nav class="snav">
        <div class="ns">Tổng quan</div>
        <a class="ni active" onclick="showPanel('dashboard',this)"><svg viewBox="0 0 24 24"><rect x="3" y="3" width="7" height="7"/><rect x="14" y="3" width="7" height="7"/><rect x="3" y="14" width="7" height="7"/><rect x="14" y="14" width="7" height="7"/></svg>Dashboard</a>

        <div class="ns">Nhân sự</div>
        <a class="ni" onclick="showPanel('nhanvien',this)"><svg viewBox="0 0 24 24"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75"/></svg>Nhân viên</a>
        <a class="ni" onclick="showPanel('phongban',this)"><svg viewBox="0 0 24 24"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/><polyline points="9 22 9 12 15 12 15 22"/></svg>Phòng ban</a>
        <a class="ni" onclick="showPanel('chucvu',this)"><svg viewBox="0 0 24 24"><circle cx="12" cy="8" r="6"/><path d="M15.477 12.89L17 22l-5-3-5 3 1.523-9.11"/></svg>Chức vụ</a>

        <div class="ns">Chấm công &amp; Nghỉ phép</div>
        <a class="ni" onclick="showPanel('chamcong',this)"><svg viewBox="0 0 24 24"><rect x="3" y="4" width="18" height="18" rx="2"/><path d="M16 2v4M8 2v4M3 10h18"/></svg>Quản lý chấm công</a>
        <a class="ni" onclick="showPanel('nghiphep',this)"><svg viewBox="0 0 24 24"><path d="M9 11l3 3L22 4"/><path d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11"/></svg>Duyệt nghỉ phép</a>

        <div class="ns">Tài chính</div>
        <a class="ni" onclick="showPanel('hopdong',this)"><svg viewBox="0 0 24 24"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><path d="M14 2v6h6"/></svg>Hợp đồng</a>
        <a class="ni" onclick="showPanel('bangluong',this)"><svg viewBox="0 0 24 24"><path d="M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/></svg>Bảng lương</a>

        <div class="ns">Đánh giá &amp; Hệ thống</div>
        <a class="ni" onclick="showPanel('danhgia',this)"><svg viewBox="0 0 24 24"><polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/></svg>Đánh giá</a>
        <a class="ni" onclick="showPanel('taikhoan',this)"><svg viewBox="0 0 24 24"><rect x="3" y="11" width="18" height="11" rx="2"/><path d="M7 11V7a5 5 0 0 1 10 0v4"/></svg>Tài khoản</a>
        <a class="ni" onclick="showPanel('thongbao',this)"><svg viewBox="0 0 24 24"><path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/><path d="M13.73 21a2 2 0 0 1-3.46 0"/></svg>Thông báo</a>
    </nav>
    <div class="sf">
        <a href="${pageContext.request.contextPath}/taikhoan?action=logout" class="lb">
            <svg viewBox="0 0 24 24"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/><polyline points="16 17 21 12 16 7"/><line x1="21" y1="12" x2="9" y2="12"/></svg>Đăng xuất
        </a>
    </div>
</aside>

<!-- MAIN -->
<div class="main">
    <div class="tb">
        <span class="pt" id="topbarTitle">Dashboard</span>
        <div class="tr">
            <span class="di" id="currentDate"></span>
            <div class="nb">
                <svg viewBox="0 0 24 24"><path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/><path d="M13.73 21a2 2 0 0 1-3.46 0"/></svg>
                <span class="nd"></span>
            </div>
        </div>
    </div>

    <div class="cnt">

        <!-- ═══ DASHBOARD ═══ -->
        <div class="panel active" id="panel-dashboard">
            <div class="wb">
                <div>
                    <h2>Xin chào, ${tk.tenDangNhap}! 👋</h2>
                    <p>Bảng điều khiển quản lý nhân sự — Tổng quan toàn công ty.</p>
                </div>
                <div class="bi"><svg viewBox="0 0 24 24"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75"/></svg></div>
            </div>
            <div class="sg">
                <div class="sc"><div class="sct"><div class="si blue"><svg viewBox="0 0 24 24"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/></svg></div></div><div class="val">${not empty tongNhanVien ? tongNhanVien : '--'}</div><div class="lbl">Tổng nhân viên</div></div>
                <div class="sc"><div class="sct"><div class="si green"><svg viewBox="0 0 24 24"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/><polyline points="22 4 12 14.01 9 11.01"/></svg></div></div><div class="val">${not empty nvDangLam ? nvDangLam : '--'}</div><div class="lbl">Đang làm việc</div></div>
                <div class="sc"><div class="sct"><div class="si orange"><svg viewBox="0 0 24 24"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/></svg></div></div><div class="val">${not empty donChoDuyet ? donChoDuyet : '--'}</div><div class="lbl">Đơn nghỉ phép chờ</div></div>
                <div class="sc"><div class="sct"><div class="si purple"><svg viewBox="0 0 24 24"><path d="M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/></svg></div></div><div class="val">${not empty tongQuyLuong ? tongQuyLuong : '--'}</div><div class="lbl">Tổng quỹ lương</div></div>
                <div class="sc"><div class="sct"><div class="si red"><svg viewBox="0 0 24 24"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/></svg></div></div><div class="val">${not empty tongPhongBan ? tongPhongBan : '--'}</div><div class="lbl">Phòng ban</div></div>
            </div>
            <div class="g2">
                <div class="box">
                    <div class="bh"><h3><svg viewBox="0 0 24 24"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/></svg>Nhân viên theo phòng ban</h3><a class="va" onclick="showPanel('phongban',null)">Chi tiết</a></div>
                    <div class="bb">
                        <c:choose>
                            <c:when test="${not empty listPhongBan}">
                                <c:forEach var="pb" items="${listPhongBan}">
                                    <div class="di2"><span class="dn">${pb.tenPhongBan}</span><span class="badge bb2">${pb.soLuong} NV</span></div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise><div class="es"><svg viewBox="0 0 24 24"><path d="M3 9l9-7 9 7"/></svg><p>Chưa có dữ liệu</p></div></c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="box">
                    <div class="bh"><h3><svg viewBox="0 0 24 24"><circle cx="12" cy="12" r="10"/><polyline points="12 6 12 12 16 14"/></svg>Đơn nghỉ phép chờ duyệt</h3><a class="va" onclick="showPanel('nghiphep',null)">Xem tất cả</a></div>
                    <table class="dt">
                        <thead><tr><th>Nhân viên</th><th>Loại</th><th>Số ngày</th><th>Thao tác</th></tr></thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty listNghiPhepChoDuyet}">
                                    <c:forEach var="np" items="${listNghiPhepChoDuyet}" end="4">
                                        <tr>
                                            <td>${not empty np.hoTen ? np.hoTen : 'NV#'.concat(np.nhanVienId)}</td>
                                            <td>${np.loaiPhep}</td>
                                            <td>${np.soNgay}</td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/nghiphep?action=duyet&id=${np.nghiPhepId}" class="btn bs bsm">Duyệt</a>
                                                <a href="${pageContext.request.contextPath}/nghiphep?action=tu-choi&id=${np.nghiPhepId}" class="btn bd bsm">Từ chối</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise><tr><td colspan="4"><div class="es">Không có đơn chờ duyệt</div></td></tr></c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- ═══ NHÂN VIÊN ═══ -->
        <div class="panel" id="panel-nhanvien">
            <div class="box">
                <div class="bh">
                    <h3><svg viewBox="0 0 24 24"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/></svg>Danh sách nhân viên</h3>
                    <div class="ac">
                        <a href="${pageContext.request.contextPath}/nhanvien?action=them" class="btn bp2 bsm"><svg viewBox="0 0 24 24"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>Thêm mới</a>
                        <a href="${pageContext.request.contextPath}/nhanvien?action=xuatexcel" class="btn bo2 bsm"><svg viewBox="0 0 24 24"><path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"/><polyline points="7 10 12 15 17 10"/><line x1="12" y1="15" x2="12" y2="3"/></svg>Xuất Excel</a>
                    </div>
                </div>
                <!-- SEARCH NHÂN VIÊN -->
                <div class="search-bar">
                    <input type="text" id="s-nv" placeholder="🔍 Tìm tên, mã NV, email, SĐT..." oninput="filterNV()"/>
                    <select id="sel-pb-nv" onchange="filterNV()">
                        <option value="">-- Tất cả phòng ban --</option>
                        <c:forEach var="pb" items="${listPhongBan}">
                            <option value="${pb.tenPhongBan}">${pb.tenPhongBan}</option>
                        </c:forEach>
                    </select>
                    <select id="sel-tt-nv" onchange="filterNV()">
                        <option value="">-- Trạng thái --</option>
                        <option value="Dang lam viec">Đang làm việc</option>
                        <option value="Nghi viec">Nghỉ việc</option>
                    </select>
                    <span class="search-count" id="count-nv"></span>
                </div>
                <table class="dt">
                    <thead><tr><th>#</th><th>Mã NV</th><th>Họ tên</th><th>Email</th><th>SĐT</th><th>Phòng ban</th><th>Chức vụ</th><tr>
                                                                                                                                          <th>#</th><th>Mã NV</th><th>Họ tên</th><th>Email</th><th>SĐT</th>
                                                                                                                                          <th>Phòng ban</th><th>Chức vụ</th><th>STK</th><th>Ngân hàng</th>
                                                                                                                                          <th>Trạng thái</th><th>Thao tác</th>
                                                                                                                                      </tr><th>Trạng thái</th><th>Thao tác</th></tr></thead>
                    <tbody id="t-nv">
                        <c:choose>
                            <c:when test="${not empty listNhanVien}">
                                <c:forEach var="nv" items="${listNhanVien}" varStatus="st">
                                    <tr>
                                        <td>${st.index + 1}</td>
                                        <td><strong>${nv.maNhanVien}</strong></td>
                                        <td>${nv.hoTen}</td>
                                        <td>${not empty nv.email ? nv.email : '--'}</td>
                                        <td>${not empty nv.dienThoai ? nv.dienThoai : '--'}</td>
                                        <td>${not empty nv.tenPhongBan ? nv.tenPhongBan : '--'}</td>
                                        <td>${not empty nv.tenChucVu ? nv.tenChucVu : '--'}</td>
                                        <td>${not empty nv.soTaiKhoan ? nv.soTaiKhoan : '--'}</td>
                                        <td>${not empty nv.nganHang ? nv.nganHang : '--'}</td>
                                        <td><span class="badge ${nv.trangThai == 'Dang lam viec' ? 'bg' : nv.trangThai == 'Nghi viec' ? 'br' : 'bo'}">${nv.trangThai}</span></td>
                                        <td style="white-space:nowrap">
                                            <a href="${pageContext.request.contextPath}/nhanvien?action=xemchitiet&id=${nv.nhanVienId}" class="btn bo2 bsm">Xem</a>

                                            <a href="${pageContext.request.contextPath}/nhanvien?action=xoa&id=${nv.nhanVienId}" class="btn bd bsm" onclick="return confirm('Xác nhận xóa?')">Xóa</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
<c:otherwise><tr><td colspan="11"><div class="es">Không có dữ liệu nhân viên</div></td></tr></c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
                <div class="no-results" id="no-nv">😔 Không tìm thấy nhân viên phù hợp</div>
            </div>
        </div>

        <!-- ═══ PHÒNG BAN ═══ -->
        <div class="panel" id="panel-phongban">
            <div class="g2">
                <div class="box">
                    <!-- SEARCH PHÒNG BAN -->
                    <div class="search-bar">
                        <input type="text" id="s-pb" placeholder="🔍 Tìm tên, mã phòng ban..." oninput="filterTable('t-pb',this.value,[0,1,2],'count-pb','no-pb')"/>
                        <span class="search-count" id="count-pb"></span>
                    </div>
                    <table class="dt">
                        <thead><tr><th>Mã PB</th><th>Tên phòng ban</th><th>Trưởng phòng</th><th>Số lượng</th><th>T.Thái</th><th colspan="2">Thao tác</th></tr></thead>
                        <tbody id="t-pb">
                            <c:choose>
                                <c:when test="${not empty listPhongBan}">
                                    <c:forEach var="pb" items="${listPhongBan}">
                                        <tr>
                                            <td><strong>${pb.maPhongBan}</strong></td>
                                            <td>${pb.tenPhongBan}</td>
                                            <td>
                                                <c:set var="tenTruong" value="--"/>
                                                <c:forEach var="nv" items="${listNhanVien}">
                                                    <c:if test="${pb.truongPhongId == nv.nhanVienId}">
                                                        <c:set var="tenTruong" value="${nv.hoTen}"/>
                                                    </c:if>
                                                </c:forEach>
                                                ${tenTruong}
                                            </td>
                                            <td>${not empty pb.soLuong ? pb.soLuong : '--'}</td>
                                            <td><span class="badge ${pb.trangThai == 1 ? 'bg' : 'br'}">${pb.trangThai == 1 ? 'Hoạt động' : 'Dừng'}</span></td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/phongban?action=sua&id=${pb.phongBanId}" class="btn bp2 bsm">Sửa</a>
                                                </td>
                                               <td> <c:choose>
                                                    <c:when test="${pb.trangThai == 1}">
                                                        <a href="${pageContext.request.contextPath}/phongban?action=xoa&id=${pb.phongBanId}"
                                                           class="btn bd bsm" onclick="return confirm('Dừng hoạt động phòng ban này?')">Dừng</a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="${pageContext.request.contextPath}/phongban?action=kichhoat&id=${pb.phongBanId}"
                                                           class="btn bs bsm">Kích hoạt</a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise><tr><td colspan="6"><div class="es">Chưa có phòng ban</div></td></tr></c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                    <div class="no-results" id="no-pb">😔 Không tìm thấy phòng ban phù hợp</div>
                </div>
                <div class="box">
                    <div class="bh"><h3><svg viewBox="0 0 24 24"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>Thêm phòng ban mới</h3></div>
                    <div class="bb fm">
                        <form action="${pageContext.request.contextPath}/phongban" method="post">
                            <input type="hidden" name="action" value="them"/>
                            <div class="fg">
                                <div>
                                    <label>Mã phòng ban</label>
                                    <input type="text" id="maPhongBan" name="maPhongBan" readonly/>
                                </div>
                                <div>
                                    <label>Tên phòng ban <span style="color:var(--danger)">*</span></label>
                                    <input type="text" name="tenPhongBan" placeholder="VD: Phòng Kỹ thuật" required/>
                                </div>
                                <div>
                                    <label>Số lượng nhân viên</label>
                                    <input type="number" name="soLuong" placeholder="0" min="0" value="0"/>
                                </div>
                                <div>
                                    <label>Trưởng phòng</label>
                                    <select name="truongPhongId">
                                        <option value="">-- Chưa chỉ định --</option>
                                        <c:forEach var="nv" items="${listNhanVienKhongPhaiTruongPhong}">
                                            <option value="${nv.nhanVienId}">${nv.hoTen}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div>
                                    <label>Trạng thái <span style="color:var(--danger)">*</span></label>
                                    <select name="trangThai" required>
                                        <option value="1">Hoạt động</option>
                                        <option value="0">Ngưng hoạt động</option>
                                    </select>
                                </div>
                                <div class="full">
                                    <label>Mô tả</label>
                                    <textarea name="moTa" placeholder="Mô tả chức năng, nhiệm vụ của phòng ban..."></textarea>
                                </div>
                            </div>
                            <div class="fa">
                                <button type="submit" class="btn bp2">
                                    <svg viewBox="0 0 24 24" style="width:13px;height:13px;stroke:currentColor;fill:none;stroke-width:2">
                                        <line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/>
                                    </svg>
                                    Thêm phòng ban
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- ═══ CHỨC VỤ ═══ -->
        <div class="panel" id="panel-chucvu">
            <div class="g2">
                <div class="box">
                    <!-- SEARCH CHỨC VỤ -->
                    <div class="search-bar">
                        <input type="text" id="s-cv" placeholder="🔍 Tìm tên, mã chức vụ..." oninput="filterTable('t-cv',this.value,[0,1],'count-cv','no-cv')"/>
                        <span class="search-count" id="count-cv"></span>
                    </div>
                    <table class="dt">
                        <thead><tr><th>Mã CV</th><th>Tên chức vụ</th><th>Cấp bậc</th><th>Lương min</th><th>T.Thái</th><th>Thao tác</th></tr></thead>
                        <tbody id="t-cv">
                            <c:choose>
                                <c:when test="${not empty listChucVu}">
                                    <c:forEach var="cv" items="${listChucVu}">
                                        <tr>
                                            <td><strong>${cv.maChucVu}</strong></td>
                                            <td>${cv.tenChucVu}</td>
                                            <td><span class="badge bb2">Cấp ${cv.capBac}</span></td>
                                            <td><c:choose><c:when test="${not empty cv.luongCoBan}"><fmt:formatNumber value="${cv.luongCoBan}" pattern="#,###"/> đ</c:when><c:otherwise>--</c:otherwise></c:choose></td>
                                            <td><span class="badge ${cv.trangThai == 1 ? 'bg' : 'br'}">${cv.trangThai == 1 ? 'Hoạt động' : 'Dừng'}</span></td>
                                            <td style="white-space:nowrap">
                                                <a href="${pageContext.request.contextPath}/chucvu?action=sua&id=${cv.chucVuId}" class="btn bp2 bsm">Sửa</a>
                                                <c:choose>
                                                    <c:when test="${cv.trangThai == 1}">
                                                        <a href="${pageContext.request.contextPath}/chucvu?action=xoa&id=${cv.chucVuId}"
                                                           class="btn bd bsm" onclick="return confirm('Dừng chức vụ này?')">Dừng</a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="${pageContext.request.contextPath}/chucvu?action=kichhoat&id=${cv.chucVuId}"
                                                           class="btn bs bsm">Kích hoạt</a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise><tr><td colspan="6"><div class="es">Chưa có chức vụ</div></td></tr></c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                    <div class="no-results" id="no-cv">😔 Không tìm thấy chức vụ phù hợp</div>
                </div>
                <div class="box">
                    <div class="bh"><h3><svg viewBox="0 0 24 24"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>Thêm chức vụ mới</h3></div>
                    <div class="bb fm">
                        <form action="${pageContext.request.contextPath}/chucvu" method="post">
                            <input type="hidden" name="action" value="them"/>
                            <div class="fg">
<div>
    <label>Mã chức vụ</label>
    <input type="text" id="maChucVu" name="maChucVu" readonly/>
</div>                                <div><label>Tên chức vụ</label><input type="text" name="tenChucVu" placeholder="Tên chức vụ" required/></div>
                                <div><label>Cấp bậc</label><input type="number" name="capBac" min="1" placeholder="1"/></div>
                                <div><label>Lương cơ bản (min)</label><input type="number" name="luongCoBanMin" placeholder="5000000"/></div>
                                <div class="full"><label>Mô tả</label><textarea name="moTa" placeholder="Mô tả..."></textarea></div>
                            </div>
                            <div class="fa"><button type="submit" class="btn bp2">Thêm chức vụ</button></div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- ═══ CHẤM CÔNG ═══ -->
        <div class="panel" id="panel-chamcong">
            <div class="box">
                <div class="bh">
                    <h3><svg viewBox="0 0 24 24"><rect x="3" y="4" width="18" height="18" rx="2"/><path d="M16 2v4M8 2v4M3 10h18"/></svg>Bảng chấm công toàn công ty</h3>
                    <div class="ac">

                    </div>
                </div>
                <!-- SEARCH CHẤM CÔNG -->
                <div class="search-bar">
                    <input type="text" placeholder="🔍 Tìm tên, mã nhân viên..." oninput="filterCC()"/>
                    <input type="date" id="sel-ngay-cc" onchange="filterCC()" style="padding:7px 10px;border:1.5px solid var(--border);border-radius:7px;font-size:.79rem;outline:none;background:#fff;font-family:'Inter',sans-serif;color:var(--text)"/>
                    <select id="sel-thang-cc" onchange="filterCC()">
                        <option value="">-- Tháng --</option>
                        <c:forEach begin="1" end="12" var="m">
                            <option value="${m}">Tháng ${m}</option>
                        </c:forEach>
                    </select>
                    <span class="search-count" id="count-cc"></span>
                </div>
                <table class="dt">
                    <thead><tr><th>Mã NV</th><th>Họ tên</th><th>Ngày</th><th>Giờ vào</th><th>Giờ ra</th><th>Số giờ</th><th>Làm thêm</th><th>Trạng thái</th></tr></thead>
                    <tbody id="t-cc">
                        <c:choose>
                            <c:when test="${not empty listChamCongAll}">
                                <c:forEach var="cc" items="${listChamCongAll}">
                                    <tr>
                                        <td>
                                            <c:set var="maNV" value="${cc.nhanVienId}"/>
                                            <c:forEach var="nv" items="${listNhanVien}">
                                                <c:if test="${nv.nhanVienId == cc.nhanVienId}">
                                                    <c:set var="maNV" value="${nv.maNhanVien}"/>
                                                </c:if>
                                            </c:forEach>
                                            <strong>${maNV}</strong>
                                        </td>
                                        <td>
                                            <c:set var="tenNV" value="--"/>
                                            <c:forEach var="nv" items="${listNhanVien}">
                                                <c:if test="${nv.nhanVienId == cc.nhanVienId}">
                                                    <c:set var="tenNV" value="${nv.hoTen}"/>
                                                </c:if>
                                            </c:forEach>
                                            ${tenNV}
                                        </td>
                                        <td>${cc.ngayChamCong}</td>
                                        <td>${not empty cc.gioVao ? cc.gioVao : '--'}</td>
                                        <td>${not empty cc.gioRa ? cc.gioRa : '--'}</td>
                                        <td>${cc.soGioLamViec}</td>
                                        <td>${cc.gioLamThem}</td>
<td>
    <span class="badge ${cc.trangThai == 'Di lam' ? 'bg' : cc.trangThai == 'Nghi phep' ? 'bb2' : 'br'}">
        ${cc.trangThai}
    </span>
</td>                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr><td colspan="8"><div class="es">Không có dữ liệu chấm công</div></td></tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
                <div class="no-results" id="no-cc">😔 Không tìm thấy kết quả phù hợp</div>
            </div>
        </div>

        <!-- ═══ DUYỆT NGHỈ PHÉP ═══ -->
        <div class="panel" id="panel-nghiphep">
            <div class="box">
                <div class="bh"><h3><svg viewBox="0 0 24 24"><path d="M9 11l3 3L22 4"/></svg>Quản lý đơn nghỉ phép</h3></div>
                <div class="bb">
                    <!-- SEARCH NGHỈ PHÉP -->
                    <div class="search-bar" style="margin-bottom:12px;border-radius:8px;border:1px solid var(--border)">
                        <input type="text" id="s-np" placeholder="🔍 Tìm tên nhân viên..." oninput="filterNP()"/>
                       <select id="sel-loai-np" onchange="filterNP()">
                           <option value="">-- Loại phép --</option>
                           <option value="Phep nam">Phép năm</option>
                           <option value="Phep thang">Phép tháng</option>
                           <option value="Phep khac">Phép khác</option>
                       </select>
                        <span class="search-count" id="count-np"></span>
                    </div>

                    <div class="tabb">
                        <button class="tbt active" onclick="switchTab('t-cho',this)">Chờ duyệt</button>
                        <button class="tbt" onclick="switchTab('t-duyet',this)">Đã duyệt</button>
                        <button class="tbt" onclick="switchTab('t-tuchoi',this)">Từ chối</button>
                    </div>
                    <div id="t-cho" class="tc active">
                        <table class="dt">
                            <thead><tr><th>Nhân viên</th><th>Loại phép</th><th>Từ ngày</th><th>Đến ngày</th><th>Số ngày</th><th>Lý do</th><th>Thao tác</th></tr></thead>
                            <tbody id="t-np-cho">
                                <c:choose>
                                    <c:when test="${not empty listNghiPhepChoDuyet}">
                                        <c:forEach var="np" items="${listNghiPhepChoDuyet}">
                                            <tr>
                                                <td><strong>${not empty np.hoTen ? np.hoTen : 'NV#'.concat(np.nhanVienId)}</strong></td>
                                                <td>${np.loaiPhep}</td>
                                                <td>${np.ngayBatDau}</td>
                                                <td>${np.ngayKetThuc}</td>
                                                <td>${np.soNgay} ngày</td>
                                                <td style="max-width:140px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap">${np.lyDo}</td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/nghiphep?action=duyet&id=${np.nghiPhepId}" class="btn bs bsm">✓ Duyệt</a>
                                                    <a href="${pageContext.request.contextPath}/nghiphep?action=tuchoi&id=${np.nghiPhepId}" class="btn bd bsm">✗ Từ chối</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise><tr><td colspan="7"><div class="es">Không có đơn chờ duyệt</div></td></tr></c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                        <div class="no-results" id="no-np">😔 Không tìm thấy kết quả phù hợp</div>
                    </div>
                    <div id="t-duyet" class="tc">
                        <table class="dt">
                            <thead><tr><th>Nhân viên</th><th>Loại phép</th><th>Từ ngày</th><th>Đến ngày</th><th>Số ngày</th><th>Người duyệt</th></tr></thead>
                            <tbody id="t-np-duyet">
                                <c:choose>
                                    <c:when test="${not empty listNghiPhepDaDuyet}">
                                        <c:forEach var="np" items="${listNghiPhepDaDuyet}">
                                            <tr>
                                                <td>${not empty np.hoTen ? np.hoTen : '--'}</td>
                                                <td>${np.loaiPhep}</td>
                                                <td>${np.ngayBatDau}</td>
                                                <td>${np.ngayKetThuc}</td>
                                                <td>${np.soNgay}</td>
                                                <td>${not empty np.tenNguoiDuyet ? np.tenNguoiDuyet : '--'}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise><tr><td colspan="6"><div class="es">Chưa có đơn được duyệt</div></td></tr></c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>
                    <div id="t-tuchoi" class="tc">
                        <table class="dt">
                            <thead><tr><th>Nhân viên</th><th>Loại phép</th><th>Từ ngày</th><th>Đến ngày</th><th>Số ngày</th></tr></thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty listNghiPhepTuChoi}">
                                        <c:forEach var="np" items="${listNghiPhepTuChoi}">
                                            <tr>
                                                <td>${not empty np.hoTen ? np.hoTen : '--'}</td>
                                                <td>${np.loaiPhep}</td>
                                                <td>${np.ngayBatDau}</td>
                                                <td>${np.ngayKetThuc}</td>
                                                <td>${np.soNgay}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise><tr><td colspan="5"><div class="es">Không có đơn từ chối</div></td></tr></c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <!-- ═══ HỢP ĐỒNG ═══ -->
        <div class="panel" id="panel-hopdong">
            <div class="box">
                <table class="dt">
                    <thead><tr><th>Số HĐ</th><th>Nhân viên</th><th>Loại HĐ</th><th>Ngày BĐ</th><th>Ngày KT</th><th>Lương CB</th><th>Phụ cấp</th><th>Trạng thái</th><th>Thao tác</th></tr></thead>
                    <tbody>
                    <c:choose>
                    <c:when test="${not empty listHopDong}">
                    <c:forEach var="hd" items="${listHopDong}">
                    <tr>

                    <td><strong>${hd.soHopDong}</strong></td>

                    <td>
                    <c:set var="tenNV" value="" />
                    <c:forEach var="nv" items="${listNhanVien}">
                    <c:if test="${hd.nhanVienId == nv.nhanVienId}">
                    <c:set var="tenNV" value="${nv.hoTen}" />
                    </c:if>
                    </c:forEach>

                    <c:choose>
                    <c:when test="${not empty tenNV}">
                    ${tenNV}
                    </c:when>
                    <c:otherwise>
                    NV#${hd.nhanVienId}
                    </c:otherwise>
                    </c:choose>
                    </td>

                    <td>${hd.loaiHopDong}</td>
                    <td>${hd.ngayBatDau}</td>
                    <td>${not empty hd.ngayKetThuc ? hd.ngayKetThuc : 'Vô thời hạn'}</td>

                    <td><fmt:formatNumber value="${hd.luongCoBan}" pattern="#,###"/> đ</td>
                    <td><fmt:formatNumber value="${hd.phuCap}" pattern="#,###"/> đ</td>

                    <td>
                    <span class="badge ${hd.trangThai == 'Hieu luc' ? 'bg' : hd.trangThai == 'Het han' ? 'bo' : 'br'}">
                    ${hd.trangThai}
                    </span>
                    </td>

                    <td style="white-space:nowrap">
                        <a href="${pageContext.request.contextPath}/hopdong?action=sua&id=${hd.hopDongId}" class="btn bp2 bsm">Sửa</a>
                        <c:choose>
                            <c:when test="${hd.trangThai == 'Hieu luc'}">
                                <a href="${pageContext.request.contextPath}/hopdong?action=huy&id=${hd.hopDongId}"
                                   class="btn bd bsm" onclick="return confirm('Xác nhận hủy hợp đồng này?')">Hủy HĐ</a>
                            </c:when>
                            <c:when test="${hd.trangThai == 'Da huy' || hd.trangThai == 'Het han'}">
                                <a href="${pageContext.request.contextPath}/hopdong?action=kichhoat&id=${hd.hopDongId}"
                                   class="btn bs bsm">Kích hoạt</a>
                            </c:when>
                        </c:choose>
                    </td>

                    </tr>
                    </c:forEach>
                    </c:when>

                    <c:otherwise>
                    <tr>
                    <td colspan="9">
                    <div class="es">Chưa có hợp đồng</div>
                    </td>
                    </tr>
                    </c:otherwise>

                    </c:choose>
                    </tbody>
                </table>
            </div>
        </div>

       <!-- ═══ BẢNG LƯƠNG ═══ -->
       <div class="panel" id="panel-bangluong">
           <div class="box">
               <div class="bh">
                   <h3><svg viewBox="0 0 24 24"><path d="M12 2v20M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/></svg>Quản lý bảng lương</h3>
                   <div class="ac">
                       <a href="${pageContext.request.contextPath}/bangluong?action=tao" class="btn bs bsm">Tính lương tháng này</a>
                       <a href="${pageContext.request.contextPath}/bangluong?action=xuat-excel" class="btn bo2 bsm">Xuất Excel</a>
                       <a href="${pageContext.request.contextPath}/bangluong?action=thanh-toan-tat-ca-tien-mat"
                          class="btn bw bsm"
                          onclick="return confirm('Xác nhận thanh toán tiền mặt cho TẤT CẢ bảng lương đang chờ duyệt?')">
                           💵 TT tiền mặt tất cả
                       </a>
                   </div>
               </div>
               <!-- SEARCH BẢNG LƯƠNG -->
               <div class="search-bar">
                   <input type="text" id="s-bl" placeholder="🔍 Tìm tên nhân viên..." oninput="filterBL()"/>
                   <select id="sel-tt-bl" onchange="filterBL()">
                       <option value="">-- Trạng thái --</option>
                       <option value="Da thanh toan tien mat">Đã TT tiền mặt</option>
                       <option value="Da thanh toan chuyen khoan">Đã TT chuyển khoản</option>
                       <option value="Da duyet">Đã duyệt</option>
                       <option value="Cho duyet">Chờ duyệt</option>
                   </select>
                   <select id="sel-thang-bl" onchange="filterBL()">
                       <option value="">-- Tháng --</option>
                       <c:forEach begin="1" end="12" var="m">
                           <option value="T${m}">Tháng ${m}</option>
                       </c:forEach>
                   </select>
                   <span class="search-count" id="count-bl"></span>
               </div>
               <table class="dt">
                   <thead><tr><th>Nhân viên</th><th>Tháng</th><th>Năm</th><th>Ngày làm</th><th>Lương CB</th><th>Thưởng</th><th>Khấu trừ</th><th>Thực lãnh</th><th>Trạng thái</th><th>Thao tác</th><th>Thanh toán</th></tr></thead>
                   <tbody id="t-bl">
                       <c:choose>
                           <c:when test="${not empty listBangLuongAll}">
                               <c:forEach var="b" items="${listBangLuongAll}">
                                   <tr>
                                       <td>
                                           <c:set var="tenNV" value="NV#${b.nhanVienId}"/>
                                           <c:set var="soTK" value=""/>
                                           <c:set var="nganHang" value=""/>
                                           <c:forEach var="nv" items="${listNhanVien}">
                                               <c:if test="${nv.nhanVienId == b.nhanVienId}">
                                                   <c:set var="tenNV" value="${nv.hoTen}"/>
                                                   <c:set var="soTK" value="${nv.soTaiKhoan}"/>
                                                   <c:set var="nganHang" value="${nv.nganHang}"/>
                                               </c:if>
                                           </c:forEach>
                                           ${tenNV}
                                       </td>
                                       <td>T${b.thang}</td>
                                       <td>${b.nam}</td>
                                       <td>${b.soNgayThucTe}/${b.soNgayLamViec}</td>
                                       <td><fmt:formatNumber value="${b.luongCoBan}" pattern="#,###"/></td>
                                       <td><fmt:formatNumber value="${b.thuong}" pattern="#,###"/></td>
                                       <td><fmt:formatNumber value="${b.tongKhauTru}" pattern="#,###"/></td>
                                       <td><strong><fmt:formatNumber value="${b.luongThucLanh}" pattern="#,###"/></strong></td>
                                       <td>
                                           <span class="badge ${b.trangThai == 'Da thanh toan tien mat' || b.trangThai == 'Da thanh toan chuyen khoan' ? 'bg' : b.trangThai == 'Da duyet' ? 'bb2' : 'bo'}">
                                               ${b.trangThai}
                                           </span>
                                       </td>
                                       <td style="white-space:nowrap">
                                           <a href="${pageContext.request.contextPath}/bangluong?action=xem&id=${b.bangLuongId}" class="btn bo2 bsm">Xem</a>


                                       </td>
                                       <td style="white-space:nowrap">
                                       <c:if test="${b.trangThai == 'Da duyet' || b.trangThai == 'Cho duyet'}">
                                                                                      <%-- Nút tiền mặt --%>
                                                                                      <a href="${pageContext.request.contextPath}/bangluong?action=thanh-toan-tien-mat&id=${b.bangLuongId}"
                                                                                         class="btn bw bsm"
                                                                                         onclick="return confirm('Xác nhận thanh toán tiền mặt cho ${tenNV}?')">
                                                                                          💵 Tiền mặt
                                                                                      </a>

                                                                                      <%-- Nút QR chuyển khoản --%>
                                                                                      <button class="btn bs bsm"
                                                                                              onclick="moQR('${b.bangLuongId}','${tenNV}','${soTK}','${nganHang}','${b.luongThucLanh}')">
                                                                                          🏦 QR
                                                                                      </button>
                                                                                  </c:if>                                       </td>

                                   </tr>
                               </c:forEach>
                           </c:when>
                           <c:otherwise>
                               <tr><td colspan="10"><div class="es">Chưa có dữ liệu bảng lương</div></td></tr>
                           </c:otherwise>
                       </c:choose>
                   </tbody>
               </table>
               <div class="no-results" id="no-bl">😔 Không tìm thấy kết quả phù hợp</div>
           </div>
       </div>

       <!-- MODAL QR THANH TOÁN -->
       <div id="modal-qr" style="display:none;position:fixed;inset:0;background:rgba(0,0,0,.5);z-index:300;align-items:center;justify-content:center;">
           <div style="background:#fff;border-radius:14px;padding:28px;max-width:420px;width:90%;text-align:center;box-shadow:0 8px 32px rgba(0,0,0,.2)">
               <h3 style="font-size:.95rem;font-weight:700;color:#1e3a5f;margin-bottom:4px" id="qr-ten-nv"></h3>
               <p style="font-size:.78rem;color:#7a8899;margin-bottom:16px" id="qr-info"></p>
               <img id="qr-img" src="" alt="VietQR" style="width:220px;height:220px;border-radius:10px;border:1px solid #dce3ec;margin-bottom:16px"/>
               <p style="font-size:.72rem;color:#7a8899;margin-bottom:18px">Quét mã QR để chuyển khoản, sau đó xác nhận bên dưới</p>
               <div style="display:flex;gap:10px;justify-content:center">
                   <button onclick="dongQR()" style="padding:8px 20px;border-radius:8px;border:1.5px solid #dce3ec;background:transparent;color:#7a8899;font-size:.8rem;cursor:pointer">Đóng</button>
                   <button onclick="xacNhanQR()" style="padding:8px 20px;border-radius:8px;border:none;background:#27ae60;color:#fff;font-size:.8rem;font-weight:600;cursor:pointer">✓ Xác nhận đã thanh toán</button>
               </div>
           </div>
       </div>

        <!-- ═══ ĐÁNH GIÁ ═══ -->
        <div class="panel" id="panel-danhgia">
            <div class="box">
                <div class="bh"><h3><svg viewBox="0 0 24 24"><polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/></svg>Quản lý đánh giá nhân viên</h3><a href="${pageContext.request.contextPath}/danhgia?action=them" class="btn bp2 bsm">+ Thêm đánh giá</a></div>
                <!-- SEARCH ĐÁNH GIÁ -->
                <div class="search-bar">
                    <input type="text" placeholder="🔍 Tìm tên nhân viên..." oninput="filterTable('t-dg',this.value,[0],'count-dg','no-dg')"/>
                    <select onchange="filterBySelectDG(4,this.value)">
                        <option value="">-- Xếp loại --</option>
                        <option value="Xuat sac">Xuất sắc</option>
                        <option value="Gioi">Giỏi</option>
                        <option value="Kha">Khá</option>
                    </select>
                    <span class="search-count" id="count-dg"></span>
                </div>
                <table class="dt">
                    <thead><tr><th>Nhân viên</th><th>Tháng</th><th>Năm</th><th>Tổng điểm</th><th>Xếp loại</th><th>Nhận xét</th><th>Người đánh giá</th><th>Ngày</th><th>Thao tác</th></tr></thead>
                    <tbody id="t-dg">
                        <c:choose>
                            <c:when test="${not empty listDanhGiaAll}">
                                <c:forEach var="dg" items="${listDanhGiaAll}">
                                    <tr>
                                        <td>${not empty dg.hoTen ? dg.hoTen : 'NV#'.concat(dg.nhanVienId)}</td>
                                        <td>T${dg.thang}</td>
                                        <td>${dg.nam}</td>
                                        <td><strong>${dg.tongDiem}</strong></td>
                                        <td><span class="badge ${dg.xepLoai == 'Xuat sac' ? 'bg' : dg.xepLoai == 'Gioi' ? 'bb2' : dg.xepLoai == 'Kha' ? 'bo' : 'br'}">${not empty dg.xepLoai ? dg.xepLoai : '--'}</span></td>
                                        <td style="max-width:120px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap">${not empty dg.nhanXet ? dg.nhanXet : '--'}</td>
                                        <td>${not empty dg.tenNguoiDanhGia ? dg.tenNguoiDanhGia : '--'}</td>
                                        <td><c:choose><c:when test="${not empty dg.ngayDanhGia}"><fmt:formatDate value="${dg.ngayDanhGia}" pattern="dd/MM/yyyy"/></c:when><c:otherwise>--</c:otherwise></c:choose></td>
                                        <td><a href="${pageContext.request.contextPath}/danhgia?action=sua&id=${dg.danhGiaId}" class="btn bp2 bsm">Sửa</a></td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise><tr><td colspan="9"><div class="es">Chưa có dữ liệu đánh giá</div></td></tr></c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
                <div class="no-results" id="no-dg">😔 Không tìm thấy kết quả phù hợp</div>
            </div>
        </div>

        <!-- ═══ TÀI KHOẢN ═══ -->
        <div class="panel" id="panel-taikhoan">
            <div class="box">
                <div class="bh"><h3><svg viewBox="0 0 24 24"><rect x="3" y="11" width="18" height="11" rx="2"/><path d="M7 11V7a5 5 0 0 1 10 0v4"/></svg>Quản lý tài khoản</h3><a href="${pageContext.request.contextPath}/taikhoan?action=them" class="btn bp2 bsm">+ Thêm tài khoản</a></div>
                <!-- SEARCH TÀI KHOẢN -->
                <div class="search-bar">
                    <input type="text" id="s-tk" placeholder="🔍 Tìm tên đăng nhập..." oninput="filterTable('t-tk',this.value,[1,2],'count-tk','no-tk')"/>
                    <select onchange="filterBySelectGen('t-tk',3,this.value,'count-tk','no-tk')">
                        <option value="">-- Tất cả vai trò --</option>
                        <option value="Quản lý">Quản lý</option>
                        <option value="Nhân viên">Nhân viên</option>
                    </select>
                    <select onchange="filterBySelectGen('t-tk',4,this.value,'count-tk','no-tk')">
                        <option value="">-- Trạng thái --</option>
                        <option value="Hoạt động">Hoạt động</option>
                        <option value="Khóa">Khóa</option>
                    </select>
                    <span class="search-count" id="count-tk"></span>
                </div>
                <table class="dt">
                    <thead><tr><th>#</th><th>Tên đăng nhập</th><th>Mã NV</th><th>Vai trò</th><th>Trạng thái</th><th>Ngày tạo</th><th>Thao tác</th></tr></thead>
                    <tbody id="t-tk">
                        <c:choose>
                            <c:when test="${not empty listTaiKhoan}">
                                <c:forEach var="t" items="${listTaiKhoan}" varStatus="st">
                                    <tr>
                                        <td>${st.index + 1}</td>
                                        <td><strong>${t.tenDangNhap}</strong></td>
                                        <td>${t.nhanVienId}</td>
                                        <td><span class="badge bp">${t.vaiTro}</span></td>
                                        <td><span class="badge ${t.trangThai == 1 ? 'bg' : 'br'}">${t.trangThai == 1 ? 'Hoạt động' : 'Khóa'}</span></td>
                                        <td><c:choose><c:when test="${not empty t.ngayTao}"><fmt:formatDate value="${t.ngayTao}" pattern="dd/MM/yyyy"/></c:when><c:otherwise>--</c:otherwise></c:choose></td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/taikhoan?action=sua&id=${t.taiKhoanId}" class="btn bp2 bsm">Sửa</a>
                                            <a href="${pageContext.request.contextPath}/taikhoan?action=khoa&id=${t.taiKhoanId}" class="btn bw bsm">Khóa</a>
                                            <a href="${pageContext.request.contextPath}/taikhoan?action=xoa&id=${t.taiKhoanId}" class="btn bd bsm" onclick="return confirm('Xóa tài khoản?')">Xóa</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise><tr><td colspan="7"><div class="es">Chưa có tài khoản</div></td></tr></c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
                <div class="no-results" id="no-tk">😔 Không tìm thấy tài khoản phù hợp</div>
            </div>
        </div>

        <!-- ═══ THÔNG BÁO ═══ -->
        <div class="panel" id="panel-thongbao">
            <div class="box">
                <div class="bh"><h3><svg viewBox="0 0 24 24"><path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/><path d="M13.73 21a2 2 0 0 1-3.46 0"/></svg>Tất cả thông báo</h3></div>
                <!-- SEARCH THÔNG BÁO -->
                <div class="search-bar">
                    <input type="text" placeholder="🔍 Tìm tiêu đề, nội dung..." oninput="filterTable('t-tb',this.value,[0,1,2],'count-tb','no-tb')"/>
                    <select onchange="filterBySelectGen('t-tb',5,this.value,'count-tb','no-tb')">
                        <option value="">-- Trạng thái --</option>
                        <option value="Đã đọc">Đã đọc</option>
                        <option value="Chưa đọc">Chưa đọc</option>
                    </select>
                    <span class="search-count" id="count-tb"></span>
                </div>
                <table class="dt">
                    <thead><tr><th>Tiêu đề</th><th>Nội dung</th><th>Người nhận</th><th>Loại</th><th>Ngày</th><th>Trạng thái</th></tr></thead>
                    <tbody id="t-tb">
                        <c:choose>
                            <c:when test="${not empty listThongBaoAll}">
                                <c:forEach var="tb" items="${listThongBaoAll}">
                                    <tr>
                                        <td><strong>${tb.tieuDe}</strong></td>
                                        <td>${tb.noiDung}</td>
                                        <td>${not empty tb.tenNguoiNhan ? tb.tenNguoiNhan : '--'}</td>
                                        <td><span class="badge bb2">${not empty tb.loai ? tb.loai : '--'}</span></td>
                                        <td><c:choose><c:when test="${not empty tb.ngayTao}"><fmt:formatDate value="${tb.ngayTao}" pattern="dd/MM/yyyy"/></c:when><c:otherwise>--</c:otherwise></c:choose></td>
                                        <td><span class="badge ${tb.daDoc == 1 ? 'bg' : 'bo'}">${tb.daDoc == 1 ? 'Đã đọc' : 'Chưa đọc'}</span></td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise><tr><td colspan="6"><div class="es">Chưa có thông báo</div></td></tr></c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
                <div class="no-results" id="no-tb">😔 Không tìm thấy thông báo phù hợp</div>
            </div>
        </div>

    </div><!-- /cnt -->
</div><!-- /main -->

<script>
    // ── CLOCK ──
    function tick() {
        const n = new Date();
        document.getElementById('currentDate').textContent = n.getDate() + '/' + (n.getMonth()+1) + '/' + n.getFullYear();
    }
    tick(); setInterval(tick, 60000);

    // ── PANEL / TAB ──
    const titles = {dashboard:'Dashboard',nhanvien:'Nhân viên',phongban:'Phòng ban',chucvu:'Chức vụ',chamcong:'Quản lý chấm công',nghiphep:'Duyệt nghỉ phép',hopdong:'Hợp đồng',bangluong:'Bảng lương',danhgia:'Đánh giá nhân viên',taikhoan:'Tài khoản',thongbao:'Thông báo'};
    function showPanel(name,el){
        document.querySelectorAll('.panel').forEach(p=>p.classList.remove('active'));
        document.querySelectorAll('.ni').forEach(n=>n.classList.remove('active'));
        const p=document.getElementById('panel-'+name);
        if(p) p.classList.add('active');
        document.getElementById('topbarTitle').textContent=titles[name]||name;
        if(el) el.classList.add('active');
    }
    function switchTab(id,btn){
        const parent=btn.closest('.bb');
        parent.querySelectorAll('.tc').forEach(t=>t.classList.remove('active'));
        parent.querySelectorAll('.tbt').forEach(b=>b.classList.remove('active'));
        document.getElementById(id).classList.add('active');
        btn.classList.add('active');
    }

    // ── SEARCH ENGINE ──
    function updateCount(countId, noId, visible, total, hasFilter) {
        const countEl = document.getElementById(countId);
        const noEl    = document.getElementById(noId);
        if (countEl) {
            if (hasFilter) {
                countEl.textContent = visible + '/' + total + ' kết quả';
                countEl.classList.add('show');
            } else {
                countEl.textContent = '';
                countEl.classList.remove('show');
            }
        }
        if (noEl) noEl.style.display = (hasFilter && visible === 0) ? 'block' : 'none';
    }

    // Generic: filter by keyword on specified cols
    function filterTable(tbodyId, keyword, cols, countId, noId) {
        const tbody = document.getElementById(tbodyId);
        if (!tbody) return;
        const rows = tbody.querySelectorAll('tr');
        const kw   = keyword.toLowerCase().trim();
        let visible = 0, total = 0;
        rows.forEach(row => {
            if (!row.cells.length) return;
            total++;
            const match = !kw || cols.some(c => row.cells[c] && row.cells[c].textContent.toLowerCase().includes(kw));
            row.style.display = match ? '' : 'none';
            if (match) visible++;
        });
        updateCount(countId, noId, visible, total, !!kw);
    }

    // Generic: filter by select on one col
    function filterBySelectGen(tbodyId, colIndex, value, countId, noId) {
        const tbody = document.getElementById(tbodyId);
        if (!tbody) return;
        const rows = tbody.querySelectorAll('tr');
        let visible = 0, total = 0;
        rows.forEach(row => {
            if (!row.cells.length) return;
            total++;
            const cell = row.cells[colIndex];
            const match = !value || (cell && cell.textContent.trim().includes(value));
            row.style.display = match ? '' : 'none';
            if (match) visible++;
        });
        updateCount(countId, noId, visible, total, !!value);
    }

    // ── NHÂN VIÊN (multi-filter) ──
    function filterNV() {
        const tbody  = document.getElementById('t-nv');
        if (!tbody) return;
        const kw     = (document.getElementById('s-nv')?.value || '').toLowerCase().trim();
        const pb     = document.getElementById('sel-pb-nv')?.value || '';
        const tt     = document.getElementById('sel-tt-nv')?.value || '';
        const rows   = tbody.querySelectorAll('tr');
        let visible  = 0, total = 0;
        rows.forEach(row => {
            if (!row.cells.length) return;
            total++;
            const text   = row.textContent.toLowerCase();
            const matchKw= !kw || text.includes(kw);
            const matchPb= !pb || (row.cells[5] && row.cells[5].textContent.includes(pb));
const matchTt = !tt || (row.cells[9] && row.cells[9].textContent.includes(tt));
            const show   = matchKw && matchPb && matchTt;
            row.style.display = show ? '' : 'none';
            if (show) visible++;
        });
        updateCount('count-nv','no-nv', visible, total, !!(kw||pb||tt));
    }

    // ── NGHỈ PHÉP (multi-filter) ──
    function filterNP() {
        const tbody  = document.getElementById('t-np-cho');
        if (!tbody) return;
        const kw     = (document.getElementById('s-np')?.value || '').toLowerCase().trim();
        const loai   = (document.getElementById('sel-loai-np')?.value || '').toLowerCase().trim();
        const rows   = tbody.querySelectorAll('tr');
        let visible  = 0, total = 0;
        rows.forEach(row => {
            if (!row.cells.length) return;
            total++;
            const matchKw   = !kw   || (row.cells[0] && row.cells[0].textContent.toLowerCase().includes(kw));
            const matchLoai = !loai || (row.cells[1] && row.cells[1].textContent.toLowerCase().includes(loai));
            const show      = matchKw && matchLoai;
            row.style.display = show ? '' : 'none';
            if (show) visible++;
        });
        updateCount('count-np','no-np', visible, total, !!(kw||loai));
    }
    // ── BẢNG LƯƠNG (multi-filter) ──
    function filterBL() {
        const tbody  = document.getElementById('t-bl');
        if (!tbody) return;
        const kw     = (document.getElementById('s-bl')?.value || '').toLowerCase().trim();
        const tt     = document.getElementById('sel-tt-bl')?.value || '';
        const thang  = document.getElementById('sel-thang-bl')?.value || '';
        const rows   = tbody.querySelectorAll('tr');
        let visible  = 0, total = 0;
        rows.forEach(row => {
            if (!row.cells.length) return;
            total++;
            const matchKw   = !kw    || (row.cells[0] && row.cells[0].textContent.toLowerCase().includes(kw));
            const matchTt   = !tt    || (row.cells[8] && row.cells[8].textContent.includes(tt));
            const matchThang= !thang || (row.cells[1] && row.cells[1].textContent.trim() === thang);
            const show      = matchKw && matchTt && matchThang;
            row.style.display = show ? '' : 'none';
            if (show) visible++;
        });
        updateCount('count-bl','no-bl', visible, total, !!(kw||tt||thang));
    }

    // ── ĐÁNH GIÁ select ──
    function filterBySelectDG(colIndex, value) {
        filterBySelectGen('t-dg', colIndex, value, 'count-dg', 'no-dg');
    }
    // ── CHẤM CÔNG (multi-filter) ──
    function filterCC() {
        const tbody  = document.getElementById('t-cc');
        if (!tbody) return;
        const kw     = (document.querySelector('#panel-chamcong .search-bar input[type="text"]')?.value || '').toLowerCase().trim();
        const ngay   = document.getElementById('sel-ngay-cc')?.value || '';   // yyyy-MM-dd
        const thang  = document.getElementById('sel-thang-cc')?.value || '';  // "1".."12"
        const rows   = tbody.querySelectorAll('tr');
        let visible  = 0, total = 0;

        rows.forEach(row => {
            if (!row.cells.length) return;
            total++;

            // col 0 = mã NV, col 1 = họ tên
            const matchKw = !kw || (
                (row.cells[0] && row.cells[0].textContent.toLowerCase().includes(kw)) ||
                (row.cells[1] && row.cells[1].textContent.toLowerCase().includes(kw))
            );

            // col 2 = ngày chấm công (định dạng yyyy-MM-dd hoặc dd/MM/yyyy tùy DB)
            const cellNgay = row.cells[2] ? row.cells[2].textContent.trim() : '';
            const matchNgay  = !ngay  || cellNgay === ngay;
            const matchThang = !thang || cellNgay.includes('-' + thang.padStart(2,'0') + '-')
                                      || cellNgay.startsWith(thang.padStart(2,'0') + '/');

            const show = matchKw && matchNgay && matchThang;
            row.style.display = show ? '' : 'none';
            if (show) visible++;
        });

        updateCount('count-cc', 'no-cc', visible, total, !!(kw || ngay || thang));
    }
</script>
<script>
    // Đếm số hàng trong bảng phòng ban (tbody id="t-pb")
    const soLuong = document.querySelectorAll('#t-pb tr').length;
        const soThuTu = soLuong + 1;
        const ma = soThuTu < 10 ? 'PB0' + soThuTu : 'PB' + soThuTu;
        document.getElementById('maPhongBan').value = ma;
     const soLuongCV = document.querySelectorAll('#t-cv tr').length;
     const soThuTuCV = soLuongCV + 1;
     document.getElementById('maChucVu').value = 'CV' + (soThuTuCV < 10 ? '0' + soThuTuCV : soThuTuCV);

</script>

<script>
    <c:if test="${not empty message}">
        alert("✅ ${message}");
    </c:if>
    // ── VIETQR MODAL ──
    let currentBangLuongId = null;

    function moQR(bangLuongId, tenNV, soTK, nganHang, soTien) {
        currentBangLuongId = bangLuongId;

        document.getElementById('qr-ten-nv').textContent = tenNV;
        document.getElementById('qr-info').textContent =
            'STK: ' + (soTK || 'Chưa có') + ' — ' + (nganHang || 'Chưa có ngân hàng');

        // Tạo VietQR URL
        // Format: https://img.vietqr.io/image/{BANK_ID}-{ACCOUNT_NO}-{TEMPLATE}.png?amount={AMOUNT}&addInfo={INFO}&accountName={NAME}
        const bankId   = nganHang || 'VCB'; // dùng tên ngân hàng hoặc mã BIN
        const soTienInt = Math.round(parseFloat(soTien) || 0);
        const qrUrl = 'https://img.vietqr.io/image/' + bankId + '-' + soTK
                    + '-compact2.png?amount=' + soTienInt
                    + '&addInfo=Luong%20thang'
                    + '&accountName=' + encodeURIComponent(tenNV);

        document.getElementById('qr-img').src = qrUrl;
        document.getElementById('modal-qr').style.display = 'flex';
    }

    function dongQR() {
        document.getElementById('modal-qr').style.display = 'none';
        currentBangLuongId = null;
    }

    function xacNhanQR() {
        if (!currentBangLuongId) return;
        if (confirm('Xác nhận đã nhận được chuyển khoản?')) {
            window.location.href = '${pageContext.request.contextPath}/bangluong?action=thanh-toan-chuyen-khoan&id=' + currentBangLuongId;
        }
    }
</script>

</body>
</html>
