package cn.pengan.util;

import org.junit.Test;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class utilTest {

    @Test
    public void test1() {
//        String name = System.getProperty("os.name");
//        System.out.println(name.toLowerCase().contains("win"));
//        String s = System.lineSeparator();
//        String property = System.getProperty("file.separator");
//        System.out.println(property);

//        String s = UUID.randomUUID().toString();
//        System.out.println(s);
//        s= s.replace("-","");
//        System.out.println(s);
    }

    @Test
    public void test2() {
        String s = DigestUtils.md5DigestAsHex("123123".getBytes());
        System.out.println(s);
    }

    @Test
    public void test3() {
        Integer i = null;
        int i2= (int)i;
        System.out.println(i2);
    }

}
