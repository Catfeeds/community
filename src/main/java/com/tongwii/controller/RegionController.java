package com.tongwii.controller;

import com.tongwii.exception.BadRequestAlertException;
import com.tongwii.domain.Region;
import com.tongwii.service.RegionService;
import com.tongwii.util.HeaderUtil;
import com.tongwii.util.PaginationUtil;
import com.tongwii.util.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * Created by admin on 2017/9/28.
 */
@RestController
@RequestMapping("/api/region")
public class RegionController {
    private final RegionService regionService;

    private static final String ENTITY_NAME = "region";

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    /**
     * POST  /regions : Create a new region.
     *
     * @param region the regionDTO to create
     *
     * @return the ResponseEntity with status 201 (Created) and with body the new regionDTO, or with status 400 (Bad
     * Request) if the region has already an ID
     *
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    public ResponseEntity<Region> createRegion(@RequestBody Region region) throws URISyntaxException {
        if (region.getId() != null) {
            throw new BadRequestAlertException("A new region cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Region result = regionService.save(region);
        return ResponseEntity.created(new URI("/api/regions/" + result.getId())).headers(HeaderUtil
            .createEntityCreationAlert(ENTITY_NAME, result.getId())).body(result);
    }

    /**
     * PUT  /regions : Updates an existing region.
     *
     * @param region the regionDTO to update
     *
     * @return the ResponseEntity with status 200 (OK) and with body the updated regionDTO,
     * or with status 400 (Bad Request) if the regionDTO is not valid,
     * or with status 500 (Internal Server Error) if the regionDTO couldn't be updated
     *
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    public ResponseEntity<Region> updateRegion(@RequestBody Region region) throws URISyntaxException {
        if (region.getId() == null) {
            return createRegion(region);
        }
        Region result = regionService.save(region);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, region.getId())).body
            (result);
    }

    /**
     * GET  /regions : get all the regions.
     *
     * @param pageable the pagination information
     *
     * @return the ResponseEntity with status 200 (OK) and the list of regions in body
     */
    @GetMapping("/regions")
    public ResponseEntity<List<Region>> getAllRegions(Pageable pageable) {
        Page<Region> page = regionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/regions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /regions/:id : get the "id" region.
     *
     * @param id the id of the regionDTO to retrieve
     *
     * @return the ResponseEntity with status 200 (OK) and with body the regionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Region> getRegion(@PathVariable String id) {
        Region region = regionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(region));
    }

    /**
     * DELETE  /regions/:id : delete the "id" region.
     *
     * @param id the id of the regionDTO to delete
     *
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegion(@PathVariable String id) {
        regionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * 获取所有省份信息
     * @author Yamo
     */
    @GetMapping("/provinces")
    public ResponseEntity getParentRegion(){
        List<Region> regionEntities = regionService.findByParentCode("0");
        return ResponseEntity.ok(regionEntities);
    }

    /**
     * 根据parentCode查询region信息
     * @author Yamo
     *
     * @param parentCode
     */
    @GetMapping("/code/{parentCode}")
    public ResponseEntity getParentRegion(@PathVariable String parentCode){
        List<Region> regionEntities = regionService.findByParentCode(parentCode);
        return ResponseEntity.ok(regionEntities);
    }
}
