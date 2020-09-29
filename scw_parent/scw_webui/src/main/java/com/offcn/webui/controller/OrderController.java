package com.offcn.webui.controller;

import com.offcn.response.AppResponse;
import com.offcn.webui.service.OrderServiceFeign;
import com.offcn.webui.vo.resp.OrderFormInfoSubmitVo;
import com.offcn.webui.vo.resp.ReturnPayConfirmVo;
import com.offcn.webui.vo.resp.TOrder;
import com.offcn.webui.vo.resp.UserRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class OrderController {
    @Autowired
    private OrderServiceFeign orderServiceFeign;

    //保存订单
    @RequestMapping("/order/save")
    public String OrderPay(OrderFormInfoSubmitVo vo, HttpSession session){
        UserRespVo userRespoonse =(UserRespVo) session.getAttribute("sessionMember");
        if(userRespoonse==null){
            return  "redirect:/login.html";
        }
        String accessToken=userRespoonse.getAccessToken();
        vo.setAccessToken(accessToken);
        ReturnPayConfirmVo confirmVo = (ReturnPayConfirmVo) session.getAttribute("returnConfirmSession");
        if(confirmVo==null){
            return "redirect:/login.html";
        }
        vo.setProjectid(confirmVo.getProjectId());
        vo.setReturnid(confirmVo.getId());
        vo.setRtncount(confirmVo.getNum());
        AppResponse<TOrder> order = orderServiceFeign.createOrder(vo);
        TOrder data = order.getData();

        String orderName = confirmVo.getProjectName();
        System.out.println("orderNum:"+data.getOrdernum());
        System.out.println("money:"+data.getMoney());
        System.out.println("orderName:"+orderName);
        System.out.println("Remark:"+vo.getRemark());

        return  "/member/minecrowdfunding";


    }
}
