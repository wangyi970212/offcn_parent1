package com.offcn.project.service;

import com.offcn.project.pojo.*;

import java.util.List;

public interface ProjectInfoService {

    public List<TReturn> getProjectReturns(Integer projectId);

    //获取系统中所有 项目
    public List<TProject> getAllProject();
    //根据项目id获取图片
    public List<TProjectImages> getProjectImages(Integer id);

    //获取项目的详细信息
    public TProject getProjectInfo(Integer projectId);

    //获取项目标签
    public List<TTag> getAllProjectTags();

    //获取项目所有分类
    List<TType> getAllProjectTypes();

    //获取项目回报的详细信息
    TReturn getReturnInfo(Integer returnId);

}
