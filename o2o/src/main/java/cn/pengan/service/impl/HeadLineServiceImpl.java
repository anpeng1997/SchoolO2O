package cn.pengan.service.impl;

import cn.pengan.annotations.DataOperationLog;
import cn.pengan.dao.IHeadLineDao;
import cn.pengan.entity.HeadLine;
import cn.pengan.service.IHeadLineService;
import cn.pengan.util.ImageUtil;
import com.google.common.base.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class HeadLineServiceImpl implements IHeadLineService {

    private final IHeadLineDao headLineDao;

    public HeadLineServiceImpl(IHeadLineDao headLineDao) {
        this.headLineDao = headLineDao;
    }

    @DataOperationLog("插入了一个Head Line")
    @Override
    public int insertHeadLine(HeadLine headLine, InputStream inputStream, String fileName) throws IOException {
        //初始化数据
        headLine.setCreateTime(new Date());
        headLine.setLastEditTime(new Date());
        headLine.setEnableStatus(1);
        int var1 = headLineDao.batchInsertHeadLine(Arrays.asList(headLine));
        if (var1 <= 0) {
            return var1;
        }
        //保存图片至本地
        String relativePath = ImageUtil.saveHeadLineImg(headLine.getLineId(), inputStream, fileName);
        headLine.setLineImg(relativePath);
        //更新图片
        return headLineDao.updateHeadLine(headLine);
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
