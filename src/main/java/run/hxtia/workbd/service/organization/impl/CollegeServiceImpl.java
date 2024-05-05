package run.hxtia.workbd.service.organization.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.mapper.CollegeMapper;
import run.hxtia.workbd.pojo.po.College;
import run.hxtia.workbd.pojo.vo.request.organization.CollegeEditReqVo;
import run.hxtia.workbd.pojo.vo.request.organization.CollegeReqVo;
import run.hxtia.workbd.pojo.vo.response.organization.CollegeVo;
import run.hxtia.workbd.pojo.vo.result.PageVo;
import run.hxtia.workbd.service.organization.CollegeService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Xiaojin
 * @date 2024/5/5
 */
@Service
@RequiredArgsConstructor
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements CollegeService {

    @Override
    public boolean save(CollegeReqVo reqVo) {
        College po = MapStructs.INSTANCE.reqVo2po(reqVo);
        Boolean flag = save(po);
        return flag;
    }

    @Override
    public boolean update(CollegeEditReqVo reqVo) {
        College po = MapStructs.INSTANCE.reqVo2po(reqVo);
        return updateById(po);
    }

    @Override
    public boolean delete(Integer collegeId) {
        return removeById(collegeId);
    }

    @Override
    public CollegeVo getCollegeInfoById(Integer collegeId) {
        College college = getById(collegeId);
        return MapStructs.INSTANCE.po2vo(college);
    }

    @Override
    public PageVo<CollegeVo> getList() {
        List<College> colleges = list();
        List<CollegeVo> collegeVos = colleges.stream()
            .map(MapStructs.INSTANCE::po2vo)
            .collect(Collectors.toList());

        PageVo<CollegeVo> pageVo = new PageVo<>();
        pageVo.setData(collegeVos);
        pageVo.setCount((long) collegeVos.size());
        pageVo.setPages(1L); // 这里假设所有的数据都在一个页面，可能需要根据分页逻辑来设置这个值

        return pageVo;
    }

    @Override
    public boolean checkClgInfo(Integer collegeId) {
        // 使用MyBatis Plus的getById方法检查学院是否存在
        // 如果getById返回的结果不为null，那么学院存在，返回true
        // 否则，学院不存在，返回false
        return getById(collegeId) != null;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean saveDefaultRegisterClg(College collegeInfo) {
        if(collegeInfo == null) return false;
        return baseMapper.insertDefaultRegisterCollege(collegeInfo) > 0;
    }
}
