package com.tongwii.service;

import com.tongwii.dao.IResidenceDao;
import com.tongwii.domain.Residence;
import com.tongwii.dto.ResidenceDTO;
import com.tongwii.dto.mapper.ResidenceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Zeral
 * @date 2017-09-21
 */
@Service
@Transactional
public class ResidenceService {
    private final IResidenceDao residenceDao;
    private final ResidenceMapper residenceMapper;

    public ResidenceService(IResidenceDao residenceDao, ResidenceMapper residenceMapper) {
        this.residenceDao = residenceDao;
        this.residenceMapper = residenceMapper;
    }

    public List<Residence> findResidenceByRegionCode(String regionId) {
        return residenceDao.findByRegionCode(regionId);
    }

    /**
     * Get one residence by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ResidenceDTO findOne(String id) {
        Residence residence = residenceDao.findOne(id);
        return residenceMapper.toDto(residence);
    }

    public Residence findById(String id) {
        return residenceDao.findOne(id);
    }

    public void update(Residence newResidence) {
        residenceDao.save(newResidence);
    }

    public Residence save(Residence residence) {
        return residenceDao.save(residence);
    }

    public ResidenceDTO saveDto(ResidenceDTO residenceDTO) {
        Residence residence = residenceMapper.toEntity(residenceDTO);
        residence = residenceDao.save(residence);
        return residenceMapper.toDto(residence);
    }
}
