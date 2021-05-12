package com.course.server.service;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.Courseuser;
import com.course.server.domain.CourseuserExample;
import com.course.server.dto.CourseuserDto;
import com.course.server.dto.LoginCourseuserDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResourceDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.CourseuserMapper;
import com.course.server.mapper.my.MyCourseMapper;
import com.course.server.mapper.my.MyUserMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;

@Service
public class CourseuserService {
    private static final Logger LOG = LoggerFactory.getLogger(CourseuserService.class);
    @Resource
    private CourseuserMapper courseuserMapper;
    @Resource
    private MyUserMapper myUserMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CourseuserExample courseuserExample = new CourseuserExample();
        List<Courseuser> courseuserList = courseuserMapper.selectByExample(courseuserExample);
        PageInfo<Courseuser> pageInfo = new PageInfo<>(courseuserList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseuserDto> courseuserDtoList = CopyUtil.copyList(courseuserList, CourseuserDto.class);
        pageDto.setList(courseuserDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(CourseuserDto courseuserDto) {
        Courseuser courseuser = CopyUtil.copy(courseuserDto, Courseuser.class);
        if (StringUtils.isEmpty(courseuserDto.getId())) {
            this.insert(courseuser);
        } else {
            this.update(courseuser);
        }
    }

    /**
     * 新增
     */
    private void insert(Courseuser courseuser) {
        courseuser.setId(UuidUtil.getShortUuid());
        Courseuser userDb = this.selectByLoginName(courseuser.getLoginname());
        if (userDb != null) {
            throw new BusinessException(
                    BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
        }
        courseuserMapper.insert(courseuser);
    }

    /**
     * 更新
     */
    private void update(Courseuser courseuser) {
        courseuser.setPassword(null);
        courseuserMapper.updateByPrimaryKeySelective(courseuser);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        courseuserMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据登录名查询用户信息
     *
     * @param loginName
     * @return
     */
    public Courseuser selectByLoginName(String loginName) {
        CourseuserExample example = new CourseuserExample();
        CourseuserExample.Criteria criteria = example.createCriteria();
        criteria.andLoginnameEqualTo(loginName);
        List<Courseuser> courseusers = courseuserMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(courseusers)) {
            return null;
        } else {
            return courseusers.get(0);
        }
    }

    /**
     * 重置密码
     *
     * @param courseuserDto
     */
    public void savePassword(CourseuserDto courseuserDto) {
        Courseuser courseuser = new Courseuser();
        courseuser.setId(courseuserDto.getId());
        courseuser.setPassword(courseuserDto.getPassword());
        courseuserMapper.updateByPrimaryKeySelective(courseuser);
    }

    /**
     * 登录
     *
     * @param courseuserDto
     */
    public LoginCourseuserDto login(CourseuserDto courseuserDto) {
        Courseuser courseuser = selectByLoginName(courseuserDto.getLoginname());
        if (courseuser == null) {
            //用户不存在
            LOG.info("用户不存在!");
            throw new BusinessException(
                    BusinessExceptionCode.LOGIN_ERROR);
        } else {
            if (courseuser.getPassword().equals(courseuserDto.getPassword())) {
                //登录成功
                LoginCourseuserDto loginCourseuserDto = CopyUtil.copy(courseuser, LoginCourseuserDto.class);
                setAuth(loginCourseuserDto);
                return loginCourseuserDto;
            } else {
                //密码不对
                LOG.info("密码不对!");
                throw new BusinessException(
                        BusinessExceptionCode.LOGIN_ERROR);
            }
        }
    }

    /**
     * 为登录用户读取权限
     */
    private void setAuth(LoginCourseuserDto loginCourseuserDto) {
        List<ResourceDto> resourceDtoList = myUserMapper.findResource(loginCourseuserDto.getId());
        loginCourseuserDto.setResources(resourceDtoList);
//整理所有有权限的请求，用于接口拦截
        HashSet<String> requestSet = new HashSet<>();
        if(!CollectionUtils.isEmpty(resourceDtoList)){
            for(int i = 0, l = resourceDtoList.size(); i < l; i++){
                ResourceDto resourceDto = resourceDtoList.get(i);
                String arrayString = resourceDto.getRequest();
                List<String> requestList = JSON.parseArray(arrayString, String.class);
                if(!CollectionUtils.isEmpty(requestList)){
                    requestSet.addAll(requestList);
                }
            }
        }
        LOG.info("有权限的请求：{}", requestSet);
        loginCourseuserDto.setRequests(requestSet);
    }
}













