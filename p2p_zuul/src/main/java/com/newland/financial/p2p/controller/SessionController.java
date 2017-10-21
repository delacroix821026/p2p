package com.newland.financial.p2p.controller;


import lombok.extern.log4j.Log4j;
import org.ohuyo.libra.client.exception.LibraClientException;
import org.ohuyo.libra.client.session.LibraSession;
import org.ohuyo.libra.client.session.LibraSessionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Log4j
@RestController
public class SessionController {
    @RequestMapping("/getUserSession")
    public String test() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        HttpSession session = req.getSession();
        System.out.println("是否生成："+session.getAttribute("abc"));

        LibraSession s = null;
        if (req instanceof HttpServletRequest) {

            session = req.getSession(false);
            if (session != null) {
                log.info("--------LibraSession--------isHttpServletRequest---------");
                String userName = (String) session.getAttribute("userName");
                if (userName == null) {
                    s = LibraSessionUtils.getSession(session);
                }
            }
        }




        log.info("--------s-----------------" + s);
        String permissions = null;
        try {
            permissions = s.getSharedAttribute("libraMasterClient", "permissions");
            log.info("--------LibraSession--------permissions---------" + permissions);
            String organization = s.getSharedAttribute("libraMasterClient", "organization");
            log.info("--------LibraSession--------organization---------" + organization);
            String loginName = s.getSharedAttribute("libraMasterClient", "loginName");
            log.info("--------LibraSession--------loginName---------" + loginName);
            String userName = s.getSharedAttribute("libraMasterClient", "userName");
            log.info("--------LibraSession--------userName---------" + userName);
        } catch (LibraClientException e) {
            e.printStackTrace();
        }

        return "PING OK";
    }
}
