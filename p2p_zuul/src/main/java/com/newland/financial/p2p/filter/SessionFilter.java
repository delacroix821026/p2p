package com.newland.financial.p2p.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//@Component
public class SessionFilter extends ZuulFilter {
    @Override
    public int filterOrder() {
        return 0; // run before PreDecoration
    }

    @Override
    public String filterType() {
        return "pre";
    }

    public boolean shouldFilter() {
        System.out.println("request.getSession()");
        return true;
    }

    /* public Object run() {
         RequestContext ctx = RequestContext.getCurrentContext();
         HttpServletRequest request = ctx.getRequest();
         HttpSession session = request.getSession(false);
         List<String> cookies = Collections.list(request.getHeaders("cookie"));
         if (session != null && cookies.stream().noneMatch(new Predicate<String>() {
             public boolean test(String s) {
                 return s.startsWith("SESSION=");
             }
         })) {
             ctx.addZuulRequestHeader("cookie", "SESSION=" + session.getId());
         }
         //session.setAttribute("abc", "123");
         Enumeration enu = request.getHeaderNames();
         while (enu.hasMoreElements()) {
             String headerName = (String) enu.nextElement();
             String headerValue = request.getHeader(headerName);
             System.out.print("headerName:" + headerName + "====");
             System.out.println("headerValue:" + headerValue);
         }
         //System.out.println(session.getAttribute("abc"));
         return null;
     }*/
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpSession httpSession = request.getSession(false);
       /* httpSession.setAttribute("abc", "123");
        Enumeration enu = request.getHeaderNames();
        while (enu.hasMoreElements()) {
            String headerName = (String) enu.nextElement();
            String headerValue = request.getHeader(headerName);
            System.out.print("headerName:" + headerName + "====");
            System.out.println("headerValue:" + headerValue);
        }
        System.out.println(httpSession.getAttribute("abc"));*/
        return null;
    }
}

