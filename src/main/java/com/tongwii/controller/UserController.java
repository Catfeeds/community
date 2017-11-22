package com.tongwii.controller;

import com.tongwii.constant.AuthoritiesConstants;
import com.tongwii.constant.Constants;
import com.tongwii.constant.UserConstants;
import com.tongwii.core.exception.BadRequestAlertException;
import com.tongwii.core.exception.InternalServerErrorException;
import com.tongwii.core.exception.InvalidPasswordException;
import com.tongwii.core.exception.LoginAlreadyUsedException;
import com.tongwii.domain.User;
import com.tongwii.dto.UserDto;
import com.tongwii.dto.mapper.UserMapper;
import com.tongwii.security.SecurityUtils;
import com.tongwii.security.jwt.JWTConfigurer;
import com.tongwii.security.jwt.TokenProvider;
import com.tongwii.service.UserService;
import com.tongwii.util.HeaderUtil;
import com.tongwii.util.PaginationUtil;
import com.tongwii.util.ResponseUtil;
import com.tongwii.vm.LoginVM;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
    private TokenProvider tokenProvider;
	@Autowired
    private AuthenticationManager authenticationManager;
	@Autowired
    private UserMapper userMapper;

	// 用户注册接口
	@PostMapping("/register")
	public ResponseEntity regist(@Valid @RequestBody User user)  {
        if(Optional.ofNullable(userService.findByAccount(user.getAccount())).isPresent()){
            throw new LoginAlreadyUsedException();
        }
        UserDto userDTO = userService.register(user);
        return ResponseEntity.ok(userDTO);
	}

    // 用户授权接口
    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginVM.getAccount(), loginVM.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.getRememberMe() == null) ? false : loginVM.getRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        // 基本用户信息
        User user = userService.findByAccountAndUpdateDeviceId(loginVM);
        UserDto userDTO = userMapper.userToUserDTO(user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(userDTO, httpHeaders, HttpStatus.OK);
    }


    // 获取当前登录用户
    @GetMapping
    public ResponseEntity user() {
        UserDto userDto = Optional.ofNullable(userService.findById(SecurityUtils.getCurrentUserId())).map(userMapper::userToUserDTO).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        return ResponseEntity.ok(userDto);
    }

    // 修改当前登录用户信息
    @PostMapping
    public ResponseEntity saveUser(@Valid @RequestBody UserDto userDto) {
        final String userLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> user = userService.findOneByAccount(userLogin);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("User could not be found");
        }
        userService.updateUser(userDto.getAccount(), userDto.getNickName(), userDto.getName(),
            userDto.getLangKey());return ResponseEntity.ok(userDto);
    }


    /**
     * POST  /account/change-password : changes the current user's password
     *
     * @param password the new password
     * @throws InvalidPasswordException 400 (Bad Request) if the new password is incorrect
     */
    @PostMapping(path = "/change-password")
    public void changePassword(@RequestBody String password) {
        if (!checkPasswordLength(password)) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(password);
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= UserConstants.PASSWORD_MIN_LENGTH &&
            password.length() <= UserConstants.PASSWORD_MAX_LENGTH;
    }


    /**
     * GET  /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(Pageable pageable) {
        final Page<UserDto> page = userService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user/users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * PUT  /users : Updates an existing User.
     *
     * @param userDto the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user
     */
    @PutMapping("/users")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto) {
        Optional<User> existingUser = userService.findOneByAccount(userDto.getAccount());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDto.getId()))) {
            throw new LoginAlreadyUsedException();
        }
        Optional<UserDto> updatedUser = userService.updateUser(userDto);

        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert("userManagement.updated", userDto.getAccount()));
    }

    /**
     * GET  /users/:login : get the "login" user.
     *
     * @param account the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @DeleteMapping("/device/{deviceId}")
    public ResponseEntity deleteUserDevice(@PathVariable String deviceId) {

        this.userService.updateUserDevices(deviceId);
        return ResponseEntity.ok("删除用户设备成功");
    }

    /**
     * GET  /users/:login : get the "login" user.
     *
     * @param account the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @GetMapping("/users/{account:" + Constants.LOGIN_REGEX + "}")
    public ResponseEntity<UserDto> getUser(@PathVariable String account) {
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithRolesByAccount(account)
                .map(userMapper::userToUserDTO));
    }

    /**
     * DELETE /users/:login : delete the "login" User.
     *
     * @param account the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{account:" + Constants.LOGIN_REGEX + "}")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteUser(@PathVariable String account) {
        try {
            userService.deleteUser(account);
        } catch (Exception e) {
            throw new BadRequestAlertException("用户删除失败", "userManagement", "userUsed");
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("userManagement.deleted", account)).build();
    }

	// 上传用户头像
	@PostMapping("/uploadAvatar")
	public ResponseEntity uploadAvatar(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("请选择上传文件!");
        }
        String userId = SecurityUtils.getCurrentUserId();
        // 上传文件并更新用户地址
        String uploadUrl = userService.updateUserAvatorById(userId, file);
        return ResponseEntity.ok(uploadUrl);
	}

	// 修改用户昵称
	@PutMapping("/updateNickName")
	public ResponseEntity updateNickName(@RequestBody User user) {
        String userId = SecurityUtils.getCurrentUserId();
        User userEntity = userService.findById(userId);
        userEntity.setNickName(user.getNickName());
        userService.update(userEntity);
        UserDto userDto = userMapper.userToUserDTO(userEntity);
        return ResponseEntity.ok(userDto);
    }

	// 修改个性签名
	@PutMapping("/updateSignature")
	public ResponseEntity updateSignature(@RequestBody User user) {
        String userId = SecurityUtils.getCurrentUserId();
        User userEntity = userService.findById(userId);
        userEntity.setSignature(user.getSignature());
        UserDto userDto = userMapper.userToUserDTO(userEntity);
        return ResponseEntity.ok(userDto);
	}

    // 修改用户电话
    @PutMapping("/updatePhone")
    public ResponseEntity updatePhone(@RequestBody User user) {
        String userId = SecurityUtils.getCurrentUserId();
        User userEntity = userService.findById(userId);
        userEntity.setPhone(user.getPhone());
        UserDto userDto = userMapper.userToUserDTO(userEntity);
        return ResponseEntity.ok(userDto);
    }

    // 修改用户真实姓名
    @PutMapping("/updateRealName")
    public ResponseEntity updateRealName(@RequestBody User user) {
        String userId = SecurityUtils.getCurrentUserId();
        User userEntity = userService.findById(userId);
        userEntity.setName(user.getName());
        UserDto userDto = userMapper.userToUserDTO(userEntity);
        return ResponseEntity.ok(userDto);
    }

    // 修改用户出生日期
    @PutMapping("/updateBirth")
    public ResponseEntity updateBirth(@RequestBody User user) {
        String userId = SecurityUtils.getCurrentUserId();
        User userEntity = userService.findById(userId);
        userEntity.setBirthday(user.getBirthday());
        UserDto userDto = userMapper.userToUserDTO(userEntity);
        return ResponseEntity.ok(userDto);
    }

    // 修改用户性别
    @PutMapping("/updateGender")
    public ResponseEntity updateGender(@RequestBody User user) {
        String userId = SecurityUtils.getCurrentUserId();
        User userEntity = userService.findById(userId);
        userEntity.setSex(user.getSex());
        UserDto userDto = userMapper.userToUserDTO(userEntity);
        return ResponseEntity.ok(userDto);
    }
}

