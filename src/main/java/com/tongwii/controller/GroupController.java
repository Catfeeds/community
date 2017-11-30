package com.tongwii.controller;

import com.tongwii.dto.GroupDTO;
import com.tongwii.exception.BadRequestAlertException;
import com.tongwii.service.GroupService;
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
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Group.
 */
@RestController
@RequestMapping("/api/group")
public class GroupController {

    private final Logger log = LoggerFactory.getLogger(GroupController.class);

    private static final String ENTITY_NAME = "group";

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * POST  /sub-groups : Create a new subGroup.
     *
     * @param groupDTO the groupDTO to create
     *
     * @return the ResponseEntity with status 201 (Created) and with body the new groupDTO, or with status 400 (Bad Request) if the subGroup has already an ID
     *
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    public ResponseEntity<GroupDTO> createSubGroup(@RequestBody GroupDTO groupDTO) throws URISyntaxException {
        log.debug("REST request to save Group : {}", groupDTO);
        if (groupDTO.getId() != null) {
            throw new BadRequestAlertException("A new subGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupDTO result = groupService.save(groupDTO);
        return ResponseEntity.created(new URI("/api/sub-groups/" + result.getId())).headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId())).body(result);
    }

    /**
     * PUT  /sub-groups : Updates an existing subGroup.
     *
     * @param groupDTO the groupDTO to update
     *
     * @return the ResponseEntity with status 200 (OK) and with body the updated groupDTO,
     * or with status 400 (Bad Request) if the groupDTO is not valid,
     * or with status 500 (Internal Server Error) if the groupDTO couldn't be updated
     *
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    public ResponseEntity<GroupDTO> updateSubGroup(@RequestBody GroupDTO groupDTO) throws URISyntaxException {
        log.debug("REST request to update Group : {}", groupDTO);
        if (groupDTO.getId() == null) {
            return createSubGroup(groupDTO);
        }
        GroupDTO result = groupService.save(groupDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, groupDTO.getId())).body(result);
    }

    /**
     * GET  /sub-groups : get all the groups.
     *
     * @param pageable the pagination information
     *
     * @return the ResponseEntity with status 200 (OK) and the list of groups in body
     */
    @GetMapping
    public ResponseEntity<List<GroupDTO>> getAllSubGroups(Pageable pageable) {
        log.debug("REST request to get a page of Group");
        Page<GroupDTO> page = groupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sub-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sub-groups/:id : get the "id" subGroup.
     *
     * @param id the id of the subGroupDTO to retrieve
     *
     * @return the ResponseEntity with status 200 (OK) and with body the subGroupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getSubGroup(@PathVariable String id) {
        log.debug("REST request to get Group : {}", id);
        GroupDTO groupDTO = groupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(groupDTO));
    }

    /**
     * DELETE  /sub-groups/:id : delete the "id" subGroup.
     *
     * @param id the id of the subGroupDTO to delete
     *
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubGroup(@PathVariable String id) {
        log.debug("REST request to delete Group : {}", id);
        groupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
