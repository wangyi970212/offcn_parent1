package com.offcn.user.controller;

import com.offcn.response.AppResponse;
import com.offcn.user.pojo.TMember;
import com.offcn.user.service.UserService;
import com.offcn.user.vo.resp.UserRespVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
@Slf4j
public class InfoController {

    @Autowired
    private UserService userService;
    //根据用户编号获取用户信息
    @GetMapping("/findUser/{id}")
    public AppResponse<UserRespVo> findUser(@PathVariable("id") Integer id){
        System.out.println("user--->findUser----id:"+id);
        TMember tmember = userService.findTmemberById(id);
        System.out.println("tmember:"+tmember);
        UserRespVo userRespVo = new UserRespVo();
        BeanUtils.copyProperties(tmember,userRespVo);
        System.out.println("userRespVo:"+userRespVo);

        return AppResponse.ok(userRespVo);
    }
}
