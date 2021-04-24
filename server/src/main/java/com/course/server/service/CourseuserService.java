package com.course.server.service;

import com.course.server.domain.Courseuser;
import com.course.server.domain.CourseuserExample;
import com.course.server.dto.CourseuserDto;
import com.course.server.dto.PageDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.CourseuserMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseuserService {

    @Resource
    private CourseuserMapper courseuserMapper;

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
        if(userDb!=null){
            throw new BusinessException(
                    BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
        }
        courseuserMapper.insert(courseuser);
    }

    /**
     * 更新
     */
    private void update(Courseuser courseuser) {
        courseuserMapper.updateByPrimaryKey(courseuser);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        courseuserMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据登录名查询用户信息
     * @param loginName
     * @return
     */
    public Courseuser selectByLoginName(String loginName){
        CourseuserExample example = new CourseuserExample();
        CourseuserExample.Criteria criteria = example.createCriteria();
        criteria.andLoginnameEqualTo(loginName);
        List<Courseuser> courseusers = courseuserMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(courseusers)){
            return null;
        }else{
            return courseusers.get(0);
        }
    }
}
