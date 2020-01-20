package cn.pengan.util;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;

public class ImageUtil {

    /*
    * 生成图片的缩略图，并返回缩略图存放的相对路径
    * */
    public static String generateThumbnail(File thumbnail, String targetAddr) {
        String basePath = FileUtil.getImgBasePath();
        String fileName = FileUtil.getRandomFileName();
        String suffix = getFileSuffix(thumbnail.getName());
        mkdirFolder(basePath + targetAddr);
        String relativeAddr =  targetAddr + fileName + suffix;
        File file = new File(basePath + relativeAddr);
        try {
            Thumbnails.of(thumbnail).size(200, 200).outputQuality(0.5f).toFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /*
     * 判断文件夹是否存在，不存在则创建
     * */
    public static void mkdirFolder(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static String getFileSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        String suffix = fileName.substring(index);
        return suffix;
    }
}
