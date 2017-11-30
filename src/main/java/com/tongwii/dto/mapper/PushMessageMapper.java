package com.tongwii.dto.mapper;

import com.tongwii.domain.Message;
import com.tongwii.dto.PushMessageDTO;
import org.springframework.stereotype.Component;

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
@Component
public class PushMessageMapper {
    public PushMessageDTO toDto(Message message) {
        if ( message == null ) {
            return null;
        }

        PushMessageDTO messageDto = new PushMessageDTO();
        messageDto.setTitle(message.getTitle());
        messageDto.setMessage(message.getContent());
        Optional.ofNullable(message.getMessageType()).ifPresent(messageType -> messageDto.setMessageTypeCode(messageType.getCode()));
        return messageDto;
    }


    public List<PushMessageDTO> toDto(List<Message> messages) {
        return messages.stream()
            .filter(Objects::nonNull)
            .map(this::toDto)
            .collect(Collectors.toList());
    }
}
