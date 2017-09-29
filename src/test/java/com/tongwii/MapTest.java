package com.tongwii;

import com.tongwii.domain.MessageEntity;
import com.tongwii.service.MessageService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * TreeMap测试
 *
 * @author Zeral
 * @date 2017-07-27
 */
public class MapTest extends Tester{
@Autowired
private MessageService messageService;
    @Test
    public void test1() {
        Map<String, String> contactMap = new TreeMap<>();
        contactMap.put("H", "test");
        contactMap.put("F", "zzz");
        contactMap.put("B", "ttt");
        contactMap.put("A", "dfadsf");
        for (String string : contactMap.keySet()) {
            System.out.printf(string);
        }
    }
    @Test
    public void test2(){
        PageRequest pageRequest = new PageRequest(0,20);
        Page<MessageEntity> messageEntityList = messageService.findByResidenceIdOrderByCreateTimeDesc(pageRequest,"1");
        for (MessageEntity messageEntity : messageEntityList) {
            System.out.println(messageEntity.getCreateTime()+"-----"+messageEntity.getContent());
        }
    }
}
