package com.tongwii.dto.mapper;

import com.tongwii.domain.Residence;
import com.tongwii.dto.ResidenceDTO;
import com.tongwii.service.RegionService;
import com.tongwii.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 社区Dto、实体转换
 *
 * @author Zeral
 * @date 2017-11-24
 */
@Service
public class ResidenceMapper {
    private final UserService userService;
    private final RegionService regionService;
    private final UserMapper userMapper;

    public ResidenceMapper(UserService userService, RegionService regionService, UserMapper userMapper) {
        this.userService = userService;
        this.regionService = regionService;
        this.userMapper = userMapper;
    }

    public ResidenceDTO toDto(Residence residence) {
        if (residence == null) {
            return null;
        }

        ResidenceDTO residenceDTO = new ResidenceDTO();
        residenceDTO.setId(residence.getId());
        residenceDTO.setName(residence.getName());
        residenceDTO.setAddress(residence.getAddress());
        residenceDTO.setFloorCount(residence.getFloorCount());
        Optional.ofNullable(residence.getRegionCode()).ifPresent(regionCode -> residenceDTO.setRegion(regionService
            .findByRegionCode(regionCode)));
        Optional.ofNullable(residence.getUserId()).ifPresent(userId -> residenceDTO.setChargeUser(userMapper
            .userToUserDTO(userService.findById(userId))));
        return residenceDTO;
    }

   public Residence toEntity(ResidenceDTO residenceDTO) {
        if (residenceDTO == null) {
            return null;
        } else {
            Residence residence = new Residence();
            residence.setId(residenceDTO.getId());
            residence.setName(residenceDTO.getName());
            residence.setAddress(residenceDTO.getAddress());
            residence.setFloorCount(residenceDTO.getFloorCount());
            residence.setRegionCode(residenceDTO.getRegionCode());
            Optional.ofNullable(residenceDTO.getChargeUser()).ifPresent(chargeUser -> residence.setUserId(chargeUser.getId()));
            return residence;
        }
    }

    public List<ResidenceDTO> toDtos(List<Residence> residences) {
        return residences.stream().filter(Objects::nonNull).map(this::toDto).collect(Collectors.toList());
    }
}
