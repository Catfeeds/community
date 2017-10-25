package com.tongwii.controller;

import com.tongwii.constant.MessageConstants;
import com.tongwii.domain.MessageCommentEntity;
import com.tongwii.dto.NeighborMessageDto;
import com.tongwii.security.SecurityUtils;
import com.tongwii.service.MessageCommentService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by admin on 2017/10/24.
 */
@RestController
@RequestMapping("/message_comment")
public class MessageCommentController {
    @Autowired
    private MessageCommentService messageCommentService;

    /**
     * 点赞接口
     */
    @PostMapping("/updateIsLikeInfo")
    public ResponseEntity updateIsLikeInfo(@RequestBody NeighborMessageDto messageEntity) {
        // 得到当前用户Id
        String userId = SecurityUtils.getCurrentUserId();
        Integer likeNum = messageEntity.getLikeNum();
        Boolean isLike = false;// 为true表示已经赞过了
        // 通过userId与传来的messageId查询点赞记录
        List<MessageCommentEntity> commentEntities = messageCommentService.findByMessageIdAndCommentatorIdAndType(messageEntity.getId(), userId, MessageConstants.ISLIKE);
        // 首先需要通过传来的messageId与commentorId查询是否存在记录，如果不存在则说明该用户还没有点赞过该消息，就要进行信息的增加操作
        if (CollectionUtils.isEmpty(commentEntities)) {
            // 添加记录
            MessageCommentEntity messageCommentEntity = new MessageCommentEntity();
            messageCommentEntity.setIsLike(true);
            messageCommentEntity.setCommentatorId(userId);
            messageCommentEntity.setMessageId(messageEntity.getId());
            messageCommentEntity.setCommentDate(new Timestamp(System.currentTimeMillis()));
            messageCommentEntity.setType(MessageConstants.ISLIKE);
            messageCommentService.addMessageComment(messageCommentEntity);
            isLike = false;
        } else {
            // 如果存在，则说明该用户已经对该消息进行过点赞或者非点赞或者评论，此时需要做的是修改记录的操作
            for (MessageCommentEntity messageCommentEntity : commentEntities) {
                if(messageCommentEntity.getIsLike()){
                    isLike = true;
                    for (MessageCommentEntity commentEntity : commentEntities) {
                        commentEntity.setIsLike(!messageCommentEntity.getIsLike());
                        messageCommentService.addMessageComment(commentEntity);
                    }
                }else{
                    isLike = false;
                    for (MessageCommentEntity commentEntity : commentEntities) {
                        commentEntity.setIsLike(!messageCommentEntity.getIsLike());
                        messageCommentService.addMessageComment(commentEntity);
                    }
                }
            }
        }
        if (isLike) {
            likeNum--;
        } else {
            likeNum++;
        }
        messageEntity.setLikeNum(likeNum);
        return ResponseEntity.ok(messageEntity);
    }

    /**
     * 评论接口
     */
    @PostMapping("/commentMessage/{comment}")
    public ResponseEntity commentMessage(@RequestBody NeighborMessageDto neighborMessageDto, @PathVariable String comment){
        // 获取当前用户
        String userId = SecurityUtils.getCurrentUserId();
        // 添加记录
        MessageCommentEntity messageCommentEntity = new MessageCommentEntity();
        messageCommentEntity.setComment(comment);
        messageCommentEntity.setCommentatorId(userId);
        messageCommentEntity.setMessageId(neighborMessageDto.getId());
        messageCommentEntity.setCommentDate(new Timestamp(System.currentTimeMillis()));
        messageCommentEntity.setType(MessageConstants.COMMENT);
        messageCommentService.addMessageComment(messageCommentEntity);

        Integer commentNum = neighborMessageDto.getCommentNum();
        commentNum = commentNum++;
        neighborMessageDto.setCommentNum(commentNum);
        return ResponseEntity.ok(neighborMessageDto);
    }

    /**
     * 根据messageId来获取评论列表
     */
    @GetMapping("/getCommentList/{messageId}")
    public ResponseEntity getCommentList(@PathVariable String messageId){
        List<MessageCommentEntity> messageCommentEntities = messageCommentService.findByMessageIdAndType(messageId, MessageConstants.COMMENT);
        // 封装返回到前台的数据
        JSONArray commentList = new JSONArray();
        for(MessageCommentEntity messageCommentEntity: messageCommentEntities){
            JSONObject commentObject = new JSONObject();
            commentObject.put("account", messageCommentEntity.getUserByCommentatorId().getAccount());
            commentObject.put("comment", messageCommentEntity.getComment());
            commentObject.put("commentDate", messageCommentEntity.getCommentDate());
            commentList.add(commentObject);
        }
        return ResponseEntity.ok(commentList);
    }
}
