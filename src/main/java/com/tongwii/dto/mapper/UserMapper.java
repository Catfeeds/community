package com.tongwii.dto.mapper;

import com.tongwii.domain.*;
import com.tongwii.dto.RoomDto;
import com.tongwii.dto.UserDto;
import com.tongwii.service.FloorService;
import com.tongwii.service.UserRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the entity User and its DTO called UserDto.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserMapper {

    @Autowired
    private UserRoomService userRoomService;

    @Autowired
    private FloorService floorService;

    public UserDto userToUserDTO(UserEntity user) {
        UserDto userDTO = new UserDto();
        userDTO.setId(user.getId());
        userDTO.setAccount(user.getAccount());
        userDTO.setNickName(user.getNickName());
        userDTO.setAvatarFileSrc(Objects.nonNull(user.getFileByAvatarFileId()) ? user.getFileByAvatarFileId().getFilePath() : null);
        userDTO.setPhone(user.getPhone());
        userDTO.setSignature(user.getSignature());
        userDTO.setAddTime(user.getAddTime());
        userDTO.setClientId(user.getClientId());
        userDTO.setIdCard(user.getIdCard());
        userDTO.setBirthday(user.getBirthday());
        userDTO.setName(user.getName());
        userDTO.setSex(user.getSex());
        userDTO.setState(user.getState());
        if(!CollectionUtils.isEmpty(user.getUserRolesById())) {
            userDTO.setRoles(user.getUserRolesById().stream().map(UserRoleEntity::getRoleByRoleId).map(RoleEntity::getCode).collect(Collectors.toList()));
        }
        List<RoomDto> roomDTOS = userRoomService.findRoomByUserId(userDTO.getId()).stream().map(userRoomEntity -> {
            RoomEntity roomEntity = userRoomEntity.getRoomByRoomId();
            RoomDto roomDTO = new RoomDto();
            Map<String, FloorEntity> floorMap = floorService.findFloorById(roomEntity.getFloorId());
            roomDTO.setRoomId(roomEntity.getId());
            roomDTO.setUnitCode(roomEntity.getUnitCode());
            roomDTO.setRoomCode(roomEntity.getRoomCode());
            roomDTO.setChargeName(roomEntity.getUserByOwnerId().getName());
            roomDTO.setChargePhone(roomEntity.getUserByOwnerId().getPhone());
            roomDTO.setRoomFloor(floorMap.get(FloorEntity.UNIT).getCode() + roomEntity.getUnitCode()+ "单元" + roomEntity.getRoomCode());
            return roomDTO;
        }).collect(Collectors.toList());
        userDTO.setRooms(roomDTOS);
        return userDTO;
    }

    public List<UserDto> usersToUserDTOs(List<UserEntity> users) {
        return users.stream()
            .filter(Objects::nonNull)
            .map(this::userToUserDTO)
            .collect(Collectors.toList());
    }

    public UserEntity userDTOToUser(UserDto userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            UserEntity user = new UserEntity();
            user.setId(user.getId());
            user.setAccount(user.getAccount());
            user.setNickName(user.getNickName());
            user.setPhone(user.getPhone());
            user.setSignature(user.getSignature());
            user.setAddTime(user.getAddTime());
            user.setClientId(user.getClientId());
            user.setIdCard(user.getIdCard());
            user.setBirthday(user.getBirthday());
            user.setName(user.getName());
            user.setSex(user.getSex());
            user.setState(user.getState());
            return user;
        }
    }

    public List<UserEntity> userDTOsToUsers(List<UserDto> userDTOs) {
        return userDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::userDTOToUser)
            .collect(Collectors.toList());
    }


    public List<RoleEntity> rolesFromStrings(Set<String> strings) {
        return strings.stream().map(string -> {
            RoleEntity role = new RoleEntity();
            role.setCode(string);
            return role;
        }).collect(Collectors.toList());
    }
}
