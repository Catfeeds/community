package com.tongwii.security;

import com.tongwii.constant.UserConstants;
import com.tongwii.dao.IUserDao;
import com.tongwii.domain.UserEntity;
import com.tongwii.security.jwt.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    @Autowired
    private IUserDao userDao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String account) {
        log.debug("Authenticating {}", account);
        UserEntity userEntity = userDao.findByAccount(account);
        if(Objects.isNull(userEntity)) {
            throw new UsernameNotFoundException("User " + account + " was not found in the database");
        } else {
            if(userEntity.getState().equals(UserConstants.USER_DISABLE)) {
                throw new UserNotActivatedException("User " + account + " was not activated");
            }
            List<GrantedAuthority> grantedAuthorities = userEntity.getUserRolesById().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getRoleByRoleId().getCode()))
                    .collect(Collectors.toList());
            return new JwtUser(userEntity.getId(), userEntity.getAccount(), userEntity.getPassword(), userEntity.getState().equals(UserConstants.USER_ENABLE), grantedAuthorities);
        }
    }
}
