package run.hxtia.workbd.common.upload;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import run.hxtia.workbd.common.prop.WorkBoardProperties;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.Strings;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * 1、文件上传
 * 2、支持多文件上传、编辑
 * 3、并且会同步删除无用的文件
 */
@Slf4j
public class Uploads {

    // 拿到读取到配置里的Upload对象【用来获取文件目录】
    private static final WorkBoardProperties.Upload UPLOAD;
    static {
        UPLOAD = WorkBoardProperties.getProperties().getUpload();
    }

    /**
     * 单图片上传
     * @param multipartFile：图片数据
     * @return ：保存文件的相对路径【upload/....】
     */
    public static String uploadImage(MultipartFile multipartFile) throws Exception {
        // 获取图片相对目录
        String relativeDir = UPLOAD.getImageDir();
        return uploadFile(multipartFile, relativeDir);
    }

    // 多图片上传
    public static String uploadImages(List<MultipartFile> multipartFiles) throws Exception {
        if (CollectionUtils.isEmpty(multipartFiles)) return "";
        // 获取图片相对目录
        String relativeDir = UPLOAD.getImageDir();

        // 最终保存的图片字符串
        StringBuilder builder = new StringBuilder();
        // 遍历图片数据，挨个保存
        for (MultipartFile multipartFile : multipartFiles) {
            String doneRelativePath = uploadFile(multipartFile, relativeDir);
            builder.append(doneRelativePath).append(",");
        }
        // 将最后一个“ ，” 去掉
        if (builder.length() > 0)
        builder.replace(builder.length() - 1, builder.length(), "");
        return builder.toString();
    }

    /**
     * 多图片编辑
     * @param uploadParam ：【新文件数据 + 对标索引 + 数据库以前存的路径】
     * @return ：【新上传的文件路径 + 需要删除的文件路径 + 保存数据库的路径】
     */
    public static UploadEditResult uploadEditImages(UploadEditParam uploadParam) throws Exception {

        List<MultipartFile> newFiles = uploadParam.getNewFiles();
        List<Integer> matchIndex = uploadParam.getMatchIndex();
        String oldFilesPath = uploadParam.getOldFilesPath();

        // 原先的相册路径
        String[] oldAlbumPath = oldFilesPath.split(",");

        // 新保存的文件路径
        String filePath;

        // 原先需要删除的文件路径
        StringBuilder deletePath = new StringBuilder();

        // 原先图片删减后的相册路径
        StringBuilder newFilePathBuilder = new StringBuilder();

        // 遍历去掉要删除的文件 和要保存的文件
        for (int i = 0; i < oldAlbumPath.length; i++) {
            // 若对标数组为1，那么说明原图片被编辑了
            if (matchIndex.get(i) == Constants.Status.MATCH_INDEX_EDIT) {
                if (i == 0) {
                    deletePath.append(oldAlbumPath[i]);
                } else {
                    deletePath.append(",").append(oldAlbumPath[i]);
                }
                continue;
            }

            // 拼接删减后的路径
            if (oldAlbumPath[i].length() != 0) {
                newFilePathBuilder.append(oldAlbumPath[i]).append(",");
            }
        }

        // 如果删减后的图片路径字符串
        String newFilePath = newFilePathBuilder.toString();

        // 保存新的图片到服务器
        String newUploadFilePath = uploadImages(newFiles);
        filePath = newUploadFilePath;

        // 判断以前是否有图片【】
        if (StringUtils.hasLength(newFilePath)) {
            // 判断有没有新传的图片
            if (StringUtils.hasLength(filePath)) {
                filePath = newFilePath + filePath;
            } else {
                // 来到这说明没有新传，是以前的图片【去掉最后一个逗号】
                filePath = newFilePath.substring(0, newFilePath.length() - 1);
            }
        }

        // 构建需要返回的结果
        return new UploadEditResult(filePath,
                deletePath.toString(), newUploadFilePath);

    }


    // 单视频上传
    public static String uploadVideo(MultipartFile multipartFile) throws Exception {
        // 获取图片相对目录
        String relativeDir = UPLOAD.getVideoDir();
        return uploadFile(multipartFile, relativeDir);
    }

    /**
     * 上传文件
     * @param multipartFile : 文件数据
     * @param dir ： 保存的目录
     * @return ： 保存文件的相对路径【upload/....】
     */
    private static String uploadFile(MultipartFile multipartFile, String dir) throws Exception {
        if (multipartFile == null || multipartFile.getSize() <= 0) return null;

        // 文件扩展名
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        // 文件名
        String fileName = Strings.getUUID(16) + "." + extension;

        // 文件相对路径【upload/....】
        String relativePath = dir + fileName;

        // 文件绝对路径【D：crm/....】
        String fullPath = UPLOAD.getBasePath() + relativePath;

        // 创建文件对象
        File file = new File(fullPath);

        // 如果目录不存在，强制创建目录
        FileUtils.forceMkdirParent(file);

        // 剪切图片数据到刚刚的目录去
        multipartFile.transferTo(file);

        // 将相对路径返回，用做保存数据库的字符串
        return relativePath;
    }

    /**
     * / 根据文件路径删除文件
     * @param relativePath :文件相对路径
     */
    public static void deleteFile(String relativePath) throws Exception {
        if (!StringUtils.hasLength(relativePath)) return;

        // 拿到文件绝对路径
        String fullPath = UPLOAD.getBasePath() + relativePath;
        File file = new File(fullPath);

        // 如果是目录，就不删除
        if (file.isDirectory()) return;

        // 来到这说明要删除
        FileUtils.forceDelete(file);
    }

    /**
     * 多文件的删除
     * @param filePath：数据库里的路径逗号隔开的
     */
    public static void deleteFiles(String filePath) throws Exception {
        if (!StringUtils.hasLength(filePath)) return;

        String[] relativeFiles = filePath.split(",");

        for (String relativeFile : relativeFiles) {
            deleteFile(relativeFile);
        }

    }

    /**
     * 上传图片 并且 保存到数据库
     * @param po：对应的 PO 对象
     * @param baseMapper：对应的 Mapper
     * @param setName：需要设置文件路径的字段【利用方法引用】 PO::setXXX
     * @param <PO>：数据库对象
     * @return ：是否成功
     */
    public static <PO> boolean uploadOneWithPo(
        PO po, UploadReqParam reqParam, BaseMapper<PO> baseMapper, BiConsumer<PO, String> setName) throws Exception {

        // 上传文件
        String filePath = "";
        MultipartFile file = reqParam.getFile();
        if (file != null) {
            // 上传到磁盘，并且返回文件路径，用于存库
            filePath = uploadImage(file);
            if (StringUtils.hasLength(filePath))
                // 调用 set 方法
                setName.accept(po, filePath);
        }

        try {
            // 编辑信息
            boolean res = baseMapper.updateById(po) > 0;
            if (res) {
                // 说明成功了，删除以前的文件
                deleteFile(reqParam.getOriginUri());
            }
            return res;
        } catch (Exception e) {
            // 如果在上传的时候出现异常，将刚刚上传成功的文件删掉
            deleteFile(filePath);
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 多图片编辑 并且保存到数据库
     * @param po ：对应的 PO 对象
     * @param editParam ：编辑所需参数
     * @param baseMapper ：对应的 Mapper
     * @param setName：需要设置文件路径的字段【利用方法引用】 PO::setXXX
     * @param <PO> 数据库对象
     * @return ：是否成功
     */
    public static <PO> boolean uploadMoreWithPo(
        PO po, UploadEditParam editParam, BaseMapper<PO> baseMapper, BiConsumer<PO, String> setName) throws Exception {

        // 上传文件
        UploadEditResult editResult = uploadEditImages(editParam);
        // 调用 set 方法
        setName.accept(po, editResult.getFilePath());
        try {
            // 编辑信息
            boolean res = baseMapper.updateById(po) > 0;
            if (res) {
                // 说明成功了，删除需要删除的文件【以前编辑过的图片】
                deleteFiles(editResult.getNeedDeletePath());
            }
            return res;
        } catch (Exception e) {
            // 如果在上传的时候出现异常，将刚刚上传成功的文件删掉
            deleteFiles(editResult.getNewUploadFilePath());
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }

}
