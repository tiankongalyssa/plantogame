package com.sky.web.filter;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * zuul过滤器
 */
@Component
public class WebFilter extends ZuulFilter {

    /**
     * pre: 请求被路由之前调用
     * route: 请求时被调用
     * post: route 和error过滤器之后被调用
     * error: 处理请求时发生错误时被调用
     */
    @Override
    public String filterType() {
        return "pre"; //前置过滤器
    }

    @Override
    public int filterOrder() {
        /**
         * 通过int值来定义过滤器的执行顺序
         */
        return 0; //优先级为0，数字越大，优先级越低
    }

    /**
     * 返回一个boolean类型来判断该过滤器是否要执行。
     */
    @Override
    public boolean shouldFilter() {

        return true; //是否执行该过滤器
    }

    /**
     * 过滤器的具体逻辑
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String authorization = request.getHeader("Authorization");
        if (authorization != null) {
            requestContext.addZuulRequestHeader("Authorization", authorization);
        }
        return null;  //内容过滤的内容
    }
}
