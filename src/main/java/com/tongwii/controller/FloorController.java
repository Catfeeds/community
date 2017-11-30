package com.tongwii.controller;

import com.tongwii.domain.Floor;
import com.tongwii.dto.FloorDTO;
import com.tongwii.exception.BadRequestAlertException;
import com.tongwii.service.FloorService;
import com.tongwii.service.ResidenceService;
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
@RequestMapping("/api/floor")
public class FloorController {

    private final Logger log = LoggerFactory.getLogger(FloorController.class);

    private static final String ENTITY_NAME = "floor";

    private final FloorService floorService;
    private final ResidenceService residenceService;

    public FloorController(FloorService floorService, ResidenceService residenceService) {
        this.floorService = floorService;
        this.residenceService = residenceService;
    }

    /**
     * 添加单个楼宇信息
     * @author Yamo
     *
     * @param floor 楼宇
     */
    @PostMapping("/addSingleFloor")
    public ResponseEntity addSingleFloor(@RequestBody Floor floor){
        // TODO 此处需要做楼宇名称的唯一性设置
        List<Floor> floorEntities = floorService.findFloorByResidenceId(floor.getResidenceId());
        boolean floorExist = false;
        if(!CollectionUtils.isEmpty(floorEntities)){
            for(Floor floor1 : floorEntities){
                if(floor1.getCode().equals(floor.getCode())){
                    floorExist = true;
                }
            }
        }
        if(floorExist){
            return ResponseEntity.badRequest().body("楼宇已存在!");
        }
        try {
            floorService.save(floor);
            return ResponseEntity.ok("添加成功!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("添加失败!");
        }
    }

    /**
     * 根据floorCode查询记录
     * @author Yamo
     * @param floorCode 楼宇编码
     * @param residenceId 社区id
     */
    @GetMapping("/getFloorByFloorCode/{residenceId}/{floorCode}")
    public ResponseEntity getFloorByFloorCode(@PathVariable String residenceId, @PathVariable String floorCode){
        List<Floor> floorEntities = floorService.findByCodeAndResidenceId(residenceId, floorCode);
        if(CollectionUtils.isEmpty(floorEntities)){
            return ResponseEntity.badRequest().body("无楼宇信息!");
        }
        return ResponseEntity.ok(floorEntities);
    }
    /**
     * 根据residenceId查询floor列表
     * @param residenceId 社区id
     * @return result
     * */
    @GetMapping(value = "/floor/{residenceId}")
    public ResponseEntity findFloorByResidenceId(@PathVariable String residenceId){
        List<Floor> floorEntities = floorService.findFloorByResidenceId(residenceId);
        String address = residenceService.findById(residenceId).getAddress();
        if(floorEntities == null){
            return ResponseEntity.badRequest().body("此社区没有楼宇实体!");
        }
        List<Map> jsonArray = new ArrayList<>();
        for(Floor floor : floorEntities){
            Map<String, Object> object = new HashMap<>();
            object.put("floorName", floor.getCode());
            object.put("floorPiles", floor.getFloorNumber());
            object.put("isElev", floor.getHasElev());
            object.put("floorId", floor.getId());
            object.put("address", address+ floor.getCode()+"栋");
            jsonArray.add(object);
        }
        return ResponseEntity.ok(jsonArray);
    }

    /**
     * 修改flooe表信息
     * @param floor 楼宇
     * @return result
     * */
    @PutMapping(value = "/updateFloorInfo")
    public ResponseEntity updateFloorInfo(@RequestBody Floor floor){
        if(StringUtils.isEmpty(floor.getId())){
            return ResponseEntity.badRequest().body("楼宇实体不存在!");
        }
        Floor newFloor= floorService.findById(floor.getId());
        if(!StringUtils.isEmpty(floor.getCode())){
            newFloor.setCode(floor.getCode());
        }
        if(!StringUtils.isEmpty(floor.getPrincipalId())){
            newFloor.setPrincipalId(floor.getPrincipalId());
        }
        if(!StringUtils.isEmpty(floor.getResidenceId())){
            newFloor.setResidenceId(floor.getResidenceId());
        }
        try{
            floorService.update(newFloor);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("楼宇信息修改失败!");
        }
        Map<String, Object> object = new HashMap<>();
        object.put("floorName", newFloor.getCode());
        object.put("principalId", newFloor.getPrincipalId());
        object.put("residenceId", newFloor.getResidenceId());
        return ResponseEntity.ok(object);
    }

    /**
     * POST  /floors : Create a new floor.
     *
     * @param floorDTO the floorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new floorDTO, or with status 400 (Bad Request) if the floor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    public ResponseEntity<FloorDTO> createFloor(@RequestBody FloorDTO floorDTO) throws URISyntaxException {
        log.debug("REST request to save Floor : {}", floorDTO);
        if (floorDTO.getId() != null) {
            throw new BadRequestAlertException("A new floor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FloorDTO result = floorService.save(floorDTO);
        return ResponseEntity.created(new URI("/api/floors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * PUT  /floors : Updates an existing floor.
     *
     * @param floorDTO the floorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated floorDTO,
     * or with status 400 (Bad Request) if the floorDTO is not valid,
     * or with status 500 (Internal Server Error) if the floorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    public ResponseEntity<FloorDTO> updateFloor(@RequestBody FloorDTO floorDTO) throws URISyntaxException {
        log.debug("REST request to update Floor : {}", floorDTO);
        if (floorDTO.getId() == null) {
            return createFloor(floorDTO);
        }
        FloorDTO result = floorService.save(floorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, floorDTO.getId()))
            .body(result);
    }

    /**
     * GET  /floors : get all the floors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of floors in body
     */
    @GetMapping
    public ResponseEntity<List<FloorDTO>> getAllFloors(Pageable pageable) {
        log.debug("REST request to get a page of Floors");
        Page<FloorDTO> page = floorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/floors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /floors/:id : get the "id" floor.
     *
     * @param id the id of the floorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the floorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<FloorDTO> getFloor(@PathVariable String id) {
        log.debug("REST request to get Floor : {}", id);
        FloorDTO floorDTO = floorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(floorDTO));
    }

    /**
     * DELETE  /floors/:id : delete the "id" floor.
     *
     * @param id the id of the floorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFloor(@PathVariable String id) {
        log.debug("REST request to delete Floor : {}", id);
        floorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

}
