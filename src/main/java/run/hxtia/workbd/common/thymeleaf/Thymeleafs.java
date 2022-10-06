package run.hxtia.workbd.common.thymeleaf;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

/**
 * 模板工具
 */
@Component
@RequiredArgsConstructor
public class Thymeleafs {
    private final TemplateEngine engine;

    /**
     * 获取映射的模板名字
     * @param varMap：模板中需要用到的 《Key, Value》
     * @param template：静态模板所在路径
     * @return ：映射后的类容
     */
    public String getProcess(Map<String, Object> varMap, String template) {
        Context context = new Context();
        //FreeMarker邮件模板参数
        varMap.forEach(context::setVariable);
        // email.html:映射的html名字
        return engine.process(template, context);
    }

}
