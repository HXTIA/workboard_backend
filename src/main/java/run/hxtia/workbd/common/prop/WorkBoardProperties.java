package run.hxtia.workbd.common.prop;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("work-board")
public class WorkBoardProperties implements ApplicationContextAware {

    /**
     * 1、配置类
     * 2、文件上传路径类
     */
    private Cfg cfg;
    private Upload upload;
    private Wx wx;
    /**
     * 1、如果没有放在Spring容器中的Bean
     * 2、不可以直接使用 @Autowired 注入对象。给那些没有在Spring Ioc中的类使用
     */
    private static WorkBoardProperties properties;
    public static WorkBoardProperties getProperties() {
        return properties;
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
    }

}
