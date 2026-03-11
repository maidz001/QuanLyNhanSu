package service;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.util.Random;

public class EmailService {

    private final String EMAIL = "hmai030606@gmail.com";
    private final String PASSWORD = "kjrjbbhihtcpyvie";

    public String taoMaOTP() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public boolean guiOTP(String toEmail, String otp) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Mã xác nhận đặt lại mật khẩu");
            message.setText("Mã OTP của bạn là: " + otp + "\nMã có hiệu lực trong 5 phút.");
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}