package com.example.HIS.generate;

import com.example.HIS.models.RemainDoctorNumber;
import com.example.HIS.tool.DoctorRemain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface RemainDoctorNumberDao {

    List<DoctorRemain> getAllDoctorRemain();

    int insert(RemainDoctorNumber record);

    int insertSelective(RemainDoctorNumber record);

    List<RemainDoctorNumber> selectByDoctorID(String doctorId);

    void updateByPrimaryKey(RemainDoctorNumber remainDoctorNumber);
}
