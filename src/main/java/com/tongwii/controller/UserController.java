package com.tongwii.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tongwii.bean.TongWIIResult;
import com.tongwii.constant.ResultConstants;
import com.tongwii.po.UserEntity;
import com.tongwii.service.IUserService;
import com.tongwii.util.TokenUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@SessionAttributes("token")
public class UserController {
	@Autowired
	private IUserService userService;
	
	private TongWIIResult result = new TongWIIResult();

	
	// �û�ע��ӿ�
	@RequestMapping(value = "/regist", method = RequestMethod.POST, produces={"application/json;charset=UTF-8"})
	public TongWIIResult regist(@RequestBody UserEntity user)  {
		if(Objects.nonNull(userService.findByAccount(user.getAccount()))){
			result.errorResult("�û��Ѵ��ڣ�");
			return result;
		}
		// �ڴ˵����û�ע��ķ���
		try {
			userService.save(user);
			result.successResult("ע��ɹ�", user);
			return result;
		} catch (Exception e) {
			result.errorResult("ע��ʧ��", e.getMessage());
		}
		return result;
	}

	// �û���¼�ӿ�
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces={"application/json;charset=UTF-8"})
	public TongWIIResult login(@RequestBody UserEntity user, @RequestHeader("Host") String host, Model model) {
		try {
			if(StringUtils.isEmpty(user.getAccount())){
				result.errorResult("�û��˺Ų���Ϊ��");
                return result;
            }
			if(StringUtils.isEmpty(user.getPassword())){
				result.errorResult("���벻��Ϊ��");
                return result;
            }
			//ͨ���û�����ѯ�û���������Ϣ
			UserEntity findUser = userService.findByAccount(user.getAccount());
			if(Objects.isNull(findUser)){
				result.errorResult("�û�������");
                return result;
            }
			if (findUser.getPassword().equals(user.getPassword())) {
				// �û�����token
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("account", user.getAccount());
				jsonObject.put("userId", user.getId());
				String token = TokenUtil.createToken(host, jsonObject.toString());
				model.addAttribute("token", token);
				result.successResult("��½�ɹ�", findUser);
				return result;
			} else {
				result.errorResult("�������");
				return result;
			}
		} catch (Exception e) {
			result.errorResult("��½����", e.getMessage());
			return result;
		}
	}

	// �ϴ��û�ͷ��
	@RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
	public TongWIIResult uploadAvatar(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println("=========��ʼ�ϴ�ͷ��======================================");
			String token = request.getSession().getAttribute("token").toString();
			UserEntity userEntity = TokenUtil.getUserInfoFormToken(token);
			if(Objects.nonNull(userEntity)) {
				// �ϴ��ļ��������û���ַ
				String uploadUrl = userService.updateUserAvatorById(userEntity.getId(), file);

				result.successResult("ͷ���ϴ��ɹ�", uploadUrl);
				System.out.println("==========ͷ���ϴ����======================================");
				// ʹ�����ϴ��ļ����������response�ķ���json���������response
				response.reset();
				return result;
			} else {
				result.setStatus(ResultConstants.ILLEGAL);
				result.setInfo("��½״̬���Ϸ�");
				return result;
			}
		} catch (Exception e) {
			result.errorResult("ͷ���ϴ�ʧ��");
			response.reset();
			return result;
		}
	}

	@RequestMapping(value = "/test")
	public String test() {
		return "hello";
		
	}
}