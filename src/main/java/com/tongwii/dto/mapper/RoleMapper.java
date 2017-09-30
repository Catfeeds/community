package com.tongwii.dto.mapper;

import com.tongwii.domain.RoleEntity;
import com.tongwii.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-26
 */
@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper( RoleMapper.class );

    RoleDto RoleToRoleDto(RoleEntity role);
}
