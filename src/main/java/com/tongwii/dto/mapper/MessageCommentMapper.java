package com.tongwii.dto.mapper;

import com.tongwii.domain.Message;
import com.tongwii.domain.MessageComment;
import com.tongwii.domain.User;
import com.tongwii.dto.MessageCommentDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageCommentMapper {

    public List<MessageComment> toEntity(List<MessageCommentDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<MessageComment> list = new ArrayList<>();
        for ( MessageCommentDTO messageCommentDTO : arg0 ) {
            list.add( toEntity( messageCommentDTO ) );
        }

        return list;
    }

    public List<MessageCommentDTO> toDto(List<MessageComment> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<MessageCommentDTO> list = new ArrayList<>();
        for ( MessageComment messageComment : arg0 ) {
            list.add( toDto( messageComment ) );
        }

        return list;
    }

    public MessageCommentDTO toDto(MessageComment messageComment) {
        if ( messageComment == null ) {
            return null;
        }

        MessageCommentDTO messageCommentDTO_ = new MessageCommentDTO();

        messageCommentDTO_.setMessageId( messageCommentMessageId( messageComment ) );
        messageCommentDTO_.setCommentator( messageCommentCommentatorAccount( messageComment ) );
        messageCommentDTO_.setId( messageComment.getId() );
        messageCommentDTO_.setIsLike( messageComment.getIsLike() );
        messageCommentDTO_.setComment( messageComment.getComment() );
        messageCommentDTO_.setCommentDate( messageComment.getCommentDate() );
        messageCommentDTO_.setType( messageComment.getType() );

        return messageCommentDTO_;
    }

    public MessageComment toEntity(MessageCommentDTO messageCommentDTO) {
        if ( messageCommentDTO == null ) {
            return null;
        }

        MessageComment messageComment_ = new MessageComment();

        Message message = new Message();
        User commentator = new User();
        messageComment_.setMessage( message );
        messageComment_.setCommentator( commentator );

        commentator.setAccount( messageCommentDTO.getCommentator() );
        message.setId( messageCommentDTO.getMessageId() );
        messageComment_.setId( messageCommentDTO.getId() );
        messageComment_.setMessageId( messageCommentDTO.getMessageId() );
        messageComment_.setIsLike( messageCommentDTO.getIsLike() );
        messageComment_.setComment( messageCommentDTO.getComment() );
        messageComment_.setCommentDate( messageCommentDTO.getCommentDate() );
        messageComment_.setType( messageCommentDTO.getType() );

        return messageComment_;
    }

    private String messageCommentMessageId(MessageComment messageComment) {

        if ( messageComment == null ) {
            return null;
        }
        Message message = messageComment.getMessage();
        if ( message == null ) {
            return null;
        }
        String id = message.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String messageCommentCommentatorAccount(MessageComment messageComment) {

        if ( messageComment == null ) {
            return null;
        }
        User commentator = messageComment.getCommentator();
        if ( commentator == null ) {
            return null;
        }
        String account = commentator.getAccount();
        if ( account == null ) {
            return null;
        }
        return account;
    }
}
