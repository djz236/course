package com.course.server.service;


import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.domain.Test;
import com.course.server.domain.TestExample;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.ChapterPageDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ChapterMapper;
import com.course.server.mapper.TestMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChapterService {
    @Resource
    private ChapterMapper chapterMapper;
    public void list(ChapterPageDto chapterPageDto){
        PageHelper.startPage(chapterPageDto.getPage(),chapterPageDto.getSize());
        ChapterExample chapterExample = new ChapterExample();
       /* chapterExample.createCriteria().andIdEqualTo("1");*/
        ChapterExample.Criteria criteria = chapterExample.createCriteria();
        if(!StringUtils.isEmpty(chapterPageDto.getCourseId())){
            criteria.andCourseIdEqualTo(chapterPageDto.getCourseId());
        }
        chapterExample.setOrderByClause("id desc");
        List<Chapter> chapterList = chapterMapper.selectByExample(chapterExample);
        PageInfo<Chapter> pageInfo=new PageInfo<>(chapterList);
        chapterPageDto.setTotal(pageInfo.getTotal());
        List<ChapterDto> chapterDtoList=new ArrayList<>();
        for (int i=0;i<chapterList.size();i++){
            Chapter chapter=chapterList.get(i);
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter,chapterDto);
            chapterDtoList.add(chapterDto);
        }
        chapterPageDto.setList(chapterDtoList);
    }
    public void save(ChapterDto chapterDto){
        Chapter chapter = CopyUtil.copy(chapterDto,Chapter.class);
        if(StringUtils.isEmpty(chapterDto.getId())){
            this.insert(chapter);
        }else{
            this.update(chapter);
        }
    }
    private void insert(Chapter chapter){
        chapter.setId(UuidUtil.getShortUuid());
        chapterMapper.insert(chapter);
    }
    private void update(Chapter chapter){
        chapterMapper.updateByPrimaryKey(chapter);
    }

    public void delete(String id) {
        chapterMapper.deleteByPrimaryKey(id);
    }
    /**
     * 查询某一课程下的所有章
     */
    public List<ChapterDto> listByCourse(String courseId) {
        ChapterExample example = new ChapterExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        List<Chapter> chapterList = chapterMapper.selectByExample(example);
        List<ChapterDto> chapterDtoList = CopyUtil.copyList(chapterList, ChapterDto.class);
        return chapterDtoList;
    }
}










