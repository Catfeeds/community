package com.tongwii.controller;

import com.tongwii.domain.Device;
import com.tongwii.exception.BadRequestAlertException;
import com.tongwii.service.DeviceService;
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
 * REST controller for managing Device.
 */
@RestController
@RequestMapping("/api/device")
public class DeviceController {

    private final Logger log = LoggerFactory.getLogger(DeviceController.class);

    private static final String ENTITY_NAME = "device";

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    /**
     * POST  /device : Create a new device.
     *
     * @param device the device to create
     * @return the ResponseEntity with status 201 (Created) and with body the new device, or with status 400 (Bad Request) if the device has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    public ResponseEntity<Device> createDevice(@RequestBody Device device) throws URISyntaxException {
        log.debug("REST request to save Device : {}", device);
        if (device.getId() != null) {
            throw new BadRequestAlertException("A new device cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Device result = deviceService.save(device);
        return ResponseEntity.created(new URI("/api/devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * PUT  /device : Updates an existing device.
     *
     * @param device the device to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated device,
     * or with status 400 (Bad Request) if the device is not valid,
     * or with status 500 (Internal Server Error) if the device couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    public ResponseEntity<Device> updateDevice(@RequestBody Device device) throws URISyntaxException {
        log.debug("REST request to update Device : {}", device);
        if (device.getId() == null) {
            return createDevice(device);
        }
        Device result = deviceService.save(device);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, device.getId()))
            .body(result);
    }

    /**
     * GET  /devices : get all the devices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of devices in body
     */
    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices(Pageable pageable) {
        log.debug("REST request to get a page of Devices");
        Page<Device> page = deviceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/devices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /devices/:id : get the "id" device.
     *
     * @param id the id of the device to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the device, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Device> getDevice(@PathVariable String id) {
        log.debug("REST request to get Device : {}", id);
        Device device = deviceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(device));
    }

    /**
     * DELETE  /devices/:id : delete the "id" device.
     *
     * @param id the id of the device to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable String id) {
        log.debug("REST request to delete Device : {}", id);
        deviceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
