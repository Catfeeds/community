package com.tongwii.controller;

import com.tongwii.domain.Residence;
import com.tongwii.dto.ResidenceDTO;
import com.tongwii.service.ResidenceService;
import com.tongwii.service.UserService;
import com.tongwii.util.HeaderUtil;
import com.tongwii.util.PaginationUtil;
import com.tongwii.util.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by admin on 2017/7/18.
 */
@RestController
@RequestMapping("/api/residence")
public class ResidenceController {
    private final ResidenceService residenceService;
    private final UserService userService;

    private static final String ENTITY_NAME = "residence";

    public ResidenceController(ResidenceService residenceService, UserService userService) {
        this.residenceService = residenceService;
        this.userService = userService;
    }

    /**
     * 添加单个小区信息
     * @author Yamo
     *
     * @param residence 社区
     */
    @PostMapping("/addSingleResidence")
    public ResponseEntity addSingleResidence(@RequestBody Residence residence){
        residenceService.save(residence);
        return ResponseEntity.ok("添加成功!");
    }

    /**
     * POST  /residence : Create a new residence.
     *
     * @param residenceDTO the residence to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bankAccount, or with status 400 (Bad Request) if the bankAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    public ResponseEntity<ResidenceDTO> createResidence(@RequestBody ResidenceDTO residenceDTO) throws URISyntaxException {
        ResidenceDTO result = residenceService.saveDto(residenceDTO);
        return ResponseEntity.created(new URI("/api/residence/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
            .body(residenceDTO);
    }

    /**
     * PUT  /residence : Updates an existing residence.
     *
     * @param residenceDTO the residenceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated residenceDTO,
     * or with status 400 (Bad Request) if the residenceDTO is not valid,
     * or with status 500 (Internal Server Error) if the residenceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    public ResponseEntity<ResidenceDTO> updateResidence(@RequestBody ResidenceDTO residenceDTO) throws URISyntaxException {
        if (residenceDTO.getId() == null) {
            return createResidence(residenceDTO);
        }
        ResidenceDTO result = residenceService.saveDto(residenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, residenceDTO.getId()))
            .body(result);
    }

    /**
     * GET  /residence/:id : get the "id" residence.
     *
     * @param id the id of the residenceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the residenceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResidenceDTO> getResidence(@PathVariable String id) {
        ResidenceDTO residenceDTO = residenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(residenceDTO));
    }

    /**
     * DELETE  /residence/:id : delete the "id" residence.
     *
     * @param id the id of the residenceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResidence(@PathVariable String id) {
        residenceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * 根据residenceId获取社区信息
     * @param residenceId 社区id
     */
    @GetMapping("/getResidenceInfo/{residenceId}")
    public ResponseEntity getResidenceInfo(@PathVariable String residenceId){
        Residence residence = residenceService.findById(residenceId);
        return ResponseEntity.ok(residence);
    }
    /**
     * 根据regionId查询residence信息
     * @param regionCode 社区编码
     * @return result
     * */
    @GetMapping("/find/{regionCode}")
    public ResponseEntity findResidenceByRegionId(@PathVariable String regionCode){

        List<Residence> residenceEntities = residenceService.findResidenceByRegionCode(regionCode);
        if(CollectionUtils.isEmpty(residenceEntities)){
            return ResponseEntity.badRequest().body("抱歉,该区域暂无小区加入本系统!");
        }
        List<Map> jsonArray = new ArrayList<>();
        for(Residence residence : residenceEntities){
            Map<String ,Object> object = new HashMap<>();
            object.put("residenceName", residence.getName());
            object.put("address", residence.getAddress() + residence.getName());
            object.put("residenceId", residence.getId());
            object.put("floorCount", residence.getFloorCount());
            jsonArray.add(object);
        }
        return ResponseEntity.ok(jsonArray);
    }

    /**
     * 修改residence表
     * @param residence 社区
     * @return result
     * */
    @PostMapping("/updateResidenceInfo")
    public ResponseEntity updateResidenceInfo(@RequestBody Residence residence){
        if(residence.getId() == null || residence.getId().isEmpty()){
            return ResponseEntity.badRequest().body("社区实体信息不存在!");
        }
        Residence newResidence = residenceService.findById(residence.getId());
        if(!StringUtils.isEmpty(residence.getName())){
            newResidence.setName(residence.getName());
        }
        if(!StringUtils.isEmpty(residence.getUserId())){
            newResidence.setUserId(residence.getUserId());
        }
        if(!StringUtils.isEmpty(residence.getFloorCount())){
            newResidence.setFloorCount(residence.getFloorCount());
        }
        if(!StringUtils.isEmpty(residence.getRegionCode())){
            newResidence.setRegionCode(residence.getRegionCode());
        }
        if(!StringUtils.isEmpty(residence.getAddress())){
            newResidence.setAddress(residence.getAddress());
        }
        try{
            residenceService.update(newResidence);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("社区信息更新失败!");
        }

        Map<String, Object> object = new HashMap<>();
        object.put("name",newResidence.getName());
        object.put("floorCount",newResidence.getFloorCount());
        object.put("regionId",newResidence.getRegionCode());
        object.put("address",newResidence.getAddress());
        return ResponseEntity.ok(object);
    }

    /**
     * GET  /residence : get all the labels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of residences in body
     */
    @GetMapping
    public ResponseEntity<List<ResidenceDTO>> getAllResidences(Pageable pageable) {
        final Page<ResidenceDTO> page = residenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/residence/all");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
