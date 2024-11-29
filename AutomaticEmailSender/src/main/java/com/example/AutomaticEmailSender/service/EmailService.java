package com.example.AutomaticEmailSender.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.io.File;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private List<String> hrEmails = List.of("kunal.yadav@walkingtree.tech");

    // Method to send email with attachment
    public void sendEmailWithAttachment(String to, String subject, String body, String attachmentPath) throws MessagingException, MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // 'true' to enable multipart

        helper.setFrom("KunalYadav");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        // Add resume attachment
        File resumeFile = new File(attachmentPath);
        if (resumeFile.exists()) {
            helper.addAttachment("Resume.pdf", resumeFile); // You can customize the file name
        }

        javaMailSender.send(message);
    }

    @Scheduled(cron = "*/30 * * * * *")  // This triggers the task every 30 seconds
    public void sendEmailsToHrs() {
        String subject = "Java Developer Position Application";
        String body = "Dear HR,\n\nI am interested in applying for the Java Developer position. Please find my resume attached.\n\nRegards, [Your Name]";

        // Set the correct path to your resume file
        String resumePath = "C:\\Users\\WalkingTree\\Downloads\\Resume (5).pdf";  // Update with your actual resume file path

        for (String hrEmail : hrEmails) {
            try {
                sendEmailWithAttachment(hrEmail, subject, body, resumePath);
            } catch (MessagingException e) {
                e.printStackTrace(); // Handle exception (logging or retry logic can be added here)
            }
        }
    }
}
