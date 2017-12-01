package com.tongwii.service;

import com.tongwii.dao.IFileDao;
import com.tongwii.domain.File;
import com.tongwii.service.gateWay.FtpGateway;
import com.tongwii.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * fileService实现类
 *
 * @author: Zeral
 * @date: 2017/7/12
 */
@Service
@Transactional
public class FileService {

    private final Logger log = LoggerFactory.getLogger(FileService.class);

    private final IFileDao fileDao;

    private final FtpGateway ftpGateway;

    private FtpRemoteFileTemplate remoteFileTemplate;

    public FileService(IFileDao fileDao, FtpGateway ftpGateway, FtpRemoteFileTemplate remoteFileTemplate) {
        this.fileDao = fileDao;
        this.ftpGateway = ftpGateway;
        this.remoteFileTemplate = remoteFileTemplate;
    }

    /**
     * Save a file.
     *
     * @param file the entity to save
     * @return the persisted entity
     */
    public File save(File file) {
        log.debug("Request to save File : {}", file);
        return fileDao.save(file);
    }

    /**
     * Get all the files.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<File> findAll(Pageable pageable) {
        log.debug("Request to get all Files");
        return fileDao.findAll(pageable);
    }

    /**
     * Get one file by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public File findOne(String id) {
        log.debug("Request to get File : {}", id);
        return fileDao.findOne(id);
    }

    /**
     * Delete the file by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete File : {}", id);
        fileDao.delete(id);
    }

    /**
     * 上传并保存图片
     *
     * @param userId 用户id
     * @param file 文件
     * @return file 上传的文件实体
     * @throws IOException
     */
    public File saveAndUploadFile(String userId, MultipartFile file) throws IOException {
        String id = UUID.randomUUID().toString();
        String suffix = FileUtil.getFileSuffix(file.getOriginalFilename());
        String relativeUrl = FileUtil.uploadFile(file, id + suffix);
        File fileEntity = new File();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(FileUtil.rtnFileType(file.getOriginalFilename()));
        fileEntity.setFilePath(relativeUrl);
        fileEntity.setUploadUserId(userId);
        fileDao.saveAndFlush(fileEntity);
        return fileEntity;
    }

    /**
     * 查询某个路径下所有文件
     *
     * @param path
     * @return
     */
    public List<String> listAllFile(String path) {
        return remoteFileTemplate.execute(session -> {
            Stream<String> names = Arrays.stream(session.listNames(path));
            return names.collect(Collectors.toList());
        });
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名
     * @param savePath 本地文件存储位置
     * @return
     */
    public java.io.File downloadFile(String fileName, String savePath) {
        return remoteFileTemplate.execute(session -> {
            boolean existFile = session.exists(fileName);
            if (existFile) {
                InputStream is = session.readRaw(fileName);
                return FileUtil.convertInputStreamToFile(is, savePath);
            } else {
                return null;
            }
        });
    }


    /**
     * 文件是否存在
     *
     * @param filePath 文件名
     * @return
     */
    public boolean existFile(String filePath) {
        return remoteFileTemplate.execute(session ->
            session.exists(filePath));
    }

    /**
     * 删除文件
     *
     * @param fileName 待删除文件名
     * @return
     */
    public boolean deleteFile(String fileName) {
        return remoteFileTemplate.execute(session -> {
            boolean existFile = session.exists(fileName);
            return existFile && session.remove(fileName);
        });
    }

    /**
     * 单文件上传 (MultipartFile)
     *
     * @param userId 上传用户id
     * @param multipartFile 上传的文件
     * @return
     */
    public File saveAndUploadFileToFTP(String userId, MultipartFile multipartFile) {
        String suffix = FileUtil.getFileSuffix(multipartFile.getOriginalFilename());
        String fileName = UUID.randomUUID().toString() + suffix;
        String fileUrl = FileUtil.getRelativeFilePath();
        File fileEntity = new File();
        fileEntity.setFileName(multipartFile.getOriginalFilename());
        fileEntity.setFileType(FileUtil.rtnFileType(multipartFile.getOriginalFilename()));
        fileEntity.setFilePath(fileUrl + "/" + fileName);
        fileEntity.setUploadUserId(userId);
        fileDao.saveAndFlush(fileEntity);
        java.io.File file = FileUtil.multipartToFile(multipartFile);
        ftpGateway.sendToFtp(file, fileName, fileUrl);
        file.delete();
        return fileEntity;
    }

    /**
     * 批量上传 (MultipartFile)
     *
     * @param files List<MultipartFile>
     */
    public List<File> saveAndUploadFilesToFTP(String userId, List<MultipartFile> multipartFiles) {
        return multipartFiles.parallelStream().filter(Objects::nonNull).map(file -> this.saveAndUploadFileToFTP(userId, file)).collect(Collectors.toList());
    }
}
