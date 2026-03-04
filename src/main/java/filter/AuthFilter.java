package filter;

import jakarta.servlet.http.HttpSession;
import model.TaiKhoan;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session= (HttpSession) req.getSession(false);
        String uri=req.getRequestURI();
        TaiKhoan tk=(TaiKhoan) session.getAttribute("tk");
        boolean daDangNhap=session!=null&&tk.getVaiTro()!=null;
        if(uri.endsWith("login.jsp")||
                uri.endsWith("login") ||
                uri.contains(".css") ||
                uri.contains(".js") ||
                uri.contains(".png") ||
                uri.contains(".jpg") ||
                uri.contains(".jpeg") ||
                uri.contains(".gif") ||
                uri.contains(".woff") ||
                uri.contains(".ttf")){

            chain.doFilter(request,response);
            return;

        }
        if(!daDangNhap){
            res.sendRedirect(req.getContextPath()+"/login.jsp");
            return;
        }

        if(tk.getVaiTro().toLowerCase(Locale.ROOT).equals("quanly")){
        if(uri.contains("chuyenphongban")||uri.contains("danhgia")||uri.contains("duyetnghiphep")||uri.contains("tinhluong")){
            req.setAttribute("message","ban khong co quyen");
            req.getRequestDispatcher("/ThongBao.jsp").forward(request,response);
            return;
        }}
        chain.doFilter(request,response);
    }
}



