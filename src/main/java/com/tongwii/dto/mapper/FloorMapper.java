package com.tongwii.dto.mapper;

import com.tongwii.domain.Floor;
import com.tongwii.dto.FloorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Floor and its DTO FloorDTO.
 */
@Mapper(componentModel = "spring", uses = {ResidenceMapper.class, UserMapper.class})
public interface FloorMapper extends EntityMapper<FloorDTO, Floor> {

    FloorDTO toDto(Floor floor);

    @Mapping(target = "rooms", ignore = true)
    Floor toEntity(FloorDTO floorDTO);
}
