package cn.pengan.config;

import cn.pengan.util.DESUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


public class EncryptPropertyPlaceholderConfig extends PropertyPlaceholderConfigurer {
    //需要解密的property name数组
    private final String[] DecryptPropertyName = {"jdbc.username", "jdbc.password"};

    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (isDecrypt(propertyName)) {
            return DESUtils.getDecryptString(propertyValue);
        }
        return propertyValue;
    }

    private boolean isDecrypt(String name) {
        for (String s : DecryptPropertyName) {
            if (s.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
