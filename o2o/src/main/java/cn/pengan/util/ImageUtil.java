package cn.pengan.util;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {

    /**
     * 生成图片的缩略图，并返回缩略图存放的相对路径
     *
     * @param fileInputStream
     * @param targetAddr
     * @param fileName
     * @return
     */
    public static String generateThumbnail(InputStream fileInputStream, String targetAddr, String fileName) {
        String basePath = FileUtil.getImgBasePath();
        String randomFileName = FileUtil.getRandomFileName();
        String suffix = getFileSuffix(fileName);
        mkdirFolder(basePath + targetAddr);
        String relativeAddr = targetAddr + randomFileName + suffix;
        File thumbnailFile = new File(basePath + relativeAddr);
        try {
            Thumbnails.of(fileInputStream).size(200, 200).outputQuality(0.5f).toFile(thumbnailFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * 保存图片
     *
     * @param shopId
     * @return
     * @throws IOException
     */
    public static String saveShopImg(long shopId, InputStream fileInputStream, String fileName) throws IOException {
        //先获取shop图片的相对路径
        String relativePath = FileUtil.getShopImgPath(shopId);
        //生成缩略图后返回（相对路径加上文件名）
        String path = generateThumbnail(fileInputStream, relativePath, fileName);
        return path;
    }

    /**
     * 判断文件夹是否存在，不存在则创建
     */
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
