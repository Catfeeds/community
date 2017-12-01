package com.tongwii.controller;

import com.tongwii.domain.File;
import com.tongwii.exception.BadRequestAlertException;
import com.tongwii.security.SecurityUtils;
import com.tongwii.service.FileService;
import com.tongwii.util.HeaderUtil;
import com.tongwii.util.PaginationUtil;
import com.tongwii.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by admin on 2017/7/28.
 */
@RestController
@RequestMapping("/api/file")
public class FileController {

    private final Logger log = LoggerFactory.getLogger(FileController.class);

    private static final String ENTITY_NAME = "file";

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * POST  /files : Create a new file.
     *
     * @param file the file to create
     * @return the ResponseEntity with status 201 (Created) and with body the new file, or with status 400 (Bad Request) if the file has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    public ResponseEntity<File> createFile(@RequestBody File file) throws URISyntaxException {
        log.debug("REST request to save File : {}", file);
        if (file.getId() != null) {
            throw new BadRequestAlertException("A new file cannot already have an ID", ENTITY_NAME, "idexists");
        }
        File result = fileService.save(file);
        return ResponseEntity.created(new URI("/api/files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * PUT  /files : Updates an existing file.
     *
     * @param file the file to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated file,
     * or with status 400 (Bad Request) if the file is not valid,
     * or with status 500 (Internal Server Error) if the file couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    public ResponseEntity<File> updateFile(@RequestBody File file) throws URISyntaxException {
        log.debug("REST request to update File : {}", file);
        if (file.getId() == null) {
            return createFile(file);
        }
        File result = fileService.save(file);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, file.getId()))
            .body(result);
    }

    /**
     * GET  /files : get all the files.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of files in body
     */
    @GetMapping
    public ResponseEntity<List<File>> getAllFiles(Pageable pageable) {
        log.debug("REST request to get a page of Files");
        Page<File> page = fileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/files");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /files/:id : get the "id" file.
     *
     * @param id the id of the file to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the file, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<File> getFile(@PathVariable String id) {
        log.debug("REST request to get File : {}", id);
        File file = fileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(file));
    }

    /**
     * DELETE  /files/:id : delete the "id" file.
     *
     * @param id the id of the file to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable String id) {
        log.debug("REST request to delete File : {}", id);
        fileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     *  添加图片文件接口
     *
     * */
    @PostMapping("/addPicture")
    public ResponseEntity addPicture(@RequestParam("file") MultipartFile file, HttpServletResponse response){
        try {
            System.out.println("=========开始上传图片======================================");
            String id = SecurityUtils.getCurrentUserLogin();
            // 上传文件并更新用户地址
            File fileEntity = fileService.saveAndUploadFile(id, file);
            System.out.println("==========图片上传完毕======================================");
            // 使用了上传文件的输出流和response的返回json会出错，重置response
            response.reset();
            return ResponseEntity.ok(fileEntity.getId());
        } catch (Exception e) {
            response.reset();
            return ResponseEntity.badRequest().body("图片上传失败!");
        }
    }

    @PostMapping("/upload")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile uploadFile) {

        if (uploadFile.isEmpty()) {
            return ResponseEntity.badRequest().body("请选择上传文件!");
        }
        String userId = SecurityUtils.getCurrentUserId();
        fileService.saveAndUploadFileToFTP(userId, uploadFile);

        return ResponseEntity.ok("上传成功 - " + uploadFile.getOriginalFilename());

    }

    @PostMapping("/upload-files")
    public ResponseEntity uploadFileMulti(@RequestParam("files") MultipartFile[] uploadFiles) {

        String uploadedFileName = Arrays.stream(uploadFiles).map(MultipartFile::getOriginalFilename)
            .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));

        if (StringUtils.isEmpty(uploadedFileName)) {
            return ResponseEntity.badRequest().body("请选择上传文件!");
        }
        String userId = SecurityUtils.getCurrentUserId();
        fileService.saveAndUploadFilesToFTP(userId, Arrays.asList(uploadFiles));
        return ResponseEntity.ok("上传成功 - " + uploadedFileName);
    }
}
