package run.hxtia.workbd.common.utils;

import cn.binarywang.wx.miniapp.api.WxMaService;
import run.hxtia.workbd.common.prop.WorkBoardProperties;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;

public class MiniApps {
    public final static String APP_ID = WorkBoardProperties.getNotBeanWx().getAppId();
    public final static String TEMPLATE_ID = WorkBoardProperties.getNotBeanWx().getTemplateId();
    /**
     * 检查appId
     */
    public static void checkAppId(WxMaService wxMaService) {
        if (!wxMaService.switchover(APP_ID)) {
            JsonVos.raise(CodeMsg.NO_APP_ID);
        }
    }
}
