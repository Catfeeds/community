package com.tongwii.controller;

import com.tongwii.core.Result;
import com.tongwii.domain.UserEntity;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
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
	public ResponseEntity regist(@Valid @RequestBody UserEntity user)  {
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
	public ResponseEntity login(@RequestBody UserEntity user) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getAccount(), user.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        // 基本用户信息
        UserDto userDTO = userMapper.userToUserDTO(user);
        Map map = new HashMap();
        map.put("userInfo", userDTO);
        map.put("token", jwt);
        return ResponseEntity.ok(map);
	}

	// 上传用户头像
	@PostMapping("/uploadAvatar")
	public Result uploadAvatar(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        System.out.println("=========开始上传头像======================================");
        String userId = SecurityUtils.getCurrentUserId();
        // 上传文件并更新用户地址
        String uploadUrl = userService.updateUserAvatorById(userId, file);
        System.out.println("==========头像上传完毕======================================");
        // 使用了上传文件的输出流和response的返回json会出错，重置response
        response.reset();
        return Result.successResult("头像上传成功").add("uploadUrl", uploadUrl);
	}

	// 修改用户昵称
	@PutMapping("/updateNickName")
	public Result updateNickName(@RequestBody UserEntity user) {
        String userId = SecurityUtils.getCurrentUserId();
        UserEntity userEntity = userService.findById(userId);
        userEntity.setNickName(user.getNickName());
        userService.update(userEntity);
        return Result.successResult(userEntity);
    }

	// 修改个性签名
	@PutMapping("/updateSignature")
	public Result updateSignature(@RequestBody UserEntity user) {
        String userId = SecurityUtils.getCurrentUserId();
        UserEntity userEntity = userService.findById(userId);
        userEntity.setSignature(user.getSignature());
        userService.update(userEntity);
        return Result.successResult(userEntity);
	}

    // 修改用户电话
    @PutMapping("/updatePhone")
    public Result updatePhone(@RequestBody UserEntity user) {
        String userId = SecurityUtils.getCurrentUserId();
        UserEntity userEntity = userService.findById(userId);
        userEntity.setPhone(user.getPhone());
        userService.update(userEntity);
        return Result.successResult(userEntity);
    }

	@GetMapping(value = "/test")
	public ResponseEntity<Object> test() {
		List<UserEntity> userEntities = userService.findAll();
		return ResponseEntity.ok(userMapper.usersToUserDTOs(userEntities));
	}
}

