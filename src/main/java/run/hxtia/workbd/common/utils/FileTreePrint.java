package run.hxtia.workbd.common.utils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

/**
 * 1、打印文件目录
 * 2、https://zhuanlan.zhihu.com/p/103040661
 */
public class FileTreePrint {
    private final static String[] TABS = new String[]{"└── ", "├── ", "│   ", "    "};

    public static String generate(String rootDir) throws Exception {
        return generate(rootDir, new HashSet<>(), true, false);
    }

    public static String generate(String rootDir, Boolean packageCollapse) throws Exception {
        return generate(rootDir, new HashSet<>(), packageCollapse, false);
    }

    public static String generate(String rootDir, Set<String> ignoreDirs) throws Exception {
        return generate(rootDir, ignoreDirs, true, false);
    }

    /**
     * all directories and files of a specific path
     *
     * @param rootDir         root dir
     * @param ignoreDirs      a set of dir to ignore.
     *                        if parent ignored, all children will be ignored too
     * @param packageCollapse if true, empty packages will fold to java package name.
     *                        like IntelliJ IDEA project outline(default, not flatten packages mode)
     *                        default true
     * @param generateFile    if true, file will be generated, otherwise only directory will be generated
     *                        default false
     * @return formatted outline string
     * @throws Exception
     */
    public static String generate(String rootDir, Set<String> ignoreDirs, Boolean packageCollapse, Boolean generateFile) throws Exception {
        if (StringUtils.isBlank(rootDir)) {
            throw new NullPointerException("null root dir!");
        }
        File file = new File(rootDir);
        if (!file.exists()) {
            throw new FileNotFoundException("root dir not exists!");
        }

        if (!file.isDirectory()) {
            throw new IllegalArgumentException("root dir is not a directory!");
        }

        StringBuilder stringBuilder = new StringBuilder(file.getName());

        generateChildren(file, stringBuilder, "", ignoreDirs, packageCollapse, generateFile);

        return stringBuilder.toString();
    }

    /**
     * generate directory structure with recursive function
     *
     * @param file            dir to generate
     * @param stringBuilder   outer string builder for
     * @param level           depth of file or directory which determines how many blanks to display before this file or directory
     * @param ignoreDirs      a set of dir to ignore
     * @param packageCollapse if true, empty packages will fold to java package name
     */
    private static void generateChildren(File file, StringBuilder stringBuilder, String level, Set<String> ignoreDirs, Boolean packageCollapse, Boolean generateFile) {
        File[] childes = file.listFiles();
        if (childes == null) {
            return;
        }
        int lastDirIndex = lastDirIndex(childes, ignoreDirs, generateFile);
        int dirCnt = dirCnt(childes, ignoreDirs, generateFile);

        if (lastDirIndex >= 0) {
            if (dirCnt == 1) {
                if (packageCollapse && childes[lastDirIndex].isDirectory()) {
                    stringBuilder.append(".").append(childes[lastDirIndex].getName());
                    generateChildren(childes[lastDirIndex], stringBuilder, level, ignoreDirs, packageCollapse, generateFile);
                    return;
                } else {
                    stringBuilder.append("\n").append(level).append(TABS[0]).append(childes[lastDirIndex].getName());
                    generateChildren(childes[lastDirIndex], stringBuilder, level + TABS[3], ignoreDirs, packageCollapse, generateFile);
                    return;
                }
            }

            for (int i = 0; i < childes.length; i++) {
                if ((childes[i].isDirectory() && !ignoreDirs.contains(childes[i].getName())) || generateFile) {
                    stringBuilder.append("\n").append(level);
                    if (i == lastDirIndex) {
                        stringBuilder.append(TABS[0]).append(childes[i].getName());
                        generateChildren(childes[i], stringBuilder, level + (TABS[3]), ignoreDirs, packageCollapse, generateFile);
                    } else {
                        stringBuilder.append(TABS[1]).append(childes[i].getName());
                        generateChildren(childes[i], stringBuilder, level + (TABS[2]), ignoreDirs, packageCollapse, generateFile);
                    }
                }
            }
        }
    }

    private static int dirCnt(File[] childes, Set<String> ignoreDirs, Boolean generateFile) {
        int cnt = 0;

        if (childes == null || childes.length == 0) {
            return cnt;
        }

        for (int i = 0; i < childes.length; i++) {
            if ((childes[i].isDirectory() && !ignoreDirs.contains(childes[i].getName())) || generateFile) {
                cnt++;
            }
        }
        return cnt;
    }

    private static int lastDirIndex(File[] childes, Set<String> ignoreDirs, Boolean generateFile) {
        int index = -1;

        if (childes == null || childes.length == 0) {
            return index;
        }
        for (int i = 0; i < childes.length; i++) {
            if ((childes[i].isDirectory() && !ignoreDirs.contains(childes[i].getName())) || generateFile) {
                index = i;
            }
        }
        return index;
    }

}

