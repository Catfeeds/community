package com.tongwii.dto.mapper;

import com.tongwii.domain.MessageEntity;
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

    MessageDto messageToMessageDto(MessageEntity message) {
        if ( message == null ) {
            return null;
        }

        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setTitle(message.getTitle());
        messageDto.setContent(message.getContent());
        messageDto.setCreateTime(message.getCreateTime());
        messageDto.setCreateUser(message.getUserByCreateUserId().getAccount());
        return messageDto;
    }


    public List<MessageDto> messagesToMessageDtos(List<MessageEntity> messages) {
        return messages.stream()
            .filter(Objects::nonNull)
            .map(this::messageToMessageDto)
            .collect(Collectors.toList());
    }
}
