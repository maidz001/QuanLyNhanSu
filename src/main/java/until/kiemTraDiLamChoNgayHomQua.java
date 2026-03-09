package until;

import dao.ChamCongDAO;
import model.NhanVien;
import service.NhanVienService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

@WebListener
public class kiemTraDiLamChoNgayHomQua implements ServletContextListener {
private NhanVienService nhanVienService=new NhanVienService();
    private Timer timer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        timer = new Timer("kiemtradilam", true);

        // cho nay dat gio vhayj moi ngay
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 5);
        cal.set(Calendar.SECOND, 0);
        if (cal.getTime().before(new java.util.Date())) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ChamCongDAO dao = new ChamCongDAO();
                LocalDate homQua = LocalDate.now().minusDays(1);

                //o day bo qua cuoi tuan
                DayOfWeek thu = homQua.getDayOfWeek();
                if (thu == DayOfWeek.SATURDAY || thu == DayOfWeek.SUNDAY) {
                    System.out.println("(Kiem Tra Di Lam) hom qua la cuoi tuan bo qua.");
                    return;
                }

                //kiem tra tung nhan vien
                for (NhanVien nvId : nhanVienService.layTatCa()) {
                    if (!dao.daCoRecord(nvId.getNhanVienId(), homQua)) {
                        dao.insertNghiKhongPhep(nvId.getNhanVienId(), homQua);
                    }
                }
            }
        },
                // lap sau moi 24h
                cal.getTime(), 24 * 60 * 60 * 1000L);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (timer != null) timer.cancel();
    }
}
