package cn.pengan.service.impl;

import cn.pengan.dao.IHeadLineDao;
import cn.pengan.entity.HeadLine;
import cn.pengan.service.IHeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class IHeadLineServiceImpl implements IHeadLineService {

    private final IHeadLineDao headLineDao;

    public IHeadLineServiceImpl(IHeadLineDao headLineDao) {
        this.headLineDao = headLineDao;
    }

    @Override
    public int insertHeadLine(HeadLine headLine) {
        return headLineDao.batchInsertHeadLine(Arrays.asList(headLine));
    }

    @Override
    public List<HeadLine> findHeadLineList(int size) {
        return headLineDao.findHeadLineList(size);
    }

    @Override
    public int deleteHeadLineById(Long lineId) {
        return headLineDao.deleteHeadLineById(lineId);
    }
}
