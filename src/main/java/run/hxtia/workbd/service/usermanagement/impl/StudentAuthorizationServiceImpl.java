package run.hxtia.workbd.service.usermanagement.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.mapper.AuthorizationMapper;
import run.hxtia.workbd.mapper.StudentAuthorizationMapper;
import run.hxtia.workbd.pojo.po.Authorization;
import run.hxtia.workbd.pojo.po.College;
import run.hxtia.workbd.pojo.po.StudentAuthorization;
import run.hxtia.workbd.pojo.vo.usermanagement.request.StudentAuthorizationReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.StudentAuthorizationVo;
import run.hxtia.workbd.service.usermanagement.AuthorizationService;
import run.hxtia.workbd.service.usermanagement.StudentAuthorizationService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Xiaojin
 * @date 2024/5/17
 */

@Service
@RequiredArgsConstructor
public class StudentAuthorizationServiceImpl extends ServiceImpl<StudentAuthorizationMapper, StudentAuthorization> implements StudentAuthorizationService {


    @Override
    public boolean saveStudentAuthorization(StudentAuthorizationReqVo studentAuthorizationReqVo) {
        StudentAuthorization studentAuthorization = MapStructs.INSTANCE.reqVo2po(studentAuthorizationReqVo);
        return save(studentAuthorization);
    }

    @Override
    public boolean deleteStudentAuthorization(String studentId) {
        if (studentId == null || studentId.isEmpty()) return false;

        LambdaQueryWrapper<StudentAuthorization> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudentAuthorization::getStudentId, studentId);
        return remove(wrapper);
    }

    @Override
    public boolean updateStudentAuthorization(StudentAuthorizationReqVo studentAuthorizationReqVo) {
        StudentAuthorization studentAuthorization = MapStructs.INSTANCE.reqVo2po(studentAuthorizationReqVo);

        UpdateWrapper<StudentAuthorization> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("student_id", studentAuthorization.getStudentId());

        return update(studentAuthorization, updateWrapper);
    }

    @Override
    public StudentAuthorizationVo getStudentAuthorizationById(String studentId) {
        LambdaQueryWrapper<StudentAuthorization> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudentAuthorization::getStudentId, studentId);
        StudentAuthorization studentAuthorization = getOne(wrapper);
        return MapStructs.INSTANCE.po2vo(studentAuthorization);
    }

    @Override
    public List<StudentAuthorizationVo> getAllStudentAuthorizations() {
        List<StudentAuthorization> studentAuthorizations = list();
        return studentAuthorizations.stream()
            .map(MapStructs.INSTANCE::po2vo)
            .collect(Collectors.toList());
    }

    @Override
    public boolean saveOrUpdate(StudentAuthorization studentAuthorization) {
        LambdaQueryWrapper<StudentAuthorization> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudentAuthorization::getStudentId, studentAuthorization.getStudentId());
        return saveOrUpdate(studentAuthorization, wrapper);
    }
}