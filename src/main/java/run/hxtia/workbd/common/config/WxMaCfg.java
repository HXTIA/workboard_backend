package run.hxtia.workbd.common.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import run.hxtia.workbd.common.prop.WorkBoardProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信SDK配置类
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class WxMaCfg {
    /**
     * 用于读取项目Yml中的配置
     */
    private final WorkBoardProperties properties;

    @Bean
    public WxMaService wxMaService() {
        WorkBoardProperties.Wx wx = this.properties.getWx();
        WxMaService maService = new WxMaServiceImpl();
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(wx.getAppId());
        config.setSecret(wx.getSecret());
        config.setMsgDataFormat(wx.getMsgDataFormat());

        Map<String, WxMaConfig> map = new HashMap<>();
        map.put(wx.getAppId(), config);
        maService.setMultiConfigs(map);
        return maService;
    }
}
