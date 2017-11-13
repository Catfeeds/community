package com.tongwii.controller;

import com.tongwii.domain.User;
import com.tongwii.dto.UserDto;
import com.tongwii.dto.mapper.UserMapper;
import com.tongwii.security.SecurityUtils;
import com.tongwii.security.jwt.TokenProvider;
import com.tongwii.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/user")
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
        if(StringUtils.isBlank(user.getAccount()) || StringUtils.isBlank(user.getPassword())){
            return ResponseEntity.badRequest().body("用户名或密码不能为空");
        }
        if(Objects.nonNull(userService.findByAccount(user.getAccount()))){
            return ResponseEntity.badRequest().body("用户已存在");
        }
        UserDto userDTO = userService.save(user);
        return ResponseEntity.ok(userDTO);
	}

	// 用户登录接口
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody User user) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getAccount(), user.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        // 基本用户信息
        user = userService.findByAccount(user.getAccount());
        UserDto userDTO = userMapper.userToUserDTO(user);
        Map<String, Object> map = new HashMap<>();
        map.put("userInfo", userDTO);
        map.put("token", jwt);
        return ResponseEntity.ok(map);
	}

	// 上传用户头像
	@PostMapping("/uploadAvatar")
	public ResponseEntity uploadAvatar(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("请选择上传文件!");
        }
        System.out.println("=========开始上传头像======================================");
        String userId = SecurityUtils.getCurrentUserId();
        // 上传文件并更新用户地址
        String uploadUrl = userService.updateUserAvatorById(userId, file);
        System.out.println("==========头像上传完毕======================================");
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

