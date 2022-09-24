package run.hxtia.workbd.service.admin;


import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.Organization;

@Transactional(readOnly = true)
public interface OrganizationService extends IService<Organization> {

}
