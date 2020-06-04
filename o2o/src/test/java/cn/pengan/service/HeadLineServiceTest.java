package cn.pengan.service;

import cn.pengan.BaseTest;
import cn.pengan.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.List;

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

    @Test
    public void findListTest() {
        HeadLine headLine = new HeadLine();
        headLine.setEnableStatus(1);
        List<HeadLine> headLineList = headLineService.findHeadLineList(headLine);
        headLineList.forEach(System.out::println);
    }
}
