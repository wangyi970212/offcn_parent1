package com.offcn.order.service;

import com.offcn.order.pojo.TOrder;
import com.offcn.order.vo.req.OrderInfoSubmitVo;

public interface OrderService {
    //保存订单
    public TOrder saveOrder(OrderInfoSubmitVo vo);
}
