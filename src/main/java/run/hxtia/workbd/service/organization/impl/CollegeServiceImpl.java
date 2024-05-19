package run.hxtia.workbd.service.organization.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.enhance.MpPage;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.mapper.CollegeMapper;
import run.hxtia.workbd.pojo.po.AdminUsers;
import run.hxtia.workbd.pojo.po.College;
import run.hxtia.workbd.pojo.vo.organization.request.CollegeEditReqVo;
import run.hxtia.workbd.pojo.vo.organization.request.CollegeReqVo;
import run.hxtia.workbd.pojo.vo.common.request.page.PageReqVo;
import run.hxtia.workbd.pojo.vo.organization.request.page.CollegePageReqVo;
import run.hxtia.workbd.pojo.vo.organization.response.CollegeVo;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;
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
        // 检查名称是否已经存在
        boolean exists = lambdaQuery().eq(College::getName, reqVo.getName()).one() != null;
        if (exists) {
            // 如果名称已经存在，那么返回false，表示保存操作失败
            return false;
        }

        // 如果名称不存在，那么创建新的学院
        College po = MapStructs.INSTANCE.reqVo2po(reqVo);
        return save(po);
    }

    @Override
    public boolean update(CollegeEditReqVo reqVo) {
        // 检查新的名称是否已经存在
        boolean exists = lambdaQuery().eq(College::getName, reqVo.getName()).ne(College::getId, reqVo.getId()).one() != null;
        if (exists) {
            // 如果新的名称已经存在，那么返回false，表示更新操作失败
            return false;
        }

        // 如果新的名称不存在，那么更新学院信息
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
     /**
     * 分页获取学院信息
     * @param reqVo ：分页请求参数
     * @return 分页的学院信息
     */
    public PageVo<CollegeVo> getPageList(CollegePageReqVo pageReqVo) {
     MpLambdaQueryWrapper<College> wrapper = new MpLambdaQueryWrapper<>();
     wrapper.like(pageReqVo.getKeyword(), College::getName);

     return baseMapper.
         selectPage(new MpPage<>(pageReqVo), wrapper).
         buildVo(MapStructs.INSTANCE::po2vo);
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
