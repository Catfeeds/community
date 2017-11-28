package com.tongwii.controller;

import com.tongwii.domain.MessageType;
import com.tongwii.exception.BadRequestAlertException;
import com.tongwii.service.MessageTypeService;
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
 * REST controller for managing MessageType.
 */
@RestController
@RequestMapping("/api/message-type")
public class MessageTypeController {

    private final Logger log = LoggerFactory.getLogger(MessageTypeController.class);

    private static final String ENTITY_NAME = "messageType";

    private final MessageTypeService messageTypeService;

    public MessageTypeController(MessageTypeService messageTypeService) {
        this.messageTypeService = messageTypeService;
    }

    /**
     * POST  /message-type : Create a new messageType.
     *
     * @param messageType the messageTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new messageTypeDTO, or with status 400 (Bad Request) if the messageType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    public ResponseEntity<MessageType> createMessageType(@RequestBody MessageType messageType) throws URISyntaxException {
        log.debug("REST request to save MessageType : {}", messageType);
        if (messageType.getId() != null) {
            throw new BadRequestAlertException("A new messageType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MessageType result = messageTypeService.save(messageType);
        return ResponseEntity.created(new URI("/api/message-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * PUT  /message-type : Updates an existing messageType.
     *
     * @param messageType the messageTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated messageTypeDTO,
     * or with status 400 (Bad Request) if the messageTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the messageTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    public ResponseEntity<MessageType> updateMessageType(@RequestBody MessageType messageType) throws URISyntaxException {
        log.debug("REST request to update MessageType : {}", messageType);
        if (messageType.getId() == null) {
            return createMessageType(messageType);
        }
        MessageType result = messageTypeService.save(messageType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, messageType.getId()))
            .body(result);
    }

    /**
     * GET  /message-type/message-types : get all the messageTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of messageTypes in body
     */
    @GetMapping("/message-types")
    public ResponseEntity<List<MessageType>> getAllMessageTypes(Pageable pageable) {
        log.debug("REST request to get a page of MessageTypes");
        Page<MessageType> page = messageTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/message-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /message-type/:id : get the "id" messageType.
     *
     * @param id the id of the messageTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the messageTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<MessageType> getMessageType(@PathVariable String id) {
        log.debug("REST request to get MessageType : {}", id);
        MessageType messageType = messageTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(messageType));
    }

    /**
     * DELETE  /message-type/:id : delete the "id" messageType.
     *
     * @param id the id of the messageTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessageType(@PathVariable String id) {
        log.debug("REST request to delete MessageType : {}", id);
        messageTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
