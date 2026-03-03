package filter;

import model.TaiKhoan;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();

        boolean daCoTaiKhoan = session != null && session.getAttribute("taiKhoan") != null;
        boolean laLoginPage = uri.equals(contextPath + "/login") || uri.equals(contextPath + "/dangky");
        boolean laResource = uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith(".ico");

        if (laResource || laLoginPage) {
            chain.doFilter(request, response);
            return;
        }

        if (!daCoTaiKhoan) {
            res.sendRedirect(contextPath + "/login");
            return;
        }

        TaiKhoan tk = (TaiKhoan) session.getAttribute("taiKhoan");
        boolean laAdminRoute = uri.contains("/phongban") || uri.contains("/chucvu") || uri.contains("/taikhoan") || uri.contains("/xuatexcel");
        boolean laQuanLyRoute = uri.contains("/duyet") || uri.contains("/tinhluong");

        if (laAdminRoute && !"Admin".equals(tk.getVaiTro())) {
            res.sendRedirect(contextPath + "/dashboard?loi=khongcoquyen");
            return;
        }

        if (laQuanLyRoute && "Nhan vien".equals(tk.getVaiTro())) {
            res.sendRedirect(contextPath + "/dashboard?loi=khongcoquyen");
            return;
        }

        chain.doFilter(request, response);
    }
}



