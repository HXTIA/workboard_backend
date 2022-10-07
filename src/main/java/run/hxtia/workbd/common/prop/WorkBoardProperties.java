package run.hxtia.workbd.common.prop;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 读取yml中 work-board 的配置选项
 */
@Data
@Component
@ConfigurationProperties("work-board")
public class WorkBoardProperties implements ApplicationContextAware {

    /**
     * 1、配置类
     * 2、文件上传路径类
     * 3、微信小程序
     * 4、邮件发送
     */
    private Cfg cfg;
    private Upload upload;
    private Wx wx;
    private Email email;
    /**
     * 1、如果没有放在Spring容器中的Bean
     * 2、不可以直接使用 @Autowired 注入对象。给那些没有在Spring Ioc中的类使用
     */
    private static WorkBoardProperties properties;
    public static WorkBoardProperties getProperties() {
        return properties;
    }

    /**
     * 给没有放入Bean的获取 Wx 的配置
     * @return 装载的 Wx 配置
     */
    public static Wx getNotBeanWx() {
        return properties.getWx();
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        properties = this;
    }

    /**
     * 读取文件上传的配置
     */
    @Data
    public static class Upload {
        private String basePath;
        private String uploadPath;
        private String imagePath;
        private String videoPath;

        // 获取图片相对目录
        public String getImageDir() {
            return uploadPath + imagePath;
        }

        // 获取视频相对目录
        public String getVideoDir() {
            return uploadPath + videoPath;
        }
    }

    /**
     * 读取项目配置
     */
    @Data
    public static class Cfg {
        // 允许通过的域
        private String[] corsOrigins;
    }

    /**
     * 读取微信小程序配置
     */
    @Data
    public static class Wx {
        /**
         * 设置微信小程序的appId
         */
        private String appId;

        /**
         * 设置微信小程序的Secret
         */
        private String secret;

        /**
         * 消息格式，XML或者JSON
         */
        private String msgDataFormat;

        /**
         * 消息模板ID
         */
        private String templateId;
    }

    /**
     * 读取邮件配置
     */
    @Data
    public static class Email {
        /**
         * 邮件发送地址
         */
        private String fromAddr;
        /**
         * 用户名
         */
        private String nickName;

        public String getForm() {
            return nickName + "<" + fromAddr + ">";
        }
    }

}
