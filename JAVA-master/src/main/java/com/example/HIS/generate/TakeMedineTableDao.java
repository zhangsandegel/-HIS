package com.example.HIS.generate;

import com.example.HIS.models.TakeMedineTable;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TakeMedineTableDao {
    int deleteByPrimaryKey(Integer takeId);

    int insert(TakeMedineTable record);

    int insertSelective(TakeMedineTable record);

    TakeMedineTable selectByPrimaryKey(Integer takeId);

    int updateByPrimaryKeySelective(TakeMedineTable record);

    int updateByPrimaryKey(TakeMedineTable record);
}