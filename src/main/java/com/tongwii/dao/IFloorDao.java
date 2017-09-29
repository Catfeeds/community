package com.tongwii.dao;

import com.tongwii.domain.FloorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-21
 */
@Repository
public interface IFloorDao extends JpaRepository<FloorEntity, String> {
    List<FloorEntity> findByResidenceId(String residenceId);
    List<FloorEntity> findByCodeAndResidenceId(String code, String risidenceId);
}
