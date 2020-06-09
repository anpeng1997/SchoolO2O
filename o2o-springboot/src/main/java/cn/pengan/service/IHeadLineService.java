package cn.pengan.service;

import cn.pengan.entity.HeadLine;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface IHeadLineService {

    int insertHeadLine(HeadLine headLine, InputStream inputStream, String fileName) throws IOException;

    List<HeadLine> findHeadLineList(HeadLine headLineCondition);

    int deleteHeadLineById(Long lineId);
}
