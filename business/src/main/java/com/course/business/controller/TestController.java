package com.course.business.controller;

import com.course.server.domain.Test;
import com.course.server.dto.ChapterDto;
import com.course.server.service.ChapterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TestController {
    @Resource
    private ChapterService testService;
    @RequestMapping("/test")
    public List<ChapterDto> test() {
        return  testService.list();
    }
}
