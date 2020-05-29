package cn.pengan.util;

import org.junit.Test;
import org.springframework.util.DigestUtils;
import org.springframework.util.SocketUtils;

import javax.sound.midi.Soundbank;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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
        int i2 = (int) i;
        System.out.println(i2);
    }

    @Test
    public void test4() {
        int[] numbers = {9977, 9988, 99};
        String[] strings = Arrays.stream(numbers).mapToObj(s -> String.valueOf(s)).toArray(String[]::new);
        for (int i = 0; i < strings.length - 1; i++) {
            for (int j = 0; j < strings.length - i - 1; j++) {
                String var1 = strings[j];
                String var2 = strings[j + 1];
                String var1First = var1.substring(0, 1);
                String var2First = var2.substring(0, 1);
                boolean bool = false;
                if (Integer.parseInt(var1First) == Integer.parseInt(var2First)) {
                    int var1Len = var1.length();
                    int var2Len = var2.length();
                    if (var1Len != 1 && var2Len != 1) {
                        int minLen = var1Len < var2Len ? var1Len : var2Len;
                        for (int m = 1; m < minLen; m++) {
                            String sub1 = var1.substring(m, m + 1);
                            String sub2 = var2.substring(m, m + 1);
                            if (Integer.parseInt(sub1) < Integer.parseInt(sub2)) {
                                bool = true;
                                break;
                            }
                            if (Integer.parseInt(sub1) > Integer.parseInt(sub2)) {
                                bool = false;
                                break;
                            }
                        }
                    }
                } else {
                    bool = Integer.parseInt(var1First) < Integer.parseInt(var2First);
                }
                if (bool) {
                    String temp = strings[j + 1];
                    strings[j + 1] = strings[j];
                    strings[j] = temp;
                }
            }
        }
        Arrays.stream(strings).forEach(System.out::print);
    }

    @Test
    public void test5() {
        long l = System.currentTimeMillis();
        int[] numbers = {123, 321, 8888, 78979,454565497,78361984,576456};
        StringBuilder build = new StringBuilder();
        for (String i : Arrays.stream(numbers).boxed().map(String::valueOf).sorted((n1, n2) -> (n2 + n1).compareTo(n1 + n2)).toArray(String[]::new)
        ) {
            build.append(i);
        }
        long l1 = System.currentTimeMillis();

        if ("0".equals(build.toString().substring(0, 1))) {
            System.out.println("0");
        } else {
            System.out.println(build.toString());
        }
        System.out.println(l1 - l);
    }

}
