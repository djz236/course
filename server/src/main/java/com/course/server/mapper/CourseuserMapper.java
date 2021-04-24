package com.course.server.mapper;

import com.course.server.domain.Courseuser;
import com.course.server.domain.CourseuserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseuserMapper {
    long countByExample(CourseuserExample example);

    int deleteByExample(CourseuserExample example);

    int deleteByPrimaryKey(String id);

    int insert(Courseuser record);

    int insertSelective(Courseuser record);

    List<Courseuser> selectByExample(CourseuserExample example);

    Courseuser selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Courseuser record, @Param("example") CourseuserExample example);

    int updateByExample(@Param("record") Courseuser record, @Param("example") CourseuserExample example);

    int updateByPrimaryKeySelective(Courseuser record);

    int updateByPrimaryKey(Courseuser record);
}