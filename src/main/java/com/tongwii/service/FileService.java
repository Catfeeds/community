package com.tongwii.service;

import com.tongwii.dao.IFileDao;
import com.tongwii.domain.FileEntity;
import com.tongwii.service.gateWay.FtpGateway;
import com.tongwii.util.FileUtil;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
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

    private final IFileDao filedao;

    private final FtpGateway ftpGateway;

    private FtpRemoteFileTemplate remoteFileTemplate;

    public FileService(IFileDao filedao, FtpGateway ftpGateway, FtpRemoteFileTemplate remoteFileTemplate) {
        this.filedao = filedao;
        this.ftpGateway = ftpGateway;
        this.remoteFileTemplate = remoteFileTemplate;
    }

    public FileEntity saveAndUploadFile(String userId, MultipartFile file) {
        FileEntity fileEntity = null;
        try {
            String id = UUID.randomUUID().toString();
            String suffix = FileUtil.getFileSuffix(file.getOriginalFilename());
            String relativeUrl = FileUtil.uploadFile(file, id + suffix);
            fileEntity = new FileEntity();
            fileEntity.setFileName(file.getOriginalFilename());
            fileEntity.setFileType(FileUtil.rtnFileType(file.getOriginalFilename()));
            fileEntity.setFilePath(relativeUrl);
            fileEntity.setUploadUserId(userId);
            filedao.saveAndFlush(fileEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public File downloadFile(String fileName, String savePath) {
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
     * @param multipartFile MultipartFile
     */
    public void uploadFileToFTP(MultipartFile multipartFile) {
        File file = FileUtil.multipartToFile(multipartFile);
        ftpGateway.sendToFtp(file);
        file.delete();
    }

    /**
     * 批量上传 (MultipartFile)
     *
     * @param files List<MultipartFile>
     */
    public void uploadFilesToFTP(List<MultipartFile> files) {
        uploadFiles(files, true);
    }


    /**
     * 批量上传 (MultipartFile)
     *
     * @param files List<MultipartFile>
     * @throws IOException
     */
    public void uploadFiles(List<MultipartFile> files, boolean deleteSource) {
        for (MultipartFile multipartFile : files) {
            if (multipartFile.isEmpty()) {
                continue;
            }
            File file = FileUtil.multipartToFile(multipartFile);
            ftpGateway.sendToFtp(file);
            if (deleteSource) {
                file.delete();
            }
        }
    }
}
