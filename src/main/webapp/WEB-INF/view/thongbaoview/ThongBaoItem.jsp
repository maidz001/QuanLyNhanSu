<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:forEach var="tb" items="${danhSach}">
    <tr data-tbid="${tb.thongBaoId}">
        <td><strong>${tb.tieuDe}</strong></td>
        <td>${tb.noiDung}</td>
        <td><span class="badge badge-blue">${not empty tb.loai ? tb.loai : '--'}</span></td>
        <td><fmt:formatDate value="${tb.ngayTao}" pattern="yyyy-MM-dd"/></td>
        <td><span class="badge badge-orange">Chưa đọc</span></td>
        <td>
            <a href="${pageContext.request.contextPath}/thongbao?action=danhdaudadoc&id=${tb.thongBaoId}"
               style="font-size:0.72rem;color:var(--primary-light)">Đánh dấu đọc</a>
        </td>
    </tr>
</c:forEach>