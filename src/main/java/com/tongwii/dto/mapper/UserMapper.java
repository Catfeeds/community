package com.tongwii.dto.mapper;

import com.tongwii.domain.*;
import com.tongwii.dto.RoomDTO;
import com.tongwii.dto.UserDTO;
import com.tongwii.service.FloorService;
import com.tongwii.service.UserRoomService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Mapper for the entity User and its DTO called UserDTO.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserMapper {

    private final UserRoomService userRoomService;

    private final FloorService floorService;

    public UserMapper(UserRoomService userRoomService, FloorService floorService) {
        this.userRoomService = userRoomService;
        this.floorService = floorService;
    }

    public UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setAccount(user.getAccount());
        userDTO.setNickName(user.getNickName());
        userDTO.setAvatarFileSrc(Objects.nonNull(user.getAvatarFile()) ? user.getAvatarFile().getFilePath() : null);
        userDTO.setPhone(user.getPhone());
        userDTO.setSignature(user.getSignature());
        userDTO.setAddTime(user.getAddTime());
        userDTO.setIdCard(user.getIdCard());
        userDTO.setBirthday(user.getBirthday());
        userDTO.setName(user.getName());
        userDTO.setSex(user.getSex());
        userDTO.setLangKey(user.getLangKey());
        userDTO.setActivated(user.isActivated());
        userDTO.setDevices(Optional.ofNullable(user.getDevices()).orElse(new HashSet<>()).stream().map(Device::getDeviceId).collect(Collectors.toSet()));
        userDTO.setRoles(Optional.ofNullable(user.getRoles()).orElse(new HashSet<>()).stream().map(Role::getCode).collect(Collectors.toSet()));
        List<RoomDTO> roomDTOS = userRoomService.findRoomByUserId(userDTO.getId()).stream().map(userRoomEntity -> {
            Room room = userRoomEntity.getRoomByRoomId();
            RoomDTO roomDTO = new RoomDTO();
            Map<String, Floor> floorMap = floorService.findFloorById(room.getFloorId());
            roomDTO.setRoomId(room.getId());
            roomDTO.setUnitCode(room.getUnitCode());
            roomDTO.setRoomCode(room.getRoomCode());
            roomDTO.setChargeName(room.getUserByOwnerId().getName());
            roomDTO.setChargePhone(room.getUserByOwnerId().getPhone());
            roomDTO.setRoomFloor(floorMap.get(Floor.UNIT).getCode() + room.getUnitCode()+ "单元" + room.getRoomCode());
            return roomDTO;
        }).collect(Collectors.toList());
        userDTO.setRooms(roomDTOS);
        return userDTO;
    }

    public List<UserDTO> usersToUserDTOs(List<User> users) {
        return users.stream()
            .filter(Objects::nonNull)
            .map(this::userToUserDTO)
            .collect(Collectors.toList());
    }

    public User userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            User user = new User();
            user.setId(userDTO.getId());
            user.setAccount(userDTO.getAccount());
            user.setNickName(userDTO.getNickName());
            user.setPhone(userDTO.getPhone());
            user.setSignature(userDTO.getSignature());
            user.setAddTime(userDTO.getAddTime());
            user.setIdCard(userDTO.getIdCard());
            user.setBirthday(userDTO.getBirthday());
            user.setName(userDTO.getName());
            user.setSex(userDTO.getSex());
            user.setActivated(userDTO.isActivated());
            return user;
        }
    }

    public List<User> userDTOsToUsers(List<UserDTO> userDTOS) {
        return userDTOS.stream()
            .filter(Objects::nonNull)
            .map(this::userDTOToUser)
            .collect(Collectors.toList());
    }


    public Set<Role> rolesFromStrings(Set<String> strings) {
        return strings.stream().map(string -> {
            Role role = new Role();
            role.setCode(string);
            return role;
        }).collect(Collectors.toSet());
    }

    public User userFromId(String id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
