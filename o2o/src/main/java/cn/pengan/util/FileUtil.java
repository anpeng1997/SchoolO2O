package cn.pengan.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/*
 * 文件工具类
 * */
public class FileUtil {

    //当前系统的文件分隔符
    private static final String separator = System.getProperty("file.separator");

    /*
     * 获取上传图片要存放的Base目录
     * */
    public static String getImgBasePath() {
        //先判断当前计算机系统
        String osName = System.getProperty("os.name");
        String bashPath = "";
        if (osName.toLowerCase().contains("win")) {
            //当前系统为windows系统
            bashPath = "c:/o2o/upload";
        } else {
            //当前系统为apple or linux系统
            bashPath = "/home/o2o/upload";
        }
        bashPath = bashPath.replace("/", separator);
        return bashPath;
    }

    public static String getShopImgPath(long shopId) {
        return "/images/item/shop/" + shopId + "/";
    }

    public static String getHeadLinePath(long headLineId) {
        return "/images/item/headline/" + headLineId + "/";
    }

    public static String getProductImgPath(long productId) {
        return "/images/item/product/" + productId + "/";
    }

    public static String getShopCategoryImgPath(long shopCategoryId) {
        return "/images/item/shopCategory/" + shopCategoryId + "/";
    }

    /*
     * 获取一个随机的文件名
     * */
    public static String getRandomFileName() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }

    /**
     * 删除文件或文件夹
     *
     * @param relativePath 文件目录相对路径
     */
    public static void deleteFileOrDirectory(String relativePath) {
        String wholePath = getImgBasePath() + relativePath;
        File fileOrPath = new File(wholePath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File[] files = fileOrPath.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }
}
