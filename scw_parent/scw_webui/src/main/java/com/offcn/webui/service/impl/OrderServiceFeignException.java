package com.offcn.webui.service.impl;

import com.offcn.response.AppResponse;
import com.offcn.webui.service.OrderServiceFeign;
import com.offcn.webui.vo.resp.OrderFormInfoSubmitVo;
import com.offcn.webui.vo.resp.TOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderServiceFeignException implements OrderServiceFeign {
    @Override
    public AppResponse<TOrder> createOrder(OrderFormInfoSubmitVo vo) {
        AppResponse<TOrder> response = AppResponse.fail(null);
        response.setMsg("远程调用失败【订单】");
        return response;
    }
}
