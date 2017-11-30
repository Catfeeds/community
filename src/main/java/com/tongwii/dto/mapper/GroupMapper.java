package com.tongwii.dto.mapper;

import com.tongwii.domain.Group;
import com.tongwii.dto.GroupDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity SubGroup and its DTO GroupDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface GroupMapper extends EntityMapper<GroupDTO, Group> {

    @Mapping(source = "group.id", target = "groupId")
    GroupDTO toDto(Group group);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "groups", ignore = true)
    @Mapping(source = "groupId", target = "group")
    Group toEntity(GroupDTO groupDTO);

    default Group fromId(String id) {
        if (id == null) {
            return null;
        }
        Group subGroup = new Group();
        subGroup.setId(id);
        return subGroup;
    }
}
