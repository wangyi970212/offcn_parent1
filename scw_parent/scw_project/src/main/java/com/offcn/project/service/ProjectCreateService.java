package com.offcn.project.service;

import com.offcn.enums.ProjectStatusEnume;
import com.offcn.project.vo.req.ProjectRedisStorageVo;

public interface ProjectCreateService {
    //初始化项目
    public String initCreateProject(Integer memberId);

    //保存项目信息
    public void saveProjectInfo(ProjectStatusEnume auth, ProjectRedisStorageVo project);
}
