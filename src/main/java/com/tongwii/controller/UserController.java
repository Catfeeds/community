package com.tongwii.controller;

import com.tongwii.bean.TongWIIResult;
import com.tongwii.po.UserEntity;
import com.tongwii.service.IUserService;
import com.tongwii.util.TokenUtil;
import com.tongwii.util.VOUtil;
import com.tongwii.vo.UserVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService userService;
	
	private TongWIIResult result = new TongWIIResult();

	
	// 用户注册接口
	@PostMapping("/regist")
	public TongWIIResult regist(@RequestBody UserEntity user)  {
		if(Objects.nonNull(userService.findByAccount(user.getAccount()))){
			result.errorResult("用户已存在！");
			return result;
		}
		// 在此调用用户注册的服务
		try {
			userService.save(user);
			result.successResult("注册成功", user);
			return result;
		} catch (Exception e) {
			result.errorResult("注册失败", e.getMessage());
		}
		return result;
	}

	// 用户登录接口
	@PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public TongWIIResult login(@RequestBody UserEntity user, @RequestHeader("Host") String host) {
		try {
			if(StringUtils.isEmpty(user.getAccount())){
				result.errorResult("用户账号不可为空");
                return result;
            }
			if(StringUtils.isEmpty(user.getPassword())){
				result.errorResult("密码不可为空");
                return result;
            }
			//通过用户名查询用户的所有信息
			UserEntity findUser = userService.findByAccount(user.getAccount());
			if(Objects.isNull(findUser)){
				result.errorResult("用户不存在");
                return result;
            }
			if (findUser.getPassword().equals(user.getPassword())) {
				// 用户设置token
				String token = TokenUtil.generateToken(host, findUser);
				UserVO userVO = VOUtil.transformUserToVO(findUser);
				userVO.setToken(token);
				result.successResult("登陆成功", userVO);
				return result;
			} else {
				result.errorResult("密码错误！");
				return result;
			}
		} catch (Exception e) {
			result.errorResult("登陆出错！", e.getMessage());
			return result;
		}
	}

	// 上传用户头像
	@PostMapping(path = "/uploadAvatar", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public TongWIIResult uploadAvatar(@RequestParam("file") MultipartFile file, @RequestParam("token")String token, HttpServletResponse response) {
		try {
			System.out.println("=========开始上传头像======================================");
			String userId = TokenUtil.getUserIdFromToken(token);
			// 上传文件并更新用户地址
			String uploadUrl = userService.updateUserAvatorById(userId, file);

			result.successResult("头像上传成功", uploadUrl);
			System.out.println("==========头像上传完毕======================================");
			// 使用了上传文件的输出流和response的返回json会出错，重置response
			response.reset();
			return result;
		} catch (Exception e) {
			result.errorResult("头像上传失败");
			response.reset();
			return result;
		}
	}

	@GetMapping(value = "/test")
	public String test() {
		return "hello";
	}
}