package com.tongwii.dao;

import com.tongwii.domain.Floor;
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
public interface IFloorDao extends JpaRepository<Floor, String> {
    List<Floor> findByResidenceId(String residenceId);
    List<Floor> findByCodeAndResidenceId(String code, String risidenceId);
}
