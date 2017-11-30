package com.tongwii.controller;

import com.tongwii.domain.Role;
import com.tongwii.exception.BadRequestAlertException;
import com.tongwii.service.RoleService;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by admin on 2017/10/17.
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final Logger log = LoggerFactory.getLogger(RoleController.class);

    private static final String ENTITY_NAME = "role";

    private final RoleService roleService;

    public RoleController(RoleService roleService) {this.roleService = roleService;}

    /**
     * POST  /role : Create a new role.
     *
     * @param role the role to create
     * @return the ResponseEntity with status 201 (Created) and with body the new role, or with status 400 (Bad Request) if the role has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) throws URISyntaxException {
        log.debug("REST request to save Role : {}", role);
        if (role.getId() != null) {
            throw new BadRequestAlertException("A new role cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Role result = roleService.save(role);
        return ResponseEntity.created(new URI("/api/roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * PUT  /roles : Updates an existing role.
     *
     * @param role the role to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated role,
     * or with status 400 (Bad Request) if the role is not valid,
     * or with status 500 (Internal Server Error) if the role couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    public ResponseEntity<Role> updateRole(@RequestBody Role role) throws URISyntaxException {
        log.debug("REST request to update Role : {}", role);
        if (role.getId() == null) {
            return createRole(role);
        }
        Role result = roleService.save(role);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, role.getId()))
            .body(result);
    }

    /**
     * GET  /roles : get all the roles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of roles in body
     */
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles(Pageable pageable) {
        log.debug("REST request to get a page of Roles");
        Page<Role> page = roleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/roles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /roles/:id : get the "id" role.
     *
     * @param id the id of the role to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the role, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRole(@PathVariable String id) {
        log.debug("REST request to get Role : {}", id);
        Role role = roleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(role));
    }

    /**
     * DELETE  /roles/:id : delete the "id" role.
     *
     * @param id the id of the role to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable String id) {
        log.debug("REST request to delete Role : {}", id);
        roleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * 获取角色信息
     */
    @GetMapping("/getRoleInfo")
    public ResponseEntity getRoleInfo(){
        List<Role> roleEntities = roleService.findAll();
        List<Map> roleArray = new ArrayList<>();
        for(Role r: roleEntities){
            Map<String, Object> role = new HashMap<>();
            role.put("roleName", r.getName());
            role.put("roleCode", r.getCode());
            role.put("roleId", r.getId());
            role.put("roleDesc", r.getName());
            roleArray.add(role);
        }
        return ResponseEntity.ok(roleArray);
    }

    /**
     * GET  /roles : get all rolesCode.
     *
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/rolesCode")
    public ResponseEntity getAllRoles() {
        final List<Role> roles = roleService.findAll();
        List<String> roleCodes = Optional.ofNullable(roles).orElse(new ArrayList<>()).stream().map(Role::getCode).collect(Collectors.toList());
        return ResponseEntity.ok(roleCodes);
    }
}
