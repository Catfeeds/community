package com.tongwii.controller;

import com.tongwii.bean.TongWIIResult;
import com.tongwii.core.Result;
import com.tongwii.po.FloorEntity;
import com.tongwii.po.RoomEntity;
import com.tongwii.po.UserEntity;
import com.tongwii.service.IFloorService;
import com.tongwii.service.IUserRoomService;
import com.tongwii.service.IUserService;
import com.tongwii.util.Encoder.MD5PasswordEncoder;
import com.tongwii.util.TokenUtil;
import com.tongwii.util.VOUtil;
import com.tongwii.vo.RoomVO;
import com.tongwii.vo.UserVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService userService;
	@Autowired
    private IFloorService floorService;
	@Autowired
    private IUserRoomService userRoomService;
	
	private TongWIIResult result = new TongWIIResult();
	private MD5PasswordEncoder md5PasswordEncoder = new MD5PasswordEncoder();
	
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
				result.errorResult("用户账号不可为空!");
                return result;
            }
			if(StringUtils.isEmpty(user.getPassword())){
				result.errorResult("密码不可为空!");
				return result;
			}
			//通过用户名查询用户的所有信息
			UserEntity findUser = userService.findByAccount(user.getAccount());
			if(Objects.isNull(findUser)){
				result.errorResult("用户不存在!");
                return result;
            }
			if (findUser.getPassword().equals(md5PasswordEncoder.encoder(user.getPassword()))) {
				// 用户设置token
				String token = TokenUtil.generateToken(host, findUser);
				// 基本用户信息
				UserVO userVO = VOUtil.transformUserToVO(findUser);
                List<RoomEntity> roomEntities = userRoomService.findRoomByUserId(userVO.getId());
                List<RoomVO> roomVOS = new ArrayList<>();
                for (RoomEntity roomEntity : roomEntities) {
                    RoomVO roomVO = new RoomVO();
                    Map<String, FloorEntity> floorMap = floorService.findFloorById(roomEntity.getUnitId());
                    roomVO.setRoomId(roomEntity.getId());
                    roomVO.setRoomCode(roomEntity.getRoomCode());
                    roomVO.setChargeName(roomEntity.getUserByOwnerId().getName());
                    roomVO.setChargePhone(roomEntity.getUserByOwnerId().getPhone());
//                    roomVO.setRoomFloor(floorMap.get(FloorEntity.DONG).getName() + floorMap.get(FloorEntity.UNIT).getName() + roomEntity.getRoomCode());
					roomVO.setRoomFloor(floorMap.get(FloorEntity.UNIT).getName() + floorMap.get(FloorEntity.UNIT).getParentCode()+ "单元" + roomEntity.getRoomCode());
                    roomVOS.add(roomVO);
                }
                // 设置房间信息
                userVO.setRooms(roomVOS);
                // 设置token
                userVO.setToken(token);
				result.successResult("登陆成功!", userVO);
				return result;
			} else {
				result.errorResult("密码错误!");
				return result;
			}
		} catch (Exception e) {
			result.errorResult("登陆出错!", e.getMessage());
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

	// 修改用户头像
	@PostMapping("/updateNickName")
	public TongWIIResult updateNickName(@RequestParam("nickName") String nickName, @RequestParam("token")String token) {
        try {
            String userId = TokenUtil.getUserIdFromToken(token);
            UserEntity userEntity = userService.findById(userId);
            userEntity.setNickName(nickName);
            userService.update(userEntity);
            result.successResult();
            return result;
        } catch (Exception e) {
            result.errorResult();
            return result;
        }
    }

    // 修改用户电话
    @PostMapping("/updatePhone")
    public Result updatePhone(@RequestParam("phone") String phone, @RequestParam("token")String token) {
        try {
            String userId = TokenUtil.getUserIdFromToken(token);
            UserEntity userEntity = userService.findById(userId);
            userEntity.setPhone(phone);
            userService.update(userEntity);
            return Result.successResult(userEntity);
        } catch (Exception e) {
            result.errorResult();
            return Result.errorResult("修改失败!");
        }
    }

	@GetMapping(value = "/test")
	public String test() {
		return "hello";
	}
}