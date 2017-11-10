package com.tongwii.dao;

import com.tongwii.domain.Residence;
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
public interface IResidenceDao extends JpaRepository<Residence, String> {
    List<Residence> findByRegionCode(String regionId);
}
