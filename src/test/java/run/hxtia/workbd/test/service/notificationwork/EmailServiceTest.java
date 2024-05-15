package run.hxtia.workbd.test.service.notificationwork;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import run.hxtia.workbd.service.notificationwork.EmailService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void testSendHTMLEmail() {
        try {
            emailService.sendHTMLEmail("1533460130@qq.com", "Test Subject");
            System.out.println("Email sent successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
}
