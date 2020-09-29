package com.offcn.order.service.impl;

import com.offcn.order.service.ProjectServiceFeign;
import com.offcn.order.vo.resp.TReturn;
import com.offcn.response.AppResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectServiceFeignException implements ProjectServiceFeign {
    @Override
    public AppResponse<List<TReturn>> returnInfo(Integer projectId) {
        AppResponse<List<TReturn>> fail = AppResponse.fail(null);
        fail.setMsg("调用远程服务器失败【订单】");
        return fail;
    }
}
