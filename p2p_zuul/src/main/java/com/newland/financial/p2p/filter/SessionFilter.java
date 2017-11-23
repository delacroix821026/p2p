package com.newland.financial.p2p.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.ohuyo.libra.client.session.LibraSession;
import org.ohuyo.libra.client.session.LibraSessionUtils;
import org.ohuyo.libra.client.session.SlaveLibraSessionImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@Component
@Log4j
public class SessionFilter implements Filter {
    public int filterOrder() {
        return 1; // run before PreDecoration
    }

    public String filterType() {
        return "pre";
    }

    public boolean shouldFilter() {
        return true;
    }



    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Installment filter:");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpSession httpSession = ctx.getRequest().getSession();//false
        if(httpSession.getAttribute("ORG_OHUYO_LIBRA_CLIENT_LIBRA_SESSION") == null) {
            log.debug("USER_INFO in session is null");
            String userNo = ctx.getRequest().getHeader("userNo");
            String mercList = ctx.getRequest().getHeader("mercList");
            String tokenId = ctx.getRequest().getHeader("tokenId");
            SlaveLibraSessionImpl libraSession = new SlaveLibraSessionImpl();
            libraSession.setAppTicket(tokenId);
            libraSession.setLoginName(userNo);
            libraSession.setOrgList(mercList);
            httpSession.setAttribute("ORG_OHUYO_LIBRA_CLIENT_LIBRA_SESSION", libraSession);
        }

        //ctx.addZuulRequestHeader("Cookie", "SESSION=" + httpSession.getId());
        log.debug("Current SessionId is:" + httpSession.getId());
    }

    public void destroy() {

    }
}

