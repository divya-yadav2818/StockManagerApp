package com.maruti.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {

    private static final String FROM_EMAIL = "divyayadav84848@gmail.com";
    private static final String APP_PASSWORD = "jtdpwonvwxoxrzmw";

    // ✅ Existing method - keep it
    public static boolean sendOtp(String toEmail, String otp) {
        return sendMail(toEmail, otp);
    }

    // ✅ Optional method you can call from servlet
    public static boolean sendOTPEmail(String toEmail, String otp) {
        System.out.println("Sending OTP to: " + toEmail + " | OTP: " + otp);
        return sendMail(toEmail, otp);
    }

    // 🔹 Main mail sending logic
    private static boolean sendMail(String toEmail, String otp) {
        try {
            // SMTP properties
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            // Mail session with authentication
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
                }
            });

            // Prepare message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, "Maruti Stock Manager"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Your OTP - Maruti Stock Manager");
            message.setText("Your OTP is: " + otp + "\n\nThis OTP is valid for 5 minutes.");

            // Send message safely
            try {
                Transport.send(message);
            } catch (Throwable t) {
                // Handles MessagingException AND NoClassDefFoundError
                System.err.println("Error sending email:");
                t.printStackTrace();
                return false;
            }

            return true;

        } catch (Throwable e) {
            // Handles any other error
            System.err.println("Unexpected error in sendMail:");
            e.printStackTrace();
            return false;
        }
    }
}
