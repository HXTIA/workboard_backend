package run.hxtia.workbd.service.Organization.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.mapper.ClassMapper;
import run.hxtia.workbd.pojo.po.Classes;
import run.hxtia.workbd.pojo.vo.request.organization.ClassEditReqVo;
import run.hxtia.workbd.pojo.vo.request.organization.ClassReqVo;
import run.hxtia.workbd.pojo.vo.response.organization.ClassVo;
import run.hxtia.workbd.pojo.vo.result.PageVo;
import run.hxtia.workbd.service.Organization.ClassService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Xiaojin
 * @date 2024/5/5
 */
@Service
@RequiredArgsConstructor
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Classes> implements ClassService {

    @Override
    public boolean save(ClassReqVo reqVo, Integer gradeId) {
        // 检查名称是否已经存在
        boolean exists = lambdaQuery().eq(Classes::getName, reqVo.getName()).eq(Classes::getGradeId, gradeId).one() != null;
        if (exists) {
            // 如果名称已经存在，那么返回false，表示保存操作失败
            return false;
        }

        // 如果名称不存在，那么创建新的班级
        Classes po = MapStructs.INSTANCE.reqVo2po(reqVo);
        return save(po);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean update(ClassEditReqVo reqVo) {
        // 检查在同一个年级下是否已经存在相同的班级名称
        boolean exists = lambdaQuery().eq(Classes::getName, reqVo.getName()).eq(Classes::getGradeId, reqVo.getGradeId()).one() != null;
        if (exists) {
            // 如果存在，那么抛出一个异常
            throw new RuntimeException("A class with the same name already exists in the same grade.");
        }

        // 如果不存在，那么更新班级信息
        Classes po = MapStructs.INSTANCE.reqVo2po(reqVo);
        return updateById(po);
    }

    @Override
    public boolean delete(Integer classId) {
        return removeById(classId);
    }

    @Override
    public ClassVo getClassInfoById(Integer classId) {
        Classes aClass = getById(classId);
        return MapStructs.INSTANCE.po2vo(aClass);
    }

    @Override
    public PageVo<ClassVo> getList() {
        List<Classes> classes = list();
        List<ClassVo> classVos = classes.stream()
            .map(MapStructs.INSTANCE::po2vo)
            .collect(Collectors.toList());

        PageVo<ClassVo> pageVo = new PageVo<>();
        pageVo.setData(classVos);
        pageVo.setCount((long) classVos.size());
        pageVo.setPages(1L); // 这里假设所有的数据都在一个页面，可能需要根据分页逻辑来设置这个值

        return pageVo;
    }

    @Override
    public PageVo<ClassVo> getClassInfoByGradeIdWithPagination(Integer gradeId, int pageNum, int pageSize) {
        // 创建分页条件
        Page<Classes> page = new Page<>(pageNum, pageSize);
        // 执行分页查询
        IPage<Classes> classPage = lambdaQuery().eq(Classes::getGradeId, gradeId).page(page);
        // 将查询结果转换为VO对象
        List<ClassVo> classVos = classPage.getRecords().stream().map(MapStructs.INSTANCE::po2vo).collect(Collectors.toList());
        // 创建并返回分页结果
        PageVo<ClassVo> result = new PageVo<>();
        result.setCount(classPage.getTotal());
        result.setData(classVos);
        return result;
    }

    @Override
    public boolean checkClassInfo(Integer classId) {
        return getById(classId) != null;
    }
}
