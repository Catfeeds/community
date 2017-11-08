package com.tongwii.controller;

import com.tongwii.constant.MessageConstants;
import com.tongwii.domain.MessageCommentEntity;
import com.tongwii.domain.MessageEntity;
import com.tongwii.domain.UserEntity;
import com.tongwii.dto.MessageDto;
import com.tongwii.dto.NeighborMessageDto;
import com.tongwii.dto.mapper.MessageMapper;
import com.tongwii.dto.mapper.NeighborMessageMapper;
import com.tongwii.security.SecurityUtils;
import com.tongwii.service.MessageCommentService;
import com.tongwii.service.MessageService;
import com.tongwii.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

import static com.tongwii.constant.Constants.DEFAULT_PAGE_SIZE;


/**
 * Created by admin on 2017/7/14.
 */
@RestController
@RequestMapping("/message")
public class BaseMessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    NeighborMessageMapper neighborMessageMapper;
    @Autowired
    private MessageCommentService messageCommentService;

    /**
     * 添加消息接口
     *
     * @param messageEntity
     * @return result
     **/
    @PostMapping("/insertMessage")
    public ResponseEntity insertMessage(@RequestBody MessageEntity messageEntity) {
        if (messageEntity.getContent().isEmpty() && messageEntity.getMessageTypeId().isEmpty()) {
            return ResponseEntity.badRequest().body("消息体不可为空!");
        }
        String userId = SecurityUtils.getCurrentUserId();
        messageEntity.setCreateUserId(userId);
        try {
            messageEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            messageService.save(messageEntity);
            return ResponseEntity.ok(messageEntity);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("消息添加失败!");
        }

    }

    /**
     * 修改消息的进度
     *
     * @param messageEntity
     * @return result
     */
    @PutMapping("/updateProcessOfMessage")
    public ResponseEntity updateProcessOfMessage(@RequestBody MessageEntity messageEntity) {
        // 此消息实体包含id与Process的信息，通过id找到该条消息的数据记录，并将Process的状态更改成传来的值
        // 判空
        if (messageEntity.getId().isEmpty() || messageEntity.getProcessState().toString().isEmpty()) {
            return ResponseEntity.badRequest().body("消息记录不存在!");
        }
        // 此处更改消息进度状态
        messageService.updateMessageProcess(messageEntity.getId(), messageEntity.getProcessState() & 0xff);
        return ResponseEntity.ok("修改状态成功!");
    }

    /**
     * 删除历史消息
     *
     * @param messageId
     */
    @PutMapping("/deleteMessage/{messageId}")
    public ResponseEntity deleteMessage(@PathVariable String messageId) {
        // 此消息实体包含id,通过id找到该条消息的数据记录，并将Process的状态更改成-1状态
        try {
            messageService.updateMessageProcess(messageId, -1);
            return ResponseEntity.ok("消息删除成功!");

        } catch (Exception e) {
            return ResponseEntity.ok("消息删除失败!");
        }
    }

    /**
     * 通过消息类型查询消息
     *
     * @param message
     * @return result
     */
    @RequestMapping("/selectMessageByType")
    public ResponseEntity selectMessageByType(@RequestHeader("page") Integer page, @RequestBody MessageEntity message) {
        if (message.getMessageTypeId() == null || message.getMessageTypeId().isEmpty()) {
            return ResponseEntity.badRequest().body("消息类型不可为空!");
        }
        Pageable pageInfo = new PageRequest(page, 5);
        Page<MessageEntity> messageEntityPage = messageService.findByMessageTypeIdAndResidenceIdOrderByCreateTimeDesc(pageInfo, message.getMessageTypeId(), message.getResidenceId());
        List<MessageEntity> messageEntities = messageEntityPage.getContent();
        if (CollectionUtils.isEmpty(messageEntities)) {
            return ResponseEntity.badRequest().body("信息查询失败!");
        }
        List<Map> messageJsonArray = new ArrayList<>();
        Map<String, Object> messageObject = new HashMap<>();
        for (MessageEntity messageEntity : messageEntities) {
            messageObject.put("id", messageEntity.getId());
            messageObject.put("title", messageEntity.getTitle());
            messageObject.put("content", messageEntity.getContent());
            messageObject.put("type", messageEntity.getMessageTypeId());
            String time = messageEntity.getCreateTime().toString();
            String createTime = time.substring(0, time.length() - 2);
            messageObject.put("createTime", createTime);
            // 通过userId查询userName
            UserEntity userEntity = userService.findById(messageEntity.getCreateUserId());
            messageObject.put("createUser", userEntity.getAccount());
            messageJsonArray.add(messageObject);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("totalPages", messageEntityPage.getTotalPages());
        data.put("messageInfo", messageJsonArray);
        return ResponseEntity.ok(data);
    }

    /**
     * 查询公告类消息
     *
     * @return result
     */
    @GetMapping("/selectAnnounceMessage/{residenceId}")
    public ResponseEntity selectAnnounceMessage(@RequestHeader("page") Integer page, @PathVariable(value = "residenceId") String residenceId) {
        Pageable pageInfo = new PageRequest(page, DEFAULT_PAGE_SIZE);
        Page<MessageEntity> messageEntityPage = messageService.findByResidenceIdOrderByCreateTimeDesc(pageInfo, residenceId);
        List<MessageDto> messageDtos = messageMapper.messagesToMessageDtos(messageEntityPage.getContent());
        Map map = new HashMap<>();
        map.put("totalPages", messageEntityPage.getTotalPages());
        map.put("data", messageDtos);
        return ResponseEntity.ok(map);
    }

    /**
     * 查询历史公告类消息
     *
     * @return result
     */
    @GetMapping("/selectHistroyAnnounceMessage/{residenceId}")
    public ResponseEntity selectHistroyAnnounceMessage(@RequestHeader("page") Integer page, @PathVariable(value = "residenceId") String residenceId) {
        Pageable pageInfo = new PageRequest(page, DEFAULT_PAGE_SIZE);
        Page<MessageEntity> messageEntityPage = messageService.findByResidenceIdOrderByCreateTimeAsc(pageInfo, residenceId);
        List<MessageDto> messageDtos = messageMapper.messagesToMessageDtos(messageEntityPage.getContent());
        Map map = new HashMap<>();
        map.put("totalPages", messageEntityPage.getTotalPages());
        map.put("data", messageDtos);
        return ResponseEntity.ok(map);
    }

    /**
     * 查询邻里消息
     */
    @GetMapping("/selectNeighborMessage/{residenceId}")
    public ResponseEntity selectNeighborMessage(@RequestHeader("page") Integer page, @PathVariable(value = "residenceId") String residenceId) {
        Pageable pageInfo = new PageRequest(page, 15);
        try {
            Page<MessageEntity> messageEntityPage = messageService.findByMessageTypeIdAndResidenceIdOrderByCreateTimeDesc(pageInfo, MessageConstants.DYNAMIC_MESSAGE.toString(), residenceId);
            List<MessageEntity> messageEntities = messageEntityPage.getContent();
            List<NeighborMessageDto> neighborMessageDtoList = neighborMessageMapper.messagesToNeighborMessageDtos(messageEntities);

            for(NeighborMessageDto neighborMessageDto: neighborMessageDtoList){
                Integer likeNum = 0;
                Integer commentNum = 0;
                // 封装点赞参数
                List<MessageCommentEntity> likeMessageEntities = messageCommentService.findByMessageIdAndType(neighborMessageDto.getId(), MessageConstants.IS_LIKE);
                for(MessageCommentEntity commentEntity: likeMessageEntities){
                    if(commentEntity.getIsLike()!=null && commentEntity.getIsLike()){
                        likeNum++;
                    }
                }
                // 封装评论参数
                List<MessageCommentEntity> commentMessageEntities = messageCommentService.findByMessageIdAndType(neighborMessageDto.getId(), MessageConstants.COMMENT);
                for(MessageCommentEntity commentEntity: commentMessageEntities){
                    if(commentEntity.getComment()!=null ||!(StringUtils.isEmpty(commentEntity.getComment()))){
                        commentNum++;
                    }
                }
                neighborMessageDto.setLikeNum(likeNum);
                neighborMessageDto.setCommentNum(commentNum);
            }

            Map map = new HashMap<>();
            map.put("totalPages", messageEntityPage.getTotalPages());
            map.put("data", neighborMessageDtoList);
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("暂无动态消息!");
        }
    }

    @GetMapping("/getMessageByMessageId/{messageId}")
    public ResponseEntity getMessageByMessageId(@PathVariable String messageId){
        return ResponseEntity.ok(messageService.findByMessageId(messageId));
    }
}
