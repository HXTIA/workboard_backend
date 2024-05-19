package run.hxtia.workbd.service.organization.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.enhance.MpPage;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.mapper.GradeMapper;
import run.hxtia.workbd.pojo.po.Grade;
import run.hxtia.workbd.pojo.vo.organization.request.GradeEditReqVo;
import run.hxtia.workbd.pojo.vo.organization.request.GradeReqVo;
import run.hxtia.workbd.pojo.vo.organization.request.page.GradePageReqVo;
import run.hxtia.workbd.pojo.vo.organization.response.GradeVo;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;
import run.hxtia.workbd.service.organization.GradeService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Xiaojin
 * @date 2024/5/5
 */
@Service
@RequiredArgsConstructor
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {

    @Override
    public boolean save(GradeReqVo reqVo) {
        // 检查名称是否已经存在
        boolean exists = lambdaQuery().eq(Grade::getName, reqVo.getName()).eq(Grade::getCollegeId, reqVo.getCollegeId()).one() != null;
        if (exists) {
            // 如果名称已经存在，那么返回false，表示保存操作失败
            return false;
        }

        // 如果名称不存在，那么创建新的年级
        Grade po = MapStructs.INSTANCE.reqVo2po(reqVo);
        return save(po);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean update(GradeEditReqVo reqVo) {
        // 检查在同一个学院下是否已经存在相同的name
        boolean exists = lambdaQuery().eq(Grade::getName, reqVo.getName()).eq(Grade::getCollegeId, reqVo.getCollegeId()).one() != null;

        // 如果不存在，那么更新年级信息
        Grade po = MapStructs.INSTANCE.reqVo2po(reqVo);
        return updateById(po);
    }

    @Override
    public boolean delete(Integer gradeId) {
        return removeById(gradeId);
    }

    @Override
    public GradeVo getGradeInfoById(Integer gradeId) {
        Grade grade = getById(gradeId);
        return MapStructs.INSTANCE.po2vo(grade);
    }

    @Override
    public PageVo<GradeVo> getList() {
        List<Grade> grades = list();
        List<GradeVo> gradeVos = grades.stream()
            .map(MapStructs.INSTANCE::po2vo)
            .collect(Collectors.toList());

        PageVo<GradeVo> pageVo = new PageVo<>();
        pageVo.setData(gradeVos);
        pageVo.setCount((long) gradeVos.size());
        pageVo.setPages(1L); // 这里假设所有的数据都在一个页面，可能需要根据分页逻辑来设置这个值

        return pageVo;
    }

    @Override
    public boolean checkGradeInfo(Integer gradeId) {
        return getById(gradeId) != null;
    }

    @Override
    public List<GradeVo> getGradeInfoByCollegeId(Integer collegeId) {
        List<Grade> grades = this.query().eq("college_id", collegeId).list();
        return grades.stream()
            .map(MapStructs.INSTANCE::po2vo)
            .collect(Collectors.toList());
    }

    @Override
    public PageVo<GradeVo> getGradeInfoByCollegeIdWithPagination(GradePageReqVo reqVo) {

        MpLambdaQueryWrapper<Grade> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.like(reqVo.getKeyword(), Grade::getName)
            .eq(Grade::getCollegeId, reqVo.getCollegeId());

        // 构建分页结果
        return baseMapper.selectPage(new MpPage<>(reqVo), wrapper)
            .buildVo(MapStructs.INSTANCE::po2vo);
    }

    @Override
    public boolean checkGradeExists(String gradeName, Integer collegeId) {
        // 检查名称是否已经存在
        boolean exists = lambdaQuery().eq(Grade::getName, gradeName).eq(Grade::getCollegeId, collegeId).one() != null;
        return exists;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Integer, String> getGradeNamesByIds(List<Integer> gradeIds) {
        LambdaQueryWrapper<Grade> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Grade::getId, gradeIds);
        List<Grade> grades = list(wrapper);
        return grades.stream()
            .collect(Collectors.toMap(Grade::getId, Grade::getName));
    }
}
