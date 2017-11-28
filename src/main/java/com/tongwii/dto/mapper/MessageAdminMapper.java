package com.tongwii.dto.mapper;

import com.tongwii.domain.Message;
import com.tongwii.dto.MessageAdminDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Message and its DTO MessageDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, FileMapper.class, ResidenceMapper.class})
public interface MessageAdminMapper extends EntityMapper<MessageAdminDTO, Message> {

    @Mapping(source = "createUser.account", target = "createUser")
    @Mapping(source = "file.id", target = "fileId")
    MessageAdminDTO toDto(Message message);

    @Mapping(source = "createUser", target = "createUser.account")
    @Mapping(source = "fileId", target = "file")
    @Mapping(target = "messageComments", ignore = true)
    Message toEntity(MessageAdminDTO messageDTO);

    default Message fromId(String id) {
        if (id == null) {
            return null;
        }
        Message message = new Message();
        message.setId(id);
        return message;
    }
}
