package com.tongwii.service;

import com.tongwii.dao.IGroupDao;
import com.tongwii.domain.Group;
import com.tongwii.dto.GroupDTO;
import com.tongwii.dto.mapper.GroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Group.
 */
@Service
@Transactional
public class GroupService {

    private final Logger log = LoggerFactory.getLogger(GroupService.class);

    private final IGroupDao groupDao;

    private final GroupMapper groupMapper;

    public GroupService(IGroupDao groupDao, GroupMapper groupMapper) {
        this.groupDao = groupDao;
        this.groupMapper = groupMapper;
    }

    /**
     * Save a subGroup.
     *
     * @param groupDTO the entity to save
     * @return the persisted entity
     */
    public GroupDTO save(GroupDTO groupDTO) {
        log.debug("Request to save Group : {}", groupDTO);
        Group group = groupMapper.toEntity(groupDTO);
        group = groupDao.save(group);
        return groupMapper.toDto(group);
    }

    /**
     * Get all the groups.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SubGroups");
        return groupDao.findAll(pageable)
            .map(groupMapper::toDto);
    }

    /**
     * Get one subGroup by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public GroupDTO findOne(String id) {
        log.debug("Request to get Group : {}", id);
        Group group = groupDao.findOne(id);
        return groupMapper.toDto(group);
    }

    /**
     * Delete the subGroup by id.
     *
     * @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Group : {}", id);
        groupDao.delete(id);
    }
}
