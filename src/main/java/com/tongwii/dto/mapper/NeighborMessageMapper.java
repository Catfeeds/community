package com.tongwii.dto.mapper;

import com.tongwii.domain.Message;
import com.tongwii.dto.NeighborMessageDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by admin on 2017/10/24.
 */
@Service
public class NeighborMessageMapper {
    NeighborMessageDto messageToNeighborMessageDto(Message message) {
        if ( message == null ) {
            return null;
        }

        NeighborMessageDto neighborMessageDto = new NeighborMessageDto();
        neighborMessageDto.setId(message.getId());
        neighborMessageDto.setTitle(message.getTitle());
        neighborMessageDto.setContent(message.getContent());
        neighborMessageDto.setCreateTime(message.getCreateTime());
        neighborMessageDto.setCreateUser(message.getCreateUser().getAccount());
        neighborMessageDto.setMessageTypeId(message.getMessageTypeId());
        return neighborMessageDto;
    }


    public List<NeighborMessageDto> messagesToNeighborMessageDtos(List<Message> messages) {
        return messages.stream()
            .filter(Objects::nonNull)
            .map(this::messageToNeighborMessageDto)
            .collect(Collectors.toList());
    }
}
