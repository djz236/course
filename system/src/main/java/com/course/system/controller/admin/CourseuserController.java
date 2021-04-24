package com.course.system.controller.admin;

import com.course.server.dto.CourseuserDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CourseuserService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/user")
public class CourseuserController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseuserController.class);
    public static final String BUSINESS_NAME = "用户";

    @Resource
    private CourseuserService courseuserService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        courseuserService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody CourseuserDto courseuserDto) {
        // 保存校验
        ValidatorUtil.length(courseuserDto.getLoginname(), "用户名", 1, 20);
        ValidatorUtil.length(courseuserDto.getName(), "昵称", 1, 20);
        ValidatorUtil.length(courseuserDto.getPassword(), "密码", 1, 20);

        ResponseDto responseDto = new ResponseDto();
        courseuserService.save(courseuserDto);
        responseDto.setContent(courseuserDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        courseuserService.delete(id);
        return responseDto;
    }
}
