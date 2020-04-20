package cn.pengan.service;

import cn.pengan.entity.HeadLine;

import java.util.List;

public interface IHeadLineService {

    int insertHeadLine(HeadLine headLine);

    List<HeadLine> findHeadLineList(int size);

    int deleteHeadLineById(Long lineId);
}
