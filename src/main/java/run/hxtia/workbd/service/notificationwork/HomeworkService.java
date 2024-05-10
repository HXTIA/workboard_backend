package run.hxtia.workbd.service.notificationwork;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.dto.StudentHomeworkDetailDto;
import run.hxtia.workbd.pojo.po.Homework;
import run.hxtia.workbd.pojo.vo.request.page.HomeworkPageReqVo;
import run.hxtia.workbd.pojo.vo.request.save.HomeworkReqVo;
import run.hxtia.workbd.pojo.vo.request.save.HomeworkUploadReqVo;
import run.hxtia.workbd.pojo.vo.response.HomeworkVo;
import run.hxtia.workbd.pojo.vo.result.PageVo;

import java.util.List;

/**
 * 作业模块 业务层
 */
@Transactional(readOnly = true)
public interface HomeworkService extends IService<Homework> {

    /**
     * 分页查询作业
     * @param pageReqVo：分页信息
     * @param status：作业状态 【1：可用作业 0：历史作业】
     * @return 分页后的数据
     */
    PageVo<HomeworkVo> list(HomeworkPageReqVo pageReqVo, Short status);

    /**
     * 保存 or 编辑作业
     * @param reqVo：作业信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean saveOrUpdate(HomeworkReqVo reqVo) throws Exception;

    /**
     * 多图片编辑，编辑作业的图片
     * @param uploadReqVo ：所需参数
     * @return ：是否成功
     */
    boolean updatePictures(HomeworkUploadReqVo uploadReqVo) throws Exception;

    /**
     * 删除一条or多条作业【逻辑删除】
     * @param ids：需要删除的作业ID
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean removeByIds(String ids);

    /**
     * 根据作业ID获取作业信息
     * @param workId ：作业ID
     * @return ：作业数据
     */
    HomeworkVo getByWorkId(Long workId);

    /**
     * 删除一条or多条作业【彻底删除】
     * @param ids：需要删除的作业ID
     * @return ：是否成功
     */
    boolean removeHistory(String ids);

    /**
     * 根据学生 ID 获取学生作业
     * @param stuId：学生 ID
     * @return ：学生所有作业
     */
    List<StudentHomeworkDetailDto> getWorkInfoListByStuId(Long stuId);

    //TODO 根据课程id，获取作业id列表。传入 courseidlist ——> 获取作业idlist
    /**
     * 根据课程ID获取作业ID列表
     * @param courseIdList 课程ID列表
     * @return 作业ID列表
     */
    List<Long> getWorkIdsByCourseIds(List<Integer> courseIdList);
}
