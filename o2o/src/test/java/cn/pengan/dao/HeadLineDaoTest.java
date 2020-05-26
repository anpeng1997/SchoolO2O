package cn.pengan.dao;

import cn.pengan.BaseTest;
import cn.pengan.entity.HeadLine;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HeadLineDaoTest extends BaseTest {

    @Autowired
    private IHeadLineDao headLineDao;

    @Test
    public void a_InsertHeadLine() {
        HeadLine headLine = new HeadLine();
        headLine.setCreateTime(new Date());
        headLine.setEnableStatus(1);
        headLine.setLastEditTime(new Date());
        headLine.setLineImg("/test/test");
        headLine.setLineLink("/test");
        headLine.setLineName("test head line");
        headLine.setPriority(1);
        List<HeadLine> headLines = Collections.nCopies(5, headLine);
        int i = headLineDao.batchInsertHeadLine(headLines);
        Assert.assertEquals(5, i);
    }

    @Test
    public void b_FindHeadLineList() {
        HeadLine headLine = new HeadLine();
        List<HeadLine> headLineList = headLineDao.findHeadLineList(headLine);
        headLineList.forEach(System.out::println);
        Assert.assertEquals(5, headLineList.size());
    }

    @Test
    public void c_DeleteHeadLine() {
        int i = headLineDao.deleteHeadLineById(1L);
        Assert.assertEquals(1, i);
    }
}
