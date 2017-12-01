package com.tongwii.security;

import com.tongwii.dao.IUserDao;
import com.tongwii.domain.User;
import com.tongwii.security.jwt.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Authenticate a User from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final IUserDao userDao;

    public DomainUserDetailsService(IUserDao userDao) {this.userDao = userDao;}

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String account) {
        log.debug("Authenticating {}", account);
        Optional<User> userFromDatabase = userDao.findOneWithRolesByAccount(account);
        return userFromDatabase.map(user -> {
            if (!user.isActivated()) {
                throw new UserNotActivatedException("User " + account + " was not activated");
            }
            List<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getCode())).collect(Collectors.toList());
            return new JwtUser(user.getId(), user.getAccount(), user.getPassword(), grantedAuthorities, user.isActivated());
        }).orElseThrow(() -> new UsernameNotFoundException("User " + account + " was not found in the " + "database"));
    }
}
