package com.newland.financial.p2p.service;

import com.newland.financial.p2p.service.Impl.LenderServiceHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "p2p-server${DEVLOPER_NAME:}", fallback = LenderServiceHystrix.class)
public interface ILenderService {
    @RequestMapping(method = RequestMethod.POST, value = "/LenderController/ApplyInterviewPro")
    Object applyProDebit(@RequestBody String jsonStr);

    @RequestMapping(method = RequestMethod.POST, value = "/LenderController/StagingPlan")
    Object findStagingPlan(@RequestBody String jsonStr);

    @RequestMapping(method = RequestMethod.POST, value = "/LenderController/AllProStatus")
    Object findAllProStatus(@RequestBody String jsonStr);

    @RequestMapping(method = RequestMethod.POST, value = "/LenderController/FindAllRepay")
    Object findAllRepay(@RequestBody String jsonStr);
}
