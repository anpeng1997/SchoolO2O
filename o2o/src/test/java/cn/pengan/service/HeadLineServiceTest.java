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
        File file = new File("C:\\Users\\pengan\\Desktop\\headtitle_47\\headtitle\\2017061320315746624.jpg");
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
