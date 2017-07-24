package com.tongwii.filter;

import com.tongwii.bean.TongWIIResult;
import com.tongwii.po.UserEntity;
import com.tongwii.service.IUserService;
import com.tongwii.util.TokenUtil;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * 请求过滤器，过滤除登陆注册外的请求，验证token
 *
 * Author: zeral
 * Date: 2017/6/30
 */
public class RequestFilter extends OncePerRequestFilter {
    private static final Logger logger = LogManager.getLogger();

    private static final String TOKEN = "token";

    @Autowired
    private IUserService userService;

    private TongWIIResult tongWIIResult = new TongWIIResult();
    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String path = urlPathHelper.getLookupPathForRequest(httpServletRequest);

        if (path.equals("/login") || path.equals("/registUser") || path.endsWith("html") || path.endsWith("js") || path.endsWith("css")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        } else {
            String token = httpServletRequest.getHeader(TOKEN);
            String userId = TokenUtil.getUserIdFromToken(token);
            if(null != token && null != userId) {
                UserEntity userDetails = userService.findById(userId);
                if(TokenUtil.validateToken(token, userDetails, httpServletRequest.getServerName())) {
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                    return;
                } else {
                    tongWIIResult.errorResult("认证失败");
                    httpServletResponse.getWriter().write(JSONObject.fromObject(tongWIIResult).toString());
                    return;
                }
            } else {
                tongWIIResult.errorResult("请求非法");
                httpServletResponse.getWriter().write(JSONObject.fromObject(tongWIIResult).toString());
                return;
            }
        }
    }

}
