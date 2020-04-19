package cn.pengan.service.impl;

import cn.pengan.annotations.DataOperationLog;
import cn.pengan.dao.IHeadLineDao;
import cn.pengan.entity.HeadLine;
import cn.pengan.service.IHeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class HeadLineServiceImpl implements IHeadLineService {

    private final IHeadLineDao headLineDao;

    public HeadLineServiceImpl(IHeadLineDao headLineDao) {
        this.headLineDao = headLineDao;
    }

    @DataOperationLog("插入了一个Head Line")
    @Override
    public int insertHeadLine(HeadLine headLine) {
        return headLineDao.batchInsertHeadLine(Arrays.asList(headLine));
    }

    @Override
    public List<HeadLine> findHeadLineList(int size) {
        return headLineDao.findHeadLineList(size);
    }

    @DataOperationLog("删除了一个Head Line")
    @Override
    public int deleteHeadLineById(Long lineId) {
        return headLineDao.deleteHeadLineById(lineId);
    }
}
