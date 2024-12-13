package travel_management_system.Components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class MailSenderComponent {

    private JavaMailSender mailSender;

    public MailSenderComponent(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendLeaveRequestMail(String receiverEmail, String receiverName){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Leave Request Application");
            message.setTo(receiverEmail);
            message.setFrom("eyidana001@gmail.com");
            message.setText(String.format("""
            Hello %s,
            
            You have successfully completed your leave request application.
            We will review it and get back to you soon.
            
            Thank you!
            """, receiverName));
            mailSender.send(message);
            log.info("message sent successfully:{}", message);
        } catch (MailSendException e) {
            throw new MailSendException(Objects.requireNonNull(e.getMessage()));
        }
    }

    public void sendLeaveRequestApprovalMail(String receiverEmail, String receiverName){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Leave Request Application");
            message.setTo(receiverEmail);
            message.setFrom("eyidana001@gmail.com");
            message.setText(String.format("""
            Hello %s,
            
            Your leave request application has successfully been processed and approved.
            Congratulation!!🎉👏
            
            Thank you!
            """, receiverName));
            mailSender.send(message);
            log.info("leave approved:{}", message);
        } catch (MailSendException e) {
            throw new MailSendException(Objects.requireNonNull(e.getMessage()));
        }
    }
}
