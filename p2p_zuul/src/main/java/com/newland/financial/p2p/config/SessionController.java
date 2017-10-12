package com.newland.financial.p2p.config;

import com.netflix.zuul.context.RequestContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class SessionController {
    // 用于测试 SpringBoot 容器是否启动
    // http:localhost:8080/test
    @RequestMapping("/test")
    public String test() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpSession session = request.getSession(true);
        System.out.println("是否生成："+session==null);
        return "PING OK";
    }

    // http:localhost:8080/put?key=name&value=liwei
    @RequestMapping("/put")
    public String put(HttpSession session,
                      @RequestParam("key") String key, @RequestParam("value") String value) {
        session.setAttribute(key, value);
        return "PUT OK";
    }

    // http:localhost:8080/get?key=name
    @RequestMapping("/get")
    public String get(HttpSession session,
                      @RequestParam("key") String key) {
        String value = (String) session.getAttribute(key);

        if (value == null || "".equals(value)) {
            return "NO VALUE GET";
        }
        return value;
    }
}
