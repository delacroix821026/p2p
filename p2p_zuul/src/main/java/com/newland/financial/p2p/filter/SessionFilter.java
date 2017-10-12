package com.newland.financial.p2p.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//@Component
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
        HttpSession httpSession = ctx.getRequest().getSession(true);
        httpSession.setAttribute("abc", "123");
        ctx.addZuulRequestHeader("Cookie", "SESSION=" + httpSession.getId());
        System.out.println("request.getSession()::::::::" + httpSession.getId());
        return null;
    }
}

