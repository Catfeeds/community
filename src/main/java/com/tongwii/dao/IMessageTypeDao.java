package com.tongwii.dao;

import com.tongwii.domain.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * messageType数据访问层
 *
 * @author Zeral
 * @date 2017-09-21
 */
@Repository
public interface IMessageTypeDao extends JpaRepository<MessageType, String> {

    MessageType findByCode(String code);
}
