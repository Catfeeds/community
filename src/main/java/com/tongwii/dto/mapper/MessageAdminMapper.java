package com.tongwii.dto.mapper;

import com.tongwii.domain.File;
import com.tongwii.domain.Message;
import com.tongwii.domain.User;
import com.tongwii.dto.MessageAdminDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageAdminMapper {

    private final ResidenceMapper residenceMapper;

    public MessageAdminMapper(ResidenceMapper residenceMapper) {
        this.residenceMapper = residenceMapper;
    }

    public List<Message> toEntity(List<MessageAdminDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Message> list = new ArrayList<>();
        for ( MessageAdminDTO messageAdminDTO : arg0 ) {
            list.add( toEntity( messageAdminDTO ) );
        }

        return list;
    }

    public List<MessageAdminDTO> toDto(List<Message> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<MessageAdminDTO> list = new ArrayList<>();
        for ( Message message : arg0 ) {
            list.add( toDto( message ) );
        }

        return list;
    }

    public MessageAdminDTO toDto(Message message) {
        if ( message == null ) {
            return null;
        }

        MessageAdminDTO messageAdminDTO_ = new MessageAdminDTO();

        messageAdminDTO_.setCreateUser( messageCreateUserAccount( message ) );
        messageAdminDTO_.setFileId( messageFileId( message ) );
        messageAdminDTO_.setId( message.getId() );
        messageAdminDTO_.setTitle( message.getTitle() );
        messageAdminDTO_.setContent( message.getContent() );
        messageAdminDTO_.setCreateTime( message.getCreateTime() );
        messageAdminDTO_.setProcessState( message.getProcessState() );
        messageAdminDTO_.setRepairStartTime( message.getRepairStartTime() );
        messageAdminDTO_.setRepairEndTime( message.getRepairEndTime() );
        messageAdminDTO_.setMessageType( message.getMessageType() );
        messageAdminDTO_.setResidence( residenceMapper.toDto( message.getResidence() ) );

        return messageAdminDTO_;
    }

    public Message toEntity(MessageAdminDTO messageDTO) {
        if ( messageDTO == null ) {
            return null;
        }

        Message message_ = new Message();

        User createUser = new User();
        message_.setCreateUser( createUser );

        createUser.setAccount( messageDTO.getCreateUser() );
        message_.setId( messageDTO.getId() );
        message_.setTitle( messageDTO.getTitle() );
        message_.setContent( messageDTO.getContent() );
        message_.setFileId( messageDTO.getFileId() );
        message_.setCreateTime( messageDTO.getCreateTime() );
        message_.setProcessState( messageDTO.getProcessState() );
        message_.setRepairStartTime( messageDTO.getRepairStartTime() );
        message_.setRepairEndTime( messageDTO.getRepairEndTime() );
        message_.setMessageType( messageDTO.getMessageType() );
        message_.setResidence( residenceMapper.toEntity( messageDTO.getResidence() ) );

        return message_;
    }

    private String messageCreateUserAccount(Message message) {

        if ( message == null ) {
            return null;
        }
        String id = message.getCreateUserId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String messageFileId(Message message) {

        if ( message == null ) {
            return null;
        }
        File file = message.getFile();
        if ( file == null ) {
            return null;
        }
        String id = file.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
