package com.tongwii.dao;

import com.tongwii.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Device entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IDeviceDao extends JpaRepository<Device, String> {

    @Query("select device from Device device where device.user.account = ?#{principal.username}")
    List<Device> findByUserIsCurrentUser();

}
