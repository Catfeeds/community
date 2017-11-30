package com.tongwii.dao;

import com.tongwii.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Group entity.
 */
@Repository
public interface IGroupDao extends JpaRepository<Group, String> {
/*
    @Query("select group from Group group where group.user.login = ?#{principal.username}")
    List<Group> findByUserIsCurrentUser();
    @Query("select distinct group from Group group left join fetch group.roles")
    List<Group> findAllWithEagerRelationships();

    @Query("select group from Group group left join fetch group.roles where group.id =:id")
    Group findOneWithEagerRelationships(@Param("id") String id);*/

}
