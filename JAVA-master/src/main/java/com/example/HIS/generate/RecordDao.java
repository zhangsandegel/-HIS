package com.example.HIS.generate;

import com.example.HIS.models.Record;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordDao {
    int deleteByPrimaryKey(Integer recordId);

    int insert(Record record);

    int insertSelective(Record record);

    Record selectByPrimaryKey(Integer recordId);

    int updateByPrimaryKeySelective(Record record);

    int updateByPrimaryKey(Record record);
}