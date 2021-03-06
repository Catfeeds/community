package com.tongwii.dto.mapper;

import com.tongwii.domain.Message;
import com.tongwii.dto.MessageDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-25
 */
@Component
public class MessageMapper {

    public MessageDTO toDto(Message message) {
        if ( message == null ) {
            return null;
        }

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(message.getId());
        messageDTO.setTitle(message.getTitle());
        messageDTO.setContent(message.getContent());
        messageDTO.setCreateTime(message.getCreateTime());
        messageDTO.setCreateUser(message.getCreateUser().getAccount());
        messageDTO.setMessageType(message.getMessageType());
        return messageDTO;
    }


    public List<MessageDTO> toDto(List<Message> messages) {
        return messages.stream()
            .filter(Objects::nonNull)
            .map(this::toDto)
            .collect(Collectors.toList());
    }
}
