package com.newland.financial.p2p.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.java.Log;
import org.ohuyo.libra.client.session.LibraSession;
import org.ohuyo.libra.client.session.LibraSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//@Component
@Log
public class SessionFilter extends ZuulFilter {
    @Override
    public int filterOrder() {
        return 1; // run before PreDecoration
    }

    @Override
    public String filterType() {
        return "pre";
    }

    public boolean shouldFilter() {
        return true;
    }

    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpSession httpSession = ctx.getRequest().getSession();//false
        if(httpSession.getAttribute("USERINFO") == null) {
            log.info("USER_INFO in session is null");
            /*httpSession.setAttribute("abc", "123");
            LibraSession libraSess = LibraSessionUtils.getSession(httpSession);
            httpSession.setAttribute("USER_INFO", libraSess);*/
        }

        ctx.addZuulRequestHeader("Cookie", "SESSION=" + httpSession.getId());
        log.info("Current SessionId is:" + httpSession.getId());
        return null;
    }
}

