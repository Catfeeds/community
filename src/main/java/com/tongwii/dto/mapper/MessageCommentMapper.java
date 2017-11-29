package com.tongwii.dto.mapper;

import com.tongwii.domain.MessageComment;
import com.tongwii.dto.MessageCommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity MessageComment and its DTO MessageCommentDTO.
 */
@Mapper(componentModel = "spring")
public interface MessageCommentMapper extends EntityMapper<MessageCommentDTO, MessageComment> {

    @Mapping(source = "commentator.account", target = "commentator")
    @Mapping(source = "message.id", target = "messageId")
    MessageCommentDTO toDto(MessageComment messageComment);

    @Mapping(source = "commentator", target = "commentator.account")
    @Mapping(source = "messageId", target = "message.id")
    MessageComment toEntity(MessageCommentDTO messageCommentDTO);

    default MessageComment fromId(String id) {
        if (id == null) {
            return null;
        }
        MessageComment messageComment = new MessageComment();
        messageComment.setId(id);
        return messageComment;
    }
}
