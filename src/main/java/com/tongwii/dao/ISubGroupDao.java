package com.tongwii.dao;

import com.tongwii.domain.SubGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-21
 */
@Repository
public interface ISubGroupDao extends JpaRepository<SubGroup, String> {
}
