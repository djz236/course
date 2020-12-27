package com.course.server.mapper;

import com.course.server.domain.Test;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestMapper {
    long countByExample(Test example);

    int deleteByExample(Test example);

    int insert(Test record);

    int insertSelective(Test record);

    List<Test> selectByExample(Test example);
    List<Test> list();
    int updateByExampleSelective(@Param("record") Test record, @Param("example") Test example);

    int updateByExample(@Param("record") Test record, @Param("example") Test example);
}