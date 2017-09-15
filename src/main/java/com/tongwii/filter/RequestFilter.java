package com.tongwii.filter;

import com.tongwii.constant.CommunityConstants;
import com.tongwii.po.UserEntity;
import com.tongwii.service.IUserService;
import com.tongwii.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private IUserService userService;

    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String path = urlPathHelper.getLookupPathForRequest(httpServletRequest);

        if (path.equals("/user/login") || path.equals("/user/regist") || path.endsWith("html") || path.endsWith("js") || path.endsWith("css")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        } else {
            String token = httpServletRequest.getHeader(CommunityConstants.Token);
            String userId = TokenUtil.getUserIdFromToken(token);
            if(StringUtils.isNotEmpty(token) && StringUtils.isNotEmpty(userId)) {
                UserEntity userDetails = userService.findById(userId);
                if(TokenUtil.validateToken(token, userDetails, httpServletRequest.getServerName())) {
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                    return;
                } else {
                    httpServletResponse.getWriter().write(new ResponseEntity<>("认证失败", HttpStatus.UNAUTHORIZED).toString());
                    return;
                }
            } else {
                httpServletResponse.getWriter().write(new ResponseEntity<>("非法请求", HttpStatus.UNAUTHORIZED).toString());
                return;
            }
        }
    }

}
