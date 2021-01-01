package com.course.server.mapper;

import com.course.server.domain.Tt;
import com.course.server.domain.TtExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TtMapper {
    long countByExample(TtExample example);

    int deleteByExample(TtExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Tt record);

    int insertSelective(Tt record);

    List<Tt> selectByExample(TtExample example);

    Tt selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Tt record, @Param("example") TtExample example);

    int updateByExample(@Param("record") Tt record, @Param("example") TtExample example);

    int updateByPrimaryKeySelective(Tt record);

    int updateByPrimaryKey(Tt record);
}