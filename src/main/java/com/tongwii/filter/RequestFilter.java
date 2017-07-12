package com.tongwii.filter;

import com.tongwii.bean.TongWIIResult;
import com.tongwii.constant.ResultConstants;
import com.tongwii.util.TokenUtil;
import io.jsonwebtoken.Claims;
import net.sf.json.JSONObject;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 请求过滤器，过滤除登陆注册外的请求，验证token
 *
 * Author: zeral
 * Date: 2017/6/30
 */
public class RequestFilter implements Filter {
    private TongWIIResult tongWIIResult = new TongWIIResult();
    private UrlPathHelper urlPathHelper = new UrlPathHelper();


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse resp = (HttpServletResponse)servletResponse;
        resp.setCharacterEncoding("utf-8");
        String path = urlPathHelper.getLookupPathForRequest(req);
        if (path.equals("/login") || path.equals("/registUser")) {
            filterChain.doFilter(req, resp);
            return;
        } else {
            String token = req.getHeader("token");
            TongWIIResult checkTokenResult = TokenUtil.checkToken(token);
            Claims claims = (Claims) checkTokenResult.getData();
            if(checkTokenResult.getStatus() == ResultConstants.SUCCESS && req.getHeader("host").equals(claims.getIssuer())) {
                filterChain.doFilter(req, resp);
                return;
            } else {
                tongWIIResult.setStatus(ResultConstants.ILLEGAL);
                tongWIIResult.setInfo("请求不合法");
                resp.getWriter().write(JSONObject.fromObject(tongWIIResult).toString());
            }
        }
    }

    @Override
    public void destroy() {

    }
}
