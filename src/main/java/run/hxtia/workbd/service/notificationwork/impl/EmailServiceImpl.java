package run.hxtia.workbd.service.notificationwork.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.cache.Caches;
import run.hxtia.workbd.common.prop.WorkBoardProperties;
import run.hxtia.workbd.common.thymeleaf.Thymeleafs;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.Strings;
import run.hxtia.workbd.pojo.vo.common.response.result.CodeMsg;
import run.hxtia.workbd.service.notificationwork.EmailService;

import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 邮件发送 业务层实现
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final WorkBoardProperties properties;
    private final Thymeleafs thymeleafs;

    /**
     * 发送验证码邮件
     * @param to：接收方邮件地址
     * @param subject：主题
     */
    @Override
    public void sendHTMLEmail(String to, String subject) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        // 生成验证码，并且缓存到 ehcache 中 【之后若需要，可缓存到redis中】
        String code = Strings.getUUID(6);
        String emailCodePrefix = Constants.VerificationCode.EMAIL_CODE_PREFIX;
        if (Caches.isExistCode(emailCodePrefix, to)) {
            // 说明在60秒内已经发送过了
            JsonVos.raise(CodeMsg.WRONG_CODE_EXIST);
        }

        // 构建邮件
        buildMimeMessageHelper(mimeMessage, to, subject, code);
        // 发送邮件
        try {
            javaMailSender.send(mimeMessage);
            Caches.putCode(emailCodePrefix + to, code);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            JsonVos.raise(CodeMsg.CODE_SEND_ERROR);
        }
    }

    /**
     * 构建邮件
     * @param mimeMessage：邮件对象
     * @param to：接收方邮件地址
     * @param subject：主题
     * @param code：验证码
     */
    private void buildMimeMessageHelper(MimeMessage mimeMessage,
                                        String to, String subject,
                                        String code) throws Exception {
        WorkBoardProperties.Email email = properties.getEmail();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        // 主题
        helper.setSubject(subject);
        // 发件人的邮箱
        helper.setFrom(email.getFromAddr());
        // 发送日期
        helper.setSentDate(new Date());
        // 要发给的邮箱
        helper.setTo(to);
        // 密送的邮箱
        helper.setBcc(to);
        // 抄送邮箱
        helper.setCc(to);
        // 快速回复
        helper.setReplyTo(email.getFromAddr());
        // 构建模板类容
        String content = buildTemplate(code);
        // 邮件内容(html渲染 所以要填true)
        helper.setText(content, true);
    }

    /**
     * 构建验证码的模板内容
     * @param code：验证码
     * @return ：模板类容
     */
    private String buildTemplate(String code) {
        if (!StringUtils.hasLength(code)) return "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis() + 60000);
        Map<String, Object> varMap = new HashMap<>();
        // Thymeleaf邮件模板参数
        varMap.put("code", code);
        varMap.put("expirationDate", format.format(date));
        return thymeleafs.getProcess(varMap, "email.html");
    }

}
