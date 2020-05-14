package cn.pengan.dao;

import cn.pengan.entity.HeadLine;

import java.util.List;

public interface IHeadLineDao {

    int batchInsertHeadLine(List<HeadLine> headLines);

    List<HeadLine> findHeadLineList(int size);

    int deleteHeadLineById(Long lineId);

    int updateHeadLine(HeadLine headLine);
}
