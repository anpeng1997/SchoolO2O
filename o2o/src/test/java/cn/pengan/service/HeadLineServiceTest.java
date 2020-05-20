package cn.pengan.service;

import cn.pengan.BaseTest;
import cn.pengan.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;

public class HeadLineServiceTest extends BaseTest {
    @Autowired
    private IHeadLineService headLineService;

    @Test
    public void insertTest() throws IOException {
        File file = new File("E:\\MyFile\\资料\\java\\校园商铺\\网站用到的图片集\\images\\item\\headtitle\\2017061320400198256.jpg");
        HeadLine headLine = new HeadLine();
        headLine.setLineName("test");
        headLine.setLineLink("/test/test");
        headLine.setPriority(10);
        InputStream inputStream = new FileInputStream(file);
        int i = headLineService.insertHeadLine(headLine, inputStream, file.getName());
        inputStream.close();
        System.out.println(headLine);
    }
}
