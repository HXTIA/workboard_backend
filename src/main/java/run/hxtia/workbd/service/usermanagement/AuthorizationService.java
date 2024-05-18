package run.hxtia.workbd.service.usermanagement;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.pojo.po.Authorization;
import run.hxtia.workbd.pojo.vo.usermanagement.request.AuthCourseAndClassIdReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.AuthCourseAndClassInfoReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.AuthorizationReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.AuthorizationVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.CourseAndClassVo;

/**
 * @author Xiaojin
 * @date 2024/5/14
 */

public interface AuthorizationService extends IService<Authorization> {



    // 保存授权信息
    /**
     * 保存授权信息
     * @param authorizationReqVo ：授权信息
     * @return ：是否成功
     */
    boolean save(AuthorizationReqVo authorizationReqVo);

    // 查询授权信息
    /**
     * 查询授权信息
     * @param permission ：权限信息
     * @return ：授权信息
     */
    AuthorizationVo getAuthorizationByPermission(String permission);


    // 根据第投权的列表(课程、班级)
    /**
     * 认证登录信息
     * @param token：认证参数
     * @return ：用户信息
     */
    CourseAndClassVo getCourseAndClasslistByAuth(String token);

    // 根据用户选择的授权的集合(课程id列表 和 班级id 列表)生成code
    // 根据用户选择的授权集合生成 code 并存储在 Redis 中

    /**
     * 通过课程id和班级id，生成授权码
     *
     * @param reqVo ：课程id和班级id
     * @param token
     * @return ：授权码
     */
    String generateSelectionCode(AuthCourseAndClassIdReqVo reqVo, String token);

    /**
     * 通过课程信息和班级信息，生成授权码
     *
     * @param reqVo ：课程信息和班级信息
     * @param token
     * @return ：授权码
     */
    String generateCodeByCourseAndClassInfo(AuthCourseAndClassInfoReqVo reqVo, String token);

    /**
     * 核销code，根据code获取授权码信息
     * @param code : 授权码
     * @return ：授权信息
     */
    @Transactional(readOnly = false)
    CourseAndClassVo getCourseAndClassByCode(String code, String token);

    /*
    一开始一个管理员只生成一个code，我可以直接把code set进Redis。也就是 key-value 弄 code——courseAndClass
       这样code和信息绑定了，还需要获取code的，就 user_id ——code。
       这样子类似于token的方式。
     */

    /*
    但是，后面写完后，发现一个管理员可以生成多个code，这种方式就不行。
    每个code可以绑定courseAndClass在没问题
    code1——courseAndClass1
    code2——courseAndClass2
    在redis中可以存，但是 user_id - code 这样的方式就不行了。
    user_id -code1 ，然后下一个code2会覆盖掉code1，变成了 user_id - code1
     */

    /*
    然后后面我想用哈希表存，tableName（user_id） - code - courseAndClass
    这样弄了弄，有想了想，wc。一个用户一张表，不行。
    然后又想 tableName（all） - tableName（user_id） - code - courseAndClass
    OK，试试。后面发现，这样也不行，code的粒度没了，无法给code设置过期时间了。变成哈希表了有过期时间了。而且redis不直接支持嵌套
     */

    /**
     * 所以最后想要维护code，应该使用数据库，而不是redis。redis中的code只是一个中间变量，用来存储code和courseAndClass的关系。
     */

}
