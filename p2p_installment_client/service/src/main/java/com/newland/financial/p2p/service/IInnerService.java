package com.newland.financial.p2p.service;

import com.newland.financial.p2p.service.Impl.InnerServiceHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "p2p-installment-server${DEVLOPER_NAME:}", fallback = InnerServiceHystrix.class)
public interface IInnerService {

    /**
     * 获取短信接口请求参数.
     * @param jsonStr 前端请求参数
     * @return CodeMsgReq 对象
     */
    @RequestMapping(method = RequestMethod.POST, value = "/smsCodeController/sendSmsCode")
    Object getMsgCodeReqPram(@RequestBody String jsonStr);

    /**
     * 更新还款表.
     * @param jsonStr 乐百分推送的数据
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/repayController/receiveRepaymentInfo")
    String updateRepayInfo(@RequestBody String jsonStr);
}
