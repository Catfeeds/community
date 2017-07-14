package com.tongwii.util;

import com.tongwii.po.UserEntity;
import com.tongwii.vo.UserVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VOUtil {

	public static UserVO transformUserToVO(UserEntity user) {
		UserVO userVO = new UserVO();
		userVO.setId(user.getId());
		userVO.setAccount(user.getAccount());
		userVO.setNickName(user.getNickName());
		userVO.setAvatarFileSrc(Objects.nonNull(user.getFileByAvatarFileId()) ? user.getFileByAvatarFileId().getFilePath() : null);
		userVO.setPhone(user.getPhone());
		userVO.setSignature(user.getSignature());
		userVO.setAddTime(user.getAddTime());
		userVO.setClientId(user.getClientId());
		userVO.setIdCard(user.getIdCard());
		userVO.setBirthday(user.getBirthday());
		userVO.setName(user.getName());
		userVO.setPassword(user.getPassword());
		userVO.setSex(user.getSex());
		userVO.setState(user.getState());
		return userVO;
	}
	
	public static final List<UserVO> transformUserToVOList(List<UserEntity> userList) {
		List<UserVO> userVOList = new ArrayList<UserVO>();
		for (UserEntity user : userList) {
			UserVO userVO = transformUserToVO(user);
			userVOList.add(userVO);
		}
		return userVOList;
	}

}

