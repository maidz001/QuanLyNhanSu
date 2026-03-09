package until;

import dao.ChamCongDAO;
import model.NhanVien;
import service.NhanVienService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

@WebListener
public class kiemTraDiLamChoNgayHomQua implements ServletContextListener {

    private NhanVienService nhanVienService = new NhanVienService();
    private Timer timer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        timer = new Timer("DailyJob", true);

        // Test: chạy sau 5 giây
        // Sau khi test xong thì đổi lại cal.getTime() như bên dưới
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ChamCongDAO dao = new ChamCongDAO();

                // Ngày hôm qua
                LocalDate homQua = LocalDate.now().minusDays(1);

                // Bỏ qua cuối tuần
                //

                // Check từng nhân viên
                for (NhanVien nv : nhanVienService.layTatCa()) {
                    if (!dao.daCoRecord(nv.getNhanVienId(), homQua)) {
                        dao.insertNghiKhongPhep(nv.getNhanVienId(), homQua);
                        System.out.println("[DailyJob] NV " + nv.getNhanVienId() + " nghỉ không phép ngày " + homQua);
                    }
                }
            }
        }, 5000L, 24 * 60 * 60 * 1000L); // chạy sau 5 giây để test
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (timer != null) timer.cancel();
    }
}