package com.offcn.webui.controller;

import com.offcn.response.AppResponse;
import com.offcn.webui.service.MemberServiceFeign;
import com.offcn.webui.service.ProjectServiceFeign;
import com.offcn.webui.vo.resp.ProjectVo;
import com.offcn.webui.vo.resp.UserRespVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
public class DispathcherController {

    @Autowired
    private MemberServiceFeign memberServiceFeign;

    @Autowired
    private ProjectServiceFeign projectServiceFeign;
    @Autowired
    private  RedisTemplate redisTemplate;

    @RequestMapping("/")
    public  String toIndex(Model model){
        List<ProjectVo> data = (List<ProjectVo>) redisTemplate.opsForValue().get("projectStr");
        //判断从redis获取，项目集合是否为空
        if(data==null){
            //如果为空远程调用服务查询项目列表
            AppResponse<List<ProjectVo>> allProject = projectServiceFeign.all();
            //将查询响应的项目数据传递给data
            data=allProject.getData();
            redisTemplate.opsForValue().set("projectStr",data,1, TimeUnit.HOURS);

        }
        model.addAttribute("projectList",data);
        return "index";
    }

    @RequestMapping("/doLogin")
    public String doLogin(String username, String password, HttpSession session){
        //调用远程用户服务
        AppResponse<UserRespVo> appResponse = memberServiceFeign.login(username, password);
        //获取响应数据
        UserRespVo userRespVo = appResponse.getData();
        log.info("登录账号：{},密码：{}",username,password);
        log.info("登录用户数据:{}",userRespVo);
        if(userRespVo==null){
            //账号不存在
            return "redirect:/login.html";
        }
        //用户存在，登录用户信息存到sesssion中
        session.setAttribute("sessionMember",userRespVo);
        //从session中获取前缀
        String preUrl=(String)session.getAttribute("preUrl");
        //
        if(StringUtils.isEmpty(preUrl)){
            return "redirect:/";
        }

        return "redirect:/"+preUrl;
    }
}
