package com.newland.financial.p2p.controller;

import com.newland.financial.p2p.domain.OrderInfo;
import com.newland.financial.p2p.domain.OrderMsgReq;
import com.newland.financial.p2p.domain.OrderQueryReq;
import com.newland.financial.p2p.service.IOrderService;
import com.newland.financial.p2p.service.impl.OrderService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    private IOrderService oerderService = new OrderService();

    /**
     * 创建订单.
     * @param ob
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/sendOrderMsg", method = {RequestMethod.POST, RequestMethod.GET})
    public OrderInfo sendOrderMsg(@RequestBody OrderMsgReq ob) throws IOException {
        log.info("come in ybf controller:" + ob);
        OrderMsgReq orm = ob;
        return oerderService.sendOrderMsg(ob);
    }


    @RequestMapping(value = "/sendOrderQueryMsg", method = {RequestMethod.POST, RequestMethod.GET})
    public Object senOrderQueryMsg(@RequestBody OrderQueryReq oq) throws IOException {
        log.info("5:come in ybf controller:" + oq);
        OrderQueryReq oqr = oq;
        return oerderService.findOrderInfo(oqr);
    }

}
