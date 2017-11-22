package com.tongwii.dto.mapper;

import com.tongwii.domain.Message;
import com.tongwii.dto.PushMessageDto;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 推送消息转换消息Mapper
 *
 * @author Zeral
 * @date 2017-11-22
 */
public class PushMessageMapper {
    public static PushMessageDto messageToPushMessageDto(Message message) {
        if ( message == null ) {
            return null;
        }

        PushMessageDto messageDto = new PushMessageDto();
        messageDto.setTitle(message.getTitle());
        messageDto.setMessage(message.getContent());
        Optional.ofNullable(message.getMessageType()).ifPresent(messageType -> messageDto.setMessageTypeCode(messageType.getCode()));
        return messageDto;
    }


    public static List<PushMessageDto> messagesToPushMessageDtos(List<Message> messages) {
        return messages.stream()
            .filter(Objects::nonNull)
            .map(PushMessageMapper::messageToPushMessageDto)
            .collect(Collectors.toList());
    }
}
