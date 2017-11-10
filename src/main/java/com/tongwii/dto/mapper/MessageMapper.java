package com.tongwii.dto.mapper;

import com.tongwii.domain.Message;
import com.tongwii.dto.MessageDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-25
 */
@Service
public class MessageMapper {

    public MessageDto messageToMessageDto(Message message) {
        if ( message == null ) {
            return null;
        }

        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setTitle(message.getTitle());
        messageDto.setContent(message.getContent());
        messageDto.setCreateTime(message.getCreateTime());
        messageDto.setCreateUser(message.getCreateUser().getAccount());
        messageDto.setMessageTypeId(message.getMessageTypeId());
        if(Objects.nonNull(message.getMessageType())) {
            messageDto.setMessageTypeCode(message.getMessageType().getCode());
        }
        return messageDto;
    }


    public List<MessageDto> messagesToMessageDtos(List<Message> messages) {
        return messages.stream()
            .filter(Objects::nonNull)
            .map(this::messageToMessageDto)
            .collect(Collectors.toList());
    }
}
