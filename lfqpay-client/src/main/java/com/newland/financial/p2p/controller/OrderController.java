package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.domain.OrderMsgReq;
import com.newland.financial.p2p.domain.OrderQueryReq;
import com.newland.financial.p2p.service.IOrderService;
import com.newland.financial.p2p.service.impl.OrderService;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;

/**
 * 订单处理Controller.
 *
 * @author Mxia
 */

@RestController
@Log4j
@RequestMapping("/ybforder")
public class OrderController {
    /**
     * 创建service对象.
     */
    private IOrderService oerderService = new OrderService();

    /**
     * 创建订单.
     *
     * @param ob 请求报文
     * @return 返回应答报文
     * @throws IOException an error
     */
    @RequestMapping(value = "/sendOrderMsg", method = {RequestMethod.POST, RequestMethod.GET})
    public Object sendOrderMsg(@RequestBody OrderMsgReq ob) throws IOException {
        log.info("come in ybf controller:" + ob);
        OrderMsgReq orm = ob;
        return oerderService.sendOrderMsg(ob);
    }

    /**
     * 查询订单.
     * @param oq 查询报文
     * @return 查询结果
     * @throws IOException an error
     */
    @RequestMapping(value = "/sendOrderQueryMsg", method = {RequestMethod.POST, RequestMethod.GET})
    public Object senOrderQueryMsg(@RequestBody OrderQueryReq oq) throws IOException {
        log.info("5:come in ybf controller:" + oq);
        OrderQueryReq oqr = oq;
        return oerderService.findOrderInfo(oqr);
    }

}
