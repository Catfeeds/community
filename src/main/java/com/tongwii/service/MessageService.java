package com.tongwii.service;

import com.tongwii.dao.IMessageDao;
import com.tongwii.dao.IMessageTypeDao;
import com.tongwii.domain.File;
import com.tongwii.domain.Message;
import com.tongwii.domain.MessageType;
import com.tongwii.dto.MessageAdminDTO;
import com.tongwii.dto.mapper.MessageAdminMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;


/**
 * Created by admin on 2017/7/13.
 */

@Service
@Transactional
public class MessageService {
    private final IMessageDao messageDao;
    private final IMessageTypeDao messageTypeDao;
    private final FileService fileService;
    private final MessageAdminMapper messageAdminMapper;

    public MessageService(IMessageDao messageDao, IMessageTypeDao messageTypeDao, FileService fileService,
                          MessageAdminMapper messageAdminMapper) {
        this.messageDao = messageDao;
        this.messageTypeDao = messageTypeDao;
        this.fileService = fileService;
        this.messageAdminMapper = messageAdminMapper;
    }

    public void save(Message message) {
        if(Objects.nonNull(message.getMessageType()) && !StringUtils.isEmpty(message.getMessageType().getCode())) {
            MessageType messageType = messageTypeDao.findByCode(message.getMessageType().getCode());
            message.setMessageType(messageType);
        }
        messageDao.save(message);
    }

    public void updateMessageProcess(String messageId, Integer processState) {
        Message message = messageDao.findById(messageId);
        message.setProcessState(processState);
    }


    public Page<Message> findByMessageTypeCodeAndResidenceIdOrderByCreateTimeDesc(Pageable pageable, String messageTypeCode, String residenceId) {
        return messageDao.findByMessageType_CodeAndResidence_IdOrderByCreateTimeDesc(pageable, messageTypeCode, residenceId);
    }

    /**
     * 查询公告类消息
     * */
    public Page<Message> findByResidenceIdOrderByCreateTimeDesc(Pageable pageable, String residenceId) {
        return messageDao.findByResidenceIdOrderByCreateTimeDesc(pageable, residenceId);
    }

    /**
     * 查询历史公告类消息
     * */
    public Page<Message> findByResidenceIdOrderByCreateTimeAsc(Pageable pageable, String residenceId) {
        return messageDao.findByResidenceIdOrderByCreateTimeAsc(pageable, residenceId);
    }

    public Message findByMessageId(String messageId){
        return messageDao.findById(messageId);
    }

    /**
     * 上传文件并更新消息的文件id
     * @param userId 用户id
     * @param messageId 消息id
     * @param multipartFile 附件
     * @return String 文件地址
     */
    public String updateMessageFileById(String userId, String messageId, MultipartFile multipartFile) {
        File file = fileService.saveAndUploadFileToFTP(userId, multipartFile);
        messageDao.updateMessageFileIdById(messageId, file.getId());
        return file.getFilePath();
    }

    /**
     * 根据processState查询消息
     *
     * @param processState 消息处理状态
     * @return List<Message> </Message>
     */
    public List<Message> findByProcessState(int processState) {
        return messageDao.findByProcessState(processState);
    }


    /**
     * Save a message.
     *
     * @param messageDTO the entity to save
     * @return the persisted entity
     */
    public MessageAdminDTO save(MessageAdminDTO messageDTO) {
        Message message = messageAdminMapper.toEntity(messageDTO);
        message = messageDao.save(message);
        return messageAdminMapper.toDto(message);
    }


    /**
     * Get all the messages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MessageAdminDTO> findAll(Pageable pageable) {
        return messageDao.findAll(pageable)
            .map(messageAdminMapper::toDto);
    }

    /**
     * Get one message by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MessageAdminDTO findOne(String id) {
        Message message = messageDao.findOne(id);
        return messageAdminMapper.toDto(message);
    }

    /**
     * Delete the message by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        messageDao.delete(id);
    }
}
