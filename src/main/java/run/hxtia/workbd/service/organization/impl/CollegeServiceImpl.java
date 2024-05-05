package run.hxtia.workbd.service.organization.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.hxtia.workbd.mapper.CollegeMapper;
import run.hxtia.workbd.pojo.po.College;
import run.hxtia.workbd.pojo.vo.request.save.CollegeReqVo;
import run.hxtia.workbd.pojo.vo.response.CollegeVo;
import run.hxtia.workbd.pojo.vo.result.PageVo;
import run.hxtia.workbd.service.organization.CollegeService;

/**
 * @author Xiaojin
 * @date 2024/5/5
 */
@Service
@RequiredArgsConstructor
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements CollegeService {

    @Override
    public boolean save(CollegeReqVo reqVo) {
        return false;
    }


    @Override
    public boolean delete(Integer collegeId) {
        return false;
    }

    @Override
    public CollegeVo getCollegeInfoById(Integer collegeId) {
        return null;
    }

    @Override
    public PageVo<CollegeVo> getList() {
        return null;
    }
}
