package com.tongwii.controller;

import com.google.common.collect.ImmutableMap;
import com.tongwii.constant.MessageConstants;
import com.tongwii.domain.File;
import com.tongwii.domain.Message;
import com.tongwii.domain.User;
import com.tongwii.dto.MessageAdminDTO;
import com.tongwii.dto.MessageDTO;
import com.tongwii.dto.NeighborMessageDTO;
import com.tongwii.dto.mapper.MessageMapper;
import com.tongwii.dto.mapper.NeighborMessageMapper;
import com.tongwii.dto.mapper.UserMapper;
import com.tongwii.exception.BadRequestAlertException;
import com.tongwii.security.SecurityUtils;
import com.tongwii.service.FileService;
import com.tongwii.service.MessageCommentService;
import com.tongwii.service.MessageService;
import com.tongwii.service.UserService;
import com.tongwii.util.HeaderUtil;
import com.tongwii.util.PaginationUtil;
import com.tongwii.util.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.*;

import static com.tongwii.constant.Constants.DEFAULT_PAGE_SIZE;


/**
 * Created by admin on 2017/7/14.
 */
@RestController
@RequestMapping("/api/message")
public class BaseMessageController {
    private final MessageService messageService;
    private final UserService userService;
    private final MessageMapper messageMapper;
    private final NeighborMessageMapper neighborMessageMapper;
    private final MessageCommentService messageCommentService;
    private final FileService fileService;

    private static final String ENTITY_NAME = "message";

    public BaseMessageController(MessageService messageService, UserService userService, MessageMapper messageMapper, NeighborMessageMapper neighborMessageMapper, MessageCommentService messageCommentService, FileService fileService, UserMapper userMapper) {
        this.messageService = messageService;
        this.userService = userService;
        this.messageMapper = messageMapper;
        this.neighborMessageMapper = neighborMessageMapper;
        this.messageCommentService = messageCommentService;
        this.fileService = fileService;
    }

    /**
     * POST  /messages : Create a new message.
     *
     * @param messageDTO the messageDTO to create
     *
     * @return the ResponseEntity with status 201 (Created) and with body the new messageDTO, or with status 400 (Bad
     * Request) if the message has already an ID
     *
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    public ResponseEntity<MessageAdminDTO> createMessage(@RequestBody MessageAdminDTO messageDTO) throws URISyntaxException {
        if (messageDTO.getId() != null) {
            throw new BadRequestAlertException("A new message cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MessageAdminDTO result = messageService.save(messageDTO);
        return ResponseEntity.created(new URI("/api/messages/" + result.getId())).headers(HeaderUtil
            .createEntityCreationAlert(ENTITY_NAME, result.getId())).body(result);
    }

    /**
     * PUT  /messages : Updates an existing message.
     *
     * @param messageDTO the messageDTO to update
     *
     * @return the ResponseEntity with status 200 (OK) and with body the updated messageDTO,
     * or with status 400 (Bad Request) if the messageDTO is not valid,
     * or with status 500 (Internal Server Error) if the messageDTO couldn't be updated
     *
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    public ResponseEntity<MessageAdminDTO> updateMessage(@RequestBody MessageAdminDTO messageDTO) throws URISyntaxException {
        if (messageDTO.getId() == null) {
            return createMessage(messageDTO);
        }
        MessageAdminDTO result = messageService.save(messageDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, messageDTO.getId())).body
            (result);
    }

    /**
     * GET  /messages : get all the messages.
     *
     * @param pageable the pagination information
     *
     * @return the ResponseEntity with status 200 (OK) and the list of messages in body
     */
    @GetMapping
    public ResponseEntity<List<MessageAdminDTO>> getAllMessages(Pageable pageable) {
        Page<MessageAdminDTO> page = messageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/messages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /message/:id : get the "id" message.
     *
     * @param id the id of the messageDTO to retrieve
     *
     * @return the ResponseEntity with status 200 (OK) and with body the messageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<MessageAdminDTO> getMessage(@PathVariable String id) {
        MessageAdminDTO messageDTO = messageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(messageDTO));
    }

    /**
     * DELETE  /message/:id : delete the "id" message.
     *
     * @param id the id of the messageDTO to delete
     *
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable String id) {
        messageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * 添加消息接口
     *
     * @param message 添加的消息
     * @return result
     **/
    @PostMapping("/insertMessage")
    public ResponseEntity insertMessage(@RequestBody Message message) {
        if (StringUtils.isEmpty(message.getContent())) {
            return ResponseEntity.badRequest().body("消息体不可为空!");
        }
        String userId = SecurityUtils.getCurrentUserId();
        message.setCreateUserId(userId);
        try {
            message.setCreateTime(new Timestamp(System.currentTimeMillis()));
            messageService.save(message);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("消息添加失败!");
        }

    }

    // 上传消息图片
    @PostMapping("/uploadPicture")
    public ResponseEntity uploadAvatar(@RequestParam("file") MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return ResponseEntity.badRequest().body("请选择上传文件!");
        }
        String userId = SecurityUtils.getCurrentUserId();
        File file = fileService.saveAndUploadFileToFTP(userId, multipartFile);
        Map<String, Object> fileDto = ImmutableMap.<String, Object> builder()
            .put("url", file.getFilePath())
            .put("id", file.getId())
            .build();
        return ResponseEntity.ok(fileDto);
    }

    /**
     * 修改消息的进度
     *
     * @param message 消息
     * @return result
     */
    @PutMapping("/updateProcessOfMessage")
    public ResponseEntity updateProcessOfMessage(@RequestBody @Valid Message message) {
        // 此消息实体包含id与Process的信息，通过id找到该条消息的数据记录，并将Process的状态更改成传来的值
        messageService.updateMessageProcess(message.getId(), message.getProcessState() & 0xff);
        return ResponseEntity.ok("修改状态成功!");
    }

    /**
     * 删除历史消息
     *
     * @param messageId 消息id
     */
    @PutMapping("/deleteMessage/{messageId}")
    public ResponseEntity updateMessage(@PathVariable String messageId) {
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
     * @param message 消息
     * @return result
     */
    @RequestMapping("/selectMessageByType")
    public ResponseEntity selectMessageByType(@RequestHeader("page") int page, @RequestBody Message message) {
        if (Objects.isNull(message.getMessageType()) || message.getMessageType().getCode().isEmpty()) {
            return ResponseEntity.badRequest().body("消息类型不可为空!");
        }
        Pageable pageInfo = new PageRequest(page, 5);
        Page<Message> messageEntityPage = messageService.findByMessageTypeCodeAndResidenceIdOrderByCreateTimeDesc(pageInfo, message.getMessageType().getCode(), message.getResidenceId());
        if (!messageEntityPage.hasContent()) {
            return ResponseEntity.badRequest().body("没有数据了");
        }
        List<Message> messageEntities = messageEntityPage.getContent();
        List<Map> messageJsonArray = new ArrayList<>();
        Map<String, Object> messageObject = new HashMap<>();
        for (Message messageEntity : messageEntities) {
            messageObject.put("id", messageEntity.getId());
            messageObject.put("title", messageEntity.getTitle());
            messageObject.put("content", messageEntity.getContent());
            messageObject.put("type", messageEntity.getMessageType().getCode());
            messageObject.put("fileUrl", Objects.nonNull(messageEntity.getFile()) ? messageEntity.getFile().getFilePath() : null);
            String time = messageEntity.getCreateTime().toString();
            String createTime = time.substring(0, time.length() - 2);
            messageObject.put("createTime", createTime);
            // 通过userId查询userName
            User user = userService.findById(messageEntity.getCreateUserId());
            messageObject.put("createUser", user.getAccount());
            messageObject.put("createUserAvatar", user.getImageUrl());
            messageJsonArray.add(messageObject);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("messageInfo", messageJsonArray);
        data.put("totalPages", messageEntityPage.getTotalPages());
        return ResponseEntity.ok(data);
    }

    /**
     * 查询公告类消息
     *
     * @return result
     */
    @GetMapping("/selectAnnounceMessage/{residenceId}")
    public ResponseEntity selectAnnounceMessage(@RequestHeader("page") int page, @PathVariable(value = "residenceId") String residenceId) {
        Pageable pageInfo = new PageRequest(page, DEFAULT_PAGE_SIZE);
        Page<Message> messageEntityPage = messageService.findByResidenceIdOrderByCreateTimeDesc(pageInfo, residenceId);
        if(!messageEntityPage.hasContent()) {
            return ResponseEntity.badRequest().body("没有数据了");
        }
        List<MessageDTO> messageDTOS = messageMapper.toDto(messageEntityPage.getContent());
        Map<String, Object> map = new HashMap<>();
        map.put("totalPages", messageEntityPage.getTotalPages());
        map.put("data", messageDTOS);
        return ResponseEntity.ok(map);
    }

    /**
     * 查询历史公告类消息
     *
     * @return result
     */
    @GetMapping("/selectHistroyAnnounceMessage/{residenceId}")
    public ResponseEntity selectHistroyAnnounceMessage(@RequestHeader("page") int page, @PathVariable(value = "residenceId") String residenceId) {
        Pageable pageInfo = new PageRequest(page, DEFAULT_PAGE_SIZE);
        Page<Message> messageEntityPage = messageService.findByResidenceIdOrderByCreateTimeAsc(pageInfo, residenceId);
        if(!messageEntityPage.hasContent()) {
            return ResponseEntity.badRequest().body("没有数据了");
        }
        List<MessageDTO> messageDTOS = messageMapper.toDto(messageEntityPage.getContent());
        Map<String, Object> map = new HashMap<>();
        map.put("totalPages", messageEntityPage.getTotalPages());
        map.put("data", messageDTOS);
        return ResponseEntity.ok(map);
    }

    /**
     * 查询邻里消息
     */
    @GetMapping("/selectNeighborMessage/{residenceId}")
    public ResponseEntity selectNeighborMessage(@RequestHeader("page") int page, @PathVariable(value = "residenceId") String residenceId) {
        Pageable pageInfo = new PageRequest(page, 15);
        try {
            Page<Message> messageEntityPage = messageService.findByMessageTypeCodeAndResidenceIdOrderByCreateTimeDesc(pageInfo, MessageConstants.DYNAMIC_MESSAGE, residenceId);
            if (!messageEntityPage.hasContent()) {
                return ResponseEntity.badRequest().body("没有数据了");
            }
            List<Message> messageEntities = messageEntityPage.getContent();
            List<NeighborMessageDTO> neighborMessageDTOList = neighborMessageMapper.toDto(messageEntities);

            for(NeighborMessageDTO neighborMessageDTO : neighborMessageDTOList){
                // 封装点赞参数
                int likeNum = messageCommentService.getCountByMessageIdAndType(neighborMessageDTO.getId(), MessageConstants.IS_LIKE);
                // 封装评论参数
                int commentNum = messageCommentService.getCountByMessageIdAndType(neighborMessageDTO.getId(), MessageConstants.COMMENT);
                neighborMessageDTO.setLikeNum(likeNum);
                neighborMessageDTO.setCommentNum(commentNum);
            }

            Map<String, Object> map = new HashMap<>();
            map.put("totalPages", messageEntityPage.getTotalPages());
            map.put("data", neighborMessageDTOList);
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("暂无动态消息!");
        }
    }

    @GetMapping("/getMessageByMessageId/{messageId}")
    public ResponseEntity getMessageByMessageId(@PathVariable String messageId){
        return ResponseEntity.ok(messageMapper.toDto(messageService.findByMessageId(messageId)));
    }
}
