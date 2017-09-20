package com.tongwii.controller;

import com.tongwii.core.Result;
import com.tongwii.domain.UserEntity;
import com.tongwii.dto.UserDTO;
import com.tongwii.dto.mapper.UserMapper;
import com.tongwii.security.SecurityUtils;
import com.tongwii.security.jwt.JWTConfigurer;
import com.tongwii.security.jwt.TokenProvider;
import com.tongwii.service.IUserService;
import com.tongwii.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService userService;
	@Autowired
    private TokenProvider tokenProvider;
	@Autowired
    private AuthenticationManager authenticationManager;
	@Autowired
    private UserMapper userMapper;

	// 用户注册接口
	@PostMapping("/regist")
	public Result regist(@RequestBody UserEntity user)  {
		if(Objects.nonNull(userService.findByAccount(user.getAccount()))){
			return Result.errorResult("用户已存在");
		}
		// 在此调用用户注册的服务
        userService.save(user);
        return Result.successResult("注册成功").add("user", user);
	}

	// 用户登录接口
	@PostMapping("/login")
	public Result login(@RequestBody UserEntity user) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getAccount(), user.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        // 基本用户信息
        UserDTO userDTO = userMapper.userToUserDTO(user);
        return Result.successResult("登录成功").add(JWTConfigurer.AUTHORIZATION_HEADER,  "Bearer " + jwt).add("userInfo", userDTO);
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
	@PostMapping("/updateNickName")
	public Result updateNickName(@RequestParam("nickName") String nickName) {
        String userId = SecurityUtils.getCurrentUserId();
        UserEntity userEntity = userService.findById(userId);
        userEntity.setNickName(nickName);
        userService.update(userEntity);
        return Result.successResult(userEntity);
    }

	// 修改个性签名
	@PostMapping("/updateSignature")
	public Result updateSignature(@RequestBody Map map) {
        String userId = TokenUtil.getUserIdFromToken(map.get("token").toString());
        UserEntity userEntity = userService.findById(userId);
        userEntity.setSignature(map.get("signature").toString());
        userService.update(userEntity);
        return Result.successResult(userEntity);
	}

    // 修改用户电话
    @PostMapping("/updatePhone")
    public Result updatePhone(@RequestParam("phone") String phone) {
        String userId = SecurityUtils.getCurrentUserId();
        UserEntity userEntity = userService.findById(userId);
        userEntity.setPhone(phone);
        userService.update(userEntity);
        return Result.successResult(userEntity);
    }

	@GetMapping(value = "/test")
	public ResponseEntity<Object> test() {
		List<UserEntity> userEntities = userService.findAll();
		return ResponseEntity.ok(userMapper.usersToUserDTOs(userEntities));
	}
}

