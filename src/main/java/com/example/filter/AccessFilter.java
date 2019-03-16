package com.example.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class AccessFilter extends ZuulFilter {

  private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

  @Override
  public String filterType(){
    return "pre";
  }

  @Override
  public boolean shouldFilter(){
    return true;
  }

  @Override
  public int filterOrder(){
    return 0;
  }

  public Object run(){
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();

    log.info("Send {} request to {}", request.getMethod(), request.getRequestURL());

    Object accessToken = request.getParameter("accessToken");
    if(Objects.isNull(accessToken)){
      log.warn("Access token is null");
      ctx.setSendZuulResponse(false);
      ctx.setResponseStatusCode(401);
      ctx.setResponseBody("unauthorized");

      return null;
    }

    log.info("Access token is ok");
    return null;
  }
}
