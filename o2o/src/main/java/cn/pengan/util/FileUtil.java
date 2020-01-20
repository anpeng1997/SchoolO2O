package cn.pengan.util;

import java.util.UUID;

/*
 * 文件工具类
 * */
public class FileUtil {

    //当前系统的文件分隔符
    private static final String separator = System.getProperty("file.separator");

    /*
     * 获取上传图片要存放的目录
     * */
    public static String getImgBasePath() {
        //先判断当前计算机系统
        String osName = System.getProperty("os.name");
        String bashPath = "";
        if (osName.toLowerCase().contains("windows")) {
            //当前系统为windows系统
            bashPath = "c:/o2o/upload/";
        } else {
            //当前系统为apple or linux系统
            bashPath = "/home/o2o/upload/";
        }
        bashPath = bashPath.replace("/", separator);
        return bashPath;
    }

    public static String getShopImgPath(long shopId) {
        String path = "/images/item/shop/" + shopId + "/";
        String newPath = path.replace("/", separator);
        return newPath;
    }

    /*
     * 获取一个随机的文件名
     * */
    public static String getRandomFileName() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
}
