package com.offcn.project.controller;

import com.offcn.project.pojo.*;
import com.offcn.project.service.ProjectInfoService;
import com.offcn.project.vo.resp.ProjectDetailVo;
import com.offcn.project.vo.resp.ProjectVo;
import com.offcn.response.AppResponse;
import com.offcn.util.OssTemplat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags="项目基本功能模块（文件上传、项目信息获取等）")
@Slf4j
@RequestMapping("/project")
@RestController
public class ProjectInfoController {
    @Autowired
    private OssTemplat ossTemplate;

    @Autowired
    private ProjectInfoService projectInfoService;

    @ApiOperation("文件上传功能")
    @PostMapping("/upload")
    public AppResponse<Map<String,Object>> upload(@RequestParam("file")MultipartFile[] files)throws IOException {
        Map<String,Object> map = new HashMap<>();
        List<String> list= new ArrayList<>();
        if(files!=null&&files.length>0){
            for(MultipartFile item:files){
                if(!item.isEmpty()){
                    String upload = ossTemplate.upload(item.getInputStream(),item.getOriginalFilename());
                    list.add(upload);
                }
            }
        }
        map.put("urls",list);
        log.debug("ossTemplate信息：{},文件上传成功访问路径{}",ossTemplate,list);
        return AppResponse.ok(map);
    }


    @ApiOperation("获取项目回报列表")
    @GetMapping("/details/returns/{projectId}")
    public AppResponse<List<TReturn>> detailsReturn(@PathVariable("projectId") Integer projectId) {

        List<TReturn> returns = projectInfoService.getProjectReturns(projectId);
        return AppResponse.ok(returns);
    }

    @ApiOperation("获取系统所有的项目")
    @GetMapping("/all")
    public AppResponse<List<ProjectVo>> all() {
        // 1、创建集合存储全部项目的VO
        List<ProjectVo> prosVo = new ArrayList<>();
        // 2、查询全部项目
        List<TProject> pros = projectInfoService.getAllProject();
        //3.遍历项目集合
        for (TProject pro : pros) {
            //获取项目编号
            Integer id = pro.getId();
            //根据项目编号查询图片
            List<TProjectImages> images = projectInfoService.getProjectImages(id);
            //转存
            ProjectVo projectVo = new ProjectVo();
            BeanUtils.copyProperties(pro,projectVo);
            //遍历图片集合
            for (TProjectImages projectImages : images) {
                //如果图片类型是头部图片
                if(projectImages.getImgtype()==0){
                    projectVo.setHeaderImage(projectImages.getImgurl());
                }

            }
            prosVo.add(projectVo);
        }
        return AppResponse.ok(prosVo);
    }


    @ApiOperation("获取项目信息详情")
    @GetMapping("/details/info/{projectId}")
    public AppResponse<ProjectDetailVo> detailsInfo(@PathVariable("projectId") Integer projectId) {
        //查询项目详细信息
        TProject projectInfo = projectInfoService.getProjectInfo(projectId);
        //先创建一个响应对象
        ProjectDetailVo projectDetailVo = new ProjectDetailVo();
        //1查出项目的图片信息
        List<TProjectImages> projectImages = projectInfoService.getProjectImages(projectInfo.getId());
        //获取响应对象中详细图片的集合
        List<String> detailsImage = projectDetailVo.getDetailsImage();
        if(detailsImage==null){
            detailsImage=new ArrayList<String>();
        }
        //遍历查询出图片集合
        for (TProjectImages projectImage : projectImages) {
            if(projectImage.getImgtype()==0){
                projectDetailVo.setHeaderImage(projectImage.getImgurl());
            }else {
                detailsImage.add(projectImage.getImgurl());
            }
        }
        projectDetailVo.setDetailsImage(detailsImage);

        //2项目汇报
        List<TReturn> projectReturns = projectInfoService.getProjectReturns(projectInfo.getId());
        projectDetailVo.setProjectReturns(projectReturns);
        //3将详细信息转存
        BeanUtils.copyProperties(projectInfo,projectDetailVo);
        return  AppResponse.ok(projectDetailVo);

    }

    @ApiOperation("获取系统项目中的所有标签")
    @GetMapping("/tags")
    public AppResponse<List<TTag>> tags(){
        return AppResponse.ok(projectInfoService.getAllProjectTags());
    }

    @ApiOperation("获取系统项目中的所有分类")
    @GetMapping("/types")
    public AppResponse<List<TType>> types(){
        return AppResponse.ok(projectInfoService.getAllProjectTypes());
    }

    @ApiOperation("获取系统项目中的回报信息")
    @GetMapping("/returns/info/{returnId}")
    public AppResponse<TReturn> getTReturn(@PathVariable("returnId") Integer returnId){
        return AppResponse.ok(projectInfoService.getReturnInfo(returnId));
    }


}
