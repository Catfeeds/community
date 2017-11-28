package com.tongwii.service;

import com.tongwii.dao.IMessageTypeDao;
import com.tongwii.domain.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 消息类型service
 *
 * @author Zeral
 * @date 2017-11-10
 */
@Service
@Transactional
public class MessageTypeService {
    private final Logger log = LoggerFactory.getLogger(MessageTypeService.class);

    private final IMessageTypeDao messageTypeDao;

    public MessageTypeService(IMessageTypeDao messageTypeDao) {
        this.messageTypeDao = messageTypeDao;
    }

    MessageType findByCode(String code){
        return messageTypeDao.findByCode(code);
    }

    /**
     * Save a messageType.
     *
     * @param messageType the entity to save
     *
     * @return the persisted entity
     */
    public MessageType save(MessageType messageType) {
        log.debug("Request to save MessageType : {}", messageType);
        return messageTypeDao.save(messageType);
    }

    /**
     * Get all the messageTypes.
     *
     * @param pageable the pagination information
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MessageType> findAll(Pageable pageable) {
        log.debug("Request to get all MessageTypes");
        return messageTypeDao.findAll(pageable);
    }

    /**
     * Get one messageType by id.
     *
     * @param id the id of the entity
     *
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MessageType findOne(String id) {
        log.debug("Request to get MessageType : {}", id);
        return messageTypeDao.findOne(id);
    }

    /**
     * Delete the messageType by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete MessageType : {}", id);
        messageTypeDao.delete(id);
    }
}
