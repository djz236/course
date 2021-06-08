package com.course.system.controller.admin;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.utils.StringUtils;
import com.course.server.dto.*;
import com.course.server.service.CourseuserService;
import com.course.server.util.UuidUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/admin/user")
public class CourseuserController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseuserController.class);
    public static final String BUSINESS_NAME = "用户";

    @Resource
    private CourseuserService courseuserService;

    @Resource
    private RedisTemplate redisTemplate;
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
        //  courseuserDto.setPassword(DigestUtils.md5DigestAsHex(courseuserDto.getPassword().getBytes()));
        // 保存校验
        ValidatorUtil.length(courseuserDto.getLoginname(), "用户名", 1, 20);
        ValidatorUtil.length(courseuserDto.getName(), "昵称", 1, 20);
        ValidatorUtil.length(courseuserDto.getPassword(), "密码", 1, 200);

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

    /**
     * 重置密码
     */
    @PostMapping("/save-password")
    public ResponseDto savePassword(@RequestBody CourseuserDto courseuserDto) {
        courseuserDto.setPassword(DigestUtils.md5DigestAsHex(courseuserDto.getPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();
        courseuserService.savePassword(courseuserDto);
        responseDto.setContent(courseuserDto);
        return responseDto;
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public ResponseDto login(@RequestBody CourseuserDto courseuserDto
            , HttpServletRequest request) {
        ResponseDto responseDto = new ResponseDto();

        //
       // String imageCode = (String) request.getSession().getAttribute(courseuserDto.getImageCode());
        String imageCode = (String) redisTemplate.opsForValue().get(courseuserDto.getImageCodeToken());
        if (StringUtils.isEmpty(imageCode)) {
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码已过期");
            LOG.info("用户登录失败");
            return responseDto;
        }
        if (!imageCode.toLowerCase().equals(courseuserDto.getImageCode().toLowerCase())) {
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码不对");
            LOG.info("用户登录失败");
            return responseDto;
        }else{
            //request.getSession().removeAttribute(courseuserDto.getImageCodeToken());
            redisTemplate.delete(courseuserDto.getImageCodeToken());
        }

        LoginCourseuserDto loginCourseuserDto = courseuserService.login(courseuserDto);
        String token = UuidUtil.getShortUuid();
        loginCourseuserDto.setToken(token);
        redisTemplate.opsForValue().set(token, JSON.toJSON(loginCourseuserDto),3600, TimeUnit.SECONDS);
       // request.getSession().setAttribute(Constants.LOGIN_USER, loginCourseuserDto);
        responseDto.setContent(loginCourseuserDto);
        return responseDto;
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout/{token}")
    public ResponseDto logout(HttpServletRequest request,String token) {
        ResponseDto responseDto = new ResponseDto();
      //  request.getSession().removeAttribute(Constants.LOGIN_USER);
        redisTemplate.delete(token);
        return responseDto;
    }
}
