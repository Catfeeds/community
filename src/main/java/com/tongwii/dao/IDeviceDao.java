package com.tongwii.dao;

import com.tongwii.domain.Device;
import com.tongwii.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-11-21
 */
public interface IDeviceDao extends JpaRepository<Device, String> {

}
