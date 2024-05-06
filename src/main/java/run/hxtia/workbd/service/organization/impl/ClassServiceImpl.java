package run.hxtia.workbd.service.organization.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.mapper.ClassMapper;
import run.hxtia.workbd.pojo.po.Classes;
import run.hxtia.workbd.pojo.vo.request.organization.ClassEditReqVo;
import run.hxtia.workbd.pojo.vo.request.organization.ClassReqVo;
import run.hxtia.workbd.pojo.vo.response.organization.ClassVo;
import run.hxtia.workbd.pojo.vo.result.PageVo;
import run.hxtia.workbd.service.organization.ClassService;

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
    public boolean update(ClassEditReqVo reqVo) {
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
    public List<ClassVo> getClassInfoByGradeId(Integer gradeId) {
        List<Classes> classes = this.query().eq("grade_id", gradeId).list();
        return classes.stream()
            .map(MapStructs.INSTANCE::po2vo)
            .collect(Collectors.toList());
    }

    @Override
    public boolean checkClassInfo(Integer classId) {
        return getById(classId) != null;
    }
}
