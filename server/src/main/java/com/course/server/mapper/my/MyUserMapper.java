package com.course.server.mapper.my;

import com.course.server.dto.ResourceDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyUserMapper {
    List<ResourceDto> findResource(@Param("userId") String userId);
}
