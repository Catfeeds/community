package com.tongwii.dao;

import com.tongwii.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-21
 */
@Repository
public interface IRegionDao extends JpaRepository<Region, String> {
    List<Region> findByParentCode(String parentCode);

    Region findOneByRegionCode(String regionCode);
}
