package com.example.HIS.generate;

import com.example.HIS.models.ReserveTable;
import com.example.HIS.models.ViolateTable;
import com.example.HIS.tool.ReserveHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReserveTableDao {
    int deleteByPrimaryKey(Integer reserveId);

    int insert(ReserveTable record);

    int insertSelective(ReserveTable record);

    ReserveTable selectByPrimaryKey(Integer reserveId);

    int updateByPrimaryKeySelective(ReserveTable record);

    int updateByPrimaryKey(ReserveTable record);
    int updateByPrimaryKey(ViolateTable record);

    List<ReserveTable> selectViolateByPatientId(String patientId);

    List<ReserveHistory> selectByPatientId(String patientIdentity);
}