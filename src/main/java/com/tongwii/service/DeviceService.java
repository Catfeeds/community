package com.tongwii.service;

import com.tongwii.dao.IDeviceDao;
import com.tongwii.domain.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Device.
 */
@Service
@Transactional
public class DeviceService {

    private final Logger log = LoggerFactory.getLogger(DeviceService.class);

    private final IDeviceDao deviceDao;

    public DeviceService(IDeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    /**
     * Save a device.
     *
     * @param device the entity to save
     * @return the persisted entity
     */
    public Device save(Device device) {
        log.debug("Request to save Device : {}", device);
        return deviceDao.save(device);
    }

    /**
     * Get all the devices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Device> findAll(Pageable pageable) {
        log.debug("Request to get all Devices");
        return deviceDao.findAll(pageable);
    }

    /**
     * Get one device by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Device findOne(String id) {
        log.debug("Request to get Device : {}", id);
        return deviceDao.findOne(id);
    }

    /**
     * Delete the device by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Device : {}", id);
        deviceDao.delete(id);
    }
}
