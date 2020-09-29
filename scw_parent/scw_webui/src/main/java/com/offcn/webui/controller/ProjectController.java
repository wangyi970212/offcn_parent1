package com.offcn.webui.controller;

import com.offcn.response.AppResponse;
import com.offcn.webui.service.MemberServiceFeign;
import com.offcn.webui.service.ProjectServiceFeign;
import com.offcn.webui.vo.resp.ProjectDetailVo;
import com.offcn.webui.vo.resp.ReturnPayConfirmVo;
import com.offcn.webui.vo.resp.UserAddressVo;
import com.offcn.webui.vo.resp.UserRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectServiceFeign projectServiceFeign;

    @Autowired
    private MemberServiceFeign memberServiceFeign;

    @RequestMapping("/projectInfo")
    public String index(Integer id, Model model, HttpSession session){
        //远程调用服务方法，查询项目详情
        AppResponse<ProjectDetailVo> vo = projectServiceFeign.detailsInfo(id);
        //
        ProjectDetailVo data = vo.getData();
        //
        model.addAttribute("DetailVo",data);
        session.setAttribute("DetailVo", data);
        return "project/project";
    }


    @RequestMapping("/confirm/returns/{projectId}/{returnId}")
    public String getTReturn(@PathVariable("projectId") Integer projectId,
                             @PathVariable("returnId") Integer returnId,
                             Model model, HttpSession session){
        //从session获取项目详细信息
        ProjectDetailVo projectDetailVo= (ProjectDetailVo) session.getAttribute("DetailVo");
        //根据项目回报编号，获取项目回报信息
        AppResponse<ReturnPayConfirmVo> appResponse = projectServiceFeign.getTReturn(returnId);
        //获取项目回报信息
        ReturnPayConfirmVo returnPayConfirmVo = appResponse.getData();

        //设置项目回报的项目id
        returnPayConfirmVo.setProjectId(projectId);
        //设置项目回报的项目名称
        returnPayConfirmVo.setProjectName(projectDetailVo.getName());
        //根据项目发起方id，获取项目发起方名称
        AppResponse<UserRespVo> memberServiceAppResponse = memberServiceFeign.findUser(projectDetailVo.getMemberid());
        UserRespVo userRespVo = memberServiceAppResponse.getData();
        System.out.println("userRespVo:"+userRespVo);
        //设置发起方名称
        returnPayConfirmVo.setMemberName(userRespVo.getRealname());
        //添加项目回报信息到session
        session.setAttribute("returnConfirm",returnPayConfirmVo);
        //添加项目回报信息到Model
        model.addAttribute("returnConfirm",returnPayConfirmVo);

        return "project/pay-step-1";

    }

    //跳转支付
    @GetMapping("/confirm/order/{num}")
    public String confirmOrder(@PathVariable("num") Integer num,Model model, HttpSession session){
        //查询用户是否一登录
        UserRespVo userRespVo = (UserRespVo) session.getAttribute("sessionMember");
        //判断是否为空
        if(userRespVo==null){
            //用户没有登录，保存当前访问的url,登录成功后继续这个页面
            session.setAttribute("preUrl","project/confirm/order/"+num);
            return "redirect:/login.html";
        }
        String accessToken = userRespVo.getAccessToken();
        //根据用户的令牌查询用户的收获地址列表
        AppResponse<List<UserAddressVo>> addressAppResponse = memberServiceFeign.address(accessToken);
        //取出地址列表
        List<UserAddressVo> userAddressVos = addressAppResponse.getData();
        //传递地址列表
        model.addAttribute("address",userAddressVos);
        //从session中取出回报
        ReturnPayConfirmVo confirmVo =(ReturnPayConfirmVo) session.getAttribute("returnConfirm");

        confirmVo.setNum(num);
        //填充回报的金额
        confirmVo.setTotalPrice(new BigDecimal(num * confirmVo.getSupportmoney() + confirmVo.getFreight()));

        session.setAttribute("returnConfirmSession",confirmVo);

        return "project/pay-step-2";

    }
}
