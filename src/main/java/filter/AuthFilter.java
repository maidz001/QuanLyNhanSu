package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import model.TaiKhoan;
import java.io.IOException;
import java.util.Locale;

@WebFilter(urlPatterns = "/*", asyncSupported = true)
public class AuthFilter implements Filter {



    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // ✅ Set TRƯỚC chain.doFilter để áp dụng cho MỌI response
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        res.setHeader("Pragma", "no-cache");
        res.setDateHeader("Expires", 0);

        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false); // false = không tạo session mới

        TaiKhoan tk = null;
        if (session != null) {
            Object attr = session.getAttribute("taiKhoanDangDangNhap");
            tk = (attr instanceof TaiKhoan) ? (TaiKhoan) attr : null;
        }

        boolean daDangNhap = tk != null;

        boolean publicPage =
                uri.contains("/taikhoan") ||
                        uri.contains(".css") || uri.contains(".js") ||
                        uri.contains(".png") || uri.contains(".jpg") ||
                        uri.contains(".jpeg") || uri.contains(".gif") ||
                        uri.contains(".woff") || uri.contains(".ttf");

        if (publicPage) {
            chain.doFilter(request, response);
            return;
        }

        if (!daDangNhap) {
            res.sendRedirect(req.getContextPath() + "/taikhoan/login");
            return;
        }

        if (tk.getVaiTro().toLowerCase(Locale.ROOT).equals("nhanvien")) {
            if (uri.contains("chuyenphongban") || uri.contains("danhgia")
                    || uri.contains("duyetnghiphep") || uri.contains("tinhluong")) {
                req.setAttribute("message", "Bạn không có quyền thực hiện hành vi này!");
                req.getRequestDispatcher("/WEB-INF/view/thongbaoview/ThongBao.jsp")
                        .forward(request, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

}