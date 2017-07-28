package com.tongwii.dao;

import com.tongwii.po.MessageEntity;
import com.tongwii.service.IPageInfo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageDao extends BaseDao<MessageEntity, String> {
    /**
     * 修改message状态
     * @param messageId
     * @param processState
     * */
    public boolean updateMessageState(String messageId, Integer processState){
        String hql = "update MessageEntity message set message.processState=? where message.id=?";
        int state  = executeBulk(hql, processState.byteValue(), messageId);
        if(state > 1){
            return true;
        }
        return false;
    }

    /**
     *按类型查询消息
     * @param messageTypeId
     * @param pageInfo
     * @param residenceId
     * @return
     **/
    public List<MessageEntity> selectMessageByType(IPageInfo pageInfo, String messageTypeId, String residenceId){
//        order by createTime desc
        String hql = "from MessageEntity where messageTypeId=? and residenceId=? order by createTime desc";
        List<MessageEntity> messageEntities = findByHQL(hql,pageInfo,messageTypeId, residenceId);
        return messageEntities;
    }
	
}
