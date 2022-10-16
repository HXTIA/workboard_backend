package run.hxtia.workbd.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.enhance.MpPage;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.upload.UploadEditParam;
import run.hxtia.workbd.common.upload.Uploads;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.mapper.WorkMapper;
import run.hxtia.workbd.pojo.po.Work;
import run.hxtia.workbd.pojo.vo.request.page.WorkPageReqVo;
import run.hxtia.workbd.pojo.vo.request.save.WorkReqVo;
import run.hxtia.workbd.pojo.vo.request.save.WorkUploadReqVo;
import run.hxtia.workbd.pojo.vo.response.WorkVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.result.PageVo;
import run.hxtia.workbd.service.admin.UserWorkService;
import run.hxtia.workbd.service.admin.WorkService;

import java.util.Arrays;
import java.util.List;

/**
 * 作业模块 【管理】业务层
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class WorkServiceImpl extends ServiceImpl<WorkMapper, Work> implements WorkService {

    private final UserWorkService userWorkService;

    /**
     * 分页查询作业
     * @param pageReqVo：分页信息
     * @param status：作业状态 【1：可用作业 0：历史作业】
     * @return 分页后的数据
     */
    @Override
    public PageVo<WorkVo> list(WorkPageReqVo pageReqVo, Short status) {

        // 构建分页sql
        MpLambdaQueryWrapper<Work> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.like(pageReqVo.getKeyword(), Work::getTitle, Work::getDetail).
            between(pageReqVo.getCreatedTime(), Work::getCreatedAt).
            between(pageReqVo.getDeadline(), Work::getUpdatedAt).
            eq(Work::getCourseId, pageReqVo.getCourseId()).
            eq(Work::getSemesterId, pageReqVo.getSemesterId()).
            eq(Work::getEnable, status);

        return baseMapper.
            selectPage(new MpPage<>(pageReqVo), wrapper).
            buildVo(MapStructs.INSTANCE::po2vo);
    }

    /**
     * 保存 or 编辑作业
     * @param reqVo：作业信息
     * @return ：是否成功
     */
    @Override
    public boolean saveOrUpdate(WorkReqVo reqVo) throws Exception {
        Work po = MapStructs.INSTANCE.reqVo2po(reqVo);

        // 上传图片
        String filePath = "";
        if (reqVo.getId() == null) {
            // 保证是新建时，才来上传图片
            List<MultipartFile> pictureFiles = reqVo.getPicturesFiles();
            if (!CollectionUtils.isEmpty(pictureFiles))
                filePath = Uploads.uploadImages(pictureFiles);

            // 如果上传成功，保存图片路径到数据库
            if (StringUtils.hasLength(filePath))
                po.setPictures(filePath);
        }

        try {
            // 保存数据
            return saveOrUpdate(po);
        } catch (Exception e) {
            // 出现异常将刚上传的图片给删掉
            log.error(e.getMessage());
            e.printStackTrace();
            Uploads.deleteFiles(filePath);
            return false;
        }
    }

    /**
     * 多图片编辑，编辑作业的图片
     * @param uploadReqVo ：所需参数
     * @return ：是否成功
     */
    @Override
    public boolean updatePictures(WorkUploadReqVo uploadReqVo) throws Exception {
        Work po = baseMapper.selectById(uploadReqVo.getId());

        // 构建编辑图片的对象
        UploadEditParam editParam = new UploadEditParam(
            uploadReqVo.getPicturesFiles(),
            uploadReqVo.getMatchIndex(), po.getPictures());

        // 编辑图片
        return Uploads.uploadMoreWithPo(po, editParam, baseMapper, Work::setPictures);
    }

    /**
     * 删除一条or多条作业【逻辑删除】
     * @param ids：需要删除的作业ID
     * @return ：是否成功
     */
    @Override
    public boolean removeByIds(String ids) {
        List<String> workIds = Arrays.asList(ids.split(","));
        if (CollectionUtils.isEmpty(workIds)) return false;

        // 查出所有作业【并且设置不可见】
        List<Work> works = Streams.map(baseMapper.selectBatchIds(workIds), (work -> {
            work.setEnable(Constants.Status.WORK_DISABLE);
            return work;
        }));

        return updateBatchById(works);
    }

    /**
     * 根据作业ID获取作业信息
     * @param workId ：作业ID
     * @return ：作业数据
     */
    @Override
    public WorkVo getByWorkId(Long workId) {
        if (workId == null || workId <= 0) return null;
        MpLambdaQueryWrapper<Work> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(Work::getId, workId).eq(Work::getEnable, Constants.Status.WORK_ENABLE);
        return MapStructs.INSTANCE.po2vo(baseMapper.selectOne(wrapper));
    }

    /**
     * 删除一条or多条作业【彻底删除】
     * @param ids：需要删除的作业ID
     * @return ：是否成功
     */
    @Override
    public boolean removeHistory(String ids) {
        if (!StringUtils.hasLength(ids)) return false;

        List<String> workIds = Arrays.asList(ids.split(","));
        // 检查作业是否能删除
        checkWorkRemove(workIds);
        boolean deleteWork = removeByIds(workIds);
        boolean deleteUserWork = userWorkService.removeByWorkId(workIds);
        // 在用户作业表里删除作业
        if (!deleteWork || !deleteUserWork) {
            return JsonVos.raise(CodeMsg.REMOVE_ERROR);
        }
        return true;
    }

    /**
     * 检查作业是否能删除【只有是历史作业才能彻底删除】
     * @param workIds ：检查的作业ID
     */
    private void checkWorkRemove(List<String> workIds) {
        if (CollectionUtils.isEmpty(workIds)) return;
        List<Work> works = listByIds(workIds);
        for (Work work : works) {
            if (Constants.Status.WORK_ENABLE.equals(work.getEnable())) {
                JsonVos.raise(CodeMsg.WRONG_WORK_NO_REMOVE);
            }
        }
    }
}
