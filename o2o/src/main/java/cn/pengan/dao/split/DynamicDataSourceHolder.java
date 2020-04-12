package cn.pengan.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 获取不同的数据源类型
 */
public class DynamicDataSourceHolder {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceHolder.class);
    //ThreadLocal是线程安全的
    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();
    public static final String MASTER_DATASOURCE = "master";
    public static final String SLAVE_DATASOURCE = "slave";

    public static String getDBType() {
        String type = contextHolder.get();
        if (type == null) {
            //默认使用master库
            return MASTER_DATASOURCE;
        }
        return type;
    }

    public static void setDBType(String dbTypeStr) {
        logger.debug("设置DBType为：{}", dbTypeStr);
        contextHolder.set(dbTypeStr);
    }

    public static void clearDBType() {
        contextHolder.remove();
    }
}
