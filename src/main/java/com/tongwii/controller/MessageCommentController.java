package com.tongwii.controller;

import com.tongwii.constant.MessageConstants;
import com.tongwii.domain.MessageComment;
import com.tongwii.dto.MessageCommentDTO;
import com.tongwii.dto.NeighborMessageDTO;
import com.tongwii.security.SecurityUtils;
import com.tongwii.service.MessageCommentService;
import com.tongwii.service.UserService;
import com.tongwii.util.DateUtil;
import com.tongwii.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by admin on 2017/10/24.
 */
@RestController
@RequestMapping("/api/message_comment")
public class MessageCommentController {

    private final MessageCommentService messageCommentService;
    private final UserService userService;

    public MessageCommentController(MessageCommentService messageCommentService, UserService userService) {this.messageCommentService =
        messageCommentService;
        this.userService = userService;
    }

    /**
     * GET  /message_comment : get all the messageComments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of messageComments in body
     */
    @GetMapping
    public ResponseEntity<List<MessageCommentDTO>> getAllMessageComments(Pageable pageable) {
        Page<MessageCommentDTO> page = messageCommentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/message-comments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /message-comments : get all the messageComments by messageId.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of messageComments in body
     */
    @GetMapping("/messageComments/{messageId}")
    public ResponseEntity selectAnnounceMessage(@PathVariable String messageId, Pageable pageable) {
        Page<MessageCommentDTO> page = messageCommentService.findAllByMessageId(messageId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/message_comment/messageComments/"+messageId);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * 点赞接口
     */
    @PostMapping("/updateIsLikeInfo")
    public ResponseEntity updateIsLikeInfo(@RequestBody NeighborMessageDTO messageEntity) {
        // 得到当前用户Id
        String userId = SecurityUtils.getCurrentUserId();
        Integer likeNum = messageEntity.getLikeNum();
        Boolean isLike = false;// 为true表示已经赞过了
        // 通过userId与传来的messageId查询点赞记录
        List<MessageComment> commentEntities = messageCommentService.findByMessageIdAndCommentatorIdAndType(messageEntity.getId(), userId, MessageConstants.IS_LIKE);
        // 首先需要通过传来的messageId与commentorId查询是否存在记录，如果不存在则说明该用户还没有点赞过该消息，就要进行信息的增加操作
        if (CollectionUtils.isEmpty(commentEntities)) {
            // 添加记录
            MessageComment messageComment = new MessageComment();
            messageComment.setIsLike(true);
            messageComment.setCommentator(userService.findById(userId));
            messageComment.setMessageId(messageEntity.getId());
            messageComment.setCommentDate(new Date());
            messageComment.setType(MessageConstants.IS_LIKE);
            messageCommentService.addMessageComment(messageComment);
            isLike = false;
        } else {
            // 如果存在，则说明该用户已经对该消息进行过点赞或者非点赞或者评论，此时需要做的是修改记录的操作
            for (MessageComment messageComment : commentEntities) {
                if(messageComment.getIsLike()){
                    isLike = true;
                    for (MessageComment commentEntity : commentEntities) {
                        commentEntity.setIsLike(!messageComment.getIsLike());
                        messageCommentService.addMessageComment(commentEntity);
                    }
                }else{
                    isLike = false;
                    for (MessageComment commentEntity : commentEntities) {
                        commentEntity.setIsLike(!messageComment.getIsLike());
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
    public ResponseEntity commentMessage(@RequestBody NeighborMessageDTO neighborMessageDTO, @PathVariable String comment){
        // 获取当前用户
        String userId = SecurityUtils.getCurrentUserId();
        // 添加记录
        MessageComment messageComment = new MessageComment();
        messageComment.setComment(comment);
        messageComment.setCommentator(userService.findById(userId));
        messageComment.setMessageId(neighborMessageDTO.getId());
        messageComment.setCommentDate(new Date());
        messageComment.setType(MessageConstants.COMMENT);
        messageCommentService.addMessageComment(messageComment);

        neighborMessageDTO.setCommentNum(messageCommentService.getCommentCounts(neighborMessageDTO.getId()));
        return ResponseEntity.ok(neighborMessageDTO);
    }

    /**
     * 根据messageId来获取评论列表
     */
    @GetMapping("/getCommentList/{messageId}")
    public ResponseEntity getCommentList(@PathVariable String messageId){
        List<MessageComment> messageCommentEntities = messageCommentService.findByMessageIdAndType(messageId, MessageConstants.COMMENT);
        // 封装返回到前台的数据
        List<Map> commentList = new ArrayList<>();
        for(MessageComment messageComment : messageCommentEntities){
            Map<String, Object> commentObject = new HashMap<>();
            commentObject.put("account", messageComment.getCommentator().getAccount());
            commentObject.put("comment", messageComment.getComment());
            commentObject.put("commentDate", DateUtil.date2Str(messageComment.getCommentDate(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
            commentList.add(commentObject);
        }
        return ResponseEntity.ok(commentList);
    }
}
