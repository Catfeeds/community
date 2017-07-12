package com.tongwii.service.impl;

import com.tongwii.dao.BaseDao;
import com.tongwii.dao.FileDao;
import com.tongwii.po.FileEntity;
import com.tongwii.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * fileService实现类
 *
 * @author: Zeral
 * @date: 2017/7/12
 */
@Service
public class FileServiceImpl extends BaseServiceImpl<FileEntity> implements IFileService {
    @Autowired
    private FileDao filedao;

    @Override
    public BaseDao<FileEntity, String> getDao() {
        return filedao;
    }
}
