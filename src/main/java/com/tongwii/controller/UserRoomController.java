package com.tongwii.controller;

import com.tongwii.domain.User;
import com.tongwii.domain.UserRoom;
import com.tongwii.dto.mapper.UserMapper;
import com.tongwii.exception.BadRequestAlertException;
import com.tongwii.service.UserRoomService;
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
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by admin on 2017/9/30.
 */
@RestController
@RequestMapping("/api/userRoom")
public class UserRoomController {

    private final Logger log = LoggerFactory.getLogger(UserRoomController.class);

    private static final String ENTITY_NAME = "userRoom";

    private final UserRoomService userRoomService;
    private final UserMapper userMapper;

    public UserRoomController(UserRoomService userRoomService, UserMapper userMapper) {
        this.userRoomService = userRoomService;
        this.userMapper = userMapper;
    }

    /**
     * 根据roomId查询用户列表
     * @author Yamo
     * @param roomId 房间id
     */
    @GetMapping("/getUserByRoomId/{roomId}")
    public ResponseEntity getUserByRoomId(@PathVariable String roomId){
        List<UserRoom> userRoomEntities = userRoomService.findUsersByRoomId(roomId);
        if(CollectionUtils.isEmpty(userRoomEntities)){
            return ResponseEntity.badRequest().body("该住房暂未出售，无住户!");
        }
        List<User> userEntities = new ArrayList<>();
        for(UserRoom userRoom : userRoomEntities){
            userEntities.add(userRoom.getUserByUserId());
        }
        return ResponseEntity.ok(userMapper.usersToUserDTOs(userEntities));
    }

    /**
     * POST  /user-rooms : Create a new userRoom.
     *
     * @param userRoom the userRoomDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userRoomDTO, or with status 400 (Bad Request) if the userRoom has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    public ResponseEntity<UserRoom> createUserRoom(@RequestBody UserRoom userRoom) throws URISyntaxException {
        log.debug("REST request to save UserRoom : {}", userRoom);
        if (userRoom.getId() != null) {
            throw new BadRequestAlertException("A new userRoom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserRoom result = userRoomService.save(userRoom);
        return ResponseEntity.created(new URI("/api/user-rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * PUT  /user-rooms : Updates an existing userRoom.
     *
     * @param userRoom the userRoomDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userRoomDTO,
     * or with status 400 (Bad Request) if the userRoomDTO is not valid,
     * or with status 500 (Internal Server Error) if the userRoomDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    public ResponseEntity<UserRoom> updateUserRoom(@RequestBody UserRoom userRoom) throws URISyntaxException {
        log.debug("REST request to update UserRoom : {}", userRoom);
        if (userRoom.getId() == null) {
            return createUserRoom(userRoom);
        }
        UserRoom result = userRoomService.save(userRoom);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userRoom.getId()))
            .body(result);
    }

    /**
     * GET  /user-rooms : get all the userRooms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userRooms in body
     */
    @GetMapping
    public ResponseEntity<List<UserRoom>> getAllUserRooms(Pageable pageable) {
        log.debug("REST request to get a page of UserRooms");
        Page<UserRoom> page = userRoomService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-rooms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-rooms/:id : get the "id" userRoom.
     *
     * @param id the id of the userRoomDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userRoomDTO, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserRoom> getUserRoom(@PathVariable String id) {
        log.debug("REST request to get UserRoom : {}", id);
        UserRoom userRoom = userRoomService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userRoom));
    }

    /**
     * DELETE  /user-rooms/:id : delete the "id" userRoom.
     *
     * @param id the id of the userRoomDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserRoom(@PathVariable String id) {
        log.debug("REST request to delete UserRoom : {}", id);
        userRoomService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
