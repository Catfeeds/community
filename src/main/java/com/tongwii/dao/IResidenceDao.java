package com.tongwii.dao;

import com.tongwii.domain.ResidenceEntity;
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
public interface IResidenceDao extends JpaRepository<ResidenceEntity, String> {
    List<ResidenceEntity> findByRegionId(String regionId);

    ResidenceEntity findByCode(String code);
}
