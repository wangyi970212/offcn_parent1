package com.offcn.project.service.impl;

import com.offcn.project.mapper.*;
import com.offcn.project.pojo.*;
import com.offcn.project.service.ProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectInfoServiceImpl implements ProjectInfoService {
    @Autowired
    private TReturnMapper returnMapper;

    @Autowired
    private TProjectMapper projectMapper;

    @Autowired
    private TProjectImagesMapper imagesMapper;

    @Autowired
    private TTagMapper tagMapper;

    @Autowired
    private TTypeMapper typeMapper;

    //根据项目id获取回报列表
    @Override
    public List<TReturn> getProjectReturns(Integer projectId) {
        TReturnExample example = new TReturnExample();
        TReturnExample.Criteria criteria = example.createCriteria();
        criteria.andProjectidEqualTo(projectId);
        return returnMapper.selectByExample(example);

    }

    //查询所有
    @Override
    public List<TProject> getAllProject() {
        return projectMapper.selectByExample(null);
    }

    //根据项目id查图片
    @Override
    public List<TProjectImages> getProjectImages(Integer id) {
        //创建查询条件
        TProjectImagesExample example = new TProjectImagesExample();
        TProjectImagesExample.Criteria criteria = example.createCriteria();
        criteria.andProjectidEqualTo(id);

        return imagesMapper.selectByExample(example);
    }

    //获取项目详细信息
    @Override
    public TProject getProjectInfo(Integer projectId) {
        return projectMapper.selectByPrimaryKey(projectId);
    }

    @Override
    public List<TTag> getAllProjectTags() {
        return tagMapper.selectByExample(null);
    }

    @Override
    public List<TType> getAllProjectTypes() {
        return typeMapper.selectByExample(null);
    }

    @Override
    public TReturn getReturnInfo(Integer returnId) {
        return returnMapper.selectByPrimaryKey(returnId);
    }
}
