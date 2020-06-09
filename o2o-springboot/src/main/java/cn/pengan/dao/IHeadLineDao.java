package cn.pengan.dao;

import cn.pengan.entity.HeadLine;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHeadLineDao {

    int batchInsertHeadLine(List<HeadLine> headLines);

    List<HeadLine> findHeadLineList(HeadLine headLineCondition);

    int deleteHeadLineById(Long lineId);

    int updateHeadLine(HeadLine headLine);
}
