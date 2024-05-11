package run.hxtia.workbd.service.usermanagement.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.httpclient.HttpClient;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.upload.UploadReqParam;
import run.hxtia.workbd.common.upload.Uploads;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.MiniApps;
import run.hxtia.workbd.common.util.Strings;
import run.hxtia.workbd.mapper.StudentMapper;
import run.hxtia.workbd.pojo.dto.StudentInfoDto;
import run.hxtia.workbd.pojo.po.Student;
import run.hxtia.workbd.pojo.vo.notificationwork.response.StudentVo;
import run.hxtia.workbd.pojo.vo.organization.response.ClassVo;
import run.hxtia.workbd.pojo.vo.organization.response.CollegeVo;
import run.hxtia.workbd.pojo.vo.organization.response.GradeVo;
import run.hxtia.workbd.pojo.vo.organization.response.OrganizationVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.StudentAvatarReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.StudentReqVo;
import run.hxtia.workbd.pojo.vo.common.response.WxAccessTokenVo;
import run.hxtia.workbd.pojo.vo.common.response.WxCodeMsg;
import run.hxtia.workbd.pojo.vo.common.response.WxTokenVo;
import run.hxtia.workbd.pojo.vo.common.response.result.CodeMsg;
import run.hxtia.workbd.service.organization.ClassService;
import run.hxtia.workbd.service.organization.CollegeService;
import run.hxtia.workbd.service.organization.GradeService;
import run.hxtia.workbd.service.usermanagement.StudentService;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    private final Redises redises;

    @Autowired
    private StudentMapper studentMapper;


    /**
     * 根据 code验证码换取 session_key + openId
     * @param code：验证码
     * @return ：session_key + openId
     */
    @Override
    public String getToken(String code) throws Exception {

        String url = MiniApps.buildGetTokenUrl(code);
        String resStr = HttpClient.httpGetRequest(url);
        WxTokenVo wxTokenVo = WxTokenVo.parseFromJson(resStr);
        if (wxTokenVo == null){
            JsonVos.raise("得到的结果为 Null, URL = " + url);
        }

        // TODO：增加默认值，还是使用 errCode 判断
        if (!StringUtils.hasLength(wxTokenVo.getOpenid())) {
            JsonVos.raise("请求出错：code = " + wxTokenVo.getErrcode() + ", msg = " + wxTokenVo.getErrmsg());
        }

        // 来到这里说明得到了结果
        String token = Strings.getUUID();
        redises.set(Constants.WxMiniApp.WX_PREFIX, token, wxTokenVo, Constants.Date.EXPIRE_DATS, TimeUnit.DAYS);

        return  token;
    }

    /**
     * 验证 Token 是否有效
     * @param token token
     * @return 是否有效
     */
    public Boolean checkToken(String token) throws Exception {

        // 获取 Token 的信息
        WxTokenVo wxTokenVo = MiniApps.getWxTokenVo(token);
        if (wxTokenVo == null) {
            JsonVos.raise(CodeMsg.TOKEN_EXPIRED);
        }

        // 先从缓存获取 Access Token
        WxAccessTokenVo wxAccessTokenVo = (WxAccessTokenVo) redises.get(Constants.WxMiniApp.WX_AT_PREFIX, token);

        if (wxAccessTokenVo == null) {
            // 说明缓存没有，发请求获取
            String atUrl = MiniApps.buildGetWXAccessTokenUrl();
            String atRespStr = HttpClient.httpGetRequest(atUrl);
            wxAccessTokenVo = WxAccessTokenVo.parseFromJson(atRespStr);
            if (!StringUtils.hasLength(wxAccessTokenVo.getAccessToken())) {
                JsonVos.raise(CodeMsg.GET_ACCESS_TOKEN_ERR);
            }

            // 存入缓存中
            redises.set(Constants.WxMiniApp.WX_AT_PREFIX, token, wxAccessTokenVo.getAccessToken(), wxAccessTokenVo.getExpiresIn(), TimeUnit.SECONDS);
        }

        // 构建 TCheck Token 的 URL
        String url = buildCheckSessionUrl(wxTokenVo, wxAccessTokenVo.getAccessToken());
        String resp = HttpClient.httpGetRequest(url);
        WxCodeMsg wxCodeMsg = WxCodeMsg.parseFromJson(resp);
        if (wxCodeMsg == null) {
            JsonVos.raise(CodeMsg.TOKEN_EXPIRED);
        }

        return  true;

    }

    /**
     * 获取用户信息
     * @param token：获取的 Token
     * @return ：用户信息
     */
    @Override
    public StudentInfoDto getStudentByToken(String token) {


        String openId = MiniApps.getOpenId(token);

        // 从redis中读取 user 信息
        StudentInfoDto studentInfoDto = (StudentInfoDto) redises.get(Constants.WxMiniApp.WX_USER, openId);

        // 检查 redis中有无用户
        if (studentInfoDto != null) return studentInfoDto;

        // 先去数据库查询
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getWechatId, openId);
        Student studentPo = baseMapper.selectOne(wrapper);
        studentInfoDto = new StudentInfoDto();

        if (studentPo != null) {
            studentInfoDto.setStudentVo(MapStructs.INSTANCE.po2vo(studentPo));
        } else {
            // 来到这里说明用户是第一次授权，需要注册
            studentInfoDto.setStudentVo(MapStructs.INSTANCE.po2vo(registerUser(openId)));
        }

        // 将最新消息缓存到 redis
        redises.set(Constants.WxMiniApp.WX_USER, openId, studentInfoDto, Constants.Date.WX_STUDENT_EXPIRE_DATS, TimeUnit.DAYS);
        return studentInfoDto;
    }

    /**
     * 注册用户【存开发者服务器】
     * @param wechatId：
     * @return ：是否成功。
     */
    private Student registerUser(String wechatId) {
        Student po = new Student();
        po.setWechatId(wechatId);
        po.setNickname(Strings.getUUID(10));
        // TODO：设置默认的头像
        po.setAvatarUrl("");
        if (baseMapper.insert(po) <= 0) {
            return JsonVos.raise(CodeMsg.AUTHORIZED_ERROR);
        }
        return po;
    }

    /**
     * 完善用户信息
     * @param reqVo：用户信息
     * @return ：是否成功
     */
    @Override
    public boolean update(StudentReqVo reqVo) {
        Student po = MapStructs.INSTANCE.reqVo2po(reqVo);
        // TODO：判断组织是否存在
//        if (!orgService.isExist(reqVo.getOrgId())) {
//            return JsonVos.raise(CodeMsg.NO_ORG_INFO);
//        }

        return baseMapper.updateById(po) > 0;
    }

    /**
     * 用户上传头像
     * @param reqVo：头像数据
     * @return ：是否成功
     */
    @Override
    public boolean update(StudentAvatarReqVo reqVo) throws Exception {
        Student po = new Student();
        po.setId(reqVo.getId());
        return Uploads.uploadOneWithPo(po,
            new UploadReqParam(reqVo.getAvatarUrl(),
                reqVo.getAvatarFile()),
            baseMapper, Student::setAvatarUrl);
    }

    @Override
    public OrganizationVo getOrganizationDetailsByStudentId(Long studentId) throws Exception {
        // 直接通过Mapper方法获取所有信息
        OrganizationVo organizationVo = studentMapper.getOrganizationDetailsByStudentId(studentId);

        // 检查是否所有必需的信息都被获取
        if (organizationVo == null || organizationVo.getCollege() == null ||
            organizationVo.getGrade() == null || organizationVo.getClassVo() == null) {
            throw new Exception("One or more essential details are missing for student ID: " + studentId);
        }

        return organizationVo;
    }

    @Override
    public StudentVo getStudentById(Long studentId) throws Exception {
        Student student = baseMapper.selectById(studentId);
        if (student == null) {
            throw new Exception("Student not found with ID: " + studentId);
        }
        return MapStructs.INSTANCE.po2vo(student);
    }


    /**
     * 根据 wxTokenVo 构建验证登录状态的 URL
     * @param wxTokenVo Token 缓存的信息
     * @return 请求 url
     */
    private String buildCheckSessionUrl(WxTokenVo wxTokenVo, String accessToken) {

        // 创建 HmacUtils 实例，指定算法和密钥
        HmacUtils hmacUtils = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, wxTokenVo.getSessionKey());
        // 对空字符串进行签名，并返回十六进制字符串形式的结果
        String signature = hmacUtils.hmacHex("");


        StringBuilder url = new StringBuilder(Constants.WxApp.PREFIX);
        url.append("?access_token=").append(accessToken);
        url.append("&openid=").append(wxTokenVo.getOpenid());
        url.append("&signature=").append(signature);
        url.append("&sig_method=").append("hmac_sha256");

        return url.toString();
    }

}
